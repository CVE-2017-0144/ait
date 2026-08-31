package dev.amble.ait.core.item;

import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;

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
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putDouble(AU_LEVEL, 0);
        return super.getDefaultInstance();
    }

    public static UUID getUuid(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();

        if (nbt.contains(UUID_KEY))
            return nbt.getUUID(UUID_KEY);
        nbt.putUUID(UUID_KEY, UUID.randomUUID());
        return nbt.getUUID(UUID_KEY);
    }

    public static double getFuel(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();

        if (nbt.contains(AU_LEVEL))
            return nbt.getDouble(AU_LEVEL);
        nbt.putDouble(AU_LEVEL, 0);
        return 0d;
    }

    public static double addFuel(ItemStack stack, double fuel) {
        CompoundTag nbt = stack.getOrCreateTag();
        double currentFuel = getFuel(stack);
        nbt.putDouble(AU_LEVEL, getFuel(stack) <= COLLECTOR_MAX_FUEL ? getFuel(stack) + fuel : COLLECTOR_MAX_FUEL);
        if (getFuel(stack) > COLLECTOR_MAX_FUEL)
            nbt.putDouble(AU_LEVEL, COLLECTOR_MAX_FUEL);
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
        CompoundTag nbt = cellItemStack.getOrCreateTag();

        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        if (player.isShiftKeyDown()) {
            if (world.getBlockEntity(clickedPos) instanceof ExteriorBlockEntity exterior) {
                if (exterior.tardis().isEmpty())
                    return InteractionResult.FAIL;

                double residual = exterior.tardis().get().addFuel(nbt.getDouble(AU_LEVEL));
                nbt.putDouble(AU_LEVEL, residual);
                return InteractionResult.CONSUME;
            } else if (world.getBlockEntity(clickedPos) instanceof ConsoleBlockEntity console) {
                if (console.tardis().isEmpty())
                    return InteractionResult.FAIL;

                double residual = console.tardis().get().addFuel(nbt.getDouble(AU_LEVEL));
                nbt.putDouble(AU_LEVEL, residual);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.FAIL;
        }

        return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        CompoundTag tag = stack.getOrCreateTag();
        String text = tag.contains(AU_LEVEL) ? "" + tag.getDouble(AU_LEVEL) : "0.0";
        tooltip.add(Component.literal(text + " / " + COLLECTOR_MAX_FUEL + ".0").withStyle(ChatFormatting.BLUE));
    }
}
