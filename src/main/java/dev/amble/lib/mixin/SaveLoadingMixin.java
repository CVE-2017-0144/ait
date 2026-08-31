package dev.amble.lib.mixin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.server.WorldLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.amble.lib.api.KitEvents;

@Mixin(WorldLoader.class)
public class SaveLoadingMixin {
    @Inject(method = "load(Lnet/minecraft/server/WorldLoader$InitConfig;Lnet/minecraft/server/WorldLoader$WorldDataSupplier;Lnet/minecraft/server/WorldLoader$ResultFactory;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;", at = @At("HEAD"))
    private static <D, R> void amblekit$load(WorldLoader.InitConfig serverConfig, WorldLoader.WorldDataSupplier<D> loadContextSupplier, WorldLoader.ResultFactory<D, R> saveApplierFactory, Executor prepareExecutor, Executor applyExecutor, CallbackInfoReturnable<CompletableFuture<R>> cir) {
        KitEvents.PRE_DATAPACK_LOAD.invoker().load();
    }
}
