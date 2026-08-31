package dev.amble.lib.platform.datagen;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gson.JsonElement;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

// no vanilla missing-blockstate throw, only our blocks get generated
public abstract class PlatformModelProvider implements DataProvider {

    private final PackOutput.PathProvider blockStatePath;
    private final PackOutput.PathProvider modelPath;

    protected PlatformModelProvider(PlatformDataOutput output) {
        this.blockStatePath = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.modelPath = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
    }

    public abstract void generateBlockStateModels(BlockModelGenerators generator);

    public abstract void generateItemModels(ItemModelGenerators generator);

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        Map<Block, BlockStateGenerator> blockStates = new HashMap<>();
        Map<ResourceLocation, Supplier<JsonElement>> models = new HashMap<>();
        Set<Item> skipped = new HashSet<>();

        this.generateBlockStateModels(new BlockModelGenerators(
                g -> blockStates.put(g.getBlock(), g), models::put, skipped::add));
        this.generateItemModels(new ItemModelGenerators(models::put));

        BuiltInRegistries.BLOCK.forEach(block -> {
            if (!blockStates.containsKey(block))
                return;

            Item item = Item.BY_BLOCK.get(block);

            if (item == null || skipped.contains(item))
                return;

            ResourceLocation id = ModelLocationUtils.getModelLocation(item);

            if (!models.containsKey(id))
                models.put(id, new DelegatedModel(ModelLocationUtils.getModelLocation(block)));
        });

        return CompletableFuture.allOf(
                this.saveCollection(output, blockStates,
                        block -> this.blockStatePath.json(block.builtInRegistryHolder().key().location())),
                this.saveCollection(output, models, this.modelPath::json));
    }

    private <T> CompletableFuture<?> saveCollection(CachedOutput output,
            Map<T, ? extends Supplier<JsonElement>> entries, Function<T, Path> path) {
        List<CompletableFuture<?>> fs = new ArrayList<>();

        entries.forEach((k, v) -> fs.add(DataProvider.saveStable(output, v.get(), path.apply(k))));

        return CompletableFuture.allOf(fs.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Model Definitions";
    }
}
