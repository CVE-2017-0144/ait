package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import dev.amble.ait.AITMod;

public class VersionCommand {

    private static final ModContainer AIT = FabricLoader.getInstance().getModContainer(AITMod.MOD_ID).get();
    private static final String VERSION = AIT.getMetadata().getVersion().getFriendlyString();

    private static final Component LOGO = Component.literal("""
                ::::::\\\\     ::::::::::::|| ::::::::::::::::||
               == ==\\\\      ==||      ==||
              =======\\\\     ==||      ==||
             ##//   ##\\\\    ##||      ##||
            ##//     ##\\\\ ######||    ##||""").copy().setStyle(Style.EMPTY.withFont(new ResourceLocation("uniform")));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(
                literal("version").executes(VersionCommand::run)));
    }

    private static int run(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();

        source.sendSystemMessage(LOGO.copy().withStyle(ChatFormatting.GOLD));
        source.sendSystemMessage(Component.translatable("message.ait.version").withStyle(ChatFormatting.GOLD)
                .append(Component.literal(": ").append(VERSION).withStyle(ChatFormatting.WHITE)));

        return Command.SINGLE_SUCCESS;
    }
}
