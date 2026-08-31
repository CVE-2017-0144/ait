package dev.amble.lib.platform.clientlifecycle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

@Environment(EnvType.CLIENT)
public final class ClientInputEvents {

    private ClientInputEvents() {}

    public interface PreAttack {
        boolean onClientPlayerPreAttack(Minecraft client, LocalPlayer player, int clickCount);
    }

    public static final Event<PreAttack> PRE_ATTACK = EventFactory.createArrayBacked(PreAttack.class,
            callbacks -> (client, player, clickCount) -> {
                for (PreAttack callback : callbacks) {
                    if (callback.onClientPlayerPreAttack(client, player, clickCount))
                        return true;
                }

                return false;
            });

    static {
        net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback.EVENT
                .register((client, player, clickCount) -> PRE_ATTACK.invoker()
                        .onClientPlayerPreAttack(client, player, clickCount));
    }
}
