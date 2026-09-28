package dev.amble.ait.module.gun.core.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import dev.amble.ait.api.ArtronHolderItem;
import dev.amble.ait.core.item.ZeitonShardItem;
import dev.amble.ait.core.util.ItemNbt;
import dev.amble.ait.module.gun.core.entity.GunEntityTypes;
import dev.amble.ait.module.gun.core.entity.StaserBoltEntity;
import org.jetbrains.annotations.Nullable;

public class StaserBoltMagazine extends Item implements ArtronHolderItem {
    public StaserBoltMagazine(Properties settings) {
        super(settings);
    }

    public static final double MAX_FUEL = 64;

    public AbstractArrow createStaserbolt(Level world, ItemStack stack, LivingEntity shooter) {
        StaserBoltEntity staserBoltEntity = new StaserBoltEntity(GunEntityTypes.STASER_BOLT_ENTITY_TYPE, world);
        return staserBoltEntity.createFromConstructor(world, shooter);
    }
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        CompoundTag nbt = ItemNbt.get(stack);

        nbt.putDouble(FUEL_KEY, MAX_FUEL);
        ItemNbt.set(stack, nbt);

        return stack;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
        if (otherStack.getItem() instanceof ZeitonShardItem) {
            int shardCount = otherStack.getCount();
            if (stack.getItem() instanceof StaserBoltMagazine mag) {
                double ammo = mag.getCurrentFuel(stack);
                if (clickType == ClickAction.SECONDARY && mag.getCurrentFuel(stack) < mag.getMaxFuel(stack)) {
                    int residual = (int) Math.min(shardCount, Math.ceil(MAX_FUEL - ammo));
                    mag.setCurrentFuel(Math.min(MAX_FUEL, ammo + shardCount), stack);
                    otherStack.shrink(residual);
                    return true;
                }
            }
        }
        return super.overrideOtherStackedOnMe(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        super.onCraftedBy(stack, world, player);
        CompoundTag nbt = ItemNbt.get(stack);
        nbt.putDouble(FUEL_KEY, 0);
        ItemNbt.set(stack, nbt);
    }

    @Override
    public double getMaxFuel(ItemStack stack) {
        return MAX_FUEL;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        int currentFuel = (int) Math.round(this.getCurrentFuel(stack));
        ChatFormatting fuelColor = currentFuel > (MAX_FUEL / 4) ? ChatFormatting.GREEN : ChatFormatting.RED;

        tooltip.add(
                Component.translatable("message.ait.artron_units", currentFuel)
                        .withStyle(fuelColor)
                        .append(Component.literal(" / ").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal(String.valueOf(MAX_FUEL)).withStyle(ChatFormatting.GRAY))
        );

        super.appendHoverText(stack, tooltipContext, tooltip, context);
    }
}
