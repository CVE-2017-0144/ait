package dev.amble.ait.mixin.compat.portals;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.loqor.portal.client.ClientWorldAnalog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.SectionBufferBuilderPool;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;

@Mixin(SectionRenderDispatcher.class)
public class SectionRenderDispatcherMixin {

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBuffers;sectionBufferPool()Lnet/minecraft/client/renderer/SectionBufferBuilderPool;"))
    private SectionBufferBuilderPool ait$sharedPool(RenderBuffers buffers, Operation<SectionBufferBuilderPool> original, @Local(argsOnly = true) ClientLevel level) {
        return level instanceof ClientWorldAnalog ? buffers.sectionBufferPool() : original.call(buffers);
    }
}
