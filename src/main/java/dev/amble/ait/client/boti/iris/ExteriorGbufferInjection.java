package dev.amble.ait.client.boti.iris;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.data.DirectedBlockPos;
import dev.amble.lib.platform.render.WorldRenderContext;
import org.lwjgl.opengl.GL11;

import org.joml.Vector3f;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.boti.AITRenderHelper;
import dev.amble.ait.client.boti.BOTI;
import dev.amble.ait.client.boti.TardisExteriorBOTI;
import dev.amble.ait.client.models.exteriors.ExteriorModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.SkyboxUtil;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.blocks.ExteriorBlock;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.loqor.portal.Portals;
import dev.loqor.portal.client.PortalData;
import dev.loqor.portal.client.PortalDataManager;
import dev.loqor.portal.client.WorldGeometryRenderer;

public final class ExteriorGbufferInjection {
    private static boolean loggedError = false;

    private ExteriorGbufferInjection() {}

    public static void run(WorldRenderContext ctx) {
        if (!DependencyChecker.isIrisShaderPackInUse())
            return;
        if (AITModClient.skipBuiltInBOTI())
            return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null)
            return;

        boolean stencilEnabled = AITRenderHelper.getIsStencilEnabled(mc.getMainRenderTarget());
        if (!stencilEnabled)
            return;

