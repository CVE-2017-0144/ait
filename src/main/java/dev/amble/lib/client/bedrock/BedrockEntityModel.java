package dev.amble.lib.client.bedrock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.lib.animation.AnimatedEntity;
import dev.amble.lib.animation.AnimatedInstance;
import dev.amble.lib.animation.client.AnimatedEntityModel;
import java.util.List;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

public class BedrockEntityModel<T extends Entity & AnimatedEntity> extends EntityModel<T> implements AnimatedEntityModel {
	private final BedrockModel model;
	private final ModelPart root;
	private final int textureWidth;
	private final int textureHeight;

	public BedrockEntityModel(BedrockModel model) {
		this.model = model;
		this.root = model.create().bakeRoot();
		this.textureWidth = model.geometry.get(0).description.textureWidth;
		this.textureHeight = model.geometry.get(0).description.textureHeight;
	}

	@Override
	public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.applyAnimationPre(entity, animationProgress);
		this.applyAnimation(entity, animationProgress);
	}

	public void setAngles(AnimatedInstance instance, float animationProgress) {
		this.applyAnimationPre(instance, animationProgress);
		this.applyAnimation(instance, animationProgress);
	}

	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.getPart().render(matrices, vertices, light, overlay, red, green, blue, alpha);

		List<BedrockModel.PerFaceCube> deferred = model.deferredPerFaceCubes();
		if (deferred.isEmpty()) return;

		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees(180f));

		BedrockPerFaceRenderer.render(
				this.root,
				deferred,
				matrices,
				vertices,
				light,
				overlay,
				red, green, blue, alpha,
				this.textureWidth,
				this.textureHeight
		);
		matrices.popPose();
	}

	@Override
	public ModelPart getPart() {
		return root;
	}
}