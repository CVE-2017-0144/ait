package dev.amble.ait.datagen.datagen_providers;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.module.ModuleRegistry;
import dev.amble.lib.platform.datagen.PlatformAdvancementProvider;
import dev.amble.lib.platform.datagen.PlatformDataOutput;

public class AITAchievementProvider extends PlatformAdvancementProvider {
    public AITAchievementProvider(PlatformDataOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registries,
            Consumer<AdvancementHolder> consumer) {

        ModuleRegistry.instance().iterator().forEachRemaining(module -> module.getDataGenerator().ifPresent(dataGenerator -> {
            dataGenerator.advancements(consumer);
        }));

        AdvancementHolder root = Advancement.Builder.advancement()
                .display(AITItems.CHARGED_ZEITON_CRYSTAL, Component.translatable("achievement.ait.title.root"),
                        Component.translatable("achievement.ait.description.root"), ResourceLocation.parse("textures/entity/end_portal.png"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("root", TardisCriterions.ROOT.conditions())
                .rewards(AdvancementRewards.Builder.function(ResourceLocation.fromNamespaceAndPath("ait", "wikimessage")))
                .save(consumer, AITMod.MOD_ID + "/root");

        AdvancementHolder placeEnergizer = Advancement.Builder.advancement().parent(root)
                .display(AITBlocks.MATRIX_ENERGIZER, Component.translatable("achievement.ait.title.place_energizer"),
                        Component.translatable("achievement.ait.description.place_energizer"),
                        null,
                        AdvancementType.TASK, true, true, true)
                .addCriterion("place_energizer", TardisCriterions.PLACE_ENERGIZER.conditions())
                .save(consumer, AITMod.MOD_ID + "/place_energizer");

        AdvancementHolder placeCoral = Advancement.Builder.advancement().parent(placeEnergizer)
                .display(AITBlocks.CORAL_PLANT, Component.translatable("achievement.ait.title.place_coral"),
                        Component.translatable("achievement.ait.description.place_coral"),
                        null,
                        AdvancementType.TASK, true, true, true)
                .addCriterion("place_coral", TardisCriterions.PLACE_CORAL.conditions())
                .save(consumer, AITMod.MOD_ID + "/place_coral");

        AdvancementHolder firstEnter = Advancement.Builder.advancement().parent(placeCoral)
                .display(AITItems.TARDIS_ITEM, Component.translatable("achievement.ait.title.enter_tardis"),
                        Component.translatable("achievement.ait.description.enter_tardis"), null, AdvancementType.CHALLENGE, true,
                        true, false)
                .addCriterion("enter_tardis", TardisCriterions.ENTER_TARDIS.conditions())
                .save(consumer, AITMod.MOD_ID + "/enter_tardis"); // for now this is the root advancement, meaning
        // its the first
        // one
        // that shows

        AdvancementHolder feedPowerConverter = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITBlocks.POWER_CONVERTER, Component.translatable("achievement.ait.title.feed_power_converter"),
                        Component.translatable("achievement.ait.description.feed_power_converter"),
                        null,
                        AdvancementType.TASK, true, true, true)
                .addCriterion("feed_power_converter", TardisCriterions.FEED_POWER_CONVERTER.conditions())
                .save(consumer, AITMod.MOD_ID + "/feed_power_converter");

        AdvancementHolder attackEyebrows = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITItems.SONIC_SCREWDRIVER, Component.translatable("achievement.ait.title.attack_eyebrows"),
                        Component.translatable("achievement.ait.description.attack_eyebrows"),
                        null,
                        AdvancementType.TASK, true, true, true)
                .addCriterion("attack_eyebrows", TardisCriterions.ATTACK_EYEBROWS.conditions())
                .save(consumer, AITMod.MOD_ID + "/attack_eyebrows");

        AdvancementHolder brandNew = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITItems.MUG, Component.translatable("achievement.ait.title.brand_new"),
                        Component.translatable("achievement.ait.description.brand_new"),
                        null,
                        AdvancementType.CHALLENGE, true, true, true)
                .addCriterion("brand_new", TardisCriterions.BRAND_NEW.conditions())
                .save(consumer, AITMod.MOD_ID + "/brand_new");

