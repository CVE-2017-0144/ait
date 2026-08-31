package dev.amble.ait.core.item.control;

import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
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
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        findControlId(stack).ifPresent(s -> tooltip.add(Component.translatable(s.toLanguageKey("control")).withStyle(ChatFormatting.AQUA)));

        super.appendHoverText(stack, world, tooltip, context);
    }

    public static Optional<ResourceLocation> findControlId(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();

        if (!nbt.contains(CONTROL_ID_KEY))
            return Optional.empty();

        return Optional.of(new ResourceLocation(stack.getOrCreateTag().getString(CONTROL_ID_KEY)));
    }

    public static Optional<ResourceLocation> findConsoleTypeId(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();

        if (!nbt.contains(CONSOLE_TYPE_ID_KEY))
            return Optional.empty();

        return Optional.of(new ResourceLocation(stack.getOrCreateTag().getString(CONSOLE_TYPE_ID_KEY)));
    }
}
