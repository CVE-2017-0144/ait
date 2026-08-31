package dev.amble.ait.module.planet.core.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import dev.amble.ait.module.planet.core.PlanetBlocks;
import dev.amble.ait.module.planet.core.PlanetItems;
import dev.amble.lib.platform.registry.PlatformRegistries;

public class PlanetCustomTrades {
    public static void registerCustomTrades() {
        // Wandering Trader
        PlatformRegistries.wanderingTraderTrades(3,
                factories -> {
                    factories.add((entity, random) -> new MerchantOffer(
                            new ItemCost(Items.EMERALD, 6),
                            new ItemStack(PlanetBlocks.ANORTHOSITE, 3),
                            1, 24, 0.075f));

                    factories.add((entity, random) -> new MerchantOffer(
                            new ItemCost(Items.EMERALD, 9),
                            new ItemStack(PlanetBlocks.MARTIAN_STONE, 3),
                            1, 24, 0.075f));
                });

        PlatformRegistries.wanderingTraderTrades(2,
                factories -> {
                    factories.add((entity, random) -> new MerchantOffer(
                            new ItemCost(Items.EMERALD, 2),
                            new ItemStack(PlanetItems.ANORTHOSITE_PICKAXE, 1),
                            3, 12, 0.075f));

                    factories.add((entity, random) -> new MerchantOffer(
                            new ItemCost(Items.EMERALD, 3),
                            new ItemStack(PlanetItems.ANORTHOSITE_SWORD, 1),
                            5, 12, 0.075f));

                    factories.add((entity, random) -> new MerchantOffer(
                            new ItemCost(Items.EMERALD, 3),
                            new ItemStack(PlanetItems.MARTIAN_STONE_PICKAXE, 1),
                            7, 12, 0.075f));

                    factories.add((entity, random) -> new MerchantOffer(
                            new ItemCost(Items.EMERALD, 1),
                            new ItemStack(PlanetItems.ANORTHOSITE_HOE, 1),
                            4, 12, 0.075f));
                });
    }
}