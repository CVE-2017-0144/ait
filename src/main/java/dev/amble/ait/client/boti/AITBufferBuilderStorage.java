package dev.amble.ait.client.boti;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import dev.amble.ait.client.renderers.AITRenderLayers;
import java.util.SequencedMap;

@Environment(value = EnvType.CLIENT)
public class AITBufferBuilderStorage extends RenderBuffers {

    private static final int BOTI_BUFFER_SIZE = 786432;

    private final SequencedMap<RenderType, ByteBufferBuilder> botiBuilder = Util
            .make(new Object2ObjectLinkedOpenHashMap<>(), map -> put(map, AITRenderLayers.getBoti()));

    private final MultiBufferSource.BufferSource botiVertexConsumer = MultiBufferSource
            .immediateWithBuffers(this.botiBuilder, new ByteBufferBuilder(256));

    public AITBufferBuilderStorage() {
        super(BOTI_BUFFER_SIZE);
    }

    private static void put(Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> builderStorage,
            RenderType layer) {
        builderStorage.put(layer, new ByteBufferBuilder(layer.bufferSize()));
    }

    public MultiBufferSource.BufferSource getBotiVertexConsumer() {
        return this.botiVertexConsumer;
    }
}
