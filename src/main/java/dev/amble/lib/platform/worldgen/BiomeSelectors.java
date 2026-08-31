package dev.amble.lib.platform.worldgen;

import java.util.Set;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;

public class BiomeSelectors {

    public static BiomeSelector foundInOverworld() {
        return biome -> biome.is(BiomeTags.IS_OVERWORLD);
    }

    @SafeVarargs
    public static BiomeSelector isBiome(ResourceKey<Biome>... keys) {
        Set<ResourceKey<Biome>> wanted = Set.of(keys);
        return biome -> biome.is(wanted::contains);
    }
}
