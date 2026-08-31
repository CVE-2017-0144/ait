package dev.amble.ait.client.renderers.machines;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.machines.UntemperedSchismModel;
import dev.amble.ait.core.blockentities.UntemperedSchismBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

public class UntemperedSchismRenderer<T extends UntemperedSchismBlockEntity> implements BlockEntityRenderer<T> {

    public static final ResourceLocation UNTEMPERED_SCHISM_TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/machines/untempered_schism.png"));
    private final UntemperedSchismModel untemperedSchismModel;

    public UntemperedSchismRenderer(BlockEntityRendererProvider.Context ctx) {
        this.untemperedSchismModel = new UntemperedSchismModel(UntemperedSchismModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public void render(UntemperedSchismBlockEntity entity, float tickDelta, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light, int overlay) {

        BlockState blockState = entity.getBlockState();

        float f = blockState.getValue(HorizontalDirectionalBlock.FACING).toYRot();

        matrices.pushPose();
        matrices.translate(0.5f, 1.5f, 0.5f);

        matrices.mulPose(Axis.YN.rotationDegrees(f));

        matrices.mulPose(Axis.XP.rotationDegrees(180));

        this.untemperedSchismModel.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(UNTEMPERED_SCHISM_TEXTURE)), light, overlay, 0xFFFFFFFF);

        matrices.popPose();
    }
}
