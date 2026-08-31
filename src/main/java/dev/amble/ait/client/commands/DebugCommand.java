package dev.amble.ait.client.commands;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

import java.util.List;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;
import dev.amble.ait.AITMod;
import dev.amble.ait.registry.impl.door.ClientDoorRegistry;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import dev.amble.ait.registry.impl.exterior.ClientExteriorVariantRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import dev.amble.lib.api.Identifiable;

public class DebugCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID + "-client").then(literal("debug").executes(context -> {

            context.getSource().sendFeedback(Component.literal("Door registry: " + stringify(DoorRegistry.getInstance().toList())));
            context.getSource().sendFeedback(Component.literal("Client Door registry: " + stringify(ClientDoorRegistry.getInstance().toList())));
            context.getSource().sendFeedback(Component.literal("Exterior registry: " + stringify(ExteriorVariantRegistry.getInstance().toList())));
            context.getSource().sendFeedback(Component.literal("Client Exterior registry: " + stringify(ClientExteriorVariantRegistry.getInstance().toList())));
            DoorRegistry.getInstance().toList();
            return Command.SINGLE_SUCCESS;
        })));
    }

    public static String stringify(List<? extends Identifiable> list) {
        return list.stream().map(idlike -> idlike == null ? null : idlike.id().toString()).toList().toString();
    }
}
