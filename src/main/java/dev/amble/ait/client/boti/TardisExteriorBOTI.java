package dev.amble.ait.client.boti;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.models.boti.BotiPortalModel;
import dev.amble.ait.client.models.exteriors.ExteriorModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.DyeColorUtil;
import dev.amble.ait.client.util.SkyboxUtil;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.core.tardis.handler.StatsHandler;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.registry.impl.exterior.ClientExteriorVariantRegistry;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.data.DirectedBlockPos;
import dev.loqor.portal.Portals;
import dev.loqor.portal.client.PortalData;
import dev.loqor.portal.client.PortalDataManager;
import dev.loqor.portal.client.WorldGeometryRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

public class TardisExteriorBOTI extends BOTI {
    public void renderExteriorBoti(ExteriorBlockEntity exterior, ClientExteriorVariantSchema variant, PoseStack stack, ResourceLocation frameTex, ExteriorModel frame, ModelPart mask, int light) {
        if (Minecraft.getInstance().level == null
                || Minecraft.getInstance().player == null) return;

        if (!exterior.isLinked())
            return;

        ClientTardis tardis = exterior.tardis().get().asClient();

        BOTI.LAST_RENDERED_EXTERIOR.put(tardis.getUuid(), exterior);

        // the gbuffer injection draws it, this only keeps the view and sections current
        if (DependencyChecker.isIrisShaderPackInUse()) {
            renderInterior(tardis, false);
            return;
        }

        stack.pushPose();

        // Split into framebuffer work and geometry work. A single zone around the whole portal cannot
        // tell "the blits are expensive" from "drawing the interior is expensive", which is the only
        // thing worth knowing here.
        ProfilerFiller profiler = client.getProfiler();
        profiler.push("ait:boti_ext_fbo_setup");
        profiler.incrementCounter("ait_boti_ext_portals");

        BOTI.BotiCompositeState composite = BOTI.beginBotiComposite();
        int winW = composite.viewport[2];
        int winH = composite.viewport[3];

        BOTI_HANDLER.setupFramebuffer();

        Vec3 skyColor = Minecraft.getInstance().level.getSkyColor(Minecraft.getInstance().player.position(), Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true));
        if (AITModClient.CONFIG.greenScreenBOTI)
            BOTI.setFramebufferColor(BOTI_HANDLER.afbo, 0, 1, 0, 1);
        else
            BOTI.setFramebufferColor(BOTI_HANDLER.afbo, (float) skyColor.x, (float) skyColor.y, (float) skyColor.z, 1);

        BOTI.copyFramebufferFromFbo(composite.drawFbo, winW, winH, BOTI_HANDLER.afbo);
        profiler.incrementCounter("ait_boti_blit");

        profiler.popPush("ait:boti_ext_mask");

        MultiBufferSource.BufferSource botiProvider = AIT_BUF_BUILDER_STORAGE.getBotiVertexConsumer();

        BOTI.resetStencilByDraw();
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        RenderSystem.depthMask(true);
        stack.pushPose();
        StatsHandler stats = tardis.stats();
        String name = stats.getName();
        stack.mulPose(Axis.YP.rotationDegrees(180));
        Vector3f scale = tardis.travel().getScale();
        if (name.equalsIgnoreCase("grumm") || name.equalsIgnoreCase("dinnerbone")) {
            stack.mulPose(Axis.XP.rotationDegrees(-90f));
            stack.translate(0, scale.y() + 0.25f, scale.z() - 1.7f);
        }
        ExteriorVariantSchema parent = variant.parent();

        Vec3 vec = parent.getPortalPosition();
        if (vec == null) vec = Vec3.ZERO;

