package dev.amble.lib.platform.clientlifecycle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.NeoForge;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

@OnlyIn(Dist.CLIENT)
public class ClientInputEvents {

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
        NeoForge.EVENT_BUS.addListener(InputEvent.InteractionKeyMappingTriggered.class, e -> {
            if (!e.isAttack())
                return;

            Minecraft client = Minecraft.getInstance();

            if (client.player == null)
                return;

            if (PRE_ATTACK.invoker().onClientPlayerPreAttack(client, client.player, 0)) {
                e.setSwingHand(false);
                e.setCanceled(true);
            }
        });
    }
}
