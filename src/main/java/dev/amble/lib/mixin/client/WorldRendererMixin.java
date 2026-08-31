
package dev.amble.lib.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.lib.animation.AnimatedEntity;
import dev.amble.lib.animation.client.AnimationMetadata;
import dev.amble.lib.client.bedrock.BedrockAnimation;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {

	@Final
	@Shadow
	private RenderBuffers renderBuffers;

	@Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;checkPoseStack(Lcom/mojang/blaze3d/vertex/PoseStack;)V", ordinal = 0))
	public void render(DeltaTracker tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightmapTextureManager, Matrix4f frustumMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
		if (!(camera.getEntity() instanceof AnimatedEntity animated)) return;

		float tickDelta = tickCounter.getGameTimeDeltaPartialTick(true);
		PoseStack matrices = new PoseStack();
		matrices.mulPose(frustumMatrix);

		BedrockAnimation anim = BedrockAnimation.getFor(animated);
		
		if (anim == null) {
			BedrockAnimation.IS_RENDERING_HEAD = null;
			return;
		}
		
		AnimationMetadata metadata = anim.metadata;
		
		if (metadata == null || !metadata.fpsCamera()) {
			BedrockAnimation.IS_RENDERING_HEAD = null;
			return;
		}

		boolean thirdPerson = camera.isDetached();
		boolean hasCamera = anim.boneTimelines.containsKey("camera");
		boolean isNear = camera.getPosition().distanceTo(camera.getEntity().position().add(0, camera.getEntity().getEyeHeight(), 0)) <= BedrockAnimation.HEAD_HIDE_DISTANCE;

		Vec3 vec3d = camera.getPosition();
		double d = vec3d.x;
		double e = vec3d.y;
		double f = vec3d.z;
		MultiBufferSource.BufferSource immediate = this.renderBuffers.bufferSource();
		BedrockAnimation.IS_RENDERING_PLAYER = true;
		BedrockAnimation.IS_RENDERING_HEAD = thirdPerson && hasCamera && !isNear;
		this.renderEntity(camera.getEntity(), d, e, f, tickDelta, matrices, immediate);
		BedrockAnimation.IS_RENDERING_PLAYER = false;
	}

	@Shadow
	private void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta,
	                          PoseStack matrices, MultiBufferSource vertexConsumers) {

	}
}
