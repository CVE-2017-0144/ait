package dev.amble.lib.platform.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public class Commands {

    public interface Registration {
        void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext access,
                CommandSelection environment);
    }

    private static final Event<Registration> REGISTER = EventFactory.createArrayBacked(Registration.class,
            callbacks -> (dispatcher, access, environment) -> {
                for (Registration callback : callbacks) {
                    callback.register(dispatcher, access, environment);
                }
            });

    static {
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, e -> REGISTER.invoker()
                .register(e.getDispatcher(), e.getBuildContext(), e.getCommandSelection()));
    }

    public static void register(Registration registration) {
        REGISTER.register(registration);
    }

    // server serializes by class, client looks up the registry
    public static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void argumentType(
            ResourceLocation id, Class<A> type, ArgumentTypeInfo<A, T> info) {
        ArgumentTypeInfos.registerByClass(type, info);
        Registry.register(BuiltInRegistries.COMMAND_ARGUMENT_TYPE, id, info);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client {

        public interface Registration {
            void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext access);
        }

        private static final Event<Registration> REGISTER_CLIENT = EventFactory
                .createArrayBacked(Registration.class, callbacks -> (dispatcher, access) -> {
                    for (Registration callback : callbacks) {
                        callback.register(dispatcher, access);
                    }
                });

        static {
            NeoForge.EVENT_BUS.addListener(
                    net.neoforged.neoforge.client.event.RegisterClientCommandsEvent.class,
                    e -> REGISTER_CLIENT.invoker().register(e.getDispatcher(), e.getBuildContext()));
        }

        public static void register(Registration registration) {
            REGISTER_CLIENT.register(registration);
        }
    }
}
