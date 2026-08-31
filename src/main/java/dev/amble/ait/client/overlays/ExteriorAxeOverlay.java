package dev.amble.ait.client.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.blocks.ExteriorBlock;
import dev.amble.ait.core.tardis.Tardis;

public class ExteriorAxeOverlay implements HudRenderCallback {
    @Override
    public void onHudRender(GuiGraphics drawContext, float delta) {
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
        if (!(block instanceof ExteriorBlock)) return;
        ExteriorBlockEntity exterior = (ExteriorBlockEntity) mc.player.level().getBlockEntity(((BlockHitResult) mc.hitResult).getBlockPos());

        if (exterior == null || !exterior.isLinked())
            return;

        Tardis tardis = exterior.tardis().get();

        if (tardis == null)
            return;

        if (!tardis.siege().isActive() && !tardis.isGrowth()
                && !tardis.fuel().hasPower() && tardis.door().locked()
                && !(mc.player.getMainHandItem().getItem() instanceof AxeItem)) {
            stack.pushPose();
            stack.translate((float) drawContext.guiWidth() / 2 - 8f,
                    (float) drawContext.guiHeight() / 2 - 8f,
                    -10);
            drawContext.blit(AITMod.id("textures/gui/overlay/axe_door.png"), 2, -4, 0, 0, 16, 16, 16, 16);
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            stack.popPose();
        }
    }
}