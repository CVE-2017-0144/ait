package dev.amble.lib.animation.client;

import dev.amble.lib.AmbleKit;
import dev.amble.lib.animation.AnimatedInstance;
import dev.amble.lib.animation.AnimationTracker;
import dev.amble.lib.client.bedrock.BedrockAnimation;
import dev.amble.lib.client.bedrock.BedrockAnimationReference;
import lombok.extern.slf4j.Slf4j;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public interface AnimatedEntityModel {
	/**
	 * @return the root modelpart of the renderer
	 */
	ModelPart getPart();

	default Optional<ModelPart> getChild(String name) {
		if (name.equals("root") || name.equalsIgnoreCase("player")) {
			return Optional.of(this.getPart());
		}
		return this.getPart().getAllParts().filter(part -> part.hasChild(name)).findFirst().map(part -> part.getChild(name));
	}

	/**
	 * Call this in {@link EntityModel#setupAnim(Entity, float, float, float, float, float)} where progress is usually named 'h'
	 */
	default void applyAnimation(AnimatedInstance entity, float progress) {
		BedrockAnimationReference reference = entity.getCurrentAnimation();

		if (reference == null) return;

		BedrockAnimation animation = reference.get().orElse(null);
		if (animation == null) {
			AmbleKit.LOGGER.error("Got unknown animation reference: {}", reference.id());
			AnimationTracker.getInstance().removeLocal(entity);
			return;
		}

		AnimationState state = entity.getAnimationState();

		if (entity.isAnimationDirty()) {
			state.stop();
			state.startIfStopped(entity.getAge());
		}

		if (animation.isFinished(state)) {
			AnimationTracker.getInstance().removeLocal(entity);
			return;
		}

		state.startIfStopped(entity.getAge());

		animation.apply(this.getPart(), state, progress, 1.0F, entity);
	}

	default void applyAnimationPre(AnimatedInstance entity, float progress) {
		BedrockAnimationReference reference = entity.getCurrentAnimation();

		if (reference == null) {
			this.getPart().getAllParts().forEach(ModelPart::resetPose);
			return;
		}

		BedrockAnimation animation = reference.get().orElse(null);
		if (animation == null) {
			this.getPart().getAllParts().forEach(ModelPart::resetPose);
			return;
		}

		AnimationState state = entity.getAnimationState();
		if (state == null || animation.isFinished(state)) {
			this.getPart().getAllParts().forEach(ModelPart::resetPose);
			return;
		}

		if (animation.metadata != null && !animation.metadata.movement()) {
			this.getPart().getAllParts().forEach(ModelPart::resetPose);
		}
	}
}