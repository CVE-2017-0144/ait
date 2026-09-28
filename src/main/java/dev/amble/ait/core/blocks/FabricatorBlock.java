package dev.amble.ait.core.blocks;

import static dev.amble.ait.client.util.TooltipUtil.addShiftHiddenTooltip;

import java.util.List;

import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.blockentities.FabricatorBlockEntity;
import dev.amble.ait.core.blocks.types.HorizontalDirectionalBlock;
import org.jetbrains.annotations.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FabricatorBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public static final VoxelShape DEFAULT_SHAPE = Shapes.box(0, 0, 0, 1, (double) 2 / 16, 1);

    public FabricatorBlock(Properties settings) {
        super(settings);

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
            ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        this.registerDefaultState(state.setValue(FACING, placer.getDirection().getOpposite()));
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock()) && world.getBlockEntity(pos) instanceof FabricatorBlockEntity be)
            be.onBroken();

        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof FabricatorBlockEntity be) {
            be.useOn(state, world, player.isShiftKeyDown(), player);
            return InteractionResult.SUCCESS;
        }

        return super.useWithoutItem(state, world, pos, player, hit);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return DEFAULT_SHAPE;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!(world.getBlockEntity(pos) instanceof FabricatorBlockEntity be)) return;
        if (!be.isValid()) return;
        if (!be.hasBlueprint()) return;
        if (be.getBlueprint().orElseThrow().isComplete()) return;

        Direction direction = state.getValue(FACING);
        double d = (double) pos.getX() + 0.55 - (double) (random.nextFloat() * 0.1f);
        double e = (double) pos.getY() + 0.25 - (double) (random.nextFloat() * 0.1f);
        double f = (double) pos.getZ() + 0.55 - (double) (random.nextFloat() * 0.1f);
        double g = 0.4f - (random.nextFloat() + random.nextFloat()) * 0.4f;
        world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d + (double) direction.getStepX() * g,
                e + (double) direction.getStepY() * g, f + (double) direction.getStepZ() * g,
                random.nextGaussian() * 0.005, random.nextGaussian() * 0.005, random.nextGaussian() * 0.005);

        if (random.nextDouble() < 0.05) {
            world.playLocalSound(d, e, f, AITSounds.FABRICATOR_LOOP, SoundSource.BLOCKS, 0.25f, 1.0f, false);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return DEFAULT_SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return DEFAULT_SHAPE;
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FabricatorBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, tooltipContext, tooltip, options);

        addShiftHiddenTooltip(stack, tooltip, tooltips -> {
            tooltip.add(Component.translatable("block.ait.fabricator.tooltip.use").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        });
    }
}
