package dev.amble.ait.client.boti;


import com.mojang.blaze3d.pipeline.RenderTarget;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


public class AITRenderHelper {

    @OnlyIn(Dist.CLIENT)
    public static boolean getIsStencilEnabled(RenderTarget renderTarget) {
        return ((StencilFrameBuffer) renderTarget).ait$getIsStencilBufferEnabled();
    }

    @OnlyIn(Dist.CLIENT)
    public static void setIsStencilEnabled(RenderTarget renderTarget, boolean cond) {
        ((StencilFrameBuffer) renderTarget).ait$setIsStencilBufferEnabledAndReload(cond);
    }

}
