package dev.amble.ait.module.decoration;

import java.util.Optional;
import java.util.function.Consumer;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import dev.amble.ait.AITMod;
import dev.amble.ait.datagen.datagen_providers.AITBlockTagProvider;
import dev.amble.ait.datagen.datagen_providers.AITItemTagProvider;
import dev.amble.ait.datagen.datagen_providers.AITRecipeProvider;
import dev.amble.ait.module.Module;
import dev.amble.ait.module.decoration.core.DecorationBlocks;
import dev.amble.ait.module.decoration.core.DecorationItems;
import dev.amble.lib.container.RegistryContainer;
import dev.amble.lib.container.impl.BlockContainer;
import dev.amble.lib.container.impl.ItemContainer;
import dev.amble.lib.datagen.lang.AmbleLanguageProvider;
import dev.amble.lib.datagen.model.AmbleModelProvider;
import dev.amble.lib.itemgroup.AItemGroup;



public class DecorationModule extends Module {
    private static final DecorationModule INSTANCE = new DecorationModule();

    public static final ResourceLocation ID = AITMod.id("decoration");

    @Override
    public void init() {
        RegistryContainer.register(DecorationItems.class, AITMod.MOD_ID);
        RegistryContainer.register(DecorationBlocks.class, AITMod.MOD_ID);
    }

    @Override
    protected AItemGroup.Builder buildItemGroup() {
        return AItemGroup.builder(id()).icon(() -> new ItemStack(Blocks.BARRIER));
    }



    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }


    @Override
    public Optional<Class<? extends BlockContainer>> getBlockRegistry() {
        return Optional.of(DecorationBlocks.class);
    }

    @Override
    public Optional<Class<? extends ItemContainer>> getItemRegistry() {
        return Optional.of(DecorationItems.class);
    }

    @Override
    public Optional<DataGenerator> getDataGenerator() {
        return Optional.of(new DataGenerator() {
            @Override
            public void lang(AmbleLanguageProvider provider) {
                provider.addTranslation(getItemGroup(), "AIT: Decoration");
                provider.addTranslation("itemGroup.ait.decoration", "AIT: Decoration");

            }

            @Override
            public void recipes(AITRecipeProvider provider) {

            }


            @Override
            public void blockTags(AITBlockTagProvider provider) {
            }

            @Override
            public void itemTags(AITItemTagProvider provider) {

            }

            @Override
            public void generateItemModels(AmbleModelProvider provider, ItemModelGenerators generator) {

            }

            @Override
            public void models(AmbleModelProvider provider, BlockModelGenerators generator) {

            }

            @Override
            public void advancements(Consumer<AdvancementHolder> consumer) {

            }
        });
    }

    public static DecorationModule instance() {
        return INSTANCE;
    }
}
