package dev.amble.ait.core.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import dev.amble.ait.core.AITDataComponents;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.tardis.handler.StatsHandler;
import dev.amble.ait.core.util.ItemNbt;
import org.jetbrains.annotations.Nullable;

public class TardisMatrixItem extends Item {
    public TardisMatrixItem(Properties settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultInstance() {
        return super.getDefaultInstance();
    }

    public static ItemStack randomize() {
        ItemStack stack = new ItemStack(AITItems.TARDIS_MATRIX);
        CompoundTag nbt = ItemNbt.get(stack);
        stack.set(AITDataComponents.R, (int) (Math.random() * 256));
        stack.set(AITDataComponents.G, (int) (Math.random() * 256));
        stack.set(AITDataComponents.B, (int) (Math.random() * 256));
        nbt.putString("name", StatsHandler.getRandomName());
        ItemNbt.set(stack, nbt);
        return stack;
    }

    public int[] getColor(ItemStack stack) {
        if (!stack.has(AITDataComponents.R) && !stack.has(AITDataComponents.G) && !stack.has(AITDataComponents.B)) {
            return new int[]{255, 255, 255};
        }
        return new int[]{stack.getOrDefault(AITDataComponents.R, 0), stack.getOrDefault(AITDataComponents.G, 0), stack.getOrDefault(AITDataComponents.B, 0)};
    }

    public static int colorToInt(int r, int g, int b) {
        int result = 0;
        result += r;
        result = result << 8;
        result += g;
        result = result << 8;
        result += b;

        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);

        tooltip.add(Component.translatable("tooltip.ait.tardis_matrix.name", ItemNbt.get(stack).getString("name"))
                .withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.literal("#" + colorToInt(getColor(stack)[0], getColor(stack)[1], getColor(stack)[2]))
                .withStyle(ChatFormatting.GRAY));
    }
}
