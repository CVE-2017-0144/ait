package dev.amble.ait.core.blocks;

import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.blockentities.DetectorBlockEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.handler.TardisCrashHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;

@SuppressWarnings("deprecation")
public class DetectorBlock extends FaceAttachedHorizontalDirectionalBlock implements EntityBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;

    public static final EnumProperty<Type> TYPE = EnumProperty.create("type", Type.class);

    protected static final VoxelShape NORTH_WALL_SHAPE = Block.box(5.0, 4.0, 10.0, 11.0, 12.0, 16.0);
    protected static final VoxelShape SOUTH_WALL_SHAPE = Block.box(5.0, 4.0, 0.0, 11.0, 12.0, 6.0);
    protected static final VoxelShape WEST_WALL_SHAPE = Block.box(10.0, 4.0, 5.0, 16.0, 12.0, 11.0);
    protected static final VoxelShape EAST_WALL_SHAPE = Block.box(0.0, 4.0, 5.0, 6.0, 12.0, 11.0);
    protected static final VoxelShape FLOOR_Z_AXIS_SHAPE = Block.box(5.0, 0.0, 4.0, 11.0, 6.0, 12.0);
    protected static final VoxelShape FLOOR_X_AXIS_SHAPE = Block.box(4.0, 0.0, 5.0, 12.0, 6.0, 11.0);
    protected static final VoxelShape CEILING_Z_AXIS_SHAPE = Block.box(5.0, 10.0, 4.0, 11.0, 16.0, 12.0);
    protected static final VoxelShape CEILING_X_AXIS_SHAPE = Block.box(4.0, 10.0, 5.0, 12.0, 16.0, 11.0);

    public DetectorBlock(BlockBehaviour.Properties settings) {
        super(settings.emissiveRendering((state, world, pos) -> state.getValue(POWERED))
                .lightLevel(value -> value.getValue(POWERED) ? 9 : 3));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWER, 0)
                .setValue(POWERED, false).setValue(FACE, AttachFace.WALL).setValue(TYPE, Type.POWER));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACE)) {
            case FLOOR -> state.getValue(FACING).getAxis() == Direction.Axis.X ? FLOOR_X_AXIS_SHAPE : FLOOR_Z_AXIS_SHAPE;
            case WALL -> switch (state.getValue(FACING)) {
                case EAST -> EAST_WALL_SHAPE;
                case WEST -> WEST_WALL_SHAPE;
                case SOUTH -> SOUTH_WALL_SHAPE;
                default -> NORTH_WALL_SHAPE;
            };
            default -> state.getValue(FACING).getAxis() == Direction.Axis.X ? CEILING_X_AXIS_SHAPE : CEILING_Z_AXIS_SHAPE;
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
            BlockHitResult hit) {
        if (!player.mayBuild())
            return super.use(state, world, pos, player, hand, hit);

        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        state = state.cycle(TYPE);

        world.setBlock(pos, state, Block.UPDATE_INVISIBLE);
        world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
        return InteractionResult.CONSUME;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (moved || state.is(newState.getBlock()))
            return;

        if (state.getValue(POWERED))
            this.updateNeighbors(state, world, pos);

        super.onRemove(state, world, pos, newState, false);
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return state.getValue(POWER);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING, POWERED, POWER, TYPE);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state,
            BlockEntityType<T> type) {
        return checkType(type, AITBlockEntityTypes.DETECTOR_BLOCK_ENTITY_TYPE, DetectorBlock::tick);
    }

    @Nullable protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(
            BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    private void updateNeighbors(BlockState state, Level world, BlockPos pos) {
        world.updateNeighborsAt(pos, this);
        world.updateNeighborsAt(pos.relative(DetectorBlock.getConnectedDirection(state).getOpposite()), this);
    }

    private static void updateState(BlockState state, Level world, BlockPos pos, Tardis tardis) {
        int power = state.getValue(TYPE).getPower(tardis);

        world.setBlock(pos, state.setValue(POWER, power).setValue(POWERED, power != 0), UPDATE_ALL);
    }

    private static void tick(Level world, BlockPos pos, BlockState state, DetectorBlockEntity detector) {
        if (world.isClientSide() || !detector.isLinked())
            return;

        updateState(state, world, pos, detector.tardis().get());
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DetectorBlockEntity(pos, state);
    }

    public enum Type implements StringRepresentable {
        FLIGHT(tardis -> tardis.travel().getState() != TravelHandlerBase.State.LANDED ? 15 : 0), POWER(
                tardis -> tardis.fuel().hasPower() ? 15 : 0), CRASHED(tardis -> {
                    TardisCrashHandler.State state = tardis.crash().getState();

                    if (state == TardisCrashHandler.State.NORMAL)
                        return 0;

                    return state == TardisCrashHandler.State.UNSTABLE ? 7 : 15;
        }),
        DOOR_LOCKED(tardis -> tardis.door().locked() ? 15 : 0),
        DOOR_OPEN(tardis -> tardis.door().isOpen() ? 15 : 0),
        SONIC(tardis -> tardis.getDesktop().getConsolePos().stream().anyMatch(pos -> {
            BlockEntity entity = tardis.asServer().world().getBlockEntity(pos);
            if (entity instanceof ConsoleBlockEntity consoleBlock) {
                return consoleBlock.isLinked() && consoleBlock.getSonicScrewdriver() != null && !consoleBlock.getSonicScrewdriver().isEmpty();
            }
            return false;
        }) ? 15 : 0),
        ALARMS(tardis -> tardis.alarm().isEnabled() ? 15 : 0);

        private final String name;
        private final Function<Tardis, Integer> func;

        Type(Function<Tardis, Integer> func) {
            this.name = this.toString().toLowerCase();
            this.func = func;
        }

        public int getPower(Tardis tardis) {
            return this.func.apply(tardis);
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
