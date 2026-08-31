package dev.amble.ait.client.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.models.exteriors.ExteriorModel;
import dev.amble.ait.client.models.machines.ShieldsModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.client.renderers.VortexRender;
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.entities.FlightTardisEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.phys.Vec3;

public class FlightTardisRenderer extends EntityRenderer<FlightTardisEntity> {

    private ExteriorModel model;
    private ClientExteriorVariantSchema variant;

    public FlightTardisRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(FlightTardisEntity entity, float yaw, float tickDelta, PoseStack matrices,
            MultiBufferSource vertexConsumers, int light) {
        if (!entity.isLinked())
            return;

        Tardis tardis = entity.tardis().get();

        if (tardis == null) return;

        this.updateModel(tardis);

        if (entity.getControllingPassenger() == null ||
                !(entity.getControllingPassenger() instanceof AbstractClientPlayer player)) return;

        if (player.getVehicle() == null || player.getVehicle() != entity) return;

        Vec3 vec3d = entity.getViewVector(tickDelta);
        Vec3 vec3d2 = entity.lerpVelocity(tickDelta);

        double d = vec3d2.horizontalDistanceSqr();
        double e = vec3d.horizontalDistanceSqr();

        matrices.pushPose();
        if (tardis.door().isClosed() && !entity.verticalCollisionBelow)
            matrices.translate(0, 0.25f * -vec3d2.y(), 0);

        if (tardis.travel().position().getDimension() == AITDimensions.TIME_VORTEX_WORLD) {
            VortexRender vortexRender = tardis.stats().getVortexEffects().toRender();
            matrices.pushPose();
            matrices.mulPose(Axis.ZP.rotationDegrees((float) Minecraft.getInstance().player.tickCount / 100 * 360f));
            matrices.translate(0, 0, 500);
            vortexRender.render(matrices);
            matrices.popPose();
        }

        if (d > 0.0 && e > 0.0) {
            double l = (vec3d2.x * vec3d.x + vec3d2.z * vec3d.z) / Math.sqrt(d * e);
            double m = vec3d2.x * vec3d.z - vec3d2.z * vec3d.x;
            double v = Math.signum(m) * Math.acos(l);
            matrices.mulPose(Axis.YP.rotation((float) v));
        }

        boolean doorsClosed = tardis.door().isClosed();
        float deg = doorsClosed ? (float) (d * 22.5f) : (float) -d * 22.5f;

        if (!entity.verticalCollision && !doorsClosed) {
            this.model.root().setRotation((float) 0, 0, 0);
            matrices.mulPose(Axis.YP.rotationDegrees(180f));
        } else if (!entity.verticalCollision) {
            this.model.root().setRotation((float) 0, ((entity.getRotation(tickDelta)) * tardis.travel().speed()), 0);
        }

        /*if (!entity.verticalCollision)
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) (2f * Math.cos(0.2f * (tickDelta + entity.age))) + deg));*/

        matrices.mulPose(Axis.ZP.rotationDegrees(entity.verticalCollision ? 180f : (float) (2f * Math.sin(0.2f * (tickDelta + entity.tickCount)) + 180f)));

        this.model.renderEntity(entity, this.model.root(), matrices, vertexConsumers.getBuffer(AITRenderLayers.entityTranslucentCull(getTextureLocation(entity))), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        if (variant.emission() != null && tardis.fuel().hasPower()) {
            boolean alarms = tardis.alarm().isEnabled();

            float color = alarms ? 0.3f : 1f;

            model.renderEntity(entity, this.model.root(), matrices, vertexConsumers.getBuffer(AITRenderLayers.tardisEmissiveCullZOffset(variant.emission(), true)), 0xf000f0, OverlayTexture.NO_OVERLAY, color, color, color, 1);
        }

        BiomeHandler biome = tardis.handler(TardisComponent.Id.BIOME);
        ResourceLocation biomeTexture = biome.getBiomeKey().get(variant.overrides());

        if (biomeTexture != null && !this.getTextureLocation(entity).equals(biomeTexture))
            model.renderEntity(entity, this.model.root(), matrices, vertexConsumers.getBuffer(AITRenderLayers.entityTranslucentCull(biomeTexture)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        int maxLight = 0xF000F0;

        matrices.popPose();
        if (tardis.areVisualShieldsActive()) {
            matrices.pushPose();

            float delta = ((tickDelta + entity.tickCount) * 0.03f);
            ShieldsModel shieldsModel = new ShieldsModel(ShieldsModel.getTexturedModelData().bakeRoot());
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.energySwirl(ResourceLocation.parse("textures/misc/forcefield.png"), delta % 1.0F, (delta * 0.1F) % 1.0F));
            shieldsModel.renderToBuffer(matrices, vertexConsumer, maxLight, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(1f, 0f, 0.25f, 0.5f));
            matrices.popPose();
        }
    }

    private ExteriorModel getModel(Tardis tardis) {
        if (model == null)
            model = tardis.getExterior().getVariant().getClient().getCachedModel();

        return model;
    }

    @Override
    public ResourceLocation getTextureLocation(FlightTardisEntity entity) {
        if (!entity.isLinked())
            return TextureAtlas.LOCATION_BLOCKS; // random texture just so i dont crash

        return entity.tardis().get().getExterior().getVariant().getClient().texture();
    }

    private void updateModel(Tardis tardis) {
        ClientExteriorVariantSchema variant = tardis.getExterior().getVariant().getClient();

        if (this.variant != variant) {
            this.variant = variant;
            this.model = variant.getCachedModel();
        }
    }
}
