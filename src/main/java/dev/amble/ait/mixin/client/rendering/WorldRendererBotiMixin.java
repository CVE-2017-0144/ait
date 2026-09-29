package dev.amble.ait.mixin.client.rendering;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import dev.loqor.portal.client.PortalLevelRenderer;
import dev.loqor.portal.client.WorldGeometryRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.phys.Vec3;

@Mixin(LevelRenderer.class)
public abstract class WorldRendererBotiMixin {

    // sky only, a full view area allocs every section
    @WrapOperation(method = "allChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;getEffectiveRenderDistance()I"))
    private int ait$portalViewArea(Options options, Operation<Integer> original) {
        return (Object) this instanceof PortalLevelRenderer ? 0 : original.call(options);
    }

    // wrap, not redirect: immersive portals redirects the same eye call
    @WrapOperation(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getEyePosition(F)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 ait$botiVoidPlaneEye(LocalPlayer player, float tickDelta, Operation<Vec3> original) {
        Vec3 portalEye = WorldGeometryRenderer.getPortalSkyCameraPos();
        return portalEye != null ? portalEye : original.call(player, tickDelta);
    }

    @Redirect(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/Camera;getPosition()Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 ait$botiSkyColorPos(Camera camera) {
        Vec3 portalEye = WorldGeometryRenderer.getPortalSkyCameraPos();
        return portalEye != null ? portalEye : camera.getPosition();
    }
}
