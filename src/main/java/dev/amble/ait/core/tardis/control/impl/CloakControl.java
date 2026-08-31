package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.handler.CloakHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class CloakControl extends Control {
    public static final ResourceLocation ID = AITMod.id("protocol_3");

    public CloakControl() {
        // ⬚ ?
        super(ID);
    }

    @Override
    public Component getName(Tardis tardis) {
        return Component.translatable(tardis.cloak().silent().get()
                ? "control.ait.protocol_3_silent_active"
                : "control.ait.protocol_3_silent_inactive");
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        CloakHandler cloak = tardis.handler(TardisComponent.Id.CLOAK);
        boolean wasCloaked = cloak.cloaked().get();

        if (leftClick && wasCloaked) {
            boolean wasSilent = cloak.silent().get();
            cloak.silent().set(!wasSilent);

            player.displayClientMessage(Component.translatable("control.ait.protocol_3_silent_" + (wasSilent ? "deactivated" : "activated")), true);
        } else {
            cloak.cloaked().set(!wasCloaked);

            cloak.silent().set(false);
        }

        return cloak.cloaked().get() ? Result.SUCCESS : Result.SUCCESS_ALT;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    protected SubSystem.IdLike requiredSubSystem() {
        return SubSystem.Id.CHAMELEON;
    }
}
