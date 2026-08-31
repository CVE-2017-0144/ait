package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.item.HammerItem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.handler.ExtraHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class HammerHangerControl extends Control {

    public HammerHangerControl() {
        super(AITMod.id("hammer_hanger"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console,
                             boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        ExtraHandler handler = tardis.extra();

        if ((leftClick || player.isShiftKeyDown()) && (handler.getConsoleHammer() != null)) {
            ItemStack item;

            item = handler.consoleHammerInserted();

            player.getInventory().placeItemBackInInventory(item);
            handler.insertConsoleHammer(null);
            return Result.SUCCESS_ALT;
        }

        ItemStack stack = player.getMainHandItem();

        if (stack.getItem() instanceof HammerItem) {
            if (handler.getConsoleHammer() == null || handler.getConsoleHammer().isEmpty()) {
                handler.insertConsoleHammer(stack.copy());
                player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            }
        }

        return Result.SUCCESS;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return SoundEvents.CHAIN_HIT;
    }

    @Override
    public boolean requiresPower() {
        return false;
    }
}
