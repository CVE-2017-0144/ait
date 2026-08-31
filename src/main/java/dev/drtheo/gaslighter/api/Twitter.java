package dev.drtheo.gaslighter.api;

import net.minecraft.core.BlockPos;

public interface Twitter {
    void ait$setFake(BlockPos pos, boolean fake);
    boolean ait$isFake(BlockPos pos);
}
