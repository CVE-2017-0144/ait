package dev.amble.lib.platform.itemgroup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;

public interface ItemGroupEntries extends CreativeModeTab.Output {

    void addAfter(ItemLike after, ItemLike... items);
}
