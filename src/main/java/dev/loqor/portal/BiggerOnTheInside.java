package dev.loqor.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import dev.amble.ait.AITMod;
import dev.amble.ait.compat.DependencyChecker;
import dev.drtheo.portal.PacketProxyPlayer;
import dev.drtheo.portal.PortalInitS2CPacket;
import dev.drtheo.portal.WrappedPacketS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.platform.ModEntrypoint;
import dev.amble.lib.platform.lifecycle.ServerLifecycleEvents;
import dev.amble.lib.platform.lifecycle.ServerTickEvents;

public class BiggerOnTheInside implements ModEntrypoint {

    private static final TicketType<UUID> PORTAL_TICKET =
            TicketType.create("portal_proxy", UUID::compareTo);

    private static final int TICKING_RADIUS = 2;

    private static final int MAX_PAYLOAD_SIZE = 1 << 20;

    private static final Map<UUID, ProxyEntry> PROXIES = new HashMap<>();

    private static final Map<UUID, ProxyEntry> INTERIOR_PROXIES = new HashMap<>();

    private static final long REFRESH_INTERVAL = 5L;

    private static final long INTERIOR_GRACE_MS = 30_000L;

    private final Set<UUID> activeThisTick = new HashSet<>();
    private final List<UUID> staleIds      = new ArrayList<>();

    private long tickCounter = 0;

    @Override
    public void onInitialize() {
        // ip owns chunk/entity tracking for every server player, proxies would get nothing
        if (DependencyChecker.hasPortals())
            return;

        ServerTickEvents.END_SERVER_TICK.register(this::onServerTick);
        ServerTickEvents.END_SERVER_TICK.register(server -> sendChunks());
        ServerLifecycleEvents.SERVER_STOPPING.register(this::clearAll);
    }

    private void onServerTick(MinecraftServer server) {
        if (this.tickCounter++ % REFRESH_INTERVAL != 0)
            return;

        ServerTardisManager manager = ServerTardisManager.getInstance();
        if (manager == null)
            return;

        activeThisTick.clear();
        manager.forEach(tardis -> {
            if (ensureProxy(server, tardis))
                activeThisTick.add(tardis.getUuid());
        });

        staleIds.clear();
        for (UUID id : PROXIES.keySet()) {
            if (!activeThisTick.contains(id))
                staleIds.add(id);
        }
        for (UUID id : staleIds)
            removeProxy(id);

        activeThisTick.clear();
        manager.forEach(tardis -> {
            if (ensureInteriorProxy(server, tardis))
                activeThisTick.add(tardis.getUuid());
        });

        staleIds.clear();
        for (UUID id : INTERIOR_PROXIES.keySet()) {
            if (!activeThisTick.contains(id))
                staleIds.add(id);
        }
        for (UUID id : staleIds)
            removeInteriorProxy(id);
    }

    private static void sendChunks() {
        for (ProxyEntry entry : PROXIES.values())
            entry.proxy.sendChunks();

        for (ProxyEntry entry : INTERIOR_PROXIES.values())
            entry.proxy.sendChunks();
    }

    private static boolean ensureProxy(MinecraftServer server, ServerTardis tardis) {
        UUID id = tardis.getUuid();

        Set<UUID> viewers = viewerIds(tardis);
        if (!tardis.travel().isLanded() || viewers.isEmpty())
            return false;

        CachedDirectedGlobalPos ext = tardis.travel().position();
        if (ext == null)
            return false;

        ServerLevel extWorld = ext.getWorld();
        if (extWorld == null) {
            ext.init(server);
            extWorld = ext.getWorld();
        }
        if (extWorld == null)
            return false;

        BlockPos extPos  = ext.getPos();
        ProxyEntry entry = PROXIES.get(id);

        if (entry == null) {
            PROXIES.put(id, createProxy(tardis, extWorld, extPos, viewers));
            return true;
        }

        boolean newViewer = !entry.viewers.containsAll(viewers);
        entry.viewers = viewers;

        if (!entry.world.dimension().equals(extWorld.dimension()) || newViewer) {
            despawn(entry);
            PROXIES.put(id, createProxy(tardis, extWorld, extPos, viewers));
            return true;
        }

        broadcastTime(tardis, extWorld);
        maybeBroadcastWeather(tardis, extWorld, entry);

        boolean movedChunk = entry.pos.getX() >> 4 != extPos.getX() >> 4
                || entry.pos.getZ() >> 4 != extPos.getZ() >> 4
                || entry.pos.getY() != extPos.getY();

        if (movedChunk) {
            removeChunkTickets(entry.world, entry.pos, id);

            entry.proxy.setPos(extPos.getX(), extPos.getY(), extPos.getZ());

            if (entry.world.hasChunk(extPos.getX() >> 4, extPos.getZ() >> 4)) {
                entry.proxy.onChunkEntered();
                entry.proxy.sendChunks();
            }

            entry.posRef[0] = extPos;
            entry.pos = extPos;

            addChunkTickets(extWorld, extPos, id);
            broadcastCenter(tardis, entry.proxy);
        }

        return true;
    }

