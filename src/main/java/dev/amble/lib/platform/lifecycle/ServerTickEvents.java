package dev.amble.lib.platform.lifecycle;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public class ServerTickEvents {

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
        NeoForge.EVENT_BUS.addListener(ServerTickEvent.Pre.class,
                event -> START_SERVER_TICK.invoker().onTick(event.getServer()));
        NeoForge.EVENT_BUS.addListener(ServerTickEvent.Post.class,
                event -> END_SERVER_TICK.invoker().onTick(event.getServer()));

        NeoForge.EVENT_BUS.addListener(LevelTickEvent.Pre.class, event -> {
            if (event.getLevel() instanceof ServerLevel world)
                START_WORLD_TICK.invoker().onTick(world);
        });
        NeoForge.EVENT_BUS.addListener(LevelTickEvent.Post.class, event -> {
            if (event.getLevel() instanceof ServerLevel world)
                END_WORLD_TICK.invoker().onTick(world);
        });
    }
}
