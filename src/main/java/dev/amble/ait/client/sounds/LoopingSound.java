package dev.amble.ait.client.sounds;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

// Referencing how music which loops is done but in our own way
@OnlyIn(Dist.CLIENT)
public abstract class LoopingSound extends AbstractTickableSoundInstance {
    public LoopingSound(SoundEvent soundEvent, SoundSource soundCategory) {
        super(soundEvent, soundCategory, RandomSource.create());
        this.looping = true;
    }

    @Override
    public boolean isLooping() {
        return true;
    }

    @Override
    public void tick() {
    }

    public void setPosition(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public BlockPos getPosition() {
        return new BlockPos((int) this.x, (int) this.y, (int) this.z);
    }
}
