package dev.amble.ait.core.blocks;

import static dev.amble.ait.client.util.TooltipUtil.addShiftHiddenTooltip;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.blockentities.ArtronCollectorBlockEntity;
import dev.amble.ait.core.engine.link.block.FluidLinkBlockEntity;
import dev.amble.ait.core.engine.link.block.HorizontalFluidLinkBlock;

@SuppressWarnings("deprecation")
public class ArtronCollectorBlock extends HorizontalFluidLinkBlock implements EntityBlock {

    protected static final VoxelShape Y_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 32.0, 16.0);

    public ArtronCollectorBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Y_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Y_SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level world, @NotNull BlockState state,
            @NotNull BlockEntityType<T> type) {
        return (world1, blockPos, blockState, ticker) -> {
            if (ticker instanceof ArtronCollectorBlockEntity collector) {
                collector.tick(world, blockPos, blockState, collector);
            }
        };
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    @Nullable @Override
    public FluidLinkBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ArtronCollectorBlockEntity(pos, state);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player,
            BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ArtronCollectorBlockEntity artronCollectorBlockEntity)
            artronCollectorBlockEntity.useOn(world, player.isShiftKeyDown(), player);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, tooltipContext, tooltip, options);

        addShiftHiddenTooltip(stack, tooltip, tooltips -> {
            tooltip.add(Component.translatable("block.ait.artron_collector_block.tooltip.use").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        });
    }
}
