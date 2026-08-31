package dev.amble.ait.core.engine.link.block;

import dev.amble.ait.core.AITBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CableBlockEntity extends FluidLinkBlockEntity {

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.CABLE_BLOCK_ENTITY_TYPE, pos, state);
    }
}
