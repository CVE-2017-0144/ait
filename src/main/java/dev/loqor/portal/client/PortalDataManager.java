package dev.loqor.portal.client;

import dev.amble.ait.AITMod;
import dev.amble.ait.client.boti.BOTI;
import dev.amble.ait.client.boti.PortalParticleManager;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.lib.platform.clientlifecycle.ClientEvents;
import dev.drtheo.portal.PortalInitS2CPacket;
import dev.drtheo.portal.WrappedPacketS2CPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import java.util.*;

public class PortalDataManager {

    private static final Minecraft client = Minecraft.getInstance();

    // TODO: replace with array or intmap maybe
    private static final Map<UUID, PortalData> map = new HashMap<>();

    private static final Map<UUID, PortalParticleManager> particles = new HashMap<>();
    private static final RandomSource random = RandomSource.create();

    public static void init() {
        AitNetworking.registerClientReceiver(WrappedPacketS2CPacket.TYPE, (minecraftClient, handler, buf, packetSender) -> {
            handle(WrappedPacketS2CPacket.read(buf));
        });

        AitNetworking.registerClientReceiver(PortalInitS2CPacket.TYPE, (minecraftClient, handler, buf, packetSender) -> {
            PortalInitS2CPacket packet = PortalInitS2CPacket.read(buf);
            handleInit(packet.id(), packet.dimension(), packet.dimensionType());
        });

        ClientEvents.DISCONNECT.register(minecraftClient -> {
            if (minecraftClient.isSameThread())
                reset();
            else
                minecraftClient.execute(PortalDataManager::reset);
        });

        ClientEvents.END_CLIENT_TICK.register(minecraftClient -> {
            long idleReclaimNanos = (long) (dev.amble.ait.client.AITModClient.CONFIG != null
                    ? dev.amble.ait.client.AITModClient.CONFIG.botiIdleReclaimSeconds : 10) * 1_000_000_000L;

            for (PortalData data : new ArrayList<>(map.values())) {
                step(data, "chunk updates", d -> d.world().pollLightUpdates());

                step(data, "clock", d -> d.world().tickTime());

                PortalParticleManager simManager = particles.computeIfAbsent(data.id(),
                        uuid -> new PortalParticleManager(data.world(), client));
                ParticleEngine prevManager = client.particleEngine;
                client.particleEngine = simManager;
                try {
                    step(data, "block entities", d -> d.world().tickBlockEntities());
                    step(data, "entities", PortalData::tickEntities);
                } finally {
                    client.particleEngine = prevManager;
                }

                step(data, "particles", PortalDataManager::spawnDisplayParticles);

                step(data, "geometry reclaim", d -> d.geometry().reclaimIfIdle(idleReclaimNanos));
            }

            for (PortalParticleManager manager : new ArrayList<>(particles.values())) {
                try {
                    manager.tick();
                } catch (Exception e) {
                    AITMod.LOGGER.error("BOTI: failed to tick portal particles", e);
                }
            }
        });
    }

    private static void step(PortalData data, String name, java.util.function.Consumer<PortalData> action) {
        try {
            action.accept(data);
        } catch (Exception e) {
            AITMod.LOGGER.error("BOTI: portal '{}' step failed", name, e);
        }
    }

    private static void spawnDisplayParticles(PortalData data) {
        BlockPos center = data.geometry().centerPos();
        if (center == null || center.equals(BlockPos.ZERO))
            return;

        net.minecraft.world.phys.Vec3 eye = data.geometry().eyeWorldPos();
        int radius = Math.min(data.geometry().renderDistance(), 24);
        int cx, cy, cz;
        if (eye != null) {
            cx = (int) Math.floor(eye.x);
            cy = (int) Math.floor(eye.y);
            cz = (int) Math.floor(eye.z);
        } else {
            cx = center.getX();
            cy = center.getY();
            cz = center.getZ();
        }

        PortalParticleManager manager = particles.computeIfAbsent(data.id(),
                uuid -> new PortalParticleManager(data.world(), client));

        ParticleEngine previous = client.particleEngine;
        client.particleEngine = manager;
        try {
            data.spawnDisplayParticles(cx, cy, cz, radius);
        } finally {
            client.particleEngine = previous;
        }
    }

    public static void handleInit(UUID id, ResourceKey<Level> dimension, ResourceKey<DimensionType> dimensionType) {
        if (!client.isSameThread()) {
            client.executeBlocking(() -> handleInit(id, dimension, dimensionType));
            return;
        }

        free(id);
        map.put(id, PortalData.create(id, dimension, dimensionType));
    }

    public static void reset() {
        for (PortalData data : map.values())
            data.close();

        map.clear();
        particles.clear();
        BOTI.LAST_RENDERED_DOOR.clear();
        BOTI.LAST_RENDERED_EXTERIOR.clear();
    }

    public static void free(UUID id) {
        PortalData data = map.remove(id);
        if (data != null)
            data.close();

        particles.remove(id);
    }

