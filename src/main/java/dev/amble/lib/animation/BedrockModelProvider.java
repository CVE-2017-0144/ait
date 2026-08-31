package dev.amble.lib.animation;

import dev.amble.lib.client.bedrock.BedrockModelReference;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public interface BedrockModelProvider {
	@Nullable
	default BedrockModelReference getModel() {
		return null;
	}

	/*
	* This lets you define custom rotation for the renderer.
	* */
	default float getRenderYaw() {
		return 0;
	}

	@Nullable
	default ResourceLocation getTexture() {
		if (getModel() == null) return null;

		BedrockModelReference model = getModel();
		ResourceLocation id = model.id();

		String prefix = getTexturePrefix();
		if (!prefix.isEmpty() && !prefix.endsWith("/")) {
			prefix += "/";
		}

		String namespace = getModId();
		if (namespace.isEmpty()) {
			namespace = id.getNamespace();
		}

		return ResourceLocation.tryBuild(namespace, "textures/" + prefix + model.id().getPath() + ".png");
	}

	@Nullable
	default ResourceLocation getEmissionTexture() {
		if (!hasEmission()) return null;

		ResourceLocation texture = getTexture();
		if (texture == null) return null;

		// add _emission suffix
		return ResourceLocation.tryBuild(texture.getNamespace(), texture.getPath().replace(".png", "_emission.png"));
	}

	default boolean hasEmission() {
		return false;
	}

	default String getModId() {
		return "";
	}

	default String getTexturePrefix() {
		return "";
	}
}
