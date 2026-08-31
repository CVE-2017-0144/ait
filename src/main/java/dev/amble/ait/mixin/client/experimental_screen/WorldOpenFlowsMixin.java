package dev.amble.ait.mixin.client.experimental_screen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.ait.client.AITModClient;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;

@Mixin(value = WorldOpenFlows.class)
public abstract class WorldOpenFlowsMixin {

    @Shadow protected abstract void doLoadLevel(Screen parent, String levelName, boolean safeMode, boolean canShowBackupPrompt);

    @Inject(method = "loadLevel(Lnet/minecraft/client/gui/screens/Screen;Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows;doLoadLevel(Lnet/minecraft/client/gui/screens/Screen;Ljava/lang/String;ZZ)V"), cancellable = true)
    private void skipBackupScreen(Screen parent, String levelName, CallbackInfo ci) {
        if (!AITModClient.CONFIG.showExperimentalWarning) {
            this.doLoadLevel(parent, levelName, false, false);
            ci.cancel();
        }
    }
}
