package dev.amble.ait.core.handles;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.lib.api.Identifiable;
import dev.amble.lib.util.Levenshtein;

/**
 * For Handles the robot, this is used to handle responses.
 * @author james
 */
public interface HandlesResponse extends Identifiable {
    /**
     * Send a chat message to the target player.
     * Auto prefixed with <Handles> for the message.
     * @param target The player to send the message to.
     * @param message The message to send.
     */
    default void sendChat(ServerPlayer target, Component message) {
        message = Component.literal("<Handles> ").append(message);
        target.displayClientMessage(message, false);
    }

    /**
     * @return The sound to play when the command fails.
     */
    default SoundEvent failureSound() {
        return AITSounds.HANDLES_DENIED;
    }

    /**
     * @return The sound to play when the command succeeds.
     */
    default SoundEvent successSound() {
        return AITSounds.HANDLES_AFFIRMATIVE;
    }

    default boolean success(HandlesSound source) {
        source.playSound(successSound(), SoundSource.PLAYERS, 1.0f, 1.0f);
        return true;
    }

    default boolean failure(HandlesSound source) {
        source.playSound(failureSound(), SoundSource.PLAYERS, 1.0f, 1.0f);
        return false;
    }

    /**
     * Run the responses.
     * @param player player who invoked this responses
     * @param source sound handler
     * @param tardis handles linked tardis
     * @return Whether the responses was successful.
     */
    boolean run(ServerPlayer player, HandlesSound source, ServerTardis tardis);

    /**
     * @param command keyword to search for
     * @return whether the word given is a command word for this responses
     */
    default boolean isCommand(String command) {
        return getCommandWords().contains(command.toLowerCase());
    }

    // i have no idea how this shit fucking works
    default int distance(String command, String input) {
        return Levenshtein.distance(command, input);
    }

    /**
     * @return Whether a non-secure person can run the command when P19 is on.
     */
    default boolean requiresSudo() {
        return true;
    }

    /**
     * @return The command words that this responses can handle.
     */
    List<String> getCommandWords();
}
