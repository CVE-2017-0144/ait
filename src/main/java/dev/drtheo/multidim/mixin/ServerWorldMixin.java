package dev.drtheo.multidim.mixin;

import dev.drtheo.multidim.event.WorldSaveEvent;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerWorldMixin {

    @Inject(method = "saveLevelData", at = @At("HEAD"))
    private void saveLevel(CallbackInfo ci) {
        WorldSaveEvent.EVENT.invoker().onWorldSave((ServerLevel) (Object) this);
    }
}
