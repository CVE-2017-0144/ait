package dev.amble.ait.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public record SoundData(SoundEvent sound, SoundSource category, float volume, float pitch) {
    public SoundData {
        if (volume < 0.0F) {
            throw new IllegalArgumentException("Volume must be positive");
        }
        if (pitch < 0.0F) {
            throw new IllegalArgumentException("Pitch must be positive");
        }
    }

    public void play(ServerLevel world, BlockPos pos) {
        world.playSound(null, pos, sound, category, volume, pitch);
    }
}
