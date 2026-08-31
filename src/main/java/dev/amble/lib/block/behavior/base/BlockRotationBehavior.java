package dev.amble.lib.block.behavior.base;

import dev.amble.lib.block.behavior.api.BlockBehavior;
import dev.amble.lib.block.behavior.api.BlockBehaviors;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;

public class BlockRotationBehavior implements BlockBehavior {

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state;
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state;
    }

    @Override
    public int idx() {
        return BlockBehaviors.BLOCK_ROTATION;
    }
}
