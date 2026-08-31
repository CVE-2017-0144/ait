package dev.amble.lib.platform.render;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Matrix4f;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.profiling.ProfilerFiller;

@Environment(EnvType.CLIENT)
final class FabricWorldRenderContext implements WorldRenderContext {

    private final net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext delegate;

    FabricWorldRenderContext(net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext delegate) {
        this.delegate = delegate;
    }

    net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext delegate() {
        return this.delegate;
    }

    @Override
    public PoseStack matrixStack() {
        return this.delegate.matrixStack();
    }

    @Override
    public Camera camera() {
        return this.delegate.camera();
    }

    @Override
    public MultiBufferSource consumers() {
        MultiBufferSource consumers = this.delegate.consumers();
        return consumers != null ? consumers : Minecraft.getInstance().renderBuffers().bufferSource();
    }

    @Override
    public DeltaTracker tickCounter() {
        return this.delegate.tickCounter();
    }

    @Override
    public ClientLevel world() {
        return this.delegate.world();
    }

    @Override
    public LevelRenderer worldRenderer() {
        return this.delegate.worldRenderer();
    }

    @Override
    public Matrix4f projectionMatrix() {
        return this.delegate.projectionMatrix();
    }

    @Override
    public Matrix4f positionMatrix() {
        return this.delegate.positionMatrix();
    }

    @Override
    public Frustum frustum() {
        return this.delegate.frustum();
    }

    @Override
    public ProfilerFiller profiler() {
        return this.delegate.profiler();
    }
}
