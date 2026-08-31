package dev.amble.lib.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.amble.lib.AmbleKit;
import dev.amble.lib.animation.AnimatedEntity;
import dev.amble.lib.client.bedrock.BedrockAnimationReference;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class PlayAnimationCommand {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(literal(AmbleKit.MOD_ID)
				.requires(source -> source.hasPermission(2))
				.then(literal("animation").then(argument("target", EntityArgument.entity())
						.then(argument("id", ResourceLocationArgument.id()).executes(PlayAnimationCommand::execute)))));
	}

	private static int execute(CommandContext<CommandSourceStack> context) {
		ResourceLocation animationId = ResourceLocationArgument.getId(context, "id");
		Entity target;

		try {
			target = EntityArgument.getEntity(context, "target");
		} catch (Exception e) {
			context.getSource().sendFailure(Component.literal("Invalid Target, using self."));
			target = context.getSource().getEntity();
		}

		if (!(target instanceof AnimatedEntity animated)) {
			context.getSource().sendFailure(Component.literal("Target is not an AnimatedEntity"));
			return 0;
		}

		animated.playAnimation(BedrockAnimationReference.parse(animationId));

		String name = target.getScoreboardName();
		context.getSource().sendSuccess(() -> Component.literal("Playing animation "+ animationId +" on "+ name), true);
		return 1;
	}
}
