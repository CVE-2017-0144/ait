package dev.amble.lib.platform.lifecycle;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarted;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStarting;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStopped;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.ServerStopping;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SyncDataPackContents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public final class ServerLifecycleEvents {

    private ServerLifecycleEvents() {}

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
        bootstrap();
    }

    private static void bootstrap() {
        ServerStarting starting = server -> SERVER_STARTING.invoker().onServerStarting(server);
        ServerStarted started = server -> SERVER_STARTED.invoker().onServerStarted(server);
        ServerStopping stopping = server -> SERVER_STOPPING.invoker().onServerStopping(server);
        ServerStopped stopped = server -> SERVER_STOPPED.invoker().onServerStopped(server);
        SyncDataPackContents sync = (player, joined) -> SYNC_DATA_PACK_CONTENTS.invoker()
                .onSyncDataPackContents(player, joined);

        net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STARTING.register(starting);
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STARTED.register(started);
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STOPPING.register(stopping);
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SERVER_STOPPED.register(stopped);
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(sync);
    }
}
