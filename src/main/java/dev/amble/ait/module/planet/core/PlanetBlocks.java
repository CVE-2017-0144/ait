package dev.amble.ait.module.planet.core;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.PushReaction;
import dev.amble.ait.core.blocks.FlagBlock;
import dev.amble.ait.module.planet.PlanetModule;
import dev.amble.ait.module.planet.core.block.OxygenatorBlock;
import dev.amble.lib.container.impl.BlockContainer;
import dev.amble.lib.datagen.util.AutomaticModel;
import dev.amble.lib.datagen.util.NoBlockDrop;
import dev.amble.lib.datagen.util.PickaxeMineable;
import dev.amble.lib.item.AItemSettings;

public class PlanetBlocks extends BlockContainer {

    public static final Block FLAG = new FlagBlock(
            FabricBlockSettings.of().noOcclusion().strength(0.01F, 0.01F).pushReaction(PushReaction.DESTROY));

    // Tech

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block OXYGENATOR_BLOCK = new OxygenatorBlock(
            FabricBlockSettings.copy(Blocks.IRON_BLOCK));

    // Mars

        // Stone

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_STONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.STONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_STONE_WALL = new WallBlock(
            BlockBehaviour.Properties.copy(Blocks.STONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_STONE_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.STONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_STONE_STAIRS = new StairBlock(
            MARTIAN_STONE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_STONE_BUTTON = new ButtonBlock(
            BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON), BlockSetType.STONE, 10, false);

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_STONE_PRESSURE_PLATE  = new PressurePlateBlock(
            PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE),BlockSetType.STONE);

        // Ores

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.NONE)
    public static final Block MARTIAN_COAL_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.COAL_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_COPPER_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.COPPER_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_IRON_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.IRON_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_GOLD_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.GOLD_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_LAPIS_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.LAPIS_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_REDSTONE_ORE = new RedStoneOreBlock(
            BlockBehaviour.Properties.copy(Blocks.REDSTONE_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_DIAMOND_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_EMERALD_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.EMERALD_ORE));

