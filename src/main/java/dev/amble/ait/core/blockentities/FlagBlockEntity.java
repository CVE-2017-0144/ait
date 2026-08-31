package dev.amble.ait.core.blockentities;

import dev.amble.ait.core.AITBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FlagBlockEntity extends BlockEntity {
    public FlagBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.FLAG_BLOCK_ENTITY_TYPE, pos, state);
    }
}
