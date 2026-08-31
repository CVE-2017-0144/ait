package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.commands.argument.TardisArgumentType;
import dev.amble.ait.core.tardis.ServerTardis;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class GetNameCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID)
                .then(literal("name").requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.name", 2)).then(literal("get")
                        .then(argument("tardis", TardisArgumentType.tardis()).executes(GetNameCommand::runCommand)))));
    }

    private static int runCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");

        source.sendSystemMessage(Component.translatableWithFallback("command.tardis.ait.name", "TARDIS name: %s", tardis.stats().getName()));
        return Command.SINGLE_SUCCESS;
    }
}
