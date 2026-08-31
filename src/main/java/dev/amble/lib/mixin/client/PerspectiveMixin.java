package dev.amble.lib.mixin.client;

import dev.amble.lib.animation.AnimatedEntity;
import dev.amble.lib.animation.client.AnimationMetadata;
import dev.amble.lib.client.bedrock.BedrockAnimation;
import dev.amble.lib.client.bedrock.BedrockAnimationReference;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CameraType.class)
public class PerspectiveMixin {
    @Inject(method = "isFirstPerson", at = @At("HEAD"), cancellable = true)
    private void animation$isFirstPerson(CallbackInfoReturnable<Boolean> cir) {
	    LocalPlayer player = Minecraft.getInstance().player;

	    if (!(player instanceof AnimatedEntity animated)) return;

	    AnimationMetadata metadata = AnimationMetadata.getFor(animated);

	    if (metadata == null || metadata.perspective() == null) return;

        cir.setReturnValue(metadata.perspective().isFirstPerson());
    }
    @Inject(method = "isMirrored", at = @At("HEAD"), cancellable = true)
    private void animation$isFrontView(CallbackInfoReturnable<Boolean> cir) {
	    LocalPlayer player = Minecraft.getInstance().player;

	    if (!(player instanceof AnimatedEntity animated)) return;

	    AnimationMetadata metadata = AnimationMetadata.getFor(animated);

	    if (metadata == null || metadata.perspective() == null) return;

	    cir.setReturnValue(metadata.perspective().isFrontView());
    }
}