package dev.amble.ait.mixin.compat.ipsodium;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.caffeinemc.mods.sodium.client.render.viewport.Viewport;
import net.caffeinemc.mods.sodium.client.render.viewport.frustum.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import qouteall.imm_ptl.core.compat.sodium_compatibility.SodiumInterface;
import qouteall.imm_ptl.core.render.FrustumCuller;

@Mixin(value = Viewport.class, remap = false)
public class ViewportMixin {

    // 0.8 tests a section by its centre, the radius lives in the frustum planes
    @Unique private static final float RADIUS = 9.125f;

    @WrapOperation(method = "isBoxVisible", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/viewport/frustum/Frustum;testSection(FFF)Z"))
    private boolean portalCull(Frustum frustum, float x, float y, float z, Operation<Boolean> original) {
        return original.call(frustum, x, y, z) && !culled(x, y, z, RADIUS);
    }

    @WrapOperation(method = "isBoxVisibleLooser", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/viewport/frustum/Frustum;testSectionExpanded(FFFF)Z"))
    private boolean portalCullLooser(Frustum frustum, float x, float y, float z, float extend, Operation<Boolean> original) {
        return original.call(frustum, x, y, z, extend) && !culled(x, y, z, RADIUS + extend);
    }

    @Unique private static boolean culled(float x, float y, float z, float r) {
        FrustumCuller culler = SodiumInterface.frustumCuller;
        return culler != null && culler.canDetermineInvisibleWithCameraCoord(x - r, y - r, z - r, x + r, y + r, z + r);
    }
}
