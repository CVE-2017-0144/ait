package dev.amble.lib.platform.resource;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public interface SimpleReloadListener extends ResourceManagerReloadListener {

    ResourceLocation getReloadId();
}
