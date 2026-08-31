package dev.amble.ait.client.renderers.entities;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.boti.BOTI;
import dev.amble.ait.core.entities.RiftEntity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public class RiftEntityRenderer extends EntityRenderer<RiftEntity> {

    public static final ResourceLocation RIFT_TEXTURE = AITMod.id("textures/entity/rift/rift.png");
    public static final ResourceLocation CIRCLE_TEXTURE = AITMod.id("textures/entity/rift/circle_rift.png");

    public RiftEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(RiftEntity riftEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        if (AITModClient.CONFIG.enableTardisBOTI && !DependencyChecker.hasPortals()) {
            BOTI.RIFT_RENDERING_QUEUE.add(riftEntity);
            return;
        }

        matrixStack.pushPose();
        matrixStack.mulPose(Axis.YP.rotationDegrees(riftEntity.getYRot() + 180));
        matrixStack.translate(0, 0.3, 0);
        matrixStack.scale(1, 1, 1);

        renderCircleQuad(
                matrixStack,
                vertexConsumerProvider.getBuffer(RenderType.endGateway()),
                0xf000f0,
                OverlayTexture.NO_OVERLAY,
                1, 1, 1, 1,
                4.5f
        );

        // The endGateway renderLayer culls the backface so rendering it twice is fine
        matrixStack.mulPose(Axis.YP.rotationDegrees(180f));
        renderCircleQuad(
                matrixStack,
                vertexConsumerProvider.getBuffer(RenderType.endGateway()),
                0xf000f0,
                OverlayTexture.NO_OVERLAY,
                1, 1, 1.0f, 1.0f,
                4.5f
        );

        matrixStack.popPose();
    }

    private static void renderCircleQuad(PoseStack matrixStack, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha, float size) {
        PoseStack.Pose entry = matrixStack.last();
        Matrix4f positionMatrix = entry.pose();
        Matrix3f normalMatrix = entry.normal();

        float half = size / 2.0f;

        vertexConsumer.addVertex(positionMatrix, -half, -half, 0).setColor(red, green, blue, alpha).setUv(0.0f, 0.5f).setOverlay(overlay).setLight(light).setNormal(normalMatrix, 0.0f, 0.0f, 1.0f);
        vertexConsumer.addVertex(positionMatrix, half, -half, 0).setColor(red, green, blue, alpha).setUv(0.5f, 0.5f).setOverlay(overlay).setLight(light).setNormal(normalMatrix, 0.0f, 0.0f, 1.0f);
        vertexConsumer.addVertex(positionMatrix, half, half, 0).setColor(red, green, blue, alpha).setUv(0.5f, 0.0f).setOverlay(overlay).setLight(light).setNormal(normalMatrix, 0.0f, 0.0f, 1.0f);
        vertexConsumer.addVertex(positionMatrix, -half, half, 0).setColor(red, green, blue, alpha).setUv(0.0f, 0.0f).setOverlay(overlay).setLight(light).setNormal(normalMatrix, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(RiftEntity entity) {
        return null;
    }
}
