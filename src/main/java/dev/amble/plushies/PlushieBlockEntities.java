package dev.amble.plushies;

import dev.amble.lib.animation.HasBedrockModel;
import dev.amble.lib.container.impl.BlockEntityContainer;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class PlushieBlockEntities implements BlockEntityContainer {

    public static final BlockEntityType<MarketablePlushieBlockEntity> MARKETABLE_PLUSHIE_BLOCK_ENTITY_TYPE =
            BlockEntityType.Builder.of(MarketablePlushieBlockEntity::new,
                    PlushieBlocks.getAllMarketablePlushies()
            ).build(null);

    @HasBedrockModel
    public static final BlockEntityType<GiftBoxBlockEntity> GIFT_BOX_BLOCK_ENTITY_TYPE =
            BlockEntityType.Builder.of(GiftBoxBlockEntity::new,
                    PlushieBlocks.GIFT_BOX
            ).build(null);
}
