package dev.amble.plushies.client;

import dev.amble.lib.platform.ClientModEntrypoint;
import dev.amble.lib.platform.render.ClientRegistries;
import dev.amble.plushies.PlushieBlockEntities;
import dev.amble.plushies.PlushieBlocks;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;

public class PlushiesClient implements ClientModEntrypoint {

    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(PlushieBlockEntities.MARKETABLE_PLUSHIE_BLOCK_ENTITY_TYPE, MarketablePlushieRenderer::new);

        for (Block block : PlushieBlocks.getAllMarketablePlushies()) {
            ClientRegistries.itemRenderer(block.asItem(), new PlushieDynamicItemRenderer());
        }
    }
}
