package dev.amble.ait.data.hum;

import dev.amble.ait.api.Nameable;
import dev.amble.lib.api.Identifiable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class Hum implements Identifiable, Nameable {
    private final ResourceLocation id;
    private final SoundEvent sound;

    protected Hum(ResourceLocation id, SoundEvent sound) {
        this.id = id;
        this.sound = sound;
    }

    @Override
    public ResourceLocation id() {
        return this.id;
    }

    public SoundEvent sound() {
        return this.sound;
    }

    public static Hum create(String modId, String name, SoundEvent sound) {
        return new Hum(ResourceLocation.tryBuild(modId, name), sound);
    }

    @Override
    public String name() {
        return this.id().getPath();
    }

    @Override
    public Component text() {
        return Component.translatableWithFallback(this.id().toLanguageKey("hum"), this.name());
    }
}
