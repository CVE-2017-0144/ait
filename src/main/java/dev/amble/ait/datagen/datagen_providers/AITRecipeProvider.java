package dev.amble.ait.datagen.datagen_providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import dev.amble.ait.AITMod;
import dev.amble.lib.platform.datagen.PlatformDataOutput;

public class AITRecipeProvider extends RecipeProvider {
    public List<ShapelessRecipeBuilder> shapelessRecipes = new ArrayList<>();
    public List<ShapedRecipeBuilder> shapedRecipes = new ArrayList<>();
    public HashMap<SmithingTransformRecipeBuilder, ResourceLocation> smithingTransformRecipes = new HashMap<>();
    public HashMap<ShapelessRecipeBuilder, ResourceLocation> shapelessRecipesWithNameHashMap = new HashMap<>();
    public HashMap<SingleItemRecipeBuilder, ResourceLocation> stonecutting = new HashMap<>();
    public List<BlastFurnaceRecipeEntry> blasting = new ArrayList<>();
    public List<FurnaceRecipeEntry> smelting = new ArrayList<>();
    public record FurnaceRecipeEntry(SimpleCookingRecipeBuilder builder, ResourceLocation id) {}
    public record BlastFurnaceRecipeEntry(SimpleCookingRecipeBuilder builder, ResourceLocation id) {}


    public AITRecipeProvider(PlatformDataOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        for (ShapelessRecipeBuilder shapelessRecipeJsonBuilder : shapelessRecipes) {
            shapelessRecipeJsonBuilder.save(exporter);
        }
        for (ShapedRecipeBuilder shapedRecipeJsonBuilder : shapedRecipes) {
            shapedRecipeJsonBuilder.save(exporter);
        }
        shapelessRecipesWithNameHashMap.forEach((shapelessRecipeJsonBuilder, identifier) -> {
            shapelessRecipeJsonBuilder.save(exporter, identifier);
        });
        smithingTransformRecipes.forEach((smithingTransformRecipeJsonBuilder, identifier) -> {
            smithingTransformRecipeJsonBuilder.save(exporter, identifier);
        });

        stonecutting.forEach((stonecuttingRecipeJsonBuilder, identifier) -> {
            stonecuttingRecipeJsonBuilder.save(exporter, identifier);
        });

        for (BlastFurnaceRecipeEntry entry : blasting) {
            entry.builder().save(exporter, entry.id());
        }

        for (FurnaceRecipeEntry entry : smelting) {
            entry.builder().save(exporter, entry.id());
        }


    }

    public void addShapelessRecipe(ShapelessRecipeBuilder builder) {
        if (!shapelessRecipes.contains(builder)) {
            shapelessRecipes.add(builder);
        }
    }

    public void addShapelessRecipeWithCustomname(ShapelessRecipeBuilder builder, ResourceLocation id) {
        shapelessRecipesWithNameHashMap.put(builder, id);
    }

    public void addSmithingTransformRecipe(SmithingTransformRecipeBuilder builder, ResourceLocation id) {
        smithingTransformRecipes.put(builder, id);
    }


    public void addShapedRecipe(ShapedRecipeBuilder builder) {
        if (!shapedRecipes.contains(builder)) {
            shapedRecipes.add(builder);
        }
    }

    public void addStonecutting(Block in, Block out, int count) {
        ResourceLocation id = getStonecuttingIdentifier(in, out);

        stonecutting.put(SingleItemRecipeBuilder.stonecutting(Ingredient.of(in), RecipeCategory.BUILDING_BLOCKS, out, count).unlockedBy("has_block", VanillaRecipeProvider.has(in)), id);
    }
    public void addStonecutting(Block in, Block out) {
        addStonecutting(in, out, 1);
    }

    private ResourceLocation getStonecuttingIdentifier(Block in, Block out) {
        return AITMod.id(fixupBlockKey(in.getDescriptionId()) + "_to_" + fixupBlockKey(out.getDescriptionId()) + "_stonecutting");
    }
    private String fixupBlockKey(String key) {
        return key.substring(key.lastIndexOf(".") + 1);
    }

    public void addBlastFurnaceRecipe(SimpleCookingRecipeBuilder cookingBuilder, ResourceLocation id) {
        blasting.add(new BlastFurnaceRecipeEntry(cookingBuilder, id));
    }

    public void addFurnaceRecipe(SimpleCookingRecipeBuilder cookingBuilder, ResourceLocation id) {
        smelting.add(new FurnaceRecipeEntry(cookingBuilder, id));
    }

}
