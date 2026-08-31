package dev.amble.ait.datagen.datagen_providers;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.module.ModuleRegistry;

public class AITAchievementProvider extends FabricAdvancementProvider {
    public AITAchievementProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {

        ModuleRegistry.instance().iterator().forEachRemaining(module -> module.getDataGenerator().ifPresent(dataGenerator -> {
            dataGenerator.advancements(consumer);
        }));

        Advancement root = Advancement.Builder.advancement()
                .display(AITItems.CHARGED_ZEITON_CRYSTAL, Component.translatable("achievement.ait.title.root"),
                        Component.translatable("achievement.ait.description.root"), new ResourceLocation("textures/entity/end_portal.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("root", TardisCriterions.ROOT.conditions())
                .rewards(AdvancementRewards.Builder.function(new ResourceLocation("ait", "wikimessage")))
                .save(consumer, AITMod.MOD_ID + "/root");

        Advancement placeEnergizer = Advancement.Builder.advancement().parent(root)
                .display(AITBlocks.MATRIX_ENERGIZER, Component.translatable("achievement.ait.title.place_energizer"),
                        Component.translatable("achievement.ait.description.place_energizer"),
                        null,
                        FrameType.TASK, true, true, true)
                .addCriterion("place_energizer", TardisCriterions.PLACE_ENERGIZER.conditions())
                .save(consumer, AITMod.MOD_ID + "/place_energizer");

        Advancement placeCoral = Advancement.Builder.advancement().parent(placeEnergizer)
                .display(AITBlocks.CORAL_PLANT, Component.translatable("achievement.ait.title.place_coral"),
                        Component.translatable("achievement.ait.description.place_coral"),
                        null,
                        FrameType.TASK, true, true, true)
                .addCriterion("place_coral", TardisCriterions.PLACE_CORAL.conditions())
                .save(consumer, AITMod.MOD_ID + "/place_coral");

        Advancement firstEnter = Advancement.Builder.advancement().parent(placeCoral)
                .display(AITItems.TARDIS_ITEM, Component.translatable("achievement.ait.title.enter_tardis"),
                        Component.translatable("achievement.ait.description.enter_tardis"), null, FrameType.CHALLENGE, true,
                        true, false)
                .addCriterion("enter_tardis", TardisCriterions.ENTER_TARDIS.conditions())
                .save(consumer, AITMod.MOD_ID + "/enter_tardis"); // for now this is the root advancement, meaning
        // its the first
        // one
        // that shows

        Advancement feedPowerConverter = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITBlocks.POWER_CONVERTER, Component.translatable("achievement.ait.title.feed_power_converter"),
                        Component.translatable("achievement.ait.description.feed_power_converter"),
                        null,
                        FrameType.TASK, true, true, true)
                .addCriterion("feed_power_converter", TardisCriterions.FEED_POWER_CONVERTER.conditions())
                .save(consumer, AITMod.MOD_ID + "/feed_power_converter");

        Advancement attackEyebrows = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITItems.SONIC_SCREWDRIVER, Component.translatable("achievement.ait.title.attack_eyebrows"),
                        Component.translatable("achievement.ait.description.attack_eyebrows"),
                        null,
                        FrameType.TASK, true, true, true)
                .addCriterion("attack_eyebrows", TardisCriterions.ATTACK_EYEBROWS.conditions())
                .save(consumer, AITMod.MOD_ID + "/attack_eyebrows");

        Advancement brandNew = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITItems.MUG, Component.translatable("achievement.ait.title.brand_new"),
                        Component.translatable("achievement.ait.description.brand_new"),
                        null,
                        FrameType.CHALLENGE, true, true, true)
                .addCriterion("brand_new", TardisCriterions.BRAND_NEW.conditions())
                .save(consumer, AITMod.MOD_ID + "/brand_new");

