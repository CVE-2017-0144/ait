package dev.amble.ait.core;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.effects.OxygenatedEffect;
import dev.amble.ait.core.effects.ZeitonHighEffect;
import dev.amble.ait.module.planet.core.effect.LunarRegolithEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class AITStatusEffects {

    public static Holder<MobEffect> ZEITON_HIGH = register(new ZeitonHighEffect(), "zeiton_high");

    public static Holder<MobEffect> OXYGENATED = register(new OxygenatedEffect(), "oxygenated");
    public static Holder<MobEffect> LUNAR_SICKNESS = register(new LunarRegolithEffect(), "lunar_sickness");

    public static void init() {
    }

    private static Holder<MobEffect> register(MobEffect effect, String name) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, AITMod.id(name), effect);
    }
}
