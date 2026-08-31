package dev.amble.lib.mixin.client;

import dev.amble.lib.animation.AnimatedEntity;
import dev.amble.lib.client.bedrock.BedrockAnimation;
import dev.amble.lib.client.bedrock.BedrockAnimationReference;
import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
	@Shadow
	protected abstract void setRotation(float yaw, float pitch);

	@Shadow
	protected abstract void setPosition(Vec3 pos);

	@Shadow
	protected abstract void move(double x, double y, double z);

	@Shadow
	public abstract float getYRot();

	@Shadow
	public abstract Vec3 getPosition();

	@Shadow
	public abstract Quaternionf rotation();

	@Shadow
	protected abstract double getMaxZoom(double desiredCameraDistance);

	@Inject(method="setup", at=@At("TAIL"))
	private void amble$update(BlockGetter area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
		if (!(focusedEntity instanceof AnimatedEntity animated)) return;

		BedrockAnimationReference ref = animated.getCurrentAnimation();
		if (ref == null) return;

		BedrockAnimation animation = ref.get().orElse(null);
		if (animation == null || !animation.metadata.fpsCamera()) return;

		AnimationState state = animated.getAnimationState();
		if (state == null || animation.isFinished(state)) return;

		double progress = animation.getRunningSeconds(state);

		String cameraPart = thirdPerson ? "camera" : "head";
		if (!animation.boneTimelines.containsKey(cameraPart)) return;

		float yaw;

		if (thirdPerson && animation.metadata.fpsCameraCopiesHead()) {
			yaw = (focusedEntity instanceof LocalPlayer clientPlayer) ? (Mth.rotLerp(tickDelta, clientPlayer.yHeadRotO, clientPlayer.yHeadRot)) : focusedEntity.getYHeadRot();
		} else {
			yaw = (focusedEntity instanceof LocalPlayer clientPlayer) ? (Mth.rotLerp(tickDelta, clientPlayer.yBodyRotO, clientPlayer.yBodyRot)) : focusedEntity.getVisualRotationYInDegrees();
		}

		Vec3 position = animation.boneTimelines.get(cameraPart).position().resolve(progress);
		float height = cameraPart.equals("head") ? focusedEntity.getEyeHeight() : 0;

		this.setPosition(
				new Vec3(
				Mth.lerp(tickDelta, focusedEntity.xo, focusedEntity.getX()),
				Mth.lerp(tickDelta, focusedEntity.yo, focusedEntity.getY()) + (thirdPerson ? 0 : height),
				Mth.lerp(tickDelta, focusedEntity.zo, focusedEntity.getZ()))
		);

		Tuple<Float, Float> rots = animation.getRotations(cameraPart, (float) progress);
		float animYaw = rots.getB();
		float animPitch = rots.getA();

		Vec3 pos = position.yRot((float)Math.toRadians(90)).scale(-1 / 16F);
		this.setRotation(yaw, 0);
		// todo \/ the clipping causes the camera to break when on ground
		this.move(getMaxZoom(pos.x), getMaxZoom(pos.y), getMaxZoom(pos.z));
		this.setRotation(animYaw + yaw, animPitch);
	}
}
