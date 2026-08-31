package dev.amble.ait.module.planet.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.config.AITClientConfig;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.module.planet.core.item.SpacesuitItem;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;
import dev.amble.lib.platform.render.HudRenderEvents;

public class SpaceSuitOverlay implements HudRenderEvents.HudRender {

    @Override
    public void onHudRender(GuiGraphics drawContext, DeltaTracker v) {
        Minecraft mc = Minecraft.getInstance();
        PoseStack stack = drawContext.pose();

        if (mc.player == null || mc.level == null)
            return;

        Planet planet = PlanetRegistry.getInstance().get(mc.level);

        boolean isPlanetOrTARDIS = planet != null || TardisServerWorld.isTardisDimension(mc.level);

        if (!mc.options.getCameraType().isFirstPerson())
            return;

        Font textRenderer = mc.font;

        if (isPlanetOrTARDIS && mc.player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SpacesuitItem) {
            stack.pushPose();
            stack.scale(1.5f, 1.5f, 1.5f);

            drawContext.drawString(textRenderer,
                    TardisServerWorld.isTardisDimension(mc.level) ? Component.literal("??????").withStyle(ChatFormatting.OBFUSCATED) :
                            Component.literal(this.getTemperatureType(AITModClient.CONFIG, planet)),
                    0, 0, 0xFFFFFF);

            stack.popPose();
            stack.pushPose();
            stack.scale(1.5f, 1.5f, 1.5f);
            String oxygen = "" + Planet.getOxygenInTank(mc.player);
            drawContext.drawString(textRenderer, Component.literal(
                    oxygen.substring(0, 3) + "L / " + SpacesuitItem.MAX_OXYGEN + "L"), 0, 50, 0xFFFFFF);
            stack.popPose();
        }
    }

    public String getTemperatureType(AITClientConfig config, Planet planet) {
        return switch(config.temperatureType) {
            case CELSIUS -> ("" + planet.celsius()).substring(0, 5) + " °C";
            case FAHRENHEIT -> ("" + planet.fahrenheit()).substring(0, 5) + " °F";
            case KELVIN -> planet.kelvin() + " K";
        };
    }
}