    private static ProxyEntry createProxy(ServerTardis tardis, ServerLevel world,
                                          BlockPos pos, Set<UUID> viewers) {
        UUID id = tardis.getUuid();

        broadcastInit(tardis, world);
        addChunkTickets(world, pos, id);

        BlockPos[] posRef = { pos };

        PacketProxyPlayer proxy = new PacketProxyPlayer(world);
        proxy.setPos(pos.getX(), pos.getY(), pos.getZ());
        proxy.setPacketListener(packet -> forwardIfInRange(tardis, posRef[0], packet));

        world.addFreshEntity(proxy);
        proxy.sendChunks();
        // proxy.onChunkEntered();

        float rain    = world.getRainLevel(1.0f);
        float thunder = world.getThunderLevel(1.0f);

        broadcastTime(tardis, world);
        broadcastWeather(tardis, rain, thunder);
        broadcastCenter(tardis, proxy);

        return new ProxyEntry(id, proxy, world, posRef, pos, viewers, rain, thunder);
    }

    private static void removeProxy(UUID id) {
        ProxyEntry entry = PROXIES.remove(id);
        if (entry != null)
            despawn(entry);
    }

    private static void despawn(ProxyEntry entry) {
        removeChunkTickets(entry.world, entry.pos, entry.tardisId);
        entry.world.removePlayerImmediately(entry.proxy, Entity.RemovalReason.DISCARDED);
    }

    private static boolean keepDuringGrace(ProxyEntry entry) {
        return entry != null && System.currentTimeMillis() <= entry.graceDeadline;
    }

    private static boolean ensureInteriorProxy(MinecraftServer server, ServerTardis tardis) {
        UUID key = tardis.getUuid();
        ProxyEntry entry = INTERIOR_PROXIES.get(key);

        if (!tardis.travel().isLanded() || !tardis.door().isOpen())
            return keepDuringGrace(entry);

        List<ServerPlayer> viewers = exteriorViewers(tardis);
        if (viewers.isEmpty())
            return keepDuringGrace(entry);

        ServerLevel interior = tardis.world();
        if (interior == null)
            return keepDuringGrace(entry);

        BlockPos doorPos     = tardis.getDesktop().getDoorPos().getPos();
        UUID portalId        = Portals.interiorId(key);
        Set<UUID> viewerIds  = idsOf(viewers);

        if (entry == null) {
            INTERIOR_PROXIES.put(key, createInteriorProxy(tardis, interior, doorPos, viewerIds));
            return true;
        }

        entry.graceDeadline = System.currentTimeMillis() + INTERIOR_GRACE_MS;

        boolean newViewer = !entry.viewers.containsAll(viewerIds);
        boolean dimChanged = !entry.world.dimension().equals(interior.dimension());

        boolean dirtyWhileUnviewed = entry.worldDirtyRef != null && entry.worldDirtyRef[0];

        if (dimChanged || newViewer || dirtyWhileUnviewed) {
            entry.viewers = viewerIds;
            despawn(entry);
            ProxyEntry rebuilt = createInteriorProxy(tardis, interior, doorPos, viewerIds);
            INTERIOR_PROXIES.put(key, rebuilt);
            return true;
        }
        entry.viewers = viewerIds;
        replayMissed(portalId, viewers, entry.missedEntities);

        broadcastTime(portalId, viewers, interior);
        maybeBroadcastWeather(portalId, viewers, interior, entry);

        boolean movedChunk = entry.pos.getX() >> 4 != doorPos.getX() >> 4
                || entry.pos.getZ() >> 4 != doorPos.getZ() >> 4
                || entry.pos.getY() != doorPos.getY();

        if (movedChunk) {
            removeChunkTickets(entry.world, entry.pos, portalId);

            entry.proxy.setPos(doorPos.getX(), doorPos.getY(), doorPos.getZ());
            entry.proxy.onChunkEntered();
            entry.proxy.sendChunks();
            entry.posRef[0] = doorPos;
            entry.pos = doorPos;

            addChunkTickets(interior, doorPos, portalId);
            broadcastCenter(portalId, viewers, entry.proxy);
        }

        return true;
    }

