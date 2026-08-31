package dev.amble.lib.client.bedrock;

import com.mojang.serialization.Codec;
import dev.amble.lib.api.Identifiable;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public record BedrockAnimationReference(String fileName, String animationName) implements Identifiable {
	public static Codec<BedrockAnimationReference> CODEC = ResourceLocation.CODEC.xmap(
			BedrockAnimationReference::parse,
			BedrockAnimationReference::id
	);

	@Override
	public ResourceLocation id() {
		return ResourceLocation.tryBuild(fileName, animationName);
	}

	public Optional<BedrockAnimation> get() {
		BedrockAnimation animation = BedrockAnimationRegistry.getInstance().get(this);
		return Optional.ofNullable(animation);
	}

	public static BedrockAnimationReference parse(ResourceLocation id) {
		return new BedrockAnimationReference(id.getNamespace(), id.getPath());
	}
}
