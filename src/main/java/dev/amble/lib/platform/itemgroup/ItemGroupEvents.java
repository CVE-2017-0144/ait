package dev.amble.lib.platform.itemgroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import dev.amble.lib.platform.Platform;
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
        IEventBus modBus = Platform.modBus();

        if (modBus != null)
            modBus.addListener(BuildCreativeModeTabContentsEvent.class, event -> {
                ItemGroupEntries entries = wrap(event);

                Event<Modify> forTab = EVENTS.get(event.getTabKey());

                if (forTab != null)
                    forTab.invoker().modify(entries);

                MODIFY_ENTRIES_ALL.invoker().modify(event.getTab(), entries);
            });
    }

    public static Event<Modify> modifyEntriesEvent(ResourceKey<CreativeModeTab> tab) {
        return EVENTS.computeIfAbsent(tab, key -> EventFactory.createArrayBacked(Modify.class,
                callbacks -> entries -> {
                    for (Modify callback : callbacks) {
                        callback.modify(entries);
                    }
                }));
    }

    private static ItemGroupEntries wrap(BuildCreativeModeTabContentsEvent event) {
        return new ItemGroupEntries() {
            @Override
            public void accept(ItemStack stack, CreativeModeTab.TabVisibility visibility) {
                event.accept(stack, visibility);
            }

            @Override
            public void addAfter(ItemLike after, ItemLike... items) {
                ItemStack anchor = new ItemStack(after);

                for (ItemLike item : items) {
                    ItemStack stack = new ItemStack(item);
                    event.insertAfter(anchor, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    anchor = stack;
                }
            }
        };
    }
}
