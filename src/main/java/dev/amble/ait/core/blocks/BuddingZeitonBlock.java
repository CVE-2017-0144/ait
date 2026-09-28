package dev.amble.ait.core.blocks;

import dev.amble.ait.core.AITBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class BuddingZeitonBlock extends AmethystBlock {
    public static final int GROW_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    public BuddingZeitonBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            Direction direction = UPDATE_SHAPE_ORDER[random.nextInt(UPDATE_SHAPE_ORDER.length)];
            BlockPos blockPos = pos.relative(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (canGrowIn(blockState)) {
                block = AITBlocks.SMALL_ZEITON_BUD;
            } else if (blockState.is(AITBlocks.SMALL_ZEITON_BUD)
                    && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
                block = AITBlocks.MEDIUM_ZEITON_BUD;
            } else if (blockState.is(AITBlocks.MEDIUM_ZEITON_BUD)
                    && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
                block = AITBlocks.LARGE_ZEITON_BUD;
            } else if (blockState.is(AITBlocks.LARGE_ZEITON_BUD)
                    && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
                block = AITBlocks.ZEITON_CLUSTER;
            }

            if (block != null) {
                BlockState blockState2 = block.defaultBlockState().setValue(AmethystClusterBlock.FACING, direction)
                        .setValue(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getType() == Fluids.WATER);
                world.setBlockAndUpdate(blockPos, blockState2);
            }
        }
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) && state.getFluidState().getAmount() == 8;
    }
}