        stack.translate(vec.x, -vec.y - parent.portalHeight() / 2f, vec.z);
        stack.scale((float) parent.portalWidth() * scale.x(),
                (float) parent.portalHeight() * scale.y(), scale.z());
        RenderType whichOne = RenderType.debugFilledBox();
        float[] colorsForGreenScreen = AITModClient.CONFIG.greenScreenBOTI ? new float[]{0, 1, 0, 1} : new float[] {0f, 0f, 0f};
        mask.render(stack, botiProvider.getBuffer(whichOne), light, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(1, colorsForGreenScreen[0], colorsForGreenScreen[1], colorsForGreenScreen[2]));
        botiProvider.endBatch();
        profiler.incrementCounter("ait_boti_draw_flush");
        stack.popPose();

        profiler.popPush("ait:boti_ext_fbo_depth");
        BOTI.copyDepthToFbo(BOTI_HANDLER.afbo, composite.drawFbo, winW, winH);
        profiler.incrementCounter("ait_boti_blit");

        BOTI_HANDLER.afbo.bindWrite(false);
        BOTI.resetDepthByDraw();

        profiler.popPush("ait:boti_ext_doors");

        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

        renderInterior(tardis, true);

        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(180));
        if (name.equalsIgnoreCase("grumm") || name.equalsIgnoreCase("dinnerbone")) {
            stack.mulPose(Axis.XP.rotationDegrees(-90f));
            stack.translate(0, scale.y + 0.25f, scale.z -1.7f);
        }
        stack.scale(scale.x(), scale.y(), scale.z());

        frame.renderDoors(tardis, exterior, frame.root(), stack, botiProvider.getBuffer(AITRenderLayers.getBotiInterior(variant.texture())), light, OverlayTexture.NO_OVERLAY, 1, 1F, 1.0F, 1.0F, true);
        botiProvider.endBatch();
        profiler.incrementCounter("ait_boti_draw_flush");
        stack.popPose();

        profiler.popPush("ait:boti_ext_biome");

        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(180));
        if (name.equalsIgnoreCase("grumm") || name.equalsIgnoreCase("dinnerbone")) {
            stack.mulPose(Axis.XP.rotationDegrees(-90f));
            stack.translate(0, scale.y() + 0.25f, scale.z() -1.7f);
        }
        stack.scale(scale.x(), scale.y(), scale.z());

        if (variant != ClientExteriorVariantRegistry.CORAL_GROWTH) {
            BiomeHandler handler = exterior.tardis().get().handler(TardisComponent.Id.BIOME);
            ResourceLocation biomeTexture = handler.getBiomeKey().get(variant.overrides());
            if (biomeTexture != null)
                frame.renderDoors(tardis, exterior, frame.root(), stack,
                        botiProvider.getBuffer(AITRenderLayers.entityTranslucentCull(biomeTexture)),
                        light, OverlayTexture.NO_OVERLAY, 1, 1F, 1.0F, 1.0F, true);
        }
        botiProvider.endBatch();
        profiler.incrementCounter("ait_boti_draw_flush");
        stack.popPose();

        profiler.popPush("ait:boti_ext_emission");

        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(180));
        if (name.equalsIgnoreCase("grumm") || name.equalsIgnoreCase("dinnerbone")) {
            stack.mulPose(Axis.XP.rotationDegrees(-90f));
            stack.translate(0, scale.y + 0.25f, scale.z -1.7f);
        }
        stack.scale(scale.x(), scale.y(), scale.z());
        if (variant.emission() != null) {
            float u;
            float t;
            float s;

            if ((stats.getName() != null && "partytardis".equals(stats.getName().toLowerCase()) || (!exterior.tardis().get().extra().getInsertedDisc().isEmpty()))) {
                int m = 25;
                int n = Minecraft.getInstance().player.tickCount / m + Minecraft.getInstance().player.getId();
                int o = DyeColor.values().length;
                int p = n % o;
                int q = (n + 1) % o;
                float r = ((float) (Minecraft.getInstance().player.tickCount % m)) / m;
                float[] fs = DyeColorUtil.rgb(DyeColor.byId(p));
                float[] gs = DyeColorUtil.rgb(DyeColor.byId(q));
                s = fs[0] * (1f - r) + gs[0] * r;
                t = fs[1] * (1f - r) + gs[1] * r;
                u = fs[2] * (1f - r) + gs[2] * r;
            } else {
                float[] hs = new float[]{1.0f, 1.0f, 1.0f};
                s = hs[0];
                t = hs[1];
                u = hs[2];
            }

            boolean power = tardis.fuel().hasPower();
            boolean alarms = tardis.alarm().isEnabled();

            float red = power ? s : alarms ? 0.3f : 0;
            float green = power ? alarms ? 0.3f : t : 0;
            float blue = power ? alarms ? 0.3f : u : 0;

            frame.renderDoors(tardis, exterior, frame.root(), stack, botiProvider.getBuffer(AITRenderLayers.tardisEmissiveCullZOffset(variant.emission())), LightTexture.FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY, red, green, blue, 1, true);
            botiProvider.endBatch();
            profiler.incrementCounter("ait_boti_draw_flush");
        }
        stack.popPose();

        profiler.popPush("ait:boti_ext_fbo_resolve");

        if (!DependencyChecker.isIrisShaderPackInUse())
            BOTI.copyColorToFbo(BOTI_HANDLER.afbo, composite.drawFbo, winW, winH);
        BOTI.endBotiComposite(composite);

        profiler.pop();

        stack.popPose();
    }

    private static void renderInterior(ClientTardis tardis, boolean draw) {
        PortalData interior = PortalDataManager.get(Portals.interiorId(tardis.getUuid()));
        if (interior != null && interior.world() != null && tardis.getDesktop() != null) {
            try {
                WorldGeometryRenderer geometry = interior.geometry();

                DirectedBlockPos interiorDoor = tardis.getDesktop().getDoorPos();
                BlockPos interiorDoorPos = interiorDoor.getPos();
                Direction interiorFacing = interiorDoor.toMinecraftDirection().getOpposite();
                geometry.setDoorFacing(interiorFacing);

                CachedDirectedGlobalPos exteriorPos = tardis.travel().position();
                Direction exteriorFacing = Direction.fromYRot(exteriorPos.getRotationDegrees()).getOpposite();
                Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();

                float deltaYaw = interiorFacing.toYRot() - (tardis.travel().position().getRotationDegrees());

                BlockPos extBlock = exteriorPos.getPos();
                Vec3 exteriorDoorCenter = new Vec3(extBlock.getX() + 0.5, extBlock.getY() + 1.0, extBlock.getZ() + 0.5);
                Vec3 rel = camera.getPosition().subtract(exteriorDoorCenter);

                double rad = Math.toRadians(deltaYaw);
                double cos = Math.cos(rad);
                double sin = Math.sin(rad);
                Vec3 relRotated = new Vec3(rel.x * cos - rel.z * sin, rel.y, rel.x * sin + rel.z * cos);
                Vec3 eyeRelToCenter = new Vec3(0.5, 1.0, 0.5).add(relRotated);

                float portalYaw = camera.getYRot() + deltaYaw;
                float portalPitch = camera.getXRot();

                SkyboxUtil.PORTAL_SKY_TARDIS = tardis;
                try {
                    geometry.render(Portals.interiorId(tardis.getUuid()), interior.world(), interiorDoorPos,
                            eyeRelToCenter, portalYaw, portalPitch, Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true), true, draw);
                } finally {
                    SkyboxUtil.PORTAL_SKY_TARDIS = null;
                }
            } catch (Throwable t) {
                AITMod.LOGGER.error("Failed to render exterior BOTI interior", t);
            }
        }
    }

    public static void drawExteriorApertureMask(ClientTardis tardis, ClientExteriorVariantSchema variant, PoseStack stack) {
        drawExteriorApertureMask(tardis, variant, stack, false);
    }

    public static void drawExteriorApertureMask(ClientTardis tardis, ClientExteriorVariantSchema variant, PoseStack stack, boolean writeDepth) {
        ExteriorVariantSchema parent = variant.parent();
        Vector3f scale = tardis.travel().getScale();

        Vec3 vec = parent.getPortalPosition();
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
}
