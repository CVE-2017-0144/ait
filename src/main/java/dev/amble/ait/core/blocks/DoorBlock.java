package dev.amble.ait.core.blocks;

import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.blockentities.DoorBlockEntity;
import dev.amble.ait.core.blocks.types.HorizontalDirectionalBlock;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.util.ShapeUtil;
import dev.amble.ait.data.ShapeMap;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class DoorBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock {

    public static final VoxelShape NORTH_SHAPE = Block.box(0.0, 0.0, 12.1, 16.0, 32.0, 16.0);
    private static final ShapeMap SHAPES = ShapeUtil.rotations(Direction.NORTH, NORTH_SHAPE).build();
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty LEVEL_4 = ExteriorBlock.LEVEL_4;

    static {
        TardisEvents.DOOR_OPEN.register(tardis -> {
            CachedDirectedGlobalPos globalPos = tardis.travel().position();
            BlockPos exteriorPos = globalPos.getPos();
            Level exteriorWorld = globalPos.getWorld();

            LevelChunk chunk = exteriorWorld.getChunkSource().getChunkNow(SectionPos.blockToSectionCoord(exteriorPos.getX()),
                    SectionPos.blockToSectionCoord(exteriorPos.getZ()));

            if (chunk == null)
                return;

            BlockState exteriorState = chunk.getBlockState(exteriorPos);
            if (!tardis.travel().inFlight() && exteriorState.getBlock() instanceof ExteriorBlock)
                setDoorLight(tardis.asServer(), exteriorState.getValue(ExteriorBlock.LEVEL_4));
        });

        TardisEvents.REAL_DOOR_CLOSE.register(tardis -> setDoorLight(tardis.asServer(), 0));
    }

    private static void setDoorLight(ServerTardis tardis, int level) {
        if (!tardis.hasWorld() || !tardis.world().shouldTick()) return;

        ServerLevel world = tardis.world();

        // FIXME: ensure the DOOR_OPEN and DOOR_CLOSE events always get called on the main thread instead of doing this
        world.getServer().execute(() -> {
            BlockPos pos = tardis.getDesktop().getDoorPos().getPos();

            BlockState state = world.getBlockState(pos);
            if (!(state.getBlock() instanceof DoorBlock))
                return;

            world.setBlockAndUpdate(pos, state.setValue(LEVEL_4, level));
        });
    }

    @Override
    public float getExplosionResistance() {
        return 10000f;
    }

    public DoorBlock(Properties settings) {
        super(settings);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(LEVEL_4, 0));
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
            LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED))
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (world.getBlockEntity(pos) instanceof DoorBlockEntity door && door.isLinked() &&
                door.tardis().get().siege() != null && door.tardis().get().siege().isActive())
            return Shapes.empty();

        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
            ItemStack itemStack) {

        super.setPlacedBy(world, pos, state, placer, itemStack);
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player,
            BlockHitResult hit) {
        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        if (world.getBlockEntity(pos) instanceof DoorBlockEntity door)
            door.useOn(world, player.isShiftKeyDown(), player);

        return InteractionResult.CONSUME;
    }

    @Nullable @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state,
            BlockEntityType<T> type) {
        return type == AITBlockEntityTypes.DOOR_BLOCK_ENTITY_TYPE ? DoorBlockEntity::tick : null;
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (world.isClientSide())
            return;

        if (!(world.getBlockEntity(pos) instanceof DoorBlockEntity door))
            return;

        if (!door.isLinked())
            return;

        if (door.tardis().get().siege().isActive())
            return;

//        if (door.tardis().get().stats().getYScale() == 0)
//            return;

        Vec3 expansionBehind = new Vec3(entity.xo, entity.yo, entity.zo).subtract(entity.position());
        Vec3 expansionForward = entity.getDeltaMovement();

        AABB entityBox = entity.getBoundingBox().expandTowards(expansionForward.scale(1.2)).expandTowards(expansionBehind);

        AABB doorShape = this.getShape(state, world, pos, CollisionContext.of(entity)).bounds().move(pos);

        double insideBlockExpanded = 1.0E-7D;

        AABB biggerEntityBox = entityBox.inflate(insideBlockExpanded);
        AABB biggerDoorShape = doorShape.inflate(insideBlockExpanded);

        if (biggerEntityBox.intersects(biggerDoorShape))
            door.onEntityCollision(entity);
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide() && world.getBlockEntity(pos) instanceof DoorBlockEntity door)
            door.onBreak();

        return super.playerWillDestroy(world, pos, state, player);
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED,
                fluidState.getType() == Fluids.WATER);
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DoorBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, LEVEL_4);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return !(Boolean) state.getValue(WATERLOGGED);
    }
}
