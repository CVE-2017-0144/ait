package dev.amble.ait.client.renderers.machines;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.core.blockentities.ArtronCollectorBlockEntity;
import dev.amble.lib.client.bedrock.BedrockEntityModel;
import dev.amble.lib.client.bedrock.BedrockModelReference;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ArtronCollectorRenderer<T extends ArtronCollectorBlockEntity> implements BlockEntityRenderer<T> {

    private static final int FRAME_COUNT = 8;
    private static final float TICKS_PER_FRAME = 3.0F;

    protected BedrockEntityModel<?> model;

    public ArtronCollectorRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (entity.getLevel() == null) return;

        if (this.model == null) {
            this.refreshModel(entity);
        }

        BlockState blockState = entity.getBlockState();
        float f = blockState.getValue(HorizontalDirectionalBlock.FACING).toYRot();

        matrices.pushPose();

        matrices.translate(0.5D, 0, 0.5D);
        matrices.mulPose(Axis.XP.rotationDegrees(180F));
        matrices.mulPose(Axis.YN.rotationDegrees(f));

        ModelPart batteryLevels = this.model.getPart().getChild("main").getChild("Meter");

        if (batteryLevels != null) {
            batteryLevels.getChild("Light_1").visible = entity.getCurrentFuel() > 500;
            batteryLevels.getChild("Light_2").visible = entity.getCurrentFuel() > 1000;
            batteryLevels.getChild("Light_3").visible = entity.getCurrentFuel() > 1250;
            batteryLevels.getChild("Light_4").visible = entity.getCurrentFuel() >= 1500;
        }

        this.model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(entity.getTexture())), light, overlay, 0xFFFFFFFF);

        ResourceLocation emission = entity.getEmissionTexture();

        if (emission == null) {
            emission = entity.getTexture();
        }

        ResourceLocation animatedTexture = getAnimatedTexture(entity);

        if (animatedTexture == null) {
            animatedTexture = entity.getTexture();
        }

        VertexConsumer consumer = vertexConsumers.getBuffer(RenderType.entityCutoutNoCullZOffset(emission));

        float alpha = 1f;

        if (entity.getCurrentFuel() > 0) {
            VertexConsumer emissive = vertexConsumers.getBuffer(RenderType.eyes(animatedTexture));

            long worldTime = entity.getLevel().getGameTime();
            float t = (worldTime + tickDelta) / TICKS_PER_FRAME;
            int frame = Math.floorMod((int) Math.floor(t), FRAME_COUNT);
            int nextFrame = (frame + 1) % FRAME_COUNT;
            alpha = t - Mth.floor(t);

            consumer = new FrameOffsetVertexConsumer(emissive, nextFrame, FRAME_COUNT);
        }

        this.model.renderToBuffer(matrices, consumer, LightTexture.FULL_BRIGHT, overlay, FastColor.ARGB32.colorFromFloat(alpha, 1.0f, 1.0f, 1.0f));

        matrices.popPose();
    }

    protected BedrockEntityModel<?> refreshModel(T entity) {
        BedrockModelReference ref = entity.getModel();
        if (ref == null) {
            throw new IllegalStateException("BlockEntity " + entity + " does not have a BedrockModelReference");
        }
        return this.model = new BedrockEntityModel<>(ref.get().orElseThrow(() ->
                new IllegalStateException("BedrockModel " + ref.id() + " not found for block entity " + entity)));
    }

    protected ResourceLocation getAnimatedTexture(T entity) {
        return entity.getTexture().withPath(s -> s.replace(".png", "_anim.png"));
    }

    private record FrameOffsetVertexConsumer(VertexConsumer delegate, int frame, int frameCount)
            implements VertexConsumer {

        @Override
        public VertexConsumer setUv(float u, float v) {
            this.delegate.setUv(u, (v + this.frame) / this.frameCount);
            return this;
        }

        @Override
        public VertexConsumer addVertex(float x, float y, float z) {
            this.delegate.addVertex(x, y, z);
            return this;
        }

        @Override
        public VertexConsumer setColor(int red, int green, int blue, int alpha) {
            this.delegate.setColor(red, green, blue, alpha);
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

    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        return AABB.INFINITE;
    }
}