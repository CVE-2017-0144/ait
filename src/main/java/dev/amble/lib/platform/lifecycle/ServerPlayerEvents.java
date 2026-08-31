package dev.amble.lib.platform.lifecycle;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public class ServerPlayerEvents {

    public interface AllowChatMessage {
        boolean allowChatMessage(String message, ServerPlayer sender);
    }

    public interface AfterChangeWorld {
        void afterChangeWorld(ServerPlayer player, ServerLevel origin, ServerLevel destination);
    }

    public static final Event<AllowChatMessage> ALLOW_CHAT_MESSAGE = EventFactory
            .createArrayBacked(AllowChatMessage.class, callbacks -> (message, sender) -> {
                for (AllowChatMessage callback : callbacks) {
                    if (!callback.allowChatMessage(message, sender))
                        return false;
                }

                return true;
            });

    public static final Event<AfterChangeWorld> AFTER_PLAYER_CHANGE_WORLD = EventFactory
            .createArrayBacked(AfterChangeWorld.class, callbacks -> (player, origin, destination) -> {
                for (AfterChangeWorld callback : callbacks) {
                    callback.afterChangeWorld(player, origin, destination);
                }
            });

    static {
        NeoForge.EVENT_BUS.addListener(ServerChatEvent.class, event -> {
            if (!ALLOW_CHAT_MESSAGE.invoker().allowChatMessage(event.getRawText(), event.getPlayer()))
                event.setCanceled(true);
        });

        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerChangedDimensionEvent.class, event -> {
            if (!(event.getEntity() instanceof ServerPlayer player))
                return;

            ServerLevel origin = player.getServer().getLevel(event.getFrom());
            ServerLevel destination = player.getServer().getLevel(event.getTo());

            if (origin != null && destination != null)
                AFTER_PLAYER_CHANGE_WORLD.invoker().afterChangeWorld(player, origin, destination);
        });
    }
}
