package dev.amble.lib.container.impl;

import dev.amble.lib.AmbleKit;
import dev.amble.lib.animation.AnimatedEntity;
import dev.amble.lib.animation.AnimatedInstance;
import dev.amble.lib.animation.client.BedrockEntityRenderer;
import dev.amble.lib.animation.HasBedrockModel;
import dev.amble.lib.util.RegistrationUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import dev.amble.lib.container.RegistryContainer;
import dev.amble.lib.platform.Platform;
import java.lang.reflect.Field;

public interface EntityContainer extends RegistryContainer<EntityType<?>> {
	@Override
	default void postProcessField(ResourceLocation identifier, EntityType<?> value, Field field) {
		if (!Platform.isClient()) return;

		// automagically register bedrock renderer
		if (!field.isAnnotationPresent(HasBedrockModel.class)) return;

		RegistrationUtil.registerBedrockRenderer(value);
	}

	@Override
	default Class<EntityType<?>> getTargetClass() {
		return RegistryContainer.conform(EntityType.class);
	}

	@Override
	default Registry<EntityType<?>> getRegistry() {
		return BuiltInRegistries.ENTITY_TYPE;
	}
}
