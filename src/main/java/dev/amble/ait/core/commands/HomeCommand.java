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
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.commands.argument.TardisArgumentType;
import dev.amble.ait.core.lock.LockedDimensionRegistry;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.util.CommandUtil;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.data.DirectedGlobalPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public final class HomeCommand {

    private HomeCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                literal(AITMod.MOD_ID)
                        .then(literal("home")
                                .requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.home", 2))
                                .then(literal("get")
                                        .executes(HomeCommand::runGet)
                                        .then(argument("tardis", TardisArgumentType.tardis())
                                                .executes(HomeCommand::runGet)))
                                .then(literal("set")
                                        .executes(HomeCommand::runSet)
                                        .then(argument("tardis", TardisArgumentType.tardis())
                                                .executes(HomeCommand::runSet)
                                                .then(argument("dimension", DimensionArgument.dimension())
                                                .suggests(CommandUtil.NON_TARDIS_DIM_SUGGESTIONS)
                                                        .then(argument("position", BlockPosArgument.blockPos())
                                                                .executes(HomeCommand::runSet)
                                                                .then(argument("facing", StringArgumentType.word())
                                                                        .suggests(CommandUtil.DIRECTION)
                                                                        .executes(HomeCommand::runSet)
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
        );
    }

    private static int runGet(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerTardis tardis = resolveTardis(context);
        CachedDirectedGlobalPos homePos = tardis.stats().getHome();

        if (homePos == null)
            return -1;

        return printHome(context, homePos);
    }

    private static int runSet(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerTardis tardis = resolveTardis(context);
        CachedDirectedGlobalPos homePos = tardis.stats().getHome();

        if (CommandUtil.hasArgument(context, "position")) {
            ServerLevel world = DimensionArgument.getDimension(context, "dimension");
            ResourceKey<Level> key = world.dimension();
            BlockPos manualPos = BlockPosArgument.getBlockPos(context, "position");
            byte rotation;

            if (TardisServerWorld.isTardisDimension(world)
                    || key.location().equals(AITDimensions.TIME_VORTEX_WORLD.location())
                    || key.location().toString().equals("ait:tardis_dimension_type"))
                return -1;

            if (!LockedDimensionRegistry.getInstance().isUnlocked(tardis, world)) {
                context.getSource().sendFailure(Component.translatable("command.ait.home.dimension_locked",
                        WorldUtil.worldText(world.dimension(), false)));
                return -1;
            }

            if (CommandUtil.hasArgument(context, "facing")) {
                rotation = WorldUtil.stringName2Rot(StringArgumentType.getString(context, "facing"));
            }else {
                rotation = WorldUtil.tardis2Rot(tardis);
            }

            homePos = CachedDirectedGlobalPos.create(world, manualPos, rotation);
            tardis.stats().setHome(homePos);
        } else {
            CachedDirectedGlobalPos current = tardis.travel().position();

            if (current == null)
                return -1;

            homePos = current;
            tardis.stats().setHome(current);
        }

        return printHome(context, homePos);
    }

    private static int printHome(CommandContext<CommandSourceStack> context, CachedDirectedGlobalPos homePos) {
        BlockPos blockPos = homePos.getPos();
        String facing = WorldUtil.rot2StringName(homePos.getRotation());
        String arrow = DirectedGlobalPos.rotationForArrow(homePos.getRotation());
        ResourceLocation dimension = homePos.getDimension().location();

        context.getSource().sendSystemMessage(Component.literal(
                blockPos.getX() + " " + blockPos.getY() + " " + blockPos.getZ() + " (" + facing + " " + arrow + ") " + dimension));
        return Command.SINGLE_SUCCESS;
    }

    private static ServerTardis resolveTardis(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        if (CommandUtil.hasArgument(context, "tardis"))
            return TardisArgumentType.getTardis(context, "tardis");

        throw TardisArgumentType.INVALID_UUID.create();
    }

}