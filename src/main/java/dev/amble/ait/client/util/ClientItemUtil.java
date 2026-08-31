package dev.amble.ait.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

public class ClientItemUtil {

    private static final RandomSource RANDOM = RandomSource.create(42L);
    private static final Direction[] DIRECTIONS = Direction.values();

    public static void renderBakedItemModel(BakedModel model, int color, int light, int overlay, PoseStack matrices,
            MultiBufferSource provider) {
        renderBakedItemModel(model, color, light, overlay, matrices, ItemRenderer.getFoilBufferDirect(provider,
                Sheets.translucentCullBlockSheet(), true, false));
    }

    public static void renderBakedItemModel(BakedModel model, int color, int light, int overlay, PoseStack matrices,
            VertexConsumer vertices) {
        for (Direction direction : DIRECTIONS) {
            renderBakedItemQuads(matrices, vertices, model.getQuads(null, direction, RANDOM), color, light, overlay);
        }

        renderBakedItemQuads(matrices, vertices, model.getQuads(null, null, RANDOM), color, light, overlay);
    }

    private static void renderBakedItemQuads(PoseStack matrices, VertexConsumer vertices, List<BakedQuad> quads,
            int color, int light, int overlay) {
        PoseStack.Pose entry = matrices.last();

        for (BakedQuad quad : quads) {
            int i = quad.isTinted() ? color : -1;

            float f = (float) (i >> 16 & 255) / 255.0F;
            float g = (float) (i >> 8 & 255) / 255.0F;
            float h = (float) (i & 255) / 255.0F;
            vertices.putBulkData(entry, quad, f, g, h, light, overlay);
        }
    }
}
