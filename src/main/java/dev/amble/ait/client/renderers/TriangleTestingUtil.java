package dev.amble.ait.client.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import org.joml.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;

public class TriangleTestingUtil {

    public static void renderTriangle(WorldRenderContext context) {
        Camera camera = context.camera();

        Vec3 targetPosition = new Vec3(-67, 67, 108);
        Vec3 transformedPosition = targetPosition.subtract(camera.getPosition());

        PoseStack matrixStack = new PoseStack();
        matrixStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        matrixStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));
        matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);
        matrixStack.scale(1f, 8f, 1f);

        Matrix4f positionMatrix = matrixStack.last().pose();
        Tesselator tessellator = Tesselator.getInstance();

        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
        for (int i = 0; i < 6; ++i) {
            matrixStack.rotateAround(
                    Axis.YP.rotationDegrees(i * ((i > 1f && i < 3f) || i == 4 ? 120f : 60F)), 0, 0, 0);
            buffer.addVertex(positionMatrix, -0.5f, 1, -0.865625f).setColor(1f, 1f, 1f, 1f).setUv(0f, 0f);
            buffer.addVertex(positionMatrix, 0, 0, /*-0.865625f*/ 0).setColor(1f, 0f, 0f, 1f).setUv(0f, 1f);
            buffer.addVertex(positionMatrix, 0.5f, 1, -0.865625f).setColor(0f, 0f, 1f, 1f).setUv(1f, 0f);
        }

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableCull();

        BufferUploader.drawWithShader(buffer.buildOrThrow());

        RenderSystem.enableCull();
    }
}
