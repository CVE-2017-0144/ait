package dev.amble.ait.module.decoration.core;

import dev.amble.ait.module.decoration.DecorationModule;
import dev.amble.lib.container.impl.BlockContainer;
import dev.amble.lib.item.AItemSettings;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class DecorationBlocks extends BlockContainer {



    @Override
    public Item.Properties createBlockItemSettings(Block block) {
        return new AItemSettings().group(DecorationModule.instance().getItemGroup());
    }
}
