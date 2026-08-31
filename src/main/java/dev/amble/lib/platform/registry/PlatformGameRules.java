package dev.amble.lib.platform.registry;

import net.minecraft.world.level.GameRules;

public class PlatformGameRules {

    public static GameRules.Key<GameRules.BooleanValue> registerBoolean(String name,
            GameRules.Category category, boolean defaultValue) {
        return GameRules.register(name, category, GameRules.BooleanValue.create(defaultValue));
    }
}
