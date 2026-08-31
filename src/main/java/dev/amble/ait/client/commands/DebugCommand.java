package dev.amble.ait.client.commands;

import static net.minecraft.commands.Commands.literal;

import java.util.List;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import dev.amble.ait.AITMod;
import dev.amble.ait.registry.impl.door.ClientDoorRegistry;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import dev.amble.ait.registry.impl.exterior.ClientExteriorVariantRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import dev.amble.lib.api.Identifiable;

public class DebugCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID + "-client").then(literal("debug").executes(context -> {

            context.getSource().sendSystemMessage(Component.literal("Door registry: " + stringify(DoorRegistry.getInstance().toList())));
            context.getSource().sendSystemMessage(Component.literal("Client Door registry: " + stringify(ClientDoorRegistry.getInstance().toList())));
            context.getSource().sendSystemMessage(Component.literal("Exterior registry: " + stringify(ExteriorVariantRegistry.getInstance().toList())));
            context.getSource().sendSystemMessage(Component.literal("Client Exterior registry: " + stringify(ClientExteriorVariantRegistry.getInstance().toList())));
            DoorRegistry.getInstance().toList();
            return Command.SINGLE_SUCCESS;
        })));
    }

    public static String stringify(List<? extends Identifiable> list) {
        return list.stream().map(idlike -> idlike == null ? null : idlike.id().toString()).toList().toString();
    }
}
