package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class PowerControl extends Control {

    public PowerControl() {
        super(AITMod.id("power"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);
        tardis.fuel().togglePower();

        if (tardis.fuel().hasPower()) {
            int power = (int) tardis.fuel().getCurrentFuel();
            boolean inRange = power >= 1500 && power <= 2017;
            boolean doorLocked = tardis.door().locked();
            boolean refueling = !tardis.isRefueling();

            if (inRange && doorLocked && refueling) {
                TardisCriterions.ATTACK_EYEBROWS.trigger((ServerPlayer) player);
                world.playSound(null, console, AITSounds.MAD_MAN_MUSIC, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }

        return tardis.fuel().hasPower() ? Result.SUCCESS : Result.SUCCESS_ALT;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.POWER_FLICK;
    }

    @Override
    public boolean requiresPower() {
        return false;
    }

    @Override
    public long getDelayLength(Tardis tardis) {
        return 200;
    }

    @Override
    public boolean shouldHaveDelay(Tardis tardis) {
        return !tardis.fuel().hasPower() && super.shouldHaveDelay();
    }
}
