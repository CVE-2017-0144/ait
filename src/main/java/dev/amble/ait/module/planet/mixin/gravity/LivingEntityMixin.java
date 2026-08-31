package dev.amble.ait.module.planet.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.AITStatusEffects;
import dev.amble.ait.core.entities.FlightTardisEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;
import dev.amble.ait.module.planet.core.util.ISpaceImmune;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@Mixin(value = LivingEntity.class, priority = 1001)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    public void ait$tickMovement(CallbackInfo ci) {
        if (!this.isControlledByLocalInstance())
            return;

        Planet planet = PlanetRegistry.getInstance().get(this.level());

        if (planet == null || !planet.hasGravityModifier())
            return;

        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.isSwimming() || entity.isNoGravity()/* || entity.isFallFlying()*/ || entity.isSpectator())
            return;

        if (entity instanceof Player player && player.getAbilities().flying)
            return;

        if (entity.getType() == EntityType.BOAT || entity.getType() == EntityType.CHEST_BOAT)
            return;

        boolean oxygenated = entity.hasEffect(AITStatusEffects.OXYGENATED);

        if (oxygenated)
            return;

        Vec3 movement = entity.getDeltaMovement();
        entity.setDeltaMovement(movement.x, movement.y + planet.gravity(), movement.z);
    }

    @Inject(method = "onBelowWorld", at = @At("HEAD"), cancellable = true)
    public void ait$tickInVoid(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof FlightTardisEntity)
            ci.cancel();
        if (entity.level().dimension().equals(AITDimensions.SPACE))
            ci.cancel();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void ait$tick(CallbackInfo ci) {
        if (this.tickCount % 10 != 0)
            return;

        LivingEntity entity = (LivingEntity) (Object) this;
        Planet planet = PlanetRegistry.getInstance().get(this.level());

        if (planet == null)
            return;


        if (entity instanceof Player player
                && player.isSpectator())
            return;

        boolean oxygenated = entity.hasEffect(AITStatusEffects.OXYGENATED);

        if (oxygenated)
            return;

        if (entity instanceof ISpaceImmune)
            return;

        if (entity instanceof Player player && player.isCreative())
            return;

        if (planet.isFreezing() && !Planet.hasFullSuit(entity)) {
            if (entity.getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES))
                return;

            if (entity.getTicksFrozen() < entity.getTicksRequiredToFreeze())
                entity.setTicksFrozen(entity.getTicksRequiredToFreeze() + 20);
        }

        if (!planet.hasOxygen() && (!Planet.hasFullSuit(entity) || !Planet.hasOxygenInTank(entity))) {
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,
                    200, 1, false, false));
            entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 1,
                    200, false, false));
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,
                    200, 1, false, false));
        }
    }

    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void ait$handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        Level world = this.level();
        Planet planet = PlanetRegistry.getInstance().get(world);

        if (planet != null) {
            if (planet.hasNoFallDamage())
                cir.setReturnValue(false);
            return;
        }

        // Prevent fall damage in TARDIS for loyalty OWNER (and working life support)
        LivingEntity entity = (LivingEntity)(Object) this;
        if (world instanceof TardisServerWorld tardisWorld
                && entity instanceof Player player) {

            Tardis tardis = tardisWorld.getTardis();
            boolean hasLifeSupport = tardis.subsystems().lifeSupport().isUsable();

            if (hasLifeSupport && tardis.loyalty().get(player).isOf(Loyalty.Type.OWNER))
                cir.setReturnValue(false);
        }
    }
}
