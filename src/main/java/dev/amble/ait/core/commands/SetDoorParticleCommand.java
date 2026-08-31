package dev.amble.ait.core.commands;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.amble.ait.AITMod;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.ait.core.commands.argument.TardisArgumentType;
import dev.amble.ait.core.tardis.ServerTardis;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;

public class SetDoorParticleCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext access) {
        dispatcher.register(literal(AITMod.MOD_ID).then(literal("door_particle").requires(source -> PermissionAPICompat.hasPermission(source, "ait.command.door_particle", 2))
                .then(argument("tardis", TardisArgumentType.tardis()).then(argument("particle_type", ParticleArgument.particle(access)).executes(SetDoorParticleCommand::execute)))));
    }

    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerTardis tardis = TardisArgumentType.getTardis(context, "tardis");
        ParticleOptions particle = ParticleArgument.getParticle(context, "particle_type");

        tardis.door().setDoorParticles(particle);

        source.sendSuccess(
                () -> Component.translatableWithFallback("command.ait.door_particle.done", "Particle of [%s] set to [%s]", tardis.getUuid(), particle.writeToString()),
                true);

        return Command.SINGLE_SUCCESS;
    }
}
