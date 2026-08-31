package dev.amble.ait.client.renderers;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import org.joml.Matrix4f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.data.ClientLandingManager;
import dev.amble.ait.data.landing.LandingPadRegion;
import dev.amble.ait.data.landing.LandingPadSpot;

public class LandingRegionRenderer {

    private static final int DARK_CYAN = FastColor.ARGB32.color(255, 0, 155, 155);

    private static final ResourceLocation AVAILABLE = AITMod.id("textures/marker/available.png");
    private static final ResourceLocation OCCUPIED = AITMod.id("textures/marker/occupied.png");

    private final Minecraft client;
    private ResourceLocation previous;

    public LandingRegionRenderer(Minecraft client) {
        this.client = client;
    }

    private static ResourceLocation getTexture(LandingPadSpot spot) {
        return spot.isOccupied() ? OCCUPIED : AVAILABLE;
    }

    public boolean shouldRender() {
        return SonicRendering.isPlayerHoldingScanningSonic() && ClientLandingManager.getInstance().getRegion(client.player.chunkPosition()) != null;
    }

    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        ProfilerFiller profiler = client.level.getProfiler();

        profiler.popPush("landing_pad");

        profiler.push("region");
        renderRegion();
        profiler.popPush("chunk");
        renderChunk(matrices, vertexConsumers, cameraX, cameraY, cameraZ);

        profiler.pop();
    }

    private void renderRegion() {
        ProfilerFiller profiler = client.level.getProfiler();

        profiler.push("get");
        LandingPadRegion region = ClientLandingManager.getInstance().getRegion(client.player.chunkPosition());

        if (region == null)
            return;

        profiler.popPush("iterate");
        List<LandingPadSpot> spots = region.getSpots();

        for (int i = 0; i < spots.size(); i++) {
            boolean isLast = i == spots.size() - 1;
            renderSpot(spots.get(i), isLast);
        }

        this.previous = null;

        profiler.pop();
    }

    private void renderSpot(LandingPadSpot spot, boolean forceRender) {
        ResourceLocation text = getTexture(spot);
        SonicRendering.renderFloorTexture(spot.getPos().offset(0, -1, 0), text, forceRender ? null : this.previous, true);

        forceRender = forceRender || !text.equals(this.previous);

        this.previous = forceRender ? null : text;
    }

    private void renderChunk(PoseStack matrices, MultiBufferSource vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        int k = DARK_CYAN;
        int j;
        Entity entity = this.client.gameRenderer.getMainCamera().getEntity();
        float f = (float)((double)this.client.level.getMinBuildHeight() - cameraY);
        float g = (float)((double)this.client.level.getMaxBuildHeight() - cameraY);
        ChunkPos chunkPos = entity.chunkPosition();
        float h = (float)((double)chunkPos.getMinBlockX() - cameraX);
        float i = (float)((double)chunkPos.getMinBlockZ() - cameraZ);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.debugLineStrip(1.0));
        Matrix4f matrix4f = matrices.last().pose();
        for (j = 2; j < 16; j += 2) {
            vertexConsumer.vertex(matrix4f, h + (float)j, f, i).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h + (float)j, f, i).color(k).endVertex();
            vertexConsumer.vertex(matrix4f, h + (float)j, g, i).color(k).endVertex();
            vertexConsumer.vertex(matrix4f, h + (float)j, g, i).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h + (float)j, f, i + 16.0f).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h + (float)j, f, i + 16.0f).color(k).endVertex();
            vertexConsumer.vertex(matrix4f, h + (float)j, g, i + 16.0f).color(k).endVertex();
            vertexConsumer.vertex(matrix4f, h + (float)j, g, i + 16.0f).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
        }
        for (j = 2; j < 16; j += 2) {
            vertexConsumer.vertex(matrix4f, h, f, i + (float)j).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h, f, i + (float)j).color(k).endVertex();
            vertexConsumer.vertex(matrix4f, h, g, i + (float)j).color(k).endVertex();
            vertexConsumer.vertex(matrix4f, h, g, i + (float)j).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h + 16.0f, f, i + (float)j).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h + 16.0f, f, i + (float)j).color(k).endVertex();
            vertexConsumer.vertex(matrix4f, h + 16.0f, g, i + (float)j).color(k).endVertex();
            vertexConsumer.vertex(matrix4f, h + 16.0f, g, i + (float)j).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
        }
        for (j = this.client.level.getMinBuildHeight(); j <= this.client.level.getMaxBuildHeight(); j += 2) {
            float l = (float)((double)j - cameraY);
            int m = DARK_CYAN;
            vertexConsumer.vertex(matrix4f, h, l, i).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h, l, i).color(m).endVertex();
            vertexConsumer.vertex(matrix4f, h, l, i + 16.0f).color(m).endVertex();
            vertexConsumer.vertex(matrix4f, h + 16.0f, l, i + 16.0f).color(m).endVertex();
            vertexConsumer.vertex(matrix4f, h + 16.0f, l, i).color(m).endVertex();
            vertexConsumer.vertex(matrix4f, h, l, i).color(m).endVertex();
            vertexConsumer.vertex(matrix4f, h, l, i).color(1.0f, 1.0f, 0.0f, 0.0f).endVertex();
        }
        vertexConsumer = vertexConsumers.getBuffer(RenderType.debugLineStrip(2.0));
        for (j = 0; j <= 16; j += 16) {
            for (int k2 = 0; k2 <= 16; k2 += 16) {
                vertexConsumer.vertex(matrix4f, h + (float)j, f, i + (float)k2).color(0.25f, 0.25f, 1.0f, 0.0f).endVertex();
                vertexConsumer.vertex(matrix4f, h + (float)j, f, i + (float)k2).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
                vertexConsumer.vertex(matrix4f, h + (float)j, g, i + (float)k2).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
                vertexConsumer.vertex(matrix4f, h + (float)j, g, i + (float)k2).color(0.25f, 0.25f, 1.0f, 0.0f).endVertex();
            }
        }
        for (j = this.client.level.getMinBuildHeight(); j <= this.client.level.getMaxBuildHeight(); j += 16) {
            float l = (float)((double)j - cameraY);
            vertexConsumer.vertex(matrix4f, h, l, i).color(0.25f, 0.25f, 1.0f, 0.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h, l, i).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h, l, i + 16.0f).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h + 16.0f, l, i + 16.0f).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h + 16.0f, l, i).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h, l, i).color(0.25f, 0.25f, 1.0f, 1.0f).endVertex();
            vertexConsumer.vertex(matrix4f, h, l, i).color(0.25f, 0.25f, 1.0f, 0.0f).endVertex();
        }
    }

    public void tryRender(PoseStack matrices, MultiBufferSource vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        if (!this.shouldRender())
            return;

        this.render(matrices, vertexConsumers, cameraX, cameraY, cameraZ);
    }

    private static LandingRegionRenderer INSTANCE;

    public static LandingRegionRenderer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new LandingRegionRenderer(Minecraft.getInstance());

        return INSTANCE;
    }
}
