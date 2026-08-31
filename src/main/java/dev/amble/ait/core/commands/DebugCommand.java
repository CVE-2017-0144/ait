package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.WorldWithTardis;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.commands.argument.TardisArgumentType;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.util.NetworkUtil;
import dev.amble.ait.core.world.LandingPadManager;
import dev.amble.ait.data.landing.LandingPadRegion;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class DebugCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("debug").requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.debug", 2)).executes(DebugCommand::execute)
                .then(argument("tardis", TardisArgumentType.tardis()).executes(DebugCommand::executeTardis)
                        .then(argument("player", EntityArgument.player()).executes(DebugCommand::executePlayer)))));

    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();

        if (!source.isPlayer())
            return Command.SINGLE_SUCCESS;

        ServerLevel world = source.getLevel();
        Player player = source.getPlayer();

        LandingPadRegion region = LandingPadManager.getInstance(world).getRegion(player.chunkPosition());

        if (region != null)
            source.sendSystemMessage(Component.literal("LP in chunk: " + region));

        ((WorldWithTardis) context.getSource().getLevel()).ait$withLookup(lookup -> {
            source.sendSystemMessage(Component.empty());
            source.sendSystemMessage(Component.literal("TARDIS in chunk: " + lookup.get(source.getPlayer().chunkPosition())));
        });

        return Command.SINGLE_SUCCESS;
    }

    private static int executeTardis(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();

        if (!source.isPlayer())
            return 0;

        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");

        context.getSource().getServer().executeIfPossible(() -> {
            tardis.chameleon().clearDisguise();
            tardis.chameleon().applyDisguise();
        });
        return Command.SINGLE_SUCCESS;
    }

    private static int executePlayer(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");

        long start = System.nanoTime();
        NetworkUtil.hasLinkedItem(tardis, player);

        context.getSource().sendSuccess(() -> Component.literal("Checked player in "
                + (System.nanoTime() - start) + "ns"), false);

        return Command.SINGLE_SUCCESS;
    }
}
