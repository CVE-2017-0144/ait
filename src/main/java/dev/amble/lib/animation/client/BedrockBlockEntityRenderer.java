package dev.amble.lib.animation.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.lib.animation.AnimatedBlockEntity;
import dev.amble.lib.client.bedrock.BedrockEntityModel;
import dev.amble.lib.client.bedrock.BedrockModelReference;
import dev.amble.lib.client.bedrock.BedrockModelRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

@Environment(EnvType.CLIENT)
public class BedrockBlockEntityRenderer<T extends BlockEntity & AnimatedBlockEntity> implements BlockEntityRenderer<T> {

	protected BedrockEntityModel<?> model;

	public BedrockBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		if (this.model == null) this.refreshModel(entity);

		matrices.pushPose();
		matrices.translate(0.5D, 0.0D, 0.5D);
		matrices.mulPose(Axis.XP.rotationDegrees(180F));
		matrices.mulPose(Axis.YP.rotationDegrees(entity.getRenderYaw()));

        model.setAngles(entity, entity.getAge() + tickDelta);

		model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityCutoutNoCull(this.getTexture(entity))), light, overlay, 0xFFFFFFFF);

		ResourceLocation emission = entity.getEmissionTexture();
		if (emission != null) {
			model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityCutoutNoCullZOffset(emission)), LightTexture.FULL_BRIGHT, overlay, 0xFFFFFFFF);
		}

		matrices.popPose();
	}

	public ResourceLocation getTexture(T entity) {
		return entity.getTexture();
	}

    protected BedrockEntityModel<?> refreshModel(T entity) {
		BedrockModelReference ref = entity.getModel();
		if (ref == null) {
			throw new IllegalStateException("BlockEntity " + entity + " does not have a BedrockModelReference");
		}
		return this.model = new BedrockEntityModel<>(ref.get().orElseThrow(() ->
				new IllegalStateException("BedrockModel " + ref.id() + " not found for block entity " + entity)));
	}
}