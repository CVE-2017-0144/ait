package dev.amble.ait.core.tardis.control.impl.waypoint;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.TardisDesktop;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.handler.WaypointHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class LoadWaypointControl extends Control {

    public LoadWaypointControl() {
        super(AITMod.id("load_waypoint"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        if (!tardis.waypoint().hasCartridge() && !tardis.waypoint().isDisc()) {
            player.displayClientMessage(Component.translatable("control.ait.load_waypoint.no_cartridge"), true);
            TardisDesktop.playSoundAtConsole(world, console, SoundEvents.NOTE_BLOCK_BIT.value(), SoundSource.PLAYERS, 6f, 0.1f);
            return Result.SUCCESS;
        }

        WaypointHandler waypoints = tardis.waypoint();

        if (waypoints.loadWaypoint()) {
            TardisDesktop.playSoundAtConsole(world, console, AITSounds.NAV_NOTIFICATION, SoundSource.PLAYERS, 6f, 1);
            if (waypoints.isDisc()) {
                player.displayClientMessage(Component.translatable("control.ait.load_control_disc.loaded"), true);
                TravelHandler travel = tardis.travel();
                travel.handbrake(false);
                travel.autopilot(true);
                travel.speed(travel.maxSpeed().get());
                travel.setFlightTicks(travel.getTargetTicks() / 2);
            }
        } else {
            player.displayClientMessage(Component.translatable("control.ait.load_waypoint.error"), true);
            TardisDesktop.playSoundAtConsole(world, console, SoundEvents.NOTE_BLOCK_BIT.value(), SoundSource.PLAYERS, 6f, 0.1f);
        }

        return Result.SUCCESS;
    }
    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.LOAD_WAYPOINT;
    }
}
