package dev.amble.ait.module.planet.core.space.planet;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.LevelStem;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITDimensions;

public class PlanetBiomeSelectors {
    private static final ResourceKey<Registry<LevelStem>> DIMENSION_KEY =
            ResourceKey.createRegistryKey(AITMod.id("dimension"));

    public static final ResourceKey<LevelStem> MARS_DIMENSION_OPTIONS =
            ResourceKey.create(DIMENSION_KEY, AITDimensions.MARS.location());

    public static final ResourceKey<LevelStem> MOON_DIMENSION_OPTIONS =
            ResourceKey.create(DIMENSION_KEY, AITDimensions.MOON.location());

    public static final ResourceKey<LevelStem> SPACE_DIMENSION_OPTIONS =
            ResourceKey.create(DIMENSION_KEY, AITDimensions.SPACE.location());

    public static Predicate<BiomeSelectionContext> foundInMars() {
        return context -> context.canGenerateIn(MARS_DIMENSION_OPTIONS);
    }

    public static Predicate<BiomeSelectionContext> foundInMoon() {
        return context -> context.canGenerateIn(MOON_DIMENSION_OPTIONS);
    }

    public static Predicate<BiomeSelectionContext> foundInSpace() {
        return context -> context.canGenerateIn(SPACE_DIMENSION_OPTIONS);
    }
}
