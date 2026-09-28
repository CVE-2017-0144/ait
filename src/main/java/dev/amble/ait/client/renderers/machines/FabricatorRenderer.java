package dev.amble.ait.client.renderers.machines;

import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.machines.FabricatorModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.core.blockentities.FabricatorBlockEntity;
import dev.amble.ait.core.blocks.FabricatorBlock;
import dev.amble.ait.core.item.blueprint.Blueprint;
import org.joml.Vector3f;

public class FabricatorRenderer<T extends FabricatorBlockEntity> implements BlockEntityRenderer<T> {

    public static final ResourceLocation FABRICATOR_TEXTURE = AITMod.id("textures/block/fabricator.png");
    public static final ResourceLocation EMISSIVE_FABRICATOR_TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/block/fabricator_emission.png");
    private final FabricatorModel fabricatorModel;

    public FabricatorRenderer(BlockEntityRendererProvider.Context ctx) {
        this.fabricatorModel = new FabricatorModel(FabricatorModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public void render(FabricatorBlockEntity entity, float tickDelta, PoseStack matrices,
            MultiBufferSource vertexConsumers, int light, int overlay) {
        ProfilerFiller profiler = entity.getLevel().getProfiler();
        profiler.push("fabricator");

        matrices.pushPose();
        matrices.translate(0.5f, 1.5f, 0.5f);

        matrices.mulPose(Axis.XP.rotationDegrees(180));
        matrices.mulPose(Axis.YP
                .rotationDegrees(entity.getBlockState().getValue(FabricatorBlock.FACING).toYRot()));

        this.fabricatorModel.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(FABRICATOR_TEXTURE)), light, overlay, 0xFFFFFFFF);

        if (entity.isValid()) {
            this.fabricatorModel.renderToBuffer(matrices, vertexConsumers.getBuffer(AITRenderLayers.tardisEmissiveCullZOffset(EMISSIVE_FABRICATOR_TEXTURE)), 0xf000f0, overlay, 0xFFFFFFFF);
        }

        matrices.popPose();
        matrices.pushPose();

        ItemStack stack = entity.getShowcaseStack();

        // Apply the same rotation as the block
        matrices.translate(0.5, 1.5, 0.5);
        float rotation = entity.getBlockState().getValue(FabricatorBlock.FACING).toYRot();
        if (entity.getBlockState().getValue(FabricatorBlock.FACING) == Direction.NORTH ||
                entity.getBlockState().getValue(FabricatorBlock.FACING) == Direction.SOUTH) {
            rotation += 180;
        }
        matrices.mulPose(Axis.YP.rotationDegrees(rotation));
        matrices.translate(-0.5, -1.5, -0.5);

        if (!stack.isEmpty()) {
            matrices.pushPose();
            double offset = Math.sin((entity.getLevel().getGameTime() + tickDelta) / 8.0) / 18.0;

            matrices.translate(0.5f, 0.35f + (offset / 2), 0.5f);

            Vector3f scale = Minecraft.getInstance().getItemRenderer().getModel(stack, entity.getLevel(), null, 0).getTransforms().firstPersonRightHand.scale;
            matrices.scale(0.7f, 0.7f, 0.7f);
            matrices.scale(scale.x, scale.y, scale.z);

            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, 0xf000f0,
                    overlay, matrices, vertexConsumers, entity.getLevel(), 0);
            matrices.popPose();
        }
        renderText(entity, tickDelta, matrices, vertexConsumers, light, overlay);

        matrices.popPose();
        profiler.pop();
    }

    private void renderText(FabricatorBlockEntity entity, float tickDelta, PoseStack matrices,
                            MultiBufferSource vertexConsumers, int light, int overlay) {
        Font renderer = Minecraft.getInstance().font;
        matrices.pushPose();

        matrices.translate(0.93, 0.1255, 0.315);
        matrices.mulPose(Axis.YP.rotationDegrees(180f));
        matrices.mulPose(Axis.XP.rotationDegrees(90f));
        matrices.scale(0.005f, 0.005f, 0.005f);

        // display "COLLECT OUTPUT" if enough materials
        Component text = Component.translatable("block.ait.fabricator.status.collect_output");

        // if does not have blueprint, text is "INSERT BLUEPRINT"
        if (!entity.hasBlueprint()) {
            text = Component.translatable("block.ait.fabricator.status.insert_blueprint");
        }

        Blueprint print = entity.getBlueprint().orElse(null);
        ItemStack stack = entity.getShowcaseStack();
        // display "INSERT (COUNT) MATERIAL" if not enough materials
        if (print != null && !print.isComplete()) {
            String material = Component.translatable(stack.getDescriptionId()).getString().toUpperCase(Locale.ROOT);
            text = Component.translatable("block.ait.fabricator.status.insert_material", print.getCountLeftFor(stack),
                    material);
        }

        renderer.drawInBatch8xOutline(text.getVisualOrderText(), 0, 40, 0x60eaf0, 0x108fb3,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        matrices.popPose();
    }
}
