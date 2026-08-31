package dev.amble.ait.compat.permissionapi;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.api.ModInitializer;
import net.minecraft.commands.CommandSourceStack;
import dev.amble.ait.compat.DependencyChecker;

public class PermissionAPICompat implements ModInitializer {

    private static PermissionCheck CHECKER = (ctx, permission, level) -> ctx.hasPermission(level);

    @FunctionalInterface
    public interface PermissionCheck {
        boolean hasPermission(CommandSourceStack ctx, String permission, int level);
    }

    @Override
    public void onInitialize() {
        if (DependencyChecker.hasPermissionApi()) CHECKER = Permissions::check;
    }

    // Public static method to use the permission check lambda
    public static boolean hasPermission(CommandSourceStack ctx, String permission, int level) {
        return CHECKER.hasPermission(ctx, permission, level);
    }
}
