package dev.amble.ait.client.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.models.exteriors.ExteriorModel;
import dev.amble.ait.client.models.exteriors.SiegeModeModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.blocks.ExteriorBlock;
import dev.amble.ait.core.entities.FallingTardisEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.TardisExterior;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.amble.ait.registry.impl.exterior.ClientExteriorVariantRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.RotationSegment;

public class FallingTardisRenderer extends EntityRenderer<FallingTardisEntity> {

    public FallingTardisRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(FallingTardisEntity entity, float yaw, float tickDelta, PoseStack matrices,
            MultiBufferSource vertexConsumers, int light) {
        Tardis tardis = entity.tardis().get();

        if (tardis == null)
            return;

        TardisExterior tardisExterior = tardis.getExterior();
        ClientExteriorVariantSchema exteriorVariant = tardisExterior.getVariant().getClient();

        if (exteriorVariant == null)
            return;

        if (Minecraft.getInstance().player == null)
            return;

        ResourceLocation texture = exteriorVariant.texture();
        ResourceLocation emission = exteriorVariant.emission();
        ExteriorModel model = exteriorVariant.getCachedModel();

        if (model == null)
            return;

        matrices.pushPose();
        int k = entity.getBlockState().getValue(ExteriorBlock.ROTATION);
        float h = RotationSegment.convertToDegrees(k);

        matrices.mulPose(
                Axis.YN.rotationDegrees(!exteriorVariant.equals(ClientExteriorVariantRegistry.DOOM)
                        ? 180f + h
                        : Minecraft.getInstance().player.getYHeadRot() + 180f));
        matrices.mulPose(Axis.XP.rotationDegrees(180f));

        boolean siege = tardis.siege().isActive();

        if (siege) {
            model = new SiegeModeModel(SiegeModeModel.getTexturedModelData().bakeRoot());
            texture = tardis.siege().texture().get();
        }

        model.renderEntity(entity, model.root(), matrices,
                vertexConsumers.getBuffer(AITRenderLayers.entityTranslucentCull(texture)), light,
                OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        if (siege) {
            matrices.popPose();
            return;
        }

        if (emission != null)
            model.renderEntity(entity, model.root(), matrices,
                    vertexConsumers.getBuffer(DependencyChecker.hasIris() ? AITRenderLayers.tardisEmissiveCullZOffset(emission) : AITRenderLayers.text(emission)),
                    0xf000f0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        if (!exteriorVariant.equals(ClientExteriorVariantRegistry.CORAL_GROWTH)) {
            BiomeHandler handler = tardis.handler(TardisComponent.Id.BIOME);
            ResourceLocation biomeTexture = handler.getBiomeKey().get(exteriorVariant.overrides());

            if (biomeTexture != null && !exteriorVariant.texture().equals(biomeTexture)) {
                model.renderEntity(entity, model.root(), matrices,
                        vertexConsumers.getBuffer(AITRenderLayers.entityTranslucentCull(biomeTexture)), light,
                        OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
        }

        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(FallingTardisEntity entity) {
        if (entity.tardis().get() == null)
            return TextureAtlas.LOCATION_BLOCKS; // random texture just so i dont crash

        return entity.tardis().get().getExterior().getVariant().getClient().texture();
    }
}
