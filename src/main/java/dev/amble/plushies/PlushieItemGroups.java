package dev.amble.plushies;

import dev.amble.lib.AmbleKit;
import dev.amble.lib.container.impl.ItemGroupContainer;
import dev.amble.lib.itemgroup.AItemGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class PlushieItemGroups implements ItemGroupContainer {

    public static final AItemGroup PLUSHIES = AItemGroup.builder(AmbleKit.id("item_group"))
            .icon(() -> new ItemStack(PlushieBlocks.MARKETABLE_PLUSHIES.get(0) == null ? Blocks.SAND : PlushieBlocks.MARKETABLE_PLUSHIES.get(0))).build();
}