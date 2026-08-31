package dev.amble.ait.mixin.client.rendering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.ait.client.util.FoggyUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;

@Mixin(FogRenderer.class)
public class BGRendererMixin {
    @Inject(at = @At("TAIL"), method = "setupFog")
    private static void applyFog(Camera camera, FogRenderer.FogMode fogType, float viewDistance,
            boolean thickFog, float tickDelta, CallbackInfo ci) {
        FoggyUtils.overrideFog();
    }
}
