package dev.amble.ait.core.blocks;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.core.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.core.blockentities.WaypointBankBlockEntity;
import dev.amble.ait.core.blocks.types.HorizontalDirectionalBlock;
import dev.amble.ait.core.util.WorldUtil;

@SuppressWarnings("deprecation")
public class WaypointBankBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public static final int MAX_COUNT = 16;

    public WaypointBankBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(HALF,
                DoubleBlockHalf.LOWER));
    }

    private static int getSlotForHitPos(Vec2 hitPos, DoubleBlockHalf half) {
        int column = hitPos.x >= 0.5f ? 1 : 0;

        if (half == DoubleBlockHalf.UPPER)
            return column * (MAX_COUNT / 2);

        int row = (int) ((1 - hitPos.y) * MAX_COUNT / 2);
        row = Mth.clamp(row, 0, 6);

        return row + column * (MAX_COUNT / 2) + 1;
    }

    private static Optional<Vec2> getHitPos(BlockHitResult hit, Direction facing) {
        Direction direction = hit.getDirection();

        if (facing != direction)
            return Optional.empty();

        BlockPos blockPos = hit.getBlockPos().relative(direction);
        Vec3 vec3d = hit.getLocation().subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        double x = vec3d.x();
        double y = vec3d.y();
        double z = vec3d.z();

        return switch (direction) {
            case NORTH -> Optional.of(new Vec2((float) (1.0 - x), (float) y));
            case SOUTH -> Optional.of(new Vec2((float) x, (float) y));
            case WEST -> Optional.of(new Vec2((float) z, (float) y));
            case EAST -> Optional.of(new Vec2((float) (1.0 - z), (float) y));
            case DOWN, UP -> Optional.empty();
        };
    }

    @Override
    public void attack(BlockState state, Level world, BlockPos pos, Player player) {
        if (world.getBlockEntity(pos) instanceof WaypointBankBlockEntity bank)
            bank.unselect();
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
            BlockHitResult hit) {
        Optional<Vec2> hitPos = getHitPos(hit, state.getValue(net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING));

        if (hitPos.isEmpty())
            return InteractionResult.PASS;

        if (state.getValue(HALF) == DoubleBlockHalf.UPPER)
            pos = pos.below();

        if (!(world.getBlockEntity(pos) instanceof WaypointBankBlockEntity bank))
            return InteractionResult.PASS;

        int slot = getSlotForHitPos(hitPos.get(), state.getValue(HALF));
        return bank.onUse(world, state, player, hand, slot);
    }

    @Override
    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(),
                pos.getZ());
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER)
            return null;

        return new WaypointBankBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
            LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(HALF);

        if (direction.getAxis() == Direction.Axis.Y && half == DoubleBlockHalf.LOWER == (direction == Direction.UP))
            return neighborState.is(this) && neighborState.getValue(HALF) != half
                    ? state.setValue(FACING, neighborState.getValue(FACING))
                    : Blocks.AIR.defaultBlockState();

        return half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(world, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    @Nullable public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos blockPos = ctx.getClickedPos();
        Level world = ctx.getLevel();

        if (blockPos.getY() < world.getMaxBuildHeight() - 1 && world.getBlockState(blockPos.above()).canBeReplaced(ctx))
            return super.getStateForPlacement(ctx).setValue(HALF, DoubleBlockHalf.LOWER);

        return null;
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide() && player.isCreative())
            WorldUtil.onBreakHalfInCreative(world, pos, state, player);

        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.is(newState.getBlock()))
            return;

        if (world.getBlockEntity(pos) instanceof WaypointBankBlockEntity bank) {
            bank.dropItems();
            world.updateNeighbourForOutputSignal(pos, this);
        }

        super.onRemove(state, world, pos, newState, moved);
    }
}