    public static PortalParticleManager particles(UUID id) {
        return particles.get(id);
    }

    public static PortalData getOrCreate(UUID id) {
        return map.computeIfAbsent(id, uuid -> PortalData.fromCurrent(id));
    }

    public static PortalData get(UUID id) {
        return map.get(id);
    }

    private static void handle(WrappedPacketS2CPacket packet) {
        handle(packet.id(), packet.packet());
    }

    public static void handle(UUID id, Packet<?> packet) {
        if (!client.isSameThread()) {
            client.executeBlocking(() -> handle(id, packet));
            return;
        }

        try {
            PortalData data = handle0(id, packet);
            PortalEvents.UPDATE.invoker().onPortalUpdate(data);
        } catch (Exception var3) {
            AITMod.LOGGER.error("Failed to handle packet {}, suppressing error", packet, var3);
        }
    }

    private static PortalData handle0(UUID id, Packet<?> packet) {
        PortalData data = getOrCreate(id);

        if (packet instanceof ClientboundBundlePacket bundle) {
            for (Packet<?> otherPacket : bundle.subPackets()) {
                handle0(data, otherPacket);
            }

            return data;
        }

        handle0(data, packet);
        return data;
    }

    private static void handle0(PortalData data, Packet<?> packet) {
        if (packet instanceof ClientboundSetChunkCacheCenterPacket render) {
            data.onChunkRenderDistanceCenter(render);
        } else if (packet instanceof ClientboundSetTimePacket time) {
            data.onWorldTime(time);
        } else if (packet instanceof ClientboundLevelChunkWithLightPacket chunkData) {
            data.onChunkData(chunkData);
        } else if (packet instanceof ClientboundSectionBlocksUpdatePacket update) {
            data.onChunkDeltaUpdate(update);
        } else if (packet instanceof ClientboundBlockUpdatePacket update) {
            data.onBlockUpdate(update);
        } else if (packet instanceof ClientboundForgetLevelChunkPacket unload) {
            data.onUnloadChunk(unload);
        } else if (packet instanceof ClientboundAddEntityPacket spawn && spawn.getType() != EntityType.PLAYER) {
            data.onEntitySpawn(spawn);
        } else if (packet instanceof ClientboundAddEntityPacket spawn) {
            data.onPlayerSpawn(spawn);
        } else if (packet instanceof ClientboundGameEventPacket state) {
            data.onGameStateChange(state);
        } else if (packet instanceof ClientboundTeleportEntityPacket position) {
            data.onEntityPosition(position);
        } else if (packet instanceof ClientboundMoveEntityPacket move) {
            data.onEntityMove(move);
        } else if (packet instanceof ClientboundSetEntityMotionPacket velocity) {
            data.onEntityVelocity(velocity);
        } else if (packet instanceof ClientboundRotateHeadPacket headYaw) {
            data.onEntitySetHeadYaw(headYaw);
        } else if (packet instanceof ClientboundAnimatePacket animation) {
            data.onEntityAnimation(animation);
        } else if (packet instanceof ClientboundSetEntityDataPacket tracker) {
            data.onEntityTrackerUpdate(tracker);
        } else if (packet instanceof ClientboundSetEquipmentPacket equipment) {
            data.onEntityEquipment(equipment);
        } else if (packet instanceof ClientboundRemoveEntitiesPacket destroy) {
            data.onEntitiesDestroy(destroy);
        } else if (packet instanceof ClientboundLevelParticlesPacket particle) {
            onParticle(data, particle);
        } else if (packet instanceof ClientboundChunksBiomesPacket biome) {
//          this.onChunkBiomeData(biome); // - uncomment if it breaks everything
        }
    }

    private static void onParticle(PortalData data, ClientboundLevelParticlesPacket packet) {
        PortalParticleManager manager = particles.computeIfAbsent(data.id(),
                uuid -> new PortalParticleManager(data.world(), client));

        if (packet.getCount() == 0) {
            double vx = packet.getMaxSpeed() * packet.getXDist();
            double vy = packet.getMaxSpeed() * packet.getYDist();
            double vz = packet.getMaxSpeed() * packet.getZDist();
            manager.createParticle(packet.getParticle(),
                    packet.getX(), packet.getY(), packet.getZ(), vx, vy, vz);
        } else {
            for (int i = 0; i < packet.getCount(); i++) {
                double ox = random.nextGaussian() * (double) packet.getXDist();
                double oy = random.nextGaussian() * (double) packet.getYDist();
                double oz = random.nextGaussian() * (double) packet.getZDist();
                double vx = random.nextGaussian() * (double) packet.getMaxSpeed();
                double vy = random.nextGaussian() * (double) packet.getMaxSpeed();
                double vz = random.nextGaussian() * (double) packet.getMaxSpeed();
                manager.createParticle(packet.getParticle(),
                        packet.getX() + ox, packet.getY() + oy, packet.getZ() + oz, vx, vy, vz);
            }
        }
    }
}
