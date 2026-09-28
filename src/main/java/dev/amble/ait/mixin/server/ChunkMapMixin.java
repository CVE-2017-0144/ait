package dev.amble.ait.mixin.server;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;

@Mixin(ChunkMap.class)
public class ChunkMapMixin {

    @Shadow @Final private Long2ObjectLinkedOpenHashMap<ChunkHolder> pendingUnloads;

    // holder reloaded mid-generation keeps re-queueing its stale unload, hangs shutdown
    @Inject(method = "scheduleUnload", at = @At("HEAD"), cancellable = true)
    private void ait$dropStaleUnload(long pos, ChunkHolder holder, CallbackInfo ci) {
        if (this.pendingUnloads.get(pos) != holder)
            ci.cancel();
    }
}
