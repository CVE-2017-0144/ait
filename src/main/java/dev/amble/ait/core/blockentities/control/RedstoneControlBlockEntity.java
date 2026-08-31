package dev.amble.ait.core.blockentities.control;

import dev.amble.ait.core.AITBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RedstoneControlBlockEntity extends ControlBlockEntity {

    public RedstoneControlBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.REDSTONE_CONTROL_BLOCK_ENTITY, pos, state);
    }
}
