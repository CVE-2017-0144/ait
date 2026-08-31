package dev.amble.ait.core.tardis.control.impl.pos;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;

public class IncrementControl extends Control {

    public IncrementControl() {
        super(AITMod.id("increment"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        if (!leftClick) {
            IncrementManager.nextIncrement(tardis);
        } else {
            IncrementManager.prevIncrement(tardis);
        }

        messagePlayerIncrement(player, tardis);

        return leftClick ? Result.SUCCESS_ALT : Result.SUCCESS;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.CRANK;
    }

    private void messagePlayerIncrement(ServerPlayer player, Tardis tardis) {
        Component text = Component.translatable("tardis.message.control.increment.info")
                .append(Component.literal("" + IncrementManager.increment(tardis)));
        player.displayClientMessage(text, true);
    }

    @Override
    public boolean shouldHaveDelay() {
        return false;
    }
}