    private static ProxyEntry createInteriorProxy(ServerTardis tardis, ServerLevel interior,
                                                  BlockPos doorPos, Set<UUID> viewerIds) {
        UUID portalId = Portals.interiorId(tardis.getUuid());
        List<ServerPlayer> viewers = exteriorViewers(tardis);

        broadcastInit(portalId, viewers, interior);
        addChunkTickets(interior, doorPos, portalId);

        BlockPos[] posRef = { doorPos };

        boolean[] dirtyRef = { false };
        List<Packet<?>> missedEntities = new ArrayList<>();

        PacketProxyPlayer proxy = new PacketProxyPlayer(interior);
        proxy.setPos(doorPos.getX(), doorPos.getY(), doorPos.getZ());
        proxy.setPacketListener(packet -> forwardInteriorIfInRange(portalId, tardis, posRef[0], dirtyRef, missedEntities, packet));

        interior.addFreshEntity(proxy);
        proxy.sendChunks();
        // proxy.onChunkEntered();

        float rain    = interior.getRainLevel(1.0f);
        float thunder = interior.getThunderLevel(1.0f);

        broadcastTime(portalId, viewers, interior);
        broadcastWeather(portalId, viewers, rain, thunder);
        broadcastCenter(portalId, viewers, proxy);

        ProxyEntry entry = new ProxyEntry(portalId, proxy, interior, posRef, doorPos, viewerIds, rain, thunder);
        entry.graceDeadline = System.currentTimeMillis() + INTERIOR_GRACE_MS;
        entry.worldDirtyRef = dirtyRef;
        entry.missedEntities = missedEntities;
        return entry;
    }

    private static void removeInteriorProxy(UUID key) {
        ProxyEntry entry = INTERIOR_PROXIES.remove(key);
        if (entry != null)
            despawn(entry);
    }

    private static List<ServerPlayer> exteriorViewers(ServerTardis tardis) {
        CachedDirectedGlobalPos ext = tardis.travel().position();
        if (ext == null)
            return List.of();

        ServerLevel extWorld = ext.getWorld();
        if (extWorld == null)
            return List.of();

        BlockPos extPos = ext.getPos();
        double range = (AITMod.CONFIG.botiRenderDistance + 1) * 16.0;
        double rangeSq = range * range;

        List<ServerPlayer> result = new ArrayList<>();
        for (ServerPlayer player : extWorld.players()) {
            if (player instanceof PacketProxyPlayer)
                continue;
            if (player.blockPosition().distSqr(extPos) <= rangeSq)
                result.add(player);
        }
        return result;
    }

    private static Set<UUID> idsOf(List<ServerPlayer> players) {
        Set<UUID> ids = new HashSet<>();
        for (ServerPlayer player : players)
            ids.add(player.getUUID());
        return ids;
    }

    private static void addChunkTickets(ServerLevel world, BlockPos center, UUID tardisId) {
        ChunkPos origin = new ChunkPos(center);
        for (int dx = -AITMod.CONFIG.botiRenderDistance; dx <= AITMod.CONFIG.botiRenderDistance; dx++) {
            for (int dz = -AITMod.CONFIG.botiRenderDistance; dz <= AITMod.CONFIG.botiRenderDistance; dz++) {
                world.getChunkSource().addRegionTicket(
                        PORTAL_TICKET,
                        new ChunkPos(origin.x + dx, origin.z + dz),
                        TICKING_RADIUS,
                        tardisId);
            }
        }
    }

    private static void removeChunkTickets(ServerLevel world, BlockPos center, UUID tardisId) {
        ChunkPos origin = new ChunkPos(center);
        for (int dx = -AITMod.CONFIG.botiRenderDistance; dx <= AITMod.CONFIG.botiRenderDistance; dx++) {
            for (int dz = -AITMod.CONFIG.botiRenderDistance; dz <= AITMod.CONFIG.botiRenderDistance; dz++) {
                world.getChunkSource().removeRegionTicket(
                        PORTAL_TICKET,
                        new ChunkPos(origin.x + dx, origin.z + dz),
                        TICKING_RADIUS,
                        tardisId);
            }
        }
    }

    private static void forwardIfInRange(ServerTardis tardis, BlockPos extPos, Packet<?> packet) {
        forwardIfInRange(tardis.getUuid(), () -> interiorViewers(tardis), extPos, packet);
    }

    private static void forwardIfInRange(UUID portalId, Supplier<List<ServerPlayer>> viewers,
                                         BlockPos center, Packet<?> packet) {
        if (isChunkPacketOutOfRange(packet, center))
            return;
        if (shouldForward(packet))
            broadcast(portalId, viewers.get(), packet);
    }

