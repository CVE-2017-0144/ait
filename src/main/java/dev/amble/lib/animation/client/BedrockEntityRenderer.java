package dev.amble.lib.animation.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.lib.animation.AnimatedEntity;
import dev.amble.lib.client.bedrock.BedrockEntityModel;
import dev.amble.lib.client.bedrock.BedrockModel;
import dev.amble.lib.client.bedrock.BedrockModelReference;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class BedrockEntityRenderer<T extends LivingEntity & AnimatedEntity> extends LivingEntityRenderer<T, BedrockEntityModel<T>> {
	public BedrockEntityRenderer(EntityRendererProvider.Context ctx, float shadowRadius) {
		super(ctx, null, shadowRadius);
	}

	public BedrockEntityRenderer(EntityRendererProvider.Context ctx) {
		this(ctx, 0.5f);
	}

	@Override
	public void render(T livingEntity, float f, float g, PoseStack matrices, MultiBufferSource vertexConsumerProvider, int i) {
		if (this.model == null) this.refreshModel(livingEntity);

		matrices.pushPose();

		matrices.translate(0.0D, -1.5D, 0.0D);
		super.render(livingEntity, f, g, matrices, vertexConsumerProvider, i);

		matrices.popPose();
	}

	@Override
	protected void renderNameTag(T entity, Component text, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.translate(0, 1.5D, 0);
		super.renderNameTag(entity, text, matrices, vertexConsumers, light);
		matrices.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(T entity) {
		return entity.getTexture();
	}

	protected BedrockEntityModel<T> refreshModel(T entity) {
		BedrockModelReference ref = entity.getModel();
		if (ref == null) throw new IllegalStateException("Entity " + entity + " does not have a BedrockModelReference");
		BedrockModel bedrock = ref.get().orElseThrow(() -> new IllegalStateException("BedrockModel " + ref.id() + " not found for entity " + entity));

		this.model = new BedrockEntityModel<>(bedrock);
		return this.model;
	}
}
