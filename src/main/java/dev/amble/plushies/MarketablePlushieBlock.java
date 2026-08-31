package dev.amble.plushies;

import dev.amble.lib.AmbleKit;
import dev.amble.lib.animation.BedrockModelProvider;
import dev.amble.lib.block.ABlock;
import dev.amble.lib.block.ABlockSettings;
import dev.amble.lib.block.AWaterloggableBlock;
import dev.amble.lib.block.behavior.base.BlockWithEntityBehavior;
import dev.amble.lib.blockentity.ABlockEntity;
import dev.amble.lib.client.bedrock.BedrockEntityModel;
import dev.amble.lib.client.bedrock.BedrockModelReference;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MarketablePlushieBlock extends AWaterloggableBlock implements EntityBlock, BedrockModelProvider, Equipable {

    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    public static final int MAX_ROTATION_INDEX = RotationSegment.getMaxSegmentIndex();
    private static final int MAX_ROTATIONS = MAX_ROTATION_INDEX + 1;

    public static final BooleanProperty STACKED = BooleanProperty.create("stacked");
    protected static final VoxelShape SHAPE = Block.box(4.0F, 0.0F, 4.0F, 12.0F, 8.0F, 12.0F);

    private final BedrockModelReference modelRef;

    @Environment(EnvType.CLIENT)
    public BedrockEntityModel<?> model;

    public MarketablePlushieBlock(ABlockSettings settings, String modelId) {
        super(settings, new BlockWithEntityBehavior.Ticking(MarketablePlushieBlockEntity::new));
        
        this.modelRef = new BedrockModelReference(AmbleKit.MOD_ID, modelId);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ROTATION, 0)
                .setValue(STACKED, false)
                .setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public String getTexturePrefix() {
        return "block";
    }

    @Override
    public BedrockModelReference getModel() {
        return this.modelRef;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MarketablePlushieBlockEntity(PlushieBlockEntities.MARKETABLE_PLUSHIE_BLOCK_ENTITY_TYPE, pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof MarketablePlushieBlockEntity be)
            return be.onUse(state, world, pos, player, hand, hit);

        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        Level world = ctx.getLevel();

        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        
        boolean sameAbove = world.getBlockState(pos.above()).is(this);

        return this.defaultBlockState()
                .setValue(ROTATION, RotationSegment.convertToSegment(ctx.getRotation()))
                .setValue(STACKED, sameAbove)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
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
        builder.add(ROTATION, STACKED, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world.isClientSide()) return;

        boolean sameAbove = world.getBlockState(pos.above()).is(this);
        if (state.getValue(STACKED) != sameAbove) {
            world.setBlock(pos, state.setValue(STACKED, sameAbove), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.getBlockEntity(pos) instanceof MarketablePlushieBlockEntity be)
            be.onPlaced(world, pos, state, placer, itemStack);
        if (world.isClientSide()) return;
        boolean sameAbove = world.getBlockState(pos.above()).is(this);
        if (state.getValue(STACKED) != sameAbove) {
            world.setBlock(pos, state.setValue(STACKED, sameAbove), Block.UPDATE_CLIENTS);
        }

        BlockPos below = pos.below();
        BlockState belowState = world.getBlockState(below);
        if (belowState.is(this)) {
            boolean belowSameAbove = world.getBlockState(below.above()).is(this);
            world.setBlock(below, belowState.setValue(STACKED, belowSameAbove), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.is(newState.getBlock())) return;

        if (!world.isClientSide()) {
            BlockPos below = pos.below();
            BlockState belowState = world.getBlockState(below);
            if (belowState.is(this)) {
                boolean belowSameAbove = world.getBlockState(below.above()).is(this);
                world.setBlock(below, belowState.setValue(STACKED, belowSameAbove), Block.UPDATE_CLIENTS);
            }
        }
        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }
}
