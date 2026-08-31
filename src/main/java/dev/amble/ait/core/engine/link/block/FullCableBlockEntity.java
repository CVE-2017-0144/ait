package dev.amble.ait.core.engine.link.block;

import dev.amble.ait.core.AITBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class FullCableBlockEntity extends FluidLinkBlockEntity{

    public FullCableBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.FULL_CABLE_BLOCK_ENTITY_TYPE, pos, state);
    }
}
