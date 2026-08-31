package dev.amble.ait.core.blockentities;

import dev.amble.ait.core.AITBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SnowGlobeBlockEntity extends BlockEntity {
    public SnowGlobeBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.SNOW_GLOBE_BLOCK_ENTITY_TYPE, pos, state);
    }
}
