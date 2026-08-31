package dev.amble.lib.block.behavior;

import dev.amble.lib.block.behavior.base.RenderBlockBehavior;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class InvisibleBlockBehavior extends RenderBlockBehavior {

    public static InvisibleBlockBehavior behavior = new InvisibleBlockBehavior();

    @Override
    public RenderShape getRenderType(BlockState state) {
        return RenderShape.INVISIBLE;
    }
}
