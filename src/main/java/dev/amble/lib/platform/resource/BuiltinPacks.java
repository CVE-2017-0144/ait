package dev.amble.lib.platform.resource;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinPacks {

    private BuiltinPacks() {}

    public static void register(String modId, ResourceLocation id, boolean enabledByDefault) {
        FabricLoader.getInstance().getModContainer(modId).ifPresent(container ->
                ResourceManagerHelper.registerBuiltinResourcePack(id, container, enabledByDefault
                        ? ResourcePackActivationType.DEFAULT_ENABLED
                        : ResourcePackActivationType.NORMAL));
    }
}
