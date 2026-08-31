package dev.amble.ait.core.blocks;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.blockentities.PlaqueBlockEntity;
import dev.amble.ait.core.blocks.types.HorizontalDirectionalBlock;
import dev.amble.ait.core.util.ShapeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PlaqueBlock extends HorizontalDirectionalBlock implements EntityBlock {

    protected static final VoxelShape SHAPE = Block.box(-0.25 * 16, 0.125 * 16, 0.875 * 16, 1.25 * 16,
            0.875 * 16, 16);

    public PlaqueBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return ShapeUtil.rotate(Direction.NORTH, state.getValue(FACING), SHAPE);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide && world.getBlockEntity(pos) instanceof PlaqueBlockEntity plaque) {
            if (plaque.onUse((ServerPlayer) player, InteractionHand.MAIN_HAND)) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return ShapeUtil.rotate(Direction.NORTH, state.getValue(FACING), SHAPE);
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PlaqueBlockEntity(pos, state);
    }

    @Override
    public BlockState getAppearance(BlockState state, BlockAndTintGetter renderView, BlockPos pos, Direction side,
                                    @Nullable BlockState sourceState, @Nullable BlockPos sourcePos) {
        return super.getAppearance(state, renderView, pos, side, sourceState, sourcePos);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
