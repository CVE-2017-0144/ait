package dev.amble.ait.mixin.client.rendering;

import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VertexBuffer.class)
public interface VertexBufferWrapper {
    @Accessor
    void setMode(VertexFormat.Mode drawMode);

    @Accessor
    void setIndexType(VertexFormat.IndexType indexType);

    @Accessor("indexType")
    VertexFormat.IndexType ait$getIndexType();
}
