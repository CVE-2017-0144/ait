package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.Nameable;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.commands.argument.IdentifierWildcardArgumentType;
import dev.amble.ait.core.commands.argument.TardisArgumentType;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.data.Wildcard;
import dev.amble.ait.registry.impl.DesktopRegistry;
import dev.amble.ait.registry.impl.console.variant.ConsoleVariantRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import dev.amble.lib.api.Identifiable;
import dev.amble.lib.register.unlockable.Unlockable;
import dev.amble.lib.register.unlockable.UnlockableRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class UnlockCommand {

    public static final SuggestionProvider<CommandSourceStack> CONSOLE_SUGGESTION = (context,
            builder) -> IdentifierWildcardArgumentType.suggestWildcardIds(builder,
                    ConsoleVariantRegistry.getInstance());
    public static final SuggestionProvider<CommandSourceStack> DESKTOP_SUGGESTION = (context,
            builder) -> IdentifierWildcardArgumentType.suggestWildcardIds(builder, DesktopRegistry.getInstance());
    public static final SuggestionProvider<CommandSourceStack> EXTERIOR_SUGGESTION = (context,
            builder) -> IdentifierWildcardArgumentType.suggestWildcardIds(builder,
                    ExteriorVariantRegistry.getInstance());

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("unlock")
                .requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.unlock", 2))
                .then(argument("tardis", TardisArgumentType.tardis())
                        .then(literal("console").then(argument("console", IdentifierWildcardArgumentType.wildcard())
                                .suggests(CONSOLE_SUGGESTION).executes(UnlockCommand::unlockConsole)))
                        .then(literal("desktop").then(argument("desktop", IdentifierWildcardArgumentType.wildcard())
                                .suggests(DESKTOP_SUGGESTION).executes(UnlockCommand::unlockDesktop)))
                        .then(literal("exterior").then(argument("exterior", IdentifierWildcardArgumentType.wildcard())
                                .suggests(EXTERIOR_SUGGESTION).executes(UnlockCommand::unlockExterior))))));
    }

    private static <T extends Identifiable & Unlockable & Nameable> int unlock(
            CommandContext<CommandSourceStack> context, Component type, Wildcard<T> wildcard,
            UnlockableRegistry<T> registry) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");

        if (wildcard.isPresent()) {
            T t = wildcard.get();
            source.getServer().execute(() -> tardis.stats().unlock(t));

            source.sendSystemMessage(Component.translatableWithFallback("command.ait.unlock.some", "Granted [%s] %s %s",
                    tardis.getUuid(), t.name(), type));

            return Command.SINGLE_SUCCESS;
        }

        source.getServer().execute(() -> registry.unlockAll(tardis));
        source.sendSystemMessage(Component.translatableWithFallback("command.ait.unlock.all", "Granted [%s] every %s",
                tardis.getUuid(), type));

        return Command.SINGLE_SUCCESS;
    }

    private static int unlockConsole(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return unlock(context, Component.translatable("command.ait.unlock.type.console"),
                IdentifierWildcardArgumentType.getConsoleVariantArgument(context, "console"), ConsoleVariantRegistry.getInstance());
    }

    private static int unlockDesktop(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return unlock(context, Component.translatable("command.ait.unlock.type.desktop"),
                IdentifierWildcardArgumentType.getDesktopArgument(context, "desktop"), DesktopRegistry.getInstance());
    }

    private static int unlockExterior(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return unlock(context, Component.translatable("command.ait.unlock.type.exterior_variant"),
                IdentifierWildcardArgumentType.getExteriorVariantArgument(context, "exterior"),
                ExteriorVariantRegistry.getInstance());
    }
}
