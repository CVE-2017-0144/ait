package dev.amble.lib.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.lib.animation.AnimatedEntity;
import dev.amble.lib.animation.client.AnimationMetadata;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public class HeldItemFeatureRendererMixin {
	@Inject(method="renderArmWithItem", at = @At("HEAD"), cancellable = true)
	private void amblekit$renderItem(LivingEntity entity,
	                                 ItemStack stack,
	                                 ItemDisplayContext transformationMode,
	                                 HumanoidArm arm,
	                                 PoseStack matrices,
	                                 MultiBufferSource vertexConsumers,
	                                 int light, CallbackInfo ci) {
		if (!(entity instanceof AnimatedEntity animated)) return;

		AnimationMetadata metadata = AnimationMetadata.getFor(animated);
		if (metadata == null || !metadata.hideHandItems()) return;

		ci.cancel();
	}
}
