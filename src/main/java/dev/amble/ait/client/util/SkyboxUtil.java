package dev.amble.ait.client.util;

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
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.renderers.VortexRender;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.module.planet.client.renderers.CelestialBodyRenderer;
import dev.amble.ait.module.planet.client.renderers.SpaceSkyRenderer;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRenderInfo;
import dev.amble.ait.module.planet.core.space.system.SolarSystem;
import dev.amble.ait.module.planet.core.space.system.Space;



public class SkyboxUtil extends LevelRenderer {
    private static final ResourceLocation TARDIS_SKY = AITMod.id("textures/environment/tardis_sky.png");
    private static final ResourceLocation SUN = AITMod.id("textures/environment/tardis_star.png");

    public static final Quaternionf[] LOOKUP = new Quaternionf[]{null, Axis.XP.rotationDegrees(90.0f),
            Axis.XP.rotationDegrees(-90.0f), Axis.XP.rotationDegrees(180.0f),
            Axis.ZP.rotationDegrees(90.0f), Axis.ZP.rotationDegrees(-90.0f), null};

    public SkyboxUtil(Minecraft client, EntityRenderDispatcher entityRenderDispatcher, BlockEntityRenderDispatcher blockEntityRenderDispatcher, RenderBuffers bufferBuilders) {
        super(client, entityRenderDispatcher, blockEntityRenderDispatcher, bufferBuilders);
    }

    public static void renderVortexSky(PoseStack matrices, Tardis tardis) {
        VortexRender util = tardis.stats().getVortexEffects().toRender();
        matrices.pushPose();
        float scale = 100f;
        float zOffset = 500 * scale;
        float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) + Minecraft.getInstance().player.tickCount;
        if (!tardis.travel().autopilot() && tardis.travel().getState() != TravelHandlerBase.State.LANDED)
            matrices.mulPose(Axis.YN.rotationDegrees((delta) * (tardis.travel().speed() * 0.7f)));
        if (!tardis.crash().isNormal())
            matrices.mulPose(Axis.XP.rotationDegrees((delta)));
        matrices.mulPose(Axis.ZP.rotationDegrees((delta) * (tardis.travel().speed() + 1)));
        matrices.translate(0, 0, zOffset);
        matrices.scale(scale, scale, scale);

