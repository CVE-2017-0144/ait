package dev.amble.lib.platform.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class PlatformDataGenerator {

    private final DataGenerator generator;
    private final PlatformDataOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;
    private final boolean run;

    public PlatformDataGenerator(GatherDataEvent event, String modId) {
        this.generator = event.getGenerator();
        this.output = new PlatformDataOutput(event.getGenerator().getPackOutput(), modId, event.getExistingFileHelper());
        this.registries = event.getLookupProvider();
        this.run = event.includeClient() || event.includeServer();
    }

    public Pack createPack() {
        return new Pack();
    }

    public class Pack {

        @FunctionalInterface
        public interface Factory<T extends DataProvider> {
            T create(PlatformDataOutput output);
        }

        @FunctionalInterface
        public interface RegistryDependentFactory<T extends DataProvider> {
            T create(PlatformDataOutput output, CompletableFuture<HolderLookup.Provider> registries);
        }

        public <T extends DataProvider> T addProvider(Factory<T> factory) {
            return generator.addProvider(run,
                    (DataProvider.Factory<T>) packOutput -> factory.create(output));
        }

        public <T extends DataProvider> T addProvider(RegistryDependentFactory<T> factory) {
            return generator.addProvider(run,
                    (DataProvider.Factory<T>) packOutput -> factory.create(output,
                            registries));
        }
    }

    public interface Entrypoint {
        void onInitializeDataGenerator(PlatformDataGenerator generator);
    }
}
