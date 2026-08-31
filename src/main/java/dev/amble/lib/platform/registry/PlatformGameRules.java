package dev.amble.lib.platform.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.level.GameRules;

public final class PlatformGameRules {

    private PlatformGameRules() {}

    public static GameRules.Key<GameRules.BooleanValue> registerBoolean(String name,
            GameRules.Category category, boolean defaultValue) {
        return GameRuleRegistry.register(name, category, GameRuleFactory.createBooleanRule(defaultValue));
    }
}
