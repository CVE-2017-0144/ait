package dev.amble.ait.mixin.compat.portals;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.loqor.portal.client.PortalData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import qouteall.imm_ptl.core.ClientWorldLoader;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;

@Mixin(PortalData.class)
public class PortalDataMixin {

    @WrapOperation(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;setLevel(Lnet/minecraft/client/multiplayer/ClientLevel;)V"))
    private static void ait$secondaryRenderer(LevelRenderer renderer, ClientLevel level, Operation<Void> original) {
        boolean creating = ClientWorldLoader.getIsCreatingClientWorld();
        ClientWorldLoaderAccessor.ait$setCreatingClientWorld(true);

        try {
            original.call(renderer, level);
        } finally {
            ClientWorldLoaderAccessor.ait$setCreatingClientWorld(creating);
        }
    }
}
