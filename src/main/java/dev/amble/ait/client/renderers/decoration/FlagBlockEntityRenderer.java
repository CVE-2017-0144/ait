package dev.amble.ait.client.renderers.decoration;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.decoration.FlagModel;
import dev.amble.ait.core.blockentities.FlagBlockEntity;
import dev.amble.ait.core.blocks.FlagBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FlagBlockEntityRenderer<T extends FlagBlockEntity> implements BlockEntityRenderer<T> {
    public static final ResourceLocation FLAG_TEXTURE = AITMod.id("textures/blockentities/decoration/us_flag.png");

    public FlagModel flagModel = new FlagModel(FlagModel.getTexturedModelData().bakeRoot());


    public FlagBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(FlagBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();
        matrices.translate(0.5, 1.5f, 0.5);
        matrices.mulPose(Axis.XP.rotationDegrees(180));
        float k = entity.getBlockState().getValue(FlagBlock.FACING).toYRot();
        matrices.mulPose(Axis.YP.rotationDegrees(k));
        flagModel.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityCutoutNoCull(FLAG_TEXTURE)), light, overlay, 1, 1, 1, 1);
        matrices.popPose();
    }
}
