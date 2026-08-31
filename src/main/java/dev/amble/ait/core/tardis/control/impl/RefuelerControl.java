package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;

public class RefuelerControl extends Control {
    public static final ResourceLocation ID = AITMod.id("refueler");

    public RefuelerControl() {
        super(ID);
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        if (tardis.isGrowth())
            return Result.FAILURE;

        if (tardis.travel().getState() == TravelHandlerBase.State.LANDED && tardis.travel().handbrake()) {
            tardis.setRefueling(!tardis.isRefueling());

            return tardis.isRefueling() ? Result.SUCCESS_ALT : Result.SUCCESS;
        }

        return Result.SUCCESS;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.ENGINE_REFUEL_CRANK;
    }

    @Override
    public boolean requiresPower() {
        return false;
    }

    @Override
    protected SubSystem.IdLike requiredSubSystem() {
        return null;
    }
}
