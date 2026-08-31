package dev.amble.lib.platform.lifecycle;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public final class ServerWorldEvents {

    private ServerWorldEvents() {}

    public interface Load {
        void onWorldLoad(MinecraftServer server, ServerLevel world);
    }

    public interface Unload {
        void onWorldUnload(MinecraftServer server, ServerLevel world);
    }

    public static final Event<Load> LOAD = EventFactory.createArrayBacked(Load.class,
            callbacks -> (server, world) -> {
                for (Load callback : callbacks) {
                    callback.onWorldLoad(server, world);
                }
            });

    public static final Event<Unload> UNLOAD = EventFactory.createArrayBacked(Unload.class,
            callbacks -> (server, world) -> {
                for (Unload callback : callbacks) {
                    callback.onWorldUnload(server, world);
                }
            });

    static {
        bootstrap();
    }

    private static void bootstrap() {
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents.LOAD
                .register((server, world) -> LOAD.invoker().onWorldLoad(server, world));
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents.UNLOAD
                .register((server, world) -> UNLOAD.invoker().onWorldUnload(server, world));
    }
}
