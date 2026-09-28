package dev.amble.ait.client.renderers.decoration;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.core.blockentities.PottedSonicScrewdriverBlockEntity;
public class PottedSonicScrewdriverRenderer implements BlockEntityRenderer<PottedSonicScrewdriverBlockEntity> {
    private static final float SCALE = 0.5f;
    private static final float BASE_Y = 4f / 16f;
    private static final float LIFT = 0.531f;
    private static final float[][] SLOTS = {{ 0.00f,  0.00f,  10f, 0f,   0f },
            { -0.07f, -0.04f, 60f, 1f, -20f }, { 0.07f,  0.04f, -40f, 1f,  20f }, {0.03f,  0.07f, 150f, 0f, -20f }, { -0.05f, 0.06f, -110f, 0f,  20f }, { 0.05f, -0.06f, 100f, 1f, -15f }};

    public PottedSonicScrewdriverRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(PottedSonicScrewdriverBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        List<ItemStack> sonics = entity.getSonics();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        for (int i = 0; i < sonics.size(); i++) {
            ItemStack stack = sonics.get(i);
            float[] slot = SLOTS[Math.min(i, SLOTS.length - 1)];
            matrices.pushPose();
            matrices.translate(0.5f + slot[0], BASE_Y, 0.5f + slot[1]);
            matrices.mulPose(Axis.YP.rotationDegrees(slot[2]));
            if (slot[4] != 0f) {
                if (slot[3] != 0f)
                    matrices.mulPose(Axis.ZP.rotationDegrees(slot[4]));
                else
                    matrices.mulPose(Axis.XP.rotationDegrees(slot[4]));
            }
            matrices.scale(SCALE, SCALE, SCALE);
            matrices.translate(0f, LIFT, 0f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.NONE, light, overlay, matrices, vertexConsumers, entity.getLevel(), 0);
            matrices.popPose();
        }
    }

    @Override
    public AABB getRenderBoundingBox(PottedSonicScrewdriverBlockEntity blockEntity) {
        return AABB.INFINITE;
    }
}
