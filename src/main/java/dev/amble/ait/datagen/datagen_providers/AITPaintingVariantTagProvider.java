package dev.amble.ait.datagen.datagen_providers;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.decoration.PaintingVariant;
import dev.amble.ait.core.AITPaintings;
import dev.amble.lib.platform.datagen.PlatformDataOutput;

public class AITPaintingVariantTagProvider extends TagsProvider<PaintingVariant> {
    public AITPaintingVariantTagProvider(PlatformDataOutput output,
                                         CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.PAINTING_VARIANT, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.tag(PaintingVariantTags.PLACEABLE).add(AITPaintings.CRAB_THROWER).add(AITPaintings.PEANUT);
    }
}
