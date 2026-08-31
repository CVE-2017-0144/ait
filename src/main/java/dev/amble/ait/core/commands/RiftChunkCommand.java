package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.world.RiftChunkManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

public class RiftChunkCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("rift_chunk")
                .requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.rift_chunk", 2))
                .then(literal("check")
                        .then(argument("position", BlockPosArgument.blockPos()).executes(RiftChunkCommand::check)))
                .then(literal("get")
                        .then(argument("position", BlockPosArgument.blockPos()).executes(RiftChunkCommand::get)))
                .then(literal("set").then(argument("position", BlockPosArgument.blockPos())
                        .then(argument("artron", DoubleArgumentType.doubleArg()).executes(RiftChunkCommand::set))))));
    }

    private static int check(CommandContext<CommandSourceStack> context) {
        BlockPos targetBlockPos = BlockPosArgument.getBlockPos(context, "position");
        CommandSourceStack source = context.getSource();

        boolean isARiftChunk = RiftChunkManager.isRiftChunk(source.getLevel(), targetBlockPos);
        Component isriftchunk = Component.translatable("message.ait.sonic.riftfound");
        Component notriftchunk = Component.translatable("message.ait.sonic.riftnotfound");

        source.sendSystemMessage((isARiftChunk ? isriftchunk : notriftchunk));
        return 1;
    }

    private static int get(CommandContext<CommandSourceStack> context) {
        BlockPos targetBlockPos = BlockPosArgument.getBlockPos(context, "position");
        CommandSourceStack source = context.getSource();

        boolean isARiftChunk = RiftChunkManager.isRiftChunk(source.getLevel(), targetBlockPos);

        ServerLevel world = source.getLevel();

        Component message = !isARiftChunk
                ? Component.translatable("command.ait.riftchunk.cannotgetlevel")
                : Component.translatable("command.ait.riftchunk.getlevel",
                    RiftChunkManager.getInstance(world).getArtron(new ChunkPos(targetBlockPos)));

        source.sendSystemMessage(message);
        return 1;
    }

    private static int set(CommandContext<CommandSourceStack> context) {
        BlockPos targetBlockPos = BlockPosArgument.getBlockPos(context, "position");
        CommandSourceStack source = context.getSource();

        Component message;

        if (!RiftChunkManager.isRiftChunk(source.getLevel(), targetBlockPos)) {
            message = Component.translatable("command.ait.riftchunk.cannotsetlevel");
        } else {
            double artron = DoubleArgumentType.getDouble(context, "artron");

            ServerLevel world = source.getLevel();
            RiftChunkManager.getInstance(world).setCurrentFuel(new ChunkPos(targetBlockPos), artron);

            message = Component.translatable("command.ait.riftchunk.setlevel", artron);
        }

        source.sendSystemMessage(message);
        return 1;
    }
}