        Advancement ironKey = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITItems.IRON_KEY, Component.translatable("achievement.ait.title.iron_key"),
                        Component.translatable("achievement.ait.description.iron_key"), null, FrameType.TASK, true, false, true)
                .addCriterion("iron_key", InventoryChangeTrigger.TriggerInstance.hasItems(AITItems.IRON_KEY))
                .save(consumer, AITMod.MOD_ID + "/iron_key");

        Advancement goldKey = Advancement.Builder.advancement().parent(ironKey)
                .display(AITItems.GOLD_KEY, Component.translatable("achievement.ait.title.gold_key"), Component.translatable("achievement.ait.description.gold_key"), null,
                        FrameType.TASK, true, false, true)
                .addCriterion("gold_key", InventoryChangeTrigger.TriggerInstance.hasItems(AITItems.GOLD_KEY))
                .save(consumer, AITMod.MOD_ID + "/gold_key");

        Advancement netheriteKey = Advancement.Builder.advancement().parent(goldKey)
                .display(AITItems.NETHERITE_KEY, Component.translatable("achievement.ait.title.netherite_key"), Component.translatable("achievement.ait.description.netherite_key"),
                        null, FrameType.TASK, true, true, true)
                .addCriterion("netherite_key", InventoryChangeTrigger.TriggerInstance.hasItems(AITItems.NETHERITE_KEY))
                .save(consumer, AITMod.MOD_ID + "/netherite_key");

        Advancement classicKey = Advancement.Builder.advancement().parent(netheriteKey)
                .display(AITItems.CLASSIC_KEY, Component.translatable("achievement.ait.title.classic_key"),
                        Component.translatable("achievement.ait.description.classic_key"), null, FrameType.TASK, true, true, true)
                .addCriterion("classic_key", InventoryChangeTrigger.TriggerInstance.hasItems(AITItems.CLASSIC_KEY))
                .save(consumer, AITMod.MOD_ID + "/classic_key");

        Advancement firstDemat = Advancement.Builder.advancement().parent(firstEnter)
                .display(Items.ENDER_EYE, Component.translatable("achievement.ait.title.first_demat"), Component.translatable(
                        "achievement.ait.description.first_demat"),
                        null, FrameType.CHALLENGE, true, true, true)
                .addCriterion("first_demat", TardisCriterions.TAKEOFF.conditions())
                .save(consumer, AITMod.MOD_ID + "/first_demat");

        Advancement firstCrash = Advancement.Builder.advancement().parent(firstDemat)
                .display(Items.TNT, Component.translatable("achievement.ait.title.first_crash"), Component.translatable(
                        "achievement.ait.description.first_crash"),
                        null, FrameType.CHALLENGE, true, true, true)
                .addCriterion("first_crash", TardisCriterions.CRASH.conditions())
                .save(consumer, AITMod.MOD_ID + "/first_crash");

        Advancement breakGrowth = Advancement.Builder.advancement().parent(firstEnter)
                .display(Items.OAK_LEAVES, Component.translatable("achievement.ait.title.break_growth"), Component.translatable(
                        "achievement.ait.description.break_growth"),
                        null, FrameType.TASK, true, false, true)
                .addCriterion("break_growth", TardisCriterions.VEGETATION.conditions())
                .save(consumer, AITMod.MOD_ID + "/break_growth");

        Advancement redecoration = Advancement.Builder.advancement().parent(firstEnter)
                .display(Items.PAINTING , Component.translatable("achievement.ait.title.redecorate"),
                        Component.translatable("achievement.ait.description.redecorate"), null, FrameType.TASK, true, false, true)
                .addCriterion("redecorate", TardisCriterions.REDECORATE.conditions())
                .save(consumer, AITMod.MOD_ID + "/redecorate");

        Advancement sonicWood = Advancement.Builder.advancement().parent(root)
                .display(AITItems.SONIC_SCREWDRIVER, Component.translatable("achievement.ait.title.ultimate_counter"),
                        Component.translatable("achievement.ait.description.ultimate_counter"), null, FrameType.TASK, true, false, true)
                .addCriterion("ultimate_counter", TardisCriterions.SONIC_WOOD.conditions())
                .save(consumer, AITMod.MOD_ID + "/ultimate_counter");

        Advancement axeTardis = Advancement.Builder.advancement().parent(firstEnter)
                .display(Items.IRON_AXE, Component.translatable("achievement.ait.title.forced_entry"),
                        Component.translatable("achievement.ait.description.forced_entry"), null, FrameType.TASK, true, false, true)
                .addCriterion("forced_entry", TardisCriterions.FORCED_ENTRY.conditions())
                .save(consumer, AITMod.MOD_ID + "/forced_entry");

        Advancement pilotHigh = Advancement.Builder.advancement().parent(firstDemat)
                .display(AITItems.ZEITON_DUST, Component.translatable("achievement.ait.title.pui"),
                        Component.translatable("achievement.ait.description.pui"), null, FrameType.TASK, true, false, true)
                .addCriterion("pui", TardisCriterions.PILOT_HIGH.conditions())
                .save(consumer, AITMod.MOD_ID + "/pui");

        Advancement reachPilot = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITBlocks.CORAL_PLANT, Component.translatable("achievement.ait.title.bonding"),
                        Component.translatable("achievement.ait.description.bonding"), null, FrameType.TASK, true, false, true)
                .addCriterion("bonding", TardisCriterions.REACH_PILOT.conditions())
                .save(consumer, AITMod.MOD_ID + "/bonding");

        Advancement reachOwner = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITItems.TARDIS_ITEM, Component.translatable("achievement.ait.title.owner_ship"),
                        Component.translatable("achievement.ait.description.owner_ship"), null, FrameType.CHALLENGE, true, true, true)
                .addCriterion("owner_ship", TardisCriterions.REACH_OWNER.conditions())
                .save(consumer, AITMod.MOD_ID + "/owner_ship");

        Advancement enableSubsystem = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITBlocks.GENERIC_SUBSYSTEM, Component.translatable("achievement.ait.title.enable_subsystem"),
                        Component.translatable("achievement.ait.description.enable_subsystem"), null, FrameType.CHALLENGE, true, true, true)
                .addCriterion("enable_subsystem", TardisCriterions.ENABLE_SUBSYSTEM.conditions())
                .save(consumer, AITMod.MOD_ID + "/enable_subsystem");
        Advancement repairSubsystem = Advancement.Builder.advancement().parent(firstEnter)
                .display(AITItems.HAMMER, Component.translatable("achievement.ait.title.repair_subsystem"),
                        Component.translatable("achievement.ait.description.repair_subsystem"), null, FrameType.TASK, true, true, true)
                .addCriterion("repair_subsystem", TardisCriterions.REPAIR_SUBSYSTEM.conditions())
                .save(consumer, AITMod.MOD_ID + "/repair_subsystem");
        Advancement enginesPhase = Advancement.Builder.advancement().parent(firstDemat)
                .display(AITItems.DEMATERIALIZATION_CIRCUIT, Component.translatable("achievement.ait.title.engines_phase"),
                        Component.translatable("achievement.ait.description.engines_phase"), null, FrameType.CHALLENGE, true, true, true)
                .addCriterion("engines_phase", TardisCriterions.ENGINES_PHASE.conditions())
                .save(consumer, AITMod.MOD_ID + "/engines_phase");

        Advancement statRemote = Advancement.Builder.advancement().parent(ironKey)
                .display(AITItems.REMOTE_ITEM, Component.translatable("achievement.ait.title.remote"),
                        Component.translatable("achievement.ait.description.remote"), null, FrameType.CHALLENGE, true, false, true)
                .addCriterion("gain_remote", InventoryChangeTrigger.TriggerInstance.hasItems(AITItems.REMOTE_ITEM))
                .save(consumer, AITMod.MOD_ID + "/gain_remote");

        Advancement firstRift = Advancement.Builder.advancement().parent(root)
                .display(AITItems.CORAL_FRAGMENT, Component.translatable("achievement.ait.title.first_rift"),
                        Component.translatable("achievement.ait.description.first_rift"), null, FrameType.TASK, true, true, false)
                .addCriterion("first_rift", TardisCriterions.FIRST_RIFT.conditions())
                .save(consumer, AITMod.MOD_ID + "/first_rift");
    }
}
