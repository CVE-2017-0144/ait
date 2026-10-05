package dev.amble.ait.mixin.client.rendering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.ait.client.boti.TardisDoorBOTI;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;

@Mixin(GameRenderer.class)
public abstract class GameRendererBotiMixin {

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", shift = At.Shift.AFTER,
            target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V"))
    private void ait$doorBotiOverShaders(DeltaTracker tracker, CallbackInfo ci) {
        TardisDoorBOTI.drawShaderOverlay();
    }
}
