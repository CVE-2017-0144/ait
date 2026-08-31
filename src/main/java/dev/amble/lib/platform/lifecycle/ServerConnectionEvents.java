package dev.amble.lib.platform.lifecycle;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public final class ServerConnectionEvents {

    private ServerConnectionEvents() {}

    public interface Join {
        void onPlayReady(ServerPlayer player, MinecraftServer server);
    }

    public interface Disconnect {
        void onPlayDisconnect(ServerPlayer player, MinecraftServer server);
    }

    public static final Event<Join> JOIN = EventFactory.createArrayBacked(Join.class,
            callbacks -> (player, server) -> {
                for (Join callback : callbacks) {
                    callback.onPlayReady(player, server);
                }
            });

    public static final Event<Disconnect> DISCONNECT = EventFactory.createArrayBacked(Disconnect.class,
            callbacks -> (player, server) -> {
                for (Disconnect callback : callbacks) {
                    callback.onPlayDisconnect(player, server);
                }
            });

    static {
        bootstrap();
    }

    private static void bootstrap() {
        net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents.JOIN
                .register((handler, sender, server) -> JOIN.invoker().onPlayReady(handler.getPlayer(), server));
        net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents.DISCONNECT
                .register((handler, server) -> DISCONNECT.invoker().onPlayDisconnect(handler.getPlayer(), server));
    }
}
