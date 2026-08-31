package dev.amble.ait.core.tardis.control.impl.waypoint;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.TardisDesktop;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.Waypoint;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class SaveWaypointControl extends Control {

    public SaveWaypointControl() {
        super(AITMod.id("save_waypoint"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        if (!tardis.waypoint().hasCartridge()) {
            player.displayClientMessage(Component.translatable("control.ait.load_waypoint.no_cartridge"), true);
            TardisDesktop.playSoundAtConsole(world, console, SoundEvents.NOTE_BLOCK_BIT.value(), SoundSource.PLAYERS, 6f, 0.1f);
            return Result.SUCCESS;
        }

        CachedDirectedGlobalPos cached = tardis.travel().position();
        if (cached.getWorld() instanceof TardisServerWorld) {
            cached = CachedDirectedGlobalPos.create(TardisServerWorld.OVERWORLD, cached.getPos(), cached.getRotation());
        }
        tardis.waypoint().set(Waypoint.fromPos(cached), console, false);
        TardisDesktop.playSoundAtConsole(world, console, AITSounds.TARDIS_BLING, SoundSource.PLAYERS, 6f, 1);
        return Result.SUCCESS;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.SAVE_WAYPOINT;
    }
}
