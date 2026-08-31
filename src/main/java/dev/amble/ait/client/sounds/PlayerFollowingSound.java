package dev.amble.ait.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class PlayerFollowingSound extends AbstractTickableSoundInstance {
    public PlayerFollowingSound(SoundEvent soundEvent, SoundSource soundCategory, float volume, float pitch) {
        super(soundEvent, soundCategory, RandomSource.create());

        LocalPlayer client = Minecraft.getInstance().player;
        this.x = client.getX();
        this.y = client.getY();
        this.z = client.getZ();
        this.volume = volume;
        this.pitch = pitch;
        this.looping = false;
    }

    public PlayerFollowingSound(SoundEvent soundEvent, SoundSource soundCategory, float volume) {
        this(soundEvent, soundCategory, volume, 1);
    }

    public PlayerFollowingSound(SoundEvent soundEvent, SoundSource soundCategory) {
        this(soundEvent, soundCategory, 1, 1);
    }

    @Override
    public void tick() {
        this.setCoordsToPlayerCoords();
    }

    private void setCoordsToPlayerCoords() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null)
            return;

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }
}
