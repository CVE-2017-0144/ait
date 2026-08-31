package dev.amble.lib.platform.lifecycle;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public final class ServerPlayerEvents {

    private ServerPlayerEvents() {}

    public interface AllowChatMessage {
        boolean allowChatMessage(PlayerChatMessage message, ServerPlayer sender, ChatType.Bound params);
    }

    public interface AfterChangeWorld {
        void afterChangeWorld(ServerPlayer player, ServerLevel origin, ServerLevel destination);
    }

    public static final Event<AllowChatMessage> ALLOW_CHAT_MESSAGE = EventFactory
            .createArrayBacked(AllowChatMessage.class, callbacks -> (message, sender, params) -> {
                for (AllowChatMessage callback : callbacks) {
                    if (!callback.allowChatMessage(message, sender, params))
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
        bootstrap();
    }

    private static void bootstrap() {
        net.fabricmc.fabric.api.message.v1.ServerMessageEvents.ALLOW_CHAT_MESSAGE
                .register((message, sender, params) -> ALLOW_CHAT_MESSAGE.invoker()
                        .allowChatMessage(message, sender, params));
        net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD
                .register((player, origin, destination) -> AFTER_PLAYER_CHANGE_WORLD.invoker()
                        .afterChangeWorld(player, origin, destination));
    }
}
