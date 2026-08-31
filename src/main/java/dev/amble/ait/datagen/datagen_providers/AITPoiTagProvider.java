package dev.amble.ait.datagen.datagen_providers;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import dev.amble.ait.AITMod;

public class AITPoiTagProvider extends TagsProvider<PoiType> {
    public AITPoiTagProvider(PackOutput output,
                             CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.POINT_OF_INTEREST_TYPE, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptional(AITMod.id("fabricator_engineer_poi"));
    }
}
