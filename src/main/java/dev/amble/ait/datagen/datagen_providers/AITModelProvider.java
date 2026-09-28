package dev.amble.ait.datagen.datagen_providers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.module.ModuleRegistry;
import dev.amble.lib.datagen.model.AmbleModelProvider;
import dev.amble.lib.platform.datagen.PlatformDataOutput;
import net.minecraft.core.Direction;
import net.minecraft.data.models.*;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.*;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class AITModelProvider extends AmbleModelProvider {
    private final List<Block> directionalBlocksToRegister = new ArrayList<>();
    private final List<Block> simpleBlocksToRegister = new ArrayList<>();
    private final List<Tuple<Block, Block>> coralFanBlocksToRegister = new ArrayList<>();
    private final List<Block> pillarBlocksToRegister = new ArrayList<>();

    public AITModelProvider(PlatformDataOutput output) {
        super(output);
    }

    private static ModelTemplate item(String modid, String parent, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(Optional.of(ResourceLocation.fromNamespaceAndPath(modid, "item/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    private static ModelTemplate item(String parent, TextureSlot... requiredTextureKeys) {
        return item(AITMod.MOD_ID, parent, requiredTextureKeys);
    }

    private static ModelTemplate item(TextureSlot... requiredTextureKeys) {
        return item("minecraft", "generated", requiredTextureKeys);
    }

    private static ModelTemplate item(String name) {
        return item(name, TextureSlot.LAYER0);
    }

    private static String getItemName(Item item) {
        return item.getDescriptionId().split("\\.")[2];
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        for (Block block : directionalBlocksToRegister) {
            // Identifier identifier = new
            // Identifier(block.getTranslationKey().split("\\.")[1]);
            generator.blockStateOutput.accept(MultiPartGenerator.multiPart(block).with(
                    Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH),
                    Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R0)));
        }
        for (Block block : simpleBlocksToRegister) {
            generator.createTrivialCube(block);
        }

        for (Tuple<Block, Block> pair : coralFanBlocksToRegister) {
            generator.createCoralFans(pair.getA(), pair.getB());
        }

        for (Block block : pillarBlocksToRegister) {
            generator.createCraftingTableLike(block, block, (a, b) -> TextureMapping.cubeTop(block));
        }

        ModuleRegistry.instance().iterator().forEachRemaining(module -> {
            module.getDataGenerator().ifPresent(data -> data.models(this, generator));
            module.getBlockRegistry().ifPresent(this::withBlocks);
        });

        BlockModelGenerators.BlockFamilyProvider tardis_coral_pool = generator.family(AITBlocks.TARDIS_CORAL_BLOCK);
        tardis_coral_pool.stairs(AITBlocks.TARDIS_CORAL_STAIRS);
        tardis_coral_pool.slab(AITBlocks.TARDIS_CORAL_SLAB);
        tardis_coral_pool.wall(AITBlocks.TARDIS_CORAL_WALL);
        tardis_coral_pool.fence(AITBlocks.TARDIS_CORAL_FENCE);


        super.generateBlockStateModels(generator);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        ModuleRegistry.instance().iterator().forEachRemaining(module -> {
            module.getItemRegistry().ifPresent(this::withItems);
            module.getBlockRegistry().ifPresent(this::withBlocks);
            module.getDataGenerator().ifPresent(data -> data.generateItemModels(this, generator));
        });

        this.withItems(AITItems.class);
        this.withBlocks(AITBlocks.class);

        super.generateItemModels(generator);
    }

    public void registerDirectionalBlock(Block block) {
        directionalBlocksToRegister.add(block);
    }

    public void registerCoralFanBlock(Block fanBlock, Block wallFanBlock) {
        coralFanBlocksToRegister.add(new Tuple<>(fanBlock, wallFanBlock));
    }

    public void registerSimpleBlock(Block block) {
        simpleBlocksToRegister.add(block);
    }

    public void registerPillarBlock(Block block) {
        pillarBlocksToRegister.add(block);
    }

    private void registerItem(ItemModelGenerators generator, Item item, String modid) {
        ModelTemplate model = item(TextureSlot.LAYER0);
        model.create(ModelLocationUtils.getModelLocation(item), createTextureMap(item, modid), generator.output);
    }

    private TextureMapping createTextureMap(Item item, String modid) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(modid, "item/" + getItemName(item));
        if (!(doesTextureExist(texture))) {
            texture = AITMod.id("item/error");
        }

        return new TextureMapping().put(TextureSlot.LAYER0, texture);
    }

    public boolean doesTextureExist(ResourceLocation texture) {
        return this.output.findResource("assets/" + texture.getNamespace() + "/textures/" + texture.getPath() + ".png").isPresent();
    }
}
