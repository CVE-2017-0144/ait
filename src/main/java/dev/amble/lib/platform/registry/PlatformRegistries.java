package dev.amble.lib.platform.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import dev.amble.lib.platform.Platform;

public class PlatformRegistries {

    private record AttributeEntry(EntityType<? extends LivingEntity> type,
            Supplier<AttributeSupplier.Builder> builder) {}

    private record VillagerEntry(VillagerProfession profession, int level,
            Consumer<List<VillagerTrades.ItemListing>> factories) {}

    private record WandererEntry(int level, Consumer<List<VillagerTrades.ItemListing>> factories) {}

    private static final List<Registry<?>> REGISTRIES = new ArrayList<>();
    private static final List<AttributeEntry> ATTRIBUTES = new ArrayList<>();
    private static final List<VillagerEntry> VILLAGER_TRADES = new ArrayList<>();
    private static final List<WandererEntry> WANDERER_TRADES = new ArrayList<>();

    static {
        IEventBus modBus = Platform.modBus();

        if (modBus != null) {
            modBus.addListener(NewRegistryEvent.class, event -> REGISTRIES.forEach(event::register));
            modBus.addListener(EntityAttributeCreationEvent.class,
                    event -> ATTRIBUTES.forEach(entry -> event.put(entry.type(), entry.builder().get().build())));
        }

        NeoForge.EVENT_BUS.addListener(VillagerTradesEvent.class, event -> {
            for (VillagerEntry entry : VILLAGER_TRADES) {
                if (entry.profession().equals(event.getType()))
                    entry.factories().accept(event.getTrades().get(entry.level()));
            }
        });

        NeoForge.EVENT_BUS.addListener(WandererTradesEvent.class, event -> {
            for (WandererEntry entry : WANDERER_TRADES) {
                if (entry.level() == 1)
                    entry.factories().accept(event.getGenericTrades());
                else if (entry.level() == 2)
                    entry.factories().accept(event.getRareTrades());
            }
        });
    }

    // built now, handed over on NewRegistryEvent
    public static <T> MappedRegistry<T> createRegistry(ResourceKey<Registry<T>> key) {
        MappedRegistry<T> registry = (MappedRegistry<T>) new RegistryBuilder<>(key).create();
        REGISTRIES.add(registry);
        return registry;
    }

    public static SimpleParticleType simpleParticle() {
        return new SimpleParticleType(false);
    }

    public static PoiType pointOfInterest(ResourceLocation id, int ticketCount, int searchDistance,
            Block... blocks) {
        java.util.Set<BlockState> states = new java.util.HashSet<>();

        for (Block block : blocks) {
            states.addAll(block.getStateDefinition().getPossibleStates());
        }

        return Registry.register(BuiltInRegistries.POINT_OF_INTEREST_TYPE, id,
                new PoiType(states, ticketCount, searchDistance));
    }

    // attributes aren't bound yet in the ctor
    public static void attributes(EntityType<? extends LivingEntity> type,
            Supplier<AttributeSupplier.Builder> builder) {
        ATTRIBUTES.add(new AttributeEntry(type, builder));
    }

    public static void villagerTrades(VillagerProfession profession, int level,
            Consumer<List<VillagerTrades.ItemListing>> factories) {
        VILLAGER_TRADES.add(new VillagerEntry(profession, level, factories));
    }

    public static void wanderingTraderTrades(int level, Consumer<List<VillagerTrades.ItemListing>> factories) {
        WANDERER_TRADES.add(new WandererEntry(level, factories));
    }
}
