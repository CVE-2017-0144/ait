package dev.amble.lib.blockentity;

import net.minecraft.nbt.CompoundTag;

public interface StructurePlaceableBlockEntity {
    void amble$onStructurePlaced(CompoundTag nbt);
}
