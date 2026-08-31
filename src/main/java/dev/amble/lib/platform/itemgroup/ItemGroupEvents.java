package dev.amble.lib.platform.itemgroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public final class ItemGroupEvents {

    private ItemGroupEvents() {}

    public interface Modify {
        void modify(ItemGroupEntries entries);
    }

    public interface ModifyAll {
        void modify(CreativeModeTab tab, ItemGroupEntries entries);
    }

    private static final Map<ResourceKey<CreativeModeTab>, Event<Modify>> EVENTS = new ConcurrentHashMap<>();

    public static final Event<ModifyAll> MODIFY_ENTRIES_ALL = EventFactory.createArrayBacked(ModifyAll.class,
            callbacks -> (tab, entries) -> {
                for (ModifyAll callback : callbacks) {
                    callback.modify(tab, entries);
                }
            });

    static {
        net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.MODIFY_ENTRIES_ALL
                .register((tab, entries) -> MODIFY_ENTRIES_ALL.invoker().modify(tab, wrap(entries)));
    }

    public static Event<Modify> modifyEntriesEvent(ResourceKey<CreativeModeTab> tab) {
        return EVENTS.computeIfAbsent(tab, key -> {
            Event<Modify> event = EventFactory.createArrayBacked(Modify.class, callbacks -> entries -> {
                for (Modify callback : callbacks) {
                    callback.modify(entries);
                }
            });

            net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.modifyEntriesEvent(key)
                    .register(entries -> event.invoker().modify(wrap(entries)));

            return event;
        });
    }

    private static ItemGroupEntries wrap(net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries entries) {
        return new ItemGroupEntries() {
            @Override
            public void accept(ItemStack stack, CreativeModeTab.TabVisibility visibility) {
                entries.accept(stack, visibility);
            }

            @Override
            public void addAfter(ItemLike after, ItemLike... items) {
                entries.addAfter(after, items);
            }
        };
    }
}
