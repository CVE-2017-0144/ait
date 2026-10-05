package dev.amble.ait.mixin.compat.portals;

import net.minecraft.client.Minecraft;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qouteall.imm_ptl.core.IPCGlobal;
import qouteall.imm_ptl.core.compat.iris_compatibility.IrisPortalRenderer;

@Mixin(value = IrisPortalRenderer.class, remap = false)
public class IrisPortalRendererMixin {

    // ip guesses main depth from the gpu, neo's stencil is depth32f_stencil8 everywhere and the blit from main fails on nvidia
    @Inject(method = "prepareRendering", at = @At(value = "FIELD", target = "Lqouteall/imm_ptl/core/IPCGlobal;useSeparatedStencilFormat:Z", opcode = Opcodes.PUTSTATIC, shift = At.Shift.AFTER))
    private void matchMainDepth(CallbackInfo ci) {
        if (Minecraft.getInstance().getMainRenderTarget().isStencilEnabled())
            IPCGlobal.useSeparatedStencilFormat = true;
    }
}
