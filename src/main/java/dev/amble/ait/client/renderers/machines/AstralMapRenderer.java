package dev.amble.ait.client.renderers.machines;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.machines.AstralMapModel;
import dev.amble.ait.core.blockentities.AstralMapBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.AABB;
public class AstralMapRenderer<T extends AstralMapBlockEntity> implements BlockEntityRenderer<T> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/blockentities/machines/astral_map.png");

    private final AstralMapModel model;



    public AstralMapRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new AstralMapModel(AstralMapModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public void render(AstralMapBlockEntity entity, float tickDelta, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light, int overlay) {
        BlockState blockState = entity.getBlockState();
        int k = blockState.getValue(SkullBlock.ROTATION);
        float h = 180.0f - RotationSegment.convertToDegrees(k);


        matrices.pushPose();
        matrices.scale(1f, 1f, 1f);


        matrices.mulPose(Axis.XP.rotationDegrees(180));
        matrices.translate(0.5, -1.5f, -0.5);
        matrices.mulPose(Axis.YN.rotationDegrees(h));

        this.model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(TEXTURE)), light, overlay, 0xFFFFFFFF);

        matrices.popPose();
        matrices.pushPose();
        matrices.mulPose(Axis.XP.rotationDegrees(180));
        matrices.translate(0.5, 0, -0.5);
        matrices.mulPose(Axis.YN.rotationDegrees(h));

        this.model.void_cube.render(matrices, vertexConsumers.getBuffer(RenderType.endGateway()), light, overlay, 0xFFFFFFFF);
        matrices.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        return AABB.INFINITE;
    }
}
