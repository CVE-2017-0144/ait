package dev.amble.ait.module.planet.core.world;

import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import dev.amble.ait.AITMod;
import dev.amble.ait.module.planet.core.PlanetBlocks;

public class PlanetConfiguredFeatures {
   public static final ResourceKey<ConfiguredFeature<?, ?>> MARTIAN_COAL_ORE = registryKey("martian_coal_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MARTIAN_COPPER_ORE = registryKey("martian_copper_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MARTIAN_IRON_ORE = registryKey("martian_iron_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MARTIAN_GOLD_ORE = registryKey("martian_gold_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MARTIAN_REDSTONE_ORE = registryKey("martian_redstone_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MARTIAN_LAPIS_ORE = registryKey("martian_lapis_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MARTIAN_DIAMOND_ORE = registryKey("martian_diamond_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MARTIAN_EMERALD_ORE = registryKey("martian_emerald_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ANORTHOSITE_COAL_ORE = registryKey("anorthosite_coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ANORTHOSITE_COPPER_ORE = registryKey("anorthosite_copper_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ANORTHOSITE_IRON_ORE = registryKey("anorthosite_iron_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ANORTHOSITE_GOLD_ORE = registryKey("anorthosite_gold_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ANORTHOSITE_REDSTONE_ORE = registryKey("anorthosite_redstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ANORTHOSITE_LAPIS_ORE = registryKey("anorthosite_lapis_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ANORTHOSITE_DIAMOND_ORE = registryKey("anorthosite_diamond_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ANORTHOSITE_EMERALD_ORE = registryKey("anorthosite_emerald_ore");

   public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
       RuleTest martianStoneReplaceables = new BlockMatchTest(PlanetBlocks.MARTIAN_STONE);
       RuleTest anorthositeReplaceables = new BlockMatchTest(PlanetBlocks.ANORTHOSITE);

       List<OreConfiguration.TargetBlockState> marsOres =
               List.of(OreConfiguration.target(martianStoneReplaceables, PlanetBlocks.MARTIAN_COAL_ORE.defaultBlockState()),
                       OreConfiguration.target(martianStoneReplaceables, PlanetBlocks.MARTIAN_COPPER_ORE.defaultBlockState()),
                       OreConfiguration.target(martianStoneReplaceables, PlanetBlocks.MARTIAN_IRON_ORE.defaultBlockState()),
                       OreConfiguration.target(martianStoneReplaceables, PlanetBlocks.MARTIAN_GOLD_ORE.defaultBlockState()),
                       OreConfiguration.target(martianStoneReplaceables, PlanetBlocks.MARTIAN_REDSTONE_ORE.defaultBlockState()),
                       OreConfiguration.target(martianStoneReplaceables, PlanetBlocks.MARTIAN_LAPIS_ORE.defaultBlockState()),
                       OreConfiguration.target(martianStoneReplaceables, PlanetBlocks.MARTIAN_DIAMOND_ORE.defaultBlockState()),
                       OreConfiguration.target(martianStoneReplaceables, PlanetBlocks.MARTIAN_EMERALD_ORE.defaultBlockState()));


       List<OreConfiguration.TargetBlockState> anorthorsiteOres =
               List.of(OreConfiguration.target(anorthositeReplaceables, PlanetBlocks.ANORTHOSITE_COAL_ORE.defaultBlockState()),
                       OreConfiguration.target(anorthositeReplaceables, PlanetBlocks.ANORTHOSITE_COPPER_ORE.defaultBlockState()),
                       OreConfiguration.target(anorthositeReplaceables, PlanetBlocks.ANORTHOSITE_IRON_ORE.defaultBlockState()),
                       OreConfiguration.target(anorthositeReplaceables, PlanetBlocks.ANORTHOSITE_GOLD_ORE.defaultBlockState()),
                       OreConfiguration.target(anorthositeReplaceables, PlanetBlocks.ANORTHOSITE_REDSTONE_ORE.defaultBlockState()),
                       OreConfiguration.target(anorthositeReplaceables, PlanetBlocks.ANORTHOSITE_LAPIS_ORE.defaultBlockState()),
                       OreConfiguration.target(anorthositeReplaceables, PlanetBlocks.ANORTHOSITE_DIAMOND_ORE.defaultBlockState()),
                       OreConfiguration.target(anorthositeReplaceables, PlanetBlocks.ANORTHOSITE_EMERALD_ORE.defaultBlockState()));


       register(context, MARTIAN_COAL_ORE, Feature.ORE, new OreConfiguration(marsOres, 12));
       register(context, MARTIAN_COPPER_ORE, Feature.ORE, new OreConfiguration(marsOres, 10));
       register(context, MARTIAN_IRON_ORE, Feature.ORE, new OreConfiguration(marsOres, 8));
       register(context, MARTIAN_GOLD_ORE, Feature.ORE, new OreConfiguration(marsOres, 6));
       register(context, MARTIAN_REDSTONE_ORE, Feature.ORE, new OreConfiguration(marsOres, 6));
       register(context, MARTIAN_LAPIS_ORE, Feature.ORE, new OreConfiguration(marsOres, 4));
       register(context, MARTIAN_DIAMOND_ORE, Feature.ORE, new OreConfiguration(marsOres, 3));
       register(context, MARTIAN_EMERALD_ORE, Feature.ORE, new OreConfiguration(marsOres, 1));


       register(context, ANORTHOSITE_COAL_ORE, Feature.ORE, new OreConfiguration(anorthorsiteOres, 12));
       register(context, ANORTHOSITE_COPPER_ORE, Feature.ORE, new OreConfiguration(anorthorsiteOres, 10));
       register(context, ANORTHOSITE_IRON_ORE, Feature.ORE, new OreConfiguration(anorthorsiteOres, 8));
       register(context, ANORTHOSITE_GOLD_ORE, Feature.ORE, new OreConfiguration(anorthorsiteOres, 6));
       register(context, ANORTHOSITE_REDSTONE_ORE, Feature.ORE, new OreConfiguration(anorthorsiteOres, 6));
       register(context, ANORTHOSITE_LAPIS_ORE, Feature.ORE, new OreConfiguration(anorthorsiteOres, 4));
       register(context, ANORTHOSITE_DIAMOND_ORE, Feature.ORE, new OreConfiguration(anorthorsiteOres, 3));
       register(context, ANORTHOSITE_EMERALD_ORE, Feature.ORE, new OreConfiguration(anorthorsiteOres, 1));
   }

    public static ResourceKey<ConfiguredFeature<?, ?>> registryKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, AITMod.id(name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                   ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }


}
