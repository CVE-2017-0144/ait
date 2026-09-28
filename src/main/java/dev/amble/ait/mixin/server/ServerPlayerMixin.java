package dev.amble.ait.mixin.server;

import dev.amble.ait.core.entities.FlightTardisEntity;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.control.impl.SecurityControl;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.core.world.TardisServerWorld;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void ait$tick(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;

        // if player is in tardis and y is less than -100 save them
        // if leave-behind is on, and they do not have a key + enough loyalty, then evict them instead
        if (player.getY() <= -100 && player.serverLevel() instanceof TardisServerWorld tardisWorld) {
            ServerTardis serverTardis = tardisWorld.getTardis();

            if (!SecurityControl.hasMatchingKey(player, serverTardis) && serverTardis.travel().leaveBehind().get())
                TardisUtil.teleportOutside(serverTardis, player);
            else
                TardisUtil.teleportInside(serverTardis, player);

            player.fallDistance = 0;
        }
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void ait$attack(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        if (player.isPassenger() && player.getVehicle() instanceof FlightTardisEntity)
            ci.cancel();
    }
}
