package dev.amble.plushies.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.lib.animation.client.BedrockBlockEntityRenderer;
import dev.amble.lib.client.bedrock.BedrockEntityModel;
import dev.amble.plushies.MarketablePlushieBlock;
import dev.amble.plushies.MarketablePlushieBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MarketablePlushieRenderer<T extends MarketablePlushieBlockEntity> extends BedrockBlockEntityRenderer<T> {

    private static final float MAX_SCALE = 3.0f;
    private static final float NORMAL_SCALE = 1.5f;

    public MarketablePlushieRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        BlockState state = entity.getBlockState();
        Block block = state.getBlock();

        if (!(block instanceof MarketablePlushieBlock plushieBlock)) return;

        BedrockEntityModel<?> model = plushieBlock.model == null ? plushieBlock.model = refreshModel(entity) : plushieBlock.model;

        BlockState downState = entity.getLevel().getBlockState(entity.getBlockPos().below());
        Block downBlock = downState.getBlock();
        if (downBlock instanceof MarketablePlushieBlock && downState.getValue(MarketablePlushieBlock.STACKED))
            return;

        matrices.pushPose();
        matrices.translate(0.5D, 0.0D, 0.5D);
        matrices.mulPose(Axis.XP.rotationDegrees(180F));
        matrices.mulPose(Axis.YP.rotationDegrees(entity.getRenderYaw()));

        boolean stacked = state.getValue(MarketablePlushieBlock.STACKED);
        float scale = stacked ? MAX_SCALE : NORMAL_SCALE;
        matrices.scale(scale, scale, scale);

        model.setAngles(entity, entity.getAge() + tickDelta);

        model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityCutoutNoCull(getTexture(entity))), light, overlay, 0xFFFFFFFF);

        ResourceLocation emission = entity.getEmissionTexture();
        if (emission != null) {
            model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityCutoutNoCullZOffset(emission)), LightTexture.FULL_BRIGHT, overlay, 0xFFFFFFFF);
        }

        matrices.popPose();
    }
}
