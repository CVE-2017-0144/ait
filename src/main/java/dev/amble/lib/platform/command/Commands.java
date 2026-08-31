package dev.amble.lib.platform.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public final class Commands {

    private Commands() {}

    public interface Registration {
        void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext access,
                CommandSelection environment);
    }

    public static void register(Registration registration) {
        CommandRegistrationCallback.EVENT.register(registration::register);
    }

    public static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void argumentType(
            ResourceLocation id, Class<A> type, ArgumentTypeInfo<A, T> info) {
        ArgumentTypeRegistry.registerArgumentType(id, type, info);
    }

    @Environment(EnvType.CLIENT)
    public static final class Client {

        private Client() {}

        public interface Registration {
            void register(
                    CommandDispatcher<net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource> dispatcher,
                    CommandBuildContext access);
        }

        public static void register(Registration registration) {
            net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback.EVENT
                    .register(registration::register);
        }
    }
}
