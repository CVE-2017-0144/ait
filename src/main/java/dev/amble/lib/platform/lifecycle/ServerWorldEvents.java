package dev.amble.lib.platform.lifecycle;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

// multidim fires these for runtime worlds
public class ServerWorldEvents {

    public interface Load {
        void onWorldLoad(MinecraftServer server, ServerLevel world);
    }

    public interface Unload {
        void onWorldUnload(MinecraftServer server, ServerLevel world);
    }

    public static final Event<Load> LOAD = EventFactory.createArrayBacked(Load.class,
            cbs -> (server, world) -> {
                for (Load cb : cbs) {
                    cb.onWorldLoad(server, world);
                }
            });

    public static final Event<Unload> UNLOAD = EventFactory.createArrayBacked(Unload.class,
            cbs -> (server, world) -> {
                for (Unload cb : cbs) {
                    cb.onWorldUnload(server, world);
                }
            });

    static {
        NeoForge.EVENT_BUS.addListener(LevelEvent.Load.class, event -> {
            if (event.getLevel() instanceof ServerLevel world)
                LOAD.invoker().onWorldLoad(world.getServer(), world);
        });
        NeoForge.EVENT_BUS.addListener(LevelEvent.Unload.class, event -> {
            if (event.getLevel() instanceof ServerLevel world)
                UNLOAD.invoker().onWorldUnload(world.getServer(), world);
        });
    }
}
