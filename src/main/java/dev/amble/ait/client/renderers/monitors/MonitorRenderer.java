package dev.amble.ait.client.renderers.monitors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.models.monitors.CRTMonitorModel;
import dev.amble.ait.core.blockentities.MonitorBlockEntity;
import dev.amble.ait.core.blocks.MonitorBlock;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.handler.FuelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.core.util.MonitorStateUtil;
import dev.amble.ait.core.util.MonitorUtil;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;

public class MonitorRenderer<T extends MonitorBlockEntity> implements BlockEntityRenderer<T> {


    private static final ResourceLocation MONITOR_TEXTURE_DEFAULT = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID, "textures/blockentities/monitors/crt_monitor.png");
    private static final ResourceLocation MONITOR_TEXTURE_BLAZE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID, "textures/blockentities/monitors/crt_monitor/blaze.png");
    public static final ResourceLocation EMISSIVE_MONITOR_TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/monitors/crt_monitor_emission.png"));
    private final Font textRenderer = Minecraft.getInstance().font;
    private final CRTMonitorModel crtMonitorModel;

    public MonitorRenderer(BlockEntityRendererProvider.Context ctx) {
        this.crtMonitorModel = new CRTMonitorModel(CRTMonitorModel.getTexturedModelData().bakeRoot());
    }


    @Override
    public void render(MonitorBlockEntity entity, float tickDelta, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light, int overlay) {

        MonitorStateUtil state = entity.getBlockState().getValue(MonitorBlock.TEXTURE);

        ResourceLocation texture = switch (state) {
            case BLAZE -> MONITOR_TEXTURE_BLAZE;
            default -> MONITOR_TEXTURE_DEFAULT;
        };

        BlockState blockState = entity.getBlockState();

        int k = blockState.getValue(SkullBlock.ROTATION);
        float h = RotationSegment.convertToDegrees(k);

        matrices.pushPose();
        matrices.translate(0.5f, 1.5f, 0.5f);
        matrices.mulPose(Axis.YN.rotationDegrees(h));
        matrices.mulPose(Axis.XP.rotationDegrees(180));

        this.crtMonitorModel.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(texture)), light, overlay, 0xFFFFFFFF);
        if (state == MonitorStateUtil.DEFAULT) {
            this.crtMonitorModel.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucentEmissive(EMISSIVE_MONITOR_TEXTURE)), 0xF000F00, overlay, 0xFFFFFFFF);
        }
        matrices.popPose();

        if (!entity.isLinked())
            return;

        Tardis tardis = entity.tardis().get();

        if (!tardis.fuel().hasPower())
            return;

        if (!AITModClient.CONFIG.showCRTMonitorText)
            return;

        matrices.pushPose();
        matrices.translate(0.5, 0.75, 0.5);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.mulPose(Axis.YN.rotationDegrees(180 - h));
        matrices.scale(0.005f, 0.005f, 0.005f);
        matrices.translate(-50f, 0, -77);

        TravelHandler travel = tardis.travel();
        CachedDirectedGlobalPos abpp = travel.isLanded() || travel.getState() == TravelHandlerBase.State.MAT
                ? travel.position()
                : travel.getProgress();

        BlockPos abppPos = abpp.getPos();

        CachedDirectedGlobalPos abpd = tardis.travel().destination();
        BlockPos abpdPos = abpd.getPos();

        String positionPosText = abppPos.getX() + ", " + abppPos.getY() + ", " + abppPos.getZ();
        Component positionDimensionText = Component.nullToEmpty(MonitorUtil.truncateDimensionName(WorldUtil.worldText(abpp.getDimension()).getString(), 20));

        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty("\uD83D\uDCCD").getVisualOrderText(), 4, 4, 0x00EEFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty(positionPosText).getVisualOrderText(), 12, 4, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(positionDimensionText.getVisualOrderText(), 12, 12, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(WorldUtil.rot2Text(abpp.getRotation()).getVisualOrderText(), 12, 20, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);

        String destinationPosText = abpdPos.getX() + ", " + abpdPos.getY() + ", " + abpdPos.getZ();
        Component destinationDimensionText = Component.nullToEmpty(MonitorUtil.truncateDimensionName(WorldUtil.worldText(abpd.getDimension(), false).getString(), 20));


        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty("\uD83E\uDC97").getVisualOrderText(), 4, 40, 0xFF0000, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty(destinationPosText).getVisualOrderText(), 12, 40, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(destinationDimensionText.getVisualOrderText(), 12, 48, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        this.textRenderer.drawInBatch8xOutline(WorldUtil.rot2Text(abpd.getRotation()).getVisualOrderText(), 12, 56, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);

        String fuelText = Math.round((tardis.getFuel() / FuelHandler.TARDIS_MAX_FUEL) * 100) + "%";
        this.textRenderer.drawInBatch8xOutline(Component.translatable("ait.monitor.fuel_with_text", fuelText).getVisualOrderText(), 12, 78, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);

        String flightTimeText = tardis.travel().getState() == TravelHandlerBase.State.LANDED
                ? "0%"
                : tardis.travel().getDurationAsPercentage() + "%";
        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty("⏳: " + flightTimeText).getVisualOrderText(), 12, 88, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);

        String name = tardis.stats().getName();
        this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty(name).getVisualOrderText(),  50-(this.textRenderer.width(name) / 2), 102,
                0xFFFFFF, 0x000000, matrices.last().pose(), vertexConsumers, light);

        if (tardis.alarm().isEnabled())
            this.textRenderer.drawInBatch8xOutline(Component.nullToEmpty("⚠").getVisualOrderText(), 84, 0, 0xFE0000, 0x000000,
                    matrices.last().pose(), vertexConsumers, 0xF000F0);

        matrices.popPose();
    }
}