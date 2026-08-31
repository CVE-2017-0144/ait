package dev.amble.ait.datagen.datagen_providers;


import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.module.ModuleRegistry;
import dev.amble.ait.module.planet.core.PlanetItems;
import dev.amble.lib.platform.datagen.PlatformDataOutput;

public class AITItemTagProvider extends IntrinsicHolderTagsProvider<Item> {
    public AITItemTagProvider(PlatformDataOutput output,
            @Nullable CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, Registries.ITEM, completableFuture, item -> item.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        // Items
        tag(AITTags.Items.SONIC_ITEM).add(AITItems.SONIC_SCREWDRIVER);

        tag(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(AITItems.TWO_THOUSAND_MUSIC_DISC)
                .add(AITItems.WONDERFUL_TIME_IN_SPACE_MUSIC_DISC)
                .add(AITItems.GOOD_MAN_MUSIC_DISC)
                .add(AITItems.AIT_THEME_MUSIC_DISC)
                .add(AITItems.EARTH_MUSIC_DISC)
                .add(AITItems.VENUS_MUSIC_DISC)
                .add(AITItems.CRASH_MUSIC_DISC)
                .add(AITItems.STAGE_4_MUSIC_DISC);

        tag(AITTags.Items.CLUSTER_MAX_HARVESTABLES).add(AITItems.ZEITON_SHARD);

        tag(AITTags.Items.NO_BOP).add(AITItems.SONIC_SCREWDRIVER);

        tag(AITTags.Items.FULL_RESPIRATORS).add(AITItems.RESPIRATOR);

        tag(AITTags.Items.HALF_RESPIRATORS).add(AITItems.FACELESS_RESPIRATOR);

        tag(AITTags.Items.KEY).add(AITItems.IRON_KEY, AITItems.GOLD_KEY, AITItems.CLASSIC_KEY,
                AITItems.NETHERITE_KEY, AITItems.SKELETON_KEY);

        tag(AITTags.Items.IS_TARDIS_FUEL).add(AITItems.ZEITON_DUST, AITItems.ZEITON_SHARD,
                AITBlocks.TARDIS_CORAL_BLOCK.asItem(), AITBlocks.TARDIS_CORAL_SLAB.asItem(),
                AITBlocks.TARDIS_CORAL_FAN.asItem(), AITBlocks.TARDIS_CORAL_STAIRS.asItem(),
                AITItems.CORAL_FRAGMENT);
        tag(AITTags.Items.IS_TARDIS_FUEL).addTag(ItemTags.LOGS_THAT_BURN);
        tag(AITTags.Items.IS_TARDIS_FUEL).addTag(ItemTags.COALS);
        tag(AITTags.Items.IS_TARDIS_FUEL).add(Items.LAVA_BUCKET);

        // Rifts

        tag(AITTags.Items.RIFT_SUCCESS_EXTRA_ITEM).add(AITItems.ZEITON_SHARD);
        tag(AITTags.Items.RIFT_FAIL_ITEM).add(Items.PAPER);

        //Linkable

        tag(AITTags.Items.LINK).add(AITItems.SONIC_SCREWDRIVER, AITItems.CLASSIC_KEY, AITItems.GOLD_KEY, AITItems.IRON_KEY, AITItems.REMOTE_ITEM,AITItems.NETHERITE_KEY, PlanetItems.HANDLES);

        ModuleRegistry.instance().iterator().forEachRemaining(module -> {
            module.getDataGenerator().ifPresent(generator -> {
                generator.itemTags(this);
            });
        });
    }

    @Override
    public IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> tag(TagKey<Item> tag) {
        return super.tag(tag);
    }
}
