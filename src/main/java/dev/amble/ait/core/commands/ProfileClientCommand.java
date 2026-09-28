package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import java.util.Collection;
import java.util.List;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.net.AitNetworking;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/**
 * Starts the vanilla client profiler on a connected client, so a profiling run can be driven from the
 * server console or rcon instead of someone pressing F3+L at the keyboard.
 *
 * <p>The recorder stops itself after {@code DebugRecorder.MAX_DURATION_IN_SECONDS}, so there is no stop
 * form of this command. The client logs the dump path when it finishes.
 */
public class ProfileClientCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID)
                .then(literal("profile-client")
                        .requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.profile-client", 2))
                        .executes(ProfileClientCommand::profileSelf)
                        .then(argument("players", EntityArgument.players())
                                .executes(ProfileClientCommand::profileTargets))));
    }

    private static int profileSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return profile(context, List.of(context.getSource().getPlayerOrException()));
    }

    private static int profileTargets(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return profile(context, EntityArgument.getPlayers(context, "players"));
    }

    private static int profile(CommandContext<CommandSourceStack> context,
            Collection<ServerPlayer> targets) {
        int count = 0;

        for (ServerPlayer player : targets) {
            // Counted rather than assumed. The receiver is registered only in a development
            // environment, so a client that cannot take the packet is a real case, and a harness that
            // reports a profile it never started wastes a whole run before anyone notices.
            if (!player.connection.hasChannel(new AitNetworking.Payload(AITMod.PROFILE_CLIENT, new byte[0])))
                continue;

            AitNetworking.send(player, AITMod.PROFILE_CLIENT, AitNetworking.buf());
            count++;
        }

        int started = count;
        int skipped = targets.size() - count;
        context.getSource().sendSuccess(() -> Component.literal("Started the client profiler on " + started
                + " client(s)." + (skipped > 0 ? " " + skipped + " could not take the packet." : "")), true);

        return started > 0 ? Command.SINGLE_SUCCESS : 0;
    }
}
