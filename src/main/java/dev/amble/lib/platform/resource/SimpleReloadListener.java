package dev.amble.lib.platform.resource;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public interface SimpleReloadListener extends SimpleSynchronousResourceReloadListener {

    ResourceLocation getReloadId();

    @Override
    default ResourceLocation getFabricId() {
        return this.getReloadId();
    }
}
