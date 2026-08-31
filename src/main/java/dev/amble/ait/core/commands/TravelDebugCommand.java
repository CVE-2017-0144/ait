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
import dev.amble.ait.core.tardis.Tardis;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class TravelDebugCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("travel")
                .requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.travel", 2))
                .then(argument("tardis", TardisArgumentType.tardis())
                        .then(literal("demat").executes(TravelDebugCommand::demat))
                        .then(literal("destination")
                                .then(literal("home").executes(TravelDebugCommand::setHome))
                                .then(argument("dimension", DimensionArgument.dimension())
                                        .then(argument("pos", BlockPosArgument.blockPos())
                                                .executes(TravelDebugCommand::setPos))))
                        .then(literal("remat").executes(TravelDebugCommand::remat)))));
    }

    private static int demat(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Tardis tardis = TardisArgumentType.getTardis(context, "tardis");
        tardis.travel().dematerialize();

        return Command.SINGLE_SUCCESS;
    }

    private static int setPos(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Tardis tardis = TardisArgumentType.getTardis(context, "tardis");
        ServerLevel world = DimensionArgument.getDimension(context, "dimension");
        BlockPos pos = BlockPosArgument.getBlockPos(context, "pos");

        tardis.travel().forceDestination(cached -> cached.world(world).pos(pos));
        return Command.SINGLE_SUCCESS;
    }

    private static int setHome(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Tardis tardis = TardisArgumentType.getTardis(context, "tardis");

        tardis.travel().forceDestination(cached -> tardis.stats().getHome());
        return Command.SINGLE_SUCCESS;
    }

    private static int remat(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Tardis tardis = TardisArgumentType.getTardis(context, "tardis");
        tardis.travel().rematerialize();

        return Command.SINGLE_SUCCESS;
    }
}
