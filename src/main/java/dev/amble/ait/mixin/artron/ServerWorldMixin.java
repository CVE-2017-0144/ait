package dev.amble.ait.mixin.artron;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.ait.core.events.ServerChunkEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;

@Mixin(ServerLevel.class)
public class ServerWorldMixin {

    @Inject(method = "tickChunk", at = @At("TAIL"))
    public void tickChunk(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        ServerChunkEvents.TICK.invoker().onChunkTick((ServerLevel) (Object) this, chunk);
    }
}
