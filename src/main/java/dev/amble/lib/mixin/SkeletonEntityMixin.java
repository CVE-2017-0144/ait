package dev.amble.lib.mixin;

import dev.amble.lib.animation.AnimatedEntity;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Skeleton.class)
public abstract class SkeletonEntityMixin extends Monster implements AnimatedEntity {
	private SkeletonEntityMixin(EntityType<? extends Monster> type, Level world) {
		super(type, world);
	}


	private AnimationState amblekit$animationState = new AnimationState();
	@Override
	public AnimationState getAnimationState() {
		return amblekit$animationState;
	}
}