package dev.amble.lib.platform.render;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Matrix4f;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.profiling.ProfilerFiller;

@Environment(EnvType.CLIENT)
public interface WorldRenderContext {

    PoseStack matrixStack();

    Camera camera();

    MultiBufferSource consumers();

    DeltaTracker tickCounter();

    ClientLevel world();

    LevelRenderer worldRenderer();

    Matrix4f projectionMatrix();

    Matrix4f positionMatrix();

    Frustum frustum();

    ProfilerFiller profiler();
}
