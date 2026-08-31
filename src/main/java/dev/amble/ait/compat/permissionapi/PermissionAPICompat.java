package dev.amble.ait.compat.permissionapi;

import dev.amble.lib.platform.ModEntrypoint;
import net.minecraft.commands.CommandSourceStack;

public class PermissionAPICompat implements ModEntrypoint {

    private static PermissionCheck CHECKER = (ctx, permission, level) -> ctx.hasPermission(level);

    @FunctionalInterface
    public interface PermissionCheck {
        boolean hasPermission(CommandSourceStack ctx, String permission, int level);
    }

    @Override
    public void onInitialize() {
        // neoforge perms need pre-registered nodes, op level until setChecker
    }

    public static void setChecker(PermissionCheck checker) {
        CHECKER = checker;
    }

    // Public static method to use the permission check lambda
    public static boolean hasPermission(CommandSourceStack ctx, String permission, int level) {
        return CHECKER.hasPermission(ctx, permission, level);
    }
}
