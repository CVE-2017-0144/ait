package dev.amble.lib.platform.render;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

@OnlyIn(Dist.CLIENT)
public class HudRenderEvents {

    public interface HudRender {
        void onHudRender(GuiGraphics context, DeltaTracker delta);
    }

    public static final Event<HudRender> HUD = EventFactory.createArrayBacked(HudRender.class,
            cbs -> (context, delta) -> {
                for (HudRender cb : cbs) {
                    cb.onHudRender(context, delta);
                }
            });

    static {
        NeoForge.EVENT_BUS.addListener(RenderGuiEvent.Post.class,
                event -> HUD.invoker().onHudRender(event.getGuiGraphics(), event.getPartialTick()));
    }
}
