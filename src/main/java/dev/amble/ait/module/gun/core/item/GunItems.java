package dev.amble.ait.module.gun.core.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import dev.amble.lib.container.impl.ItemContainer;


public class GunItems extends ItemContainer {

    public static final Item CULT_STASER = new BaseGunItem(new FabricItemSettings().stacksTo(1).rarity(Rarity.RARE));
    public static final Item CULT_STASER_RIFLE = new StaserRifleItem(new FabricItemSettings().stacksTo(1).rarity(Rarity.RARE));
    public static final Item STASER_BOLT_MAGAZINE = new StaserBoltMagazine(new FabricItemSettings().stacksTo(1).rarity(Rarity.RARE));

    static {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.addAfter(Items.CROSSBOW, CULT_STASER);
            entries.addAfter(CULT_STASER, CULT_STASER_RIFLE);
            entries.addAfter(Items.TIPPED_ARROW, STASER_BOLT_MAGAZINE);
        });
    }
}
