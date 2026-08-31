package dev.amble.ait.client.util;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class TooltipUtil {

    public static void addShiftHiddenTooltip(ItemStack stack, List<Component> tooltip, Consumer<List<Component>> extraTooltips) {
        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.ait.items.holdformoreinfo")
                    .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
            return;
        }

        extraTooltips.accept(tooltip);
    }

    public static void addMultilineTooltip(List<Component> tooltip, Component text) {
        text.getString().lines()
                .forEach(line -> tooltip.add(Component.literal(line).setStyle(text.getStyle())));
    }

}
