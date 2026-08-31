package dev.amble.lib.datagen.model;

import java.util.*;

import net.minecraft.data.models.*;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import dev.amble.lib.AmbleKit;
import dev.amble.lib.container.impl.BlockContainer;
import dev.amble.lib.container.impl.ItemContainer;
import dev.amble.lib.datagen.util.AutomaticModel;
import dev.amble.lib.platform.datagen.PlatformDataOutput;
import dev.amble.lib.platform.datagen.PlatformModelProvider;
import dev.amble.lib.util.ReflectionUtil;

public class AmbleModelProvider extends PlatformModelProvider {

    protected final PlatformDataOutput output;

    protected List<Class<? extends BlockContainer>> blockClass = new ArrayList<>();
    protected Queue<Class<? extends ItemContainer>> itemClass = new LinkedList<>();

    public AmbleModelProvider(PlatformDataOutput output) {
        super(output);

        this.output = output;
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        this.blockClass.forEach(clazz -> {
            ReflectionUtil.getAnnotatedValues(clazz, Block.class, AutomaticModel.class, false).forEach((block, annotation) -> {
                if (!annotation.orElseThrow().justItem()) {
                    generator.createTrivialCube(block);
                }
            });
        });
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        this.blockClass.forEach(clazz -> {
            ReflectionUtil.getAnnotatedValues(clazz, Block.class, AutomaticModel.class, false).forEach((block, annotation) -> {
                if (annotation.orElseThrow().justItem()) {
                    registerItem(generator, block.asItem(), output.getModId());
                }
            });
        });

        this.itemClass.forEach(clazz -> {
            ReflectionUtil.getAnnotatedValues(clazz, Item.class, AutomaticModel.class, false).forEach((item, annotation) -> {
                registerItem(generator, item, output.getModId());
            });
        });
    }

    @SafeVarargs
    public final AmbleModelProvider withBlocks(Class<? extends BlockContainer>... blockClass) {
        // add all to queue
        this.blockClass.addAll(Arrays.asList(blockClass));

        return this;
    }

    @SafeVarargs
    public final AmbleModelProvider withItems(Class<? extends ItemContainer>... itemClass) {
        // add all to queue
        this.itemClass.addAll(Arrays.asList(itemClass));

        return this;
    }

    private static ModelTemplate item(String modid, String parent, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(Optional.of(ResourceLocation.fromNamespaceAndPath(modid, "item/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    private static ModelTemplate item(TextureSlot... requiredTextureKeys) {
        return item("minecraft", "generated", requiredTextureKeys);
    }

    private void registerItem(ItemModelGenerators generator, Item item, String modid) {
        ModelTemplate model = item(TextureSlot.LAYER0);
        model.create(ModelLocationUtils.getModelLocation(item), createTextureMap(item, modid), generator.output);
    }

    private TextureMapping createTextureMap(Item item, String modid) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(modid, "item/" + getItemName(item));
        if (!(doesTextureExist(texture))) {
            texture = AmbleKit.id("item/error");
        }

        return new TextureMapping().put(TextureSlot.LAYER0, texture);
    }

    private static String getItemName(Item item) {
        return item.getDescriptionId().split("\\.")[2];
    }

    public boolean doesTextureExist(ResourceLocation texture) {
        return this.output.findResource("assets/" + texture.getNamespace() + "/textures/" + texture.getPath() + ".png").isPresent();
    }
}
