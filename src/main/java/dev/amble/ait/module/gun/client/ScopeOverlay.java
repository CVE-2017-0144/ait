package dev.amble.ait.module.gun.client;

import dev.amble.ait.AITMod;
import dev.amble.ait.module.gun.core.item.StaserRifleItem;
import dev.amble.lib.platform.render.HudRenderEvents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ScopeOverlay implements HudRenderEvents.HudRender {

    private static final ResourceLocation SPYGLASS_SCOPE = AITMod.id("textures/gui/overlay/scope.png");
    private int scaledWidth, scaledHeight;
    private float spyglassScale;

    @Override
    public void onHudRender(GuiGraphics drawContext, DeltaTracker v) {
        this.scaledWidth = drawContext.guiWidth();
        this.scaledHeight = drawContext.guiHeight();

        Minecraft mc = Minecraft.getInstance();

        if(mc.player == null) return;

        if(mc.player.getMainHandItem().getItem() instanceof StaserRifleItem && mc.options.getCameraType().isFirstPerson()) {
            if (mc.options.keyUse.isDown()) {
                float f = v.getGameTimeDeltaTicks();
                this.spyglassScale = Mth.lerp(0.5f * f, this.spyglassScale, 1.125f);
                this.renderSpyglassOverlay(drawContext, this.spyglassScale);
            }
        }

        if (mc.player == null)
            return;
    }

    private void renderSpyglassOverlay(GuiGraphics context, float scale) {
        float f;
        float g = f = (float)Math.min(this.scaledWidth, this.scaledHeight);
        float h = Math.min((float)this.scaledWidth / f, (float)this.scaledHeight / g) * scale;
        int i = Mth.floor(f * h);
        int j = Mth.floor(g * h);
        int k = (this.scaledWidth - i) / 2;
        int l = (this.scaledHeight - j) / 2;
        int m = k + i;
        int n = l + j;
        context.blit(SPYGLASS_SCOPE, k, l, -100, 0.0f, 0.0f, i, j, i, j);
        context.fill(RenderType.guiOverlay(), 0, n, this.scaledWidth, this.scaledHeight, -90, -16777216);
        context.fill(RenderType.guiOverlay(), 0, 0, this.scaledWidth, l, -90, -16777216);
        context.fill(RenderType.guiOverlay(), 0, l, k, n, -90, -16777216);
        context.fill(RenderType.guiOverlay(), m, l, this.scaledWidth, n, -90, -16777216);
    }
}
