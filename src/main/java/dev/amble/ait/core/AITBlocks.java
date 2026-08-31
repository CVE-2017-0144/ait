package dev.amble.ait.core;


import static dev.amble.ait.core.AITItems.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dev.amble.ait.core.blockentities.ArtronCollectorBlockEntity;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.blocks.*;
import dev.amble.ait.core.blocks.CoralPlantBlock;
import dev.amble.ait.core.blocks.DoorBlock;
import dev.amble.ait.core.blocks.control.RedstoneControlBlock;
import dev.amble.ait.core.engine.block.generic.GenericSubSystemBlock;
import dev.amble.lib.block.ABlockSettings;
import dev.amble.lib.container.impl.BlockContainer;
import dev.amble.lib.container.impl.NoBlockItem;
import dev.amble.lib.datagen.util.AutomaticModel;
import dev.amble.lib.datagen.util.NoBlockDrop;
import dev.amble.lib.datagen.util.NoEnglish;
import dev.amble.lib.datagen.util.PickaxeMineable;
import dev.amble.lib.item.AItemSettings;


public class AITBlocks extends BlockContainer {
    public static Block SNOW_GLOBE;
    @NoBlockItem
    @NoBlockDrop
    @NoEnglish
    public static final Block EXTERIOR_BLOCK = new ExteriorBlock(
            FabricBlockSettings.of().noOcclusion().noParticlesOnBreak().strength(-1.0f, 3600000.0f).noLootTable()
                    .pushReaction(PushReaction.IGNORE).lightLevel(ExteriorBlock.STATE_TO_LUMINANCE));

    @PickaxeMineable
    @NoEnglish
    public static final Block DOOR_BLOCK = new DoorBlock(FabricBlockSettings.of().noOcclusion().noCollission()
            .instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(0.5F, 6.0F).pushReaction(PushReaction.IGNORE).lightLevel(ExteriorBlock.STATE_TO_LUMINANCE));

    @NoBlockDrop
    @NoEnglish
    public static final Block CONSOLE = new ConsoleBlock(
            FabricBlockSettings.of().noOcclusion().noParticlesOnBreak().strength(-1.0f, 3600000.0f).noLootTable()
                    .instrument(NoteBlockInstrument.COW_BELL).pushReaction(PushReaction.IGNORE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    @NoBlockDrop
    public static final Block WAYPOINT_BANK = new WaypointBankBlock(
            FabricBlockSettings.of().noOcclusion().requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 6.0F)
                    .pushReaction(PushReaction.IGNORE).lightLevel(light -> 3));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    @NoEnglish
    public static final Block LANDING_PAD = new LandingPadBlock(FabricBlockSettings.of().requiresCorrectToolForDrops()
            .instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 6.0F).pushReaction(PushReaction.IGNORE));

    @NoEnglish
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ENGINE_BLOCK = new EngineBlock(ABlockSettings.of()
            .itemSettings(new AItemSettings().group(AITItemGroups.FABRICATOR)).requiresCorrectToolForDrops()
            .instrument(NoteBlockInstrument.BASEDRUM).noOcclusion().strength(1.5F, 6.0F).pushReaction(PushReaction.IGNORE));
    @PickaxeMineable
    public static final Block CONSOLE_GENERATOR = new ConsoleGeneratorBlock(
            FabricBlockSettings.of().noOcclusion().noParticlesOnBreak().requiresCorrectToolForDrops().strength(1.5F)
                    .instrument(NoteBlockInstrument.COW_BELL).pushReaction(PushReaction.DESTROY));
    @PickaxeMineable
    @NoEnglish
    public static final Block ARTRON_COLLECTOR_BLOCK = new ArtronCollectorBlock(
            FabricBlockSettings.of().noParticlesOnBreak().requiresCorrectToolForDrops().strength(1F).noOcclusion().lightLevel(state -> 6)
                    .instrument(NoteBlockInstrument.BANJO).pushReaction(PushReaction.IGNORE));

