package dev.amble.ait.client.boti;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;

public class BOTIInit {
    public RenderTarget afbo;

    public void setupFramebuffer() {
        Window window = Minecraft.getInstance().getWindow();

        if (afbo == null || afbo.width != window.getWidth() || afbo.height != window.getHeight()) {
            afbo = new TextureTarget(window.getWidth(), window.getHeight(), true, Minecraft.ON_OSX);;
        }

        afbo.bindWrite(false);
        afbo.checkStatus();

        if (!AITRenderHelper.getIsStencilEnabled(afbo)) {
            AITRenderHelper.setIsStencilEnabled(afbo, true);
        }
    }

    public void endFBO() {
        afbo.clear(Minecraft.ON_OSX);
        afbo.unbindWrite();
    }

}
