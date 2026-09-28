package dev.amble.ait.mixin.networking;

import dev.amble.ait.api.tardis.TardisEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.PlayerChunkSender;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerChunkSender.class, priority = 1001)
public abstract class ThreadedAnvilChunkStorageMixin {

    @Inject(method = "sendChunk", at = @At("RETURN"))
    private static void sendChunkDataPackets(ServerGamePacketListenerImpl handler, ServerLevel world, LevelChunk chunk,
            CallbackInfo ci) {
        TardisEvents.SYNC_TARDIS.invoker().sync(handler.player, chunk.getPos());
    }
}