    private static void forwardInteriorIfInRange(UUID portalId, ServerTardis tardis, BlockPos center,
                                                 boolean[] dirtyRef, List<Packet<?>> missedEntities, Packet<?> packet) {
        if (isChunkPacketOutOfRange(packet, center))
            return;
        if (!shouldForward(packet))
            return;

        List<ServerPlayer> viewers = exteriorViewers(tardis);
        if (viewers.isEmpty()) {
            if (isWorldChange(packet))
                dirtyRef[0] = true;
            else if (isEntityChange(packet))
                missedEntities.add(packet);
            return;
        }

        replayMissed(portalId, viewers, missedEntities);
        broadcast(portalId, viewers, packet);
    }

    // spawns and removals nobody was watching, or a quick re-look keeps ghosts
    private static void replayMissed(UUID portalId, List<ServerPlayer> viewers, List<Packet<?>> missed) {
        for (Packet<?> packet : missed)
            broadcast(portalId, viewers, packet);

        missed.clear();
    }

    private static boolean isWorldChange(Packet<?> packet) {
        return packet instanceof ClientboundBlockUpdatePacket
                || packet instanceof ClientboundSectionBlocksUpdatePacket
                || packet instanceof ClientboundLevelChunkWithLightPacket
                || packet instanceof ClientboundForgetLevelChunkPacket;
    }

    private static boolean isEntityChange(Packet<?> packet) {
        return packet instanceof ClientboundBundlePacket
                || packet instanceof ClientboundAddEntityPacket
                || packet instanceof ClientboundRemoveEntitiesPacket;
    }

    private static boolean isChunkPacketOutOfRange(Packet<?> packet, BlockPos extPos) {
        int originX = extPos.getX() >> 4;
        int originZ = extPos.getZ() >> 4;

        if (packet instanceof ClientboundLevelChunkWithLightPacket p)
            return outOfRange(p.getX(), p.getZ(), originX, originZ);

        if (packet instanceof ClientboundSectionBlocksUpdatePacket p) {
            SectionPos sec = p.sectionPos;
            return outOfRange(sec.x(), sec.z(), originX, originZ);
        }

        if (packet instanceof ClientboundBlockUpdatePacket p) {
            BlockPos bp = p.getPos();
            return outOfRange(bp.getX() >> 4, bp.getZ() >> 4, originX, originZ);
        }

        if (packet instanceof ClientboundForgetLevelChunkPacket p)
            return outOfRange(p.pos().x, p.pos().z, originX, originZ);

        return false;
    }

    private static boolean outOfRange(int chunkX, int chunkZ, int originX, int originZ) {
        return Math.abs(chunkX - originX) > AITMod.CONFIG.botiRenderDistance
                || Math.abs(chunkZ - originZ) > AITMod.CONFIG.botiRenderDistance;
    }

    private static boolean shouldForward(Packet<?> packet) {
        return packet instanceof ClientboundBundlePacket
                || packet instanceof ClientboundLevelChunkWithLightPacket
                || packet instanceof ClientboundSectionBlocksUpdatePacket
                || packet instanceof ClientboundBlockUpdatePacket
                || packet instanceof ClientboundForgetLevelChunkPacket
                || packet instanceof ClientboundAddEntityPacket
                || packet instanceof ClientboundTeleportEntityPacket
                || packet instanceof ClientboundMoveEntityPacket
                || packet instanceof ClientboundSetEntityMotionPacket
                || packet instanceof ClientboundRotateHeadPacket
                || packet instanceof ClientboundAnimatePacket
                || packet instanceof ClientboundSetEntityDataPacket
                || packet instanceof ClientboundSetEquipmentPacket
                || packet instanceof ClientboundRemoveEntitiesPacket
                || packet instanceof ClientboundLevelParticlesPacket;
    }

    private static void broadcastInit(ServerTardis tardis, ServerLevel world) {
        broadcastInit(tardis.getUuid(), interiorViewers(tardis), world);
    }

    private static void broadcastCenter(ServerTardis tardis, PacketProxyPlayer proxy) {
        broadcastCenter(tardis.getUuid(), interiorViewers(tardis), proxy);
    }

    private static void broadcastTime(ServerTardis tardis, ServerLevel world) {
        broadcastTime(tardis.getUuid(), interiorViewers(tardis), world);
    }

    private static void maybeBroadcastWeather(ServerTardis tardis, ServerLevel world, ProxyEntry entry) {
        maybeBroadcastWeather(tardis.getUuid(), interiorViewers(tardis), world, entry);
    }

