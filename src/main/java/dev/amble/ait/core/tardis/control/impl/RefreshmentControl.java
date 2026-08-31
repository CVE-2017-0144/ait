package dev.amble.ait.core.tardis.control.impl;


import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.drinks.DrinkRegistry;
import dev.amble.ait.core.drinks.DrinkUtil;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;

public class RefreshmentControl extends Control {
    private int currentIndex = 0;

    public RefreshmentControl() {
        super(AITMod.id("refreshment_control"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        currentIndex = (currentIndex + 1) % DrinkRegistry.getInstance().size();
        ItemStack selectedItem = DrinkUtil.setDrink(new ItemStack(AITItems.MUG), DrinkRegistry.getInstance().toList().get(currentIndex));

        tardis.extra().setRefreshmentItem(selectedItem);
        player.displayClientMessage(Component.translatable("ait.foodmachine.mode.refreshement_set_to", selectedItem.getHoverName()), true);

        return Result.SUCCESS;
    }

    @Override
    public boolean requiresPower() {
        return true;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.ALARM;
    }
}
