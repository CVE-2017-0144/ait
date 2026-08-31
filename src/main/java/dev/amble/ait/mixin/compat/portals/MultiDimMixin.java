package dev.amble.ait.mixin.compat.portals;

import dev.drtheo.multidim.MultiDim;
import dev.drtheo.multidim.api.MultiDimServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import qouteall.q_misc_util.api.DimensionAPI;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

@Mixin(MultiDim.class)
public class MultiDimMixin {

    @Shadow(remap = false) @Final protected MinecraftServer server;

    @Redirect(method = "remove", at = @At(value = "INVOKE", target = "Ldev/drtheo/multidim/api/MultiDimServer;multidim$removeWorld(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/server/level/ServerLevel;"))
    public ServerLevel remove(MultiDimServer instance, ResourceKey<Level> key) {
        ServerLevel world = server.getLevel(key);

        if (world == null)
            return null;

        DimensionAPI.removeDimensionDynamically(world);
        return world;
    }
}