    private static void broadcastWeather(ServerTardis tardis, float rain, float thunder) {
        broadcastWeather(tardis.getUuid(), interiorViewers(tardis), rain, thunder);
    }

    private static void broadcastInit(UUID portalId, List<ServerPlayer> targets, ServerLevel mirrored) {
        ResourceKey<DimensionType> type = mirrored.dimensionTypeRegistration().unwrapKey().orElse(BuiltinDimensionTypes.OVERWORLD);
        send(targets, PortalInitS2CPacket.TYPE, new PortalInitS2CPacket(portalId, mirrored.dimension(), type)::write);
    }

    private static void broadcastCenter(UUID portalId, List<ServerPlayer> targets, PacketProxyPlayer proxy) {
        ChunkPos center = proxy.chunkPosition();
        broadcast(portalId, targets, new ClientboundSetChunkCacheCenterPacket(center.x, center.z));
    }

    private static void broadcastTime(UUID portalId, List<ServerPlayer> targets, ServerLevel world) {
        broadcast(portalId, targets, new ClientboundSetTimePacket(
                world.getGameTime(),
                world.getDayTime(),
                world.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)));
    }

    private static void maybeBroadcastWeather(UUID portalId, List<ServerPlayer> targets, ServerLevel world, ProxyEntry entry) {
        float rain    = world.getRainLevel(1.0f);
        float thunder = world.getThunderLevel(1.0f);
        if (Math.abs(rain - entry.lastRain) < 0.01f && Math.abs(thunder - entry.lastThunder) < 0.01f)
            return;
        entry.lastRain    = rain;
        entry.lastThunder = thunder;
        broadcastWeather(portalId, targets, rain, thunder);
    }

    private static void broadcastWeather(UUID portalId, List<ServerPlayer> targets, float rain, float thunder) {
        broadcast(portalId, targets, new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE,    rain));
        broadcast(portalId, targets, new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, thunder));
    }

    private static void broadcast(UUID portalId, List<ServerPlayer> targets, Packet<?> packet) {
        send(targets, WrappedPacketS2CPacket.TYPE, new WrappedPacketS2CPacket(portalId, packet)::write);
    }

    private static void send(List<ServerPlayer> targets, ResourceLocation type, Consumer<RegistryFriendlyByteBuf> packet) {
        RegistryFriendlyByteBuf buf = null;

        for (ServerPlayer player : targets) {
            if (player instanceof PacketProxyPlayer)
                continue;

            if (buf == null) {
                buf = AitNetworking.buf();

                try {
                    packet.accept(buf);
                } catch (IllegalArgumentException e) {
                    return;
                }

                if (buf.writerIndex() > MAX_PAYLOAD_SIZE)
                    return;
            }

            AitNetworking.send(player, type, buf);
        }
    }

    private static List<ServerPlayer> interiorViewers(ServerTardis tardis) {
        return tardis.hasWorld() ? tardis.world().players() : List.of();
    }

    private static Set<UUID> viewerIds(ServerTardis tardis) {
        if (!tardis.hasWorld())
            return Set.of();
        Set<UUID> ids = new HashSet<>();
        for (ServerPlayer player : tardis.world().players()) {
            if (player instanceof PacketProxyPlayer)
                continue;
            ids.add(player.getUUID());
        }
        return ids;
    }

    private void clearAll(MinecraftServer server) {
        for (ProxyEntry entry : PROXIES.values())
            despawn(entry);
        PROXIES.clear();

        for (ProxyEntry entry : INTERIOR_PROXIES.values())
            despawn(entry);
        INTERIOR_PROXIES.clear();
    }

    private static final class ProxyEntry {

        final UUID tardisId;

        final PacketProxyPlayer proxy;

        final ServerLevel world;

        final BlockPos[] posRef;

        BlockPos pos;
        Set<UUID> viewers;

        float lastRain;
        float lastThunder;

        long graceDeadline;

        boolean[] worldDirtyRef;
        List<Packet<?>> missedEntities;

        ProxyEntry(UUID tardisId, PacketProxyPlayer proxy, ServerLevel world,
                   BlockPos[] posRef, BlockPos pos, Set<UUID> viewers,
                   float lastRain, float lastThunder) {
            this.tardisId  = tardisId;
            this.proxy     = proxy;
            this.world     = world;
            this.posRef    = posRef;
            this.pos       = pos;
            this.viewers   = viewers;
            this.lastRain  = lastRain;
            this.lastThunder = lastThunder;
        }
    }
}
