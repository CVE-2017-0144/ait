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
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class RemoveCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("remove").requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.remove", 2))
                .then(argument("tardis", TardisArgumentType.tardis()).executes(RemoveCommand::removeCommand))));
    }

    private static int removeCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");

        source.sendSuccess(() -> Component.translatableWithFallback("tardis.remove.progress",
                "Removing TARDIS with id [%s]...", tardis.getUuid()), true);

        // Delete the file. File system operations are costly!
        ServerTardisManager.getInstance().remove(context.getSource().getServer(), tardis);

        source.sendSuccess(
                () -> Component.translatableWithFallback("tardis.remove.done", "TARDIS [%s] removed", tardis.getUuid()),
                true);

        return Command.SINGLE_SUCCESS;
    }
}
