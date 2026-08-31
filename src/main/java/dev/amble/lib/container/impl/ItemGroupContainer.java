package dev.amble.lib.container.impl;

import dev.amble.lib.container.RegistryContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;

public interface ItemGroupContainer extends RegistryContainer<CreativeModeTab> {

    @Override
    default Registry<CreativeModeTab> getRegistry() {
        return BuiltInRegistries.CREATIVE_MODE_TAB;
    }

    @Override
    default Class<CreativeModeTab> getTargetClass() {
        return CreativeModeTab.class;
    }
}
