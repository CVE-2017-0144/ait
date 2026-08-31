package dev.amble.ait.core.effects;

import dev.amble.ait.core.AITStatusEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ZeitonHighEffect extends MobEffect {
    public ZeitonHighEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x8fbaff);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static boolean isHigh(LivingEntity entity) {
        return entity.hasEffect(AITStatusEffects.ZEITON_HIGH);
    }
}
