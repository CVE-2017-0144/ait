package dev.amble.ait.client.boti;

import static dev.amble.ait.client.renderers.entities.RiftEntityRenderer.CIRCLE_TEXTURE;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.renderers.VortexRender;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.lwjgl.opengl.GL11;

public class RiftBOTI extends BOTI {
    public static void renderRiftBoti(PoseStack stack, HierarchicalModel frame, int pack) {
        if (!AITModClient.CONFIG.enableTardisBOTI)
            return;

        if (client.level == null
                || client.player == null) return;

        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(180));

        client.getMainRenderTarget().unbindWrite();

        BOTI_HANDLER.setupFramebuffer();

        BOTI.copyFramebuffer(client.getMainRenderTarget(), BOTI_HANDLER.afbo);

        MultiBufferSource.BufferSource portalProvider = AIT_BUF_BUILDER_STORAGE.getBotiVertexConsumer();

        // Enable stencil testing and clear the stencil buffer
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        RenderSystem.depthMask(true);
        stack.pushPose();
        stack.translate(0, -0.7f, 0.05);
        stack.scale(1.1f, 1.1f, 1.1f);
        frame.renderToBuffer(stack, portalProvider.getBuffer(RenderType.entityTranslucentCull(CIRCLE_TEXTURE)), 0xf000f0, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        portalProvider.endBatch();
        stack.popPose();
        copyDepth(BOTI_HANDLER.afbo, client.getMainRenderTarget());

        BOTI_HANDLER.afbo.bindWrite(false);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

        stack.pushPose();
        stack.mulPose(Axis.ZP.rotationDegrees(5 * (client.getTimer().getGameTimeDeltaPartialTick(true) + client.player.tickCount)));
        stack.translate(0, -1, 400);

        // --- DISABLE FOG ---
        // Save the current fog state and push it to infinity
        float oldFogStart = RenderSystem.getShaderFogStart();
        float oldFogEnd = RenderSystem.getShaderFogEnd();
        RenderSystem.setShaderFogStart(Float.MAX_VALUE);
        RenderSystem.setShaderFogEnd(Float.MAX_VALUE);

        VortexRender util = VortexRender.getCurrentInstance();
        util.render(stack);

        // Ensure the provider draws while the fog is disabled
        portalProvider.endBatch();

        // --- RESTORE FOG ---
        // Bring the fog back to normal for the rest of the game world
        RenderSystem.setShaderFogStart(oldFogStart);
        RenderSystem.setShaderFogEnd(oldFogEnd);

        stack.popPose();

        client.getMainRenderTarget().bindWrite(true);

        BOTI.copyColor(BOTI_HANDLER.afbo, client.getMainRenderTarget());

        GL11.glDisable(GL11.GL_STENCIL_TEST);

        RenderSystem.depthMask(true);

        stack.popPose();
    }
}
