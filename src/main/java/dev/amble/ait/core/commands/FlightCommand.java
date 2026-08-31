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
import net.minecraft.server.level.ServerPlayer;

public class FlightCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("flight").requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.flight", 2))
                        .then(argument("tardis", TardisArgumentType.tardis())
                                .executes(FlightCommand::execute))));

    }

    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayer();

        if (player == null)
            return 0;

        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");

        if (!AITMod.CONFIG.rwfEnabled) {
            player.displayClientMessage(Component.translatable("tardis.message.control.rwf_disabled"), true);
            return Command.SINGLE_SUCCESS;
        }

        if (!player.isCreative()) {
            player.displayClientMessage(Component.translatable("tardis.message.control.rwf_creative_only"), true);
            return Command.SINGLE_SUCCESS;
        }

        context.getSource().getServer().executeIfPossible(()
                -> tardis.flight().enterFlight(player));

        return Command.SINGLE_SUCCESS;
    }
}
