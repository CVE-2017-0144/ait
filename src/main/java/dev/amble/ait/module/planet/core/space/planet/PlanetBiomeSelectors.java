package dev.amble.ait.module.planet.core.space.planet;

import dev.amble.ait.AITMod;
import dev.amble.lib.platform.worldgen.BiomeSelector;
import dev.amble.lib.platform.worldgen.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

// biome modifiers can't see the dimension, match the planet's biome
public class PlanetBiomeSelectors {

    public static final ResourceKey<Biome> MARS_BIOME =
            ResourceKey.create(Registries.BIOME, AITMod.id("mars_biome"));

    public static final ResourceKey<Biome> MOON_BIOME =
            ResourceKey.create(Registries.BIOME, AITMod.id("moon_biome"));

    public static final ResourceKey<Biome> SPACE_BIOME =
            ResourceKey.create(Registries.BIOME, AITMod.id("space"));

    public static BiomeSelector foundInMars() {
        return BiomeSelectors.isBiome(MARS_BIOME);
    }

    public static BiomeSelector foundInMoon() {
        return BiomeSelectors.isBiome(MOON_BIOME);
    }

    public static BiomeSelector foundInSpace() {
        return BiomeSelectors.isBiome(SPACE_BIOME);
    }
}
