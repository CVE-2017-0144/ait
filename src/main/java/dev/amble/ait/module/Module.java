package dev.amble.ait.module;

import java.util.Optional;
import java.util.function.Consumer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import dev.amble.ait.AITMod;
import dev.amble.ait.datagen.datagen_providers.AITBlockTagProvider;
import dev.amble.ait.datagen.datagen_providers.AITItemTagProvider;
import dev.amble.ait.datagen.datagen_providers.AITRecipeProvider;
import dev.amble.lib.api.Identifiable;
import dev.amble.lib.container.impl.BlockContainer;
import dev.amble.lib.container.impl.ItemContainer;
import dev.amble.lib.datagen.lang.AmbleLanguageProvider;
import dev.amble.lib.datagen.model.AmbleModelProvider;
import dev.amble.lib.itemgroup.AItemGroup;

public abstract class Module implements Identifiable {
    private AItemGroup group;

    public abstract void init();

    @Environment(EnvType.CLIENT)
    public abstract void initClient();

    protected Item register(Item item, ResourceLocation id) {
        Registry.register(BuiltInRegistries.ITEM, id, item);
        return item;
    }
    protected SoundEvent register(SoundEvent sound, ResourceLocation id) {
        Registry.register(BuiltInRegistries.SOUND_EVENT, id, sound);
        return sound;
    }
    protected SoundEvent registerSound(String name) {
        return register(SoundEvent.createVariableRangeEvent(AITMod.id(name)), AITMod.id(name));
    }
    protected <T extends BlockEntity> BlockEntityType<T> register(BlockEntityType<T> type, ResourceLocation id) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, type);
        return type;
    }

    public Optional<Class<? extends BlockContainer>> getBlockRegistry() {
        return Optional.empty();
    }
    public Optional<Class<? extends ItemContainer>> getItemRegistry() {
        return Optional.empty();
    }

    public boolean shouldRegister() {
        return true;
    }

    protected AItemGroup.Builder buildItemGroup() {
        return null;
    }

    public AItemGroup getItemGroup() {
        AItemGroup.Builder builder = buildItemGroup();

        if (builder == null) throw new UnsupportedOperationException("Item Group for module " + this + " is not defined");
        if (!(this.shouldRegister())) throw new UnsupportedOperationException("Tried to access item group for module " + this + " but it is not registered");

        if (group == null) {
            group = builder.build();

            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, group.id(), group);
        }

        return group;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id() +
                '}';
    }

    public Optional<DataGenerator> getDataGenerator() {
        return Optional.empty();
    }

    public interface DataGenerator {
        /**
         * Called when the ENGLISH language provider is generating
         */
        void lang(AmbleLanguageProvider provider);
        void recipes(AITRecipeProvider provider);
        void blockTags(AITBlockTagProvider provider);
        void itemTags(AITItemTagProvider provider);

        void generateItemModels(AmbleModelProvider provider, ItemModelGenerators generator);

        void models(AmbleModelProvider provider, BlockModelGenerators generator);

        void advancements(Consumer<Advancement> consumer);
    }
}
