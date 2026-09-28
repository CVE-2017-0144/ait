package dev.amble.ait.core;

import java.util.function.Supplier;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.commands.argument.*;
import dev.amble.lib.platform.command.Commands;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;

public class AITArgumentTypes {

    public static void register() {
        register("tardis", TardisArgumentType.class, TardisArgumentType::tardis);
        register("wildcard_resource_location", IdentifierWildcardArgumentType.class,
                IdentifierWildcardArgumentType::wildcard);
        register("permission", PermissionArgumentType.class, PermissionArgumentType::permission);
        register("json", JsonElementArgumentType.class, JsonElementArgumentType::jsonElement);
        register("ground_search", GroundSearchArgumentType.class, GroundSearchArgumentType::groundSearch);
    }

    private static <T extends ArgumentType<?>> void register(String name, Class<T> t, Supplier<T> supplier) {
        Commands.argumentType(AITMod.id(name), t,
                SingletonArgumentInfo.contextFree(supplier));
    }
}
