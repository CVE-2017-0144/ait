package dev.amble.lib.platform.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

@Environment(EnvType.CLIENT)
public final class HudRenderEvents {

    private HudRenderEvents() {}

    public interface HudRender {
        void onHudRender(GuiGraphics context, DeltaTracker delta);
    }

    public static final Event<HudRender> HUD = EventFactory.createArrayBacked(HudRender.class,
            callbacks -> (context, delta) -> {
                for (HudRender callback : callbacks) {
                    callback.onHudRender(context, delta);
                }
            });

    static {
        net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback.EVENT
                .register((context, delta) -> HUD.invoker().onHudRender(context, delta));
    }
}
