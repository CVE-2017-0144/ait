package dev.amble.ait.mixin.compat.portals;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qouteall.imm_ptl.core.teleportation.ServerTeleportationManager;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.core.world.TardisServerWorld;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

@Mixin(ServerTeleportationManager.class)
public class ServerTeleportationManagerMixin {

    @Inject(method = "changePlayerDimension", at = @At("TAIL"))
    private static void onTeleported(ServerPlayer player, ServerLevel fromWorld, ServerLevel toWorld, Vec3 newEyePos, CallbackInfo ci) {
        if (fromWorld instanceof TardisServerWorld tsw)
            TardisEvents.LEAVE_TARDIS.invoker().onLeave(tsw.getTardis(), player);

        if (toWorld instanceof TardisServerWorld tsw)
            if (TardisEvents.ENTER_TARDIS.invoker().onEnter(tsw.getTardis(), player) == TardisEvents.Interaction.FAIL) {
                TardisUtil.teleportOutside(tsw.getTardis(), player);
            }
    }
}
