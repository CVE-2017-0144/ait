package dev.amble.ait.core.blocks;

import java.util.function.ToIntFunction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.core.util.ShapeUtil;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import dev.amble.lib.api.ICantBreak;

@SuppressWarnings("deprecation")
public class ExteriorBlock extends Block implements EntityBlock, ICantBreak, SimpleWaterloggedBlock {
    public static final byte MAX_ROTATION_INDEX = (byte) RotationSegment.getMaxSegmentIndex();
    private static final int MAX_ROTATIONS = MAX_ROTATION_INDEX + 1;
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    public static final IntegerProperty LEVEL_4 = BlockStateProperties.LEVEL;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE = state -> state.getValue(LEVEL_4);
    public static final VoxelShape LEDGE_DOOM = Block.box(0, 0, -3.5, 16, 1, 16);
    public static final VoxelShape CUBE_NORTH_SHAPE = Shapes.or(
            Block.box(0.0, 0.0, 5.0, 16.0, 32.0, 16.0), Block.box(0, 0, -3.5, 16, 1, 16));
    public static final VoxelShape PORTALS_SHAPE = Shapes.or(
            Block.box(0.0, 0.0, 11.0, 16.0, 32.0, 16.0), Block.box(0, 0, -3.5, 16, 1, 16));

    public static final VoxelShape PORTALS_SHAPE_DIAGONAL = Shapes.or(
            Block.box(11.0, 0.0, 11.0, 16.0, 32.0, 16.0), Block.box(0, 0, -3.5, 16, 1, 16));
    public static final VoxelShape SIEGE_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
    public static final VoxelShape DIAGONAL_SHAPE;

