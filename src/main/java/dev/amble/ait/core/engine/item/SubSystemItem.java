package dev.amble.ait.core.engine.item;

import static dev.amble.ait.client.util.TooltipUtil.addShiftHiddenTooltip;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.engine.SubSystem;

public class SubSystemItem extends Item {
    protected final SubSystem.IdLike id;

    public SubSystemItem(Properties settings, SubSystem.IdLike id) {
        super(settings);

        this.id = id;
    }

    public SubSystem.IdLike id() {
        return this.id;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);

        addShiftHiddenTooltip(stack, tooltip, tooltips -> {
            tooltip.add(Component.translatable(this.id().toTranslationKey()).withStyle(ChatFormatting.YELLOW));
            tooltip.add(Component.translatable("tooltip.ait.subsystem_item").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        });
    }
}
