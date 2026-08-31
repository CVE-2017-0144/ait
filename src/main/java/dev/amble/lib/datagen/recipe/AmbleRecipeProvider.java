package dev.amble.lib.datagen.recipe;

import dev.amble.lib.platform.datagen.PlatformDataOutput;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AmbleRecipeProvider extends RecipeProvider {

    private final PlatformDataOutput output;

    private final List<ShapelessRecipeBuilder> shapelessRecipes = new ArrayList<>();
    private final List<ShapedRecipeBuilder> shapedRecipes = new ArrayList<>();
    private final HashMap<SmithingTransformRecipeBuilder, ResourceLocation> smithingTransformRecipes = new HashMap<>();
    private final HashMap<ShapelessRecipeBuilder, ResourceLocation> shapelessRecipesWithNameHashMap = new HashMap<>();
    private final HashMap<ShapedRecipeBuilder, ResourceLocation> shapedRecipesWithNameHashMap = new HashMap<>();
    private final HashMap<SingleItemRecipeBuilder, ResourceLocation> stonecutting = new HashMap<>();
    private final List<SimpleCookingRecipeBuilder> blasting = new ArrayList<>();

    public AmbleRecipeProvider(PlatformDataOutput output,
            CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);

        this.output = output;
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        for (ShapelessRecipeBuilder shapelessRecipeJsonBuilder : shapelessRecipes) {
            shapelessRecipeJsonBuilder.save(exporter);
        }

        for (ShapedRecipeBuilder shapedRecipeJsonBuilder : shapedRecipes) {
            shapedRecipeJsonBuilder.save(exporter);
        }

        shapelessRecipesWithNameHashMap.forEach((builder, id) -> builder.save(exporter, id));

        smithingTransformRecipes.forEach((smithingTransformRecipeJsonBuilder, identifier) -> smithingTransformRecipeJsonBuilder.save(exporter, identifier));
        shapedRecipesWithNameHashMap.forEach((builder, id) -> builder.save(exporter, id));

        stonecutting.forEach((stonecuttingRecipeJsonBuilder, identifier) -> stonecuttingRecipeJsonBuilder.save(exporter, identifier));

        for (SimpleCookingRecipeBuilder cookingRecipeJsonBuilder : blasting) {
            cookingRecipeJsonBuilder.save(exporter);
        }
    }

    public void addShapelessRecipe(ShapelessRecipeBuilder builder) {
        if (!shapelessRecipes.contains(builder)) {
            shapelessRecipes.add(builder);
        }
    }

    public void addShapelessRecipe(ShapelessRecipeBuilder builder, ResourceLocation id) {
        shapelessRecipesWithNameHashMap.put(builder, id);
    }

    public void addSmithingTransformRecipe(SmithingTransformRecipeBuilder builder, ResourceLocation id) {
        smithingTransformRecipes.put(builder, id);
    }

    public void addShapedRecipe(ShapedRecipeBuilder builder, ResourceLocation id) {
        shapedRecipesWithNameHashMap.put(builder, id);
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
        return ResourceLocation.fromNamespaceAndPath(this.output.getModId(), fixupBlockKey(in.getDescriptionId()) + "_to_" + fixupBlockKey(out.getDescriptionId()) + "_stonecutting");
    }

    private String fixupBlockKey(String key) {
        return key.substring(key.lastIndexOf(".") + 1);
    }

    public void addBlastFurnaceRecipe(SimpleCookingRecipeBuilder cookingBuilder) {
        if (!blasting.contains(cookingBuilder)) {
            blasting.add(cookingBuilder);
        }
    }
}

