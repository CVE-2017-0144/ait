package dev.amble.ait.client.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.core.item.SonicItem;

public class SonicOverlay implements HudRenderCallback {

    public static final ResourceLocation OVERLAY = AITMod.id("textures/gui/overlay/sonic_can_interact.png");

    @Override
    public void onHudRender(GuiGraphics drawContext, float v) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.level == null || mc.hitResult == null)
            return;

        if (!mc.options.getCameraType().isFirstPerson())
            return;

        if ((mc.player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() == AITItems.SONIC_SCREWDRIVER
                || mc.player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() == AITItems.SONIC_SCREWDRIVER)
                && playerIsLookingAtSonicInteractable(mc.hitResult, mc.player)) {
            this.renderOverlay(drawContext, OVERLAY);
        }
    }

    private boolean playerIsLookingAtSonicInteractable(HitResult crosshairTarget, Player player) {
        if (player != null) {
            if (player.getMainHandItem().getItem() instanceof SonicItem) {
                ItemStack sonic = player.getMainHandItem();
                if (sonic == null)
                    return false;
                CompoundTag nbt = sonic.getOrCreateTag();
                if (!nbt.contains(SonicItem.FUEL_KEY))
                    return false;
                if (crosshairTarget.getType() == HitResult.Type.BLOCK) {
                    Block block = player.level().getBlockState(((BlockHitResult) crosshairTarget).getBlockPos())
                            .getBlock();
                    return !(block instanceof AirBlock) && nbt.getDouble(SonicItem.FUEL_KEY) > 0
                            && player.level().getBlockState(((BlockHitResult) crosshairTarget).getBlockPos())
                            .is(AITTags.Blocks.SONIC_INTERACTABLE);
                }
            } else if (player.getOffhandItem().getItem() instanceof SonicItem) {
                ItemStack sonic = player.getOffhandItem();
                if (sonic == null)
                    return false;
                CompoundTag nbt = sonic.getOrCreateTag();
                if (!nbt.contains(SonicItem.FUEL_KEY))
                    return false;
                if (crosshairTarget.getType() == HitResult.Type.BLOCK) {
                    Block block = player.level().getBlockState(((BlockHitResult) crosshairTarget).getBlockPos())
                            .getBlock();
                    return !(block instanceof AirBlock) && nbt.getDouble(SonicItem.FUEL_KEY) > 0
                            && player.level().getBlockState(((BlockHitResult) crosshairTarget).getBlockPos())
                            .is(AITTags.Blocks.SONIC_INTERACTABLE);
                }
            }
        }
        return false;
    }

    private void renderOverlay(GuiGraphics context, ResourceLocation texture) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        context.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        context.blit(texture, (context.guiWidth() / 2) - 8,
                (context.guiHeight() / 2) - 24, 0, 0.0F, 0.0F, 16, 16, 16, 16);

        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        context.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
