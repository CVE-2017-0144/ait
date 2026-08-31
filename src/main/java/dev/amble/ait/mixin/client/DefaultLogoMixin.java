package dev.amble.ait.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.AITModClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.ResourceLocation;

@Mixin(LogoRenderer.class)
public class DefaultLogoMixin {

    @Unique private static final ResourceLocation AIT_LOGO = AITMod.id("textures/gui/title/ait_logo.png");
    @Unique private final Minecraft client = Minecraft.getInstance();

    @Redirect(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V", ordinal = 0))
    private void ait$drawCustomLogo(GuiGraphics context, ResourceLocation texture, int x, int y, float u, float v, int width,
                                    int height, int textureWidth, int textureHeight) {

        if (!AITModClient.CONFIG.customMenu) {
            context.blit(texture, x, y, u, v, width, height, textureWidth, textureHeight);
            return;
        }

        int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int centerX = screenWidth / 2 - 121;
        int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        int centerY = screenHeight / 5 - 21;
        context.blit(AIT_LOGO, centerX, centerY, 0, 0, 242, 42, 242, 42);
    }

    @Redirect(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V", ordinal = 1))
    private void ait$skipEdition(GuiGraphics context, ResourceLocation texture, int x, int y, float u, float v, int width,
                                 int height, int textureWidth, int textureHeight) {
        if (!AITModClient.CONFIG.customMenu)
            context.blit(texture, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    @Inject(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At("TAIL"))
    private void renderWarningMessage(GuiGraphics context, int screenWidth, float alpha, int y, CallbackInfo ci) {
        if (AITMod.isUnsafeBranch()) {

            String warningMessage =  "WARNING!: You are using an experimental version (" + AITMod.BRANCH + "), please be cautious when testing!";

            screenWidth = this.client.getWindow().getGuiScaledWidth();
            int textWidth = this.client.font.width(warningMessage);


            int x = (screenWidth - textWidth) / 2;
            y = 10;
            int padding = 7;


            context.fill(0, y - padding, screenWidth, y + this.client.font.lineHeight + padding, 0xAA000000);

            context.drawString(this.client.font, warningMessage, x, y, 0xFFFF0000, true);
        }
    }
}