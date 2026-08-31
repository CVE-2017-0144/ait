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
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.core.util.TextUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class LoadCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("load")
                .requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.load", 2)).executes(LoadCommand::load)
                .then(argument("target", TardisArgumentType.tardis())
                        .executes(LoadCommand::search))
        ));
    }

    public static int load(CommandContext<CommandSourceStack> context) {
        ServerTardisManager.getInstance().loadAll(context.getSource().getServer(), (tardis -> sendTardis(context.getSource(), tardis)));

        return Command.SINGLE_SUCCESS;
    }

    public static int search(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerTardis loaded = TardisArgumentType.getTardis(context, "target");
        CommandSourceStack source = context.getSource();
        sendTardis(source, loaded);

        return Command.SINGLE_SUCCESS;
    }

    private static void sendTardis(CommandSourceStack source, ServerTardis loaded) {
        Component message = loaded != null ? Component.translatable("command.ait.load.loaded", TextUtil.forTardis(loaded))
                : Component.translatable("command.ait.load.not_found");

        source.sendSystemMessage(message);
    }
}
