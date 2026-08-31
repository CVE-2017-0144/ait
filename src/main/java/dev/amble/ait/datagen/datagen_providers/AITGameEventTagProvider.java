package dev.amble.ait.datagen.datagen_providers;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.gameevent.GameEvent;
import dev.amble.ait.core.AITTags;
import dev.amble.lib.platform.datagen.PlatformDataOutput;

public class AITGameEventTagProvider extends TagsProvider<GameEvent> {
    public AITGameEventTagProvider(PlatformDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, Registries.GAME_EVENT, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.tag(AITTags.GameEvents.MATRIX_CAN_LISTEN).add(GameEvent.SHRIEK.key());
    }
}
