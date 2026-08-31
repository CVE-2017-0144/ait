package dev.amble.lib.block.behavior.api;

import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

@ApiStatus.Experimental
public interface BlockBehavior extends BlockBehaviorLike {

    @Override
    default void unwrap(BlockBehavior[] behaviors) {
        behaviors[idx()] = this;
    }

    default void init(Block block) { }

    default BlockState initDefaultState(Block block, BlockState state) {
        return state;
    }

    default void appendProperties(List<Property<?>> list) { }

    int idx();
}
