package dev.amble.ait.core.effects;

import dev.amble.ait.core.AITStatusEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class OxygenatedEffect extends MobEffect {
    public OxygenatedEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x8fbaff);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    public static boolean isOxygenated(LivingEntity entity) {
        return entity.hasEffect(AITStatusEffects.OXYGENATED);
    }
}
