package dev.amble.ait.client.sounds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import dev.amble.ait.AITMod;

public class SoundHandler {

    private static final List<SoundInstance> EMPTY = List.of();

    protected List<SoundInstance> sounds;

    protected void ofSounds(SoundInstance... sound) {
        if (Arrays.stream(sound).anyMatch(Objects::isNull)) {
            this.sounds = EMPTY;
            return;
        }

        this.sounds = List.of(sound);
    }

    public static SoundHandler create(SoundInstance... list) {
        SoundHandler handler = new SoundHandler();

        handler.sounds = new ArrayList<>();
        handler.sounds.addAll(List.of(list));

        return handler;
    }

    public void startIfNotPlaying(SoundEvent event) {
        if (!isPlaying(event))
            startSound(event);
    }

    public void startIfNotPlaying(SoundInstance sound) {
        if (!isPlaying(sound))
            startSound(sound);
    }

    public boolean isPlaying(SoundEvent event) {
        return Minecraft.getInstance().getSoundManager().isActive(findSoundByEvent(event));
    }

    public boolean isPlaying(SoundInstance sound) {
        return Minecraft.getInstance().getSoundManager().isActive(sound);
    }

    /**
     * Searches through the sounds in this handler and starts playing whichever one
     * matches the SoundEvent given
     *
     * @param event
     *            the event to search for
     */
    public void startSound(SoundEvent event) {
        SoundInstance sound = findSoundByEvent(event);
        if (sound == null || sound.getLocation() == null) {
            return;
        }

        Minecraft.getInstance().getSoundManager().play(sound);
    }

    public void startSound(SoundInstance sound) {
if (sound == null || sound.getLocation() == null)
return;
        Minecraft.getInstance().getSoundManager().play(sound);
    }

    public void stopSound(SoundEvent event) {
        Minecraft.getInstance().getSoundManager().stop(findSoundByEvent(event));
    }

    public void stopSound(SoundInstance sound) {
        Minecraft.getInstance().getSoundManager().stop(sound);
    }

    public void stopSounds() {
        if (this.sounds == null)
            return;

        for (SoundInstance sound : this.sounds) {
            this.stopSound(sound);
        }
    }

    /**
     * Finds the first sound instance that matches the event given FIXME i hate this
     * sm and it doesnt work for sounds which are randomised
     */
    public SoundInstance findSoundByEvent(SoundEvent event) {
        return findSoundById(event.getLocation());
    }

    public SoundInstance findSoundById(ResourceLocation id) {
        ResourceLocation temp;

        for (SoundInstance sound : this.sounds) {
            temp = sound.getLocation();

            if (temp.equals(id))
                return sound;
        }

        AITMod.LOGGER.error("Could not find sound {} in list, returning empty sound!", id);
        return new PlayerFollowingLoopingSound(SoundEvents.EMPTY, SoundSource.NEUTRAL);
    }
}
