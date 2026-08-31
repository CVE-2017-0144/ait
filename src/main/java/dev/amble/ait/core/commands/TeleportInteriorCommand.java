package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.commands.argument.TardisArgumentType;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.core.util.TextUtil;

public final class TeleportInteriorCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("teleport").requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.teleport", 2))
                .then(argument("tardis", TardisArgumentType.tardis())
                                .then(literal("interior").executes(TeleportInteriorCommand::tpSelfInterior)
                                        .then(argument("entities", EntityArgument.players())
                                                .executes(TeleportInteriorCommand::tpToInterior)))
                                .then(literal("exterior").executes(TeleportInteriorCommand::tpSelfExterior)
                                        .then(argument("entities", EntityArgument.players())
                                                .executes(TeleportInteriorCommand::tpToExterior)))
                )));
    }

    private static int tpSelfInterior(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity source = context.getSource().getEntity();
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");

        return tpToInterior(tardis, Collections.singleton(source));
    }

    private static int tpSelfExterior(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity source = context.getSource().getEntity();
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");

        return tpToExterior(tardis, Collections.singleton(source));
    }

    private static int tpToInterior(CommandContext<CommandSourceStack> context)
            throws CommandSyntaxException {
        Entity source = context.getSource().getEntity();
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");
        Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");

        return tpToInterior(tardis, source, entities);
    }

    private static int tpToExterior(CommandContext<CommandSourceStack> context)
            throws CommandSyntaxException {
        Entity source = context.getSource().getEntity();
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");
        Collection<? extends Entity> entities = EntityArgument.getEntities(context, "entities");

        return tpToExterior(tardis, source, entities);
    }

    private static int tpToInterior(ServerTardis tardis, Entity source, Collection<? extends Entity> players) {
        for (Entity player : players) {
            TardisUtil.teleportInside(tardis, player);
        }

        source.sendSystemMessage(Component.translatable("tardis.teleport.interior.success", TextUtil.forTardis(tardis)));

        return Command.SINGLE_SUCCESS;
    }

    private static int tpToExterior(ServerTardis tardis, Entity source, Collection<? extends Entity> players) {
        for (Entity player : players) {
            TardisUtil.teleportOutside(tardis, player);
        }

        source.sendSystemMessage(Component.translatable("tardis.teleport.exterior.success", TextUtil.forTardis(tardis)));

        return Command.SINGLE_SUCCESS;
    }

    private static int tpToInterior(ServerTardis tardis, Collection<? extends Entity> players) {
        if (players.isEmpty())
            return 0;

        return tpToInterior(tardis, players.stream().findFirst().get(), players);
    }

    private static int tpToExterior(ServerTardis tardis, Collection<? extends Entity> players) {
        if (players.isEmpty())
            return 0;

        return tpToExterior(tardis, players.stream().findFirst().get(), players);
    }
}
