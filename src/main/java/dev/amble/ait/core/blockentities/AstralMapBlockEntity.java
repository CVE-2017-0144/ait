package dev.amble.ait.core.blockentities;

import dev.amble.ait.core.AITBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AstralMapBlockEntity extends BlockEntity {

    public AstralMapBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.ASTRAL_MAP, pos, state);
    }
}
