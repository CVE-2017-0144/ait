package dev.amble.ait.core.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TardisCoralFanBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape UP_SHAPE = Shapes.box(0.125, 0, 0.125, 0.875, 0.5, 0.875);
    private static final VoxelShape DOWN_SHAPE = Shapes.box(0.125, 0.5, 0.125, 0.875, 1, 0.875);
    private static final VoxelShape NORTH_SHAPE = Shapes.box(0.125, 0.125, 0.5, 0.875, 0.875, 1);
    private static final VoxelShape SOUTH_SHAPE = Shapes.box(0.125, 0.125, 0, 0.875, 0.875, 0.5);
    private static final VoxelShape EAST_SHAPE = Shapes.box(0, 0.125, 0.125, 0.5, 0.875, 0.875);
    private static final VoxelShape WEST_SHAPE = Shapes.box(0.5, 0.125, 0.125, 1, 0.875, 0.875);

    public TardisCoralFanBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.UP)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);

        return switch (facing) {
            case UP -> UP_SHAPE;
            case DOWN -> DOWN_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        LevelReader world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Direction face = ctx.getClickedFace();

        BlockPos attachedPos = pos.relative(face.getOpposite());
        BlockState attachedState = world.getBlockState(attachedPos);

        if (canPlaceOn(world, attachedPos, attachedState, face.getOpposite())) {
            return this.defaultBlockState()
                    .setValue(FACING, face)
                    .setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
        }

        for (Direction direction : FACING.getPossibleValues()) {
            attachedPos = pos.relative(direction.getOpposite());
            attachedState = world.getBlockState(attachedPos);
            if (canPlaceOn(world, attachedPos, attachedState, direction.getOpposite())) {
                return this.defaultBlockState()
                        .setValue(FACING, direction)
                        .setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
            }
        }

        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos attachedPos = pos.relative(facing.getOpposite());
        BlockState attachedState = world.getBlockState(attachedPos);
        return canPlaceOn(world, attachedPos, attachedState, facing.getOpposite());
    }

    private boolean canPlaceOn(LevelReader world, BlockPos pos, BlockState state, Direction direction) {
        return state.isFaceSturdy(world, pos, direction);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction,
                                                BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if (direction == state.getValue(FACING).getOpposite() && !state.canSurvive(world, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return state;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}