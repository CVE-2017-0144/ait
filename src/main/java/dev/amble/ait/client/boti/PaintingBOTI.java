package dev.amble.ait.client.boti;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.models.decoration.PaintingFrameModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class PaintingBOTI extends BOTI {
    public static void renderBOTIPainting(PoseStack stack, PaintingFrameModel frame,
                                          int light, HierarchicalModel paintingContents, ResourceLocation frameTexture, ResourceLocation paintingContentsTexture) {
        if (!AITModClient.CONFIG.enableTardisBOTI)
            return;

        if (client.level == null
                || client.player == null) return;

        PaintingFrameModel model = new PaintingFrameModel(PaintingFrameModel.getTexturedModelData().bakeRoot());

        stack.pushPose();

        BOTI.BotiCompositeState composite = BOTI.beginBotiComposite();
        int winW = composite.viewport[2];
        int winH = composite.viewport[3];

        BOTI_HANDLER.setupFramebuffer();

        BOTI.copyFramebufferFromFbo(composite.drawFbo, winW, winH, BOTI_HANDLER.afbo);

        MultiBufferSource.BufferSource botiProvider = AIT_BUF_BUILDER_STORAGE.getBotiVertexConsumer();

        model.renderToBuffer(stack, botiProvider.getBuffer(AITRenderLayers.entityCutout(frameTexture)), light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        botiProvider.endBatch();

        stack.translate(0, 0, -0.125);

        BOTI.resetStencilByDraw();
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        // Render the mask overtop the interior of the interior stuff

        RenderSystem.depthMask(true);
        stack.pushPose();
        frame.renderWithFbo(stack, botiProvider, 0xf000f0, OverlayTexture.NO_OVERLAY, 0, 0, 0, 1, frameTexture);
        botiProvider.endBatch();
        BOTI.copyDepthToFbo(BOTI_HANDLER.afbo, composite.drawFbo, winW, winH);

        BOTI_HANDLER.afbo.bindWrite(false);
        BOTI.resetDepthByDraw();
        stack.popPose();

        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

        stack.pushPose();
        stack.translate(0, 0, -4f);
        RenderSystem.enableCull();
        paintingContents.renderToBuffer(stack, botiProvider.getBuffer(AITRenderLayers.getBotiInterior(paintingContentsTexture)), 0xf000f0, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
        RenderSystem.disableCull();
        botiProvider.endBatch();
        stack.popPose();

        BOTI.copyColorToFbo(BOTI_HANDLER.afbo, composite.drawFbo, winW, winH);
        BOTI.endBotiComposite(composite);

        stack.popPose();
    }
}
