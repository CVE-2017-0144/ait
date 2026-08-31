package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.commands.argument.TardisArgumentType;
import dev.amble.ait.core.tardis.ServerTardis;
import net.minecraft.commands.CommandSourceStack;

public class SetNameCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("name").requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.name", 2))
                .then(literal("set").then(argument("tardis", TardisArgumentType.tardis())
                        .then(argument("value", StringArgumentType.string()).executes(SetNameCommand::runCommand))))));
    }

    private static int runCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");
        String name = StringArgumentType.getString(context, "value");

        tardis.stats().setName(name);
        return Command.SINGLE_SUCCESS;
    }
}
