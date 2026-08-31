package dev.amble.lib.mixin;

import dev.amble.lib.animation.AnimatedEntity;
import dev.amble.lib.skin.PlayerSkinTexturable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity implements AnimatedEntity, PlayerSkinTexturable {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
		super(entityType, world);
	}

	private AnimationState amblekit$animationState = new AnimationState();

	@Override
	public AnimationState getAnimationState() {
		return amblekit$animationState;
	}

	@Override
	public UUID getUUID() {
		return super.getUUID();
	}
}
