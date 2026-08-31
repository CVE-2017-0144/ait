package dev.amble.lib.platform.datagen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.google.gson.JsonElement;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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
        List<Item> skipped = new ArrayList<>();

        this.generateBlockStateModels(new BlockModelGenerators(
                generator -> blockStates.put(generator.getBlock(), generator), models::put, skipped::add));
        this.generateItemModels(new ItemModelGenerators(models::put));

        List<CompletableFuture<?>> written = new ArrayList<>();

        blockStates.forEach((block, generator) -> written.add(DataProvider.saveStable(output, generator.get(),
                this.blockStatePath.json(net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block)))));
        models.forEach((id, json) -> written.add(DataProvider.saveStable(output, json.get(),
                this.modelPath.json(id))));

        return CompletableFuture.allOf(written.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Model Definitions";
    }
}
