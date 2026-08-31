package dev.amble.ait.core.events;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;

public class ServerChunkEvents {

    public static final Event<Tick> TICK = EventFactory.createArrayBacked(Tick.class,
            callbacks -> (world, chunk) -> {
                for (Tick callback : callbacks) {
                    callback.onChunkTick(world, chunk);
                }
            });

    @FunctionalInterface
    public interface Tick {
        void onChunkTick(ServerLevel world, LevelChunk chunk);
    }
}
