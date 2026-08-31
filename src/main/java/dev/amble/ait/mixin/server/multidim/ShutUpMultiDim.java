package dev.amble.ait.mixin.server.multidim;

import dev.drtheo.multidim.MultiDim;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

/**
 * Fixes MultiDim world loading recursion.
**/
@Mixin(MultiDim.class)
public class ShutUpMultiDim {

    @Redirect(method = "addOrLoad(Ldev/drtheo/multidim/api/WorldBlueprint;Lnet/minecraft/resources/ResourceKey;Z)Ldev/drtheo/multidim/api/MultiDimServerWorld;", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;getLevel(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/server/level/ServerLevel;"))
    public ServerLevel shutUp(MinecraftServer instance, ResourceKey<Level> key) {
        return null;
    }
}
