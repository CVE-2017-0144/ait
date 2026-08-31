package dev.amble.lib.client.bedrock;

import com.mojang.serialization.Codec;
import dev.amble.lib.api.Identifiable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import java.util.Optional;

public record BedrockModelReference(String fileName, String animationName) implements Identifiable {
	public static Codec<BedrockModelReference> CODEC = ResourceLocation.CODEC.xmap(
			BedrockModelReference::parse,
			BedrockModelReference::id
	);

	@Override
	public ResourceLocation id() {
		return ResourceLocation.tryBuild(fileName, animationName);
	}

	@Environment(EnvType.CLIENT)
	public Optional<BedrockModel> get() {
		BedrockModel animation = BedrockModelRegistry.getInstance().get(this.id());
		return Optional.ofNullable(animation);
	}

	public static BedrockModelReference parse(ResourceLocation id) {
		return new BedrockModelReference(id.getNamespace(), id.getPath());
	}
}
