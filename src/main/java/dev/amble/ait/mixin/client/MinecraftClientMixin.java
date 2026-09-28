package dev.amble.ait.mixin.client;

import dev.amble.ait.api.ClientWorldEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

    @Inject(method = "updateLevelInEngines", at = @At("TAIL"))
    public void setWorld(ClientLevel world, CallbackInfo ci) {
        ClientWorldEvents.CHANGE_WORLD.invoker().onChange((Minecraft) (Object) this, world);
    }
}
