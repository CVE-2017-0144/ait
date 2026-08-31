package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import java.util.function.Predicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.commands.argument.PermissionArgumentType;
import dev.amble.ait.core.commands.argument.TardisArgumentType;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.handler.permissions.Permission;
import dev.amble.ait.core.tardis.handler.permissions.PermissionHandler;

public class PermissionCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("permission")
                .requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.permission", 2))
                .then(argument("tardis", TardisArgumentType.tardis()).then(argument("player",
                        EntityArgument.player())
                        .then(argument("permission", PermissionArgumentType.permission())
                                .executes(PermissionCommand::get)
                                .then(argument("value", BoolArgumentType.bool()).executes(PermissionCommand::set)))))));
    }

    private static int set(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommonArgs args = CommonArgs.create(context);
        boolean value = BoolArgumentType.getBool(context, "value");

        args.run("ait.command.permission.set", "Set permission '%s' for player %s to '%s'",
                handler -> handler.set(args.player, args.permission, value));

        return Command.SINGLE_SUCCESS;
    }

    private static int get(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommonArgs args = CommonArgs.create(context);
        return args.run("ait.command.permission.get", "Permission check '%s' for player %s: '%s'",
                handler -> handler.check(args.player, args.permission)) ? 1 : 0;
    }

    record CommonArgs(CommandSourceStack source, ServerTardis tardis, ServerPlayer player,
            Permission permission) {

        public static CommonArgs create(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");
            ServerPlayer player = EntityArgument.getPlayer(context, "player");
            Permission permission = PermissionArgumentType.getPermission(context, "permission");

            return new CommonArgs(context.getSource(), tardis, player, permission);
        }

        public boolean run(String key, String fallback, Predicate<PermissionHandler> func) {
            boolean result = func.test(this.tardis.permissions());

            this.source.sendSuccess(
                    () -> Component.translatableWithFallback(key, fallback, this.permission, this.player.getName(), result),
                    false);

            return result;
        }
    }
}
