package dev.amble.ait.client.boti;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.models.AnimatedModel;
import dev.amble.ait.client.models.boti.BotiPortalModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.client.renderers.VortexRender;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.blockentities.DoorBlockEntity;
import dev.amble.ait.core.tardis.handler.StatsHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.registry.impl.CategoryRegistry;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.loqor.portal.client.PortalData;
import dev.loqor.portal.client.PortalDataManager;
import dev.loqor.portal.client.WorldGeometryRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

public class TardisDoorBOTI extends BOTI {
    public static void drawDoorApertureMask(ClientTardis tardis, DoorBlockEntity door, PoseStack stack) {
        drawDoorApertureMask(tardis, door, stack, false);
    }

    public static void drawDoorApertureMask(ClientTardis tardis, DoorBlockEntity door, PoseStack stack, boolean writeDepth) {
        ClientExteriorVariantSchema variant = tardis.getExterior().getVariant().getClient();
        ExteriorVariantSchema parent = variant.parent();
        Vector3f scale = tardis.travel().getScale();

        Vec3 vec = parent.door().getPortalPosition();
        if (vec == null) vec = Vec3.ZERO;

        RenderSystem.colorMask(false, false, false, false);
        RenderSystem.depthMask(writeDepth);
        if (writeDepth) {
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(GL11.GL_ALWAYS);
        }

        MultiBufferSource.BufferSource maskProvider = AIT_BUF_BUILDER_STORAGE.getBotiVertexConsumer();
        ModelPart maskPart = BotiPortalModel.getTexturedModelData().bakeRoot();

        stack.pushPose();
        stack.translate(vec.x, -vec.y - parent.portalHeight() / 2f, vec.z);
        stack.scale((float) parent.portalWidth() * scale.x(),
                (float) parent.portalHeight() * scale.y(), scale.z());
        maskPart.render(stack, maskProvider.getBuffer(RenderType.debugFilledBox()),
                0xf000f0, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        maskProvider.endBatch();
        stack.popPose();

        if (writeDepth)
            RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthMask(true);
    }

    public static void renderInteriorDoorBoti(ClientTardis tardis, DoorBlockEntity door, ClientExteriorVariantSchema variant, PoseStack stack, ResourceLocation frameTex, AnimatedModel frame, ModelPart mask, int light, float tickDelta) {
        ExteriorVariantSchema parent = variant.parent();

        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) return;

        BOTI.LAST_RENDERED_DOOR.put(tardis.getUuid(), door);

        PortalData portalData = PortalDataManager.get(tardis.getUuid());
        boolean landed = tardis.travel().getState() == TravelHandlerBase.State.LANDED;

        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(180));

        BOTI.BotiCompositeState composite = BOTI.beginBotiComposite();
        int winW = composite.viewport[2];
        int winH = composite.viewport[3];

        BOTI_HANDLER.setupFramebuffer();

        Vec3 skyColor = new Vec3(0.5d, 0.65d, 0.9d);
        if (landed && portalData != null && portalData.world() != null) {
            Vec3 exteriorFog = portalData.geometry().exteriorFogColor();
            if (exteriorFog != null) {
                skyColor = exteriorFog;
            } else {
                try {
                    skyColor = portalData.world().getSkyColor(Vec3.atLowerCornerOf(tardis.travel().position().getPos()), tickDelta);
                } catch (Exception ignored) {
                }
            }
        }
        if (AITModClient.CONFIG.greenScreenBOTI)
            BOTI.setFramebufferColor(BOTI_HANDLER.afbo, 0, 1, 0, 1);
        else
            BOTI.setFramebufferColor(BOTI_HANDLER.afbo, (float) skyColor.x, (float) skyColor.y, (float) skyColor.z, 1);

        BOTI.copyFramebufferFromFbo(composite.drawFbo, winW, winH, BOTI_HANDLER.afbo);

        MultiBufferSource.BufferSource botiProvider = AIT_BUF_BUILDER_STORAGE.getBotiVertexConsumer();

        BOTI.resetStencilByDraw();
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        drawDoorApertureMask(tardis, door, stack);

        RenderSystem.depthMask(true);
        stack.pushPose();
        StatsHandler stats = tardis.stats();
        Vector3f scale = tardis.travel().getScale();


        Vec3 vec = parent.door().getPortalPosition();
        if (vec == null) vec = Vec3.ZERO;

