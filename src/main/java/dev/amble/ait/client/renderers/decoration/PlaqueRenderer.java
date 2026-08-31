package dev.amble.ait.client.renderers.decoration;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.decoration.PlaqueModel;
import dev.amble.ait.core.blockentities.PlaqueBlockEntity;
import dev.amble.ait.core.blocks.PlaqueBlock;
import dev.amble.ait.core.tardis.Tardis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class PlaqueRenderer<T extends PlaqueBlockEntity> implements BlockEntityRenderer<T> {

    public static final ResourceLocation PLAQUE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/decoration/plaque.png"));
    private final Font textRenderer = Minecraft.getInstance().font;
    private final PlaqueModel plaqueModel;

    public PlaqueRenderer(BlockEntityRendererProvider.Context ctx) {
        this.plaqueModel = new PlaqueModel(PlaqueModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public void render(PlaqueBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {

        BlockState blockState = entity.getBlockState();

        Direction k = blockState.getValue(PlaqueBlock.FACING);

        matrices.pushPose();

        matrices.translate(0.5f, 1.5f, 0.5f);

        matrices.mulPose(Axis.YN.rotationDegrees(k.toYRot()));

        matrices.mulPose(Axis.XP.rotationDegrees(180));

        this.plaqueModel.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(PLAQUE_TEXTURE)), light, overlay, 0xFFFFFFFF);

        matrices.popPose();

        if (!entity.isLinked() || entity.tardis().isEmpty())
            return;

        Tardis tardis = entity.tardis().get();

        matrices.pushPose();
        matrices.translate(0.5, 0.75, 0.5);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.mulPose(Axis.YP.rotationDegrees(k.toYRot()));
        matrices.scale(0.01f, 0.01f, 0.01f);
        float xVal = 0;
        matrices.translate(xVal, -35f, 35f);

        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty(tardis.stats().getCreationString()).getVisualOrderText(),
                xVal - ((float) this.textRenderer.width(tardis.stats().getCreationString()) / 2), 35, 0xFFFFFF,
                0x000000, matrices.last().pose(), vertexConsumers, 0xF000F0);
        Component plaqueTypeText = entity.getPlaqueText();
        this.textRenderer.drawInBatch8xOutline(plaqueTypeText.getVisualOrderText(),
                xVal - ((float) this.textRenderer.width(plaqueTypeText) / 2), 55, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty(tardis.stats().getName()).getVisualOrderText(),
                xVal - ((float) this.textRenderer.width(tardis.stats().getName()) / 2), 75, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);

        matrices.popPose();
    }
}
