package dev.amble.ait.datagen.datagen_providers.loot;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.module.ModuleRegistry;
import dev.amble.ait.module.planet.core.PlanetBlocks;
import dev.amble.lib.datagen.loot.AmbleBlockLootTable;
import dev.amble.lib.platform.datagen.PlatformDataOutput;
import java.util.concurrent.CompletableFuture;

public class AITBlockLootTables extends AmbleBlockLootTable {

    public AITBlockLootTables(PlatformDataOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
        ModuleRegistry.instance().iterator().forEachRemaining(module -> module.getBlockRegistry().ifPresent(this::withBlocks));
        this.withBlocks(AITBlocks.class);

        super.generate();

        this.add(AITBlocks.ZEITON_CLUSTER,
                (block) -> createSilkTouchDispatchTable(block, LootItem.lootTableItem(AITItems.ZEITON_SHARD)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(this.registries.lookupOrThrow(Registries.ENCHANTMENT)
                            .getOrThrow(Enchantments.FORTUNE)))
                        .when(MatchTool
                                .toolMatches(ItemPredicate.Builder.item().of(AITTags.Items.CLUSTER_MAX_HARVESTABLES)))
                        .otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(AITItems.ZEITON_SHARD)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))));


        add(AITBlocks.WAYPOINT_BANK, createDoorTable(AITBlocks.WAYPOINT_BANK));

        // Martian
        add(PlanetBlocks.MARTIAN_STONE, createSingleItemTableWithSilkTouch(PlanetBlocks.MARTIAN_STONE, PlanetBlocks.MARTIAN_COBBLESTONE));
        add(PlanetBlocks.MARTIAN_BRICK_SLAB, createSlabItemTable(PlanetBlocks.MARTIAN_BRICK_SLAB));
        add(PlanetBlocks.MARTIAN_COBBLESTONE_SLAB, createSlabItemTable(PlanetBlocks.MARTIAN_COBBLESTONE_SLAB));
        add(PlanetBlocks.SMOOTH_MARTIAN_STONE_SLAB, createSlabItemTable(PlanetBlocks.SMOOTH_MARTIAN_STONE_SLAB));

        // Ore
        add(PlanetBlocks.ANORTHOSITE_COAL_ORE, createOreDrop(PlanetBlocks.ANORTHOSITE_COAL_ORE, Items.COAL));
        add(PlanetBlocks.ANORTHOSITE_COPPER_ORE, createOreDrop(PlanetBlocks.ANORTHOSITE_COPPER_ORE, Items.RAW_COPPER));
        add(PlanetBlocks.ANORTHOSITE_IRON_ORE, createOreDrop(PlanetBlocks.ANORTHOSITE_IRON_ORE, Items.RAW_IRON));
        add(PlanetBlocks.ANORTHOSITE_LAPIS_ORE, createOreDrop(PlanetBlocks.ANORTHOSITE_LAPIS_ORE, Items.LAPIS_LAZULI));
        add(PlanetBlocks.ANORTHOSITE_REDSTONE_ORE, createOreDrop(PlanetBlocks.ANORTHOSITE_REDSTONE_ORE, Items.REDSTONE));
        add(PlanetBlocks.ANORTHOSITE_GOLD_ORE, createOreDrop(PlanetBlocks.ANORTHOSITE_GOLD_ORE, Items.RAW_GOLD));
        add(PlanetBlocks.ANORTHOSITE_DIAMOND_ORE, createOreDrop(PlanetBlocks.ANORTHOSITE_DIAMOND_ORE, Items.DIAMOND));
        add(PlanetBlocks.ANORTHOSITE_EMERALD_ORE, createOreDrop(PlanetBlocks.ANORTHOSITE_EMERALD_ORE, Items.EMERALD));

        add(PlanetBlocks.MARTIAN_COAL_ORE, createOreDrop(PlanetBlocks.MARTIAN_COAL_ORE, Items.COAL));
        add(PlanetBlocks.MARTIAN_COPPER_ORE, createOreDrop(PlanetBlocks.MARTIAN_COPPER_ORE, Items.RAW_COPPER));
        add(PlanetBlocks.MARTIAN_IRON_ORE, createOreDrop(PlanetBlocks.MARTIAN_IRON_ORE, Items.RAW_IRON));
        add(PlanetBlocks.MARTIAN_LAPIS_ORE, createOreDrop(PlanetBlocks.MARTIAN_LAPIS_ORE, Items.LAPIS_LAZULI));
        add(PlanetBlocks.MARTIAN_REDSTONE_ORE, createOreDrop(PlanetBlocks.MARTIAN_REDSTONE_ORE, Items.REDSTONE));
        add(PlanetBlocks.MARTIAN_GOLD_ORE, createOreDrop(PlanetBlocks.MARTIAN_GOLD_ORE, Items.RAW_GOLD));
        add(PlanetBlocks.MARTIAN_DIAMOND_ORE, createOreDrop(PlanetBlocks.MARTIAN_DIAMOND_ORE, Items.DIAMOND));
        add(PlanetBlocks.MARTIAN_EMERALD_ORE, createOreDrop(PlanetBlocks.MARTIAN_EMERALD_ORE, Items.EMERALD));

        // Anorthosite
        add(PlanetBlocks.ANORTHOSITE_BRICK_SLAB, createSlabItemTable(PlanetBlocks.ANORTHOSITE_BRICK_SLAB));
        add(PlanetBlocks.ANORTHOSITE_SLAB, createSlabItemTable(PlanetBlocks.ANORTHOSITE_SLAB));
        add(PlanetBlocks.POLISHED_ANORTHOSITE_SLAB, createSlabItemTable(PlanetBlocks.POLISHED_ANORTHOSITE_SLAB));
    }
}
