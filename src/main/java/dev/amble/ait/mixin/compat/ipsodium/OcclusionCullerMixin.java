package dev.amble.ait.mixin.compat.ipsodium;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.occlusion.OcclusionCuller;
import net.caffeinemc.mods.sodium.client.render.viewport.Viewport;
import net.minecraft.core.SectionPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import qouteall.imm_ptl.core.CHelper;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.render.context_management.PortalRendering;

@Mixin(value = OcclusionCuller.class, remap = false)
public abstract class OcclusionCullerMixin {

    @Unique @Nullable private SectionPos startPoint;
    @Unique private static boolean tolerantFrustum;

    @Shadow protected abstract RenderSection getRenderSection(int x, int y, int z);

    @ModifyVariable(method = "findVisible", at = @At("HEAD"), argsOnly = true)
    private boolean portalStart(boolean useOcclusionCulling, @Local(argsOnly = true) Viewport viewport) {
        boolean caveCulling = PortalRendering.shouldEnableSodiumCaveCulling();
        this.startPoint = null;
        tolerantFrustum = false;

        if (!PortalRendering.isRendering())
            return caveCulling;

        Portal portal = PortalRendering.getRenderingPortal();
        Vec3 camera = CHelper.getCurrentCameraPos();
        this.startPoint = portal.getPortalShape().getModifiedVisibleSectionIterationOrigin(portal, camera);

        if (this.startPoint == null)
            return caveCulling;

        RenderSection section = this.getRenderSection(this.startPoint.x(), this.startPoint.y(), this.startPoint.z());

        if (section != null && !OcclusionCuller.isWithinFrustum(viewport, section))
            tolerantFrustum = true;

        return false;
    }

    @Redirect(method = {"init", "initWithinWorld"}, at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/viewport/Viewport;getChunkCoord()Lnet/minecraft/core/SectionPos;"))
    private SectionPos portalStart(Viewport viewport) {
        return this.startPoint != null ? this.startPoint : viewport.getChunkCoord();
    }

    @ModifyReturnValue(method = "isWithinFrustum", at = @At("RETURN"))
    private static boolean tolerateStart(boolean within) {
        if (!tolerantFrustum)
            return within;

        if (within)
            tolerantFrustum = false;

        return true;
    }
}
