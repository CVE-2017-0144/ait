package dev.amble.ait.core.blocks;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.blockentities.AITRadioBlockEntity;
import dev.amble.ait.core.blocks.types.HorizontalDirectionalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RadioBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public static final VoxelShape X_AXIS_RADIO = Block.box(2.5, 0.0, 0.0, 13.5, 12.0, 16.0);
    public static final VoxelShape PX_AXIS_TUNER = Block.box(1.5, 2.5, 11.5, 3.5, 4.5, 13.5);
    public static final VoxelShape PX_AXIS_VOLUME = Block.box(1.5, 2.5, 2.5, 3.5, 4.5, 4.5);
    public static final VoxelShape NX_AXIS_TUNER = Block.box(12.5, 2.5, 2.5, 14.5, 4.5, 4.5);
    public static final VoxelShape NX_AXIS_VOLUME = Block.box(12.5, 2.5, 11.5, 14.5, 4.5, 13.5);

    // -------------------------------------------------------------------------------------------------------------------------------------------
    public static final VoxelShape Z_AXIS_RADIO = Block.box(0.0, 0.0, 2.5, 16, 12.0, 13.5);
    public static final VoxelShape PZ_AXIS_TUNER = Block.box(2.5, 2.5, 1.5, 4.5, 4.5, 3.5);
    public static final VoxelShape PZ_AXIS_VOLUME = Block.box(11.5, 2.5, 1.5, 13.5, 4.5, 3.5);
    public static final VoxelShape NZ_AXIS_TUNER = Block.box(11.5, 2.5, 12.5, 13.5, 4.5, 14.5);
    public static final VoxelShape NZ_AXIS_VOLUME = Block.box(2.5, 2.5, 12.5, 4.5, 4.5, 14.5);
    private static final VoxelShape PX_AXIS_SHAPE = Shapes.or(X_AXIS_RADIO, PX_AXIS_TUNER, PX_AXIS_VOLUME);
    private static final VoxelShape PZ_AXIS_SHAPE = Shapes.or(Z_AXIS_RADIO, PZ_AXIS_TUNER, PZ_AXIS_VOLUME);
    private static final VoxelShape NX_AXIS_SHAPE = Shapes.or(X_AXIS_RADIO, NX_AXIS_TUNER, NX_AXIS_VOLUME);
    private static final VoxelShape NZ_AXIS_SHAPE = Shapes.or(Z_AXIS_RADIO, NZ_AXIS_TUNER, NZ_AXIS_VOLUME);

    private VoxelShape shape;

    public RadioBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.shape = Shapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        if (direction.getAxis() == Direction.Axis.X) {
            if (direction == Direction.WEST) {
                this.shape = PX_AXIS_SHAPE;
            } else {
                this.shape = NX_AXIS_SHAPE;
            }
        }
        if (direction.getAxis() == Direction.Axis.Z) {
            if (direction == Direction.NORTH) {
                this.shape = PZ_AXIS_SHAPE;
            } else {
                this.shape = NZ_AXIS_SHAPE;
            }
        }
        return this.shape;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
            BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AITRadioBlockEntity radioBlockEntity) {
            radioBlockEntity.useOn(hit, state, player, world, player.isShiftKeyDown());
        }
        return InteractionResult.CONSUME;
    }

    @Nullable protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(
            BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Nullable @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state,
            BlockEntityType<T> type) {
        return checkType(type, AITBlockEntityTypes.AIT_RADIO_BLOCK_ENTITY_TYPE,
                (world1, pos, state1, be) -> AITRadioBlockEntity.tick(world1, pos, state1, be));
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AITRadioBlockEntity(pos, state);
    }

    @Override
    public BlockState getAppearance(BlockState state, BlockAndTintGetter renderView, BlockPos pos, Direction side,
            @Nullable BlockState sourceState, @Nullable BlockPos sourcePos) {
        return super.getAppearance(state, renderView, pos, side, sourceState, sourcePos);
    }
}
