package dev.amble.ait.core.item;

import dev.amble.ait.core.AITBlocks;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ZeitonShardItem extends Item {
    public ZeitonShardItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());

        if (state.is(Blocks.COBBLESTONE)) {
            context.getLevel().setBlockAndUpdate(context.getClickedPos(), AITBlocks.ZEITON_COBBLE.defaultBlockState());
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }
}
