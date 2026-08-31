package dev.amble.lib.container.impl;

import dev.amble.lib.AmbleKit;
import dev.amble.lib.animation.AnimatedBlockEntity;
import dev.amble.lib.animation.AnimatedInstance;
import dev.amble.lib.animation.client.BedrockBlockEntityRenderer;
import dev.amble.lib.animation.HasBedrockModel;
import dev.amble.lib.util.RegistrationUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import dev.amble.lib.container.RegistryContainer;
import java.lang.reflect.Field;

public interface BlockEntityContainer extends RegistryContainer<BlockEntityType<?>> {
    @Override
    default void postProcessField(ResourceLocation identifier, BlockEntityType<?> value, Field field) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) return;

        // automagically register bedrock renderer
        if (!field.isAnnotationPresent(HasBedrockModel.class)) return;

	    registerRenderer((BlockEntityType<? extends AnimatedBlockEntity>) value);
    }

    @Environment(EnvType.CLIENT)
    private static void registerRenderer(BlockEntityType<? extends AnimatedBlockEntity> type) {
        BlockEntityRendererRegistry.register(type, BedrockBlockEntityRenderer::new);
    }

    @Override
    default Class<BlockEntityType<?>> getTargetClass() {
        return RegistryContainer.conform(BlockEntityType.class);
    }

    @Override
    default Registry<BlockEntityType<?>> getRegistry() {
        return BuiltInRegistries.BLOCK_ENTITY_TYPE;
    }
}
