package dev.drtheo.multidim.event;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;
import net.minecraft.CrashReport;
import net.minecraft.server.MinecraftServer;

public class ServerCrashEvent {

    public static final Event<Crash> EVENT = EventFactory.createArrayBacked(Crash.class,
            callbacks -> (server, report) -> {
                for (Crash callback : callbacks) {
                    callback.onServerCrash(server, report);
                }
            });

    @FunctionalInterface
    public interface Crash {
        void onServerCrash(MinecraftServer server, CrashReport report);
    }
}
