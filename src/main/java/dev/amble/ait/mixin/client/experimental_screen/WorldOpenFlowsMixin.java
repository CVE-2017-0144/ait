package dev.amble.ait.mixin.client.experimental_screen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.ait.client.AITModClient;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;

// skip the experimental prompt, straight to the next step
@Mixin(value = WorldOpenFlows.class)
public abstract class WorldOpenFlowsMixin {

    @Shadow protected abstract void openWorldLoadBundledResourcePack(LevelStorageSource.LevelStorageAccess access,
            WorldStem stem, PackRepository repo, Runnable onFail);

    @Inject(method = "openWorldCheckWorldStemCompatibility", at = @At("HEAD"), cancellable = true)
    private void skipBackupScreen(LevelStorageSource.LevelStorageAccess access, WorldStem stem,
            PackRepository repo, Runnable onFail, CallbackInfo ci) {
        if (!AITModClient.CONFIG.showExperimentalWarning) {
            this.openWorldLoadBundledResourcePack(access, stem, repo, onFail);
            ci.cancel();
        }
    }
}
