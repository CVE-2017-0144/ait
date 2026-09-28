package dev.amble.ait.mixin.client.sonic;

import dev.amble.ait.client.sonic.SonicResourceFinder;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.FileToIdConverter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBakery.class)
public class SonicModelLoaderWrapperMixin {

    @Mutable
    @Shadow @Final public static FileToIdConverter MODEL_LISTER;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void clinit(CallbackInfo ci) {
        MODEL_LISTER = new SonicResourceFinder(MODEL_LISTER, "models/item/sonic", ".json");
    }
}
