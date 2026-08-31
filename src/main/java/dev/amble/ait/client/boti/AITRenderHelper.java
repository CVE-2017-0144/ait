package dev.amble.ait.client.boti;


import com.mojang.blaze3d.pipeline.RenderTarget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


public class AITRenderHelper {

    @Environment(EnvType.CLIENT)
    public static boolean getIsStencilEnabled(RenderTarget renderTarget) {
        return ((StencilFrameBuffer) renderTarget).ait$getIsStencilBufferEnabled();
    }

    @Environment(EnvType.CLIENT)
    public static void setIsStencilEnabled(RenderTarget renderTarget, boolean cond) {
        ((StencilFrameBuffer) renderTarget).ait$setIsStencilBufferEnabledAndReload(cond);
    }

}
