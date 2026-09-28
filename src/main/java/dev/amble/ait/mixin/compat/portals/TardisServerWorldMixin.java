package dev.amble.ait.mixin.compat.portals;

import dev.amble.ait.compat.portal.PortalsDimSync;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.drtheo.multidim.api.MultiDimServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TardisServerWorld.class)
public class TardisServerWorldMixin {

    @Inject(method = "create", at = @At("RETURN"), remap = false)
    private static void create(ServerTardis tardis, CallbackInfoReturnable<TardisServerWorld> cir) {
        aitportals$handleWorld(cir.getReturnValue());
    }

    // makes sure that we don't handle the world twice in case it gets created
    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Ldev/amble/ait/core/world/TardisServerWorld;setTardis(Ldev/amble/ait/core/tardis/ServerTardis;)V"))
    private static void load(TardisServerWorld instance, ServerTardis tardis) {
        instance.setTardis(tardis);
        aitportals$handleWorld(instance);
    }

    @Unique private static void aitportals$handleWorld(MultiDimServerWorld world) {
        // may happen if this is called during #load and the world doesn't exist!
        if (world == null)
            return;

        PortalsDimSync.sync(world.getServer());
    }
}
