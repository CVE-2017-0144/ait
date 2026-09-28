package dev.amble.lib.platform;

import java.util.Optional;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.LoadingModList;

public final class Platform {

    private Platform() {}

    private static IEventBus modBus;

    public static void setModBus(IEventBus bus) {
        modBus = bus;
    }

    public static IEventBus modBus() {
        return modBus;
    }

    public static boolean isModLoaded(String modId) {
        // mixin plugins ask before the ModList exists
        if (ModList.get() == null)
            return LoadingModList.get().getModFileById(modId) != null;

        return ModList.get().isLoaded(modId);
    }

    public static boolean isClient() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }

    public static boolean isServer() {
        return !isClient();
    }

    public static Optional<String> modName(String modId) {
        return ModList.get().getModContainerById(modId)
                .map(container -> container.getModInfo().getDisplayName());
    }

    public static Optional<String> modVersion(String modId) {
        return ModList.get().getModContainerById(modId)
                .map(container -> container.getModInfo().getVersion().toString());
    }
}