        stack.translate(vec.x, -vec.y - parent.portalHeight() / 2f, vec.z);
        stack.scale((float) parent.portalWidth() * scale.x(),
                (float) parent.portalHeight() * scale.y(), scale.z());
        if (tardis.travel().getState() == TravelHandlerBase.State.LANDED) {
            RenderType whichOne = RenderType.debugFilledBox();
            float[] colorsForGreenScreen = AITModClient.CONFIG.greenScreenBOTI ?
                    new float[]{0, 1, 0, 1} :
                    new float[] {(float) skyColor.x, (float) skyColor.y, (float) skyColor.z};
            mask.render(stack, botiProvider.getBuffer(whichOne), 0xf000f0, OverlayTexture.NO_OVERLAY,
                    FastColor.ARGB32.colorFromFloat(1, colorsForGreenScreen[0], colorsForGreenScreen[1], colorsForGreenScreen[2]));
        } else {
            mask.render(stack, botiProvider.getBuffer(RenderType.entityTranslucentCull(frameTex)),
                    0xf000f0, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        }
        botiProvider.endBatch();
        stack.popPose();
        BOTI.copyDepthToFbo(BOTI_HANDLER.afbo, composite.drawFbo, winW, winH);

        BOTI_HANDLER.afbo.bindWrite(false);
        BOTI.resetDepthByDraw();

        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

        if (landed && portalData != null && portalData.world() != null) {
            WorldGeometryRenderer geometry = portalData.geometry();
            CachedDirectedGlobalPos exteriorPos = tardis.travel().position();
            BlockPos exteriorBlockPos = exteriorPos.getPos();

            try {
                float exteriorRotation = exteriorPos.getRotationDegrees();
                double normalRad = Math.toRadians(exteriorRotation);
                Vec3 doorNormal = new Vec3(Math.sin(normalRad), 0.0, -Math.cos(normalRad));
                geometry.setDoorNormal(doorNormal);

                Camera camera = client.gameRenderer.getMainCamera();
                Direction interiorDoorFacing = door.getFacing().getOpposite();
                float deltaYaw = (exteriorRotation + 180f) - interiorDoorFacing.toYRot();

                Vec3 interiorDoorCenter = new Vec3(door.getBlockPos().getX() + 0.5, door.getBlockPos().getY() + 1.0,
                        door.getBlockPos().getZ() + 0.5);
                Vec3 rel = camera.getPosition().subtract(interiorDoorCenter);

                double rad = Math.toRadians(deltaYaw);
                double cos = Math.cos(rad);
                double sin = Math.sin(rad);
                Vec3 relRotated = new Vec3(rel.x * cos - rel.z * sin, rel.y, rel.x * sin + rel.z * cos);
                Vec3 eyeRelToCenter = new Vec3(0.5, 1.0, 0.5).add(relRotated);

                float portalYaw = camera.getYRot() + deltaYaw;
                float portalPitch = camera.getXRot();

                geometry.render(tardis.getUuid(), portalData.world(), exteriorBlockPos, eyeRelToCenter,
                        portalYaw, portalPitch, tickDelta, true);
            } catch (Throwable t) {
                AITMod.LOGGER.error("Failed to render door BOTI interior", t);
            }
        }

        // Render vortex/effects when in flight
        stack.pushPose();
        float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) + Minecraft.getInstance().player.tickCount;
        if (!tardis.travel().autopilot() && tardis.travel().getState() != TravelHandlerBase.State.LANDED)
            stack.mulPose(Axis.YN.rotationDegrees((delta) * (tardis.travel().speed() * 0.7f)));
        if (!tardis.crash().isNormal())
            stack.mulPose(Axis.XP.rotationDegrees((delta)));
        stack.mulPose(Axis.ZP.rotationDegrees((delta) * (tardis.travel().speed() + 1)));
        stack.mulPose(Axis.YP.rotationDegrees(180));
        stack.translate(0, 0, 500);
        stack.scale(1.5f, 1.5f, 1.5f);
        VortexRender util = stats.getVortexEffects().toRender();
        if (!tardis.travel().isLanded() /*&& !tardis.flight().isFlying()*/) {
            util.setSpeed(tardis.travel().speed() < 1 ? 4 : tardis.travel().speed());
            util.render(stack);
        }
        botiProvider.endBatch();
        stack.popPose();

        // Render door frame
        if (!tardis.getExterior().getCategory().equals(CategoryRegistry.GEOMETRIC)) {
            stack.pushPose();
            stack.mulPose(Axis.YP.rotationDegrees(180));
            stack.scale(scale.x, scale.y, scale.z);

            frame.renderWithAnimations(tardis, door, frame.root(), stack,
                    botiProvider.getBuffer(AITRenderLayers.getBotiInterior(variant.texture())),
                    light, OverlayTexture.NO_OVERLAY, 1, 1F, 1.0F, 1.0F, tickDelta);
            botiProvider.endBatch();
            stack.popPose();

            // Render emissive parts
            stack.pushPose();
            stack.mulPose(Axis.YP.rotationDegrees(180));
            stack.scale(scale.x, scale.y, scale.z);
            if (variant.emission() != null) {
                float u = 1;
                float t = 1;
                float s = 1;

                if ((stats.getName() != null && "partytardis".equalsIgnoreCase(stats.getName())
                        || (!tardis.extra().getInsertedDisc().isEmpty()))) {
                    final float[] rgb = ClientTardisUtil.getPartyColors();
                    u = rgb[0];
                    t = rgb[1];
                    s = rgb[2];
                }

                boolean power = tardis.fuel().hasPower();
                boolean alarm = tardis.alarm().isEnabled();

                float red = power ? s : 0;
                float green = power ? alarm ? 0.3f : t : 0;
                float blue = power ? alarm ? 0.3f : u : 0;

                frame.renderWithAnimations(tardis, door, frame.root(), stack, botiProvider.getBuffer((DependencyChecker.hasIris() ? AITRenderLayers.tardisEmissiveCullZOffset(variant.emission()) : AITRenderLayers.text(variant.emission()))), 0xf000f0, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F, tickDelta);
                botiProvider.endBatch();
            }
            stack.popPose();
        }

        GL11.glDisable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0x00);

        if (!DependencyChecker.isIrisShaderPackInUse())
            BOTI.copyColorToFbo(BOTI_HANDLER.afbo, composite.drawFbo, winW, winH);
        BOTI.endBotiComposite(composite);

        stack.popPose();
    }
}
