package dev.amble.ait.module.planet.core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class LunarRegolithEffect extends MobEffect {

    public LunarRegolithEffect() {
        super(MobEffectCategory.HARMFUL, 0xF5F5F5); // Customize color
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, amplifier, false, false));
        entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, amplifier, false, false));

        int lunarDuration = entity.getEffect(this).getDuration();

        int delayBeforeEffect = lunarDuration - 1000;

        if (delayBeforeEffect <= 0) {
            entity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 6, false, false));

        }
    }
}
