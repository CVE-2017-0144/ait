package dev.amble.ait.client.util;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;
import dev.amble.ait.module.planet.core.space.system.Space;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FoggyUtils {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void overrideFog() {
        Tardis tardis = ClientTardisUtil.getCurrentTardis();

        if (mc.player != null && !mc.player.isSpectator() && mc.level != null && mc.level.dimension().equals(AITDimensions.SPACE)) {
            // EW WTF IS THAT
            for (Planet planet : Space.getInstance().getPlanets()) {
                if (planet != PlanetRegistry.getInstance().get(mc.level) && planet.render().position().distanceTo(mc.player.position()) < planet.render().radius()) {
                    PoseStack stack = new PoseStack();
                    stack.pushPose();
                    stack.translate(0, 0, -2);
                    stack.scale(2000, 20000, 1);
                    for (int i = 0; i < 7; i++) {
                        mc.getItemRenderer().renderStatic(new ItemStack(Items.WHITE_STAINED_GLASS_PANE),
                                ItemDisplayContext.GROUND, 0xf, OverlayTexture.NO_OVERLAY, stack, mc.renderBuffers().bufferSource(), mc.level, 0);
                    }
                    stack.popPose();
                    RenderSystem
                            .setShaderFogStart(Mth.lerpInt(mc.getFrameTime() / 100f, 1, 1));
                    RenderSystem.setShaderFogEnd(Mth.lerpInt(mc.getFrameTime() / 100f, 1, 1));
                    RenderSystem.setShaderFogShape(FogShape.SPHERE);
                    RenderSystem.setShaderFogColor(planet.render().color().x(),
                            planet.render().color().y(),
                            planet.render().color().z(), 1f);
                }
            }
        }

        if (tardis == null)
            return;

        if (!tardis.isGrowth()
                && ClientTardisUtil.getAlarmDelta() != ClientTardisUtil.MAX_ALARM_DELTA_TICKS) {
            RenderSystem.setShaderFogStart(Mth.lerpInt(ClientTardisUtil.getAlarmDeltaForLerp(), -8, 10));
            RenderSystem.setShaderFogEnd(Mth.lerpInt(ClientTardisUtil.getAlarmDeltaForLerp(), 11, 32));
            RenderSystem.setShaderFogShape(FogShape.SPHERE);
            RenderSystem.setShaderFogColor(0.5f, 0, 0, 0.5f);
            mc.gameRenderer.getMainCamera().getFluidInCamera();
        }

        if (tardis.isGrowth()
                || ClientTardisUtil.getPowerDelta() != ClientTardisUtil.MAX_POWER_DELTA_TICKS) {
            if (!AITModClient.CONFIG.powerOffDarkness) return;
            RenderSystem.setShaderFogStart(Mth.lerpInt(ClientTardisUtil.getPowerDeltaForLerp(), -8, 24));
            RenderSystem.setShaderFogEnd(Mth.lerpInt(ClientTardisUtil.getPowerDeltaForLerp(), 11, 32));
            RenderSystem.setShaderFogShape(FogShape.SPHERE);
            RenderSystem.setShaderFogColor(0, 0, 0, tardis.siege().isActive() ? 0.85f : tardis.isGrowth() ? 0.5f : 1.0f);
        }
        if (tardis.crash().isToxic() && tardis.fuel().hasPower()) {
            RenderSystem
                    .setShaderFogStart(Mth.lerpInt(mc.getFrameTime() / 100f, -8, 24));
            RenderSystem.setShaderFogEnd(Mth.lerpInt(mc.getFrameTime() / 100f, 11, 32));
            RenderSystem.setShaderFogShape(FogShape.SPHERE);

            ItemStack stack = mc.player.getItemBySlot(EquipmentSlot.HEAD);

            RenderSystem.setShaderFogColor(0.2f, 0.2f, 0.2f,
                    stack.is(AITTags.Items.FULL_RESPIRATORS) ? 0.015f : 0.35f);
        }

        if (!tardis.isGrowth()
                && ("partytardis".equalsIgnoreCase(String.valueOf(tardis.stats().getName()))
                || !tardis.extra().getInsertedDisc().isEmpty())) {

            final float[] rgb = ClientTardisUtil.getPartyColors();

            RenderSystem.setShaderFogStart(Mth.lerpInt(mc.getFrameTime() / 100f, -8, 24));
            RenderSystem.setShaderFogEnd(Mth.lerpInt(mc.getFrameTime() / 100f, 20, 40));
            RenderSystem.setShaderFogShape(FogShape.SPHERE);
            RenderSystem.setShaderFogColor(rgb[0], rgb[1], rgb[2], 0.25f);
        }
    }
}
