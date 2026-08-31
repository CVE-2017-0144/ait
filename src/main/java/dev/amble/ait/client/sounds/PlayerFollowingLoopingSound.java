package dev.amble.ait.client.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

// fixme long permission

public class PlayerFollowingLoopingSound extends LoopingSound {
    public PlayerFollowingLoopingSound(SoundEvent soundEvent, SoundSource soundCategory, float volume, float pitch) {
        super(soundEvent, soundCategory);

        LocalPlayer client = Minecraft.getInstance().player;
        this.setPosition(client == null ? new BlockPos(0,0,0) : client.blockPosition());
        this.setVolume(volume);
        this.setPitch(pitch);
        this.looping = true;
    }

    public PlayerFollowingLoopingSound(SoundEvent soundEvent, SoundSource soundCategory, float volume) {
        this(soundEvent, soundCategory, volume, 1);
    }

    public PlayerFollowingLoopingSound(SoundEvent soundEvent, SoundSource soundCategory) {
        this(soundEvent, soundCategory, 1, 1);
    }

    @Override
    public void tick() {
        super.tick();
        this.setCoordsToPlayerCoords();
    }

    private void setCoordsToPlayerCoords() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.blockPosition() == null)
            return;
        this.setPosition(player.blockPosition());
    }
}
