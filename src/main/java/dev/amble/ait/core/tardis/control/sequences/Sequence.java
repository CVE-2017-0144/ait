package dev.amble.ait.core.tardis.control.sequences;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import org.jetbrains.annotations.Nullable;

// should this be an interface? - No :)
public class Sequence {
    public ResourceLocation id() {
        return AITMod.id("sequence");
    }

    /**
     * A list of controls needed to execute the sequence, in the order they should
     * be executed
     */
    public List<Control> getControls() {
        return new ArrayList<>();
    }

    public Long timeToFail() {
        return 0L;
    }

    /**
     * @param recent
     *            Compares the recent controls to this sequence, if everything
     *            matches then it is finished
     */
    public boolean isFinished(RecentControls recent) {
        return recent.equals(this.getControls());
    }

    public void execute(Tardis tardis, @Nullable ServerPlayer player) {
        if (player != null) {
            tardis.loyalty().get(player).add(1);
        }
    }

    public void executeMissed(Tardis tardis, @Nullable ServerPlayer player) {
        if (player != null) {
            tardis.loyalty().get(player).subtract(2);
        }
    }

    public Component sequenceStartMessage() {
        return Component.nullToEmpty("");
    }

    public boolean wasMissed(RecentControls recent, int ticks) {
        if (recent.size() >= this.getControls().size() && recent != this.getControls()) {
            recent.clear();
        }
        return ticks >= this.timeToFail() /* || (recent.size() > this.getControls().size()) */;
    }

    public boolean controlPartOfSequence(Control control) {
        return this.getControls().contains(control);
    }

    public void sendMessageToInteriorPlayers(List<ServerPlayer> playersInInterior) {
        if (playersInInterior.isEmpty())
            return;
        for (ServerPlayer player : playersInInterior) {
            player.displayClientMessage(this.sequenceStartMessage(), true);
        }
    }

    public interface ExecuteSequence {
        void run(Tardis tardis);
    }

    public static class Builder extends Sequence {

        private final ResourceLocation id;
        private final List<Control> controls;
        private final ExecuteSequence execute;
        private final ExecuteSequence executeMissed;
        private final Long timeToFail;
        private final Component sequenceStartMessage;

        private Builder(ResourceLocation id, ExecuteSequence execute, ExecuteSequence executeMissed, Long timeToFail,
                Component sequenceStartMessage, Control... controls) {
            this.id = id;
            this.controls = List.of(controls);
            this.execute = execute;
            this.executeMissed = executeMissed;
            this.timeToFail = timeToFail;
            this.sequenceStartMessage = sequenceStartMessage;
        }

        public static Sequence create(ResourceLocation id, ExecuteSequence execute, ExecuteSequence executeMissed,
                Long timeToFail, Component sequenceStartMessage, Control... controls) {
            return new Builder(id, execute, executeMissed, timeToFail, sequenceStartMessage, controls);
        }

        @Override
        public ResourceLocation id() {
            return this.id;
        }

        @Override
        public List<Control> getControls() {
            return this.controls;
        }

        @Override
        public void execute(Tardis tardis, @Nullable ServerPlayer player) {
            this.execute.run(tardis);
        }

        @Override
        public void executeMissed(Tardis tardis, @Nullable ServerPlayer player) {
            tardis.travel().missEvent();
            this.executeMissed.run(tardis);
        }

        @Override
        public Long timeToFail() {
            return this.timeToFail;
        }

        @Override
        public Component sequenceStartMessage() {
            return this.sequenceStartMessage;
        }
    }
}
