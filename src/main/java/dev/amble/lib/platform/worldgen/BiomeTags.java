package dev.amble.lib.platform.worldgen;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class BiomeTags {

    private BiomeTags() {}

    public static final TagKey<Biome> SNOWY = ConventionalBiomeTags.SNOWY;
    public static final TagKey<Biome> SNOWY_PLAINS = ConventionalBiomeTags.SNOWY_PLAINS;
    public static final TagKey<Biome> ICY = ConventionalBiomeTags.ICY;
    public static final TagKey<Biome> DESERT = ConventionalBiomeTags.DESERT;
    public static final TagKey<Biome> BEACH = ConventionalBiomeTags.BEACH;
    public static final TagKey<Biome> DEAD = ConventionalBiomeTags.DEAD;
    public static final TagKey<Biome> BADLANDS = ConventionalBiomeTags.BADLANDS;
    public static final TagKey<Biome> SWAMP = ConventionalBiomeTags.SWAMP;
    public static final TagKey<Biome> IN_THE_END = ConventionalBiomeTags.IN_THE_END;
    public static final TagKey<Biome> FLORAL = ConventionalBiomeTags.FLORAL;

    static TagKey<Biome> of(String path) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("c", path));
    }
}
