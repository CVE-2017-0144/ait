package dev.loqor.portal.client;

import com.mojang.datafixers.util.Pair;

import dev.amble.ait.AITMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public record PortalData(UUID id, LevelRenderer renderer, ClientLevel world, WorldGeometryRenderer geometry) {

    private static int renderDistanceBlocks() {
        return AITMod.CONFIG.botiRenderDistance * 16;
    }

    public void onChunkDeltaUpdate(ClientboundSectionBlocksUpdatePacket packet) {
        packet.runUpdates(this::handleBlockUpdate);
    }

    public void onBlockUpdate(ClientboundBlockUpdatePacket packet) {
        handleBlockUpdate(packet.getPos(), packet.getBlockState());
    }

    private void handleBlockUpdate(BlockPos pos, BlockState state) {
        this.world.setServerVerifiedBlockState(pos, state, Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
        markSectionsDirty(pos);
    }

    private void markSectionsDirty(BlockPos pos) {
        WorldGeometryRenderer renderer = this.geometry;

        int sectionX = pos.getX() >> 4;
        int sectionY = pos.getY() >> 4;
        int sectionZ = pos.getZ() >> 4;

        int localX = pos.getX() & 15;
        int localY = pos.getY() & 15;
        int localZ = pos.getZ() & 15;

        for (int dx = (localX == 0 ? -1 : 0); dx <= (localX == 15 ? 1 : 0); dx++)
            for (int dy = (localY == 0 ? -1 : 0); dy <= (localY == 15 ? 1 : 0); dy++)
                for (int dz = (localZ == 0 ? -1 : 0); dz <= (localZ == 15 ? 1 : 0); dz++)
                    renderer.markSectionDirty(SectionPos.of(sectionX + dx, sectionY + dy, sectionZ + dz));
    }

    public void onChunkData(ClientboundLevelChunkWithLightPacket chunkDataS2CPacket) {
        int i = chunkDataS2CPacket.getX();
        int j = chunkDataS2CPacket.getZ();

        this.world.getChunkSource().updateViewCenter(i, j);

        this.loadChunk(i, j, chunkDataS2CPacket.getChunkData());
        ClientboundLightUpdatePacketData lightData = chunkDataS2CPacket.getLightData();

        this.world.queueLightUpdate(() -> {
            this.readLightData(i, j, lightData);
            LevelChunk worldChunk = this.world.getChunkSource().getChunk(i, j, false);
            if (worldChunk != null) {
                this.scheduleRenderChunk(worldChunk, i, j);
            }
        });
    }

    private void readLightData(int x, int z, ClientboundLightUpdatePacketData data) {
        LevelLightEngine lightingProvider = this.world.getChunkSource().getLightEngine();
        BitSet bitSet = data.getSkyYMask();
        BitSet bitSet2 = data.getEmptySkyYMask();
        Iterator<byte[]> iterator = data.getSkyUpdates().iterator();
        this.updateLighting(x, z, lightingProvider, LightLayer.SKY, bitSet, bitSet2, iterator);
        BitSet bitSet3 = data.getBlockYMask();
        BitSet bitSet4 = data.getEmptyBlockYMask();
        Iterator<byte[]> iterator2 = data.getBlockUpdates().iterator();
        this.updateLighting(x, z, lightingProvider, LightLayer.BLOCK, bitSet3, bitSet4, iterator2);
        lightingProvider.setLightEnabled(new ChunkPos(x, z), true);
    }

    private void updateLighting(int chunkX, int chunkZ, LevelLightEngine provider, LightLayer type, BitSet inited, BitSet uninited, Iterator<byte[]> nibbles) {
        WorldGeometryRenderer renderer = this.geometry;

        for (int i = 0; i < provider.getLightSectionCount(); ++i) {
            int j = provider.getMinLightSection() + i;
            boolean bl = inited.get(i);
            boolean bl2 = uninited.get(i);
            if (!bl && !bl2) continue;
            provider.queueSectionData(type, SectionPos.of(chunkX, j, chunkZ), bl ? new DataLayer(nibbles.next().clone()) : new DataLayer());

            if (renderer != null)
                renderer.markSectionDirty(SectionPos.of(chunkX, j, chunkZ));
        }
    }

    private void loadChunk(int x, int z, ClientboundLevelChunkPacketData chunkData) {
        this.world.getChunkSource().replaceWithPacketData(x, z, chunkData.getReadBuffer(), chunkData.getHeightmaps(), chunkData.getBlockEntitiesTagsConsumer(x, z));
    }

    public void onChunkRenderDistanceCenter(ClientboundSetChunkCacheCenterPacket packet) {
        this.world.getChunkSource().updateViewCenter(packet.getX(), packet.getZ());
    }

    public void onWorldTime(ClientboundSetTimePacket packet) {
        this.world.setGameTime(packet.getGameTime());
        this.world.setDayTime(packet.getDayTime());
    }

    public void onGameStateChange(ClientboundGameEventPacket packet) {
        ClientboundGameEventPacket.Type reason = packet.getEvent();
        float value = packet.getParam();

        if (reason == ClientboundGameEventPacket.RAIN_LEVEL_CHANGE)
            this.world.setRainLevel(value);
        else if (reason == ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE)
            this.world.setThunderLevel(value);
    }

    public void onChunkBiomeData(ClientboundChunksBiomesPacket packet) {
        for (ClientboundChunksBiomesPacket.ChunkBiomeData serialized : packet.chunkBiomeData()) {
            this.world.getChunkSource().replaceBiomes(serialized.pos().x, serialized.pos().z, serialized.getReadBuffer());
        }
        for (ClientboundChunksBiomesPacket.ChunkBiomeData serialized : packet.chunkBiomeData()) {
            this.world.onChunkLoaded(new ChunkPos(serialized.pos().x, serialized.pos().z));
        }
        for (ClientboundChunksBiomesPacket.ChunkBiomeData serialized : packet.chunkBiomeData()) {
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    for (int k = this.world.getMinSection(); k < this.world.getMaxSection(); ++k) {
                        this.renderer.setSectionDirty(serialized.pos().x + i, k, serialized.pos().z + j);
                    }
                }
            }
        }
    }

    private void scheduleRenderChunk(LevelChunk chunk, int x, int z) {
        LevelLightEngine lightingProvider = this.world.getChunkSource().getLightEngine();
        LevelChunkSection[] chunkSections = chunk.getSections();
        ChunkPos chunkPos = chunk.getPos();
        WorldGeometryRenderer renderer = this.geometry;

        for (int i = 0; i < chunkSections.length; ++i) {
            LevelChunkSection chunkSection = chunkSections[i];
            int j = this.world.getSectionYFromSectionIndex(i);
            lightingProvider.updateSectionStatus(SectionPos.of(chunkPos, j), chunkSection.hasOnlyAir());

            if (renderer != null)
                renderer.markSectionDirty(SectionPos.of(chunkPos, j));
        }
    }

    public void onUnloadChunk(ClientboundForgetLevelChunkPacket packet) {
        this.world.getChunkSource().drop(packet.pos());

        WorldGeometryRenderer renderer = this.geometry;
        if (renderer == null)
            return;

        for (int y = this.world.getMinSection(); y < this.world.getMaxSection(); y++)
            renderer.dropSection(SectionPos.of(packet.pos().x, y, packet.pos().z));
    }

    public void onEntitySpawn(ClientboundAddEntityPacket packet) {
        EntityType<?> type = packet.getType();
        Entity entity = type.create(this.world);

        if (entity == null)
            return;

        entity.recreateFromPacket(packet);
        this.world.addEntity(entity);
    }

    public void onPlayerSpawn(ClientboundAddEntityPacket packet) {
        ClientPacketListener handler = Minecraft.getInstance().getConnection();
        if (handler == null)
            return;

        PlayerInfo entry = handler.getPlayerInfo(packet.getUUID());
        if (entry == null)
            return;

        RemotePlayer player = new RemotePlayer(this.world, entry.getProfile());
        int id = packet.getId();
        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        float yaw = packet.getYRot();
        float pitch = packet.getXRot();

        player.setId(id);
        player.syncPacketPositionCodec(x, y, z);
        player.absMoveTo(x, y, z, yaw, pitch);
        player.setYHeadRot(yaw);
        player.setYBodyRot(yaw);
        this.world.addEntity(player);
    }

    public void onEntityPosition(ClientboundTeleportEntityPacket packet) {
        Entity entity = this.world.getEntity(packet.getId());
        if (entity == null)
            return;

        Vec3 pos = new Vec3(packet.getX(), packet.getY(), packet.getZ());
        entity.getPositionCodec().setBase(pos);
        entity.lerpTo(pos.x, pos.y, pos.z,
                packet.getyRot() * 360 / 256.0F, packet.getxRot() * 360 / 256.0F, 3);
        entity.setOnGround(packet.isOnGround());
    }

    public void onEntityMove(ClientboundMoveEntityPacket packet) {
        Entity entity = packet.getEntity(this.world);
        if (entity == null)
            return;

        if (packet.hasPosition()) {
            VecDeltaCodec tracked = entity.getPositionCodec();
            Vec3 pos = tracked.decode(packet.getXa(), packet.getYa(), packet.getZa());
            tracked.setBase(pos);

            float yaw = packet.hasRotation() ? packet.getyRot() * 360 / 256.0F : entity.getYRot();
            float pitch = packet.hasRotation() ? packet.getxRot() * 360 / 256.0F : entity.getXRot();
            entity.lerpTo(pos.x, pos.y, pos.z, yaw, pitch, 3);
        } else if (packet.hasRotation()) {
            entity.lerpTo(entity.getX(), entity.getY(), entity.getZ(),
                    packet.getyRot() * 360 / 256.0F, packet.getxRot() * 360 / 256.0F, 3);
        }

        entity.setOnGround(packet.isOnGround());
    }

    public void onEntityVelocity(ClientboundSetEntityMotionPacket packet) {
        Entity entity = this.world.getEntity(packet.getId());
        if (entity == null)
            return;

        entity.lerpMotion(packet.getXa(), packet.getYa(), packet.getZa());
    }

    public void onEntitySetHeadYaw(ClientboundRotateHeadPacket packet) {
        Entity entity = packet.getEntity(this.world);
        if (entity == null)
            return;

        entity.lerpHeadTo(packet.getYHeadRot() * 360 / 256.0F, 3);
    }

    public void onEntityAnimation(ClientboundAnimatePacket packet) {
        if (!(this.world.getEntity(packet.getId()) instanceof LivingEntity living))
            return;

        if (packet.getAction() == ClientboundAnimatePacket.SWING_MAIN_HAND)
            living.swing(InteractionHand.MAIN_HAND);
        else if (packet.getAction() == ClientboundAnimatePacket.SWING_OFF_HAND)
            living.swing(InteractionHand.OFF_HAND);
        else if (packet.getAction() == ClientboundAnimatePacket.WAKE_UP && living instanceof Player player)
            player.stopSleepInBed(false, false);
    }

    public void onEntityTrackerUpdate(ClientboundSetEntityDataPacket packet) {
        Entity entity = this.world.getEntity(packet.id());

        if (entity != null && packet.packedItems() != null)
            entity.getEntityData().assignValues(packet.packedItems());
    }

    public void onEntityEquipment(ClientboundSetEquipmentPacket packet) {
        if (this.world.getEntity(packet.getEntity()) instanceof LivingEntity living) {
            for (Pair<EquipmentSlot, ItemStack> pair : packet.getSlots())
                living.setItemSlot(pair.getFirst(), pair.getSecond());
        }
    }

    public void onEntitiesDestroy(ClientboundRemoveEntitiesPacket packet) {
        for (int i = 0; i < packet.getEntityIds().size(); i++) {
            int id = packet.getEntityIds().getInt(i);
            Entity entity = this.world.getEntity(id);

            if (entity != null)
                this.world.removeEntity(id, Entity.RemovalReason.DISCARDED);
        }
    }

    public void spawnDisplayParticles(int centerX, int centerY, int centerZ, int radius) {
        RandomSource random = this.world.getRandom();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int i = 0; i < 667; i++) {
            int x = centerX + random.nextInt(radius) - random.nextInt(radius);
            int y = centerY + random.nextInt(radius) - random.nextInt(radius);
            int z = centerZ + random.nextInt(radius) - random.nextInt(radius);
            pos.set(x, y, z);

            try {
                BlockState state = this.world.getBlockState(pos);
                if (!state.isAir())
                    state.getBlock().animateTick(state, this.world, pos, random);

                FluidState fluid = state.getFluidState();
                if (!fluid.isEmpty())
                    fluid.animateTick(this.world, pos, random);
            } catch (Exception ignored) {
            }
        }
    }

    public void tickEntities() {
        List<Entity> snapshot = new ArrayList<>();
        this.world.entitiesForRendering().forEach(snapshot::add);

        for (Entity entity : snapshot) {
            if (entity == null || entity.isRemoved())
                continue;

            if (entity.isPassenger())
                continue;

            try {
                this.world.tickNonPassenger(entity);
            } catch (Throwable t) {
                AITMod.LOGGER.error("BOTI: failed to tick shadow entity {}", entity, t);
            }
        }
    }

    public void close() {
        try {
            this.geometry.close();
            this.renderer.setLevel(null);
            this.renderer.close();
        } catch (Exception e) {
            AITMod.LOGGER.error("Failed to close shadow world for portal {}", this.id, e);
        }
    }

    public static PortalData fromCurrent(UUID id) {
        ClientLevel old = Minecraft.getInstance().level;
        ResourceKey<DimensionType> type = old.dimensionTypeRegistration().unwrapKey().orElse(BuiltinDimensionTypes.OVERWORLD);

        return create(id, old.dimension(), type);
    }

    public static PortalData create(UUID id, ResourceKey<Level> dimension, ResourceKey<DimensionType> dimensionType) {
        Minecraft client = Minecraft.getInstance();
        ClientLevel old = client.level;

        Holder<DimensionType> typeEntry = old.registryAccess()
                .registryOrThrow(Registries.DIMENSION_TYPE).getHolderOrThrow(dimensionType);

        LevelRenderer worldRenderer = new LevelRenderer(
                client,
                client.getEntityRenderDispatcher(),
                client.getBlockEntityRenderDispatcher(),
                client.renderBuffers()
        );

        ClientLevel world = new ClientWorldAnalog(client.getConnection(), new ClientLevel.ClientLevelData(Difficulty.NORMAL,
                false, false), dimension,
                typeEntry,
                12, old.getServerSimulationDistance(), client::getProfiler, worldRenderer,
                old.isDebug(), old.getBiomeManager().biomeZoomSeed);

        worldRenderer.setLevel(world);

        WorldGeometryRenderer geometry = new WorldGeometryRenderer(renderDistanceBlocks());

        return new PortalData(id, worldRenderer, world, geometry);
    }
}
