package dev.amble.ait.datagen.datagen_providers;

import dev.amble.ait.AITMod;
import dev.amble.ait.module.planet.core.world.PlanetConfiguredFeatures;
import dev.amble.ait.module.planet.core.world.PlanetPlacedFeatures;
import dev.amble.lib.platform.datagen.PlatformDataOutput;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public class AITWorldGeneratorProvider extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, PlanetConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, PlanetPlacedFeatures::boostrap);

    public AITWorldGeneratorProvider(PlatformDataOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, BUILDER, Set.of(AITMod.MOD_ID));
    }

    @Override
    public String getName() {
        return "AIT World Generator";
    }
}
