package dev.amble.ait.mixin.networking;

import dev.amble.ait.api.tardis.TardisEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.PlayerChunkSender;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerChunkSender.class)
public abstract class ServerPlayerEntityMixin {

    @Inject(method = "dropChunk", at = @At("TAIL"))
    public void ait$sendUnloadChunkPacket(ServerPlayer player, ChunkPos chunkPos, CallbackInfo ci) {
        if (player.isAlive())
            TardisEvents.UNLOAD_TARDIS.invoker().unload(player, chunkPos);
    }
}
