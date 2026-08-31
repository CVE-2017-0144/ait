package dev.amble.lib.platform.lifecycle;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public class ServerLifecycleEvents {

    public interface Starting {
        void onServerStarting(MinecraftServer server);
    }

    public interface Started {
        void onServerStarted(MinecraftServer server);
    }

    public interface Stopping {
        void onServerStopping(MinecraftServer server);
    }

    public interface Stopped {
        void onServerStopped(MinecraftServer server);
    }

    public interface SyncDataPacks {
        void onSyncDataPackContents(ServerPlayer player, boolean joined);
    }

    public static final Event<Starting> SERVER_STARTING = EventFactory.createArrayBacked(Starting.class,
            callbacks -> server -> {
                for (Starting callback : callbacks) {
                    callback.onServerStarting(server);
                }
            });

    public static final Event<Started> SERVER_STARTED = EventFactory.createArrayBacked(Started.class,
            callbacks -> server -> {
                for (Started callback : callbacks) {
                    callback.onServerStarted(server);
                }
            });

    public static final Event<Stopping> SERVER_STOPPING = EventFactory.createArrayBacked(Stopping.class,
            callbacks -> server -> {
                for (Stopping callback : callbacks) {
                    callback.onServerStopping(server);
                }
            });

    public static final Event<Stopped> SERVER_STOPPED = EventFactory.createArrayBacked(Stopped.class,
            callbacks -> server -> {
                for (Stopped callback : callbacks) {
                    callback.onServerStopped(server);
                }
            });

    public static final Event<SyncDataPacks> SYNC_DATA_PACK_CONTENTS = EventFactory
            .createArrayBacked(SyncDataPacks.class, callbacks -> (player, joined) -> {
                for (SyncDataPacks callback : callbacks) {
                    callback.onSyncDataPackContents(player, joined);
                }
            });

    static {
        NeoForge.EVENT_BUS.addListener(ServerStartingEvent.class,
                event -> SERVER_STARTING.invoker().onServerStarting(event.getServer()));
        NeoForge.EVENT_BUS.addListener(ServerStartedEvent.class,
                event -> SERVER_STARTED.invoker().onServerStarted(event.getServer()));
        NeoForge.EVENT_BUS.addListener(ServerStoppingEvent.class,
                event -> SERVER_STOPPING.invoker().onServerStopping(event.getServer()));
        NeoForge.EVENT_BUS.addListener(ServerStoppedEvent.class,
                event -> SERVER_STOPPED.invoker().onServerStopped(event.getServer()));

        NeoForge.EVENT_BUS.addListener(OnDatapackSyncEvent.class, event -> {
            ServerPlayer player = event.getPlayer();

            if (player != null) {
                SYNC_DATA_PACK_CONTENTS.invoker().onSyncDataPackContents(player, true);
                return;
            }

            event.getPlayerList().getPlayers()
                    .forEach(each -> SYNC_DATA_PACK_CONTENTS.invoker().onSyncDataPackContents(each, false));
        });
    }
}
