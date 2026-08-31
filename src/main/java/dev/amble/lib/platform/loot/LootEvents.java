package dev.amble.lib.platform.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

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
        net.fabricmc.fabric.api.loot.v3.LootTableEvents.MODIFY
                .register((id, table, source, registries) -> MODIFY.invoker()
                        .modify(id, table, source.isBuiltin(), registries));
    }
}
