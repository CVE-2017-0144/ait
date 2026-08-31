package dev.amble.ait.module.planet.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.amble.ait.core.entities.FlightTardisEntity;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "getDepthFar", at = @At("HEAD"), cancellable = true)
    private void ait$getFarPlaneDistance(CallbackInfoReturnable<Float> cir) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        boolean ifTardisWorld = TardisServerWorld.isTardisDimension(mc.level);
        Planet planet = PlanetRegistry.getInstance().get(mc.level);
        if (planet != null || ifTardisWorld) {
            cir.setReturnValue((64.0F * 16.0F) * 64.0F);
        }
    }

    @Inject(method = "renderConfusionOverlay", at = @At("HEAD"), cancellable = true)
    private void ait$renderNausea(GuiGraphics context, float distortionStrength, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || !mc.player.isPassenger()) return;
        if (mc.player.getVehicle() instanceof FlightTardisEntity)
            ci.cancel();
    }
}
