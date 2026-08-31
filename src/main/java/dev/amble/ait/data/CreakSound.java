package dev.amble.ait.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class CreakSound {
    private final ResourceLocation id;
    private final SoundEvent sound;

    protected CreakSound(ResourceLocation id, SoundEvent sound) {
        this.id = id;
        this.sound = sound;
    }

    public ResourceLocation id() {
        return this.id;
    }

    public SoundEvent sound() {
        return this.sound;
    }

    public static CreakSound create(String modId, String name, SoundEvent sound) {
        return new CreakSound(createId(modId, name), sound);
    }

    private static ResourceLocation createId(String modid, String name) {
        return new ResourceLocation(modid, "creak/" + name);
    }
}
