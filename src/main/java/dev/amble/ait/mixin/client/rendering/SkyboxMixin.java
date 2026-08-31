package dev.amble.ait.mixin.client.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.api.tardis.TardisClientEvents;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.client.util.SkyboxUtil;
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.world.TardisServerWorld;

@Mixin(LevelRenderer.class)
public abstract class SkyboxMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    protected abstract void renderEndSky(PoseStack matrices);

    @Shadow
    private @Nullable ClientLevel level;

    @Shadow
    private @Nullable VertexBuffer skyBuffer;

    @Shadow
    @Final
    private static ResourceLocation SUN_LOCATION;

    @Shadow
    @Final
    private static ResourceLocation MOON_LOCATION;

    @Shadow
    private @Nullable VertexBuffer starBuffer;

    @Shadow
    private @Nullable VertexBuffer darkBuffer;

    @Shadow
    public abstract void renderLevel(PoseStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline,
                                Camera camera, GameRenderer gameRenderer, LightTexture lightmapTextureManager,
                                Matrix4f projectionMatrix);

    @Shadow protected abstract void createStars();

    @Unique private static WorldRenderContext context;
    @Unique private boolean needsSkyboxReinit = false;

    static {
        TardisClientEvents.ENTER_CLIENT_TARDIS.register(tardis -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.levelRenderer != null) {
                SkyboxMixin mixin = (SkyboxMixin) (Object) mc.levelRenderer;
                mixin.needsSkyboxReinit = true;
            }
        });
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void init(CallbackInfo ci) {
        WorldRenderEvents.AFTER_SETUP.register(ctx -> context = ctx);
    }

    @Unique private static void ait$applySkyboxRotation(PoseStack matrices, float yaw, float pitch) {
        if (yaw != 0f) {
            matrices.mulPose(Axis.YP.rotationDegrees(yaw));
        }
        if (pitch != 0f) {
            matrices.mulPose(Axis.XP.rotationDegrees(pitch));
        }
    }

    @Inject(method = "renderSky(Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V", at = @At("HEAD"), cancellable = true)
    public void ait$renderSky(PoseStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera,
                              boolean thickFog, Runnable fogCallback, CallbackInfo ci) {
        if (this.level == null)
            return;

        if (this.needsSkyboxReinit && ClientTardisUtil.getCurrentTardis() != null) {
            this.needsSkyboxReinit = false;
        }

        if (TardisServerWorld.isTardisDimension(this.level)) {
            this.renderSkyDynamically(matrices, projectionMatrix, tickDelta, camera, fogCallback, ci);
            this.level.getProfiler().popPush("projector");
        }

        if (this.level.dimension() == AITDimensions.TIME_VORTEX_WORLD) {
            SkyboxUtil.renderVortexSky(matrices);
            ci.cancel();
        }

        if (this.level.dimension() == AITDimensions.MOON) {
            SkyboxUtil.renderMoonSky(matrices, fogCallback, this.starBuffer, level, tickDelta, projectionMatrix);
            ci.cancel();
        }

        if (this.level.dimension() == AITDimensions.MARS) {
            SkyboxUtil.renderMarsSky(matrices, fogCallback, this.starBuffer, level, tickDelta, projectionMatrix, ci);
        }

        if (this.level.dimension() == AITDimensions.SPACE) {
            SkyboxUtil.renderSpaceSky(false, matrices, fogCallback, this.starBuffer, level, tickDelta, projectionMatrix);
            ci.cancel();
        }
    }

    @Unique private void renderSkyDynamically(PoseStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera,
                                              Runnable fogCallback, CallbackInfo ci) {
        if (!AITModClient.CONFIG.environmentProjector || context == null) {
            SkyboxUtil.renderTardisSky(matrices);
            ci.cancel();

            return;
        }

        if (this.level == null)
            return;

        Tardis tardis = ClientTardisUtil.getCurrentTardis();

        if (tardis == null || tardis.stats() == null || tardis.stats().skybox() == null)
            return;

        ResourceKey<Level> skyboxWorld = tardis.stats().skybox().get();
        float skyboxYaw = tardis.stats().skyboxYaw().get();
        float skyboxPitch = tardis.stats().skyboxPitch().get();

        if (skyboxWorld == Level.OVERWORLD) {
            matrices.pushPose();
            ait$applySkyboxRotation(matrices, skyboxYaw, skyboxPitch);
            this.renderOverworldSky(matrices, projectionMatrix, tickDelta, camera, fogCallback);
            matrices.popPose();

            ci.cancel();
            return;
        }

        if (skyboxWorld == Level.END) {
            matrices.pushPose();
            ait$applySkyboxRotation(matrices, skyboxYaw, skyboxPitch);
            this.renderEndSky(matrices);
            matrices.popPose();
            ci.cancel();
            return;
        }

        if (skyboxWorld == Level.NETHER) {
            //this.renderEndPortalEffect(matrices, projectionMatrix, tickDelta, camera, fogCallback);
            // I will do this later I really don't care
            SkyboxUtil.renderTardisSky(matrices);
            ci.cancel();
            return;
        }

        if (skyboxWorld == AITDimensions.SPACE) {
            matrices.pushPose();
            ait$applySkyboxRotation(matrices, skyboxYaw, skyboxPitch);
            SkyboxUtil.renderSpaceSky(true, matrices, fogCallback, this.starBuffer, level, tickDelta, projectionMatrix);
            matrices.popPose();
            ci.cancel();
            return;
        }

        if (skyboxWorld == AITDimensions.MOON) {
            matrices.pushPose();
            ait$applySkyboxRotation(matrices, skyboxYaw, skyboxPitch);
            SkyboxUtil.renderMoonSky(0f, matrices, fogCallback, this.starBuffer, level, tickDelta, projectionMatrix);
            matrices.popPose();
            ci.cancel();
            return;
        }

        if (skyboxWorld == AITDimensions.MARS) {
            matrices.pushPose();
            ait$applySkyboxRotation(matrices, skyboxYaw, skyboxPitch);
            SkyboxUtil.renderMarsSky(matrices, fogCallback, this.starBuffer, level, tickDelta, projectionMatrix, ci);
            matrices.popPose();
            return;
        }

        if (skyboxWorld == AITDimensions.TIME_VORTEX_WORLD) {
            matrices.pushPose();
            ait$applySkyboxRotation(matrices, skyboxYaw, skyboxPitch);
            SkyboxUtil.renderVortexSky(matrices, tardis);
            matrices.popPose();
            ci.cancel();
            return;
        }

        DimensionRenderingRegistry.SkyRenderer renderer = DimensionRenderingRegistry.getSkyRenderer(skyboxWorld);

        if (renderer != null) {
            renderer.render(context);
            ci.cancel();
        }
    }

    @Unique private void renderOverworldSky(PoseStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera,
                                            Runnable fogCallback) {
        float q;
        float p;
        float o;
        int m;
        float k;
        float i;

        Vec3 vec3d = level.getSkyColor(camera.getPosition(), tickDelta);

        float f = (float) vec3d.x;
        float g = (float) vec3d.y;
        float h = (float) vec3d.z;

        FogRenderer.levelFogColor();

        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(f, g, h, 1.0f);

        ShaderInstance shaderProgram = RenderSystem.getShader();

        this.skyBuffer.bind();
        this.skyBuffer.drawWithShader(matrices.last().pose(), projectionMatrix, shaderProgram);

        VertexBuffer.unbind();
        RenderSystem.enableBlend();

        float[] fs = level.effects().getSunriseColor(level.getTimeOfDay(tickDelta), tickDelta);

        if (fs != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

            matrices.pushPose();
            matrices.mulPose(Axis.XP.rotationDegrees(90.0f));

            i = Mth.sin(level.getSunAngle(tickDelta)) < 0.0f ? 180.0f : 0.0f;

            matrices.mulPose(Axis.ZP.rotationDegrees(i));
            matrices.mulPose(Axis.ZP.rotationDegrees(90.0f));

            float j = fs[0];
            k = fs[1];
            float l = fs[2];

            Matrix4f matrix4f = matrices.last().pose();
            BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
            bufferBuilder.addVertex(matrix4f, 0.0f, 100.0f, 0.0f).setColor(j, k, l, fs[3]);

            for (int n = 0; n <= 16; n++) {
                o = (float) n * ((float) Math.PI * 2) / 16.0f;
                p = Mth.sin(o);
                q = Mth.cos(o);

                bufferBuilder.addVertex(matrix4f, p * 120.0f, q * 120.0f, -q * 40.0f * fs[3])
                        .setColor(fs[0], fs[1], fs[2], 0.0f);
            }

            BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
            matrices.popPose();
        }

        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        matrices.pushPose();
        i = 1.0f - level.getRainLevel(tickDelta);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, i);
        matrices.mulPose(Axis.YP.rotationDegrees(-90.0f));
        matrices.mulPose(Axis.XP.rotationDegrees(level.getTimeOfDay(tickDelta) * 360.0f));

        Matrix4f matrix4f2 = matrices.last().pose();

        k = 30.0f;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SUN_LOCATION);

        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.addVertex(matrix4f2, -k, 100.0f, -k).setUv(0.0f, 0.0f);
        bufferBuilder.addVertex(matrix4f2, k, 100.0f, -k).setUv(1.0f, 0.0f);
        bufferBuilder.addVertex(matrix4f2, k, 100.0f, k).setUv(1.0f, 1.0f);
        bufferBuilder.addVertex(matrix4f2, -k, 100.0f, k).setUv(0.0f, 1.0f);

        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
        k = 20.0f;

        RenderSystem.setShaderTexture(0, MOON_LOCATION);
        int r = level.getMoonPhase();
        int s = r % 4;
        m = r / 4 % 2;

        float t = (float) (s) / 4.0f;
        o = (float) (m) / 2.0f;
        p = (float) (s + 1) / 4.0f;
        q = (float) (m + 1) / 2.0f;

        bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.addVertex(matrix4f2, -k, -100.0f, k).setUv(p, q);
        bufferBuilder.addVertex(matrix4f2, k, -100.0f, k).setUv(t, q);
        bufferBuilder.addVertex(matrix4f2, k, -100.0f, -k).setUv(t, o);
        bufferBuilder.addVertex(matrix4f2, -k, -100.0f, -k).setUv(p, o);

        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
        float u = level.getStarBrightness(tickDelta) * i;

        if (u > 0.0f) {
            RenderSystem.setShaderColor(u, u, u, u);
            FogRenderer.setupNoFog();

            this.starBuffer.bind();
            this.starBuffer.drawWithShader(matrices.last().pose(), projectionMatrix,
                    GameRenderer.getPositionShader());

            VertexBuffer.unbind();
            fogCallback.run();
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        matrices.popPose();

        RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 1.0f);
        double d = this.minecraft.player.getEyePosition(tickDelta).y
                - this.level.getLevelData().getHorizonHeight(this.level);

        if (d < 0.0) {
            matrices.pushPose();
            matrices.translate(0.0f, 12.0f, 0.0f);

            this.darkBuffer.bind();
            this.darkBuffer.drawWithShader(matrices.last().pose(), projectionMatrix, shaderProgram);

            VertexBuffer.unbind();
            matrices.popPose();
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.depthMask(true);
    }

    @Unique private void renderEndPortalEffect(PoseStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera,
                                               Runnable fogCallback) {

        float q;
        float p;
        float o;
        float k;
        float i;
        fogCallback.run();
        Tesselator tessellator = Tesselator.getInstance();
        Vec3 vec3d = level.getSkyColor(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition(), tickDelta);
        float f = (float)vec3d.x;
        float g = (float)vec3d.y;
        float h = (float)vec3d.z;
        FogRenderer.levelFogColor();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(f, g, h, 1.0f);
        VertexBuffer.unbind();
        RenderSystem.enableBlend();
        float[] fs = level.effects().getSunriseColor(level.getTimeOfDay(tickDelta), tickDelta);
        if (fs != null) {
            RenderSystem.setShader(GameRenderer::getRendertypeEndGatewayShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            matrices.pushPose();
            matrices.mulPose(Axis.XP.rotationDegrees(90.0f));
            i = Mth.sin(level.getSunAngle(tickDelta)) < 0.0f ? 180.0f : 0.0f;
            matrices.mulPose(Axis.ZP.rotationDegrees(i));
            matrices.mulPose(Axis.ZP.rotationDegrees(90.0f));
            float j = fs[0];
            k = fs[1];
            float l = fs[2];
            Matrix4f matrix4f = matrices.last().pose();
            BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
            bufferBuilder.addVertex(matrix4f, 0.0f, 100.0f, 0.0f).setColor(j, k, l, fs[3]);
            int m = 16;
            for (int n = 0; n <= 16; ++n) {
                o = (float)n * ((float)Math.PI * 2) / 16.0f;
                p = Mth.sin(o);
                q = Mth.cos(o);
                bufferBuilder.addVertex(matrix4f, p * 120.0f, q * 120.0f, -q * 40.0f * fs[3]).setColor(fs[0], fs[1], fs[2], 0.0f);
            }
            BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
            matrices.popPose();
        }
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.depthMask(true);
    }
}