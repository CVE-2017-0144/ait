package dev.amble.ait.module.gun;

import java.util.Optional;
import java.util.function.Consumer;

import dev.amble.ait.AITMod;
import dev.amble.ait.datagen.datagen_providers.AITBlockTagProvider;
import dev.amble.ait.datagen.datagen_providers.AITItemTagProvider;
import dev.amble.ait.datagen.datagen_providers.AITRecipeProvider;
import dev.amble.ait.module.Module;
import dev.amble.ait.module.gun.client.ScopeOverlay;
import dev.amble.ait.module.gun.client.render.StaserBoltEntityRenderer;
import dev.amble.ait.module.gun.core.entity.GunEntityTypes;
import dev.amble.ait.module.gun.core.item.GunItems;
import dev.amble.lib.container.RegistryContainer;
import dev.amble.lib.container.impl.ItemContainer;
import dev.amble.lib.datagen.lang.AmbleLanguageProvider;
import dev.amble.lib.datagen.model.AmbleModelProvider;
import dev.amble.lib.itemgroup.AItemGroup;
import dev.amble.lib.platform.render.ClientRegistries;
import dev.amble.lib.platform.render.HudRenderEvents;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GunModule extends Module {
    private static final GunModule INSTANCE = new GunModule();

    public static final ResourceLocation ID = AITMod.id("gun");


    @Override
    public void init() {
        RegistryContainer.register(GunItems.class, AITMod.MOD_ID);
        RegistryContainer.register(GunEntityTypes.class, AITMod.MOD_ID);
    }

    @Override
    public void initClient() {
        HudRenderEvents.HUD.register(new ScopeOverlay());
        ClientRegistries.entityRenderer(GunEntityTypes.STASER_BOLT_ENTITY_TYPE, StaserBoltEntityRenderer::new);

        ItemProperties.register(GunItems.CULT_STASER_RIFLE, ResourceLocation.parse("ads"),
                (itemStack, clientWorld, livingEntity, integer) -> {
                    if (livingEntity == null) return 0.0f;
                    if (itemStack.getItem() == GunItems.CULT_STASER_RIFLE && livingEntity.getMainHandItem().getItem() == GunItems.CULT_STASER_RIFLE) {
                        if (livingEntity instanceof Player) {
                            boolean bl = Minecraft.getInstance().options.keyUse.isDown();
                            return bl ? 1.0f : 0.0f;
                        }
                    }
                    return 0.0F;
                });
        ItemProperties.register(GunItems.CULT_STASER, ResourceLocation.parse("ads"),
                (itemStack, clientWorld, livingEntity, integer) -> {
                    if (livingEntity == null) return 0.0f;
                    if (itemStack.getItem() == GunItems.CULT_STASER && livingEntity.getMainHandItem().getItem() == GunItems.CULT_STASER) {
                        if (livingEntity instanceof Player) {
                            boolean bl = Minecraft.getInstance().options.keyUse.isDown();
                            return bl ? 1.0f : 0.0f;
                        }
                    }
                    return 0.0F;
                });
    }

    @Override
    public Optional<Class<? extends ItemContainer>> getItemRegistry() {
        return Optional.of(GunItems.class);
    }

    @Override
    protected AItemGroup.Builder buildItemGroup() {
        return AItemGroup.builder(id()).icon(() -> new ItemStack(GunItems.CULT_STASER));
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public Optional<DataGenerator> getDataGenerator() {
        return Optional.of(new DataGenerator() {
            @Override
            public void lang(AmbleLanguageProvider provider) {
                // provider.addTranslation(getItemGroup(), "AIT: Combat");
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

    public static GunModule instance() {
        return INSTANCE;
    }
}
