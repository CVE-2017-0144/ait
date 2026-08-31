package dev.amble.ait.core.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GlintItem extends Item {
    public GlintItem(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
