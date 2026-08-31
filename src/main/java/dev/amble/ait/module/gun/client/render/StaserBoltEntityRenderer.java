package dev.amble.ait.module.gun.client.render;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.entities.projectiles.StaserBoltEntityModel;
import dev.amble.ait.module.gun.core.entity.StaserBoltEntity;

@OnlyIn(Dist.CLIENT)
public class StaserBoltEntityRenderer
        extends EntityRenderer<StaserBoltEntity> {

    public static final ResourceLocation TEXTURE = AITMod.id("textures/entity/projectiles/staser_bolt.png");
    public StaserBoltEntityModel model = new StaserBoltEntityModel(StaserBoltEntityModel.getTexturedModelData().bakeRoot());

    public StaserBoltEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(StaserBoltEntity entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
        matrices.scale(1.5f, 1.5f, 1.5f);
        matrices.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
        matrices.mulPose(Axis.XN.rotationDegrees(entity.getXRot()));
        matrices.translate(0, -1.125f, 0);
        model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE)), light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(StaserBoltEntity entity) {
        return TEXTURE;
    }
}
