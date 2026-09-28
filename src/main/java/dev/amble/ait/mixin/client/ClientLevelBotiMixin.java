package dev.amble.ait.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;

import dev.loqor.portal.client.ClientWorldAnalog;

@Mixin(ClientLevel.class)
public class ClientLevelBotiMixin {

    // shadow levels never unload, skip the load too
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE",
            target = "Lnet/neoforged/bus/api/IEventBus;post(Lnet/neoforged/bus/api/Event;)Lnet/neoforged/bus/api/Event;"))
    private Event ait$skipShadowLevelLoad(IEventBus bus, Event event, Operation<Event> original) {
        if ((Object) this instanceof ClientWorldAnalog)
            return event;

        return original.call(bus, event);
    }
}
