package dev.amble.plushies;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class GiftBoxBlock extends Block implements EntityBlock {

    public static final int MAX_ROTATION_INDEX = RotationSegment.getMaxSegmentIndex();
    private static final int MAX_ROTATIONS = MAX_ROTATION_INDEX + 1;
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    protected static final VoxelShape SHAPE = Block.box(4.0F, 0.0F, 4.0F, 12.0F, 10.0F, 12.0F);

    public GiftBoxBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ROTATION, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        Block[] blocks = PlushieBlocks.getAllMarketablePlushies();
        int randomBlock = world.getRandom().nextIntBetweenInclusive(0, blocks.length - 1);
        ItemStack stack = new ItemStack(blocks[randomBlock]);
        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY() + 0.3d, pos.getZ(), stack);
        itemEntity.setPickUpDelay(40);
        world.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 1f, 1f);
        world.playSound(null, pos, PlushieSounds.BOOP, SoundSource.BLOCKS, 0.4f, 1f);
        world.addFreshEntity(itemEntity);
        world.destroyBlock(pos, false);
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                .setValue(ROTATION, RotationSegment.convertToSegment(ctx.getRotation()));
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GiftBoxBlockEntity(PlushieBlockEntities.GIFT_BOX_BLOCK_ENTITY_TYPE, pos, state);
    }
}
