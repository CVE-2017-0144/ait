package dev.amble.ait.client.renderers.coral;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.coral.CoralGrowthModel;
import dev.amble.ait.core.blockentities.CoralBlockEntity;
import dev.amble.ait.core.blocks.CoralPlantBlock;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class CoralRenderer<T extends CoralBlockEntity> implements BlockEntityRenderer<T> {

    public static final ResourceLocation CORAL_GROWTH_TEXTURE = new ResourceLocation(AITMod.MOD_ID,
            "textures/blockentities/coral/coral_growth.png");

    private final CoralGrowthModel coralModel;

    public CoralRenderer(BlockEntityRendererProvider.Context ctx) {
        this.coralModel = new CoralGrowthModel(CoralGrowthModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers,
            int light, int overlay) {
        matrices.pushPose();
        matrices.translate(0.5, 0, 0.5);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        BlockState blockState = entity.getBlockState();
        float f = blockState.getValue(CoralPlantBlock.FACING).toYRot();
        matrices.mulPose(Axis.YN.rotationDegrees(f));
        ModelPart currentAgeModel = getCurrentAge(blockState.getValue(CoralPlantBlock.AGE), this.coralModel);
        currentAgeModel.render(matrices,
                vertexConsumers.getBuffer(RenderType.entityCutoutNoCull(CORAL_GROWTH_TEXTURE, true)), light,
                overlay, 1, 1, 1, 1);
        matrices.popPose();
    }

    public ModelPart getCurrentAge(int age, CoralGrowthModel coralModel) {
        return switch (age) {
            case 1 -> coralModel.two;
            case 2 -> coralModel.three;
            case 3 -> coralModel.four;
            case 4 -> coralModel.five;
            case 5, 6, 7 -> coralModel.six;
            default -> coralModel.one;
        };
    }
}