        util.setSpeed(tardis.travel().speed() < 1 ? 4 : tardis.travel().speed());
        util.render(matrices);
        matrices.popPose();
    }

    public static void renderVortexSky(PoseStack matrices) {
        VortexRender util = VortexRender.getCurrentInstance();
        matrices.pushPose();
        float scale = 100f;
        float zOffset = 500 * scale;
        float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) + Minecraft.getInstance().player.tickCount;
        matrices.mulPose(Axis.YN.rotationDegrees(delta));
        matrices.mulPose(Axis.XP.rotationDegrees(delta));
        matrices.mulPose(Axis.ZP.rotationDegrees(delta));
        matrices.translate(0, 0, zOffset);
        matrices.scale(scale, scale, scale);

        util.setSpeed(4);
        util.render(matrices);
        matrices.popPose();
    }

    public static void renderTardisSky(PoseStack matrices) {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);

        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, TARDIS_SKY);

        Tesselator tessellator = Tesselator.getInstance();

        for (int i = 0; i < 6; i++) {
            matrices.pushPose();

            Quaternionf rot = LOOKUP[i];

            if (rot != null) {
                matrices.mulPose(rot);
            }

            Matrix4f matrix4f = matrices.last().pose();
            BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

            bufferBuilder.addVertex(matrix4f, -100.0f, -100.0f, -100.0f).setUv(0.0f, 0.0f).setColor(40, 40, 40, 255);

            bufferBuilder.addVertex(matrix4f, -100.0f, -100.0f, 100.0f).setUv(0.0f, 16.0f).setColor(40, 40, 40, 255);

            bufferBuilder.addVertex(matrix4f, 100.0f, -100.0f, 100.0f).setUv(16.0f, 16.0f).setColor(40, 40, 40, 255);

            bufferBuilder.addVertex(matrix4f, 100.0f, -100.0f, -100.0f).setUv(16.0f, 0.0f).setColor(40, 40, 40, 255);

            BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
            matrices.popPose();
        }

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }
    public static void renderMoonSky(PoseStack matrices, Runnable fogCallback, VertexBuffer starsBuffer, ClientLevel world, float tickDelta, Matrix4f projectionMatrix) {
        renderMoonSky(0, matrices, fogCallback, starsBuffer, world, tickDelta, projectionMatrix);
    }
    public static void renderMoonSky(float tallDrinkOfWater, PoseStack matrices, Runnable fogCallback, VertexBuffer starsBuffer, ClientLevel world, float tickDelta, Matrix4f projectionMatrix) {
        Tesselator tessellator = Tesselator.getInstance();

        matrices.pushPose();
        matrices.mulPose(Axis.ZP.rotationDegrees(-405f));
        matrices.mulPose(Axis.XP.rotationDegrees(world.getTimeOfDay(tickDelta) * 360.0f + 300f));
        matrices.scale(100, 100, 100);

        drawSpace(tessellator, matrices);

        matrices.pushPose();
        matrices.mulPose(Axis.YP.rotationDegrees(90.0f));
        matrices.mulPose(Axis.XP.rotationDegrees(world.getTimeOfDay(tickDelta) * 360.0f));

        RenderSystem.setShaderColor(0.5f, 0.5f, 0.5f, 1);
        FogRenderer.setupNoFog();

        starsBuffer.bind();
        starsBuffer.drawWithShader(matrices.last().pose(), projectionMatrix,
                GameRenderer.getPositionShader());

        VertexBuffer.unbind();
        fogCallback.run();

        RenderSystem.depthMask(false);
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
        matrices.popPose();
        matrices.popPose();

        // Planet Rendering
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        matrices.pushPose();
        renderStarBody(false, matrices, SUN,
                new Vec3(cameraPos.x() + 270, cameraPos.y() - 120, cameraPos.z() + 0), new
                        Vector3f(12f, 12f, 12f),
                new Vector3f(12, 45, 0),
                true,
                new Vector3f(0.3f, 0.15f, 0.01f));

        renderSkyBody(tallDrinkOfWater + (Direction.fromYRot(tallDrinkOfWater).equals(Direction.WEST) || Direction.fromYRot(tallDrinkOfWater).equals(Direction.EAST) ? -90f : 90f), false, matrices, AITMod.id("textures/environment/earth.png"),
                new Vec3(cameraPos.x() - 530, cameraPos.y() + 40, cameraPos.z() + 10), new
                        Vector3f(76f, 76f, 76f),
                new Vector3f(-22.5f, 45f, 0), true, true,
                new Vector3f(0.18f, 0.35f, 0.60f));
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(GL11.GL_LESS);
        matrices.popPose();
    }

    public static void renderMarsSky(PoseStack matrices, Runnable fogCallback, VertexBuffer starsBuffer, ClientLevel world, float tickDelta, Matrix4f projectionMatrix, CallbackInfo ci) {
        float q;
        float p;
        float o;
        float k;
        float i;
        fogCallback.run();
        Tesselator tessellator = Tesselator.getInstance();
        Vec3 vec3d = world.getSkyColor(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition(), tickDelta);
        float f = (float)vec3d.x;
        float g = (float)vec3d.y;
        float h = (float)vec3d.z;
        FogRenderer.levelFogColor();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(f, g, h, 1.0f);
        VertexBuffer.unbind();
        RenderSystem.enableBlend();
        float[] fs = world.effects().getSunriseColor(world.getTimeOfDay(tickDelta), tickDelta);
        if (fs != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            matrices.pushPose();
            matrices.mulPose(Axis.XP.rotationDegrees(90.0f));
            i = Mth.sin(world.getSunAngle(tickDelta)) < 0.0f ? 180.0f : 0.0f;
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
        matrices.pushPose();
        matrices.mulPose(Axis.ZP.rotationDegrees(-405f));
        matrices.mulPose(Axis.XP.rotationDegrees(world.getTimeOfDay(tickDelta) * 360.0f + 300f));
        matrices.scale(100, 100, 100);

        if (fs != null)
            RenderSystem.setShaderColor(fs[0] + 0.45f, fs[1] + 0.45f, fs[2] + 0.45f, 0.1f);
        else
            RenderSystem.setShaderColor(0.8f, 1.0f, 1.0f, 0.1f);
        SpaceSkyRenderer cubeMap = new SpaceSkyRenderer(AITMod.id("textures/environment/space_sky/panorama"));
        cubeMap.draw(tessellator, matrices);
        RenderSystem.setShaderColor(1, 1, 1, 1f);

        matrices.pushPose();
        matrices.mulPose(Axis.YP.rotationDegrees(-90.0f));
        //matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(world.getSkyAngle(tickDelta) * 360.0f));
        FogRenderer.setupNoFog();

        starsBuffer.bind();
        starsBuffer.drawWithShader(matrices.last().pose(), projectionMatrix,
                GameRenderer.getPositionShader());

        VertexBuffer.unbind();
        fogCallback.run();

        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        RenderSystem.defaultBlendFunc();
        matrices.popPose();
        RenderSystem.setShaderColor(1, 1, 1, 1f);

        matrices.popPose();
        matrices.pushPose();
        i = 1.0f;
        if (fs != null)
            RenderSystem.setShaderColor(fs[0] + 0.45f, fs[1] + 0.45f, fs[2] + 0.45f, i);
        else
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, i);
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        renderStarBody(false, matrices, SUN,
                new Vec3(cameraPos.x(), cameraPos.y() + 250, cameraPos.z() + 0), new
                        Vector3f(6f, 6f, 6f),
                new Vector3f(45, 12, 12), false,
                new Vector3f(0f, 0.03f, 0.03f));
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        matrices.popPose();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.depthMask(true);
        ci.cancel();
    }

    public static void renderSpaceSky(boolean isTardisSkybox, PoseStack matrices, Runnable fogCallback, VertexBuffer starsBuffer, ClientLevel world, float tickDelta, Matrix4f projectionMatrix) {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        Tesselator tessellator = Tesselator.getInstance();

        matrices.pushPose();
        matrices.mulPose(Axis.ZP.rotationDegrees(-405f));
        matrices.mulPose(Axis.XP.rotationDegrees(300f));
        matrices.scale(100, 100, 100);

        drawSpace(tessellator, matrices);

        RenderSystem.depthMask(false);
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        matrices.pushPose();
        matrices.mulPose(Axis.YP.rotationDegrees(-90.0f));
        matrices.mulPose(Axis.XP.rotationDegrees(world.getTimeOfDay(tickDelta) * 360.0f));

        RenderSystem.setShaderColor(0.5f, 0.5f, 0.5f, 1);
        FogRenderer.setupNoFog();

        starsBuffer.bind();
        starsBuffer.drawWithShader(matrices.last().pose(), projectionMatrix,
                GameRenderer.getPositionShader());

        VertexBuffer.unbind();
        fogCallback.run();

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.defaultBlendFunc();
        matrices.popPose();
        RenderSystem.depthFunc(GL11.GL_EQUAL);
        matrices.popPose();

        // Planet Rendering todo - move info into PlanetRenderInfo !!!
        renderStarBody(isTardisSkybox, matrices, SUN,
                new Vec3(0, 1000, 0),
                new Vector3f(1000f, 1000f, 1000f),
                new Vector3f(12, 45, 0),
                true,
                new Vector3f(0.1f, 0.05f, 0.0f));


        for (SolarSystem system : Space.getInstance().systems) {
            RenderSystem.depthMask(true);
            RenderSystem.depthFunc(GL11.GL_LESS);
            for (Planet planet : system) {
                PlanetRenderInfo render = planet.render();
                if (render.isEmpty()) continue;

                renderCelestialBody(isTardisSkybox, matrices, render.texture(), render.position(), render.scale(), render.rotation(), render.clouds(), render.atmosphere(), render.color(), render.hasRings());
            }
        }

        RenderSystem.setShaderColor(1, 1, 1, 1);

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    /**
     * Renders a celestial body in space.
     */

    private static void renderSkyBody(boolean isTardisSkybox, PoseStack matrices, ResourceLocation texture, Vec3 position, Vector3f scale, Vector3f rotation, boolean clouds, boolean atmosphere, Vector3f color) {
        matrices.pushPose();
        CelestialBodyRenderer.renderComprehendableBody(isTardisSkybox, position, scale, rotation, texture, true, clouds, atmosphere, color, false);
        matrices.popPose();
    }

    private static void renderSkyBody(float rotationOf, boolean isTardisSkybox, PoseStack matrices, ResourceLocation texture, Vec3 position, Vector3f scale, Vector3f rotation, boolean clouds, boolean atmosphere, Vector3f color) {
        matrices.pushPose();
        CelestialBodyRenderer.renderComprehendableBody(rotationOf, isTardisSkybox, position, scale, rotation, texture, true, clouds, atmosphere, color, false);
        matrices.popPose();
    }

    private static void renderCelestialBody(boolean isTardisSkybox, PoseStack matrices, ResourceLocation texture, Vec3 position, Vector3f scale, Vector3f rotation, boolean clouds, boolean atmosphere, Vector3f color, boolean hasRings) {
        matrices.pushPose();
        CelestialBodyRenderer.renderComprehendableBody(isTardisSkybox, position, scale, rotation, texture, false, clouds, atmosphere, color, hasRings);
        matrices.popPose();
    }

    private static void renderStarBody(boolean isTardisSkybox, PoseStack matrices, ResourceLocation texture, Vec3 position, Vector3f scale, Vector3f rotation, boolean atmosphere, Vector3f color) {
        matrices.pushPose();
        CelestialBodyRenderer.renderStarBody(isTardisSkybox, position, scale, rotation, texture, atmosphere, color);
        matrices.popPose();
    }

    private static void drawSpace(Tesselator tessellator, PoseStack matrices) {
        RenderSystem.setShaderColor(0.25f, 0.25f, 0.25f, 1);
        SpaceSkyRenderer cubeMap = new SpaceSkyRenderer(AITMod.id("textures/environment/space_sky/panorama"));
        cubeMap.draw(tessellator, matrices);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1);
    }
}
