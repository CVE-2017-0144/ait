package dev.amble.ait.client.renderers.decoration;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.decoration.SnowGlobeModel;
import dev.amble.ait.client.models.exteriors.ExteriorModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.core.blockentities.SnowGlobeBlockEntity;
import dev.amble.ait.core.blocks.SnowGlobeBlock;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.amble.ait.registry.impl.exterior.ClientExteriorVariantRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SnowGlobeRenderer<T extends SnowGlobeBlockEntity> implements BlockEntityRenderer<T> {
    public static final ResourceLocation SNOW_GLOBE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/blockentities/decoration/advent/snow_globe.png");

    public static final SnowGlobeModel model = new SnowGlobeModel(SnowGlobeModel.getTexturedModelData().bakeRoot());

    public SnowGlobeRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(SnowGlobeBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();
        matrices.translate(0.5, 1.5f, 0.5);
        matrices.mulPose(Axis.XP.rotationDegrees(180));
        float k = entity.getBlockState().getValue(SnowGlobeBlock.FACING).toYRot();
        matrices.mulPose(Axis.YP.rotationDegrees(k));
        model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityCutoutNoCull(SNOW_GLOBE_TEXTURE)), light, overlay, 0xFFFFFFFF);
        matrices.pushPose();
        matrices.translate(0.17, 1.17, -0.2);
        matrices.mulPose(Axis.YP.rotationDegrees(225f));
        matrices.scale(0.055f, 0.055f, 0.055f);
        ClientExteriorVariantSchema schema = ClientExteriorVariantRegistry.BOX_DEFAULT;
        ExteriorModel model = schema.model();

        model.render(matrices, vertexConsumers.getBuffer(AITRenderLayers.entityTranslucentCull(schema.texture())), light, overlay, 0xFFFFFFFF);
        model.render(matrices, vertexConsumers.getBuffer(AITRenderLayers.tardisEmissiveCullZOffset(schema.emission(), true)), 0xf000f0, overlay, 0xFFFFFFFF);
        model.render(matrices, vertexConsumers.getBuffer(AITRenderLayers.entityCutoutNoCullZOffset(schema.overrides().get(BiomeHandler.BiomeType.SNOWY), false)), light, overlay, 0xFFFFFFFF);

        matrices.popPose();
        matrices.popPose();
    }
}
