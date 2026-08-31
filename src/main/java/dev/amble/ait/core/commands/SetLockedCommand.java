package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.commands.argument.TardisArgumentType;
import dev.amble.ait.core.tardis.ServerTardis;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

public class SetLockedCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("lock").requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.lock", 2))
                .then(argument("tardis", TardisArgumentType.tardis())
                        .then(argument("locked", BoolArgumentType.bool()).executes(SetLockedCommand::runCommand)))));
    }

    private static int runCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer source = context.getSource().getPlayer();
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");
        boolean locked = BoolArgumentType.getBool(context, "locked");

        tardis.door().interactLock(locked, source, true);
        return Command.SINGLE_SUCCESS;
    }
}
