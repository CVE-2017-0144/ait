package dev.amble.ait.client.sonic;

import dev.amble.lib.platform.render.ModelLoading;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;


// TODO: when finished, move to SonicRendering
public class SonicModelLoader {

    public static List<ResourceLocation> toLoad;

    public static void init() {
        ModelLoading.addModels(toLoad);
    }

    public static void fromMap(FileToIdConverter finder, Map<ResourceLocation, Resource> map) {
        List<ResourceLocation> result = new ArrayList<>();

        map.forEach((identifier, resource) -> {
            result.add(finder.fileToId(identifier));
        });

        toLoad = result;

        // after init(), the model event fires later
        ModelLoading.addModels(result);
    }
}
