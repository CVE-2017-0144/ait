package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.commands.argument.GroundSearchArgumentType;
import dev.amble.ait.core.util.SafePosSearch;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.level.ServerLevel;

public class SafePosCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                literal(AITMod.MOD_ID).then(literal("safe-pos").requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.safe-pos", 2))
                        .then(argument("world", DimensionArgument.dimension())
                                .then(argument("pos", BlockPosArgument.blockPos())
                                        .then(argument("search-type", GroundSearchArgumentType.groundSearch())
                                                .executes(SafePosCommand::execute))))));
    }

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerLevel world = DimensionArgument.getDimension(context, "world");
        BlockPos posA = BlockPosArgument.getBlockPos(context, "pos");

        SafePosSearch.Kind search = GroundSearchArgumentType.getGroundSearch(context, "search-type");
        CachedDirectedGlobalPos pos = CachedDirectedGlobalPos.create(world, posA, (byte) 0);

        SafePosSearch.wrapSafe(pos, search, false,
                result -> reply(context, result.getPos()));

        return Command.SINGLE_SUCCESS;
    }

    private static void reply(CommandContext<CommandSourceStack> context, BlockPos blockPos) {
        Component text = ComponentUtils
                .wrapInSquareBrackets(Component.translatable("chat.coordinates", blockPos.getX(), blockPos.getY(), blockPos.getZ()))
                .withStyle((style) -> style.withColor(ChatFormatting.GREEN)
                        .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                                "/tp @s " + blockPos.getX() + " " + blockPos.getY() + " " + blockPos.getZ()))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                Component.translatable("chat.coordinates.tooltip"))));

        context.getSource().sendSystemMessage(text);
    }
}
