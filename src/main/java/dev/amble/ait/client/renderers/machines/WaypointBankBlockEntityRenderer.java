package dev.amble.ait.client.renderers.machines;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.util.ClientItemUtil;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.blockentities.WaypointBankBlockEntity;
import dev.amble.ait.core.blocks.WaypointBankBlock;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.lib.data.DirectedGlobalPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.AABB;
public class WaypointBankBlockEntityRenderer<T extends WaypointBankBlockEntity> implements BlockEntityRenderer<T> {

    private static final Font textRenderer = Minecraft.getInstance().font;
    private static final String SEPARATOR = "------------";

    private static final ModelResourceLocation WAYPOINT = ModelResourceLocation.inventory(BuiltInRegistries.ITEM.getKey(AITItems.WAYPOINT_CARTRIDGE));

    public WaypointBankBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertices, int light,
            int overlay) {
        if (!entity.isLinked())
            return;

        Tardis tardis = entity.tardis().get();

        if (!tardis.fuel().hasPower())
            return;

        float facing = entity.getBlockState().getValue(WaypointBankBlock.FACING).toYRot();

        WaypointBankBlockEntity.WaypointData[] waypoints = entity.getWaypoints();
        int selectedIndex = entity.getSelected();

        if (selectedIndex != -1)
            renderMonitor(matrices, vertices, facing, waypoints, selectedIndex);

        BakedModel cartridgeModel = WaypointBankBlockEntityRenderer.cartridgeModel();
        int i = 0;

        matrices.pushPose();

        matrices.translate(0.5f, 2, 0.5f);
        matrices.mulPose(Axis.YP.rotationDegrees(-facing));
        matrices.mulPose(Axis.XP.rotationDegrees(90f));
        matrices.translate(-0.0625f * 11.5, 0.0625 * 5, 0.0625f * 5.5);

        matrices.pushPose();
        for (; i < WaypointBankBlock.MAX_COUNT / 2; i++) {
            renderCartridge(matrices, vertices, light, overlay, cartridgeModel, waypoints, selectedIndex, i);
        }
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.0625f * 7, 0, 0);

        for (; i < WaypointBankBlock.MAX_COUNT; i++) {
            renderCartridge(matrices, vertices, light, overlay, cartridgeModel, waypoints, selectedIndex, i);
        }

        matrices.popPose();

        matrices.popPose();
    }

    private static void renderLabel(PoseStack matrices, MultiBufferSource vertexConsumers, String text, int y) {
        textRenderer.drawInBatch(text, 0 - ((float) textRenderer.width(text) / 2), y, 0x00F0FF, false,
                matrices.last().pose(), vertexConsumers, Font.DisplayMode.NORMAL, 0xF000F0,
                0xF000F0);
    }

    private static void renderLabel(PoseStack matrices, MultiBufferSource vertexConsumers, FormattedCharSequence text,
            int y) {
        textRenderer.drawInBatch(text, 0 - ((float) textRenderer.width(text) / 2), y, 0x00F0FF, false,
                matrices.last().pose(), vertexConsumers, Font.DisplayMode.NORMAL, 0xF000F0,
                0xF000F0);
    }

    private static void renderMonitor(PoseStack matrices, MultiBufferSource vertices, float facing,
            WaypointBankBlockEntity.WaypointData[] data, int selectedIndex) {
        WaypointBankBlockEntity.WaypointData selected = data[selectedIndex];

        if (selected == null)
            return;

        DirectedGlobalPos abpd = selected.pos();

        int rotation = abpd.getRotation();
        BlockPos abpdPos = abpd.getPos();

        String destPos = abpdPos.getX() + ", " + abpdPos.getY() + ", " + abpdPos.getZ();
        String destDim = WorldUtil.worldText(abpd.getDimension(), false).getString();

        matrices.pushPose();
        matrices.translate(0.5, 0.75, 0.5);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.mulPose(Axis.YP.rotationDegrees(facing));

        matrices.scale(0.01f, 0.01f, 0.01f);
        matrices.translate(0f, -142f, -51f);

        renderLabel(matrices, vertices, selected.name(), 35);

        renderLabel(matrices, vertices, SEPARATOR, 46);
        renderLabel(matrices, vertices, destPos, 55);

        renderLabel(matrices, vertices, WorldUtil.rot2Text(rotation).getVisualOrderText(), 67);
        renderLabel(matrices, vertices, destDim, 78);

        String which = (selectedIndex + 1) + "/" + WaypointBankBlock.MAX_COUNT;
        renderLabel(matrices, vertices, which, 96);
        matrices.popPose();
    }

    private static void renderCartridge(PoseStack matrices, MultiBufferSource vertices, int light, int overlay,
            BakedModel model, WaypointBankBlockEntity.WaypointData[] data, int selected, int current) {
        matrices.translate(0, 0, 0.0625f * 2);
        WaypointBankBlockEntity.WaypointData waypoint = data[current];

        if (waypoint == null)
            return;

        if (current == selected) {
            matrices.pushPose();
            matrices.translate(0, 0.0625f * 2, 0);
            ClientItemUtil.renderBakedItemModel(model, waypoint.color(), 0x0F000F0, overlay, matrices, vertices);
            matrices.popPose();
        } else {
            ClientItemUtil.renderBakedItemModel(model, waypoint.color(), light, overlay, matrices, vertices);
        }
    }

    private static BakedModel cartridgeModel() {
        return Minecraft.getInstance().getModelManager().getModel(WAYPOINT);
    }

    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        return AABB.INFINITE;
    }
}
