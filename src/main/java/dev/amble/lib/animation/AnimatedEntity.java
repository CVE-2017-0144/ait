package dev.amble.lib.animation;

import dev.amble.lib.client.bedrock.BedrockAnimationReference;
import dev.amble.lib.client.bedrock.BedrockModel;
import dev.amble.lib.client.bedrock.BedrockModelReference;
import java.util.UUID;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public interface AnimatedEntity extends EntityAccess, AnimatedInstance {
	@Override
	default UUID getUuid() {
		if (this instanceof Entity entity) {
			return entity.getUUID();
		}

		throw new UnsupportedOperationException("getUuid() is only supported for Entity instances. Override this method");
	}

	@Override
	default int getAge() {
		if (this instanceof Entity entity) {
			return entity.tickCount;
		}

		throw new UnsupportedOperationException("getAge() is only supported for Entity instances. Override this method");
	}

	@Nullable
	static AnimatedEntity getInstance(EntityAccess entity) {
		if (entity instanceof AnimatedEntity animated) {
			return animated;
		}
		return null;
	}

	@Override
	default Level getWorld() {
		if (!(this instanceof Entity be)) {
			throw new UnsupportedOperationException("getWorld() is only supported for Entity instances. Override this method");
		}

		return be.level();
	}

	@Override
	default boolean isSilent() {
		if (!(this instanceof Entity be)) {
			throw new UnsupportedOperationException("isSilent() is only supported for Entity instances. Override this method");
		}

		return be.isSilent();
	}

	@Override
	default SoundSource getSoundCategory() {
		if (!(this instanceof Entity be)) {
			throw new UnsupportedOperationException("getSoundCategory() is only supported for Entity instances. Override this method");
		}

		return be.getSoundSource();
	}

	@Override
	default Vec3 getEffectPosition(float tickDelta) {
		if (!(this instanceof Entity entity)) throw new UnsupportedOperationException("getEffectPosition() is only supported for Entity instances. Override this method");

		return new Vec3(
				Mth.lerp(tickDelta, entity.xo, entity.getX()),
				Mth.lerp(tickDelta, entity.yo, entity.getY()),
				Mth.lerp(tickDelta, entity.zo, entity.getZ()));
	}
}
