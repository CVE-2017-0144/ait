package dev.amble.ait.client.models.consoles;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;

@SuppressWarnings("rawtypes")
public abstract class SimpleConsoleModel extends HierarchicalModel implements ConsoleModel {

    // Using a map actually is the worst way to do this - DO NOT REPLICATE. - Loqor
    protected static final Map<BlockEntity, Object2FloatMap<String>> ANIMATION_CACHE = new WeakHashMap<>();

    protected static final Minecraft client = Minecraft.getInstance();

    protected float getAngle(BlockEntity console, String key, float target, float delta) {
        Object2FloatMap<String> state = ANIMATION_CACHE.computeIfAbsent(console, k -> new Object2FloatOpenHashMap<>());
        float current = state.getOrDefault(key, 0f);
        float next = Mth.lerp(delta, current, target);
        state.put(key, next);
        return next;
    }

    protected float getLerpedDegrees(BlockEntity console, String key, float targetDegrees, float delta) {
        Object2FloatMap<String> state = ANIMATION_CACHE.computeIfAbsent(console, k -> new Object2FloatOpenHashMap<>());
        float currentRadians = state.getOrDefault(key, 0f);
        float currentDegrees = currentRadians * (180f / (float) Math.PI);
        float nextDegrees = Mth.rotLerp(delta, currentDegrees, targetDegrees);
        float nextRadians = nextDegrees * ((float) Math.PI / 180f);
        state.put(key, nextRadians);
        return nextRadians;
    }

    public SimpleConsoleModel() {
        this(RenderType::entityCutoutNoCull);
    }

    public SimpleConsoleModel(Function<ResourceLocation, RenderType> function) {
        super(function);
    }

    @Override
    public void animateBlockEntity(ConsoleBlockEntity console, TravelHandlerBase.State state, boolean hasPower) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        if (hasPower && AITModClient.CONFIG.animateConsole)
            this.animate(console.ANIM_STATE, this.getAnimationForState(state), client.getTimer().getGameTimeDeltaPartialTick(true) + console.getAge());
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ConsoleBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {;
        renderWithAnimations(linkableBlockEntity, tardis, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
    }

    // Overloaded method for compatibility with older code
    public void renderWithAnimations(ConsoleBlockEntity console, ClientTardis tardis, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        root.render(matrices, vertices, light, overlay, FastColor.ARGB32.colorFromFloat(pAlpha, red, green, blue));
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
    }

    public abstract AnimationDefinition getAnimationForState(TravelHandlerBase.State state);

    public void renderMonitorText(Tardis tardis, ConsoleBlockEntity entity, PoseStack matrices,
                                  MultiBufferSource vertexConsumers, int light, int overlay) {
        // no op
    }
}
