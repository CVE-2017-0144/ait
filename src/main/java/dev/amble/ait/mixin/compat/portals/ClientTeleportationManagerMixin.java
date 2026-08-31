package dev.amble.ait.mixin.compat.portals;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qouteall.imm_ptl.core.teleportation.ClientTeleportationManager;
import dev.amble.ait.api.ClientWorldEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;

@Mixin(ClientTeleportationManager.class)
public class ClientTeleportationManagerMixin {

    @Shadow
    @Final
    public static Minecraft client;

    @Inject(method = "changePlayerDimension", at = @At("TAIL"))
    private static void onTeleported(LocalPlayer player, ClientLevel fromWorld, ClientLevel toWorld, Vec3 newEyePos, CallbackInfo ci) {
        ClientWorldEvents.CHANGE_WORLD.invoker().onChange(client, toWorld);
    }
}
