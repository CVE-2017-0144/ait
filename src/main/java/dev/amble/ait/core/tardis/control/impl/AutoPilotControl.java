package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;

public class AutoPilotControl extends Control {
    public static final ResourceLocation ID = AITMod.id("protocol_116");

    public AutoPilotControl() {
        super(ID);
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        boolean autopilot = tardis.travel().autopilot();
        tardis.travel().autopilot(!autopilot);

        return autopilot ? Result.SUCCESS_ALT : Result.SUCCESS;
    }

    @Override
    protected SubSystem.IdLike requiredSubSystem() {
        return SubSystem.Id.STABILISERS;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.PROTOCOL_116_ON;
    }
}
