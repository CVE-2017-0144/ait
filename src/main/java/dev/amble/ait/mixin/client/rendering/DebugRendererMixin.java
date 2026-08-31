package dev.amble.ait.mixin.client.rendering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.client.renderers.LandingRegionRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;

@Mixin(DebugRenderer.class)
public class DebugRendererMixin {
    @Inject(method="render", at = @At("TAIL"))
    private void ait$renderWorld(PoseStack matrices, MultiBufferSource.BufferSource vertexConsumers, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        LandingRegionRenderer.getInstance().tryRender(matrices, vertexConsumers, cameraX, cameraY, cameraZ);
    }
}
