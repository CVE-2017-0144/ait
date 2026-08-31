package dev.amble.lib.container.impl;

import dev.amble.lib.container.RegistryContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;

public interface FluidContainer extends RegistryContainer<Fluid> {

    @Override
    default Class<Fluid> getTargetClass() {
        return Fluid.class;
    }

    @Override
    default Registry<Fluid> getRegistry() {
        return BuiltInRegistries.FLUID;
    }
}
