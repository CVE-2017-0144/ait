package dev.amble.ait.client.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import dev.amble.ait.core.entities.FlightTardisEntity;
import dev.amble.ait.core.tardis.Tardis;

public class RWFOverlay implements HudRenderCallback {
    private static final int ALPHA_GRAY = FastColor.ARGB32.color(125, 255, 255, 255);
    @Override
    public void onHudRender(GuiGraphics drawContext, DeltaTracker tickDelta) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.level == null)
            return;

        if (mc.player.isPassenger() &&   mc.player.getVehicle() instanceof FlightTardisEntity entity) {
            if (!entity.isLinked()) return;
            Tardis tardis = entity.tardis().get();
            Yaw.render(drawContext, mc.player);
            Position.render(drawContext, mc.player, mc);
            Position.Y.render(drawContext, mc.player, mc);
            Speed.render(drawContext, mc.player, mc);
            //this.renderOverlay(drawContext, AITMod.id("textures/gui/tardis/rwf_gui.png"));
        }
    }

    private void renderOverlay(GuiGraphics context, ResourceLocation texture) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        context.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        context.blit(texture, (context.guiWidth() / 2) - 8,
                (context.guiHeight() / 2) - 8, 0, 0.0F, 0.0F, 16, 16, 16, 16);
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        context.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static int width() {
        return Minecraft.getInstance().getWindow().getGuiScaledWidth();
    }
    private static int height() {
        return Minecraft.getInstance().getWindow().getGuiScaledHeight() - 8;
    }


    private static void renderIncrementedLine(GuiGraphics context, int x, int gap) {
        context.vLine(x, 0, height(), ALPHA_GRAY);

        // horizontal line every gap
        /*
        for (int i = 0; i < getScreenHeight(); i += gap) {
            context.drawHorizontalLine(x, x + 3, i, ALPHA_GRAY);
        }*/
    }

    private static class Yaw {
        private static void render(GuiGraphics context, AbstractClientPlayer player) {
            context.hLine(60, width() - 60, 20, ALPHA_GRAY);
            context.vLine(60, 16, 24, CommonColors.WHITE);
            context.vLine(width() - 60, 16, 24, CommonColors.WHITE);

            float current = player.getYRot();

            line(context, -180, current, false);
            line(context, -90, current, false);
            line(context, 0, current, false);
            line(context, 90, current, false);
            line(context, 180, current, false);

            line(context, player.getYRot(), 0, true);
        }

        private static void line(GuiGraphics context, float yaw, float current, boolean isPrimary) {
            int x = isPrimary ? width() / 2 : position(yaw, current);

            int middle = width() / 2;
            if (!isPrimary && (middle - 10 <= x && x <= middle + 10)) return;

            int color = isPrimary ? CommonColors.WHITE : ALPHA_GRAY;

            context.vLine(x, 16, 24, color);
            context.drawCenteredString(Minecraft.getInstance().font, Math.round(Mth.wrapDegrees(yaw)) + "", x, 26, color);

            Direction dir = Direction.fromYRot(yaw);
            context.drawCenteredString(Minecraft.getInstance().font, dir.getSerializedName().toUpperCase().charAt(0) + "", x, 35, color);
        }
        private static int position(float yaw, float current) {
            return (int) ((width() - 120) * (((Mth.wrapDegrees(yaw - current) + 180) / 360))) + 60;
        }
    }
    private static class Pitch {
        private static void render(GuiGraphics context, AbstractClientPlayer player) {
            renderIncrementedLine(context, 10, 5);

            line(context, 0, false);
            line(context, 45, false);
            line(context, -45, false);

            line(context, player.getXRot(), true);
        }

        private static void line(GuiGraphics context, float pitch, boolean isPrimary) {
            int y = position(pitch);
            int color = isPrimary ? CommonColors.WHITE : ALPHA_GRAY;

            context.hLine(10, 15, y + 3, color);
            context.drawCenteredString(Minecraft.getInstance().font, Math.round(pitch) + "", 27, y, color);
        }

        private static int position(float pitch) {
            return (int) (height() * (((pitch + 90) / 180)));
        }
    }
    private static class Position {
        private static class Y {
            private static void render(GuiGraphics context, AbstractClientPlayer player, Minecraft client) {
                int bottom = Math.abs(player.level().getMinBuildHeight());
                int range = player.level().getMaxBuildHeight() + (Math.min(bottom, 0));

                renderIncrementedLine(context, width() - 10, 8);

                line(context, range / 2d, bottom, range, false);
                line(context, range / 4d, bottom, range, false);
                line(context, 0d, 128, range, false);

                line(context, player.getY(), bottom, range, true);
            }

            private static void line(GuiGraphics context, double y, int bottom, int range, boolean isPrimary) {
                int yPosition = position(y, bottom, range);
                int color = isPrimary ? CommonColors.WHITE : ALPHA_GRAY;

                context.hLine(width() - 15, width() - 10, yPosition + 3, color);

                Font renderer = Minecraft.getInstance().font;
                String text = Math.round(y) + "";
                context.drawString(renderer, text, width() - renderer.width(text) - 17 , yPosition, color);
            }

            private static int position(double y, int bottom, int range) {
                return (int) (height() * (1f - ((float) (y + bottom) / range)));
            }
        }

        private static void render(GuiGraphics context, AbstractClientPlayer player, Minecraft client) {
            String i = Math.round(player.getX()) + ", " + Math.round(player.getZ());
            context.fill(width() - 62 - client.font.width(i), 48, width() - 58, 61, ALPHA_GRAY);
            context.drawString(client.font, i, width() - 60 - client.font.width(i), 50, 0xFFFFFF);
        }
    }
    private static class Speed {
        private static void render(GuiGraphics context, AbstractClientPlayer player, Minecraft client) {
            double deltaX = player.getX() - player.xo;
            double deltaZ = player.getZ() - player.zo;
            double deltaY = player.getY() - player.yo;
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
            String s = Math.round(distance * 20) + " m/s";

            context.fill(58, 49, 62 + client.font.width(s), 60, ALPHA_GRAY);
            context.drawString(client.font, s, 60, 50, 0xFFFFFF);
        }
    }
}
