package dev.amble.ait.module.gun.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.amble.ait.module.gun.core.item.BaseGunItem;
import dev.amble.ait.module.gun.core.item.GunItems;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Unique private boolean goBackFOV;
    @Unique private final double targetFOV = 45;
    @Unique private double currentFOV = targetFOV;

    @Inject(method = "getFov(Lnet/minecraft/client/Camera;FZ)D", at = @At("RETURN"), cancellable = true)
    public void ait$getFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        if (!changingFov) return;

        double d = cir.getReturnValue();
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        double newFov = setADS(d, Minecraft.getInstance().player);
        if (d != newFov) cir.setReturnValue(newFov);
    }

    @Unique private static boolean isADS(LocalPlayer player) {
        return Minecraft.getInstance().options.keyUse.isDown() && Minecraft.getInstance().options.getCameraType().isFirstPerson() && player.getMainHandItem().getItem() instanceof BaseGunItem;
    }

    @Unique private double setADS(double fov, LocalPlayer player) {
        double realTargetFOV = Math.max((player.getMainHandItem().getItem() == GunItems.CULT_STASER_RIFLE ? 10 : 30), currentFOV - (player.getMainHandItem().getItem() == GunItems.CULT_STASER_RIFLE ? 70 : targetFOV));
        if (isADS(player)) {
            float speed = player.getMainHandItem().getItem() == GunItems.CULT_STASER_RIFLE ? 0.2f : 0.4f;
            currentFOV = Mth.lerp(Math.min(speed * Minecraft.getInstance().getFrameTime(), speed), currentFOV, realTargetFOV);
            goBackFOV = true;
            return currentFOV;
        }
        else if (goBackFOV && Math.abs(currentFOV - fov) > 0.00001) {
            currentFOV = Mth.lerp(Math.min(0.95f * Minecraft.getInstance().getFrameTime(), 0.95f), currentFOV, fov);
            return currentFOV;
        } else {
            currentFOV = fov;
            goBackFOV = false;
            return fov;
        }
    }
}
