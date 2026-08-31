package dev.amble.lib.platform;

import java.util.Optional;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public final class Platform {

    private Platform() {}

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    public static boolean isServer() {
        return !isClient();
    }

    public static Optional<String> modName(String modId) {
        return FabricLoader.getInstance().getModContainer(modId)
                .map(container -> container.getMetadata().getName());
    }

    public static Optional<String> modVersion(String modId) {
        return FabricLoader.getInstance().getModContainer(modId)
                .map(container -> container.getMetadata().getVersion().getFriendlyString());
    }
}
