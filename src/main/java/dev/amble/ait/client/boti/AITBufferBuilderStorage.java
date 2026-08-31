package dev.amble.ait.client.boti;

import java.util.SortedMap;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.BufferBuilder;
import dev.amble.ait.client.renderers.AITRenderLayers;

@Environment(value=EnvType.CLIENT)
public class AITBufferBuilderStorage extends RenderBuffers {
    private final SortedMap<RenderType, BufferBuilder> botiBuilder = Util.make(new Object2ObjectLinkedOpenHashMap(), map -> {
        AITBufferBuilderStorage.put(map, AITRenderLayers.getBoti());
    });
    private final MultiBufferSource.BufferSource botiVertexConsumer = MultiBufferSource.immediateWithBuffers(this.botiBuilder, new BufferBuilder(256));

    private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> builderStorage, RenderType layer) {
        builderStorage.put(layer, new BufferBuilder(layer.bufferSize()));
    }

    public MultiBufferSource.BufferSource getBotiVertexConsumer() {
        return this.botiVertexConsumer;
    }
}
