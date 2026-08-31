package dev.amble.lib.platform.render;

import java.util.Collection;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public final class ModelLoading {

    private ModelLoading() {}

    public static void addModels(Collection<ResourceLocation> models) {
        ModelLoadingPlugin.register(context -> context.addModels(models));
    }
}
