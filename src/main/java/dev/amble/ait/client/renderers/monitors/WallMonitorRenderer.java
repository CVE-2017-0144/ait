package dev.amble.ait.client.renderers.monitors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.decoration.PlaqueModel;
import dev.amble.ait.core.blockentities.WallMonitorBlockEntity;
import dev.amble.ait.core.blocks.PlaqueBlock;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.impl.DirectionControl;
import dev.amble.ait.core.tardis.handler.FuelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class WallMonitorRenderer<T extends WallMonitorBlockEntity> implements BlockEntityRenderer<T> {

    public static final ResourceLocation PLAQUE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/monitors/wall_monitor.png"));
    private final Font textRenderer = Minecraft.getInstance().font;
    private final PlaqueModel plaqueModel;

    public WallMonitorRenderer(BlockEntityRendererProvider.Context ctx) {
        this.plaqueModel = new PlaqueModel(PlaqueModel.getTexturedModelData().bakeRoot());
    }

    private String truncateDimensionName(String name, int maxLength) {
        if (name.length() > maxLength) {
            return name.substring(0, maxLength) + "...";
        }
        return name;
    }

    @Override
    public void render(WallMonitorBlockEntity entity, float tickDelta, PoseStack matrices,
            MultiBufferSource vertexConsumers, int light, int overlay) {
        BlockState blockState = entity.getBlockState();

        Direction k = blockState.getValue(PlaqueBlock.FACING);

        matrices.pushPose();
        matrices.translate(0.5f, 1.5f, 0.5f);
        matrices.mulPose(Axis.YN.rotationDegrees(k.toYRot()));
        matrices.mulPose(Axis.XP.rotationDegrees(180));

        this.plaqueModel.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(PLAQUE_TEXTURE)), light, overlay, 0xFFFFFFFF);
        matrices.popPose();

        if (!entity.isLinked())
            return;

        Tardis tardis = entity.tardis().get();

        if (!tardis.fuel().hasPower())
            return;

        matrices.pushPose();
        matrices.translate(0.5, 0.75, 0.5);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.mulPose(Axis.YP.rotationDegrees(k.toYRot()));
        matrices.scale(0.01f, 0.01f, 0.01f);
        float xVal = 0f;
        matrices.translate(xVal, -35f, 35f);

        TravelHandler travel = tardis.travel();
        CachedDirectedGlobalPos abpp = travel.isLanded() || travel.getState() == TravelHandlerBase.State.MAT
                ? travel.position()
                : travel.getProgress();

        BlockPos abppPos = abpp.getPos();

        CachedDirectedGlobalPos abpd = tardis.travel().destination();
        BlockPos abpdPos = abpd.getPos();

        String positionPosText = abppPos.getX() + ", " + abppPos.getY() + ", " + abppPos.getZ();
        Component positionDimensionText = Component.nullToEmpty(truncateDimensionName(WorldUtil.worldText(abpp.getDimension()).getString(), 16));

        String fuelText = Math.round((tardis.getFuel() / FuelHandler.TARDIS_MAX_FUEL) * 100) + "%";

        String destinationPosText = abpdPos.getX() + ", " + abpdPos.getY() + ", " + abpdPos.getZ();
        Component destinationDimensionText = Component.nullToEmpty(truncateDimensionName(WorldUtil.worldText(abpd.getDimension(), false).getString(), 16));


        float v = -20f;

        String arrow = DirectionControl.rotationForArrow(abpp.getRotation());
        String arrow2 = DirectionControl.rotationForArrow(abpd.getRotation());

        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty(positionPosText).getVisualOrderText(),
                (v - xVal) - ((float) this.textRenderer.width(positionPosText) / 2), 35, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(positionDimensionText.getVisualOrderText(),
                (v - xVal) - ((float) this.textRenderer.width(positionDimensionText) / 2), 46, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty(arrow).getVisualOrderText(),
                (18 - xVal) - ((float) this.textRenderer.width(arrow) / 2), 42, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty("----------").getVisualOrderText(),
                (v - xVal) - ((float) this.textRenderer.width("----------") / 2), 55, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty(destinationPosText).getVisualOrderText(),
                (v - xVal) - ((float) this.textRenderer.width(destinationPosText) / 2), 67, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(destinationDimensionText.getVisualOrderText(),
                (v - xVal) - ((float) this.textRenderer.width(positionDimensionText) / 2), 78, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty(arrow2).getVisualOrderText(),
                (18 - xVal) - ((float) this.textRenderer.width(arrow2) / 2), 75, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);

        Component au = Component.translatable("ait.monitor.fuel");
        this.textRenderer.drawInBatch8xOutline(au.getVisualOrderText(),
                (53 - xVal) - ((float) this.textRenderer.width(au) / 2), 40, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty(fuelText).getVisualOrderText(),
                (53 - xVal) - ((float) this.textRenderer.width(fuelText) / 2), 48, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        String flightTimeText = travel.getState() == TravelHandlerBase.State.LANDED
                ? "0%"
                : tardis.travel().getDurationAsPercentage() + "%";

        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty("⏳").getVisualOrderText(),
                (53 - xVal) - ((float) this.textRenderer.width("⏳") / 2), 60, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty(flightTimeText).getVisualOrderText(),
                (53 - xVal) - ((float) this.textRenderer.width(flightTimeText) / 2), 68, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);

        matrices.popPose();
    }
}
