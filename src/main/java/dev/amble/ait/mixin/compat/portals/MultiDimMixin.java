package dev.amble.ait.mixin.compat.portals;

import de.nick1st.imm_ptl.events.DimensionEvents;
import dev.drtheo.multidim.MultiDim;
import dev.drtheo.multidim.api.MultiDimServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import qouteall.q_misc_util.dimension.DimensionIntId;

@Mixin(MultiDim.class)
public class MultiDimMixin {

    @Shadow(remap = false) @Final protected MinecraftServer server;

    @Redirect(method = "remove", at = @At(value = "INVOKE", target = "Ldev/drtheo/multidim/api/MultiDimServer;multidim$removeWorld(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/server/level/ServerLevel;"))
    public ServerLevel remove(MultiDimServer instance, ResourceKey<Level> key) {
        ServerLevel world = server.getLevel(key);

        if (world == null)
            return null;

        // neo ip has no removeDimensionDynamically, fire its hooks here
        NeoForge.EVENT_BUS.post(new DimensionEvents.BeforeRemovingDimensionEvent(server, world));
        instance.multidim$removeWorld(key);
        DimensionIntId.onServerDimensionChanged(server);
        return world;
    }
}
