package dev.amble.ait.core.handles;

import dev.amble.ait.core.tardis.ServerTardis;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

/**
 * For Handles the robot, this is used to play sounds.
 */
public interface HandlesSound {
    void playSound(SoundEvent sound, SoundSource category, float volume, float pitch);

    static HandlesSound of(ServerPlayer player) {
        return (sound, category, volume, pitch) -> player.serverLevel().playSound(null, player.blockPosition(), sound, category, volume, pitch);
    }
    static HandlesSound of(ServerTardis tardis) {
        return (sound, category, volume, pitch) -> tardis.getDesktop().playSoundAtEveryConsole(sound, category, volume, pitch);
    }
}
