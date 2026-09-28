package dev.amble.ait.core.item.blueprint;

import static dev.amble.ait.client.util.TooltipUtil.addShiftHiddenTooltip;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.util.ItemNbt;
import org.jetbrains.annotations.Nullable;

public class BlueprintItem extends Item {

    public BlueprintItem(Properties settings) {
        super(settings.stacksTo(1));
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        BlueprintSchema blueprint = BlueprintRegistry.getInstance().getRandom();

        if (blueprint != null) {
            CompoundTag nbt = ItemNbt.get(stack);
            nbt.putString("Blueprint", blueprint.id().toString());
            ItemNbt.set(stack, nbt);
        }

        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);

        addShiftHiddenTooltip(stack, tooltip, tooltips -> {
            BlueprintSchema blueprint = getSchema(stack);
            if (blueprint == null) return;

            tooltip.add(Component.translatable("ait.blueprint.tooltip").withStyle(ChatFormatting.BLUE)
                    .append(blueprint.text().copy().withStyle(ChatFormatting.GRAY)));

            for (int i = blueprint.inputs().size() - 1; i >= 0; i--) {
                tooltip.add(blueprint.inputs().get(i).text().copy().withStyle(ChatFormatting.DARK_GRAY));
            }
        });

    }

    public static BlueprintSchema getSchema(ItemStack stack) {
        CompoundTag nbt = ItemNbt.get(stack);
        Tag element = nbt.get("Blueprint");

        if (element == null) {
            AITMod.LOGGER.warn("Blueprint item has no blueprint data!");

            BlueprintSchema schema = BlueprintRegistry.getInstance().getRandom();
            nbt.putString("Blueprint", schema.id().toString());
            ItemNbt.set(stack, nbt);
            return schema;
        }

        ResourceLocation id = ResourceLocation.tryParse(element.getAsString());

        if (id == null) {
            AITMod.LOGGER.warn("Couldn't parse blueprint id: '{}'", element.getAsString());
            return null;
        }

        BlueprintSchema schema = BlueprintRegistry.getInstance().get(id);

        if (schema == null) {
            AITMod.LOGGER.warn("Couldn't find blueprint with id: '{}'!", id);
            return null;
        }

        return schema;
    }

    public static ItemStack createStack(BlueprintSchema schema) {
        ItemStack stack = new ItemStack(AITItems.BLUEPRINT);

        setSchema(stack, schema);

        return stack;
    }
    public static ItemStack setSchema(ItemStack stack, BlueprintSchema schema) {
        CompoundTag nbt = ItemNbt.get(stack);
        nbt.putString("Blueprint", schema.id().toString());
        ItemNbt.set(stack, nbt);
        return stack;
    }
}
