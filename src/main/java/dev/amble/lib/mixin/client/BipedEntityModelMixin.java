package dev.amble.lib.mixin.client;

import dev.amble.lib.animation.AnimatedEntity;
import dev.amble.lib.animation.client.AnimatedEntityModel;
import dev.amble.lib.client.bedrock.BedrockAnimation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

@Mixin(HumanoidModel.class)
public class BipedEntityModelMixin<T extends LivingEntity> implements AnimatedEntityModel {
	@Unique
	ModelPart root;

	@Shadow
	@Final
	public ModelPart head;

	@Inject(method="setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
	private void animation$setAnglePre(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
		if (!(livingEntity instanceof AnimatedEntity player)) return;

		this.applyAnimationPre(player, h);
	}

	@Inject(method="setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;copyFrom(Lnet/minecraft/client/model/geom/ModelPart;)V"))
	private void animation$setAngle(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
		if (!(livingEntity instanceof AnimatedEntity player)) return;

		this.applyAnimation(player, h);

		if (!BedrockAnimation.IS_RENDERING_PLAYER || livingEntity != Minecraft.getInstance().cameraEntity) {
			return;
		}

		if (BedrockAnimation.IS_RENDERING_HEAD != null) {
			head.visible = BedrockAnimation.IS_RENDERING_HEAD;
		}
	}

	@Inject(method = "<init>(Lnet/minecraft/client/model/geom/ModelPart;Ljava/util/function/Function;)V", at = @At("TAIL"))
	public void animation$init(ModelPart root, Function<ResourceLocation, RenderType> renderLayerFactory, CallbackInfo ci) {
		this.root = root;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}
