package dev.amble.ait.mixin.client.rendering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.ait.client.boti.PortalParticleManager;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

@Mixin(ParticleEngine.class)
public class ParticleManagerMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/texture/TextureManager;register(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/renderer/texture/AbstractTexture;)V"))
    private void ait$skipAtlasRegistration(TextureManager textureManager, ResourceLocation id, AbstractTexture texture) {
        if (!((Object) this instanceof PortalParticleManager))
            textureManager.register(id, texture);
    }

    @Inject(method = "registerProviders", at = @At("HEAD"), cancellable = true)
    private void ait$skipDefaultFactories(CallbackInfo ci) {
        if ((Object) this instanceof PortalParticleManager)
            ci.cancel();
    }
}
