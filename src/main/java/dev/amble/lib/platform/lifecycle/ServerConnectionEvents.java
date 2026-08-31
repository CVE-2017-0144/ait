package dev.amble.lib.platform.lifecycle;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public class ServerConnectionEvents {

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
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedInEvent.class, event -> {
            if (event.getEntity() instanceof ServerPlayer player)
                JOIN.invoker().onPlayReady(player, player.getServer());
        });
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedOutEvent.class, event -> {
            if (event.getEntity() instanceof ServerPlayer player)
                DISCONNECT.invoker().onPlayDisconnect(player, player.getServer());
        });
    }
}
