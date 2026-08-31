package dev.amble.ait.client.overlays;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.blockentities.FabricatorBlockEntity;
import dev.amble.ait.core.blocks.FabricatorBlock;

public class FabricatorOverlay implements HudRenderCallback {
    @Override
    public void onHudRender(GuiGraphics drawContext, DeltaTracker v) {
        Minecraft mc = Minecraft.getInstance();
        PoseStack stack = drawContext.pose();

        if (mc.player == null || mc.level == null)
            return;

        if (!mc.options.getCameraType().isFirstPerson())
            return;

        if (mc.hitResult == null) return;

        if (mc.hitResult.getType() == HitResult.Type.BLOCK) {
            Block block = mc.player.level().getBlockState(((BlockHitResult) mc.hitResult).getBlockPos())
                    .getBlock();
            if (block instanceof FabricatorBlock) {
                BlockEntity entity = mc.player.level().getBlockEntity(((BlockHitResult) mc.hitResult).getBlockPos());
                if (entity instanceof FabricatorBlockEntity fabricatorBlockEntity) {
                    stack.pushPose();
                    stack.translate(((float) drawContext.guiWidth() / 2), ((float) drawContext.guiHeight() / 2), -20);
                    stack.scale(0.75f, 0.75f, 0.75f);
                    stack.mulPose(Axis.ZP.rotationDegrees(((float) mc.player.tickCount / 200.0f) * 360f));
                    stack.translate(-((float) 83 / 2), -((float) 83 / 2), 0);
                    RenderSystem.disableDepthTest();
                    RenderSystem.depthMask(false);
                    drawContext.setColor(1.0F, 1.0F, 1.0F, 0.8f);
                    RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
                            GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE,
                            GlStateManager.DestFactor.ZERO);
                    drawContext.blit(AITMod.id("textures/gui/tardis/monitor/security_menu.png"), 0,
                            0, 0, 0, 138, 83, 83, 256, 256);
                    RenderSystem.setShaderColor(1, 1, 1, 1);
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.depthMask(true);
                    RenderSystem.enableDepthTest();
                    stack.popPose();
                    ItemStack fabricatorItemStack = fabricatorBlockEntity.getShowcaseStack();
                    float centerX = (float) drawContext.guiWidth() / 2 - 8f;
                    float centerY = (float) drawContext.guiHeight() / 2 - 8f;
                    double angleStep = 2 * Math.PI / fabricatorItemStack.getCount();

                    for (int i = 0; i < fabricatorItemStack.getCount(); i++) {
                        double angle = i * angleStep - Math.PI / 2;
                        int x = (int) (centerX + Math.cos(angle) * 32);
                        int y = (int) (centerY + Math.sin(angle) * 32);

                        stack.pushPose();
                        stack.translate(x, y, -10);
                        RenderSystem.setShaderColor(0, 0, 0, 0.5f);
                        stack.pushPose();
                        stack.translate(0, 0, -12);
                        drawContext.renderItem(fabricatorItemStack, 1, 1);
                        stack.popPose();
                        RenderSystem.setShaderColor(1, 1, 1, 1);
                        drawContext.renderItem(fabricatorItemStack, 0, 0);
                        stack.popPose();
                    }
                }
            }
        }
    }
}
