package dev.amble.ait.module.planet.mixin.client;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;

@Mixin(value = LevelRenderer.class, priority = 1001)
public abstract class CloudMixin {

    @Inject(method="renderClouds(Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FDDD)V", at = @At("HEAD"), cancellable = true)
    private void ait$renderClouds(PoseStack matrices, Matrix4f frustumMatrix, Matrix4f projectionMatrix, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return;

        if (TardisServerWorld.isTardisDimension(mc.player.level())) {
            ci.cancel();
            return;
        }

        Planet planet = PlanetRegistry.getInstance().get(mc.player.level());

        if (planet == null)
            return;

        if (!planet.render().clouds())
            ci.cancel();
    }
}
