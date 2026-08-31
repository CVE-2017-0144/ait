package dev.amble.ait.module.planet.core.world;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import dev.amble.ait.AITMod;

public class PlanetPlacedFeatures {
    public static final ResourceKey<PlacedFeature> MARTIAN_COAL_ORE_PLACED_KEY = registerKey("martian_coal_ore_placed");
    public static final ResourceKey<PlacedFeature> MARTIAN_COPPER_ORE_PLACED_KEY = registerKey("martian_copper_ore_placed");
    public static final ResourceKey<PlacedFeature> MARTIAN_IRON_ORE_PLACED_KEY = registerKey("martian_iron_ore_placed");
    public static final ResourceKey<PlacedFeature> MARTIAN_GOLD_ORE_PLACED_KEY = registerKey("martian_gold_ore_placed");
    public static final ResourceKey<PlacedFeature> MARTIAN_REDSTONE_ORE_PLACED_KEY = registerKey("martian_redstone_ore_placed");
    public static final ResourceKey<PlacedFeature> MARTIAN_LAPIS_ORE_PLACED_KEY = registerKey("martian_lapis_ore_placed");
    public static final ResourceKey<PlacedFeature> MARTIAN_DIAMOND_ORE_PLACED_KEY = registerKey("martian_diamond_ore_placed");
    public static final ResourceKey<PlacedFeature> MARTIAN_EMERALD_ORE_PLACED_KEY = registerKey("martian_emerald_ore_placed");

    public static final ResourceKey<PlacedFeature> ANORTHOSITE_COAL_ORE_PLACED_KEY = registerKey("anorthosite_coal_ore_placed");
    public static final ResourceKey<PlacedFeature> ANORTHOSITE_COPPER_ORE_PLACED_KEY = registerKey("anorthosite_copper_ore_placed");
    public static final ResourceKey<PlacedFeature> ANORTHOSITE_IRON_ORE_PLACED_KEY = registerKey("anorthosite_iron_ore_placed");
    public static final ResourceKey<PlacedFeature> ANORTHOSITE_GOLD_ORE_PLACED_KEY = registerKey("anorthosite_gold_ore_placed");
    public static final ResourceKey<PlacedFeature> ANORTHOSITE_REDSTONE_ORE_PLACED_KEY = registerKey("anorthosite_redstone_ore_placed");
    public static final ResourceKey<PlacedFeature> ANORTHOSITE_LAPIS_ORE_PLACED_KEY = registerKey("anorthosite_lapis_ore_placed");
    public static final ResourceKey<PlacedFeature> ANORTHOSITE_DIAMOND_ORE_PLACED_KEY = registerKey("anorthosite_diamond_ore_placed");
    public static final ResourceKey<PlacedFeature> ANORTHOSITE_EMERALD_ORE_PLACED_KEY = registerKey("anorthosite_emerald_ore_placed");

    public static void boostrap(BootstapContext<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, MARTIAN_COAL_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.MARTIAN_COAL_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, MARTIAN_COPPER_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.MARTIAN_COAL_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, MARTIAN_IRON_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.MARTIAN_IRON_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, MARTIAN_GOLD_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.MARTIAN_GOLD_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, MARTIAN_REDSTONE_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.MARTIAN_REDSTONE_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, MARTIAN_LAPIS_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.MARTIAN_LAPIS_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, MARTIAN_DIAMOND_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.MARTIAN_DIAMOND_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, MARTIAN_EMERALD_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.MARTIAN_EMERALD_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));

        // Anorthosite

        register(context, ANORTHOSITE_COAL_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.ANORTHOSITE_COAL_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, ANORTHOSITE_COPPER_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.ANORTHOSITE_COAL_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, ANORTHOSITE_IRON_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.ANORTHOSITE_IRON_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, ANORTHOSITE_GOLD_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.ANORTHOSITE_GOLD_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, ANORTHOSITE_REDSTONE_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.ANORTHOSITE_REDSTONE_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, ANORTHOSITE_LAPIS_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.ANORTHOSITE_LAPIS_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, ANORTHOSITE_DIAMOND_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.ANORTHOSITE_DIAMOND_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, ANORTHOSITE_EMERALD_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(PlanetConfiguredFeatures.ANORTHOSITE_EMERALD_ORE),
                PlanetOrePlacement.modifiersWithCount(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, AITMod.id(name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
