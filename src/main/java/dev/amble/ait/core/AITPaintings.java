package dev.amble.ait.core;

import dev.amble.ait.AITMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class AITPaintings {

    public static final ResourceKey<PaintingVariant> CRAB_THROWER = of("crab_thrower");
    public static final ResourceKey<PaintingVariant> PEANUT = of("peanut");

    private static ResourceKey<PaintingVariant> of(String name) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, AITMod.id(name));
    }
}
