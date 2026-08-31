package dev.amble.ait.mixin.client.experimental_screen;

import com.mojang.serialization.Lifecycle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import dev.amble.ait.client.AITModClient;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.server.RegistryLayer;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.storage.PrimaryLevelData;

@SuppressWarnings("deprecation")
@Mixin(value = CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    @Shadow
    private boolean recreated;

    @Shadow
    protected abstract void createNewWorld(PrimaryLevelData.SpecialWorldProperty specialProperty,
            LayeredRegistryAccess<RegistryLayer> combinedDynamicRegistries, Lifecycle lifecycle);

    @Inject(method = "onCreate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows;confirmWorldCreation(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;Lcom/mojang/serialization/Lifecycle;Ljava/lang/Runnable;Z)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void onCreate(CallbackInfo ci, WorldCreationContext holder,
            WorldDimensions.Complete config,
            LayeredRegistryAccess<RegistryLayer> registries, Lifecycle lifecycle, Lifecycle lifecycle2,
            Lifecycle lifecycle3, boolean showWarnings) {
        if (this.recreated)
            return;

        if (!AITModClient.CONFIG.showExperimentalWarning) {
            this.createNewWorld(config.specialWorldProperty(), registries, lifecycle3);
            ci.cancel();
        }
    }
}
