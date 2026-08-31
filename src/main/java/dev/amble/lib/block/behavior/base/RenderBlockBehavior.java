package dev.amble.lib.block.behavior.base;

import dev.amble.lib.block.behavior.api.BlockBehavior;
import dev.amble.lib.block.behavior.api.BlockBehaviors;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class RenderBlockBehavior implements BlockBehavior {

    public RenderShape getRenderType(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public int idx() {
        return BlockBehaviors.RENDER_BLOCK;
    }
}
