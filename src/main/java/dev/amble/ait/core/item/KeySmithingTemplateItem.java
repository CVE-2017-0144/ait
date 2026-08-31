package dev.amble.ait.core.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class KeySmithingTemplateItem extends Item {

    private static final ChatFormatting TITLE_FORMATTING = ChatFormatting.GRAY;
    private static final ChatFormatting DESCRIPTION_FORMATTING = ChatFormatting.BLUE;
    private final String KEY;
    private final String INGREDIENT;

    public KeySmithingTemplateItem(Properties settings, String key, String ingredient) {
        super(settings);
        this.KEY = key;
        this.INGREDIENT = ingredient;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);
        tooltip.add(Component.translatable("message.ait.keysmithing.upgrade").withStyle(TITLE_FORMATTING));
        tooltip.add(CommonComponents.EMPTY);
        tooltip.add(Component.translatable("message.ait.keysmithing.key").withStyle(TITLE_FORMATTING));
        tooltip.add(CommonComponents.space().append(Component.literal(this.KEY)).withStyle(DESCRIPTION_FORMATTING));
        tooltip.add(Component.translatable("message.ait.keysmithing.ingredient").withStyle(TITLE_FORMATTING));
        tooltip.add(CommonComponents.space().append(Component.literal(this.INGREDIENT)).withStyle(DESCRIPTION_FORMATTING));
    }
}
