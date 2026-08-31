package dev.amble.ait.module.planet.core.world.gen;


import net.minecraft.world.level.levelgen.GenerationStep;
import dev.amble.ait.module.planet.core.space.planet.PlanetBiomeSelectors;
import dev.amble.ait.module.planet.core.world.PlanetPlacedFeatures;
import dev.amble.lib.platform.worldgen.BiomeModifications;


public class PlanetOreGeneration {
    public static void generateOres() {
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMars(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.MARTIAN_COAL_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMars(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.MARTIAN_COPPER_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMars(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.MARTIAN_IRON_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMars(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.MARTIAN_REDSTONE_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMars(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.MARTIAN_LAPIS_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMars(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.MARTIAN_GOLD_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMars(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.MARTIAN_DIAMOND_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMars(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.MARTIAN_EMERALD_ORE_PLACED_KEY);

        //Anorthosite

        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMoon(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.ANORTHOSITE_COAL_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMoon(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.ANORTHOSITE_COPPER_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMoon(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.ANORTHOSITE_IRON_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMoon(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.ANORTHOSITE_REDSTONE_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMoon(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.ANORTHOSITE_LAPIS_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMoon(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.ANORTHOSITE_GOLD_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMoon(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.ANORTHOSITE_DIAMOND_ORE_PLACED_KEY);
        BiomeModifications.addFeature(PlanetBiomeSelectors.foundInMoon(),
                GenerationStep.Decoration.UNDERGROUND_ORES, PlanetPlacedFeatures.ANORTHOSITE_EMERALD_ORE_PLACED_KEY);
    }

}
