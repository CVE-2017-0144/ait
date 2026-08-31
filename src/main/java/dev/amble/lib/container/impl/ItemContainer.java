package dev.amble.lib.container.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import dev.amble.lib.container.RegistryContainer;
import dev.amble.lib.item.AItem;

public abstract class ItemContainer implements RegistryContainer<Item> {

    private List<Item> items;

    @Override
    public void start(int fields) {
        this.items = new ArrayList<>(fields);
    }

    @Override
    public Class<Item> getTargetClass() {
        return Item.class;
    }

    @Override
    public Registry<Item> getRegistry() {
        return BuiltInRegistries.ITEM;
    }

    @Override
    public void postProcessField(ResourceLocation identifier, Item value, Field field) {
        this.items.add(value);
    }

    @Override
    public void finish() {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
            for (Item item : items) {
                CreativeModeTab target = ((AItem) item).amble$group();

                if (target == null)
                    target = this.getDefaultGroup();

                if (target == group)
                    entries.accept(item);
            }
        });
    }

    @Nullable public CreativeModeTab getDefaultGroup() {
        return null;
    }
}
