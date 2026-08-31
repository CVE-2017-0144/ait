package dev.amble.ait.client.commands;

import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.config.AITConfigScreen;


public class ConfigCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID + "-client").then(literal("config").executes(context -> {
            Minecraft client = Minecraft.getInstance();
            client.tell(() -> client.setScreen(AITConfigScreen.create(null)));
            return Command.SINGLE_SUCCESS;
        })));
    }
}
