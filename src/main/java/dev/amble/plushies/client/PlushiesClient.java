package dev.amble.plushies.client;

import dev.amble.plushies.PlushieBlockEntities;
import dev.amble.plushies.PlushieBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;

public class PlushiesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(PlushieBlockEntities.MARKETABLE_PLUSHIE_BLOCK_ENTITY_TYPE, MarketablePlushieRenderer::new);

        for (Block block : PlushieBlocks.getAllMarketablePlushies()) {
            BuiltinItemRendererRegistry.INSTANCE.register(block.asItem(), new PlushieDynamicItemRenderer());
        }
    }
}
