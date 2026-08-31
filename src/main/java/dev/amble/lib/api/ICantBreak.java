package dev.amble.lib.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * An interface which stops a block from being broken.
 */
public interface ICantBreak {
    /**
     * Called when the block was attempted to be broken.
     * This still exists for backwards compatibility, do NOT call.
     */
    default void onTryBreak(Level world, BlockPos pos, BlockState state) { }

    /**
     * Called when the block was attempted to be broken but also includes the player who attempts to break it.
     *
     * @param player The player who attempted to break the block, or null if not available.
     */
    default void onTryBreak(Level world, BlockPos pos, BlockState state, Player player) {
        this.onTryBreak(world, pos, state);
    }
}
