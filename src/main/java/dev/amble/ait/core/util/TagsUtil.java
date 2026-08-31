package dev.amble.ait.core.util;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class TagsUtil {
    public static Item getRandomItemFromTag(Level world, TagKey<Item> tag) {
        HolderLookup.RegistryLookup<Item> itemLookup = world.registryAccess().lookupOrThrow(Registries.ITEM);
        Optional<HolderSet.Named<Item>> optionalList = itemLookup.get(tag);

        if (optionalList.isEmpty()) {
            return Items.AIR;
        }

        HolderSet.Named<Item> list = optionalList.get();
        Holder<Item> randomEntry = list.get(world.random.nextInt(list.size()));

        return randomEntry.value();
    }

    public static Block getRandomBlockFromTag(Level world, TagKey<Block> tag) {
        HolderLookup.RegistryLookup<Block> blockLookup = world.registryAccess().lookupOrThrow(Registries.BLOCK);
        Optional<HolderSet.Named<Block>> optionalList = blockLookup.get(tag);

        if (optionalList.isEmpty()) {
            return Blocks.AIR;
        }

        HolderSet.Named<Block> list = optionalList.get();
        Holder<Block> randomEntry = list.get(world.random.nextInt(list.size()));

        return randomEntry.value();
    }

}
