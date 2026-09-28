package dev.amble.ait.client.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.amble.ait.core.tardis.vortex.reference.VortexReference;
import dev.amble.ait.core.tardis.vortex.reference.VortexReferenceRegistry;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class VortexRender {
    private static VortexRender INSTANCE;

    public ResourceLocation texture;
    public ResourceLocation secondLayer;
    public ResourceLocation thirdLayer;
    private final float distortionSpeed;
    private final float distortionSeparationFactor;
    private final float distortionFactor;
    private final float scale;
    private float speed = 4;
    private float time = 0;

    public VortexRender(ResourceLocation texture) {
        replaceWith(texture);
        this.distortionSpeed = 0.5f;
        this.distortionSeparationFactor = 32f;
        this.distortionFactor = 2;
        this.scale = 32f;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Get the singleton instance of the VortexRender updated for the given reference.
     */
    public static VortexRender getInstance(VortexReference ref) {
        if (INSTANCE == null) {
            INSTANCE = new VortexRender(ref.texture());
        } else if (!INSTANCE.isFor(ref.texture())) {
            INSTANCE.replaceWith(ref.texture());
        }
        return INSTANCE;
    }

    /**
     * Get the instance of the renderer with the last reference seen.
     */
    public static VortexRender getCurrentInstance() {
        if (INSTANCE == null) INSTANCE = new VortexRender(VortexReferenceRegistry.getInstance().getRandom().texture());
        return INSTANCE;
    }

    public boolean isFor(ResourceLocation texture) {
        return this.texture.equals(texture);
    }

    public void replaceWith(ResourceLocation texture) {
        this.texture = texture;
        secondLayer = ResourceLocation.fromNamespaceAndPath(texture.getNamespace(), texture.getPath().substring(0, texture.getPath().length() - 4) +
                "_second" + ".png");
        thirdLayer = ResourceLocation.fromNamespaceAndPath(texture.getNamespace(), texture.getPath().substring(0, texture.getPath().length() - 4) +
                "_third" + ".png");
    }

    public void render(PoseStack matrixStack) {

        time = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) + Minecraft.getInstance().player.tickCount;

        this.renderLayer(matrixStack, 1.0F, texture);
        this.renderLayer(matrixStack, 1.5f);
        this.renderLayer(matrixStack, 2.5f);
    }

    public void renderLayer(PoseStack matrixStack, float scaleFactor) {
        ResourceLocation currentTexture = scaleFactor == 1.5f ? secondLayer : thirdLayer;
        if (Minecraft.getInstance().getResourceManager().getResource(currentTexture).isEmpty()) return;
        this.renderLayer(matrixStack, scaleFactor, currentTexture);
    }

    private void renderLayer(PoseStack matrixStack, float scaleFactor, ResourceLocation layer) {

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getRendertypeBeaconBeamShader);
        RenderSystem.setShaderTexture(0, layer);

        matrixStack.pushPose();

        matrixStack.scale(scale / scaleFactor, scale / scaleFactor, scale);

        Minecraft.getInstance().getTextureManager().bindForSetup(layer);
        Tesselator tessellator = Tesselator.getInstance();

        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);

        for (int i = 0; i < 32; ++i) {
            this.renderSection(buffer, i,(Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) + Minecraft.getInstance().player.tickCount) / (120 / this.speed), (float) Math.sin(i * Math.PI / 32),
                    (float) Math.sin((i + 1) * Math.PI / 32), matrixStack.last(), matrixStack.last().pose());
        }

        BufferUploader.drawWithShader(buffer.buildOrThrow());
        matrixStack.popPose();

        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }

    public void renderSection(VertexConsumer builder, int zOffset, float textureDistanceOffset, float startScale,
            float endScale, PoseStack.Pose pose, Matrix4f matrix4f) {
        float panel = 1/6f;
        float sqrt = (float) Math.sqrt(3) / 2.0f;
        int vOffset = (zOffset * panel + textureDistanceOffset > 1.0) ? zOffset - 6 : zOffset;
        float distortion = this.computeDistortionFactor(time, zOffset);
        float distortionPlusOne = this.computeDistortionFactor(time, zOffset + 1);
        float panelDistanceOffset = panel + textureDistanceOffset;
        float vPanelOffset = (vOffset * panel) + textureDistanceOffset;

        int uOffset = 0;

        float uPanelOffset = uOffset * panel;

        addVertex(builder, pose, matrix4f, 0f, -startScale + distortion, -zOffset, uPanelOffset, vPanelOffset);

        addVertex(builder, pose, matrix4f, 0f, -endScale + distortionPlusOne, -zOffset - 1, uPanelOffset,
                vOffset * panel + panelDistanceOffset);

        addVertex(builder, pose, matrix4f, endScale * -sqrt, endScale / -2f + distortionPlusOne, -zOffset - 1,
                uPanelOffset + panel, vOffset * panel + panelDistanceOffset);

        addVertex(builder, pose, matrix4f, startScale * -sqrt, startScale / -2f + distortion, -zOffset, uPanelOffset + panel,
                vPanelOffset);

        uOffset = 1;

        uPanelOffset = uOffset * panel;

        addVertex(builder, pose, matrix4f, startScale * -sqrt, startScale / -2f + distortion, -zOffset, uPanelOffset,
                vPanelOffset);

        addVertex(builder, pose, matrix4f, endScale * -sqrt, endScale / -2f + distortionPlusOne, -zOffset - 1, uPanelOffset,
                vOffset * panel + panelDistanceOffset);

        addVertex(builder, pose, matrix4f, endScale * -sqrt, endScale / 2f + distortionPlusOne, -zOffset - 1,
                uPanelOffset + panel, vOffset * panel + panelDistanceOffset);

        addVertex(builder, pose, matrix4f, startScale * -sqrt, startScale / 2f + distortion, -zOffset, uPanelOffset + panel,
                vPanelOffset);

        uOffset = 2;

        uPanelOffset = uOffset * panel;

        addVertex(builder, pose, matrix4f, 0f, endScale + distortionPlusOne, -zOffset - 1, uPanelOffset + panel,
                vOffset * panel + panelDistanceOffset);

        addVertex(builder, pose, matrix4f, 0f, startScale + distortion, -zOffset, uPanelOffset + panel, vPanelOffset);

        addVertex(builder, pose, matrix4f, startScale * -sqrt, startScale / 2f + distortion, -zOffset, uPanelOffset,
                vPanelOffset);

        addVertex(builder, pose, matrix4f, endScale * -sqrt, endScale / 2f + distortionPlusOne, -zOffset - 1, uPanelOffset,
                vOffset * panel + panelDistanceOffset);

        uOffset = 3;

        uPanelOffset = uOffset * panel;

        addVertex(builder, pose, matrix4f, 0f, startScale + distortion, -zOffset, uPanelOffset, vPanelOffset);

        addVertex(builder, pose, matrix4f, 0f, endScale + distortionPlusOne, -zOffset - 1, uPanelOffset,
                vOffset * panel + panelDistanceOffset);

        addVertex(builder, pose, matrix4f, endScale * sqrt, (endScale / 2f + distortionPlusOne), -zOffset - 1,
                uPanelOffset + panel, vOffset * panel + panelDistanceOffset);

        addVertex(builder, pose, matrix4f, startScale * sqrt, (startScale / 2f + distortion), -zOffset, uPanelOffset + panel,
                vPanelOffset);

        uOffset = 4;

        uPanelOffset = uOffset * panel;

        addVertex(builder, pose, matrix4f, startScale * sqrt, (startScale / 2f + distortion), -zOffset, uPanelOffset,
                vPanelOffset);

        addVertex(builder, pose, matrix4f, endScale * sqrt, endScale / 2f + distortionPlusOne, -zOffset - 1, uPanelOffset,
                vOffset * panel + panelDistanceOffset);

        addVertex(builder, pose, matrix4f, endScale * sqrt, endScale / -2f + distortionPlusOne, -zOffset - 1,
                uPanelOffset + panel, vOffset * panel + panelDistanceOffset);

        addVertex(builder, pose, matrix4f, startScale * sqrt, startScale / -2f + distortion, -zOffset, uPanelOffset + panel,
                vPanelOffset);

        uOffset = 5;

        uPanelOffset = uOffset * panel;

        addVertex(builder, pose, matrix4f, 0f, -endScale + distortionPlusOne, -zOffset - 1, uPanelOffset + panel,
                vOffset * panel + panelDistanceOffset);

        addVertex(builder, pose, matrix4f, 0f, -startScale + distortion, -zOffset, uPanelOffset + panel, vPanelOffset);

        addVertex(builder, pose, matrix4f, startScale * sqrt, startScale / -2f + distortion, -zOffset, uPanelOffset,
                vPanelOffset);

        addVertex(builder, pose, matrix4f, endScale * sqrt, endScale / -2f + distortionPlusOne, -zOffset - 1, uPanelOffset,
                vOffset * panel + panelDistanceOffset);
    }

    private void addVertex(VertexConsumer builder, PoseStack.Pose pose, Matrix4f matrix, float x, float y, float z, float u, float v) {
        builder.addVertex(matrix, x, y, z).setColor(1, 1, 1, 1f).setUv(u, v).setLight(0xF000F0).setNormal(pose, 0, 0.0f, 0);
    }

    private float computeDistortionFactor(float time, int t) {
        return (float) (Math.sin(time * this.distortionSpeed * 0.1 * Math.PI + (13 - t) *
        this.distortionSeparationFactor) * this.distortionFactor) / 6;
    }
}
