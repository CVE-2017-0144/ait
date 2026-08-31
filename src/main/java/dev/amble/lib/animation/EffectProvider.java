package dev.amble.lib.animation;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface EffectProvider {
	Level getWorld();
	boolean isSilent();
	SoundSource getSoundCategory();
	float getHeadYaw();
	float getBodyYaw();
	float getPitch();
	Vec3 getEffectPosition(float tickDelta);
}
