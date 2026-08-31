package dev.amble.ait.core.tardis.control.impl;

import static dev.amble.ait.core.engine.SubSystem.Id.GRAVITATIONAL;

import dev.amble.ait.AITMod;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class VisualiserControl extends Control {

    public VisualiserControl() {
        super(AITMod.id("visualiser"));
    }

    @Override
    public Component getName(Tardis tardis) {
        String type = "none";
        if (AITMod.CONFIG.rwfEnabled) type = "rwf";
        else if (DependencyChecker.hasPortals()) type = "normal";

        return Component.translatable("control.ait.visualiser." + type);
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean rightClick) {
        super.runServer(tardis, player, world, console, rightClick);

        if (!AITMod.CONFIG.rwfEnabled) {
            if (!tardis.travel().isLanded()) return Result.FAILURE;

            return Result.FAILURE;
        }

        if (!player.isCreative()) {
            player.displayClientMessage(Component.translatable("tardis.message.control.rwf_creative_only"), true);
            return Result.FAILURE;
        }

        if (!player.isShiftKeyDown() && tardis.travel().getState() == TravelHandlerBase.State.LANDED && tardis.subsystems().get(GRAVITATIONAL).isEnabled()) {
            if (tardis.door().isOpen()) {
                world.playSound(null, player.blockPosition(), SoundEvents.CHAIN_FALL, SoundSource.BLOCKS, 1.0F, 1.0F);
                return Result.SUCCESS;
            } else {
                world.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            tardis.flight().enterFlight(player);
            return Result.SUCCESS;
        }

        return Result.FAILURE;
    }

    @Override
    protected SubSystem.IdLike requiredSubSystem() {
        return GRAVITATIONAL;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.RENAISSANCE_ANTI_GRAV;
    }
}
