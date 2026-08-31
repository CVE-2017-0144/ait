package dev.amble.ait.client.boti;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.models.exteriors.ExteriorModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.DyeColorUtil;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.core.tardis.handler.StatsHandler;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.registry.impl.exterior.ClientExteriorVariantRegistry;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class TardisExteriorBOTI extends BOTI {
    public static void renderExteriorBoti(ExteriorBlockEntity exterior, ClientExteriorVariantSchema variant, PoseStack stack, MultiBufferSource consumers, ExteriorModel frame, ModelPart mask, int light) {
        if (client.level == null
                || client.player == null) return;

        if (!exterior.isLinked())
            return;

        ClientTardis tardis = exterior.tardis().get().asClient();

        stack.pushPose();

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

        if (client.getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            stack.pushPose();
            stack.translate(0, 0, 0.8);
            client.getItemRenderer().renderStatic(Items.BLUE_STAINED_GLASS_PANE.getDefaultInstance(), ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT, 0, stack, consumers, client.level, 0);
            stack.popPose();
        }

        RenderType whichOne = AITModClient.CONFIG.greenScreenBOTI ?
                RenderType.debugFilledBox() : RenderType.endGateway();
        float[] colorsForGreenScreen = AITModClient.CONFIG.greenScreenBOTI ? new float[]{0, 1, 0, 1} : new float[] {(float) skyColor.x, (float) skyColor.y, (float) skyColor.z};
        mask.render(stack, botiProvider.getBuffer(whichOne), light, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(1, colorsForGreenScreen[0], colorsForGreenScreen[1], colorsForGreenScreen[2]));
        botiProvider.endBatch();
        stack.popPose();

        copyDepth(BOTI_HANDLER.afbo, client.getMainRenderTarget());

        BOTI_HANDLER.afbo.bindWrite(false);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(180));
        if (name.equalsIgnoreCase("grumm") || name.equalsIgnoreCase("dinnerbone")) {
            stack.mulPose(Axis.XP.rotationDegrees(-90f));
            stack.translate(0, scale.y + 0.25f, scale.z -1.7f);
        }
        stack.scale(scale.x(), scale.y(), scale.z());

        frame.renderDoors(tardis, exterior, frame.root(), stack, botiProvider.getBuffer(AITRenderLayers.getBotiInterior(variant.texture())), light, OverlayTexture.NO_OVERLAY, 1, 1F, 1.0F, 1.0F, true);
        botiProvider.endBatch();
        stack.popPose();

        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(180));
        if (name.equalsIgnoreCase("grumm") || name.equalsIgnoreCase("dinnerbone")) {
            stack.mulPose(Axis.XP.rotationDegrees(-90f));
            stack.translate(0, scale.y() + 0.25f, scale.z() -1.7f);
        }
        stack.scale(scale.x(), scale.y(), scale.z());

        if (variant != ClientExteriorVariantRegistry.CORAL_GROWTH) {
            BiomeHandler handler = tardis.handler(TardisComponent.Id.BIOME);
            ResourceLocation biomeTexture = handler.getBiomeKey().get(variant.overrides());
            if (biomeTexture != null)
                frame.renderDoors(tardis, exterior, frame.root(), stack,
                        botiProvider.getBuffer(AITRenderLayers.entityTranslucentCull(biomeTexture)),
                        light, OverlayTexture.NO_OVERLAY, 1, 1F, 1.0F, 1.0F, true);
        }
        botiProvider.endBatch();
        stack.popPose();

        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(180));
        if (name.equalsIgnoreCase("grumm") || name.equalsIgnoreCase("dinnerbone")) {
            stack.mulPose(Axis.XP.rotationDegrees(-90f));
            stack.translate(0, scale.y + 0.25f, scale.z -1.7f);
        }
        stack.scale(scale.x(), scale.y(), scale.z());
        if (variant.emission() != null) {
            float u = 1;
            float t = 1;
            float s = 1;

            if ((stats.getName() != null && "partytardis".equalsIgnoreCase(stats.getName()) || !tardis.extra().getInsertedDisc().isEmpty())) {
                int m = 25;
                int n = client.player.tickCount / m + client.player.getId();
                int o = DyeColor.values().length;
                int p = n % o;
                int q = (n + 1) % o;
                float r = ((float) (client.player.tickCount % m)) / m;
                float[] fs = DyeColorUtil.rgb(DyeColor.byId(p));
                float[] gs = DyeColorUtil.rgb(DyeColor.byId(q));
                s = fs[0] * (1f - r) + gs[0] * r;
                t = fs[1] * (1f - r) + gs[1] * r;
                u = fs[2] * (1f - r) + gs[2] * r;
            }

            boolean power = tardis.fuel().hasPower();
            boolean alarms = tardis.alarm().isEnabled();

            float red = power ? s : alarms ? 0.3f : 0;
            float green = power ? alarms ? 0.3f : t : 0;
            float blue = power ? alarms ? 0.3f : u : 0;

            frame.renderDoors(tardis, exterior, frame.root(), stack, botiProvider.getBuffer(AITRenderLayers.tardisEmissiveCullZOffset(variant.emission(), true)), LightTexture.FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY, red, green, blue, 1, true);
            botiProvider.endBatch();
        }
        stack.popPose();

        client.getMainRenderTarget().bindWrite(true);

        BOTI.copyColor(BOTI_HANDLER.afbo, client.getMainRenderTarget());

        GL11.glDisable(GL11.GL_STENCIL_TEST);

        stack.popPose();
    }
}
