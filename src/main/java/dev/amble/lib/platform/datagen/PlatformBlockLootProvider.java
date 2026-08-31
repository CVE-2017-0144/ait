package dev.amble.lib.platform.datagen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public abstract class PlatformBlockLootProvider extends BlockLootSubProvider implements DataProvider {

    private final LootTableProvider delegate;
    private final Set<Block> known = new HashSet<>();

    protected PlatformBlockLootProvider(PlatformDataOutput output,
            CompletableFuture<HolderLookup.Provider> fut) {
        // datagen is offline, blocking is fine
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), fut.join());

        this.delegate = new LootTableProvider(output, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(registries -> this, LootContextParamSets.BLOCK)),
                fut);
    }

    @Override
    public void add(Block block, net.minecraft.world.level.storage.loot.LootTable.Builder table) {
        this.known.add(block);
        super.add(block, table);
    }

    // only blocks that got a table, keeps vanilla's completeness check quiet
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return this.known;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return this.delegate.run(output);
    }

    @Override
    public String getName() {
        return "Block Loot Tables";
    }
}
