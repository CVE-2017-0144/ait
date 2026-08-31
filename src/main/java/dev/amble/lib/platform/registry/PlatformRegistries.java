package dev.amble.lib.platform.registry;

import java.util.List;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public final class PlatformRegistries {

    private PlatformRegistries() {}

    public static <T> MappedRegistry<T> createRegistry(ResourceKey<Registry<T>> key) {
        return FabricRegistryBuilder.createSimple(key).buildAndRegister();
    }

    public static SimpleParticleType simpleParticle() {
        return FabricParticleTypes.simple();
    }

    public static PoiType pointOfInterest(ResourceLocation id, int ticketCount, int searchDistance,
            Block... blocks) {
        return PointOfInterestHelper.register(id, ticketCount, searchDistance, blocks);
    }

    public static void attributes(EntityType<? extends LivingEntity> type, AttributeSupplier.Builder builder) {
        FabricDefaultAttributeRegistry.register(type, builder);
    }

    public static void villagerTrades(VillagerProfession profession, int level,
            Consumer<List<VillagerTrades.ItemListing>> factories) {
        TradeOfferHelper.registerVillagerOffers(profession, level, factories::accept);
    }

    public static void wanderingTraderTrades(int level, Consumer<List<VillagerTrades.ItemListing>> factories) {
        TradeOfferHelper.registerWanderingTraderOffers(level, factories::accept);
    }
}
