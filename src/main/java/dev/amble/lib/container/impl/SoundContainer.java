package dev.amble.lib.container.impl;

import dev.amble.lib.container.RegistryContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public interface SoundContainer extends RegistryContainer<SoundEvent> {

    @Override
    default Registry<SoundEvent> getRegistry() {
        return BuiltInRegistries.SOUND_EVENT;
    }

    @Override
    default Class<SoundEvent> getTargetClass() {
        return SoundEvent.class;
    }
}
