package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.link.LinkableItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class UnLinkCommand {

    // TODO: add slot argument, like in "/item replace" command
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("unlink")
                .executes(UnLinkCommand::runCommand)));
    }

    private static int runCommand(CommandContext<CommandSourceStack> context) {
        ServerPlayer source = context.getSource().getPlayer();
        ItemStack stack = source.getMainHandItem();

        if (!(stack.getItem() instanceof LinkableItem linker))
            return 0;

        linker.unlink(stack);
        return Command.SINGLE_SUCCESS;
    }
}
