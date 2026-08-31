package dev.amble.ait.core.blockentities;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import dev.amble.ait.core.AITBlockEntityTypes;

public class CoralBlockEntity extends BlockEntity {

    public UUID creator;

    public CoralBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.CORAL_BLOCK_ENTITY_TYPE, pos, state);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.creator = nbt.getUUID("creator");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        if (this.creator == null) return;
        nbt.putUUID("creator", this.creator);
    }
}
