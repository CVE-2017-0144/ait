package dev.amble.ait.api;

import dev.amble.ait.core.util.ItemNbt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface ArtronHolderItem {
    String FUEL_KEY = "fuel";

    default double getCurrentFuel(ItemStack stack) {
        CompoundTag nbt = ItemNbt.get(stack);

        if (!nbt.contains(this.getFuelKey())) {
            this.setCurrentFuel(0, stack);
            return 0;
        }

        return nbt.getDouble(this.getFuelKey());
    }

    default void setCurrentFuel(double var, ItemStack stack) {
        CompoundTag nbt = ItemNbt.get(stack);

        nbt.putDouble(this.getFuelKey(), var <= 0 ? 0 : var);
        ItemNbt.set(stack, nbt);
    }

    default double addFuel(double val, ItemStack stack) {
        double current = this.getCurrentFuel(stack);
        double max = this.getMaxFuel(stack);

        this.setCurrentFuel(Math.min(current + val, max), stack);
        return 0;
    }

    default void removeFuel(double var, ItemStack stack) {
        double current = this.getCurrentFuel(stack);

        if (current - var < 0) {
            this.setCurrentFuel(0, stack);
            return;
        }
        this.setCurrentFuel(current - var, stack);
    }

    double getMaxFuel(ItemStack stack);

    default boolean isOutOfFuel(ItemStack stack) {
        return this.getCurrentFuel(stack) <= 0;
    }

    default boolean hasMaxFuel(ItemStack stack) {
        return this.getCurrentFuel(stack) >= this.getMaxFuel(stack);
    }

    default String getFuelKey() {
        return FUEL_KEY;
    }
}
