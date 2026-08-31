package dev.amble.ait.api;

import dev.amble.lib.platform.clientlifecycle.ClientEvents;
import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class ClientWorldEvents {
    public static final Event<ChangeWorld> CHANGE_WORLD = EventFactory.createArrayBacked(ChangeWorld.class,
            callbacks -> (client, world) -> {
                for (ChangeWorld callback : callbacks) {
                    callback.onChange(client, world);
                }
            });

    static {
        ClientEvents.JOIN.register((client) -> {
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
