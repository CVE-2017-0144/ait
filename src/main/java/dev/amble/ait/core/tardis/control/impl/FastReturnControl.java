package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;

public class FastReturnControl extends Control {

    public FastReturnControl() {
        super(AITMod.id("fast_return"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        TravelHandler travel = tardis.travel();
        boolean same = travel.destination().equals(travel.previousPosition());

        if (travel.previousPosition() != null) {
            travel.forceDestination(same ? travel.position() : travel.previousPosition());

            this.messagePlayer(player, same);
            return Result.SUCCESS;
        }

        Component text = Component.translatable("tardis.message.control.fast_return.destination_nonexistent");
        player.displayClientMessage(text, true);
        return Result.FAILURE;
    }

    public void messagePlayer(ServerPlayer player, boolean isLastPosition) {
        Component previousPosition = Component.translatable("tardis.message.control.fast_return.last_position");
        Component currentPosition = Component.translatable("tardis.message.control.fast_return.current_position");
        player.displayClientMessage((!isLastPosition ? previousPosition : currentPosition), true);
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.FAST_RETURN;
    }
}
