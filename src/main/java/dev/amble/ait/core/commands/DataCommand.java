package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.google.gson.JsonElement;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.commands.argument.JsonElementArgumentType;
import dev.amble.ait.core.commands.argument.TardisArgumentType;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.data.properties.Value;
import dev.amble.ait.registry.impl.TardisComponentRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

public class DataCommand {

    public static final SuggestionProvider<CommandSourceStack> COMPONENT_SUGGESTION = (context,
            builder) -> SharedSuggestionProvider.suggest(
                    TardisComponentRegistry.getInstance().getValues().stream().map(TardisComponent.IdLike::name),
                    builder);

    public static final SuggestionProvider<CommandSourceStack> VALUE_SUGGESTION = (context, builder) -> {
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");
        String rawComponent = StringArgumentType.getString(context, "component");

        TardisComponent.IdLike id = TardisComponentRegistry.getInstance().get(rawComponent);

        if (!(tardis.handler(id) instanceof KeyedTardisComponent keyed))
            return builder.buildFuture(); // womp womp

        return SharedSuggestionProvider.suggest(
                keyed.getPropertyData().values().stream().map(value -> value.getProperty().getName()), builder);
    };

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("data").requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.data", 2))

                .then(argument("tardis", TardisArgumentType.tardis()).then(argument("component",
                        StringArgumentType.word())
                        .suggests(COMPONENT_SUGGESTION)
                        .then(argument("value", StringArgumentType.word()).suggests(VALUE_SUGGESTION)
                                .then(literal("set").then(argument("data", JsonElementArgumentType.jsonElement())
                                        .executes(DataCommand::runSet)))
                                .then(literal("get").executes(DataCommand::runGet)))))));
    }

    private static <T> int runGet(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");
        Value<T> value = getValue(context, tardis);

        if (value == null)
            return 0;

        T obj = value.get();

        String json = ServerTardisManager.getInstance().getFileGson().toJson(obj);

        source.sendSystemMessage(Component.translatable("command.ait.data.get",
                value.getProperty().getName(), json));

        return Command.SINGLE_SUCCESS;
    }

    @SuppressWarnings("unchecked")
    private static <T> int runSet(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");

        Value<T> value = getValue(context, tardis);

        if (value == null)
            return 0;

        JsonElement data = JsonElementArgumentType.getJsonElement(context, "data");

        Class<?> classOfT = value.getProperty().getType().getClazz();
        T obj = (T) ServerTardisManager.getInstance().getFileGson().fromJson(data, classOfT);

        value.set(obj);
        source.sendSystemMessage(Component.translatable("command.ait.data.set",
                value.getProperty().getName(), obj.toString()));

        return Command.SINGLE_SUCCESS;
    }

    private static <T> Value<T> getValue(CommandContext<CommandSourceStack> context, Tardis tardis) {
        String valueName = StringArgumentType.getString(context, "value");
        String rawComponent = StringArgumentType.getString(context, "component");

        TardisComponent.IdLike id = TardisComponentRegistry.getInstance().get(rawComponent);

        if (!(tardis.handler(id) instanceof KeyedTardisComponent keyed)) {
            context.getSource().sendSystemMessage(Component.translatable("command.ait.data.fail", valueName, rawComponent));
            return null; // womp womp
        }

        return keyed.getPropertyData().getExact(valueName);
    }
}
