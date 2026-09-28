package dev.amble.ait.client.boti;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.models.AnimatedModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.client.renderers.VortexRender;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.blockentities.DoorBlockEntity;
import dev.amble.ait.core.blocks.DoorBlock;
import dev.amble.ait.core.tardis.handler.StatsHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.registry.impl.CategoryRegistry;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class TardisDoorBOTI extends BOTI {
    public static void renderInteriorDoorBoti(ClientTardis tardis, DoorBlockEntity door, ClientExteriorVariantSchema variant, PoseStack stack, MultiBufferSource consumers, ResourceLocation frameTex, AnimatedModel frame, ModelPart mask, int light, float tickDelta) {
        ExteriorVariantSchema parent = variant.parent();

        if (client.level == null
                || client.player == null) return;

        if (!BOTI.cameraInFrontOf(door.getBlockPos(),
                door.getBlockState().getValue(DoorBlock.FACING).toYRot()))
            return;

        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(180));

        client.getMainRenderTarget().unbindWrite();

        BOTI_HANDLER.setupFramebuffer();

        Vec3 skyColor = client.level.getSkyColor(client.player.position(), client.getTimer().getGameTimeDeltaPartialTick(true));
        if (AITModClient.CONFIG.greenScreenBOTI)
            BOTI.setFramebufferColor(BOTI_HANDLER.afbo, 0, 1, 0, 1);
        else
            BOTI.setFramebufferColor(BOTI_HANDLER.afbo, (float) skyColor.x, (float) skyColor.y, (float) skyColor.z, 1);

        BOTI.copyFramebuffer(client.getMainRenderTarget(), BOTI_HANDLER.afbo);

        MultiBufferSource.BufferSource botiProvider = AIT_BUF_BUILDER_STORAGE.getBotiVertexConsumer();

        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        RenderSystem.depthMask(true);
        stack.pushPose();
        StatsHandler stats = tardis.stats();
        Vector3f scale = tardis.travel().getScale();
        Vec3 vec = parent.door().getPortalPosition();
        if (vec == null) vec = Vec3.ZERO;

        stack.translate(vec.x, -vec.y - parent.portalHeight() / 2f, vec.z);
        stack.scale((float) parent.portalWidth() * scale.x(),
                (float) parent.portalHeight() * scale.y(), scale.z());

        if (client.getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            stack.pushPose();
            stack.translate(0, 0, 0.8);
            client.getItemRenderer().renderStatic(Items.BLUE_STAINED_GLASS_PANE.getDefaultInstance(), ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT, 0, stack, consumers, client.level, 0);
            stack.popPose();
        }

        if (tardis.travel().getState() == TravelHandlerBase.State.LANDED) {
            int far = BOTICache.skyColor(door.getBlockPos());
            int fill = AITModClient.CONFIG.greenScreenBOTI
                    ? FastColor.ARGB32.colorFromFloat(1, 0, 1, 0)
                    : far != -1
                            ? 0xFF000000 | far
                            : FastColor.ARGB32.colorFromFloat(1, (float) skyColor.x,
                                    (float) skyColor.y, (float) skyColor.z);

            mask.render(stack, botiProvider.getBuffer(RenderType.entityCutoutNoCull(AITMod.id("textures/boti/blank.png"))),
                    LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, fill);
        } else {
            mask.render(stack, botiProvider.getBuffer(RenderType.entityTranslucentCull(frameTex)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        }
        botiProvider.endBatch();
        BOTI_HANDLER.afbo.bindWrite(false);
        stack.popPose();
        copyDepth(BOTI_HANDLER.afbo, client.getMainRenderTarget());

        BOTI_HANDLER.afbo.bindWrite(false);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

        if (tardis.travel().getState() == TravelHandlerBase.State.LANDED) {
            float facing = door.getBlockState().getValue(DoorBlock.FACING).toYRot();
            BOTICache.render(tardis.getUuid(), door.getBlockPos(), facing + 180f, facing, true, stack);
        }

        stack.pushPose();
        float delta = client.getTimer().getGameTimeDeltaPartialTick(true) + client.player.tickCount;
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
            /*// TODO not a clue if this will work but oh well - Loqor
            stack.push();
            stack.scale(0.9f, 0.9f, 0.9f);
            util.renderVortex(stack);
            stack.pop();*/
        }
        botiProvider.endBatch();
        BOTI_HANDLER.afbo.bindWrite(false);
        stack.popPose();

        if (!tardis.getExterior().getCategory().equals(CategoryRegistry.GEOMETRIC)) {
            stack.pushPose();
            stack.mulPose(Axis.YP.rotationDegrees(180));
            stack.scale(scale.x, scale.y, scale.z);

            // TODO: use DoorRenderer/ClientLightUtil instead.
            frame.renderWithAnimations(tardis, door, frame.root(), stack, botiProvider.getBuffer(AITRenderLayers.getBotiInterior(variant.texture())), light, OverlayTexture.NO_OVERLAY, 1, 1F, 1.0F, 1.0F, tickDelta);
            botiProvider.endBatch();
        BOTI_HANDLER.afbo.bindWrite(false);
            stack.popPose();

            stack.pushPose();
            stack.mulPose(Axis.YP.rotationDegrees(180));
            stack.scale(scale.x, scale.y, scale.z);
            if (variant.emission() != null) {
                float u = 1;
                float t = 1;
                float s = 1;

                if ((stats.getName() != null && "partytardis".equalsIgnoreCase(stats.getName()) || !tardis.extra().getInsertedDisc().isEmpty())) {
                    final float[] rgb = ClientTardisUtil.getPartyColors();

                    u = rgb[0];
                    t = rgb[1];
                    s = rgb[2];
                }

                boolean power = tardis.fuel().hasPower();
                boolean alarm = tardis.alarm().isEnabled();

                float red = power ? s : 0;
                float green = power ? alarm ? 0.3f : t : 0;
                float blue = power ? alarm ? 0.3f : u:  0;

                frame.renderWithAnimations(tardis, door, frame.root(), stack, botiProvider.getBuffer((DependencyChecker.hasIris() ? AITRenderLayers.tardisEmissiveCullZOffset(variant.emission(), true) : AITRenderLayers.text(variant.emission()))), 0xf000f0, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F, tickDelta);
                botiProvider.endBatch();
        BOTI_HANDLER.afbo.bindWrite(false);
            }
            stack.popPose();
        }

        client.getMainRenderTarget().bindWrite(true);

        BOTI.copyColor(BOTI_HANDLER.afbo, client.getMainRenderTarget());

        GL11.glDisable(GL11.GL_STENCIL_TEST);

        RenderSystem.depthMask(true);

        stack.popPose();
    }
}
