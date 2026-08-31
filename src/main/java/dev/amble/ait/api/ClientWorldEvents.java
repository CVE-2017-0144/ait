package dev.amble.ait.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ClientWorldEvents {
    public static final Event<ChangeWorld> CHANGE_WORLD = EventFactory.createArrayBacked(ChangeWorld.class,
            callbacks -> (client, world) -> {
                for (ChangeWorld callback : callbacks) {
                    callback.onChange(client, world);
                }
            });

    static {
        ClientPlayConnectionEvents.JOIN.register((handler, packetSender, client) -> {
            client.execute(() -> {
                ClientWorldEvents.CHANGE_WORLD.invoker().onChange(client, client.level);
            });
        });
    }

    @FunctionalInterface
    public interface ChangeWorld {
        void onChange(Minecraft client, @Nullable ClientLevel world);
    }
}
