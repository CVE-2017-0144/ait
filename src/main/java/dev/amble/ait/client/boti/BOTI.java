package dev.amble.ait.client.boti;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexSorting;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.blockentities.DoorBlockEntity;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.entities.BOTIPaintingEntity;
import dev.amble.ait.core.entities.RiftEntity;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class BOTI {
    public static final Minecraft client = Minecraft.getInstance();
    public static final Collection<RiftEntity> RIFT_RENDERING_QUEUE = new LinkedList<>();
    public static BOTIInit BOTI_HANDLER = new BOTIInit();
    public static AITBufferBuilderStorage AIT_BUF_BUILDER_STORAGE = new AITBufferBuilderStorage();
    public static Queue<DoorBlockEntity> DOOR_RENDER_QUEUE = new LinkedList<>();
    public static Queue<BOTIPaintingEntity> GALLIFREYAN_RENDER_QUEUE = new LinkedList<>();
    public static Queue<BOTIPaintingEntity> TRENZALORE_PAINTING_QUEUE = new LinkedList<>();
    public static Queue<ExteriorBlockEntity> EXTERIOR_RENDER_QUEUE = new LinkedList<>();
    public static final Map<UUID, ExteriorBlockEntity> LAST_RENDERED_EXTERIOR = new HashMap<>();
    private static boolean HAS_BEEN_WARNED = false;

    public static int currentDrawFbo() {
        return GL11.glGetInteger(GL30.GL_DRAW_FRAMEBUFFER_BINDING);
    }

    public static void copyFramebuffer(RenderTarget src, RenderTarget dest) {
        copyColor(src, dest);
        copyDepth(src, dest);
    }

    public static void copyColor(RenderTarget src, RenderTarget dest) {
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height, 0, 0, dest.width, dest.height, GlConst.GL_COLOR_BUFFER_BIT, GlConst.GL_NEAREST);
    }

    public static ShaderInstance COPY_DEPTH_PROGRAM;

    public static void copyDepth(RenderTarget src, RenderTarget dest) {
        if (!Minecraft.ON_OSX || COPY_DEPTH_PROGRAM == null || src.getDepthTextureId() <= 0) {
            blitDepth(src, dest);
            return;
        }

        dest.bindWrite(true);

        RenderSystem.colorMask(false, false, false, false);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        RenderSystem.disableCull();

        Matrix4f prevProjection = new Matrix4f(RenderSystem.getProjectionMatrix());
        VertexSorting prevSorter = RenderSystem.getVertexSorting();
        RenderSystem.setProjectionMatrix(IDENTITY_MATRIX, VertexSorting.DISTANCE_TO_ORIGIN);
        Matrix4fStack modelView = RenderSystem.getModelViewStack();
        modelView.pushMatrix();
        modelView.identity();
        RenderSystem.applyModelViewMatrix();

        RenderSystem.setShaderTexture(0, src.getDepthTextureId());
        RenderSystem.setShader(() -> COPY_DEPTH_PROGRAM);

        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        builder.addVertex(-1.0f, -1.0f, 0.0f).setUv(0.0f, 0.0f);
        builder.addVertex(1.0f, -1.0f, 0.0f).setUv(1.0f, 0.0f);
        builder.addVertex(1.0f, 1.0f, 0.0f).setUv(1.0f, 1.0f);
        builder.addVertex(-1.0f, 1.0f, 0.0f).setUv(0.0f, 1.0f);
        BufferUploader.drawWithShader(builder.buildOrThrow());

        modelView.popMatrix();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setProjectionMatrix(prevProjection, prevSorter);

        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
    }

    private static void blitDepth(RenderTarget src, RenderTarget dest) {
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height, 0, 0, dest.width, dest.height, GlConst.GL_DEPTH_BUFFER_BIT, GlConst.GL_NEAREST);
        GL11.glGetError();
    }

    public static void copyColorToFbo(RenderTarget src, int destFbo, int w, int h) {
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, destFbo);
        GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height,
                0, 0, w, h, GlConst.GL_COLOR_BUFFER_BIT, GlConst.GL_NEAREST);
    }

    public static void copyColorFromFbo(int srcFbo, int w, int h, RenderTarget dest) {
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, srcFbo);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, w, h,
                0, 0, dest.width, dest.height, GlConst.GL_COLOR_BUFFER_BIT, GlConst.GL_NEAREST);
    }

    public static void copyFramebufferFromFbo(int srcFbo, int w, int h, RenderTarget dest) {
        copyColorFromFbo(srcFbo, w, h, dest);
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, srcFbo);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, w, h,
                0, 0, dest.width, dest.height, GlConst.GL_DEPTH_BUFFER_BIT, GlConst.GL_NEAREST);
        GL11.glGetError();
    }

    public static void copyDepthToFbo(RenderTarget src, int destFbo, int w, int h) {
        if (!Minecraft.ON_OSX || COPY_DEPTH_PROGRAM == null || src.getDepthTextureId() <= 0) {
            GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
            GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, destFbo);
            GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height,
                    0, 0, w, h, GlConst.GL_DEPTH_BUFFER_BIT, GlConst.GL_NEAREST);
            GL11.glGetError();
            return;
        }

        GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, destFbo);
        RenderSystem.viewport(0, 0, w, h);

        RenderSystem.colorMask(false, false, false, false);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        RenderSystem.disableCull();

        Matrix4f prevProjection = new Matrix4f(RenderSystem.getProjectionMatrix());
        VertexSorting prevSorter = RenderSystem.getVertexSorting();
        RenderSystem.setProjectionMatrix(IDENTITY_MATRIX, VertexSorting.DISTANCE_TO_ORIGIN);
        Matrix4fStack modelView = RenderSystem.getModelViewStack();
        modelView.pushMatrix();
        modelView.identity();
        RenderSystem.applyModelViewMatrix();

        RenderSystem.setShaderTexture(0, src.getDepthTextureId());
        RenderSystem.setShader(() -> COPY_DEPTH_PROGRAM);

        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        builder.addVertex(-1.0f, -1.0f, 0.0f).setUv(0.0f, 0.0f);
        builder.addVertex(1.0f, -1.0f, 0.0f).setUv(1.0f, 0.0f);
        builder.addVertex(1.0f, 1.0f, 0.0f).setUv(1.0f, 1.0f);
        builder.addVertex(-1.0f, 1.0f, 0.0f).setUv(0.0f, 1.0f);
        BufferUploader.drawWithShader(builder.buildOrThrow());

        modelView.popMatrix();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setProjectionMatrix(prevProjection, prevSorter);

        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
    }

    public static final class BotiCompositeState {
        int drawFbo;
        final int[] viewport = new int[4];
        boolean stencilEnabled;
        int stencilFunc, stencilRef, stencilValueMask, stencilWriteMask, stencilFail, stencilZFail, stencilZPass;
        boolean depthMask;
    }

    public static BotiCompositeState beginBotiComposite() {
        BotiCompositeState s = new BotiCompositeState();
        s.drawFbo = currentDrawFbo();
        GL11.glGetIntegerv(GL11.GL_VIEWPORT, s.viewport);
        // immersive portals clips its portal views with this, it has to come back as it was
        s.stencilEnabled = GL11.glIsEnabled(GL11.GL_STENCIL_TEST);
        s.stencilFunc = GL11.glGetInteger(GL11.GL_STENCIL_FUNC);
        s.stencilRef = GL11.glGetInteger(GL11.GL_STENCIL_REF);
        s.stencilValueMask = GL11.glGetInteger(GL11.GL_STENCIL_VALUE_MASK);
        s.stencilWriteMask = GL11.glGetInteger(GL11.GL_STENCIL_WRITEMASK);
        s.stencilFail = GL11.glGetInteger(GL11.GL_STENCIL_FAIL);
        s.stencilZFail = GL11.glGetInteger(GL11.GL_STENCIL_PASS_DEPTH_FAIL);
        s.stencilZPass = GL11.glGetInteger(GL11.GL_STENCIL_PASS_DEPTH_PASS);
        s.depthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
        return s;
    }

    public static void endBotiComposite(BotiCompositeState s) {
        GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, s.drawFbo);
        RenderSystem.viewport(s.viewport[0], s.viewport[1], s.viewport[2], s.viewport[3]);
        GL11.glStencilMask(s.stencilWriteMask);
        GL11.glStencilFunc(s.stencilFunc, s.stencilRef, s.stencilValueMask);
        GL11.glStencilOp(s.stencilFail, s.stencilZFail, s.stencilZPass);
        if (s.stencilEnabled)
            GL11.glEnable(GL11.GL_STENCIL_TEST);
        else
            GL11.glDisable(GL11.GL_STENCIL_TEST);
        RenderSystem.depthMask(s.depthMask);
        RenderSystem.enableCull();
    }

    public static void setFramebufferColor(RenderTarget src, float r, float g, float b, float a) {
        src.setClearColor(r, g, b, a);
    }

    private static final Matrix4f IDENTITY_MATRIX = new Matrix4f();

    public static void resetStencilByDraw() {
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 0, 0xFF);
        GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
        drawFullscreenQuad(false, false);
    }

    public static void resetDepthByDraw() {
        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 0, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
        drawFullscreenQuad(false, true);
    }

    public static void clearDepthInStencilRegion() {
        drawFullscreenQuad(false, true);
    }

    public static void writeNearDepthInStencilRegion() {
        drawFullscreenQuad(false, true, 0.0);
    }

    public static void blitInStencilRegion(RenderTarget src) {
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.disableCull();

        Matrix4f prevProjection = new Matrix4f(RenderSystem.getProjectionMatrix());
        VertexSorting prevSorter = RenderSystem.getVertexSorting();
        RenderSystem.setProjectionMatrix(IDENTITY_MATRIX, VertexSorting.DISTANCE_TO_ORIGIN);
        Matrix4fStack modelView = RenderSystem.getModelViewStack();
        modelView.pushMatrix();
        modelView.identity();
        RenderSystem.applyModelViewMatrix();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, src.getColorTextureId());
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        builder.addVertex(-1.0f, -1.0f, 0.0f).setUv(0.0f, 0.0f);
        builder.addVertex(1.0f, -1.0f, 0.0f).setUv(1.0f, 0.0f);
        builder.addVertex(1.0f, 1.0f, 0.0f).setUv(1.0f, 1.0f);
        builder.addVertex(-1.0f, 1.0f, 0.0f).setUv(0.0f, 1.0f);
        BufferUploader.drawWithShader(builder.buildOrThrow());

        modelView.popMatrix();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setProjectionMatrix(prevProjection, prevSorter);

        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
    }

    public static void fillColorInStencilRegion(float r, float g, float b) {
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthMask(false);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        RenderSystem.disableCull();

        Matrix4f prevProjection = new Matrix4f(RenderSystem.getProjectionMatrix());
        VertexSorting prevSorter = RenderSystem.getVertexSorting();
        RenderSystem.setProjectionMatrix(IDENTITY_MATRIX, VertexSorting.DISTANCE_TO_ORIGIN);
        Matrix4fStack modelView = RenderSystem.getModelViewStack();
        modelView.pushMatrix();
        modelView.identity();
        RenderSystem.applyModelViewMatrix();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        builder.addVertex(-1.0f, -1.0f, 0.0f).setColor(r, g, b, 1f);
        builder.addVertex(1.0f, -1.0f, 0.0f).setColor(r, g, b, 1f);
        builder.addVertex(1.0f, 1.0f, 0.0f).setColor(r, g, b, 1f);
        builder.addVertex(-1.0f, 1.0f, 0.0f).setColor(r, g, b, 1f);
        BufferUploader.drawWithShader(builder.buildOrThrow());

        modelView.popMatrix();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setProjectionMatrix(prevProjection, prevSorter);

        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
    }

    private static void drawFullscreenQuad(boolean writeColor, boolean writeDepth) {
        drawFullscreenQuad(writeColor, writeDepth, 1.0);
    }

    private static void drawFullscreenQuad(boolean writeColor, boolean writeDepth, double depthValue) {
        RenderSystem.colorMask(writeColor, writeColor, writeColor, writeColor);
        RenderSystem.depthMask(writeDepth);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        RenderSystem.disableCull();

        if (writeDepth)
            GL11.glDepthRange(depthValue, depthValue);

        Matrix4f prevProjection = new Matrix4f(RenderSystem.getProjectionMatrix());
        VertexSorting prevSorter = RenderSystem.getVertexSorting();
        RenderSystem.setProjectionMatrix(IDENTITY_MATRIX, VertexSorting.DISTANCE_TO_ORIGIN);

        Matrix4fStack modelView = RenderSystem.getModelViewStack();
        modelView.pushMatrix();
        modelView.identity();
        RenderSystem.applyModelViewMatrix();

        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        builder.addVertex(-1.0f, -1.0f, 0.0f);
        builder.addVertex(1.0f, -1.0f, 0.0f);
        builder.addVertex(1.0f, 1.0f, 0.0f);
        builder.addVertex(-1.0f, 1.0f, 0.0f);
        BufferUploader.drawWithShader(builder.buildOrThrow());

        modelView.popMatrix();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setProjectionMatrix(prevProjection, prevSorter);

        if (writeDepth)
            GL11.glDepthRange(0.0, 1.0);

        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
    }

    public static void tryWarn(Minecraft client) {
        if (HAS_BEEN_WARNED)
            return;

        if (warn(client)) AITModClient.CONFIG.enableTardisBOTI = false;

        HAS_BEEN_WARNED = true;
    }

    private static boolean warn(Minecraft client) {
        if (DependencyChecker.hasIndium())
            return false;

        return false;
    }
}
