package dev.amble.ait.core.item.control;

import dev.amble.ait.core.util.ItemNbt;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public abstract class ControlBlockItem extends BlockItem {
    public static final String CONTROL_ID_KEY = "controlId";
    public static final String CONSOLE_TYPE_ID_KEY = "consoleTypeId";

    protected ControlBlockItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        findControlId(stack).ifPresent(s -> tooltip.add(Component.translatable(s.toLanguageKey("control")).withStyle(ChatFormatting.AQUA)));

        super.appendHoverText(stack, tooltipContext, tooltip, context);
    }

    public static Optional<ResourceLocation> findControlId(ItemStack stack) {
        CompoundTag nbt = ItemNbt.get(stack);

        if (!nbt.contains(CONTROL_ID_KEY))
            return Optional.empty();

        return Optional.of(ResourceLocation.parse(ItemNbt.get(stack).getString(CONTROL_ID_KEY)));
    }

    public static Optional<ResourceLocation> findConsoleTypeId(ItemStack stack) {
        CompoundTag nbt = ItemNbt.get(stack);

        if (!nbt.contains(CONSOLE_TYPE_ID_KEY))
            return Optional.empty();

        return Optional.of(ResourceLocation.parse(ItemNbt.get(stack).getString(CONSOLE_TYPE_ID_KEY)));
    }
}
