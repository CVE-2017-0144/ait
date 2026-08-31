package dev.amble.lib.platform.lifecycle;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public final class ServerTickEvents {

    private ServerTickEvents() {}

    public interface Tick {
        void onTick(MinecraftServer server);
    }

    public interface WorldTick {
        void onTick(ServerLevel world);
    }

    public static final Event<Tick> START_SERVER_TICK = EventFactory.createArrayBacked(Tick.class,
            callbacks -> server -> {
                for (Tick callback : callbacks) {
                    callback.onTick(server);
                }
            });

    public static final Event<Tick> END_SERVER_TICK = EventFactory.createArrayBacked(Tick.class,
            callbacks -> server -> {
                for (Tick callback : callbacks) {
                    callback.onTick(server);
                }
            });

    public static final Event<WorldTick> START_WORLD_TICK = EventFactory.createArrayBacked(WorldTick.class,
            callbacks -> world -> {
                for (WorldTick callback : callbacks) {
                    callback.onTick(world);
                }
            });

    public static final Event<WorldTick> END_WORLD_TICK = EventFactory.createArrayBacked(WorldTick.class,
            callbacks -> world -> {
                for (WorldTick callback : callbacks) {
                    callback.onTick(world);
                }
            });

    static {
        bootstrap();
    }

    private static void bootstrap() {
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.START_SERVER_TICK
                .register(server -> START_SERVER_TICK.invoker().onTick(server));
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_SERVER_TICK
                .register(server -> END_SERVER_TICK.invoker().onTick(server));
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.START_WORLD_TICK
                .register(world -> START_WORLD_TICK.invoker().onTick(world));
        net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents.END_WORLD_TICK
                .register(world -> END_WORLD_TICK.invoker().onTick(world));
    }
}
