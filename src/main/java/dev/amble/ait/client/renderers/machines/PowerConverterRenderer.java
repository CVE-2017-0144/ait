package dev.amble.ait.client.renderers.machines;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.machines.PowerConverterModel;
import dev.amble.ait.core.blocks.PlaqueBlock;
import dev.amble.ait.core.blocks.PowerConverterBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class PowerConverterRenderer<T extends PowerConverterBlock.BlockEntity> implements BlockEntityRenderer<T> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(AITMod.MOD_ID,
            ("textures/blockentities/machines/power_converter.png"));;
    private final PowerConverterModel model;

    public PowerConverterRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new PowerConverterModel();
    }

    @Override
    public void render(PowerConverterBlock.BlockEntity entity, float tickDelta, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light, int overlay) {
        BlockState blockState = entity.getBlockState();

        matrices.pushPose();
        matrices.scale(1.35f, 1.35f, 1.35f);
        matrices.translate(0.38, 1.5f, 0.38);

        Direction k = blockState.getValue(PlaqueBlock.FACING);
        matrices.mulPose(Axis.YN.rotationDegrees(k.toYRot()));

        matrices.mulPose(Axis.XP.rotationDegrees(180));

        this.model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(TEXTURE)),
                light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.popPose();
    }
}
