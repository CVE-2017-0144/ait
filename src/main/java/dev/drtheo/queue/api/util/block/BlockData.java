package dev.drtheo.queue.api.util.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public record BlockData(BlockState state, BlockPos pos, int flags) { }