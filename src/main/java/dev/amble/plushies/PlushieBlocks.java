package dev.amble.plushies;

import dev.amble.lib.block.ABlockSettings;
import dev.amble.lib.container.impl.BlockContainer;
import dev.amble.lib.item.AItemSettings;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class PlushieBlocks extends BlockContainer {

    public static final List<String> DEVS = List.of(
            "loqor", "theo", "saturn",
            "avery", "wanzz", "ember808", "max",
            "lake", "classic", "pursephone", "ben",
            "nyx", "rhyno", "monke", "kking", "rat",
            "cosmic", "dian", "tree", "echo",
            "lucien", "maggie", "ember", "peanut", "tardis"
    );

    public static final ArrayList<Block> MARKETABLE_PLUSHIES = new ArrayList<>();

    public static Block[] getAllMarketablePlushies() {
        return MARKETABLE_PLUSHIES.toArray(new Block[0]);
    }

    @Override
    public Item.Properties createBlockItemSettings(Block block) {
        return new AItemSettings().group(PlushieItemGroups.PLUSHIES);
    }

    public static void registerAll(String namespace) {
        PlushieBlocks self = new PlushieBlocks();
        self.start(DEVS.size());

        for (String name : DEVS) {
            ABlockSettings settings = new ABlockSettings();
            Block block = new MarketablePlushieBlock(settings, name);
            ResourceLocation id = new ResourceLocation(namespace, name + "_marketable_plushie");

            Registry.register(BuiltInRegistries.BLOCK, id, block);

            Item item = self.createBlockItem(block, settings.itemSettings());
            Registry.register(BuiltInRegistries.ITEM, id, item);
            self.items.add(item);

            MARKETABLE_PLUSHIES.add(block);
        }

        self.finish();
    }

    public static final Block GIFT_BOX = new GiftBoxBlock(ABlockSettings.of().itemSettings(new AItemSettings().group(PlushieItemGroups.PLUSHIES).stacksTo(16)).instabreak());
}