package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.literal;

import java.util.UUID;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.link.LinkableItem;
import dev.amble.ait.core.util.TextUtil;
import dev.amble.ait.core.world.TardisServerWorld;

public class ThisTardisCommand {

    // TODO: add BlockPosition argument type
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("this")
                .executes(ThisTardisCommand::runCommand)));
    }

    private static int runCommand(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getLevel() instanceof TardisServerWorld tardisWorld) {
            context.getSource().sendSystemMessage(Component.translatable("message.ait.id").append(TextUtil.forTardis(tardisWorld.getTardis())));
        } else if (context.getSource().isPlayer()) {
            ServerPlayer player = context.getSource().getPlayer();
            ItemStack stack = player.getMainHandItem();

            try {
                UUID id = LinkableItem.getTardisIdStatic(stack);

                if (id == null || id.toString().isEmpty()) {
                    // Since an IllegalArgumentException is automatically thrown when the held item is not linkable,
                    // we want to show the same error message when a held linkable item is not linked.
                    throw new IllegalArgumentException();
                } else
                    player.sendSystemMessage(Component.translatable("message.ait.id").append(TextUtil.forTardis(id)));
            } catch (IllegalArgumentException ignored) {
                player.sendSystemMessage(Component.translatable("command.ait.this.not_found"));
            }
        }

        return Command.SINGLE_SUCCESS;
    }
}
