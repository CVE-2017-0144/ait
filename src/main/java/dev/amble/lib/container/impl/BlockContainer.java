package dev.amble.lib.container.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import dev.amble.lib.block.ABlockSettings;
import dev.amble.lib.container.RegistryContainer;
import dev.amble.lib.item.AItem;
import dev.amble.lib.mixin.AbstractBlockAccessor;
import dev.amble.lib.platform.itemgroup.ItemGroupEvents;

public abstract class BlockContainer implements RegistryContainer<Block> {

    protected List<Item> items;

    @Override
    public void start(int fields) {
        this.items = new ArrayList<>(fields);
    }

    @Override
    public Class<Block> getTargetClass() {
        return Block.class;
    }

    @Override
    public Registry<Block> getRegistry() {
        return BuiltInRegistries.BLOCK;
    }

    @Override
    public void postProcessField(ResourceLocation identifier, Block value, Field field) {
        if (field.isAnnotationPresent(NoBlockItem.class))
            return;

        Item.Properties itemSettings = null;

        if (((AbstractBlockAccessor) value).getProperties() instanceof ABlockSettings abs)
            itemSettings = abs.itemSettings();

        Item item = this.createBlockItem(value, itemSettings);
        Registry.register(BuiltInRegistries.ITEM, identifier, item);

        // neo fills BY_BLOCK only in its own phase, asItem() is air before
        if (item instanceof BlockItem blockItem)
            blockItem.registerBlocks(Item.BY_BLOCK, item);

        this.items.add(item);
    }

    @Override
    public void finish() {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
            for (Item item : items) {
                if (((AItem) item).amble$group() == group)
                    entries.accept(item);
            }
        });
    }

    public BlockItem createBlockItem(Block block, @Nullable Item.Properties settings) {
        return new BlockItem(block, settings == null ? this.createBlockItemSettings(block) : settings);
    }

    public Item.Properties createBlockItemSettings(Block block) {
        return new Item.Properties();
    }
}
