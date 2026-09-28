package dev.amble.ait.client.overlays;

import java.awt.*;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.core.blockentities.UntemperedSchismBlockEntity;
import dev.amble.ait.core.blocks.UntemperedSchismBlock;
import dev.amble.lib.platform.render.HudRenderEvents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class UntemperedSchismOverlay implements HudRenderEvents.HudRender {

    @Override
    public void onHudRender(GuiGraphics drawContext, DeltaTracker delta) {
        Minecraft mc = Minecraft.getInstance();
        PoseStack stack = drawContext.pose();

        if (mc.player == null || mc.level == null)
            return;

        if (!mc.options.getCameraType().isFirstPerson())
            return;

        if (mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.BLOCK)
            return;

        Block block = mc.player.level()
                .getBlockState(((BlockHitResult) mc.hitResult).getBlockPos())
                .getBlock();
        if (!(block instanceof UntemperedSchismBlock)) return;
        UntemperedSchismBlockEntity schism = (UntemperedSchismBlockEntity) mc.player.level().getBlockEntity(((BlockHitResult) mc.hitResult).getBlockPos());

        if (schism == null)
            return;

        Component text = Component.translatable("overlay.ait.untempered_schism.au", (int) schism.getCurrentFuel(), (int) schism.getMaxFuel());

        stack.pushPose();
        stack.translate((float) drawContext.guiWidth() / 2 - (mc.font.width(text)/2),
                (float) drawContext.guiHeight() / 2 - 12,
                -10);
        drawContext.drawString(mc.font,  text, 0, 0, Color.WHITE.getRGB(), false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        stack.popPose();
    }
}