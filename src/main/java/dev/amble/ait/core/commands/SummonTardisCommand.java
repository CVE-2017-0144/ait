package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.commands.argument.TardisArgumentType;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.handler.travel.TravelUtil;
import dev.amble.ait.core.tardis.util.CommandUtil;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.properties.RotationSegment;

public class SummonTardisCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                literal(AITMod.MOD_ID)
                        .then(literal("summon")
                                .requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.summon", 2))
                                .then(argument("tardis", TardisArgumentType.tardis())
                                        .executes(SummonTardisCommand::runCommand)
                                        .then(literal("home")
                                                .executes(SummonTardisCommand::runCommandWithHome)
                                                .then(argument("showMessage", BoolArgumentType.bool())
                                                        .executes(SummonTardisCommand::runCommandWithHomeAndMessage)
                                                )
                                        )
                                        .then(argument("pos", BlockPosArgument.blockPos())
                                                .executes(SummonTardisCommand::runCommandWithPos)
                                                .then(argument("showMessage", BoolArgumentType.bool())
                                                        .executes(SummonTardisCommand::runCommandWithPosAndMessage)
                                                )
                                        )
                                )
                        )
        );
    }

    private static int runCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return summonTardis(context, null, true);  // Default to showing the message
    }

    private static int runCommandWithPos(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        BlockPos pos = BlockPosArgument.getBlockPos(context, "pos");
        return summonTardis(context, pos, true);
    }

    private static int runCommandWithPosAndMessage(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        BlockPos pos = BlockPosArgument.getBlockPos(context, "pos");
        boolean showMessage = BoolArgumentType.getBool(context, "showMessage");
        return summonTardis(context, pos, showMessage);
    }

    private static int runCommandWithHome(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");

        if (tardis == null)
            throw TardisArgumentType.INVALID_UUID.create();

        BlockPos pos = tardis.stats().getHome().getPos();
        return summonTardis(context, pos, true);
    }

    private static int runCommandWithHomeAndMessage(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");
        boolean showMessage = BoolArgumentType.getBool(context, "showMessage");

        if (tardis == null)
            throw TardisArgumentType.INVALID_UUID.create();

        BlockPos pos = tardis.stats().getHome().getPos();
        return summonTardis(context, pos, showMessage);
    }

    private static int summonTardis(CommandContext<CommandSourceStack> context, @Nullable BlockPos pos, boolean showMessage) throws CommandSyntaxException {
        Entity source = context.getSource().getEntity();
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");
        CachedDirectedGlobalPos globalPos;

        if (pos == null)
            pos = source.blockPosition();

        if (CommandUtil.hasArgument(context, "home")) {
            globalPos = tardis.stats().getHome();
        }else {
            globalPos = CachedDirectedGlobalPos.create((ServerLevel) source.level(), pos,
                    (byte) RotationSegment.convertToSegment(source.getVisualRotationYInDegrees()));
        }

        TravelUtil.travelTo(tardis, globalPos);

        if (showMessage) {
            source.sendSystemMessage(Component.translatableWithFallback("tardis.summon", "TARDIS [%s] is on the way!",
                    tardis.getUuid().toString().substring(0, 7)));
        }

        return Command.SINGLE_SUCCESS;
    }
}
