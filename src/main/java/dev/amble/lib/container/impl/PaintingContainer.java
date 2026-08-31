package dev.amble.lib.container.impl;

import dev.amble.lib.container.RegistryContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.decoration.PaintingVariant;

public interface PaintingContainer extends RegistryContainer<PaintingVariant> {

    @Override
    default Class<PaintingVariant> getTargetClass() {
        return PaintingVariant.class;
    }

    @Override
    default Registry<PaintingVariant> getRegistry() {
        return BuiltInRegistries.PAINTING_VARIANT;
    }
}
