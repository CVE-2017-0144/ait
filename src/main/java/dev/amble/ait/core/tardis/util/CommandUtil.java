package dev.amble.ait.core.tardis.util;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.world.TardisServerWorld;

public class CommandUtil {

    public static final SuggestionProvider<CommandSourceStack> NON_TARDIS_DIM_SUGGESTIONS =
            (CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) -> {
                MinecraftServer server = ctx.getSource().getServer();

                for (ResourceKey<Level> key : server.levelKeys()) {
                    ServerLevel world = server.getLevel(key);
                    if (world == null)
                        continue;

                    if (!TardisServerWorld.isTardisDimension(world)
                            && !key.location().equals(AITDimensions.TIME_VORTEX_WORLD.location())
                            && !key.location().toString().equals("ait:tardis_dimension_type")) {
                        builder.suggest(key.location().toString());
                    }
                }
                return builder.buildFuture();
            };

    public static final SuggestionProvider<CommandSourceStack> DIRECTION = (ctx, b) -> {
        String[] opts = {
                "north",
                "north_east",
                "east",
                "south_east",
                "south",
                "south_west",
                "west",
                "north_west"
        };
        for (String o : opts) b.suggest(o);
        return b.buildFuture();
    };

    public static boolean hasArgument(CommandContext<CommandSourceStack> context, String name) {
        for (ParsedCommandNode<CommandSourceStack> node : context.getNodes()) {
            if (node.getNode().getName().equals(name))
                return true;
        }

        return false;
    }
}
