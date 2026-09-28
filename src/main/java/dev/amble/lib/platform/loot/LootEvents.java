package dev.amble.lib.platform.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public class LootEvents {

    public interface Modify {
        void modify(ResourceKey<LootTable> id, LootTable.Builder table, boolean builtin,
                HolderLookup.Provider registries);
    }

    public static final Event<Modify> MODIFY = EventFactory.createArrayBacked(Modify.class,
            callbacks -> (id, table, builtin, registries) -> {
                for (Modify callback : callbacks) {
                    callback.modify(id, table, builtin, registries);
                }
            });

    static {
        NeoForge.EVENT_BUS.addListener(LootTableLoadEvent.class, event -> {
            // a table another listener removed is the shared EMPTY instance
            if (event.getTable() == LootTable.EMPTY)
                return;

            LootTable.Builder collector = LootTable.lootTable();

            // neoforge has no table source, every loaded table counts as builtin
            MODIFY.invoker().modify(event.getKey(), collector, true, event.getRegistries());

            collector.build().pools.forEach(event.getTable()::addPool);
        });
    }
}
