package dev.amble.ait.module.planet;

import static net.minecraft.data.recipes.RecipeProvider.*;

import java.util.Optional;
import java.util.function.Consumer;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.core.util.SpaceUtils;
import dev.amble.ait.datagen.datagen_providers.AITBlockTagProvider;
import dev.amble.ait.datagen.datagen_providers.AITItemTagProvider;
import dev.amble.ait.datagen.datagen_providers.AITRecipeProvider;
import dev.amble.ait.module.Module;
import dev.amble.ait.module.planet.client.SpaceSuitOverlay;
import dev.amble.ait.module.planet.core.PlanetBlockEntities;
import dev.amble.ait.module.planet.core.PlanetBlocks;
import dev.amble.ait.module.planet.core.PlanetItems;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;
import dev.amble.ait.module.planet.core.util.PlanetCustomTrades;
import dev.amble.lib.container.RegistryContainer;
import dev.amble.lib.container.impl.BlockContainer;
import dev.amble.lib.container.impl.ItemContainer;
import dev.amble.lib.datagen.lang.AmbleLanguageProvider;
import dev.amble.lib.datagen.model.AmbleModelProvider;
import dev.amble.lib.itemgroup.AItemGroup;
import dev.amble.lib.platform.render.HudRenderEvents;
import dev.amble.lib.register.AmbleRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class PlanetModule extends Module {
    private static final PlanetModule INSTANCE = new PlanetModule();

    public static final ResourceLocation ID = AITMod.id("planet");

    @Override
    public void init() {
        SpaceUtils.init();

        PlanetCustomTrades.registerCustomTrades();
        AmbleRegistries.getInstance().register(PlanetRegistry.getInstance());

        RegistryContainer.register(PlanetItems.class, AITMod.MOD_ID);
        RegistryContainer.register(PlanetBlocks.class, AITMod.MOD_ID);
        RegistryContainer.register(PlanetBlockEntities.class, AITMod.MOD_ID);
    }

    @Override
    protected AItemGroup.Builder buildItemGroup() {
        return AItemGroup.builder(id()).icon(() -> new ItemStack(PlanetItems.SPACESUIT_HELMET));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        HudRenderEvents.HUD.register(new SpaceSuitOverlay());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }


    @Override
    public Optional<Class<? extends BlockContainer>> getBlockRegistry() {
        return Optional.of(PlanetBlocks.class);
    }

    @Override
    public Optional<Class<? extends ItemContainer>> getItemRegistry() {
        return Optional.of(PlanetItems.class);
    }

    @Override
    public Optional<DataGenerator> getDataGenerator() {
        return Optional.of(new DataGenerator() {
            @Override
            public void lang(AmbleLanguageProvider provider) {
                provider.addTranslation(getItemGroup(), "AIT: Planetary Exploration");
                provider.addTranslation("itemGroup.ait.planet", "AIT: Planetary Exploration");
                provider.addTranslation("message.ait.oxygen", "Stored Oxygen: %s");
                provider.addTranslation("achievements.ait.title.planet_root", "Planetary Exploration");
                provider.addTranslation("achievements.ait.description.planet_root", "Explore the planets of the universe");
                provider.addTranslation("achievements.ait.title.enter_mars", "You were not the first");
                provider.addTranslation("achievements.ait.description.enter_mars", "Landed on Mars for the first time");
                provider.addTranslation("achievements.ait.title.enter_moon", "One small step for Time Lords");
                provider.addTranslation("achievements.ait.description.enter_moon", "Landed on the Moon for the first time");
                provider.addTranslation("achievements.ait.find_planet_structure.title", "Veneration.");
                provider.addTranslation("achievements.ait.find_planet_structure.description", "Dread.");
            }

            @Override
            public void recipes(AITRecipeProvider provider) {

                // Martian
                provider.addStonecutting(PlanetBlocks.MARTIAN_STONE, PlanetBlocks.MARTIAN_BRICKS);
                provider.addStonecutting(PlanetBlocks.MARTIAN_STONE, PlanetBlocks.MARTIAN_BRICK_WALL);
                provider.addStonecutting(PlanetBlocks.MARTIAN_STONE, PlanetBlocks.MARTIAN_BRICK_STAIRS);
                provider.addStonecutting(PlanetBlocks.MARTIAN_STONE, PlanetBlocks.MARTIAN_BRICK_SLAB);
                provider.addStonecutting(PlanetBlocks.MARTIAN_STONE, PlanetBlocks.CHISELED_MARTIAN_STONE);
                provider.addStonecutting(PlanetBlocks.MARTIAN_STONE, PlanetBlocks.MARTIAN_STONE_SLAB);
                provider.addStonecutting(PlanetBlocks.MARTIAN_STONE, PlanetBlocks.MARTIAN_STONE_STAIRS);

                provider.addStonecutting(PlanetBlocks.MARTIAN_COBBLESTONE, PlanetBlocks.MARTIAN_COBBLESTONE_SLAB);
                provider.addStonecutting(PlanetBlocks.MARTIAN_COBBLESTONE, PlanetBlocks.MARTIAN_COBBLESTONE_STAIRS);
                provider.addStonecutting(PlanetBlocks.MARTIAN_COBBLESTONE, PlanetBlocks.MARTIAN_COBBLESTONE_WALL);

                provider.addStonecutting(PlanetBlocks.MARTIAN_SANDSTONE, PlanetBlocks.CHISELED_MARTIAN_SANDSTONE);
                provider.addStonecutting(PlanetBlocks.MARTIAN_SANDSTONE, PlanetBlocks.MARTIAN_SANDSTONE_PILLAR);
                provider.addStonecutting(PlanetBlocks.MARTIAN_SANDSTONE, PlanetBlocks.MARTIAN_SANDSTONE_BRICKS);
                provider.addStonecutting(PlanetBlocks.MARTIAN_SANDSTONE, PlanetBlocks.MARTIAN_SANDSTONE_SLAB);
                provider.addStonecutting(PlanetBlocks.MARTIAN_SANDSTONE, PlanetBlocks.MARTIAN_SANDSTONE_STAIRS);
                provider.addStonecutting(PlanetBlocks.MARTIAN_SANDSTONE, PlanetBlocks.MARTIAN_SANDSTONE_WALL);

                provider.addStonecutting(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS, PlanetBlocks.CHISELED_MARTIAN_SANDSTONE);
                provider.addStonecutting(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS, PlanetBlocks.MARTIAN_SANDSTONE_BRICK_SLAB);
                provider.addStonecutting(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS, PlanetBlocks.MARTIAN_SANDSTONE_BRICK_STAIRS);
                provider.addStonecutting(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS, PlanetBlocks.MARTIAN_SANDSTONE_BRICK_WALL);

                provider.addStonecutting(PlanetBlocks.MARTIAN_BRICKS, PlanetBlocks.MARTIAN_BRICK_WALL);
                provider.addStonecutting(PlanetBlocks.MARTIAN_BRICKS, PlanetBlocks.MARTIAN_BRICK_STAIRS);
                provider.addStonecutting(PlanetBlocks.MARTIAN_BRICKS, PlanetBlocks.MARTIAN_BRICK_SLAB);

                provider.addStonecutting(PlanetBlocks.SMOOTH_MARTIAN_STONE, PlanetBlocks.SMOOTH_MARTIAN_STONE_SLAB);

                provider.addStonecutting(PlanetBlocks.POLISHED_MARTIAN_STONE, PlanetBlocks.POLISHED_MARTIAN_STONE_SLAB);
                provider.addStonecutting(PlanetBlocks.POLISHED_MARTIAN_STONE, PlanetBlocks.POLISHED_MARTIAN_STONE_STAIRS);

                provider.addStonecutting(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE, PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE_SLAB);
                provider.addStonecutting(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE, PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE_STAIRS);

                // Anorthosite

                provider.addStonecutting(PlanetBlocks.ANORTHOSITE, PlanetBlocks.ANORTHOSITE_BRICKS);
                provider.addStonecutting(PlanetBlocks.ANORTHOSITE, PlanetBlocks.ANORTHOSITE_BRICK_WALL);
                provider.addStonecutting(PlanetBlocks.ANORTHOSITE, PlanetBlocks.ANORTHOSITE_BRICK_STAIRS);
                provider.addStonecutting(PlanetBlocks.ANORTHOSITE, PlanetBlocks.ANORTHOSITE_BRICK_SLAB);
                provider.addStonecutting(PlanetBlocks.ANORTHOSITE, PlanetBlocks.CHISELED_ANORTHOSITE);
                provider.addStonecutting(PlanetBlocks.ANORTHOSITE, PlanetBlocks.ANORTHOSITE_SLAB);
                provider.addStonecutting(PlanetBlocks.ANORTHOSITE, PlanetBlocks.ANORTHOSITE_STAIRS);
                provider.addStonecutting(PlanetBlocks.ANORTHOSITE, PlanetBlocks.ANORTHOSITE_WALL);

                provider.addStonecutting(PlanetBlocks.MOON_SANDSTONE, PlanetBlocks.CHISELED_MOON_SANDSTONE);
                provider.addStonecutting(PlanetBlocks.MOON_SANDSTONE, PlanetBlocks.MOON_SANDSTONE_PILLAR);
                provider.addStonecutting(PlanetBlocks.MOON_SANDSTONE, PlanetBlocks.MOON_SANDSTONE_BRICKS);
                provider.addStonecutting(PlanetBlocks.MOON_SANDSTONE, PlanetBlocks.MOON_SANDSTONE_SLAB);
                provider.addStonecutting(PlanetBlocks.MOON_SANDSTONE, PlanetBlocks.MOON_SANDSTONE_STAIRS);
                provider.addStonecutting(PlanetBlocks.MOON_SANDSTONE, PlanetBlocks.MOON_SANDSTONE_WALL);

                provider.addStonecutting(PlanetBlocks.MOON_SANDSTONE_BRICKS, PlanetBlocks.CHISELED_MOON_SANDSTONE);
                provider.addStonecutting(PlanetBlocks.MOON_SANDSTONE_BRICKS, PlanetBlocks.MOON_SANDSTONE_BRICK_SLAB);
                provider.addStonecutting(PlanetBlocks.MOON_SANDSTONE_BRICKS, PlanetBlocks.MOON_SANDSTONE_BRICK_STAIRS);
                provider.addStonecutting(PlanetBlocks.MOON_SANDSTONE_BRICKS, PlanetBlocks.MOON_SANDSTONE_BRICK_WALL);

                provider.addStonecutting(PlanetBlocks.ANORTHOSITE_BRICKS, PlanetBlocks.ANORTHOSITE_BRICK_WALL);
                provider.addStonecutting(PlanetBlocks.ANORTHOSITE_BRICKS, PlanetBlocks.ANORTHOSITE_BRICK_STAIRS);
                provider.addStonecutting(PlanetBlocks.ANORTHOSITE_BRICKS, PlanetBlocks.ANORTHOSITE_BRICK_SLAB);

                provider.addStonecutting(PlanetBlocks.SMOOTH_ANORTHOSITE, PlanetBlocks.SMOOTH_ANORTHOSITE_SLAB);

                provider.addStonecutting(PlanetBlocks.POLISHED_ANORTHOSITE, PlanetBlocks.POLISHED_ANORTHOSITE_SLAB);
                provider.addStonecutting(PlanetBlocks.POLISHED_ANORTHOSITE, PlanetBlocks.POLISHED_ANORTHOSITE_STAIRS);

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PlanetBlocks.FLAG, 1)
                        .pattern("GBR")
                        .pattern("IWW")
                        .pattern("I  ")
                        .define('G', Items.GOLD_INGOT)
                        .define('I', Items.IRON_INGOT)
                        .define('B', Items.BLUE_WOOL)
                        .define('R', Items.RED_WOOL)
                        .define('W', Items.WHITE_WOOL)
                        .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                        .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .unlockedBy(getHasName(Items.RED_WOOL), has(Items.RED_WOOL))
                        .unlockedBy(getHasName(Items.BLUE_WOOL), has(Items.BLUE_WOOL))
                        .unlockedBy(getHasName(Items.WHITE_WOOL), has(Items.WHITE_WOOL)));

                // tendo count your fucking days

                // anorthosite section
                // polished anorthosite
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.POLISHED_ANORTHOSITE, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', PlanetBlocks.ANORTHOSITE)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE), has(PlanetBlocks.ANORTHOSITE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.POLISHED_ANORTHOSITE_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.POLISHED_ANORTHOSITE)
                        .unlockedBy(getHasName(PlanetBlocks.POLISHED_ANORTHOSITE), has(PlanetBlocks.POLISHED_ANORTHOSITE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.POLISHED_ANORTHOSITE_STAIRS, 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', PlanetBlocks.POLISHED_ANORTHOSITE)
                        .unlockedBy(getHasName(PlanetBlocks.POLISHED_ANORTHOSITE), has(PlanetBlocks.POLISHED_ANORTHOSITE)));
                // smooth anorthosite
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.ANORTHOSITE),
                                RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.SMOOTH_ANORTHOSITE, 0.3f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE), has(PlanetBlocks.ANORTHOSITE)),
                ResourceLocation.fromNamespaceAndPath("ait", "smooth_anorthosite_from_anorthosite_smelted"));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.SMOOTH_ANORTHOSITE_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.SMOOTH_ANORTHOSITE)
                        .unlockedBy(getHasName(PlanetBlocks.SMOOTH_ANORTHOSITE), has(PlanetBlocks.SMOOTH_ANORTHOSITE)));
                //normal anorthosite
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.ANORTHOSITE_WALL, 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', PlanetBlocks.ANORTHOSITE)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE), has(PlanetBlocks.ANORTHOSITE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.ANORTHOSITE_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.ANORTHOSITE)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE), has(PlanetBlocks.ANORTHOSITE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.ANORTHOSITE_STAIRS, 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', PlanetBlocks.ANORTHOSITE)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE), has(PlanetBlocks.ANORTHOSITE)));
                // anorthosite bricks
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.ANORTHOSITE_BRICKS, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', PlanetBlocks.SMOOTH_ANORTHOSITE)
                        .unlockedBy(getHasName(PlanetBlocks.SMOOTH_ANORTHOSITE), has(PlanetBlocks.SMOOTH_ANORTHOSITE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.ANORTHOSITE_BRICK_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.ANORTHOSITE_BRICKS)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_BRICKS), has(PlanetBlocks.ANORTHOSITE_BRICKS)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.ANORTHOSITE_BRICK_STAIRS, 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', PlanetBlocks.ANORTHOSITE_BRICKS)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_BRICKS), has(PlanetBlocks.ANORTHOSITE_BRICKS)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.ANORTHOSITE_BRICK_WALL, 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', PlanetBlocks.ANORTHOSITE_BRICKS)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_BRICKS), has(PlanetBlocks.ANORTHOSITE_BRICKS)));
                //chiseled anorthosite, anorthosite pillar, cracked anorthosite brick
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.CHISELED_ANORTHOSITE, 1)
                        .pattern("#")
                        .pattern("#")
                        .define('#', PlanetBlocks.ANORTHOSITE_SLAB)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_SLAB), has(PlanetBlocks.ANORTHOSITE_SLAB)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.ANORTHOSITE_PILLAR, 1)
                        .pattern("#")
                        .pattern("#")
                        .define('#', PlanetBlocks.SMOOTH_ANORTHOSITE)
                        .unlockedBy(getHasName(PlanetBlocks.SMOOTH_ANORTHOSITE), has(PlanetBlocks.SMOOTH_ANORTHOSITE)));
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.ANORTHOSITE_BRICKS),
                                        RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.CRACKED_ANORTHOSITE_BRICKS, 0.7f, 200)
                                .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_BRICKS), has(PlanetBlocks.ANORTHOSITE_BRICKS)),
                        ResourceLocation.fromNamespaceAndPath("ait", "cracked_anorthosite_bricks_from_anorthosite_bricks_smelted"));

                // anorthosite ores
                //coal
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.ANORTHOSITE_COAL_ORE),
                                RecipeCategory.MISC, Items.COAL, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_COAL_ORE), has(PlanetBlocks.ANORTHOSITE_COAL_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "coal_from_anorthosite_smelted"));

                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.ANORTHOSITE_COAL_ORE),
                                RecipeCategory.MISC, Items.COAL, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_COAL_ORE), has(PlanetBlocks.ANORTHOSITE_COAL_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "coal_from_anorthosite_blasted"));
                //copper
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.ANORTHOSITE_COPPER_ORE),
                                RecipeCategory.MISC, Items.COPPER_INGOT, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_COPPER_ORE), has(PlanetBlocks.ANORTHOSITE_COPPER_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "copper_from_anorthosite_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.ANORTHOSITE_COPPER_ORE),
                                RecipeCategory.MISC, Items.COPPER_INGOT, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_COPPER_ORE), has(PlanetBlocks.ANORTHOSITE_COPPER_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "copper_from_anorthosite_blasted"));
                //iron
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.ANORTHOSITE_IRON_ORE),
                                RecipeCategory.MISC, Items.IRON_INGOT, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_IRON_ORE), has(PlanetBlocks.ANORTHOSITE_IRON_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "iron_from_anorthosite_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.ANORTHOSITE_IRON_ORE),
                                RecipeCategory.MISC, Items.IRON_INGOT, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_IRON_ORE), has(PlanetBlocks.ANORTHOSITE_IRON_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "iron_from_anorthosite_blasted"));
                //gold
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.ANORTHOSITE_GOLD_ORE),
                                RecipeCategory.MISC, Items.GOLD_INGOT, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_GOLD_ORE), has(PlanetBlocks.ANORTHOSITE_GOLD_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "gold_from_anorthosite_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.ANORTHOSITE_GOLD_ORE),
                                RecipeCategory.MISC, Items.GOLD_INGOT, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_GOLD_ORE), has(PlanetBlocks.ANORTHOSITE_GOLD_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "gold_from_anorthosite_blasted"));
                //redstone
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.ANORTHOSITE_REDSTONE_ORE),
                                RecipeCategory.MISC, Items.REDSTONE, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_REDSTONE_ORE), has(PlanetBlocks.ANORTHOSITE_REDSTONE_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "redstone_from_anorthosite_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.ANORTHOSITE_REDSTONE_ORE),
                                RecipeCategory.MISC, Items.REDSTONE, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_REDSTONE_ORE), has(PlanetBlocks.ANORTHOSITE_REDSTONE_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "redstone_from_anorthosite_blasted"));
                //lapis
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.ANORTHOSITE_LAPIS_ORE),
                                RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_LAPIS_ORE), has(PlanetBlocks.ANORTHOSITE_LAPIS_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "lapis_from_anorthosite_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.ANORTHOSITE_LAPIS_ORE),
                                RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_LAPIS_ORE), has(PlanetBlocks.ANORTHOSITE_LAPIS_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "lapis_from_anorthosite_blasted"));
                //diamond
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.ANORTHOSITE_DIAMOND_ORE),
                                RecipeCategory.MISC, Items.DIAMOND, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_DIAMOND_ORE), has(PlanetBlocks.ANORTHOSITE_DIAMOND_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "diamond_from_anorthosite_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.ANORTHOSITE_DIAMOND_ORE),
                                RecipeCategory.MISC, Items.DIAMOND, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_DIAMOND_ORE), has(PlanetBlocks.ANORTHOSITE_DIAMOND_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "diamond_from_anorthosite_blasted"));
                //emerald
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.ANORTHOSITE_EMERALD_ORE),
                                RecipeCategory.MISC, Items.EMERALD, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_EMERALD_ORE), has(PlanetBlocks.ANORTHOSITE_EMERALD_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "emerald_from_anorthosite_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.ANORTHOSITE_EMERALD_ORE),
                                RecipeCategory.MISC, Items.EMERALD, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE_EMERALD_ORE), has(PlanetBlocks.ANORTHOSITE_EMERALD_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "emerald_from_anorthosite_blasted"));

                // moon sandstone section
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MOON_SANDSTONE_WALL, 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', PlanetBlocks.MOON_SANDSTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MOON_SANDSTONE), has(PlanetBlocks.MOON_SANDSTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MOON_SANDSTONE_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.MOON_SANDSTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MOON_SANDSTONE), has(PlanetBlocks.MOON_SANDSTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MOON_SANDSTONE_STAIRS, 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', PlanetBlocks.MOON_SANDSTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MOON_SANDSTONE), has(PlanetBlocks.MOON_SANDSTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.POLISHED_MOON_SANDSTONE, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', PlanetBlocks.MOON_SANDSTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MOON_SANDSTONE), has(PlanetBlocks.MOON_SANDSTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MOON_SANDSTONE_BRICKS, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', PlanetBlocks.POLISHED_MOON_SANDSTONE)
                        .unlockedBy(getHasName(PlanetBlocks.POLISHED_MOON_SANDSTONE), has(PlanetBlocks.POLISHED_MOON_SANDSTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MOON_SANDSTONE_BRICK_WALL, 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', PlanetBlocks.MOON_SANDSTONE_BRICKS)
                        .unlockedBy(getHasName(PlanetBlocks.MOON_SANDSTONE_BRICKS), has(PlanetBlocks.MOON_SANDSTONE_BRICKS)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MOON_SANDSTONE_BRICK_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.MOON_SANDSTONE_BRICKS)
                        .unlockedBy(getHasName(PlanetBlocks.MOON_SANDSTONE_BRICKS), has(PlanetBlocks.MOON_SANDSTONE_BRICKS)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MOON_SANDSTONE_BRICK_STAIRS, 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', PlanetBlocks.MOON_SANDSTONE_BRICKS)
                        .unlockedBy(getHasName(PlanetBlocks.MOON_SANDSTONE_BRICKS), has(PlanetBlocks.MOON_SANDSTONE_BRICKS)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.CHISELED_MOON_SANDSTONE, 1)
                        .pattern("#")
                        .pattern("#")
                        .define('#', PlanetBlocks.MOON_SANDSTONE_SLAB)
                        .unlockedBy(getHasName(PlanetBlocks.MOON_SANDSTONE_SLAB), has(PlanetBlocks.MOON_SANDSTONE_SLAB)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MOON_SANDSTONE_PILLAR, 1)
                        .pattern("#")
                        .pattern("#")
                        .define('#', PlanetBlocks.POLISHED_MOON_SANDSTONE)
                        .unlockedBy(getHasName(PlanetBlocks.POLISHED_MOON_SANDSTONE), has(PlanetBlocks.POLISHED_MOON_SANDSTONE)));
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MOON_SANDSTONE_BRICKS),
                                RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.CRACKED_MOON_SANDSTONE_BRICKS, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MOON_SANDSTONE_BRICKS), has(PlanetBlocks.MOON_SANDSTONE_BRICKS)),
                ResourceLocation.fromNamespaceAndPath("ait", "cracked_moon_sandstone_bricks_from_moon_sandstone_bricks_smelted"));
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MOON_SANDSTONE),
                                RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.CRACKED_MOON_SANDSTONE, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MOON_SANDSTONE), has(PlanetBlocks.MOON_SANDSTONE)),
                ResourceLocation.fromNamespaceAndPath("ait", "cracked_moon_sandstone_from_moon_sandstone_smelted"));

                // martian section

                // martian ores
                //coal
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_COAL_ORE),
                                RecipeCategory.MISC, Items.COAL, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COAL_ORE), has(PlanetBlocks.MARTIAN_COAL_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "coal_from_martian_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.MARTIAN_COAL_ORE),
                                RecipeCategory.MISC, Items.COAL, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COAL_ORE), has(PlanetBlocks.MARTIAN_COAL_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "coal_from_martian_blasted"));
                //copper
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_COPPER_ORE),
                                RecipeCategory.MISC, Items.COPPER_INGOT, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COPPER_ORE), has(PlanetBlocks.MARTIAN_COPPER_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "copper_from_martian_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.MARTIAN_COPPER_ORE),
                                RecipeCategory.MISC, Items.COPPER_INGOT, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COPPER_ORE), has(PlanetBlocks.MARTIAN_COPPER_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "copper_from_martian_blasted"));
                //iron
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_IRON_ORE),
                                RecipeCategory.MISC, Items.IRON_INGOT, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_IRON_ORE), has(PlanetBlocks.MARTIAN_IRON_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "iron_from_martian_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.MARTIAN_IRON_ORE),
                                RecipeCategory.MISC, Items.IRON_INGOT, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_IRON_ORE), has(PlanetBlocks.MARTIAN_IRON_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "iron_from_martian_blasted"));
                //gold
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_GOLD_ORE),
                                RecipeCategory.MISC, Items.GOLD_INGOT, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_GOLD_ORE), has(PlanetBlocks.MARTIAN_GOLD_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "gold_from_martian_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.MARTIAN_GOLD_ORE),
                                RecipeCategory.MISC, Items.GOLD_INGOT, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_GOLD_ORE), has(PlanetBlocks.MARTIAN_GOLD_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "gold_from_martian_blasted"));
                //redstone
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_REDSTONE_ORE),
                                RecipeCategory.MISC, Items.REDSTONE, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_REDSTONE_ORE), has(PlanetBlocks.MARTIAN_REDSTONE_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "redstone_from_martian_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.MARTIAN_REDSTONE_ORE),
                                RecipeCategory.MISC, Items.REDSTONE, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_REDSTONE_ORE), has(PlanetBlocks.MARTIAN_REDSTONE_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "redstone_from_martian_blasted"));
                //lapis
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_LAPIS_ORE),
                                RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_LAPIS_ORE), has(PlanetBlocks.MARTIAN_LAPIS_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "lapis_from_martian_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.MARTIAN_LAPIS_ORE),
                                RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_LAPIS_ORE), has(PlanetBlocks.MARTIAN_LAPIS_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "lapis_from_martian_blasted"));
                //diamond
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_DIAMOND_ORE),
                                RecipeCategory.MISC, Items.DIAMOND, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_DIAMOND_ORE), has(PlanetBlocks.MARTIAN_DIAMOND_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "diamond_from_martian_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.MARTIAN_DIAMOND_ORE),
                                RecipeCategory.MISC, Items.DIAMOND, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_DIAMOND_ORE), has(PlanetBlocks.MARTIAN_DIAMOND_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "diamond_from_martian_blasted"));
                //emerald
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_EMERALD_ORE),
                                RecipeCategory.MISC, Items.EMERALD, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_EMERALD_ORE), has(PlanetBlocks.MARTIAN_EMERALD_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "emerald_from_martian_smelted"));
                provider.addBlastFurnaceRecipe(SimpleCookingRecipeBuilder.blasting(Ingredient.of(PlanetBlocks.MARTIAN_EMERALD_ORE),
                                RecipeCategory.MISC, Items.EMERALD, 0.7f, 100)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_EMERALD_ORE), has(PlanetBlocks.MARTIAN_EMERALD_ORE)),
                ResourceLocation.fromNamespaceAndPath("ait", "emerald_from_martian_blasted"));

                // martian stones
                provider.addShapelessRecipe(ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, PlanetBlocks.MARTIAN_STONE_BUTTON, 1)
                        .requires(PlanetBlocks.MARTIAN_STONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_STONE), has(PlanetBlocks.MARTIAN_STONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_STONE_PRESSURE_PLATE, 1)
                        .pattern("##")
                        .define('#', PlanetBlocks.MARTIAN_STONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_STONE), has(PlanetBlocks.MARTIAN_STONE)));
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_COBBLESTONE),
                                RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_STONE, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COBBLESTONE), has(PlanetBlocks.MARTIAN_COBBLESTONE)),
                ResourceLocation.fromNamespaceAndPath("ait", "martian_stone_from_martian_cobblestone_smelted"));
                // martian cobblestones
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_COBBLESTONE_WALL, 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', PlanetBlocks.MARTIAN_COBBLESTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COBBLESTONE), has(PlanetBlocks.MARTIAN_COBBLESTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_COBBLESTONE_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.MARTIAN_COBBLESTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COBBLESTONE), has(PlanetBlocks.MARTIAN_COBBLESTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_COBBLESTONE_STAIRS, 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', PlanetBlocks.MARTIAN_COBBLESTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COBBLESTONE), has(PlanetBlocks.MARTIAN_COBBLESTONE)));
                // polished martian stone
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.POLISHED_MARTIAN_STONE, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', PlanetBlocks.MARTIAN_STONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_STONE), has(PlanetBlocks.MARTIAN_STONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.POLISHED_MARTIAN_STONE_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.POLISHED_MARTIAN_STONE)
                        .unlockedBy(getHasName(PlanetBlocks.POLISHED_MARTIAN_STONE), has(PlanetBlocks.POLISHED_MARTIAN_STONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.POLISHED_MARTIAN_STONE_STAIRS, 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', PlanetBlocks.POLISHED_MARTIAN_STONE)
                        .unlockedBy(getHasName(PlanetBlocks.POLISHED_MARTIAN_STONE), has(PlanetBlocks.POLISHED_MARTIAN_STONE)));
                // smooth martian stone
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_STONE),
                                RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.SMOOTH_MARTIAN_STONE, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_STONE), has(PlanetBlocks.MARTIAN_STONE)),
                ResourceLocation.fromNamespaceAndPath("ait", "smooth_martian_stone_from_martian_stone_smelted"));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.SMOOTH_MARTIAN_STONE_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.SMOOTH_MARTIAN_STONE)
                        .unlockedBy(getHasName(PlanetBlocks.SMOOTH_MARTIAN_STONE), has(PlanetBlocks.SMOOTH_MARTIAN_STONE)));
                // martian sandstone
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_SANDSTONE, 1)
                        .pattern("##")
                        .pattern("##")
                        .define('#', PlanetBlocks.MARTIAN_SAND)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_SAND), has(PlanetBlocks.MARTIAN_SAND)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.POLISHED_MARTIAN_SANDSTONE, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', PlanetBlocks.MARTIAN_SANDSTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_SANDSTONE), has(PlanetBlocks.MARTIAN_SANDSTONE)));
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_SANDSTONE),
                                RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.CRACKED_MARTIAN_SANDSTONE, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_SANDSTONE), has(PlanetBlocks.MARTIAN_SANDSTONE)),
                ResourceLocation.fromNamespaceAndPath("ait", "cracked_martian_sandstone_from_martian_sandstone_smelted"));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_SANDSTONE_WALL, 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', PlanetBlocks.MARTIAN_SANDSTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_SANDSTONE), has(PlanetBlocks.MARTIAN_SANDSTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_SANDSTONE_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.MARTIAN_SANDSTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_SANDSTONE), has(PlanetBlocks.MARTIAN_SANDSTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_SANDSTONE_STAIRS, 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', PlanetBlocks.MARTIAN_SANDSTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_SANDSTONE), has(PlanetBlocks.MARTIAN_SANDSTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_SANDSTONE_PILLAR, 1)
                        .pattern("#")
                        .pattern("#")
                        .define('#', PlanetBlocks.POLISHED_MARTIAN_SANDSTONE)
                        .unlockedBy(getHasName(PlanetBlocks.POLISHED_MARTIAN_SANDSTONE), has(PlanetBlocks.POLISHED_MARTIAN_SANDSTONE)));
                // martian sandstone bricks, martian sandstone brick wall, slab, stairs, cracked, chiseled
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_SANDSTONE_BRICKS, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', PlanetBlocks.POLISHED_MARTIAN_SANDSTONE)
                        .unlockedBy(getHasName(PlanetBlocks.POLISHED_MARTIAN_SANDSTONE), has(PlanetBlocks.POLISHED_MARTIAN_SANDSTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_SANDSTONE_BRICK_WALL, 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', PlanetBlocks.MARTIAN_SANDSTONE_BRICKS)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS), has(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_SANDSTONE_BRICK_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.MARTIAN_SANDSTONE_BRICKS)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS), has(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_SANDSTONE_BRICK_STAIRS, 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', PlanetBlocks.MARTIAN_SANDSTONE_BRICKS)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS), has(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS)));
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS),
                                RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.CRACKED_MARTIAN_SANDSTONE_BRICKS, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS), has(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS)),
                ResourceLocation.fromNamespaceAndPath("ait", "cracked_martian_sandstone_bricks_from_martian_sandstone_bricks_smelted"));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.CHISELED_MARTIAN_SANDSTONE, 1)
                        .pattern("#")
                        .pattern("#")
                        .define('#', PlanetBlocks.MARTIAN_SANDSTONE_SLAB)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_SANDSTONE_SLAB), has(PlanetBlocks.MARTIAN_SANDSTONE_SLAB)));
                // martian bricks
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_BRICKS, 4)
                        .pattern("##")
                        .pattern("##")
                        .define('#', PlanetBlocks.SMOOTH_MARTIAN_STONE)
                        .unlockedBy(getHasName(PlanetBlocks.SMOOTH_MARTIAN_STONE), has(PlanetBlocks.SMOOTH_MARTIAN_STONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_BRICK_WALL, 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', PlanetBlocks.MARTIAN_BRICKS)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_BRICKS), has(PlanetBlocks.MARTIAN_BRICKS)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_BRICK_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.MARTIAN_BRICKS)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_BRICKS), has(PlanetBlocks.MARTIAN_BRICKS)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_BRICK_STAIRS, 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', PlanetBlocks.MARTIAN_BRICKS)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_BRICKS), has(PlanetBlocks.MARTIAN_BRICKS)));
                provider.addFurnaceRecipe(SimpleCookingRecipeBuilder.smelting(Ingredient.of(PlanetBlocks.MARTIAN_BRICKS),
                                RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.CRACKED_MARTIAN_BRICKS, 0.7f, 200)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_BRICKS), has(PlanetBlocks.MARTIAN_BRICKS)),
                ResourceLocation.fromNamespaceAndPath("ait", "cracked_martian_bricks_from_martian_bricks_smelted"));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_PILLAR, 1)
                        .pattern("#")
                        .pattern("#")
                        .define('#', PlanetBlocks.POLISHED_MARTIAN_STONE)
                        .unlockedBy(getHasName(PlanetBlocks.POLISHED_MARTIAN_STONE), has(PlanetBlocks.POLISHED_MARTIAN_STONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.CHISELED_MARTIAN_STONE, 1)
                        .pattern("#")
                        .pattern("#")
                        .define('#', PlanetBlocks.MARTIAN_BRICK_SLAB)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_BRICK_SLAB), has(PlanetBlocks.MARTIAN_BRICK_SLAB)));
                // mossy martian cobblestone
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE, 1)
                        .pattern("#M")
                        .define('#', PlanetBlocks.MARTIAN_COBBLESTONE)
                        .define('M', Ingredient.of(Items.MOSS_BLOCK, Items.VINE))
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COBBLESTONE), has(PlanetBlocks.MARTIAN_COBBLESTONE))
                        .unlockedBy(getHasName(Items.MOSS_BLOCK), has(Items.MOSS_BLOCK))
                        .unlockedBy(getHasName(Items.VINE), has(Items.VINE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE_WALL, 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE), has(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE_SLAB, 6)
                        .pattern("###")
                        .define('#', PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE), has(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE)));
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE_STAIRS, 4)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .define('#', PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE)
                        .unlockedBy(getHasName(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE), has(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE)));


                // Spacesuits
                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PlanetItems.SPACESUIT_BOOTS, 1)
                        .pattern("   ")
                        .pattern("F F")
                        .pattern("FBF")
                        .define('F', PlanetItems.FABRIC)
                        .define('B', Items.IRON_BOOTS)
                        .unlockedBy(getHasName(PlanetItems.FABRIC), has(PlanetItems.FABRIC))
                        .unlockedBy(getHasName(Items.IRON_BOOTS), has(Items.IRON_BOOTS)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PlanetItems.SPACESUIT_LEGGINGS, 1)
                        .pattern("FLF")
                        .pattern("F F")
                        .pattern("F F")
                        .define('F', PlanetItems.FABRIC)
                        .define('L', Items.IRON_LEGGINGS)
                        .unlockedBy(getHasName(PlanetItems.FABRIC), has(PlanetItems.FABRIC))
                        .unlockedBy(getHasName(Items.IRON_LEGGINGS), has(Items.IRON_LEGGINGS)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PlanetItems.SPACESUIT_CHESTPLATE, 1)
                        .pattern("F F")
                        .pattern("FCF")
                        .pattern("FBF")
                        .define('F', PlanetItems.FABRIC)
                        .define('B', Items.BUCKET)
                        .define('C', Items.IRON_CHESTPLATE)
                        .unlockedBy(getHasName(PlanetItems.FABRIC), has(PlanetItems.FABRIC))
                        .unlockedBy(getHasName(Items.BUCKET), has(Items.BUCKET))
                        .unlockedBy(getHasName(Items.IRON_CHESTPLATE), has(Items.IRON_CHESTPLATE)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PlanetItems.SPACESUIT_HELMET, 1)
                        .pattern("FHF")
                        .pattern("FGF")
                        .pattern("FFF")
                        .define('F', PlanetItems.FABRIC)
                        .define('H', Items.IRON_HELMET)
                        .define('G', Items.YELLOW_STAINED_GLASS_PANE)
                        .unlockedBy(getHasName(PlanetItems.FABRIC), has(PlanetItems.FABRIC))
                        .unlockedBy(getHasName(Items.IRON_HELMET), has(Items.IRON_HELMET))
                        .unlockedBy(getHasName(Items.YELLOW_STAINED_GLASS_PANE), has(Items.YELLOW_STAINED_GLASS_PANE)));

                // Martian Tools

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, PlanetItems.MARTIAN_STONE_PICKAXE, 1)
                        .pattern("MMM")
                        .pattern(" S ")
                        .pattern(" S ")
                        .define('M', PlanetBlocks.MARTIAN_COBBLESTONE)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COBBLESTONE), has(PlanetBlocks.MARTIAN_COBBLESTONE))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, PlanetItems.MARTIAN_STONE_SWORD, 1)
                        .pattern(" M ")
                        .pattern(" M ")
                        .pattern(" S ")
                        .define('M', PlanetBlocks.MARTIAN_COBBLESTONE)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COBBLESTONE), has(PlanetBlocks.MARTIAN_COBBLESTONE))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, PlanetItems.MARTIAN_STONE_SHOVEL, 1)
                        .pattern(" M ")
                        .pattern(" S ")
                        .pattern(" S ")
                        .define('M', PlanetBlocks.MARTIAN_COBBLESTONE)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COBBLESTONE), has(PlanetBlocks.MARTIAN_COBBLESTONE))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, PlanetItems.MARTIAN_STONE_HOE, 1)
                        .pattern(" MM")
                        .pattern(" S ")
                        .pattern(" S ")
                        .define('M', PlanetBlocks.MARTIAN_COBBLESTONE)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COBBLESTONE), has(PlanetBlocks.MARTIAN_COBBLESTONE))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, PlanetItems.MARTIAN_STONE_AXE, 1)
                        .pattern(" MM")
                        .pattern(" SM")
                        .pattern(" S ")
                        .define('M', PlanetBlocks.MARTIAN_COBBLESTONE)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_COBBLESTONE), has(PlanetBlocks.MARTIAN_COBBLESTONE))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK)));

                // Anorthosite Tools

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, PlanetItems.ANORTHOSITE_PICKAXE, 1)
                        .pattern("MMM")
                        .pattern(" S ")
                        .pattern(" S ")
                        .define('M', PlanetBlocks.ANORTHOSITE)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE), has(PlanetBlocks.ANORTHOSITE))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, PlanetItems.ANORTHOSITE_SWORD, 1)
                        .pattern(" M ")
                        .pattern(" M ")
                        .pattern(" S ")
                        .define('M', PlanetBlocks.ANORTHOSITE)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE), has(PlanetBlocks.ANORTHOSITE))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, PlanetItems.ANORTHOSITE_SHOVEL, 1)
                        .pattern(" M ")
                        .pattern(" S ")
                        .pattern(" S ")
                        .define('M', PlanetBlocks.ANORTHOSITE)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE), has(PlanetBlocks.ANORTHOSITE))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, PlanetItems.ANORTHOSITE_HOE, 1)
                        .pattern(" MM")
                        .pattern(" S ")
                        .pattern(" S ")
                        .define('M', PlanetBlocks.ANORTHOSITE)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE), has(PlanetBlocks.ANORTHOSITE))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, PlanetItems.ANORTHOSITE_AXE, 1)
                        .pattern(" MM")
                        .pattern(" SM")
                        .pattern(" S ")
                        .define('M', PlanetBlocks.ANORTHOSITE)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(PlanetBlocks.ANORTHOSITE), has(PlanetBlocks.ANORTHOSITE))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK)));

                // Martian Stone

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_STONE_WALL, 1)
                        .pattern("   ")
                        .pattern("SSS")
                        .pattern("SSS")
                        .define('S', PlanetBlocks.MARTIAN_STONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_STONE), has(PlanetBlocks.MARTIAN_STONE)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_STONE_STAIRS, 1)
                        .pattern("S  ")
                        .pattern("SS ")
                        .pattern("SSS")
                        .define('S', PlanetBlocks.MARTIAN_STONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_STONE), has(PlanetBlocks.MARTIAN_STONE)));

                provider.addShapedRecipe(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PlanetBlocks.MARTIAN_STONE_SLAB, 1)
                        .pattern("   ")
                        .pattern("   ")
                        .pattern("SSS")
                        .define('S', PlanetBlocks.MARTIAN_STONE)
                        .unlockedBy(getHasName(PlanetBlocks.MARTIAN_STONE), has(PlanetBlocks.MARTIAN_STONE)));



            }


            @Override
            public void blockTags(AITBlockTagProvider provider) {
                // Martian Blocks
                provider.tag(BlockTags.WALLS)
                        .add(PlanetBlocks.MARTIAN_BRICK_WALL).add(PlanetBlocks.MARTIAN_COBBLESTONE_WALL).add(PlanetBlocks.MARTIAN_SANDSTONE_WALL).add(PlanetBlocks.MARTIAN_STONE_WALL).add(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE_WALL).add(PlanetBlocks.MARTIAN_BRICK_WALL).add(PlanetBlocks.MARTIAN_SANDSTONE_BRICK_WALL);

                // Anorthosite Blocks
                provider.tag(BlockTags.WALLS)
                        .add(PlanetBlocks.ANORTHOSITE_BRICK_WALL).add(PlanetBlocks.ANORTHOSITE_WALL).add(PlanetBlocks.MOON_SANDSTONE_BRICK_WALL).add(PlanetBlocks.MOON_SANDSTONE_WALL);

            }

            @Override
            public void itemTags(AITItemTagProvider provider) {
                provider.tag(ItemTags.TRIMMABLE_ARMOR).add(PlanetItems.SPACESUIT_BOOTS).add(PlanetItems.SPACESUIT_LEGGINGS).add(PlanetItems.SPACESUIT_CHESTPLATE).add(PlanetItems.SPACESUIT_HELMET);

                provider.tag(ItemTags.ARMOR_ENCHANTABLE).add(PlanetItems.SPACESUIT_BOOTS, PlanetItems.SPACESUIT_LEGGINGS, PlanetItems.SPACESUIT_CHESTPLATE, PlanetItems.SPACESUIT_HELMET);
                provider.tag(ItemTags.EQUIPPABLE_ENCHANTABLE).add(PlanetItems.SPACESUIT_BOOTS, PlanetItems.SPACESUIT_LEGGINGS, PlanetItems.SPACESUIT_CHESTPLATE, PlanetItems.SPACESUIT_HELMET);
                provider.tag(ItemTags.HEAD_ARMOR_ENCHANTABLE).add(PlanetItems.SPACESUIT_HELMET);
                provider.tag(ItemTags.CHEST_ARMOR_ENCHANTABLE).add(PlanetItems.SPACESUIT_CHESTPLATE);
                provider.tag(ItemTags.LEG_ARMOR_ENCHANTABLE).add(PlanetItems.SPACESUIT_LEGGINGS);
                provider.tag(ItemTags.FOOT_ARMOR_ENCHANTABLE).add(PlanetItems.SPACESUIT_BOOTS);
                provider.tag(ItemTags.SWORD_ENCHANTABLE).add(PlanetItems.MARTIAN_STONE_SWORD);
                provider.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(PlanetItems.MARTIAN_STONE_SWORD, PlanetItems.MARTIAN_STONE_AXE, PlanetItems.ANORTHOSITE_AXE);
                provider.tag(ItemTags.MINING_ENCHANTABLE).add(PlanetItems.MARTIAN_STONE_SHOVEL, PlanetItems.MARTIAN_STONE_PICKAXE, PlanetItems.MARTIAN_STONE_AXE, PlanetItems.MARTIAN_STONE_HOE,
                        PlanetItems.ANORTHOSITE_SHOVEL, PlanetItems.ANORTHOSITE_PICKAXE, PlanetItems.ANORTHOSITE_AXE, PlanetItems.ANORTHOSITE_HOE);
                provider.tag(ItemTags.MINING_LOOT_ENCHANTABLE).add(PlanetItems.MARTIAN_STONE_SHOVEL, PlanetItems.MARTIAN_STONE_PICKAXE, PlanetItems.MARTIAN_STONE_AXE, PlanetItems.MARTIAN_STONE_HOE,
                        PlanetItems.ANORTHOSITE_SHOVEL, PlanetItems.ANORTHOSITE_PICKAXE, PlanetItems.ANORTHOSITE_AXE, PlanetItems.ANORTHOSITE_HOE);
                provider.tag(ItemTags.DURABILITY_ENCHANTABLE).add(PlanetItems.SPACESUIT_BOOTS, PlanetItems.SPACESUIT_LEGGINGS, PlanetItems.SPACESUIT_CHESTPLATE, PlanetItems.SPACESUIT_HELMET,
                        PlanetItems.MARTIAN_STONE_SWORD, PlanetItems.MARTIAN_STONE_SHOVEL, PlanetItems.MARTIAN_STONE_PICKAXE, PlanetItems.MARTIAN_STONE_AXE, PlanetItems.MARTIAN_STONE_HOE,
                        PlanetItems.ANORTHOSITE_SWORD, PlanetItems.ANORTHOSITE_SHOVEL, PlanetItems.ANORTHOSITE_PICKAXE, PlanetItems.ANORTHOSITE_AXE, PlanetItems.ANORTHOSITE_HOE);
            }


            @Override
            public void generateItemModels(AmbleModelProvider provider, ItemModelGenerators generator) {
                generator.generateArmorTrims((ArmorItem) PlanetItems.SPACESUIT_BOOTS);
                generator.generateArmorTrims((ArmorItem) PlanetItems.SPACESUIT_CHESTPLATE);
                generator.generateArmorTrims((ArmorItem) PlanetItems.SPACESUIT_LEGGINGS);
                generator.generateArmorTrims((ArmorItem) PlanetItems.SPACESUIT_HELMET);

                generator.generateFlatItem(PlanetItems.MARTIAN_STONE_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
                generator.generateFlatItem(PlanetItems.MARTIAN_STONE_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
                generator.generateFlatItem(PlanetItems.MARTIAN_STONE_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
                generator.generateFlatItem(PlanetItems.MARTIAN_STONE_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
                generator.generateFlatItem(PlanetItems.MARTIAN_STONE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);

                generator.generateFlatItem(PlanetItems.ANORTHOSITE_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
                generator.generateFlatItem(PlanetItems.ANORTHOSITE_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
                generator.generateFlatItem(PlanetItems.ANORTHOSITE_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
                generator.generateFlatItem(PlanetItems.ANORTHOSITE_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
                generator.generateFlatItem(PlanetItems.ANORTHOSITE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
            }

            @Override
            public void models(AmbleModelProvider provider, BlockModelGenerators generator) {
                //Martian (Slabs, Walls, etc.)
                BlockModelGenerators.BlockFamilyProvider martian_stone_pool = generator.family(PlanetBlocks.MARTIAN_STONE);
                martian_stone_pool.stairs(PlanetBlocks.MARTIAN_STONE_STAIRS);
                martian_stone_pool.wall(PlanetBlocks.MARTIAN_STONE_WALL);
                martian_stone_pool.slab(PlanetBlocks.MARTIAN_STONE_SLAB);
                martian_stone_pool.button(PlanetBlocks.MARTIAN_STONE_BUTTON);
                martian_stone_pool.pressurePlate(PlanetBlocks.MARTIAN_STONE_PRESSURE_PLATE);

                BlockModelGenerators.BlockFamilyProvider martian_bricks_pool = generator.family(PlanetBlocks.MARTIAN_BRICKS);
                martian_bricks_pool.stairs(PlanetBlocks.MARTIAN_BRICK_STAIRS);
                martian_bricks_pool.wall(PlanetBlocks.MARTIAN_BRICK_WALL);
                martian_bricks_pool.slab(PlanetBlocks.MARTIAN_BRICK_SLAB);

                BlockModelGenerators.BlockFamilyProvider martian_cobblestone_pool = generator.family(PlanetBlocks.MARTIAN_COBBLESTONE);
                martian_cobblestone_pool.stairs(PlanetBlocks.MARTIAN_COBBLESTONE_STAIRS);
                martian_cobblestone_pool.wall(PlanetBlocks.MARTIAN_COBBLESTONE_WALL);
                martian_cobblestone_pool.slab(PlanetBlocks.MARTIAN_COBBLESTONE_SLAB);


                BlockModelGenerators.BlockFamilyProvider mossy_martian_cobblestone_pool = generator.family(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE);
                mossy_martian_cobblestone_pool.stairs(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE_STAIRS);
                mossy_martian_cobblestone_pool.wall(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE_WALL);
                mossy_martian_cobblestone_pool.slab(PlanetBlocks.MOSSY_MARTIAN_COBBLESTONE_SLAB);

                BlockModelGenerators.BlockFamilyProvider martian_sandstone_pool = generator.family(PlanetBlocks.MARTIAN_SANDSTONE);
                martian_sandstone_pool.stairs(PlanetBlocks.MARTIAN_SANDSTONE_STAIRS);
                martian_sandstone_pool.wall(PlanetBlocks.MARTIAN_SANDSTONE_WALL);
                martian_sandstone_pool.slab(PlanetBlocks.MARTIAN_SANDSTONE_SLAB);

                BlockModelGenerators.BlockFamilyProvider martian_sandstone_bricks_pool = generator.family(PlanetBlocks.MARTIAN_SANDSTONE_BRICKS);
                martian_sandstone_bricks_pool.stairs(PlanetBlocks.MARTIAN_SANDSTONE_BRICK_STAIRS);
                martian_sandstone_bricks_pool.wall(PlanetBlocks.MARTIAN_SANDSTONE_BRICK_WALL);
                martian_sandstone_bricks_pool.slab(PlanetBlocks.MARTIAN_SANDSTONE_BRICK_SLAB);

                BlockModelGenerators.BlockFamilyProvider smooth_martian_stone_pool = generator.family(PlanetBlocks.SMOOTH_MARTIAN_STONE);
                smooth_martian_stone_pool.slab(PlanetBlocks.SMOOTH_MARTIAN_STONE_SLAB);

                BlockModelGenerators.BlockFamilyProvider polished_martian_stone_pool = generator.family(PlanetBlocks.POLISHED_MARTIAN_STONE);
                polished_martian_stone_pool.stairs(PlanetBlocks.POLISHED_MARTIAN_STONE_STAIRS);
                polished_martian_stone_pool.slab(PlanetBlocks.POLISHED_MARTIAN_STONE_SLAB);


                //Anorthosite (Slabs, Walls, etc.)

                BlockModelGenerators.BlockFamilyProvider anorthosite_pool = generator.family(PlanetBlocks.ANORTHOSITE);
                anorthosite_pool.stairs(PlanetBlocks.ANORTHOSITE_STAIRS);
                anorthosite_pool.wall(PlanetBlocks.ANORTHOSITE_WALL);
                anorthosite_pool.slab(PlanetBlocks.ANORTHOSITE_SLAB);

                BlockModelGenerators.BlockFamilyProvider anorthosite_bricks_pool = generator.family(PlanetBlocks.ANORTHOSITE_BRICKS);
                anorthosite_bricks_pool.stairs(PlanetBlocks.ANORTHOSITE_BRICK_STAIRS);
                anorthosite_bricks_pool.wall(PlanetBlocks.ANORTHOSITE_BRICK_WALL);
                anorthosite_bricks_pool.slab(PlanetBlocks.ANORTHOSITE_BRICK_SLAB);

                BlockModelGenerators.BlockFamilyProvider smooth_anorthosite_stone_pool = generator.family(PlanetBlocks.SMOOTH_ANORTHOSITE);
                smooth_anorthosite_stone_pool.slab(PlanetBlocks.SMOOTH_ANORTHOSITE_SLAB);

                BlockModelGenerators.BlockFamilyProvider polished_anorthosite_stone_pool = generator.family(PlanetBlocks.POLISHED_ANORTHOSITE);
                polished_anorthosite_stone_pool.stairs(PlanetBlocks.POLISHED_ANORTHOSITE_STAIRS);
                polished_anorthosite_stone_pool.slab(PlanetBlocks.POLISHED_ANORTHOSITE_SLAB);

                BlockModelGenerators.BlockFamilyProvider moon_sandstone_pool = generator.family(PlanetBlocks.MOON_SANDSTONE);
                moon_sandstone_pool.stairs(PlanetBlocks.MOON_SANDSTONE_STAIRS);
                moon_sandstone_pool.wall(PlanetBlocks.MOON_SANDSTONE_WALL);
                moon_sandstone_pool.slab(PlanetBlocks.MOON_SANDSTONE_SLAB);

                BlockModelGenerators.BlockFamilyProvider moon_sandstone_bricks_pool = generator.family(PlanetBlocks.MOON_SANDSTONE_BRICKS);
                moon_sandstone_bricks_pool.stairs(PlanetBlocks.MOON_SANDSTONE_BRICK_STAIRS);
                moon_sandstone_bricks_pool.wall(PlanetBlocks.MOON_SANDSTONE_BRICK_WALL);
                moon_sandstone_bricks_pool.slab(PlanetBlocks.MOON_SANDSTONE_BRICK_SLAB);
            }

            @Override
            public void advancements(Consumer<AdvancementHolder> consumer) {
                AdvancementHolder root = Advancement.Builder.advancement()
                        .display(
                                PlanetItems.SPACESUIT_HELMET,
                                Component.translatable("achievements.ait.title.planet_root"),
                                Component.translatable("achievements.ait.description.planet_root"),
                                AITMod.id("textures/block/martian_stone.png"),
                                AdvancementType.TASK,
                                false,
                                false,
                                false
                        )
                        .addCriterion("enter_tardis", TardisCriterions.ENTER_TARDIS.conditions())
                        .save(consumer, AITMod.MOD_ID + "/planet_root");
                AdvancementHolder landOnMars = Advancement.Builder.advancement()
                        .parent(root)
                        .display(
                                PlanetBlocks.MARTIAN_STONE,
                                Component.translatable("achievements.ait.title.enter_mars"),
                                Component.translatable("achievements.ait.description.enter_mars"),
                                null,
                                AdvancementType.TASK,
                                true,
                                true,
                                true
                        )
                        .addCriterion(
                                "enter_mars",
                                ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(
                                        ResourceKey.create(
                                                Registries.DIMENSION,
                                                AITMod.id("mars")
                                        )
                                )
                        )
                        .save(consumer, AITMod.MOD_ID + "/enter_mars");
                AdvancementHolder landOnMoon = Advancement.Builder.advancement()
                        .parent(root)
                        .display(
                                PlanetBlocks.ANORTHOSITE,
                                Component.translatable("achievements.ait.title.enter_moon"),
                                Component.translatable("achievements.ait.description.enter_moon"),
                                null,
                                AdvancementType.TASK,
                                true,
                                true,
                                true
                        )
                        .addCriterion(
                                "enter_moon",
                                ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(
                                        ResourceKey.create(
                                                Registries.DIMENSION,
                                                AITMod.id("moon")
                                        )
                                )
                        )
                        .save(consumer, AITMod.MOD_ID + "/enter_moon");

                // todo - idk how to do this
                // Advancement findStructure = Advancement.Builder.create().parent(root).display(Blocks.REDSTONE_BLOCK, Text.translatable("advancements.ait.find_planet_structure.title"), Text.translatable("advancements.ait.find_planet_structure.description"), null, AdvancementFrame.CHALLENGE, true, true, true).criterion("planet_structure", TickCriterion.Conditions.createLocation(LocationPredicate.feature(RegistryKey.of(RegistryKeys.STRUCTURE, AITMod.id("cult_structures_overworld"))))).build(consumer, AITMod.MOD_ID + "/find_planet_structure");
            }
        });
    }

    public static PlanetModule instance() {
        return INSTANCE;
    }
}
