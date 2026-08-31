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

public class ItemGroupEvents {

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
        IEventBus bus = Platform.modBus();

        if (bus != null)
            bus.addListener(BuildCreativeModeTabContentsEvent.class, e -> {
                ItemGroupEntries entries = wrap(e);

                Event<Modify> ev = EVENTS.get(e.getTabKey());

                if (ev != null)
                    ev.invoker().modify(entries);

                MODIFY_ENTRIES_ALL.invoker().modify(e.getTab(), entries);
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
                // blocks w/o an item come in as air, neoforge throws on it
                if (stack.isEmpty())
                    return;

                event.accept(stack, visibility);
            }

            @Override
            public void addAfter(ItemLike after, ItemLike... items) {
                // insertAfter needs the exact stack, variants carry components
                ItemStack anchor = null;

                for (ItemStack s : event.getParentEntries()) {
                    if (s.getItem() == after.asItem())
                        anchor = s;
                }

                for (ItemLike item : items) {
                    ItemStack stack = new ItemStack(item);

                    if (stack.isEmpty())
                        continue;

                    if (anchor == null || anchor.isEmpty()) {
                        event.accept(stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else {
                        event.insertAfter(anchor, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }

                    anchor = stack;
                }
            }
        };
    }
}
