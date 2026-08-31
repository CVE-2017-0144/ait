package dev.amble.lib.platform.resource;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public final class ReloadListeners {

    private ReloadListeners() {}

    public static void register(PackType type, SimpleReloadListener listener) {
        ResourceManagerHelper.get(type).registerReloadListener(listener);
    }
}
