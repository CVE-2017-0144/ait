package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.drtheo.queue.api.util.block.ChunkEraser;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.world.level.block.Block;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;

public class EraseChunksCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("erase-chunks").requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.erase-chunks", 2))
                .then(argument("from", ColumnPosArgument.columnPos())
                        .then(argument("to", ColumnPosArgument.columnPos())
                                .executes(EraseChunksCommand::execute)))));

    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        ColumnPos from = ColumnPosArgument.getColumnPos(context, "from");
        ColumnPos to = ColumnPosArgument.getColumnPos(context, "to");

        new ChunkEraser.Builder()
                .withFlags(Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_IMMEDIATE)
                .build(context.getSource().getLevel(), from.toChunkPos(), to.toChunkPos())
                .execute();

        return Command.SINGLE_SUCCESS;
    }
}
