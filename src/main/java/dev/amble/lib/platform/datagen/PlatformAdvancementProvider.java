package dev.amble.lib.platform.datagen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

public abstract class PlatformAdvancementProvider implements DataProvider {

    private final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> registries;

    protected PlatformAdvancementProvider(PlatformDataOutput output,
            CompletableFuture<HolderLookup.Provider> fut) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "advancement");
        this.registries = fut;
    }

    public abstract void generateAdvancement(HolderLookup.Provider registries,
            Consumer<AdvancementHolder> consumer);

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return this.registries.thenCompose(registries -> {
            Set<ResourceLocation> seen = new HashSet<>();
            List<CompletableFuture<?>> written = new ArrayList<>();

            this.generateAdvancement(registries, holder -> {
                if (!seen.add(holder.id()))
                    throw new IllegalStateException("Duplicate advancement " + holder.id());

                written.add(DataProvider.saveStable(output, registries, Advancement.CODEC, holder.value(),
                        this.pathProvider.json(holder.id())));
            });

            return CompletableFuture.allOf(written.toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName() {
        return "Advancements";
    }
}
