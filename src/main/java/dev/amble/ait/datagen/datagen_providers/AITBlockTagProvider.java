package dev.amble.ait.datagen.datagen_providers;

import java.util.concurrent.CompletableFuture;

import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.module.ModuleRegistry;
import dev.amble.ait.module.planet.core.PlanetBlocks;
import dev.amble.lib.datagen.tag.AmbleBlockTagProvider;
import dev.amble.lib.platform.datagen.PlatformDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class AITBlockTagProvider extends AmbleBlockTagProvider {
    public AITBlockTagProvider(PlatformDataOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
//TODO: Make the glass tag work on this and the leafs, for now theres just glass and glass pane and the birch leafs as a temporarly thing.
        tag(AITTags.Blocks.SONIC_INTERACTABLE).add(Blocks.IRON_DOOR).add(Blocks.IRON_TRAPDOOR)
                .add(Blocks.TNT)
                .addTag(BlockTags.CANDLES).addTag(BlockTags.CANDLE_CAKES)
                .add(Blocks.REDSTONE_LAMP).add(AITBlocks.EXTERIOR_BLOCK).add(AITBlocks.CONSOLE_GENERATOR)
                .addTag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "glass_panes")))
                .addTag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "glass_blocks")))
                .add(AITBlocks.MACHINE_CASING, AITBlocks.CONSOLE)
                .add(Blocks.BRICKS)
                .add(Blocks.REDSTONE_WIRE, Blocks.COMPARATOR, Blocks.REPEATER, Blocks.LEVER)
                .addTag(BlockTags.BUTTONS)
                .add(Blocks.DAYLIGHT_DETECTOR)
                .add(Blocks.OBSIDIAN);

        tag(AITTags.Blocks.SONIC_CAN_LOCATE).add(AITBlocks.ZEITON_BLOCK).add(AITBlocks.ZEITON_COBBLE)
                .add(AITBlocks.BUDDING_ZEITON).add(AITBlocks.COMPACT_ZEITON).add(AITBlocks.SMALL_ZEITON_BUD)
                .add(AITBlocks.MEDIUM_ZEITON_BUD).add(AITBlocks.LARGE_ZEITON_BUD).add(AITBlocks.ZEITON_CLUSTER)
                .add(Blocks.BELL);

        tag(AITTags.Blocks.WOODEN_BLOCKS)
                .addTag(BlockTags.LOGS)
                .addTag(BlockTags.PLANKS)
                .addTag(BlockTags.WOODEN_BUTTONS)
                .addTag(BlockTags.WOODEN_PRESSURE_PLATES)
                .addTag(BlockTags.WOODEN_DOORS)
                .addTag(BlockTags.WOODEN_TRAPDOORS)
                .addTag(BlockTags.WOODEN_FENCES)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOODEN_STAIRS);

        tag(BlockTags.WALLS)
                .add(AITBlocks.TARDIS_CORAL_WALL);
        tag(BlockTags.FENCES)
                .add(AITBlocks.TARDIS_CORAL_FENCE);

        tag(BlockTags.COAL_ORES).add(PlanetBlocks.ANORTHOSITE_COAL_ORE, PlanetBlocks.MARTIAN_COAL_ORE);
        tag(BlockTags.COPPER_ORES).add(PlanetBlocks.ANORTHOSITE_COPPER_ORE, PlanetBlocks.MARTIAN_COPPER_ORE);
        tag(BlockTags.IRON_ORES).add(PlanetBlocks.ANORTHOSITE_COPPER_ORE, PlanetBlocks.MARTIAN_COPPER_ORE);
        tag(BlockTags.GOLD_ORES).add(PlanetBlocks.ANORTHOSITE_COPPER_ORE, PlanetBlocks.MARTIAN_COPPER_ORE);
        tag(BlockTags.DIAMOND_ORES).add(PlanetBlocks.ANORTHOSITE_DIAMOND_ORE, PlanetBlocks.MARTIAN_DIAMOND_ORE);
        tag(BlockTags.EMERALD_ORES).add(PlanetBlocks.ANORTHOSITE_EMERALD_ORE, PlanetBlocks.MARTIAN_EMERALD_ORE);
        tag(BlockTags.LAPIS_ORES).add(PlanetBlocks.ANORTHOSITE_LAPIS_ORE, PlanetBlocks.MARTIAN_LAPIS_ORE);
        tag(BlockTags.REDSTONE_ORES).add(PlanetBlocks.ANORTHOSITE_REDSTONE_ORE, PlanetBlocks.MARTIAN_REDSTONE_ORE);

        tag(BlockTags.DIRT).add(PlanetBlocks.MARTIAN_SAND).add(PlanetBlocks.REGOLITH);

        tag(BlockTags.DRAGON_IMMUNE).add(AITBlocks.EXTERIOR_BLOCK, AITBlocks.CONSOLE);
        tag(BlockTags.WITHER_IMMUNE).add(AITBlocks.EXTERIOR_BLOCK, AITBlocks.CONSOLE);

        tag(AITTags.Blocks.FLUID_LINK_CAN_CONNECT).add(Blocks.JUKEBOX);

        ModuleRegistry.instance().iterator().forEachRemaining(module -> {
            module.getDataGenerator().ifPresent(generator -> {
                generator.blockTags(this);
            });
            module.getBlockRegistry().ifPresent(this::withBlocks);
        });

        this.withBlocks(AITBlocks.class);
        super.addTags(arg);
    }

    @Override
    public IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> tag(TagKey<Block> tag) {
        return super.tag(tag);
    }
}
