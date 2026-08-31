package dev.amble.ait.core;

import dev.amble.lib.datagen.util.NoEnglish;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class AITFoodComponents  {
    @NoEnglish
    public static final FoodProperties FOOD_CUBE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).fast()
            .build();

    @NoEnglish
    public static final FoodProperties OVERCHARGED_FOOD_CUBE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.5f).fast()
            .alwaysEdible()
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 50, 1), 1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 3), 1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1500, 0), 1.0f)
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1500, 0), 1.0f)
            .build();
}
