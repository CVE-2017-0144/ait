package dev.amble.ait.client.sonic;

import java.util.Map;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

public class SonicResourceFinder extends FileToIdConverter {

    private final FileToIdConverter parent;

    public SonicResourceFinder(FileToIdConverter parent, String directoryName, String fileExtension) {
        super(directoryName, fileExtension);
        this.parent = parent;
    }

    @Override
    public ResourceLocation idToFile(ResourceLocation id) {
        return parent.idToFile(id);
    }

    @Override
    public ResourceLocation fileToId(ResourceLocation path) {
        return parent.fileToId(path);
    }

    @Override
    public Map<ResourceLocation, Resource> listMatchingResources(ResourceManager resourceManager) {
        Map<ResourceLocation, Resource> map = parent.listMatchingResources(resourceManager);
        SonicModelLoader.fromMap(this, super.listMatchingResources(resourceManager));
        return map;
    }
}
