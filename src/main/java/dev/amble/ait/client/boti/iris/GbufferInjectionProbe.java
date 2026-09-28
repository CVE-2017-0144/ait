package dev.amble.ait.client.boti.iris;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.DoorBlock;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.boti.AITRenderHelper;
import dev.amble.ait.client.boti.BOTI;
import dev.amble.ait.client.boti.TardisDoorBOTI;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.ClientRenderPass;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.blockentities.DoorBlockEntity;
import dev.amble.lib.platform.render.WorldRenderContext;
import dev.loqor.portal.client.PortalData;
import dev.loqor.portal.client.PortalDataManager;

public final class GbufferInjectionProbe {
    private static boolean loggedError = false;
    private static boolean loggedSuccess = false;
    private static boolean loggedStencilBits = false;
    private static long lastTimeDiagMs = 0;

    private GbufferInjectionProbe() {}

    public static void run(WorldRenderContext ctx) {
        if (!DependencyChecker.isIrisShaderPackInUse())
            return;

        ClientTardis tardis = ClientTardisUtil.getCurrentTardis();
        if (tardis == null)
            return;

        PortalData data = PortalDataManager.get(tardis.getUuid());
        if (data == null || data.geometry() == null)
            return;

        boolean stencilEnabled = AITRenderHelper.getIsStencilEnabled(
                Minecraft.getInstance().getMainRenderTarget());

        if (!loggedStencilBits) {
            int boundFbo = BOTI.currentDrawFbo();
            int fboStencilSize = org.lwjgl.opengl.GL30.glGetFramebufferAttachmentParameteri(
                    org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER,
                    org.lwjgl.opengl.GL30.GL_STENCIL_ATTACHMENT,
                    org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE);
            AITMod.LOGGER.info("Phase B gbuffer stencil probe: aitFlagStencilEnabled={} boundFBO={} "
                    + "actualFboStencilBits={}", stencilEnabled, boundFbo, fboStencilSize);
            loggedStencilBits = true;
        }

        DoorBlockEntity door = BOTI.LAST_RENDERED_DOOR.get(tardis.getUuid());
        if (door == null) {
            for (DoorBlockEntity candidate : BOTI.DOOR_RENDER_QUEUE) {
                if (candidate != null && candidate.isLinked()
                        && tardis.getUuid().equals(candidate.tardis().get().getUuid())) {
                    door = candidate;
                    break;
                }
            }
        }

        if (door != null && door.isRemoved()) {
            BOTI.LAST_RENDERED_DOOR.remove(tardis.getUuid());
            door = null;
        }
        if (door != null) {
            boolean doorOpen = tardis.door().getLeftRot() > 0
                    || tardis.getExterior().getVariant().getClient().hasTransparentDoors();
            if (!doorOpen) {
                BOTI.LAST_RENDERED_DOOR.remove(tardis.getUuid());
                door = null;
            }
        }
        if (door == null || !stencilEnabled)
            return;

        refreshPortalView(tardis, door, data);

        long now = System.currentTimeMillis();
        if (now - lastTimeDiagMs > 1000 && data.world() != null) {
            lastTimeDiagMs = now;
            net.minecraft.client.multiplayer.ClientLevel viewerWorld = Minecraft.getInstance().level;
            AITMod.LOGGER.debug("BOTI-TIME-DIAG shadowExterior timeOfDay={} (%24000={}) skyAngle={} | viewer={} timeOfDay={}",
                    data.world().getDayTime(), data.world().getDayTime() % 24000L,
                    data.world().getTimeOfDay(1.0f),
                    viewerWorld != null ? viewerWorld.dimension().location() : "null",
                    viewerWorld != null ? viewerWorld.getDayTime() % 24000L : -1);
        }

        PoseStack stack = ctx.matrixStack();

        boolean wasStencilEnabled = GL11.glIsEnabled(GL11.GL_STENCIL_TEST);
        int prevStencilFunc = GL11.glGetInteger(GL11.GL_STENCIL_FUNC);
        int prevStencilRef  = GL11.glGetInteger(GL11.GL_STENCIL_REF);
        int prevStencilMask = GL11.glGetInteger(GL11.GL_STENCIL_VALUE_MASK);
        int prevStencilWriteMask = GL11.glGetInteger(GL11.GL_STENCIL_WRITEMASK);
        int prevStencilFail = GL11.glGetInteger(GL11.GL_STENCIL_FAIL);
        int prevStencilZFail = GL11.glGetInteger(GL11.GL_STENCIL_PASS_DEPTH_FAIL);
        int prevStencilZPass = GL11.glGetInteger(GL11.GL_STENCIL_PASS_DEPTH_PASS);

        try {
            {
                GL11.glEnable(GL11.GL_STENCIL_TEST);
                GL11.glStencilMask(0xFF);
                GL11.glClearStencil(0);
                GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);

                GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
                GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

                Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
                BlockPos doorPos = door.getBlockPos();
                stack.pushPose();
                stack.translate(0.5, 0, 0.5);
                stack.translate(doorPos.getX() - camera.getPosition().x(),
                        doorPos.getY() - camera.getPosition().y(),
                        doorPos.getZ() - camera.getPosition().z());
                stack.scale(1, -1, -1);
                stack.mulPose(Axis.YP.rotationDegrees(
                        door.getBlockState().getValue(DoorBlock.FACING).toYRot()));
                stack.mulPose(Axis.YP.rotationDegrees(180));

                TardisDoorBOTI.drawDoorApertureMask(tardis, door, stack);
                stack.popPose();

                GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
                GL11.glStencilMask(0x00);
                RenderSystem.colorMask(true, true, true, true);
                RenderSystem.depthMask(true);

                BOTI.clearDepthInStencilRegion();

                net.minecraft.world.phys.Vec3 fog = data.geometry().exteriorFogColor();
                boolean skyPhase = dev.amble.ait.client.boti.iris.IrisPhase.setSky();
                try {
                    if (fog != null)
                        BOTI.fillColorInStencilRegion((float) fog.x, (float) fog.y, (float) fog.z);
                    else
                        BOTI.fillColorInStencilRegion(0.5f, 0.65f, 0.9f);

                    data.geometry().injectSky(tardis.getUuid(), data.world(), ctx.tickCounter().getGameTimeDeltaPartialTick(true));
                } finally {
                    if (skyPhase)
                        dev.amble.ait.client.boti.iris.IrisPhase.reset();
                }

                data.geometry().debugInjectTerrainIntoGbuffer();
                data.geometry().injectBlockEntitiesAndEntities(ctx.tickCounter().getGameTimeDeltaPartialTick(true));
                data.geometry().debugInjectTranslucentIntoGbuffer();

                BOTI.clearDepthInStencilRegion();

                Minecraft mc = Minecraft.getInstance();
                net.minecraft.world.phys.Vec3 camPos = mc.gameRenderer.getMainCamera().getPosition();
                net.minecraft.client.renderer.MultiBufferSource.BufferSource doorImm =
                        BOTI.AIT_BUF_BUILDER_STORAGE.getBotiVertexConsumer();
                boolean doorPhase = dev.amble.ait.client.boti.iris.IrisPhase.setBlockEntities();
                stack.pushPose();
                stack.translate(door.getBlockPos().getX() - camPos.x,
                        door.getBlockPos().getY() - camPos.y,
                        door.getBlockPos().getZ() - camPos.z);
                ClientRenderPass.suspend();
                try {
                    mc.getBlockEntityRenderDispatcher().render(door, ctx.tickCounter().getGameTimeDeltaPartialTick(true), stack, doorImm);
                    doorImm.endBatch();
                } finally {
                    ClientRenderPass.resume();
                    stack.popPose();
                    if (doorPhase)
                        dev.amble.ait.client.boti.iris.IrisPhase.reset();
                }

                BOTI.clearDepthInStencilRegion();
                stack.pushPose();
                stack.translate(0.5, 0, 0.5);
                stack.translate(door.getBlockPos().getX() - camPos.x,
                        door.getBlockPos().getY() - camPos.y,
                        door.getBlockPos().getZ() - camPos.z);
                stack.scale(1, -1, -1);
                stack.mulPose(Axis.YP.rotationDegrees(
                        door.getBlockState().getValue(DoorBlock.FACING).toYRot()));
                stack.mulPose(Axis.YP.rotationDegrees(180));
                TardisDoorBOTI.drawDoorApertureMask(tardis, door, stack, true);
                stack.popPose();

                if (!loggedSuccess) {
                    AITMod.LOGGER.info("Phase B gbuffer-injection probe: drew STENCIL-CLIPPED interior terrain+BE+entities "
                            + "into the gbuffer at AFTER_ENTITIES (stencilEnabled={}, door={})",
                            stencilEnabled, door.getBlockPos());
                    loggedSuccess = true;
                }
            }
        } catch (Throwable t) {
            if (!loggedError) {
                AITMod.LOGGER.error("Phase B gbuffer-injection probe threw", t);
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

    private static void refreshPortalView(ClientTardis tardis, DoorBlockEntity door, PortalData data) {
        try {
            Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
            float exteriorRotation = tardis.travel().position().getRotationDegrees();
            net.minecraft.core.Direction interiorDoorFacing = door.getFacing().getOpposite();
            float deltaYaw = (exteriorRotation + 180f) - interiorDoorFacing.toYRot();

            net.minecraft.world.phys.Vec3 interiorDoorCenter = new net.minecraft.world.phys.Vec3(
                    door.getBlockPos().getX() + 0.5, door.getBlockPos().getY() + 1.0, door.getBlockPos().getZ() + 0.5);
            net.minecraft.world.phys.Vec3 rel = camera.getPosition().subtract(interiorDoorCenter);

            double rad = Math.toRadians(deltaYaw);
            double cos = Math.cos(rad), sin = Math.sin(rad);
            net.minecraft.world.phys.Vec3 relRotated = new net.minecraft.world.phys.Vec3(
                    rel.x * cos - rel.z * sin, rel.y, rel.x * sin + rel.z * cos);
            net.minecraft.world.phys.Vec3 eyeRelToCenter =
                    new net.minecraft.world.phys.Vec3(0.5, 1.0, 0.5).add(relRotated);

            data.geometry().updatePortalView(eyeRelToCenter, camera.getYRot() + deltaYaw, camera.getXRot());
        } catch (Throwable ignored) {
        }
    }
}
