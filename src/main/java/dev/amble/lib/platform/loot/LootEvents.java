package dev.amble.lib.platform.loot;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public final class LootEvents {

    private LootEvents() {}

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
            LootTable.Builder collector = LootTable.lootTable();

            MODIFY.invoker().modify(event.getKey(), collector, true, event.getRegistries());

            List<LootPool> added = collector.build().pools;

            if (added.isEmpty())
                return;

            LootTable original = event.getTable();
            List<LootPool> pools = new ArrayList<>(original.pools);
            pools.addAll(added);

            event.setTable(new LootTable(original.paramSet, original.randomSequence, pools,
                    original.functions));
        });
    }
}
