package dev.amble.lib.block;

import dev.amble.lib.block.behavior.api.BlockBehavior;
import dev.amble.lib.block.behavior.api.BlockBehaviorLike;
import dev.amble.lib.block.behavior.api.BlockBehaviors;
import dev.amble.lib.block.behavior.base.*;
import dev.amble.lib.blockentity.ABlockEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

@ApiStatus.Experimental
@SuppressWarnings("deprecation")
public class ABlock extends Block implements EntityBlock {

    private final RenderBlockBehavior render;
    private final BlockPlacementBehavior placement;
    private final BlockRotationBehavior rotation;
    private final BlockWithEntityBehavior entity;

    private static BlockBehavior[] flatBehavior(BlockBehaviorLike[] groups) {
        BlockBehavior[] behaviors = BlockBehaviors.behaviors.toArray(new BlockBehavior[0]);

        for (BlockBehaviorLike like : groups) {
            like.unwrap(behaviors);
        }

        return behaviors;
    }

    private static ABlockSettings attachProperties(ABlockSettings settings, BlockBehavior[] behaviors) {
        List<Property<?>> properties = new ArrayList<>();

        for (BlockBehavior behavior : behaviors) {
            if (behavior == null) continue;
            behavior.appendProperties(properties);
        }

        return settings.properties(properties.toArray(new Property[0]));
    }

    public ABlock(ABlockSettings settings, BlockBehaviorLike... behaviorGroups) {
        this(settings, flatBehavior(behaviorGroups));
    }

    private ABlock(ABlockSettings settings, BlockBehavior[] behaviors) {
        super(attachProperties(settings, behaviors));

        BlockState defState = this.createDefaultState();

        for (BlockBehavior behavior : behaviors) {
            if (behavior == null) continue;

            behavior.init(this);
            defState = behavior.initDefaultState(this, defState);
        }

        this.registerDefaultState(defState);

        this.render = (RenderBlockBehavior) behaviors[BlockBehaviors.RENDER_BLOCK];
        this.placement = (BlockPlacementBehavior) behaviors[BlockBehaviors.BLOCK_PLACEMENT];
        this.rotation = (BlockRotationBehavior) behaviors[BlockBehaviors.BLOCK_ROTATION];
        this.entity = (BlockWithEntityBehavior) behaviors[BlockBehaviors.BLOCK_WITH_ENTITY];
    }

    protected BlockState createDefaultState() {
        return this.defaultBlockState();
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return render.getRenderType(state);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return getPlacementState(this.defaultBlockState(), ctx);
    }

    protected @Nullable BlockState getPlacementState(BlockState state, BlockPlaceContext ctx) {
        return this.placement.getPlacementState(state, ctx);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return this.rotation.rotate(state, rotation);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return this.rotation.mirror(state, mirror);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (entity != null && !state.is(newState.getBlock()) && world.getBlockEntity(pos) instanceof ABlockEntity blockEntity)
            blockEntity.onBreak(state, world, pos, newState);

        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (entity != null && world.getBlockEntity(pos) instanceof ABlockEntity be)
            return be.onUse(state, world, pos, player, hand, hit);

        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (entity != null && world.getBlockEntity(pos) instanceof ABlockEntity be)
            be.onPlaced(world, pos, state, placer, stack);

        super.setPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return entity.createBlockEntity(pos, state);
    }

    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return entity.getTicker(world, state, type);
    }
}
