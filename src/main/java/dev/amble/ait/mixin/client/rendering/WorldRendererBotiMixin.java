package dev.amble.ait.mixin.client.rendering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import dev.loqor.portal.client.WorldGeometryRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.phys.Vec3;

@Mixin(LevelRenderer.class)
public abstract class WorldRendererBotiMixin {

    @Redirect(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getEyePosition(F)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 ait$botiVoidPlaneEye(LocalPlayer player, float tickDelta) {
        Vec3 portalEye = WorldGeometryRenderer.getPortalSkyCameraPos();
        return portalEye != null ? portalEye : player.getEyePosition(tickDelta);
    }

    @Redirect(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/Camera;getPosition()Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 ait$botiSkyColorPos(Camera camera) {
        Vec3 portalEye = WorldGeometryRenderer.getPortalSkyCameraPos();
        return portalEye != null ? portalEye : camera.getPosition();
    }
}
