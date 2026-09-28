package dev.amble.ait.client.boti;

import java.util.Map;
import java.util.stream.Collectors;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.amble.ait.mixin.client.rendering.VertexBufferWrapper;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;

public class BOTIVBO {
    public static final VertexFormat format = DefaultVertexFormat.BLOCK;
    public final Map<RenderType, ByteBufferBuilder> bufferBuilders = RenderType.chunkBufferLayers().stream().collect(Collectors.toMap(renderLayer -> renderLayer, renderLayer -> new ByteBufferBuilder(renderLayer.bufferSize())));
    public final Map<RenderType, VertexBuffer> vbo = RenderType.chunkBufferLayers().stream().collect(Collectors.toMap(renderLayer -> renderLayer, renderLayer -> new VertexBuffer(VertexBuffer.Usage.STATIC)));

    public BOTIVBO() {
        this.init();
    }
    public VertexBuffer get(RenderType layer) {
        return this.vbo.get(layer);
    }

    public VertexBuffer getVBO(RenderType layer) {
        return this.vbo.getOrDefault(layer, this.vbo.get(RenderType.solid()));
    }

    public ByteBufferBuilder getBufferBuilder(RenderType layer) {
        return this.bufferBuilders.get(layer);
    }

    public void init() {
        for (RenderType layer : RenderType.chunkBufferLayers()) {
            this.bufferBuilders.put(layer, new ByteBufferBuilder(layer.bufferSize()));
            this.vbo.put(layer, new VertexBuffer(VertexBuffer.Usage.STATIC));
        }
    }

    public void begin(RenderType layer) {
        this.getVBO(layer).bind();
    }

    public void reset(RenderType layer) {
        this.bufferBuilders.get(layer).clear();
        this.bufferBuilders.get(layer).discard();
    }

    public void upload(RenderType layer) {
        MeshData builtBuffer = new BufferBuilder(this.getBufferBuilder(layer), layer.mode(), format).build();
        if (builtBuffer == null)
            return;
        this.getVBO(layer).upload(builtBuffer);
    }

    public void unbind(RenderType layer) {
        this.getBufferBuilder(layer).discard();
        this.getBufferBuilder(layer).clear();
        VertexBuffer.unbind();
    }

    public void draw() {
        this.vbo.forEach((layer, vbo) -> {
            /*if (((VertexBufferWrapper) this.getVBO(layer)).getIndexType() == null) {
                return; // Skip drawing if there's no index type
            }*/
            this.begin(layer);
            format.setupBufferState();
            layer.setupRenderState();
            ((VertexBufferWrapper) this.getVBO(layer)).setMode(layer.mode());
            ((VertexBufferWrapper) this.getVBO(layer)).setIndexType(VertexFormat.IndexType.SHORT);
            RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);
            this.getVBO(layer).draw();
            layer.clearRenderState();
            format.clearBufferState();
            VertexBuffer.unbind();
        });
    }
}
