package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;

public class AntiGravsControl extends Control {
    public static final ResourceLocation ID = AITMod.id("antigravs");

    public AntiGravsControl() {
        super(ID);
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        tardis.travel().antigravs().toggle();

        CachedDirectedGlobalPos globalPos = tardis.travel().position();
        ServerLevel targetWorld = globalPos.getWorld();
        BlockPos pos = globalPos.getPos();

        targetWorld.getChunkSource().blockChanged(pos);
        world.scheduleTick(pos, AITBlocks.EXTERIOR_BLOCK, 2);
        return tardis.travel().antigravs().get() ? Result.SUCCESS : Result.SUCCESS_ALT;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.ANTI_GRAVS;
    }

    @Override
    protected SubSystem.IdLike requiredSubSystem() {
        return SubSystem.Id.GRAVITATIONAL;
    }
}
