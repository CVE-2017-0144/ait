package dev.amble.ait.client.renderers.machines;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.machines.FoodMachineModel;
import dev.amble.ait.core.blockentities.FoodMachineBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;

public class FoodMachineRenderer<T extends FoodMachineBlockEntity> implements BlockEntityRenderer<T> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/blockentities/machines/food_machine.png");

    private final FoodMachineModel foodMachineModel;



    public FoodMachineRenderer(BlockEntityRendererProvider.Context ctx) {
        this.foodMachineModel = new FoodMachineModel(FoodMachineModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public void render(FoodMachineBlockEntity entity, float tickDelta, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light, int overlay) {
        BlockState blockState = entity.getBlockState();
        int k = blockState.getValue(SkullBlock.ROTATION);
        float h = 180.0f - RotationSegment.convertToDegrees(k);


        matrices.pushPose();



        matrices.mulPose(Axis.XP.rotationDegrees(180));
        matrices.translate(0.5, -1.5f, -0.5);
        matrices.mulPose(Axis.YN.rotationDegrees(h));

        this.foodMachineModel.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(TEXTURE)), light, overlay, 0xFFFFFFFF);

        matrices.popPose();
    }

}
