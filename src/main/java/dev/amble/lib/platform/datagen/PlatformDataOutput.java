package dev.amble.lib.platform.datagen;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.fml.ModList;

public class PlatformDataOutput extends PackOutput {

    private final String modId;
    private final ExistingFileHelper existingFiles;

    public PlatformDataOutput(PackOutput parent, String modId, ExistingFileHelper existingFiles) {
        super(parent.getOutputFolder());

        this.modId = modId;
        this.existingFiles = existingFiles;
    }

    public String getModId() {
        return this.modId;
    }

    public ExistingFileHelper existingFiles() {
        return this.existingFiles;
    }

    public Optional<Path> findResource(String path) {
        return ModList.get().getModContainerById(this.modId)
                .map(c -> c.getModInfo().getOwningFile().getFile().findResource(path))
                .filter(Files::exists);
    }
}
