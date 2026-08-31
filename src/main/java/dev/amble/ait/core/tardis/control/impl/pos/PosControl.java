package dev.amble.ait.core.tardis.control.impl.pos;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;

public abstract class PosControl extends Control {

    private final PosType type;

    public PosControl(PosType type) {
        super(AITMod.id(type.getSerializedName()));
        this.type = type;
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console,
            boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        TravelHandler travel = tardis.travel();
        CachedDirectedGlobalPos destination = travel.destination();

        BlockPos pos = this.type.add(destination.getPos(),
                (leftClick) ? -IncrementManager.increment(tardis) : IncrementManager.increment(tardis),
                destination.getWorld());

        travel.destination(destination.pos(pos));
        messagePlayerDestination(player, travel);
        return Result.SUCCESS;
    }

    private void messagePlayerDestination(ServerPlayer player, TravelHandler travel) {
        CachedDirectedGlobalPos globalPos = travel.destination();
        BlockPos pos = globalPos.getPos();

        Component text = Component.translatable("tardis.message.control.randomiser.poscontrol")
                .append(Component.literal(" " + pos.getX() + " | " + pos.getY() + " | " + pos.getZ()));
        player.displayClientMessage(text, true);
    }

    @Override
    public boolean shouldHaveDelay() {
        return false;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.XYZ;
    }
}
