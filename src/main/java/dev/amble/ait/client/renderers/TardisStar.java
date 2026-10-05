package dev.amble.ait.client.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.decoration.TardisStarModel;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.lib.platform.render.WorldRenderContext;
import org.joml.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;

public class TardisStar {

    public static final ResourceLocation TARDIS_STAR_TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/environment/eye_of_harmony.png");
    private static final float HALF_SQRT_3 = (float) (Math.sqrt(3.0) / 2.0);
    private static ModelPart star;

    private static ModelPart star() {
        if (star == null)
            star = TardisStarModel.getTexturedModelData().bakeRoot();

        return star;
    }

    public static void render(WorldRenderContext context, Tardis tardis) {
        if (DependencyChecker.hasPortals() && !TardisServerWorld.isTardisDimension(context.world()))
            return;

        renderShine(context, tardis);

        renderStar(context, tardis);
        if (!tardis.isGrowth() && !tardis.alarm().isEnabled() && tardis.fuel().hasPower())
            RenderSystem.setShaderFogColor(1, 1, 1, 0);
    }

    public static void renderStar(WorldRenderContext context, Tardis tardis) {
        Camera camera = context.camera();
        MultiBufferSource provider = context.consumers();

        Vec3 cameraPos = camera.getPosition();
        if (tardis.getDesktop() == null) return;

        ProfilerFiller profiler = context.world().getProfiler();
        profiler.push("ait:tardis_star");
        // Two full model builds every frame, unconditionally, whenever the player is in an interior.
        profiler.incrementCounter("ait_model_build", 2);

        Vec3 targetPos = new Vec3(camera.getPosition().x(),
                context.world().getMinBuildHeight() - (tardis.isGrowth() ? 150 : 120), camera.getPosition().z());

        Vec3 diff = targetPos.subtract(cameraPos);

        PoseStack matrixStack = new PoseStack();
        matrixStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        matrixStack.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180.0F));
        matrixStack.translate(0, diff.y, 0);
        matrixStack.scale(40f, 40f, 40f);

        float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) + Minecraft.getInstance().player.tickCount;
        matrixStack.mulPose(Axis.YP
                .rotationDegrees(delta));

        star().render(matrixStack, provider.getBuffer(AITRenderLayers.tardisEmissiveCullZOffset(TARDIS_STAR_TEXTURE)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(0.5f, tardis.isGrowth() ? 0.1f : 1, tardis.isGrowth() ? 0.1f : 1, tardis.isGrowth() ? 0.1f : 1));

        matrixStack.scale(0.9f, 0.9f, 0.9f);
        star().render(matrixStack, provider.getBuffer(AITRenderLayers.tardisEmissiveCullZOffset(TARDIS_STAR_TEXTURE)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(1f, 1, tardis.isGrowth() ? 0.2f : 1, tardis.isGrowth() ? 0f : 1));

        profiler.pop();
    }

    public static void renderShine(WorldRenderContext context, Tardis tardis) {
        if (tardis.getExterior() == null) return;

        if (tardis.isGrowth())
            return;

        ProfilerFiller profiler = context.world().getProfiler();
        profiler.push("ait:tardis_star_shine");

        PoseStack matrixStack = new PoseStack();
        MultiBufferSource provider = context.consumers();

        Vec3 cameraPos = context.camera().getPosition();
        Vec3 targetPos = new Vec3(cameraPos.x(),
                context.world().getMinBuildHeight() - (tardis.isGrowth() ? 150 : 120), cameraPos.z());

        Vec3 diff = targetPos.subtract(cameraPos);

        float l = (Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) / 50120L);
        float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) + Minecraft.getInstance().player.tickCount;
        float sinFunc = (float) Math.sin((delta * (tardis.travel().speed() + 1)) * 0.2f + 0.2f);
        RandomSource random = RandomSource.create(432L);
        VertexConsumer vertexConsumer4 = provider.getBuffer(AITRenderLayers.lightning());
        matrixStack.pushPose();
        matrixStack.mulPose(Axis.XP.rotationDegrees(context.camera().getXRot()));
        matrixStack.mulPose(Axis.YP.rotationDegrees(context.camera().getYRot() + 180.0F));
        matrixStack.translate(0, diff.y, 0);
        if (!tardis.isRefueling())
            matrixStack.scale(8, 8, 8);
        else
            matrixStack.scale(8 + sinFunc, 8 + sinFunc, 8 + sinFunc);

        matrixStack.mulPose(Axis.YP
                .rotationDegrees((-delta * (tardis.travel().speed() + 1))));

        float m = Math.min(l > 0.8f ? (l - 0.8f) / 0.2f : 0.0f, 1.0f);

        for (int n = 0; n < 30; n++) {
            matrixStack.mulPose(Axis.XP.rotationDegrees((random.nextFloat() * 360.0f)));
            matrixStack.mulPose(Axis.YP.rotationDegrees((random.nextFloat() * 360.0f)));
            matrixStack.mulPose(Axis.ZP.rotationDegrees((random.nextFloat() * 360.0f)));
            matrixStack.mulPose(Axis.XP.rotationDegrees((random.nextFloat() * 360.0f)));
            matrixStack.mulPose(Axis.YP.rotationDegrees((random.nextFloat() * 360.0f)));
            matrixStack.mulPose(Axis.ZP.rotationDegrees((random.nextFloat() * 360.0f + l * 90.0f)));

            float o = random.nextFloat() * 10.0f + 10.0f + m * 10.0f;
            float p = random.nextFloat() * 0.5f + 1.0f + m * 2.0f;

            Matrix4f matrix4f = matrixStack.last().pose();
            int q = (int) (255f * (1.0f - m));

            TardisStar.putDeathLightSourceVertex(tardis, vertexConsumer4, matrix4f, q);
            TardisStar.putDeathLightNegativeXTerminalVertex(tardis, vertexConsumer4, matrix4f, o, p);
            TardisStar.putDeathLightPositiveXTerminalVertex(tardis, vertexConsumer4, matrix4f, o, p);
            TardisStar.putDeathLightSourceVertex(tardis, vertexConsumer4, matrix4f, q);
            TardisStar.putDeathLightPositiveXTerminalVertex(tardis, vertexConsumer4, matrix4f, o, p);
            TardisStar.putDeathLightPositiveZTerminalVertex(tardis, vertexConsumer4, matrix4f, o, p);
            TardisStar.putDeathLightSourceVertex(tardis, vertexConsumer4, matrix4f, q);
            TardisStar.putDeathLightPositiveZTerminalVertex(tardis, vertexConsumer4, matrix4f, o, p);
            TardisStar.putDeathLightNegativeXTerminalVertex(tardis, vertexConsumer4, matrix4f, o, p);
            TardisStar.putDeathLightSourceVertex(tardis, vertexConsumer4, matrix4f, q);
            TardisStar.putDeathLightPositiveZTerminalVertex(tardis, vertexConsumer4, matrix4f, o, p);
            TardisStar.putDeathLightPositiveZTerminalVertex(tardis, vertexConsumer4, matrix4f, o, p);
        }

        profiler.incrementCounter("ait_star_shine_vertices", 30 * 12);
        profiler.pop();
    }

    public static void putDeathLightSourceVertex(Tardis tardis, VertexConsumer buffer, Matrix4f matrix, int alpha) {
        buffer.addVertex(matrix, 0.0f, 0.0f, 0.0f).setColor(255, 255, 255, alpha);
    }

    public static void putDeathLightNegativeXTerminalVertex(Tardis tardis, VertexConsumer buffer, Matrix4f matrix,
                                                            float radius, float width) {
        buffer.addVertex(matrix, -HALF_SQRT_3 * width, radius, -0.5f * width)
                .setColor(255, tardis.isGrowth() ? 30 : 154, 0, 0);
    }

    public static void putDeathLightPositiveXTerminalVertex(Tardis tardis, VertexConsumer buffer, Matrix4f matrix,
                                                            float radius, float width) {
        buffer.addVertex(matrix, HALF_SQRT_3 * width, radius, -0.5f * width).setColor(255, tardis.isGrowth() ? 30 : 154, 0, 0)
                ;
    }

    public static void putDeathLightPositiveZTerminalVertex(Tardis tardis, VertexConsumer buffer, Matrix4f matrix,
                                                            float radius, float width) {
        buffer.addVertex(matrix, 0.0f, radius, width).setColor(255, tardis.isGrowth() ? 30 : 154, 0, 0);
    }
}
