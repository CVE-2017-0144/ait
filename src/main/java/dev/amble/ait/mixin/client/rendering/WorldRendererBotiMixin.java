package dev.amble.ait.mixin.client.rendering;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import dev.loqor.portal.client.WorldGeometryRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.phys.Vec3;

@Mixin(LevelRenderer.class)
public abstract class WorldRendererBotiMixin {

    // wrap, not redirect: immersive portals redirects the same eye call
    @WrapOperation(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getEyePosition(F)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 ait$botiVoidPlaneEye(LocalPlayer player, float tickDelta, Operation<Vec3> original) {
        Vec3 portalEye = WorldGeometryRenderer.getPortalSkyCameraPos();
        return portalEye != null ? portalEye : original.call(player, tickDelta);
    }

    @WrapOperation(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/Camera;getPosition()Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 ait$botiSkyColorPos(Camera camera, Operation<Vec3> original) {
        Vec3 portalEye = WorldGeometryRenderer.getPortalSkyCameraPos();
        return portalEye != null ? portalEye : original.call(camera);
    }
}
