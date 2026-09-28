package dev.amble.ait.core.portal;

import java.util.Comparator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;

import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.lib.platform.lifecycle.ServerLifecycleEvents;
import dev.amble.lib.platform.lifecycle.ServerTickEvents;
import dev.amble.lib.util.ServerLifecycleHooks;

public final class PortalPairs {

    private static final TicketType<ChunkPos> AIT_PORTAL = TicketType.create("ait_portal",
            Comparator.comparingLong(ChunkPos::toLong), 60);

    private static final int TICKET_RADIUS = 2;

    private static final Map<UUID, PortalPair> OPEN = new ConcurrentHashMap<>();

    private PortalPairs() {}

    public static void init() {
        TardisEvents.DOOR_OPEN.register(tardis -> onServerThread(tardis, PortalPairs::open));
        TardisEvents.DOOR_MOVE.register((tardis, newPos, oldPos) -> onServerThread(tardis, PortalPairs::reopen));
        TardisEvents.EXTERIOR_CHANGE.register(tardis -> onServerThread(tardis, PortalPairs::reopen));
        TardisEvents.REAL_DOOR_CLOSE.register(tardis -> onServerThread(tardis, t -> close(t, "door closed")));
        TardisEvents.ENTER_FLIGHT.register(tardis -> onServerThread(tardis, t -> close(t, "took off")));

        ServerTickEvents.END_SERVER_TICK.register(PortalPairs::tick);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> OPEN.clear());
    }

    public static @Nullable PortalPair get(UUID tardis) {
        return OPEN.get(tardis);
    }

    public static boolean isOpen(@Nullable Tardis tardis) {
        return tardis != null && OPEN.containsKey(tardis.getUuid());
    }

    public static void forEachOpen(Consumer<PortalPair> action) {
        OPEN.values().forEach(action);
    }

    public static @Nullable PortalPair openAt(ServerLevel level, BlockPos pos) {
        for (PortalPair pair : OPEN.values()) {
            if (pair.sideAt(level, pos) != null)
                return pair;
        }

        return null;
    }

    private static void open(ServerTardis tardis) {
        PortalPair pair = PortalPair.resolve(tardis);

        if (pair == null) {
            OPEN.remove(tardis.getUuid());
            AITMod.LOGGER.info("[portal] refused {}: degenerate endpoint (door {}, exterior {})", tardis.getUuid(),
                    tardis.getDesktop().getDoorPos(), tardis.travel().position());
            return;
        }

        OPEN.put(tardis.getUuid(), pair);

        hold(pair.interior());
        hold(pair.exterior());

        AITMod.LOGGER.info("[portal] open {}: interior {} <-> exterior {}, yaw {}", tardis.getUuid(), pair.interior(),
                pair.exterior(), pair.yawDelta(pair.interior()));
    }

    private static void reopen(ServerTardis tardis) {
        if (tardis.door().isClosed())
            return;

        open(tardis);
    }

    private static void close(ServerTardis tardis, String why) {
        PortalPair pair = OPEN.remove(tardis.getUuid());

        if (pair == null)
            return;

        AITMod.LOGGER.info("[portal] close {} ({}): interior {} <-> exterior {}", tardis.getUuid(), why,
                pair.interior(), pair.exterior());
    }

    private static void tick(MinecraftServer server) {
        if (OPEN.isEmpty())
            return;

        for (PortalPair pair : OPEN.values()) {
            ServerTardis tardis = pair.tardis();

            if (tardis.isRemoved() || tardis.door().isClosed() || tardis.travel().inFlight()) {
                close(tardis, "stale");
                continue;
            }

            hold(pair.interior());
            hold(pair.exterior());
        }
    }

    private static void hold(PortalPair.Side side) {
        side.level().getChunkSource().addRegionTicket(AIT_PORTAL, side.chunk(), TICKET_RADIUS, side.chunk());
    }

    private static void onServerThread(Tardis tardis, Consumer<ServerTardis> action) {
        if (!(tardis instanceof ServerTardis server))
            return;

        MinecraftServer minecraft = ServerLifecycleHooks.get();

        if (minecraft == null)
            return;

        if (minecraft.isSameThread()) {
            action.accept(server);
        } else {
            minecraft.execute(() -> action.accept(server));
        }
    }
}
