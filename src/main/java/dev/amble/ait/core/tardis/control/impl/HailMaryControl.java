package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;

public class HailMaryControl extends Control {
    public static final ResourceLocation ID = AITMod.id("protocol_813");

    public HailMaryControl() {
        // ♡ ?
        super(ID);
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        tardis.stats().hailMary().set(!tardis.stats().hailMary().get());

        player.displayClientMessage(tardis.stats().hailMary().get()
                ? Component.translatable("tardis.message.control.hail_mary.engaged")
                : Component.translatable("tardis.message.control.hail_mary.disengaged"), true);

        return tardis.stats().hailMary().get() ? Result.SUCCESS_ALT : Result.SUCCESS;
    }

    @Override
    protected SubSystem.IdLike requiredSubSystem() {
        return SubSystem.Id.DESPERATION;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.HAIL_MARY;
    }
}
