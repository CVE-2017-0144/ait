package dev.amble.ait.core.engine.block.generic;

import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import dev.amble.ait.core.engine.block.SubSystemBlock;
import dev.amble.ait.core.engine.link.IFluidLink;
import dev.amble.ait.core.engine.link.block.FluidLinkBlockEntity;
import org.jetbrains.annotations.Nullable;

public class GenericSubSystemBlock extends SubSystemBlock {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
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

    public GenericSubSystemBlock(Properties settings) {
        super(settings, null);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.withConnectionProperties(ctx.getLevel(), ctx.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        boolean canConnect = neighborState.is(this) || neighborState.getBlock() instanceof IFluidLink;
        return state.setValue(FACING_PROPERTIES.get(direction), canConnect);
    }

    public BlockState withConnectionProperties(BlockGetter world, BlockPos pos) {
        return this.defaultBlockState()
                .setValue(DOWN, this.canConnect(world, pos.below()))
                .setValue(UP, this.canConnect(world, pos.above()))
                .setValue(NORTH, this.canConnect(world, pos.north()))
                .setValue(EAST, this.canConnect(world, pos.east()))
                .setValue(SOUTH, this.canConnect(world, pos.south()))
                .setValue(WEST, this.canConnect(world, pos.west()));
    }

    private boolean canConnect(BlockGetter world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.is(this) || state.getBlock() instanceof IFluidLink;
    }

    @Override
    public @Nullable FluidLinkBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GenericStructureSystemBlockEntity(pos, state);
    }
}