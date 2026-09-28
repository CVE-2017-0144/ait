package dev.loqor.portal.client;

import com.mojang.blaze3d.vertex.VertexConsumer;

public class OffsetVertexConsumer implements VertexConsumer {
    private final VertexConsumer delegate;
    private final double offsetX;
    private final double offsetY;
    private final double offsetZ;

    public OffsetVertexConsumer(VertexConsumer delegate, double offsetX, double offsetY, double offsetZ) {
        this.delegate = delegate;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    @Override
    public VertexConsumer addVertex(float x, float y, float z) {
        this.delegate.addVertex((float) (x + this.offsetX), (float) (y + this.offsetY), (float) (z + this.offsetZ));
        return this;
    }

    @Override
    public VertexConsumer setColor(int red, int green, int blue, int alpha) {
        this.delegate.setColor(red, green, blue, alpha);
        return this;
    }

    @Override
    public VertexConsumer setUv(float u, float v) {
        this.delegate.setUv(u, v);
        return this;
    }

    @Override
    public VertexConsumer setUv1(int u, int v) {
        this.delegate.setUv1(u, v);
        return this;
    }

    @Override
    public VertexConsumer setUv2(int u, int v) {
        this.delegate.setUv2(u, v);
        return this;
    }

    @Override
    public VertexConsumer setNormal(float x, float y, float z) {
        this.delegate.setNormal(x, y, z);
        return this;
    }
}
