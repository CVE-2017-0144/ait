package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.control.impl.pos.IncrementManager;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelUtil;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;

public class RandomiserControl extends Control {

    public RandomiserControl() {
        super(AITMod.id("randomiser"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        TravelHandler travel = tardis.travel();

        TravelUtil.randomPos(tardis, 10, IncrementManager.increment(tardis), cached -> {
            tardis.travel().destination(cached);
            tardis.removeFuel(0.1d * IncrementManager.increment(tardis) * tardis.travel().instability());

            messagePlayer(player, travel);
        });

        return Result.SUCCESS;
    }

    @Override
    public long getDelayLength(Tardis tardis) {
        return 40;
    }

    private void messagePlayer(ServerPlayer player, TravelHandler travel) {
        CachedDirectedGlobalPos dest = travel.destination();
        BlockPos pos = dest.getPos();

        Component text = Component.translatable("tardis.message.control.randomiser.destination")
                .append(Component.literal(pos.getX() + " | " + pos.getY() + " | " + pos.getZ()));

        player.displayClientMessage(text, true);
    }
    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.RANDOMIZE;
    }
}