        // Cobblestone

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_COBBLESTONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.COBBLESTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_COBBLESTONE_WALL = new WallBlock(
            BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_WALL));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    @NoBlockDrop
    public static final Block MARTIAN_COBBLESTONE_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_SLAB));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_COBBLESTONE_STAIRS = new StairBlock(
            MARTIAN_COBBLESTONE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.COBBLESTONE_STAIRS));

        // Mossy Cobblestone

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOSSY_MARTIAN_COBBLESTONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.MOSSY_COBBLESTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOSSY_MARTIAN_COBBLESTONE_WALL = new WallBlock(
            BlockBehaviour.Properties.copy(Blocks.MOSSY_COBBLESTONE_WALL));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOSSY_MARTIAN_COBBLESTONE_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.MOSSY_COBBLESTONE_SLAB));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOSSY_MARTIAN_COBBLESTONE_STAIRS = new StairBlock(
            MOSSY_MARTIAN_COBBLESTONE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.MOSSY_COBBLESTONE_STAIRS));

        // Polished Stone

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block POLISHED_MARTIAN_STONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block POLISHED_MARTIAN_STONE_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE_SLAB));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block POLISHED_MARTIAN_STONE_STAIRS = new StairBlock(
            POLISHED_MARTIAN_STONE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE_STAIRS));


        // Smooth Stone

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block SMOOTH_MARTIAN_STONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    @NoBlockDrop
    public static final Block SMOOTH_MARTIAN_STONE_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE_SLAB));

        // Sand

    public static final Block MARTIAN_SAND = new FallingBlock(
            BlockBehaviour.Properties.copy(Blocks.SAND));

        // Martian Sandstone

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_SANDSTONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_SANDSTONE_WALL = new WallBlock(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_SANDSTONE_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_SANDSTONE_BRICK_WALL = new WallBlock(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_SANDSTONE_BRICK_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_SANDSTONE_BRICK_STAIRS = new StairBlock(
            POLISHED_MARTIAN_STONE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_SANDSTONE_STAIRS = new StairBlock(
            POLISHED_MARTIAN_STONE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block CRACKED_MARTIAN_SANDSTONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block POLISHED_MARTIAN_SANDSTONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_SANDSTONE_PILLAR = new RotatedPillarBlock(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_SANDSTONE_BRICKS = new Block(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block CRACKED_MARTIAN_SANDSTONE_BRICKS = new Block(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block CHISELED_MARTIAN_SANDSTONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));


        // Bricks

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_BRICKS = new Block(
            BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_BRICK_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_BRICK_STAIRS = new StairBlock(
            MARTIAN_BRICKS.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_BRICK_WALL = new WallBlock(
            BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL));

        // Other

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MARTIAN_PILLAR = new RotatedPillarBlock(
            BlockBehaviour.Properties.copy(Blocks.QUARTZ_PILLAR));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block CHISELED_MARTIAN_STONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block CRACKED_MARTIAN_BRICKS = new Block(
            BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS));

        // Infested Blocks

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block INFESTED_MARTIAN_STONE = new InfestedBlock(
            PlanetBlocks.MARTIAN_STONE, BlockBehaviour.Properties.copy(Blocks.INFESTED_STONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block INFESTED_MARTIAN_COBBLESTONE = new InfestedBlock(
            PlanetBlocks.MARTIAN_COBBLESTONE, BlockBehaviour.Properties.copy(Blocks.INFESTED_COBBLESTONE));



    // Moon

        // Anorthosite

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE = new Block(
            BlockBehaviour.Properties.copy(Blocks.STONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_WALL = new WallBlock(
            BlockBehaviour.Properties.copy(Blocks.STONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.STONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_STAIRS = new StairBlock(
            ANORTHOSITE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE));

        // Ores

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.NONE)
    public static final Block ANORTHOSITE_COAL_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.COAL_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_COPPER_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.COPPER_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_IRON_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.IRON_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_GOLD_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.GOLD_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_LAPIS_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.LAPIS_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_REDSTONE_ORE = new RedStoneOreBlock(
            BlockBehaviour.Properties.copy(Blocks.REDSTONE_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_DIAMOND_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE));

    @AutomaticModel
    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_EMERALD_ORE = new Block(
            BlockBehaviour.Properties.copy(Blocks.EMERALD_ORE));

        // Polished Stone

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block POLISHED_ANORTHOSITE = new Block(
            BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block POLISHED_ANORTHOSITE_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE_SLAB));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block POLISHED_ANORTHOSITE_STAIRS = new StairBlock(
            POLISHED_ANORTHOSITE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE_STAIRS));

        // Smooth Stone

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block SMOOTH_ANORTHOSITE = new Block(
            BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block SMOOTH_ANORTHOSITE_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.SMOOTH_STONE_SLAB));

        // Sand (Regolith)

    public static final Block REGOLITH = new FallingBlock(
            BlockBehaviour.Properties.copy(Blocks.SAND));

        // Sandstone

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOON_SANDSTONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOON_SANDSTONE_WALL = new WallBlock(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOON_SANDSTONE_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOON_SANDSTONE_STAIRS = new StairBlock(
            POLISHED_MARTIAN_STONE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block CRACKED_MOON_SANDSTONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block POLISHED_MOON_SANDSTONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOON_SANDSTONE_PILLAR = new RotatedPillarBlock(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOON_SANDSTONE_BRICKS = new Block(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOON_SANDSTONE_BRICK_WALL = new WallBlock(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOON_SANDSTONE_BRICK_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block MOON_SANDSTONE_BRICK_STAIRS = new StairBlock(
            POLISHED_MARTIAN_STONE.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block CRACKED_MOON_SANDSTONE_BRICKS = new Block(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block CHISELED_MOON_SANDSTONE = new Block(
            BlockBehaviour.Properties.copy(Blocks.SANDSTONE));

        // Bricks

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_BRICKS = new Block(
            BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS));


    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_BRICK_SLAB = new SlabBlock(
            BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_BRICK_STAIRS = new StairBlock(
            ANORTHOSITE_BRICKS.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_BRICK_WALL = new WallBlock(
            BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL));

        // Other

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block ANORTHOSITE_PILLAR = new RotatedPillarBlock(
            BlockBehaviour.Properties.copy(Blocks.QUARTZ_PILLAR));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block CHISELED_ANORTHOSITE = new Block(
            BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS));

    @PickaxeMineable(tool = PickaxeMineable.Tool.IRON)
    public static final Block CRACKED_ANORTHOSITE_BRICKS = new Block(
            BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS));

    @Override
    public Item.Properties createBlockItemSettings(Block block) {
        return new AItemSettings().group(PlanetModule.instance().getItemGroup());
    }
}
