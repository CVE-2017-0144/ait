package dev.amble.ait.client.sounds;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class PositionedLoopingSound extends LoopingSound {
    public PositionedLoopingSound(SoundEvent soundEvent, SoundSource soundCategory, BlockPos pos, float volume,
            float pitch) {
        super(soundEvent, soundCategory);
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.volume = volume;
        this.pitch = pitch;
        this.looping = true;
    }

    public PositionedLoopingSound(SoundEvent soundEvent, SoundSource soundCategory, BlockPos pos, float volume) {
        this(soundEvent, soundCategory, pos, volume, 1);
    }

    public PositionedLoopingSound(SoundEvent soundEvent, SoundSource soundCategory, BlockPos pos) {
        this(soundEvent, soundCategory, pos, 1, 1);
    }

    public void setPosition(BlockPos pos) {
        if (pos == null)
            return;

        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }
}
