package dev.amble.ait.core.blocks;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import dev.amble.ait.core.engine.link.IFluidLink;
import dev.amble.ait.core.engine.link.block.CableBlockEntity;
import dev.amble.ait.core.engine.link.block.FluidLinkBlock;
import dev.amble.ait.core.engine.link.block.FluidLinkBlockEntity;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CableBlock extends FluidLinkBlock implements SimpleWaterloggedBlock {

    private static final Direction[] FACINGS = Direction.values();
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ImmutableMap.copyOf(Util.make(Maps.newEnumMap(Direction.class), directions -> {
        directions.put(Direction.NORTH, NORTH);
        directions.put(Direction.EAST, EAST);
        directions.put(Direction.SOUTH, SOUTH);
        directions.put(Direction.WEST, WEST);
        directions.put(Direction.UP, UP);
        directions.put(Direction.DOWN, DOWN);
    }));
    protected final VoxelShape[] connectionsToShape;

    public CableBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
        this.connectionsToShape = this.generateFacingsToShapeMap(0.2f);
    }

    private VoxelShape[] generateFacingsToShapeMap(float radius) {
        float f = 0.5f - radius;
        float g = 0.5f + radius;
        VoxelShape voxelShape = Block.box(f * 16.0f, f * 16.0f, f * 16.0f, g * 16.0f, g * 16.0f, g * 16.0f);
        VoxelShape[] voxelShapes = new VoxelShape[FACINGS.length];
        for (int i = 0; i < FACINGS.length; ++i) {
            Direction direction = FACINGS[i];
            voxelShapes[i] = Shapes.box(0.5 + Math.min(-(float) radius, (double)direction.getStepX() * 0.5), 0.5 + Math.min(-(float) radius, (double)direction.getStepY() * 0.5), 0.5 + Math.min(-(float) radius, (double)direction.getStepZ() * 0.5), 0.5 + Math.max(radius, (double)direction.getStepX() * 0.5), 0.5 + Math.max(radius, (double)direction.getStepY() * 0.5), 0.5 + Math.max(radius, (double)direction.getStepZ() * 0.5));
        }
        VoxelShape[] voxelShapes2 = new VoxelShape[64];
        for (int j = 0; j < 64; ++j) {
            VoxelShape voxelShape2 = voxelShape;
            for (int k = 0; k < FACINGS.length; ++k) {
                if ((j & 1 << k) == 0) continue;
                voxelShape2 = Shapes.or(voxelShape2, voxelShapes[k]);
            }
            voxelShapes2[j] = voxelShape2;
        }
        return voxelShapes2;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(world, pos)) {
            return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
        }
        if (state.getValue(WATERLOGGED))
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        boolean bl = neighborState.is(this) || neighborState.getBlock() instanceof IFluidLink;
        return state.setValue(FACING_PROPERTIES.get(direction), bl);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.connectionsToShape[this.getConnectionMask(state)];
    }

    protected int getConnectionMask(BlockState state) {
        int i = 0;
        for (int j = 0; j < FACINGS.length; ++j) {
            if (!state.getValue(FACING_PROPERTIES.get(FACINGS[j])))
                continue;

            i |= 1 << j;
        }

        return i;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.withConnectionProperties(ctx.getLevel(), ctx.getClickedPos())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    public BlockState withConnectionProperties(BlockGetter world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.below());
        BlockState blockState2 = world.getBlockState(pos.above());
        BlockState blockState3 = world.getBlockState(pos.north());
        BlockState blockState4 = world.getBlockState(pos.east());
        BlockState blockState5 = world.getBlockState(pos.south());
        BlockState blockState6 = world.getBlockState(pos.west());
        return this.defaultBlockState()
                .setValue(DOWN, blockState.is(this) || blockState.getBlock() instanceof IFluidLink)
                .setValue(UP, blockState2.is(this) || blockState2.getBlock() instanceof IFluidLink)
                .setValue(NORTH, blockState3.is(this) || blockState3.getBlock() instanceof IFluidLink)
                .setValue(EAST, blockState4.is(this) || blockState4.getBlock() instanceof IFluidLink)
                .setValue(SOUTH, blockState5.is(this) || blockState5.getBlock() instanceof IFluidLink)
                .setValue(WEST, blockState6.is(this) || blockState6.getBlock() instanceof IFluidLink);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN).add(WATERLOGGED);
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return !(Boolean) state.getValue(WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public FluidLinkBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CableBlockEntity(pos, state);
    }
}