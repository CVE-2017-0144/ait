package dev.amble.ait.core;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.effects.OxygenatedEffect;
import dev.amble.ait.core.effects.ZeitonHighEffect;
import dev.amble.ait.module.planet.core.effect.LunarRegolithEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class AITStatusEffects {
    public static MobEffect ZEITON_HIGH = register(new ZeitonHighEffect(), "zeiton_high");

    public static MobEffect OXYGENATED = register(new OxygenatedEffect(), "oxygenated");
    public static MobEffect LUNAR_SICKNESS = register(new LunarRegolithEffect(), "lunar_sickness");

    public static void init() {
    }

    private static MobEffect register(MobEffect effect, String name) {
        return Registry.register(BuiltInRegistries.MOB_EFFECT, AITMod.id(name), effect);
    }
}