    static {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.125, 0, -0.125, 0.875, 0.0625, 0.875),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.0625, 0.25, 0.875, 2, 0.875),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.0625, 0.1875, 0.875, 2, 0.25),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.0625, 0.3125, 0.25, 2, 0.875),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.0625, 0.375, 0.1875, 2, 0.875),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.0625, 0.0625, 0.875, 2, 0.125),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.0625, 0.125, 0.875, 2, 0.1875),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0.0625, -0.0625, 0.875, 2, 0),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.0625, 0, 0.875, 2, 0.0625),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.0625, -0.125, 0.875, 2, -0.0625),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0.4375, 0.125, 2, 0.875),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.0625, 0.5, 0.0625, 2, 0.875),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.0625, 0.0625, 0.5625, 0, 2, 0.875),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.125, 0.0625, 0.625, -0.0625, 2, 0.875),
                BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.3125, 0, -0.3125, 0.625, 0.0625, 0.625),
                BooleanOp.OR);

        DIAGONAL_SHAPE = shape;
    }

    public ExteriorBlock(Properties settings) {
        super(settings.noOcclusion());

        this.registerDefaultState(
                this.stateDefinition.any().setValue(ROTATION, 0).setValue(WATERLOGGED, false).setValue(LEVEL_4, 4));
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ExteriorBlockEntity exterior && exterior.isLinked()) {
            Tardis tardis = exterior.tardis().get();
            if (tardis != null && tardis.fuel().hasPower()) {
                return 15;
            }
        }
        return 0;
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return getSignal(state, world, pos, direction);
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(ROTATION, 0).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER)
                .setValue(LEVEL_4, 4);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, WATERLOGGED, LEVEL_4);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return AITItems.TARDIS_ITEM.getDefaultInstance();
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return !(Boolean) state.getValue(WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        VoxelShape normal = this.getNormalShape(state, false);

        if (!(blockEntity instanceof ExteriorBlockEntity exterior))
            return normal;

        if (!exterior.isLinked())
            return normal;

        Tardis tardis = exterior.tardis().get();

        if (tardis.siege() == null)
            return normal;

        if (tardis.siege().isActive())
            return SIEGE_SHAPE;

        TravelHandlerBase.State travelState = tardis.travel().getState();

        if (travelState == TravelHandlerBase.State.LANDED || tardis.travel().getAlpha() > 0.75)
            return normal;

        if (DependencyChecker.hasPortals() && AITMod.CONFIG.allowPortalsBoti)
            return PORTALS_SHAPE;

        return Shapes.empty();
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (!(blockEntity instanceof ExteriorBlockEntity exterior) || !exterior.isLinked())
            return getNormalShape(state, false);

        if (!exterior.isLinked())
            return getNormalShape(state, false);

        Tardis tardis = exterior.tardis().get();

        if (tardis.siege().isActive())
            return SIEGE_SHAPE;

        if (tardis.getExterior().getVariant().equals(ExteriorVariantRegistry.DOOM))
            return LEDGE_DOOM;

        if (DependencyChecker.hasPortals() && AITMod.CONFIG.allowPortalsBoti && !tardis.door().isOpen() && tardis.getExterior().getVariant().hasPortals())
            return getNormalShape(state, true);

        if (tardis.chameleon().isApplied())
            return Shapes.empty();

        TravelHandler travel = tardis.travel();

        if (travel.getState() == TravelHandlerBase.State.LANDED
                || travel.isHitboxShown())
            return getNormalShape(state, false);

        if (DependencyChecker.hasPortals() && AITMod.CONFIG.allowPortalsBoti)
            return PORTALS_SHAPE;

        return Shapes.empty();
    }

    // TODO cache this.
    public VoxelShape getNormalShape(BlockState state, boolean ignorePortals) {
        Direction direction = RotationSegment.convertToDirection(state.getValue(ROTATION))
                .orElse(null);

        VoxelShape shape;

        if (direction == null) {
            shape = DependencyChecker.hasPortals() && AITMod.CONFIG.allowPortalsBoti && !ignorePortals ? PORTALS_SHAPE_DIAGONAL : DIAGONAL_SHAPE;
            direction = approximateDirection(state.getValue(ROTATION));
        } else {
            shape = DependencyChecker.hasPortals() && AITMod.CONFIG.allowPortalsBoti && !ignorePortals ? PORTALS_SHAPE : CUBE_NORTH_SHAPE;
        }

        return ShapeUtil.rotate(Direction.NORTH, direction, shape);
    }

    public Direction approximateDirection(int rotation) {
        return switch (rotation) {
            default -> Direction.NORTH;
            case 1, 2, 3 -> Direction.EAST;
            case 5, 6, 7 -> Direction.SOUTH;
            case 9, 10, 11 -> Direction.WEST;
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (!(blockEntity instanceof ExteriorBlockEntity exterior) || !exterior.isLinked())
            return getNormalShape(state, false);

        Tardis tardis = exterior.tardis().get();

        TravelHandlerBase.State travelState = tardis.travel().getState();

        if (travelState == TravelHandlerBase.State.LANDED || tardis.travel().getAlpha() > 0.75)
            return getNormalShape(state, false);

        if (tardis.getExterior().getVariant().equals(ExteriorVariantRegistry.DOOM))
            return LEDGE_DOOM;

        if (DependencyChecker.hasPortals() && AITMod.CONFIG.allowPortalsBoti)
            return PORTALS_SHAPE;

        return Shapes.empty();
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
                              BlockHitResult hit) {
        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        if (!(world.getBlockEntity(pos) instanceof ExteriorBlockEntity exterior))
            return InteractionResult.CONSUME;

        if (exterior.tardis().isEmpty())
            return InteractionResult.FAIL;

        if (hit.getDirection() != Direction.UP)
            exterior.useOn((ServerLevel) world, player.isShiftKeyDown(), player);

        return InteractionResult.CONSUME; // Consume the event regardless of the outcome
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (world.isClientSide())
            return;

        if (world.getBlockEntity(pos) instanceof ExteriorBlockEntity exterior)
            exterior.onEntityCollision(entity);
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ExteriorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level world, @NotNull BlockState state,
            @NotNull BlockEntityType<T> type) {
        return (world1, blockPos, blockState, ticker) -> {
            if (ticker instanceof ExteriorBlockEntity exterior)
                exterior.tick(world, blockPos, blockState, exterior);
        };
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        //FIXME: re-enable this block after the exterior falling issues are resolved :(
        /*
        Tardis tardis = this.findTardis(world, pos);

        if (tardis == null)
            return;

        if (tardis.travel().getState() != TravelHandlerBase.State.LANDED
                || !canFallThrough(world, pos.down())) {
            tardis.flight().shouldFall().set(false);
            return;
        }

        tardis.flight().shouldFall().set(true);

        if (tardis.travel().antigravs().get() && tardis.fuel().hasPower())
            return;

        tardis.flight().onStartFalling(world, state, pos);

        if (state.get(WATERLOGGED))
            state.with(WATERLOGGED, false);
        */
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleTick(pos, this, 2);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos,
            boolean notify) {
        super.neighborChanged(state, world, pos, sourceBlock, sourcePos, notify);

        if (world.isClientSide())
            return;

        Tardis tardis = this.findTardis(((ServerLevel) world), pos);

        if (tardis == null)
            return;

        tardis.<BiomeHandler>handler(TardisComponent.Id.BIOME).update();
    }

    private static boolean canFallThrough(Level world, BlockPos pos) {
        Planet planet = PlanetRegistry.getInstance().get(world);

        if (planet != null && planet.zeroGravity())
            return false;

        BlockState state = world.getBlockState(pos);

        if (world.getBlockState(pos.below()).getBlock() == AITBlocks.EXTERIOR_BLOCK)
            return false;

        return canFallThrough(state);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);

        if (world.isClientSide())
            return;

        if (world.getBlockEntity(pos) instanceof ExteriorBlockEntity exterior)
            exterior.validateExteriorPosition();
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        super.onRemove(state, world, pos, newState, moved);

        if (world.isClientSide())
            return;

        if (world.getBlockEntity(pos) instanceof ExteriorBlockEntity exterior)
            exterior.validateExteriorPosition();
    }

    private static boolean canFallThrough(BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.liquid() || state.canBeReplaced();
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
            LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED))
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));

        world.scheduleTick(pos, this, 2);
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    private Tardis findTardis(ServerLevel world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof ExteriorBlockEntity exterior) {
            if (!exterior.isLinked() || exterior.tardis().isEmpty())
                return null;

            return exterior.tardis().get();
        }

        return null;
    }

    public void onLanding(Tardis tardis, ServerLevel world, BlockPos pos) {
        if (tardis == null)
            return;

        tardis.flight().onLanding(world, pos);
        world.scheduleTick(pos, this, 2);
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        BlockPos blockPos = pos.below();
        if (random.nextInt(16) == 0) {
            if (canFallThrough(world.getBlockState(blockPos))) {
                ParticleUtils.spawnParticleBelow(world, pos, random, ParticleTypes.TOTEM_OF_UNDYING);

                if (world.getBlockEntity(pos) instanceof ExteriorBlockEntity exterior && exterior.isLinked()) {
                    Tardis tardis = exterior.tardis().get();
                    if (tardis.cloak().silent().get())
                        return;
                }
            }
        }

    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), MAX_ROTATIONS));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(ROTATION, mirror.mirror(state.getValue(ROTATION), MAX_ROTATIONS));
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ExteriorBlockEntity exterior) {
            Entity seat = exterior.getSeatEntity(world);
            if (seat != null) {
                seat.remove(Entity.RemovalReason.DISCARDED);
            }
        }
        super.playerWillDestroy(world, pos, state, player);
    }

}
