package dev.amble.ait.core.tardis.control.impl;

import java.text.DecimalFormat;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class MonitorControl extends Control {
    public MonitorControl() {
        super(AITMod.id("monitor"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        CachedDirectedGlobalPos abpd = tardis.travel().destination();
        BlockPos abpdPos = abpd.getPos();

        if (!player.isShiftKeyDown()) {
            player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1.0F, 1.0F);

            AITMod.openScreen(player, 0, tardis.getUuid(), console);
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            String formattedNumber = df.format(tardis.getFuel());
            player.displayClientMessage(Component.translatable("message.ait.control.monitor.status", abpdPos.getX(),
                    abpdPos.getY(), abpdPos.getZ(), WorldUtil.worldText(abpd.getDimension()), formattedNumber), true);
        }

        return Result.SUCCESS;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.MONITOR;
    }
}
