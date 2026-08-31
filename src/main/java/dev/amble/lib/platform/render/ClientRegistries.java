package dev.amble.lib.platform.render;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import dev.amble.lib.platform.Platform;

@OnlyIn(Dist.CLIENT)
public final class ClientRegistries {

    private ClientRegistries() {}

    public interface DynamicItemRenderer {
        void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices,
                MultiBufferSource vertexConsumers, int light, int overlay);
    }

    private record EntityRendererEntry<T extends Entity>(EntityType<? extends T> type,
            EntityRendererProvider<T> provider) {

        void register(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(this.type, this.provider);
        }
    }

    private record BlockEntityRendererEntry<T extends BlockEntity>(BlockEntityType<T> type,
            BlockEntityRendererProvider<T> provider) {

        void register(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(this.type, this.provider);
        }
    }

    private record ParticleEntry<T extends ParticleOptions>(ParticleType<T> type,
            ParticleEngine.SpriteParticleRegistration<T> spriteSet, ParticleProvider<T> provider) {

        void register(RegisterParticleProvidersEvent event) {
            if (this.spriteSet != null) {
                event.registerSpriteSet(this.type, this.spriteSet);
                return;
            }

            event.registerSpecial(this.type, this.provider);
        }
    }

    private record ItemColorEntry(ItemColor color, ItemLike[] items) {}

    private record DimensionEffectsEntry(ResourceLocation id, DimensionSpecialEffects effects) {}

    private static final List<EntityRendererEntry<?>> ENTITY_RENDERERS = new ArrayList<>();
    private static final List<BlockEntityRendererEntry<?>> BLOCK_ENTITY_RENDERERS = new ArrayList<>();
    private static final List<ParticleEntry<?>> PARTICLES = new ArrayList<>();
    private static final List<ItemColorEntry> ITEM_COLORS = new ArrayList<>();
    private static final List<KeyMapping> KEY_MAPPINGS = new ArrayList<>();
    private static final List<DimensionEffectsEntry> DIMENSION_EFFECTS = new ArrayList<>();

    static {
        IEventBus modBus = Platform.modBus();

        if (modBus != null) {
            modBus.addListener(EntityRenderersEvent.RegisterRenderers.class, event -> {
                ENTITY_RENDERERS.forEach(entry -> entry.register(event));
                BLOCK_ENTITY_RENDERERS.forEach(entry -> entry.register(event));
            });
            modBus.addListener(RegisterParticleProvidersEvent.class,
                    event -> PARTICLES.forEach(entry -> entry.register(event)));
            modBus.addListener(RegisterColorHandlersEvent.Item.class,
                    event -> ITEM_COLORS.forEach(entry -> event.register(entry.color(), entry.items())));
            modBus.addListener(RegisterKeyMappingsEvent.class, event -> KEY_MAPPINGS.forEach(event::register));
            modBus.addListener(RegisterDimensionSpecialEffectsEvent.class,
                    event -> DIMENSION_EFFECTS.forEach(entry -> event.register(entry.id(), entry.effects())));
        }
    }

    public static <T extends Entity> void entityRenderer(EntityType<? extends T> type,
            EntityRendererProvider<T> provider) {
        ENTITY_RENDERERS.add(new EntityRendererEntry<>(type, provider));
    }

    public static <T extends BlockEntity> void blockEntityRenderer(BlockEntityType<T> type,
            BlockEntityRendererProvider<T> provider) {
        BLOCK_ENTITY_RENDERERS.add(new BlockEntityRendererEntry<>(type, provider));
    }

    public static void itemRenderer(ItemLike item, DynamicItemRenderer renderer) {
        PlatformItemRenderers.register(item.asItem(), renderer);
    }

    public static <T extends ParticleOptions> void particle(ParticleType<T> type,
            ParticleEngine.SpriteParticleRegistration<T> factory) {
        PARTICLES.add(new ParticleEntry<>(type, factory, null));
    }

    public static <T extends ParticleOptions> void particle(ParticleType<T> type, ParticleProvider<T> provider) {
        PARTICLES.add(new ParticleEntry<>(type, null, provider));
    }

    public static void blockRenderLayer(Block block, RenderType layer) {
        ItemBlockRenderTypes.TYPE_BY_BLOCK.put(block, layer);
    }

    public static void itemColor(ItemColor color, ItemLike... items) {
        ITEM_COLORS.add(new ItemColorEntry(color, items));
    }

    public static KeyMapping keyBinding(KeyMapping mapping) {
        KEY_MAPPINGS.add(mapping);
        return mapping;
    }

    public static void dimensionEffects(ResourceLocation id, DimensionSpecialEffects effects) {
        DIMENSION_EFFECTS.add(new DimensionEffectsEntry(id, effects));
    }

    public static boolean renderCustomSky(ResourceKey<Level> world, WorldRenderContext context) {
        return false;
    }
}
