package dev.amble.ait.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.model.data.ModelDataManager;
import net.neoforged.neoforge.event.level.ChunkEvent;

import dev.loqor.portal.client.ClientWorldAnalog;

@Mixin(ClientChunkCache.class)
public class ClientChunkCacheBotiMixin {

    @Shadow @Final ClientLevel level;

    // shadow level chunks stay away from the real listeners
    @WrapOperation(method = {"replaceWithPacketData", "drop"}, at = @At(value = "INVOKE",
            target = "Lnet/neoforged/bus/api/IEventBus;post(Lnet/neoforged/bus/api/Event;)Lnet/neoforged/bus/api/Event;"))
    private Event ait$skipShadowChunkEvents(IEventBus bus, Event event, Operation<Event> original) {
        if (this.level instanceof ClientWorldAnalog) {
            if (event instanceof ChunkEvent.Unload unload)
                ModelDataManager.onChunkUnload(unload);

            return event;
        }

        return original.call(bus, event);
    }
}
