package dev.amble.lib.block.behavior.base;

import dev.amble.lib.block.behavior.api.BlockBehavior;
import dev.amble.lib.block.behavior.api.BlockBehaviors;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPlacementBehavior implements BlockBehavior {

    public BlockState getPlacementState(BlockState state, BlockPlaceContext ctx) {
        return state;
    }

    @Override
    public int idx() {
        return BlockBehaviors.BLOCK_PLACEMENT;
    }
}
