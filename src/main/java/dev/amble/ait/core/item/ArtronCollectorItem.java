package dev.amble.ait.core.item;

import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import dev.amble.ait.core.AITDataComponents;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import org.jetbrains.annotations.Nullable;

public class ArtronCollectorItem extends Item {
    public static final String AU_LEVEL = "au_level";
    public static final String UUID_KEY = "uuid";
    public static final Integer COLLECTOR_MAX_FUEL = 1500;

    public ArtronCollectorItem(Properties settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        stack.set(AITDataComponents.AU_LEVEL, 0.0);
        return super.getDefaultInstance();
    }

    public static UUID getUuid(ItemStack stack) {
        if (stack.has(AITDataComponents.ITEM_UUID))
            return stack.get(AITDataComponents.ITEM_UUID);
        stack.set(AITDataComponents.ITEM_UUID, UUID.randomUUID());
        return stack.get(AITDataComponents.ITEM_UUID);
    }

    public static double getFuel(ItemStack stack) {
        if (stack.has(AITDataComponents.AU_LEVEL))
            return stack.getOrDefault(AITDataComponents.AU_LEVEL, 0.0);
        stack.set(AITDataComponents.AU_LEVEL, 0.0);
        return 0d;
    }

    public static double addFuel(ItemStack stack, double fuel) {
        double currentFuel = getFuel(stack);
        stack.set(AITDataComponents.AU_LEVEL, getFuel(stack) <= COLLECTOR_MAX_FUEL ? getFuel(stack) + fuel : COLLECTOR_MAX_FUEL);
        if (getFuel(stack) > COLLECTOR_MAX_FUEL)
            stack.set(AITDataComponents.AU_LEVEL, (double) COLLECTOR_MAX_FUEL);
        if (getFuel(stack) == COLLECTOR_MAX_FUEL)
            return fuel - (COLLECTOR_MAX_FUEL - currentFuel);
        return 0;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        ItemStack cellItemStack = context.getItemInHand();
        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        if (player.isShiftKeyDown()) {
            if (world.getBlockEntity(clickedPos) instanceof ExteriorBlockEntity exterior) {
                if (exterior.tardis().isEmpty())
                    return InteractionResult.FAIL;

                double residual = exterior.tardis().get().addFuel(cellItemStack.getOrDefault(AITDataComponents.AU_LEVEL, 0.0));
                cellItemStack.set(AITDataComponents.AU_LEVEL, residual);
                return InteractionResult.CONSUME;
            } else if (world.getBlockEntity(clickedPos) instanceof ConsoleBlockEntity console) {
                if (console.tardis().isEmpty())
                    return InteractionResult.FAIL;

                double residual = console.tardis().get().addFuel(cellItemStack.getOrDefault(AITDataComponents.AU_LEVEL, 0.0));
                cellItemStack.set(AITDataComponents.AU_LEVEL, residual);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.FAIL;
        }

        return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        String text = stack.has(AITDataComponents.AU_LEVEL) ? "" + stack.getOrDefault(AITDataComponents.AU_LEVEL, 0.0) : "0.0";
        tooltip.add(Component.literal(text + " / " + COLLECTOR_MAX_FUEL + ".0").withStyle(ChatFormatting.BLUE));
    }
}
