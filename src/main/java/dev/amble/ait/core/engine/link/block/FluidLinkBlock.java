package dev.amble.ait.core.engine.link.block;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.engine.link.IFluidLink;
import dev.amble.ait.core.engine.link.IFluidSource;
import dev.amble.ait.core.world.TardisServerWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class FluidLinkBlock extends Block implements IFluidLink, EntityBlock {
    public FluidLinkBlock(Properties settings) {
        super(settings);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);

        if (!TardisServerWorld.isTardisDimension(world)) return;

        if (world.getBlockEntity(pos) instanceof FluidLinkBlockEntity be) {
            be.onPlaced(world, pos, placer);
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) { // on break
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FluidLinkBlockEntity be) {
                be.setRemoved();
                be.onBroken(world, pos);
            }
        }

        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborChanged(state, world, pos, sourceBlock, sourcePos, notify);

        if (world.getBlockEntity(pos) instanceof FluidLinkBlockEntity be) {
            be.onNeighborUpdate(world, pos, sourceBlock, sourcePos);
        }
    }

    @Override
    public abstract FluidLinkBlockEntity newBlockEntity(BlockPos pos, BlockState state);

    @Override
    public IFluidSource source(boolean search) {
        throw new UnsupportedOperationException("FluidLinkBlock does not support this operation, did you mean to use FluidLinkBlockEntity?");
    }

    @Override
    public void setSource(IFluidSource source) {
        throw new UnsupportedOperationException("FluidLinkBlock does not support this operation, did you mean to use FluidLinkBlockEntity?");
    }

    @Override
    public IFluidLink last() {
        throw new UnsupportedOperationException("FluidLinkBlock does not support this operation, did you mean to use FluidLinkBlockEntity?");
    }

    @Override
    public void setLast(IFluidLink last) {
        throw new UnsupportedOperationException("FluidLinkBlock does not support this operation, did you mean to use FluidLinkBlockEntity?");
    }
}
