package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;

public class ThrottleControl extends Control {

    public ThrottleControl() {
        super(AITMod.id("throttle"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        if (tardis.isInDanger())
            return Result.FAILURE;

        TravelHandler travel = tardis.travel();
        TravelHandlerBase.State state = travel.getState();

        if (TelepathicControl.isLiquid(player.getMainHandItem())) {
            return TelepathicControl.spillLiquid(tardis, world, console, player);
        }

        if (!leftClick) {
            if (player.isShiftKeyDown()) {
                travel.speed(travel.maxSpeed().get());
            } else {
                if (!tardis.subsystems().stabilisers().isEnabled() && travel.speed() >= 3) {
                    player.displayClientMessage(Component.translatable("ait.tardis.control.throttle.stabilisers_disabled"), true);
                } else {
                    travel.increaseSpeed();
                }
            }
        } else {
            if (player.isShiftKeyDown()) {
                travel.speed(0);
            } else {
                travel.decreaseSpeed();
            }
        }

        if (travel.getState() == TravelHandler.State.DEMAT)
            tardis.sequence().setActivePlayer(player);


        return player.isShiftKeyDown() ? Result.SUCCESS_ALT : Result.SUCCESS;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.THROTTLE_PULL;
    }

    @Override
    public boolean requiresPower() {
        return false;
    }
}
