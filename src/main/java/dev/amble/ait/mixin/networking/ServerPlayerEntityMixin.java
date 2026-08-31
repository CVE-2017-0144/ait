package dev.amble.ait.mixin.networking;

import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.ait.api.tardis.TardisEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerEntityMixin extends Player {

    public ServerPlayerEntityMixin(Level world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "untrackChunk", at = @At("TAIL"))
    public void ait$sendUnloadChunkPacket(ChunkPos chunkPos, CallbackInfo ci) {
        if (this.isAlive())
            TardisEvents.UNLOAD_TARDIS.invoker().unload((ServerPlayer) (Object) this, chunkPos);
    }
}