        AdvancementHolder ironKey = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITItems.IRON_KEY, Component.translatable("achievement.ait.title.iron_key"),
                        Component.translatable("achievement.ait.description.iron_key"), null, AdvancementType.TASK, true, false, true)
                .addCriterion("iron_key", InventoryChangeTrigger.TriggerInstance.hasItems(AITItems.IRON_KEY))
                .save(consumer, AITMod.MOD_ID + "/iron_key");

        AdvancementHolder goldKey = Advancement.Builder.advancement().parent(ironKey)
                .display(AITItems.GOLD_KEY, Component.translatable("achievement.ait.title.gold_key"), Component.translatable("achievement.ait.description.gold_key"), null,
                        AdvancementType.TASK, true, false, true)
                .addCriterion("gold_key", InventoryChangeTrigger.TriggerInstance.hasItems(AITItems.GOLD_KEY))
                .save(consumer, AITMod.MOD_ID + "/gold_key");

        AdvancementHolder netheriteKey = Advancement.Builder.advancement().parent(goldKey)
                .display(AITItems.NETHERITE_KEY, Component.translatable("achievement.ait.title.netherite_key"), Component.translatable("achievement.ait.description.netherite_key"),
                        null, AdvancementType.TASK, true, true, true)
                .addCriterion("netherite_key", InventoryChangeTrigger.TriggerInstance.hasItems(AITItems.NETHERITE_KEY))
                .save(consumer, AITMod.MOD_ID + "/netherite_key");

        AdvancementHolder classicKey = Advancement.Builder.advancement().parent(netheriteKey)
                .display(AITItems.CLASSIC_KEY, Component.translatable("achievement.ait.title.classic_key"),
                        Component.translatable("achievement.ait.description.classic_key"), null, AdvancementType.TASK, true, true, true)
                .addCriterion("classic_key", InventoryChangeTrigger.TriggerInstance.hasItems(AITItems.CLASSIC_KEY))
                .save(consumer, AITMod.MOD_ID + "/classic_key");

        AdvancementHolder firstDemat = Advancement.Builder.advancement().parent(firstEnter)
                .display(Items.ENDER_EYE, Component.translatable("achievement.ait.title.first_demat"), Component.translatable(
                        "achievement.ait.description.first_demat"),
                        null, AdvancementType.CHALLENGE, true, true, true)
                .addCriterion("first_demat", TardisCriterions.TAKEOFF.conditions())
                .save(consumer, AITMod.MOD_ID + "/first_demat");

        AdvancementHolder firstCrash = Advancement.Builder.advancement().parent(firstDemat)
                .display(Items.TNT, Component.translatable("achievement.ait.title.first_crash"), Component.translatable(
                        "achievement.ait.description.first_crash"),
                        null, AdvancementType.CHALLENGE, true, true, true)
                .addCriterion("first_crash", TardisCriterions.CRASH.conditions())
                .save(consumer, AITMod.MOD_ID + "/first_crash");

        AdvancementHolder breakGrowth = Advancement.Builder.advancement().parent(firstEnter)
                .display(Items.OAK_LEAVES, Component.translatable("achievement.ait.title.break_growth"), Component.translatable(
                        "achievement.ait.description.break_growth"),
                        null, AdvancementType.TASK, true, false, true)
                .addCriterion("break_growth", TardisCriterions.VEGETATION.conditions())
                .save(consumer, AITMod.MOD_ID + "/break_growth");

        AdvancementHolder redecoration = Advancement.Builder.advancement().parent(firstEnter)
                .display(Items.PAINTING , Component.translatable("achievement.ait.title.redecorate"),
                        Component.translatable("achievement.ait.description.redecorate"), null, AdvancementType.TASK, true, false, true)
                .addCriterion("redecorate", TardisCriterions.REDECORATE.conditions())
                .save(consumer, AITMod.MOD_ID + "/redecorate");

        AdvancementHolder sonicWood = Advancement.Builder.advancement().parent(root)
                .display(AITItems.SONIC_SCREWDRIVER, Component.translatable("achievement.ait.title.ultimate_counter"),
                        Component.translatable("achievement.ait.description.ultimate_counter"), null, AdvancementType.TASK, true, false, true)
                .addCriterion("ultimate_counter", TardisCriterions.SONIC_WOOD.conditions())
                .save(consumer, AITMod.MOD_ID + "/ultimate_counter");

        AdvancementHolder axeTardis = Advancement.Builder.advancement().parent(firstEnter)
                .display(Items.IRON_AXE, Component.translatable("achievement.ait.title.forced_entry"),
                        Component.translatable("achievement.ait.description.forced_entry"), null, AdvancementType.TASK, true, false, true)
                .addCriterion("forced_entry", TardisCriterions.FORCED_ENTRY.conditions())
                .save(consumer, AITMod.MOD_ID + "/forced_entry");

        AdvancementHolder pilotHigh = Advancement.Builder.advancement().parent(firstDemat)
                .display(AITItems.ZEITON_DUST, Component.translatable("achievement.ait.title.pui"),
                        Component.translatable("achievement.ait.description.pui"), null, AdvancementType.TASK, true, false, true)
                .addCriterion("pui", TardisCriterions.PILOT_HIGH.conditions())
                .save(consumer, AITMod.MOD_ID + "/pui");

        AdvancementHolder reachPilot = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITBlocks.CORAL_PLANT, Component.translatable("achievement.ait.title.bonding"),
                        Component.translatable("achievement.ait.description.bonding"), null, AdvancementType.TASK, true, false, true)
                .addCriterion("bonding", TardisCriterions.REACH_PILOT.conditions())
                .save(consumer, AITMod.MOD_ID + "/bonding");

        AdvancementHolder reachOwner = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITItems.TARDIS_ITEM, Component.translatable("achievement.ait.title.owner_ship"),
                        Component.translatable("achievement.ait.description.owner_ship"), null, AdvancementType.CHALLENGE, true, true, true)
                .addCriterion("owner_ship", TardisCriterions.REACH_OWNER.conditions())
                .save(consumer, AITMod.MOD_ID + "/owner_ship");

        AdvancementHolder enableSubsystem = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITBlocks.GENERIC_SUBSYSTEM, Component.translatable("achievement.ait.title.enable_subsystem"),
                        Component.translatable("achievement.ait.description.enable_subsystem"), null, AdvancementType.CHALLENGE, true, true, true)
                .addCriterion("enable_subsystem", TardisCriterions.ENABLE_SUBSYSTEM.conditions())
                .save(consumer, AITMod.MOD_ID + "/enable_subsystem");
        AdvancementHolder repairSubsystem = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITItems.HAMMER, Component.translatable("achievement.ait.title.repair_subsystem"),
                        Component.translatable("achievement.ait.description.repair_subsystem"), null, AdvancementType.TASK, true, true, true)
                .addCriterion("repair_subsystem", TardisCriterions.REPAIR_SUBSYSTEM.conditions())
                .save(consumer, AITMod.MOD_ID + "/repair_subsystem");
        AdvancementHolder enginesPhase = Advancement.Builder.advancement().parent(firstDemat)
                .display(AITItems.DEMATERIALIZATION_CIRCUIT, Component.translatable("achievement.ait.title.engines_phase"),
                        Component.translatable("achievement.ait.description.engines_phase"), null, AdvancementType.CHALLENGE, true, true, true)
                .addCriterion("engines_phase", TardisCriterions.ENGINES_PHASE.conditions())
                .save(consumer, AITMod.MOD_ID + "/engines_phase");

        AdvancementHolder statRemote = Advancement.Builder.advancement().parent(ironKey)
                .display(AITItems.REMOTE_ITEM, Component.translatable("achievement.ait.title.remote"),
                        Component.translatable("achievement.ait.description.remote"), null, AdvancementType.CHALLENGE, true, false, true)
                .addCriterion("gain_remote", InventoryChangeTrigger.TriggerInstance.hasItems(AITItems.REMOTE_ITEM))
                .save(consumer, AITMod.MOD_ID + "/gain_remote");

        AdvancementHolder firstRift = Advancement.Builder.advancement().parent(root)
                .display(AITItems.CORAL_FRAGMENT, Component.translatable("achievement.ait.title.first_rift"),
                        Component.translatable("achievement.ait.description.first_rift"), null, AdvancementType.TASK, true, true, false)
                .addCriterion("first_rift", TardisCriterions.FIRST_RIFT.conditions())
                .save(consumer, AITMod.MOD_ID + "/first_rift");
    }
}
