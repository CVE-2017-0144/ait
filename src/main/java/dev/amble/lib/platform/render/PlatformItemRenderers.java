package dev.amble.lib.platform.render;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import dev.amble.lib.platform.Platform;

@OnlyIn(Dist.CLIENT)
class PlatformItemRenderers {

    private static final Map<Item, ClientRegistries.DynamicItemRenderer> RENDERERS = new HashMap<>();

    static {
        IEventBus bus = Platform.modBus();

        if (bus != null)
            bus.addListener(RegisterClientExtensionsEvent.class, e -> RENDERERS
                    .forEach((item, r) -> e.registerItem(extension(r), item)));
    }

    static void register(Item item, ClientRegistries.DynamicItemRenderer renderer) {
        RENDERERS.put(item, renderer);
    }

    private static IClientItemExtensions extension(ClientRegistries.DynamicItemRenderer renderer) {
        BlockEntityWithoutLevelRenderer wrapped = new BlockEntityWithoutLevelRenderer(
                Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels()) {

            @Override
            public void renderByItem(ItemStack stack, ItemDisplayContext mode, PoseStack ms,
                    MultiBufferSource buf, int light, int overlay) {
                renderer.render(stack, mode, ms, buf, light, overlay);
            }
        };

        return new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return wrapped;
            }
        };
    }
}
