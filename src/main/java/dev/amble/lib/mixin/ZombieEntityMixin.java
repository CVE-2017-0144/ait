package dev.amble.lib.mixin;

import dev.amble.lib.animation.AnimatedEntity;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Zombie.class)
public abstract class ZombieEntityMixin extends Monster implements AnimatedEntity {
	private ZombieEntityMixin(EntityType<? extends Monster> type, Level world) {
		super(type, world);
	}


	private AnimationState amblekit$animationState = new AnimationState();
	@Override
	public AnimationState getAnimationState() {
		return amblekit$animationState;
	}
}