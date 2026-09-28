package dev.amble.lib.platform.render;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Matrix4f;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

@OnlyIn(Dist.CLIENT)
public final class WorldRenderEvents {

    private WorldRenderEvents() {}

    public interface Render {
        void render(WorldRenderContext context);
    }

    private static Event<Render> event() {
        return EventFactory.createArrayBacked(Render.class, callbacks -> context -> {
            for (Render callback : callbacks) {
                callback.render(context);
            }
        });
    }

    public static final Event<Render> AFTER_SETUP = event();
    public static final Event<Render> BEFORE_ENTITIES = event();
    public static final Event<Render> AFTER_ENTITIES = event();
    public static final Event<Render> END = event();

    static {
        NeoForge.EVENT_BUS.addListener(RenderLevelStageEvent.class, event -> {
            RenderLevelStageEvent.Stage stage = event.getStage();

            if (stage == RenderLevelStageEvent.Stage.AFTER_SKY)
                AFTER_SETUP.invoker().render(wrap(event));
            else if (stage == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS)
                BEFORE_ENTITIES.invoker().render(wrap(event));
            else if (stage == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES)
                AFTER_ENTITIES.invoker().render(wrap(event));
            else if (stage == RenderLevelStageEvent.Stage.AFTER_LEVEL)
                END.invoker().render(wrap(event));
        });
    }

    private static WorldRenderContext wrap(RenderLevelStageEvent event) {
        return new WorldRenderContext() {
            @Override
            public PoseStack matrixStack() {
                return event.getPoseStack();
            }

            @Override
            public Camera camera() {
                return event.getCamera();
            }

            @Override
            public MultiBufferSource consumers() {
                return Minecraft.getInstance().renderBuffers().bufferSource();
            }

            @Override
            public DeltaTracker tickCounter() {
                return event.getPartialTick();
            }

            @Override
            public ClientLevel world() {
                return Minecraft.getInstance().level;
            }

            @Override
            public LevelRenderer worldRenderer() {
                return event.getLevelRenderer();
            }

            @Override
            public Matrix4f projectionMatrix() {
                return event.getProjectionMatrix();
            }

            @Override
            public Matrix4f positionMatrix() {
                return event.getModelViewMatrix();
            }

            @Override
            public Frustum frustum() {
                return event.getFrustum();
            }

            @Override
            public ProfilerFiller profiler() {
                return Minecraft.getInstance().getProfiler();
            }
        };
    }
}
