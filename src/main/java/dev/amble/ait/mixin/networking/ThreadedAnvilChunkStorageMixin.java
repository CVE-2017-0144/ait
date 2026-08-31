package dev.amble.ait.mixin.networking;

import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.ait.api.tardis.TardisEvents;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;

@Mixin(value = ChunkMap.class, priority = 1001)
public abstract class ThreadedAnvilChunkStorageMixin {

    @Inject(method = "playerLoadedChunk", at = @At("RETURN"))
    public void sendChunkDataPackets(ServerPlayer player, MutableObject<ClientboundLevelChunkWithLightPacket> cachedDataPacket, LevelChunk chunk, CallbackInfo ci) {
        TardisEvents.SYNC_TARDIS.invoker().sync(player, chunk.getPos());
    }
}
