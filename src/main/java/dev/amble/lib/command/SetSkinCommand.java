package dev.amble.lib.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.amble.lib.AmbleKit;
import dev.amble.lib.skin.PlayerSkinTexturable;
import dev.amble.lib.skin.SkinData;
import dev.amble.lib.skin.SkinTracker;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class SetSkinCommand {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(literal(AmbleKit.MOD_ID)
				.requires(source -> source.hasPermission(2))
				.then(literal("skin").then(argument("target", EntityArgument.entity())
								.then(literal("clear").executes(SetSkinCommand::executeClear))
								.then(literal("slim").then(argument("slim", BoolArgumentType.bool())
										.executes(SetSkinCommand::executeSlim).then(argument("value", StringArgumentType.greedyString()).executes(SetSkinCommand::executeWithSlim))))
								.then(literal("set").then(argument("value", StringArgumentType.greedyString())
												.executes(SetSkinCommand::execute))))));
	}

	private static int executeClear(CommandContext<CommandSourceStack> context) {
		PlayerSkinTexturable texturable;
		Entity entity;

		try {
			entity = EntityArgument.getEntity(context, "target");

			if (!(entity instanceof PlayerSkinTexturable)) {
				context.getSource().sendFailure(Component.literal("Target is not a PlayerSkinTexturable"));
				return 0;
			}
			texturable = (PlayerSkinTexturable) entity;

		} catch (CommandSyntaxException e) {
			context.getSource().sendFailure(Component.literal("Invalid Target"));
			return 0;
		}

		SkinTracker.getInstance().removeSynced(texturable.getUUID());
		String username = entity.getScoreboardName();
		context.getSource().sendSuccess(() -> Component.literal("Cleared skin of "+ username), true);

		return 1;
	}

	private static int executeSlim(CommandContext<CommandSourceStack> context) {
		boolean slim = BoolArgumentType.getBool(context, "slim");
		PlayerSkinTexturable texturable;
		Entity entity;

		try {
			entity = EntityArgument.getEntity(context, "target");

			if (!(entity instanceof PlayerSkinTexturable)) {
				context.getSource().sendFailure(Component.literal("Target is not a PlayerSkinTexturable"));
				return 0;
			}
			texturable = (PlayerSkinTexturable) entity;

		} catch (CommandSyntaxException e) {
			context.getSource().sendFailure(Component.literal("Invalid Target"));
			return 0;
		}

		SkinData data = SkinTracker.getInstance().get(texturable.getUUID());
		if (data == null) {
			context.getSource().sendFailure(Component.literal("Target is not disguised."));
			return 0;
		}

		data = data.withSlim(slim);

		SkinTracker.getInstance().putSynced(texturable.getUUID(), data);

		String username = entity.getScoreboardName();
		context.getSource().sendSuccess(() -> Component.literal("Set slimness of "+ username +" to " + slim), true);

		return 1;
	}

	private static int execute(CommandContext<CommandSourceStack> context) {
		String value = StringArgumentType.getString(context, "value");
		PlayerSkinTexturable texturable;
		Entity entity;

		try {
			entity = EntityArgument.getEntity(context, "target");

			if (!(entity instanceof PlayerSkinTexturable)) {
				context.getSource().sendFailure(Component.literal("Target is not a PlayerSkinTexturable"));
				return 0;
			}
			texturable = (PlayerSkinTexturable) entity;

		} catch (CommandSyntaxException e) {
			context.getSource().sendFailure(Component.literal("Invalid Target"));
			return 0;
		}

		boolean isUrl = value.startsWith("http://") || value.startsWith("https://");

		if (isUrl) {
			SkinData data = SkinData.url(value, false);

			SkinTracker.getInstance().putSynced(texturable.getUUID(), data);

			String username = entity.getScoreboardName();
			context.getSource().sendSuccess(() -> Component.literal("Set skin of " + username + " to " + value), true);

			return 1;
		}

		SkinData.username(value, result -> {
			result.upload(entity.getUUID());

			String username = entity.getScoreboardName();
			context.getSource().sendSuccess(() -> Component.literal("Set skin of " + username + " to " + value), true);
		});

		return 1;
	}

	private static int executeWithSlim(CommandContext<CommandSourceStack> context) {
		String value = StringArgumentType.getString(context, "value");
		boolean slim = BoolArgumentType.getBool(context, "slim");
		PlayerSkinTexturable texturable;
		Entity entity;

		try {
			entity = EntityArgument.getEntity(context, "target");

			if (!(entity instanceof PlayerSkinTexturable)) {
				context.getSource().sendFailure(Component.literal("Target is not a PlayerSkinTexturable"));
				return 0;
			}
			texturable = (PlayerSkinTexturable) entity;

		} catch (CommandSyntaxException e) {
			context.getSource().sendFailure(Component.literal("Invalid Target"));
			return 0;
		}

		boolean isUrl = value.startsWith("http://") || value.startsWith("https://");

		SkinData data = isUrl ? SkinData.url(value, false) : SkinData.username(value, slim);

		SkinTracker.getInstance().putSynced(texturable.getUUID(), data);

		String username = entity.getScoreboardName();
		context.getSource().sendSuccess(() -> Component.literal("Set skin of "+ username +" to " + value), true);

		return 1;
	}
}
