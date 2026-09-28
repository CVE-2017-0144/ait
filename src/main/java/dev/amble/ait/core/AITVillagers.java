package dev.amble.ait.core;

import com.google.common.collect.ImmutableSet;
import dev.amble.ait.AITMod;
import dev.amble.lib.platform.registry.PlatformRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;

public class AITVillagers {
    public static final ResourceKey<PoiType> FABRICATOR_ENGINEER_POI_KEY = poiKey("fabricator_engineer_poi");
    public static final PoiType FABRICATOR_ENGINEER_POI = registerPoi("fabricator_engineer_poi", AITBlocks.FABRICATOR);

    public static final VillagerProfession FABRICATOR_ENGINEER = registerProfession("fabricator_engineer", FABRICATOR_ENGINEER_POI_KEY);


    private static VillagerProfession registerProfession(String name, ResourceKey<PoiType> type) {
        return Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, AITMod.id(name),
                new VillagerProfession(name, entry -> entry.is(type), entry -> entry.is(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));
    }

    private static PoiType registerPoi(String name, Block block) {
        return PlatformRegistries.pointOfInterest(AITMod.id(name), 1, 1, block);
    }

    private static ResourceKey<PoiType> poiKey(String name) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, AITMod.id(name));
    }

    public static void init() {
    }
}
