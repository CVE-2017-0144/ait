package dev.drtheo.multidim.util;

import dev.drtheo.multidim.api.MutableRegistry;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.dimension.LevelStem;


public class MultiDimUtil {

    public static MappedRegistry<LevelStem> getDimensionsRegistry(MinecraftServer server) {
        RegistryAccess registryManager = server.registries().compositeAccess();
        return (MappedRegistry<LevelStem>) registryManager.registryOrThrow(Registries.LEVEL_STEM);
    }

    public static MutableRegistry<LevelStem> getMutableDimensionsRegistry(MinecraftServer server) {
        return (MutableRegistry<LevelStem>) getDimensionsRegistry(server);
    }
}
