package dev.amble.ait.datagen.datagen_providers;

import java.util.concurrent.CompletableFuture;

import dev.amble.ait.core.AITEntityTypes;
import dev.amble.ait.core.AITTags;
import dev.amble.lib.platform.datagen.PlatformDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;


public class AITEntityTypeTagProvider extends EntityTypeTagsProvider {
    public AITEntityTypeTagProvider(PlatformDataOutput output,
                                    CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        tag(AITTags.EntityTypes.NON_DISMOUNTABLE)
                .add(AITEntityTypes.FLIGHT_TARDIS_TYPE);

        tag(AITTags.EntityTypes.BOSS)
                .add(EntityType.ENDER_DRAGON).add(EntityType.WITHER)
                .add(EntityType.WARDEN).add(EntityType.ELDER_GUARDIAN);
    }
}
