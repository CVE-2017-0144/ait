package dev.amble.ait.module.planet.client.renderers;

import static dev.amble.ait.client.util.SkyboxUtil.LOOKUP;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpaceSkyRenderer {
    private static final int FACES_COUNT = 6;
    private final ResourceLocation[] faces = new ResourceLocation[6];

    public SpaceSkyRenderer(ResourceLocation faces) {
        for (int i = 0; i < 6; ++i) {
            this.faces[i] = faces.withPath(faces.getPath() + "_" + i + ".png");
        }
    }

    public void draw(Tesselator tessellator, BufferBuilder bufferBuilder, PoseStack matrixStack) {
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();

        for (int k = 0; k < 6; ++k) {
            matrixStack.pushPose();
            Quaternionf rot = LOOKUP[k];

            if (rot != null) {
                matrixStack.mulPose(rot);
            }

            Matrix4f matrix4f = matrixStack.last().pose();

            RenderSystem.setShaderTexture(0, this.faces[k]);
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            int l = 255;
            bufferBuilder.addVertex(matrix4f,-100.0f, -100.0f, -100.0f).setUv(0.0f, 0.0f).setColor(255, 255, 255, l);
            bufferBuilder.addVertex(matrix4f,-100.0f, -100.0f, 100.0f).setUv(0.0f, 1.0f).setColor(255, 255, 255, l);
            bufferBuilder.addVertex(matrix4f,100.0f, -100.0f, 100.0f).setUv(1.0f, 1.0f).setColor(255, 255, 255, l);
            bufferBuilder.addVertex(matrix4f,100.0f, -100.0f, -100.0f).setUv(1.0f, 0.0f).setColor(255, 255, 255, l);
            tessellator.end();
            matrixStack.popPose();
        }
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    /*public CompletableFuture<Void> loadTexturesAsync(TextureManager textureManager, Executor executor) {
        CompletableFuture[] completableFutures = new CompletableFuture[6];
        for (int i = 0; i < completableFutures.length; ++i) {
            completableFutures[i] = textureManager.loadTextureAsync(this.faces[i], executor);
        }
        return CompletableFuture.allOf(completableFutures);
    }*/
}
