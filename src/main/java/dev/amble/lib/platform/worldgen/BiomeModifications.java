package dev.amble.lib.platform.worldgen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import dev.amble.lib.platform.Platform;

public class BiomeModifications {

    // data/ait/neoforge/biome_modifier/queued_features.json points at this id
    public static final ResourceLocation SERIALIZER_ID =
            ResourceLocation.fromNamespaceAndPath("ait", "queued_features");

    private record Entry(BiomeSelector selector, GenerationStep.Decoration step,
            ResourceKey<PlacedFeature> feature) {}

    // neoforge modifiers are data only, one json replays these
    private static final List<Entry> ENTRIES = new ArrayList<>();

    public static void addFeature(BiomeSelector selector, GenerationStep.Decoration step,
            ResourceKey<PlacedFeature> feature) {
        ENTRIES.add(new Entry(selector, step, feature));
    }

    public record QueuedFeatures(HolderGetter<PlacedFeature> features) implements BiomeModifier {

        public static final MapCodec<QueuedFeatures> CODEC = RecordCodecBuilder
                .mapCodec(instance -> instance
                        .group(RegistryOps.<PlacedFeature, QueuedFeatures>retrieveGetter(Registries.PLACED_FEATURE))
                        .apply(instance, QueuedFeatures::new));

        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase != Phase.ADD)
                return;

            for (Entry entry : ENTRIES) {
                if (!entry.selector().test(biome))
                    continue;

                this.features.get(entry.feature())
                        .ifPresent(holder -> builder.getGenerationSettings().addFeature(entry.step(), holder));
            }
        }

        @Override
        public MapCodec<? extends BiomeModifier> codec() {
            return CODEC;
        }
    }

    public static void init() {
        IEventBus modBus = Platform.modBus();

        if (modBus == null)
            return;

        modBus.addListener(RegisterEvent.class, event -> event.register(
                NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS,
                registry -> registry.register(SERIALIZER_ID, QueuedFeatures.CODEC)));
    }
}
