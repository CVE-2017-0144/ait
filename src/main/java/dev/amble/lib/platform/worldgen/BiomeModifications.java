package dev.amble.lib.platform.worldgen;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class BiomeModifications {

    private BiomeModifications() {}

    public static void addFeature(Predicate<BiomeSelectionContext> selector, GenerationStep.Decoration step,
            ResourceKey<PlacedFeature> feature) {
        net.fabricmc.fabric.api.biome.v1.BiomeModifications.addFeature(selector, step, feature);
    }
}