    // Coral Blocks
    @NoEnglish
    public static final Block CORAL_PLANT = new CoralPlantBlock(FabricBlockSettings.of().randomTicks().noOcclusion()
            .noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
    @PickaxeMineable(tool = PickaxeMineable.Tool.STONE)
    public static final Block MATRIX_ENERGIZER = new MatrixEnergizerBlock(FabricBlockSettings.of().randomTicks().noOcclusion()
            .lightLevel(light -> 0).instrument(NoteBlockInstrument.COW_BELL)
            .strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.ANVIL).pushReaction(PushReaction.IGNORE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block TARDIS_CORAL_BLOCK = new Block(FabricBlockSettings.of().mapColor(MapColor.GOLD).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(7f, 6.0f));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block TARDIS_CORAL_STAIRS = new StairBlock(TARDIS_CORAL_BLOCK.defaultBlockState(), FabricBlockSettings.of().mapColor(MapColor.GOLD).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(7f, 6.0f));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block TARDIS_CORAL_FENCE = new FenceBlock(FabricBlockSettings.of().mapColor(MapColor.GOLD).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(7f, 6.0f));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block TARDIS_CORAL_WALL = new WallBlock(FabricBlockSettings.of().mapColor(MapColor.GOLD).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(7f, 6.0f));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block TARDIS_CORAL_LEAVES = new LeavesBlock(ABlockSettings.copyOf(Blocks.CHERRY_LEAVES));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block TARDIS_CORAL_SLAB = new SlabBlock(FabricBlockSettings.of().mapColor(MapColor.GOLD).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(7f, 6.0f));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block TARDIS_CORAL_FAN = new TardisCoralFanBlock(FabricBlockSettings.of().mapColor(MapColor.GOLD).noCollission().instabreak().sound(SoundType.WET_GRASS).pushReaction(PushReaction.DESTROY).requiresCorrectToolForDrops().strength(7f, 6.0f));

    // TARDIS Blocks

    @PickaxeMineable(tool = PickaxeMineable.Tool.STONE)
    @NoEnglish
    public static final Block MONITOR_BLOCK = new MonitorBlock(FabricBlockSettings.of().noOcclusion().requiresCorrectToolForDrops()
            .instrument(NoteBlockInstrument.COW_BELL).strength(1.5F, 6.0F).pushReaction(PushReaction.DESTROY));
    @NoEnglish
    public static final Block PLAQUE_BLOCK = new PlaqueBlock(
            FabricBlockSettings.of().noOcclusion().noParticlesOnBreak().instrument(NoteBlockInstrument.COW_BELL)
                    .strength(1.5F, 6.0F).pushReaction(PushReaction.DESTROY));
    @NoEnglish
    public static final Block WALL_MONITOR_BLOCK = new WallMonitorBlock(
            FabricBlockSettings.of().noOcclusion().noParticlesOnBreak().instrument(NoteBlockInstrument.COW_BELL)
                    .strength(1.5F, 6.0F).pushReaction(PushReaction.DESTROY));
    @NoEnglish
    public static final Block DETECTOR_BLOCK = new DetectorBlock(FabricBlockSettings.of().noOcclusion()
            .instrument(NoteBlockInstrument.COW_BELL).strength(1.5F, 6.0F).pushReaction(PushReaction.NORMAL));

    // Zeiton Blocks

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    @NoEnglish
    public static final Block ZEITON_BLOCK = new AmethystBlock(FabricBlockSettings.of().mapColor(MapColor.WARPED_STEM)
            .strength(1.5F, 6.0F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops());

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block BUDDING_ZEITON = new BuddingZeitonBlock(
            FabricBlockSettings.of().mapColor(MapColor.WARPED_STEM).randomTicks().strength(1.5F, 6.0F)
                    .sound(SoundType.AMETHYST).requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY));
    @NoBlockDrop
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ZEITON_CLUSTER = new AmethystClusterBlock(7, 3,
            FabricBlockSettings.of().mapColor(MapColor.WARPED_STEM).forceSolidOn().noOcclusion().randomTicks()
                    .sound(SoundType.AMETHYST_CLUSTER).strength(1.5F, 6.0F).lightLevel((state) -> 5)
                    .pushReaction(PushReaction.DESTROY));

    @PickaxeMineable(tool = PickaxeMineable.Tool.STONE)
    public static final Block LARGE_ZEITON_BUD = new AmethystClusterBlock(5, 3,
            FabricBlockSettings.copyOf(ZEITON_CLUSTER).sound(SoundType.MEDIUM_AMETHYST_BUD).forceSolidOn()
                    .lightLevel((state) -> 4).pushReaction(PushReaction.DESTROY));

    @PickaxeMineable(tool = PickaxeMineable.Tool.STONE)
    public static final Block MEDIUM_ZEITON_BUD = new AmethystClusterBlock(4, 3,
            FabricBlockSettings.copyOf(ZEITON_CLUSTER).sound(SoundType.LARGE_AMETHYST_BUD).forceSolidOn()
                    .lightLevel((state) -> 2).pushReaction(PushReaction.DESTROY));

    @PickaxeMineable(tool = PickaxeMineable.Tool.STONE)
    public static final Block SMALL_ZEITON_BUD = new AmethystClusterBlock(3, 4,
            FabricBlockSettings.copyOf(ZEITON_CLUSTER).sound(SoundType.SMALL_AMETHYST_BUD).forceSolidOn()
                    .lightLevel((state) -> 1).pushReaction(PushReaction.DESTROY));

    @PickaxeMineable(tool = PickaxeMineable.Tool.STONE)
    @AutomaticModel
    public static final Block COMPACT_ZEITON = new Block(FabricBlockSettings.copyOf(ZEITON_BLOCK));

    @PickaxeMineable(tool = PickaxeMineable.Tool.STONE)
    @AutomaticModel
    public static final Block ZEITON_COBBLE = new Block(FabricBlockSettings.copyOf(ZEITON_BLOCK));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    @AutomaticModel()
    public static final Block POWER_CONVERTER = new PowerConverterBlock(ABlockSettings.of()
            .itemSettings(new AItemSettings().group(AITItemGroups.FABRICATOR)).noOcclusion()
            .requiresCorrectToolForDrops().instrument(NoteBlockInstrument.COW_BELL).strength(1.5F, 6.0F).pushReaction(PushReaction.DESTROY));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    @NoEnglish
    public static final Block GENERIC_SUBSYSTEM = new GenericSubSystemBlock(ABlockSettings.of().lightLevel(5)
            .itemSettings(new AItemSettings().group(AITItemGroups.FABRICATOR)).noOcclusion()
            .requiresCorrectToolForDrops().instrument(NoteBlockInstrument.COW_BELL).strength(1.5F, 6.0F).pushReaction(PushReaction.DESTROY));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    @NoEnglish
    public static final Block FOOD_MACHINE = new FoodMachineBlock(ABlockSettings.of()
            .itemSettings(new AItemSettings().group(AITItemGroups.MAIN)).noOcclusion()
            .requiresCorrectToolForDrops().instrument(NoteBlockInstrument.COW_BELL).strength(1.5F, 6.0F).pushReaction(PushReaction.DESTROY));

    @NoBlockItem
    public static final Block RADIO = new RadioBlock(FabricBlockSettings.of().noOcclusion());

    // Machines
    @NoBlockItem
    public static final Block MACHINE_CASING = new MachineCasingBlock(FabricBlockSettings.of().noOcclusion()
            .requiresCorrectToolForDrops().instrument(NoteBlockInstrument.COW_BELL).strength(1.5F, 6.0F));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block FABRICATOR = new FabricatorBlock(ABlockSettings.of()
            .itemSettings(new AItemSettings().group(AITItemGroups.FABRICATOR)).noOcclusion()
            .requiresCorrectToolForDrops().instrument(NoteBlockInstrument.COW_BELL).strength(1.5F, 6.0F));

    @AutomaticModel(justItem = true)
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ASTRAL_MAP = new AstralMapBlock(FabricBlockSettings.of().noOcclusion().strength(1.5F, 6.0F));

    // Control Blocks
    @NoBlockItem
    @NoEnglish
    public static final Block REDSTONE_CONTROL_BLOCK = new RedstoneControlBlock(
            FabricBlockSettings.of().noOcclusion().strength(1.5F, 6.0F).pushReaction(PushReaction.DESTROY));

    public static final Block ENVIRONMENT_PROJECTOR = new EnvironmentProjectorBlock(FabricBlockSettings.of());

    // TODO ADVENT
    static {
        if (isUnlockedOnThisDay(Calendar.DECEMBER, 30)) {
            SNOW_GLOBE = new SnowGlobeBlock(FabricBlockSettings.of().noOcclusion().instrument(NoteBlockInstrument.GUITAR).strength(1.5F, 6.0F));
        }
    }

    static {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.addAfter(Items.AMETHYST_CLUSTER, ZEITON_BLOCK);
            entries.addAfter(ZEITON_BLOCK, BUDDING_ZEITON);
            entries.addAfter(BUDDING_ZEITON, SMALL_ZEITON_BUD);
            entries.addAfter(SMALL_ZEITON_BUD, MEDIUM_ZEITON_BUD);
            entries.addAfter(MEDIUM_ZEITON_BUD, LARGE_ZEITON_BUD);
            entries.addAfter(LARGE_ZEITON_BUD, ZEITON_CLUSTER);
            entries.addAfter(ZEITON_CLUSTER, CHARGED_ZEITON_CRYSTAL);

            entries.addAfter(Items.RAW_GOLD, COMPACT_ZEITON);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.AMETHYST_SHARD, ZEITON_SHARD);
            entries.addAfter(Items.SUGAR, ZEITON_DUST);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(Items.COBBLESTONE, ZEITON_COBBLE);
        });

    }

    @NoEnglish
    public static final Block CABLE_BLOCK = new CableBlock(ABlockSettings.of()
            .itemSettings(new AItemSettings().group(AITItemGroups.FABRICATOR)).noOcclusion()
            .instrument(NoteBlockInstrument.GUITAR).strength(1.5F, 6.0F));

    @NoEnglish

    public static final Block CABLE_CONNECTOR_BLOCK = new FullCableBlock(ABlockSettings.of()
            .itemSettings(new AItemSettings().group(AITItemGroups.FABRICATOR)).noOcclusion()
            .instrument(NoteBlockInstrument.GUITAR).strength(1.5F, 6.0F));

    public static final Block UNTEMPERED_SCHISM = new UntemperedSchismBlock(ABlockSettings.of().itemSettings(
            new AItemSettings().group(AITItemGroups.MAIN)).lightLevel(7)
    );

    @NoBlockItem
    @NoBlockDrop
    @NoEnglish
    public static final Block POTTED_SONIC_SCREWDRIVER = new PottedSonicScrewdriverBlock(FabricBlockSettings.copyOf(Blocks.POTTED_POPPY));
    public static List<Block> get() {
        List<Block> list = new ArrayList<>();

        for (Block block : BuiltInRegistries.BLOCK) {
            if (BuiltInRegistries.BLOCK.getKey(block).getNamespace().equalsIgnoreCase(AITMod.MOD_ID)) {
                list.add(block);
            }
        }

        return list;
    }

    @Override
    public Item.Properties createBlockItemSettings(Block block) {
        return new AItemSettings().group(AITItemGroups.MAIN);
    }
}
