package dev.amble.ait.core.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.api.ArtronHolderItem;
import dev.amble.ait.core.AITBlocks;

public class ChargedZeitonCrystalItem extends Item implements ArtronHolderItem {
    public static final double MAX_FUEL = 5000;

    public ChargedZeitonCrystalItem(Properties settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        CompoundTag nbt = stack.getOrCreateTag();

        nbt.putDouble(FUEL_KEY, getMaxFuel(stack));

        return stack;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        super.onCraftedBy(stack, world, player);
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putDouble(FUEL_KEY, 0);
    }

    @Override
    public double getMaxFuel(ItemStack stack) {
        return MAX_FUEL;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        int currentFuel = (int) Math.round(this.getCurrentFuel(stack));
        ChatFormatting fuelColor = currentFuel > (MAX_FUEL / 4) ? ChatFormatting.GREEN : ChatFormatting.RED;

        tooltip.add(
                Component.translatable("message.ait.artron_units", currentFuel)
                        .withStyle(fuelColor)
                        .append(Component.literal(" / ").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal(String.valueOf(MAX_FUEL)).withStyle(ChatFormatting.GRAY))
        );

        super.appendHoverText(stack, world, tooltip, context);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        Player player = context.getPlayer();

        if (player == null) return InteractionResult.PASS; // This may mess with some automation (like Create) but I don't care - Loqor

        if (state.is(AITBlocks.ZEITON_COBBLE)) {
            context.getLevel().setBlockAndUpdate(context.getClickedPos(), AITBlocks.COMPACT_ZEITON.defaultBlockState());
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }

        if (state.is(Blocks.LODESTONE)) {
            ItemStack stack = context.getItemInHand();
            if (!this.hasMaxFuel(stack)) {
                player.displayClientMessage(Component.translatable("ait.charged_zeiton_crystal.not_max_fuel").append(
                        Component.nullToEmpty(" " + this.getCurrentFuel(stack) + "/" + this.getMaxFuel(stack))), true);
                return InteractionResult.PASS;
            }
            Block block = AITBlocks.UNTEMPERED_SCHISM;
            context.getLevel().setBlockAndUpdate(context.getClickedPos(), block.defaultBlockState());
            AITBlocks.UNTEMPERED_SCHISM.setPlacedBy(player.level(), context.getClickedPos(), block.defaultBlockState(), player, stack);
            if (!player.isCreative()) context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }
}
