package dev.amble.lib.platform.render;

import com.mojang.blaze3d.vertex.PoseStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.DimensionSpecialEffects;
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

@Environment(EnvType.CLIENT)
public final class ClientRegistries {

    private ClientRegistries() {}

    public interface DynamicItemRenderer {
        void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices,
                MultiBufferSource vertexConsumers, int light, int overlay);
    }

    public static <T extends Entity> void entityRenderer(EntityType<? extends T> type,
            EntityRendererProvider<T> provider) {
        EntityRendererRegistry.register(type, provider);
    }

    public static <T extends BlockEntity> void blockEntityRenderer(BlockEntityType<T> type,
            BlockEntityRendererProvider<T> provider) {
        BlockEntityRendererRegistry.register(type, provider);
    }

    public static void itemRenderer(ItemLike item, DynamicItemRenderer renderer) {
        BuiltinItemRendererRegistry.INSTANCE.register(item,
                (stack, mode, matrices, vertexConsumers, light, overlay) -> renderer.render(stack, mode,
                        matrices, vertexConsumers, light, overlay));
    }

    public static <T extends ParticleOptions> void particle(ParticleType<T> type,
            ParticleFactoryRegistry.PendingParticleFactory<T> factory) {
        ParticleFactoryRegistry.getInstance().register(type, factory);
    }

    public static <T extends ParticleOptions> void particle(ParticleType<T> type, ParticleProvider<T> provider) {
        ParticleFactoryRegistry.getInstance().register(type, provider);
    }

    public static void blockRenderLayer(Block block, RenderType layer) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, layer);
    }

    public static void itemColor(ItemColor color, ItemLike... items) {
        ColorProviderRegistry.ITEM.register(color, items);
    }

    public static KeyMapping keyBinding(KeyMapping mapping) {
        return KeyBindingHelper.registerKeyBinding(mapping);
    }

    public static void dimensionEffects(ResourceLocation id, DimensionSpecialEffects effects) {
        DimensionRenderingRegistry.registerDimensionEffects(id, effects);
    }

    public static boolean renderCustomSky(ResourceKey<Level> world, WorldRenderContext context) {
        DimensionRenderingRegistry.SkyRenderer renderer = DimensionRenderingRegistry.getSkyRenderer(world);

        if (renderer == null || !(context instanceof FabricWorldRenderContext fabric))
            return false;

        renderer.render(fabric.delegate());
        return true;
    }
}
