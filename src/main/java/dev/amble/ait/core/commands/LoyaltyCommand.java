package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.commands.argument.TardisArgumentType;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.handler.LoyaltyHandler;
import dev.amble.ait.data.Loyalty;

public class LoyaltyCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("loyalty")
                .requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.loyalty", 2))
                .then(argument("tardis", TardisArgumentType.tardis())
                        .then(argument("player", EntityArgument.player()).executes(LoyaltyCommand::get).then(
                                argument("value", IntegerArgumentType.integer()).executes(LoyaltyCommand::set))))));
    }

    private static int set(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommonArgs args = CommonArgs.create(context);
        int value = IntegerArgumentType.getInteger(context, "value");

        args.run("ait.command.loyalty.set", "Set loyalty for player %s to rank %s level %s",
                handler -> handler.set(args.player, Loyalty.fromLevel(value)));

        return Command.SINGLE_SUCCESS;
    }

    private static int get(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommonArgs args = CommonArgs.create(context);

        return args.run("ait.command.loyalty.get", "Player %s has rank %s with level %s",
                handler -> handler.get(args.player)).level();
    }

    record CommonArgs(CommandSourceStack source, ServerTardis tardis, ServerPlayer player) {

        public static CommonArgs create(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");
            ServerPlayer player = EntityArgument.getPlayer(context, "player");

            return new CommonArgs(context.getSource(), tardis, player);
        }

        public Loyalty run(String key, String fallback, Function<LoyaltyHandler, Loyalty> func) {
            Loyalty result = func.apply(this.tardis.loyalty());

            this.source.sendSuccess(() -> Component.translatableWithFallback(key, fallback, this.player.getName(),
                    result.type(), result.level()), false);

            return result;
        }
    }
}
