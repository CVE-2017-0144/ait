package dev.amble.lib.animation;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

/**
 * An interface for block entities that can have animations.
 * Provides a default implementation for generating a UUID based on the block entity's position and world.
 * Note: This implementation assumes that the block entity is in a loaded world.
 * getAge should be implemented with a private int age field incremented each tick in the block entity's tick method.
 */
public interface AnimatedBlockEntity extends AnimatedInstance {
	@Override
	default UUID getUuid() {
		if (!(this instanceof BlockEntity be)) {
			throw new UnsupportedOperationException("getUuid() is only supported for BlockEntity instances. Override this method");
		}

		BlockPos pos = be.getBlockPos();
		return new UUID(be.getLevel().dimension().location().hashCode(), pos.asLong());
	}

	@Override
	default Level getWorld() {
		if (!(this instanceof BlockEntity be)) {
			throw new UnsupportedOperationException("getWorld() is only supported for BlockEntity instances. Override this method");
		}

		return be.getLevel();
	}

	@Override
	default boolean isSilent() {
		return false;
	}

	@Override
	default SoundSource getSoundCategory() {
		return SoundSource.BLOCKS;
	}

	@Override
	default Vec3 getEffectPosition(float tickDelta) {
		if (!(this instanceof BlockEntity be)) {
			throw new UnsupportedOperationException("getSoundPosition() is only supported for BlockEntity instances. Override this method");
		}

		return Vec3.atCenterOf(be.getBlockPos());
	}

	@Override
	default float getHeadYaw() {
		return 0;
	}

	@Override
	default float getBodyYaw() {
		return 0;
	}

	@Override
	default float getPitch() {
		return 0;
	}
}