        List<Map.Entry<UUID, ExteriorBlockEntity>> entries =
                new ArrayList<>(BOTI.LAST_RENDERED_EXTERIOR.entrySet());
        for (Map.Entry<UUID, ExteriorBlockEntity> entry : entries) {
            ExteriorBlockEntity exterior = entry.getValue();
            if (exterior == null || exterior.isRemoved() || !exterior.isLinked()) {
                BOTI.LAST_RENDERED_EXTERIOR.remove(entry.getKey());
                continue;
            }

            boolean wasStencilEnabled = GL11.glIsEnabled(GL11.GL_STENCIL_TEST);
            int prevStencilFunc = GL11.glGetInteger(GL11.GL_STENCIL_FUNC);
            int prevStencilRef  = GL11.glGetInteger(GL11.GL_STENCIL_REF);
            int prevStencilMask = GL11.glGetInteger(GL11.GL_STENCIL_VALUE_MASK);
            int prevStencilWriteMask = GL11.glGetInteger(GL11.GL_STENCIL_WRITEMASK);
            int prevStencilFail = GL11.glGetInteger(GL11.GL_STENCIL_FAIL);
            int prevStencilZFail = GL11.glGetInteger(GL11.GL_STENCIL_PASS_DEPTH_FAIL);
            int prevStencilZPass = GL11.glGetInteger(GL11.GL_STENCIL_PASS_DEPTH_PASS);

            try {
                injectOne(ctx, mc, exterior);
            } catch (Throwable t) {
                if (!loggedError) {
                    AITMod.LOGGER.error("Exterior gbuffer-injection threw", t);
                    loggedError = true;
                }
            } finally {
                GL11.glStencilMask(0xFF);
                GL11.glStencilFunc(prevStencilFunc, prevStencilRef, prevStencilMask);
                GL11.glStencilOp(prevStencilFail, prevStencilZFail, prevStencilZPass);
                GL11.glStencilMask(prevStencilWriteMask);
                if (!wasStencilEnabled) GL11.glDisable(GL11.GL_STENCIL_TEST);
            }
        }
    }

    private static void injectOne(WorldRenderContext ctx, Minecraft mc, ExteriorBlockEntity exterior) {
        ClientTardis tardis = exterior.tardis().get().asClient();
        ClientExteriorVariantSchema variant = tardis.getExterior().getVariant().getClient();

        boolean doorOpen = tardis.door().getLeftRot() > 0 || variant.hasTransparentDoors();
        if (!doorOpen)
            return;

        PortalData interior = PortalDataManager.get(Portals.interiorId(tardis.getUuid()));
        if (interior == null || interior.world() == null || interior.geometry() == null
                || tardis.getDesktop() == null)
            return;

        WorldGeometryRenderer geometry = interior.geometry();

        Camera camera = mc.gameRenderer.getMainCamera();
        DirectedBlockPos interiorDoor = tardis.getDesktop().getDoorPos();
        Direction interiorFacing = interiorDoor.toMinecraftDirection().getOpposite();
        geometry.setDoorFacing(interiorFacing);

        CachedDirectedGlobalPos exteriorPos = tardis.travel().position();
        float deltaYaw = interiorFacing.toYRot() - exteriorPos.getRotationDegrees();

        BlockPos extBlock = exteriorPos.getPos();
        Vec3 exteriorDoorCenter = new Vec3(extBlock.getX() + 0.5, extBlock.getY() + 1.0, extBlock.getZ() + 0.5);
        Vec3 rel = camera.getPosition().subtract(exteriorDoorCenter);
        double rad = Math.toRadians(deltaYaw);
        double cos = Math.cos(rad), sin = Math.sin(rad);
        Vec3 relRotated = new Vec3(rel.x * cos - rel.z * sin, rel.y, rel.x * sin + rel.z * cos);
        Vec3 eyeRelToCenter = new Vec3(0.5, 1.0, 0.5).add(relRotated);
        float portalYaw = camera.getYRot() + deltaYaw;
        float portalPitch = camera.getXRot();
        geometry.updatePortalView(eyeRelToCenter, portalYaw, portalPitch);

        PoseStack stack = ctx.matrixStack();
        BlockPos pos = exterior.getBlockPos();

        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);
        GL11.glClearStencil(0);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        Vec3 camPos = camera.getPosition();
        stack.pushPose();
        stack.translate(0.5, 0, 0.5);
        stack.translate(pos.getX() - camPos.x, pos.getY() - camPos.y, pos.getZ() - camPos.z);
        stack.scale(1, -1, -1);
        stack.mulPose(Axis.YP.rotationDegrees(
                RotationSegment.convertToDegrees(exterior.getBlockState().getValue(ExteriorBlock.ROTATION))));
        stack.mulPose(Axis.YP.rotationDegrees(180));

        TardisExteriorBOTI.drawExteriorApertureMask(tardis, variant, stack);
        stack.popPose();

        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
        GL11.glStencilMask(0x00);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthMask(true);

        BOTI.clearDepthInStencilRegion();

        Vec3 fog = geometry.exteriorFogColor();
        boolean skyPhase = IrisPhase.setSky();
        try {
            if (fog != null)
                BOTI.fillColorInStencilRegion((float) fog.x, (float) fog.y, (float) fog.z);
            else
                BOTI.fillColorInStencilRegion(0.5f, 0.65f, 0.9f);
        } finally {
            if (skyPhase)
                IrisPhase.reset();
        }

        SkyboxUtil.PORTAL_SKY_TARDIS = tardis;
        try {
            boolean skyInjectPhase = IrisPhase.setSky();
            try {
                geometry.injectSky(Portals.interiorId(tardis.getUuid()), interior.world(), ctx.tickCounter().getGameTimeDeltaPartialTick(true));
            } finally {
                if (skyInjectPhase)
                    IrisPhase.reset();
            }

            geometry.debugInjectTerrainIntoGbuffer();
            geometry.injectBlockEntitiesAndEntities(ctx.tickCounter().getGameTimeDeltaPartialTick(true));
            geometry.debugInjectTranslucentIntoGbuffer();
        } finally {
            SkyboxUtil.PORTAL_SKY_TARDIS = null;
        }

        BOTI.clearDepthInStencilRegion();
        ExteriorModel model = variant.getCachedModel();
        int light = LightTexture.pack(
                mc.level.getBrightness(LightLayer.BLOCK, pos), mc.level.getBrightness(LightLayer.SKY, pos));
        Vector3f doorScale = tardis.travel().getScale();
        MultiBufferSource.BufferSource doorImm = BOTI.AIT_BUF_BUILDER_STORAGE.getBotiVertexConsumer();
        boolean doorPhase = IrisPhase.setBlockEntities();
        stack.pushPose();
        stack.translate(0.5, 0, 0.5);
        stack.translate(pos.getX() - camPos.x, pos.getY() - camPos.y, pos.getZ() - camPos.z);
        stack.scale(1, -1, -1);
        stack.mulPose(Axis.YP.rotationDegrees(
                RotationSegment.convertToDegrees(exterior.getBlockState().getValue(ExteriorBlock.ROTATION))));
        stack.mulPose(Axis.YP.rotationDegrees(180));
        stack.scale(doorScale.x(), doorScale.y(), doorScale.z());
        try {
            model.renderDoors(tardis, exterior, model.root(), stack,
                    doorImm.getBuffer(AITRenderLayers.getBotiInterior(variant.texture())),
                    light, OverlayTexture.NO_OVERLAY, 1, 1F, 1.0F, 1.0F, true);
            doorImm.endBatch();
        } finally {
            stack.popPose();
            if (doorPhase)
                IrisPhase.reset();
        }

        BOTI.clearDepthInStencilRegion();
        stack.pushPose();
        stack.translate(0.5, 0, 0.5);
        stack.translate(pos.getX() - camPos.x, pos.getY() - camPos.y, pos.getZ() - camPos.z);
        stack.scale(1, -1, -1);
        stack.mulPose(Axis.YP.rotationDegrees(
                RotationSegment.convertToDegrees(exterior.getBlockState().getValue(ExteriorBlock.ROTATION))));
        stack.mulPose(Axis.YP.rotationDegrees(180));
        TardisExteriorBOTI.drawExteriorApertureMask(tardis, variant, stack, true);
        stack.popPose();
    }
}
