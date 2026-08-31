package dev.amble.ait.module.planet.client.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.math.Axis;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.module.planet.client.models.CelestialBodyModel;


public class CelestialBodyRenderer {

    public static void renderFarAwayBody(Vec3 targetPosition, Vector3f scale, ResourceLocation texture, boolean hasClouds, boolean hasAtmosphere, Vector3f atmosphereColor) {
        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();
        MultiBufferSource.BufferSource provider = mc.renderBuffers().bufferSource();

        Vec3 cameraPos = camera.getPosition();

        Vec3 targetPos = new Vec3(camera.getPosition().x() + targetPosition.x(),
                camera.getPosition().y() + targetPosition.y(),
                camera.getPosition().z() + targetPosition.z());

        Vec3 diff = targetPos.subtract(cameraPos);

        PoseStack matrixStack = new PoseStack();
        matrixStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        matrixStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));
        matrixStack.translate(diff.x, diff.y, diff.z);
        matrixStack.scale(scale.x, scale.y, scale.z);

        FogRenderer.setupNoFog();
        //RenderSystem.depthFunc(GL11.GL_NOTEQUAL);
        RenderSystem.setProjectionMatrix(matrixStack.last().pose().perspective(90, 1, 0.05f, 10000000), VertexSorting.ORTHOGRAPHIC_Z);

        CelestialBodyModel.getTexturedModelData().bakeRoot().render(matrixStack,
                provider.getBuffer(AITRenderLayers.beaconBeam(texture, false)),
                0xf000f0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1f);
        provider.endBatch();

        if (hasAtmosphere) {
            atmosphereRenderer(matrixStack, atmosphereColor, provider, false, hasClouds);
            provider.endBatch();
        }
        provider.endBatch();
        RenderSystem.restoreProjectionMatrix();
        //RenderSystem.depthFunc(GL11.GL_EQUAL);
    }

    public static void renderStarBody(boolean isTardisSkybox, Vec3 targetPosition, Vector3f scale, Vector3f rotation, ResourceLocation texture, boolean hasAtmosphere, Vector3f atmosphereColor) {
        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();
        MultiBufferSource.BufferSource provider = mc.renderBuffers().bufferSource();

        Vec3 cameraPos = camera.getPosition();

        Vec3 targetPos = new Vec3(targetPosition.x(),targetPosition.y(),targetPosition.z());

        Vec3 diff = targetPos.subtract(cameraPos);

        PoseStack matrixStack = new PoseStack();

        if (mc.level == null)
            return;

        matrixStack.pushPose();

        matrixStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        matrixStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));
        if (isTardisSkybox) {
            matrixStack.mulPose(Axis.ZP.rotationDegrees(mc.level.getTimeOfDay(mc.getFrameTime()) * 360.0f));
            matrixStack.translate(0, -4000, 0);
            matrixStack.scale(0.25f, 0.25f, 0.25f);
        }
        matrixStack.translate(diff.x, diff.y, diff.z);
        matrixStack.scale(scale.x, scale.y, scale.z);

        FogRenderer.setupNoFog();


        matrixStack.mulPose(Axis.YP.rotationDegrees(rotation.y()));
        matrixStack.mulPose(Axis.XP.rotationDegrees(rotation.x()));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(rotation.z()));

        CelestialBodyModel model = new CelestialBodyModel(CelestialBodyModel.getTexturedModelData().bakeRoot());

        RenderSystem.depthMask(true);

        //RenderSystem.setShaderColor(atmosphereColor.x + 0.25f, atmosphereColor.y + 0.25f, atmosphereColor.z + 0.25f, 1f);
        model.renderToBuffer(matrixStack,
                provider.getBuffer(AITRenderLayers.beaconBeam(texture, false)),
                0xf000f00, OverlayTexture.NO_OVERLAY, 1 - atmosphereColor.x, 1 - atmosphereColor.y, 1 - atmosphereColor.z, 1f);
        provider.endBatch();
        //RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        if (hasAtmosphere) {
            atmosphereRenderer(matrixStack, atmosphereColor, provider, true,false);
            provider.endBatch();
        }
        //RenderSystem.depthFunc(GL11.GL_EQUAL);
        matrixStack.popPose();
    }

    public static void renderComprehendableBody(boolean isTardisSkybox, Vec3 targetPosition, Vector3f scale, Vector3f rotation, ResourceLocation texture, boolean isSkyRendered, boolean hasClouds, boolean hasAtmosphere, Vector3f atmosphereColor, boolean hasRings) {
        renderComprehendableBody(0, isTardisSkybox, targetPosition, scale, rotation, texture, isSkyRendered, hasClouds, hasAtmosphere, atmosphereColor, hasRings);
    }

    public static void renderComprehendableBody(float skyboxRot, boolean isTardisSkybox, Vec3 targetPosition, Vector3f scale, Vector3f rotation, ResourceLocation texture, boolean isSkyRendered, boolean hasClouds, boolean hasAtmosphere, Vector3f atmosphereColor, boolean hasRings) {
        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();
        MultiBufferSource.BufferSource provider = mc.renderBuffers().bufferSource();

        Vec3 cameraPos = camera.getPosition();

        Vec3 targetPos = new Vec3(targetPosition.x(),targetPosition.y(),targetPosition.z());

        Vec3 diff = targetPos.subtract(cameraPos);

        PoseStack matrixStack = new PoseStack();
        matrixStack.pushPose();
        matrixStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        matrixStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F + skyboxRot));
        if (isTardisSkybox) {
            matrixStack.translate(0, 4000, 0);
            matrixStack.scale(0.25f, 0.25f, 0.25f);
        }
        matrixStack.translate(diff.x, diff.y, diff.z);
        matrixStack.scale(scale.x, scale.y, scale.z);

        FogRenderer.setupNoFog();
        //RenderSystem.depthMask(true);
        if (isSkyRendered) {
            RenderSystem.depthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthFunc(GL11.GL_ALWAYS);
        }

        matrixStack.mulPose(Axis.YP.rotationDegrees(rotation.y()));
        matrixStack.mulPose(Axis.XP.rotationDegrees(180 + rotation.x()));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(rotation.z()));

        CelestialBodyModel celestialBodyModel = new CelestialBodyModel(CelestialBodyModel.getTexturedModelData().bakeRoot());
        celestialBodyModel.renderToBuffer(matrixStack,
                provider.getBuffer(AITRenderLayers.entityNoOutline(texture)),
                0xf000f0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1f);
        if (hasRings) {
            celestialBodyModel.ring.render(matrixStack,
                    provider.getBuffer(AITRenderLayers.entityNoOutline(texture)),
                    0xf000f0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1f);
        }
        provider.endBatch();

        if (hasAtmosphere) {
            atmosphereRenderer(matrixStack, atmosphereColor, provider, false, hasClouds);
            provider.endBatch();
        }

        if (isSkyRendered) {
            RenderSystem.depthMask(false);
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
        }
        matrixStack.popPose();
    }

    public static void atmosphereRenderer(PoseStack matrixStack, Vector3f color, MultiBufferSource.BufferSource provider, boolean isStar, boolean hasClouds) {
        CelestialBodyModel model = new CelestialBodyModel(CelestialBodyModel.getTexturedModelData().bakeRoot());
        for (int i = 0; i < 6; i++) {
            float alpha = (float) (0.1f - Math.log(i + 1) * 0.001f);
            matrixStack.pushPose();
            float gg = 1.0f + ((i != 0 ? i : i + 1) * 0.025f);
            matrixStack.scale(gg, gg, gg);
            RenderType renderLayer = AITRenderLayers.itemEntityTranslucentCull(new ResourceLocation("textures/environment/clouds.png"));//RenderLayer.getEnergySwirl(new Identifier("textures/environment/clouds.png"), delta % 1.0F, (delta * 0.1F) % 1.0F);
            ResourceLocation texture = AITMod.id("textures/environment/atmosphere.png");
            if (i != 1) {
                model.renderToBuffer(matrixStack,
                        provider.getBuffer(isStar && (i == 2 || i == 3 || i == 4) ?
                                AITRenderLayers.eyes(texture) : AITRenderLayers.itemEntityTranslucentCull(texture)),
                        15728864, OverlayTexture.NO_OVERLAY,  1 + Math.min(color.x + (0.015f * i), 5.0f), 1 + Math.min(color.y + (0.015f * i), 5.0f), 1 + Math.min(color.z + (0.015f * i), 5.0f), -1 + alpha);
            } else if (hasClouds) {
                model.renderToBuffer(matrixStack,
                        provider.getBuffer(renderLayer),
                        15728864, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1F);
                matrixStack.scale(1.01f, 1.01f, 1.01f);
                model.renderToBuffer(matrixStack,
                        provider.getBuffer(renderLayer),
                        15728864, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1F);
            }
            matrixStack.popPose();
        }
    }
}
