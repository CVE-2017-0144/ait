package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.util.SafePosSearch;
import dev.amble.ait.data.schema.console.variant.coral.*;
import dev.amble.ait.data.schema.console.variant.renaissance.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;

public class LandTypeControl extends Control {
    public LandTypeControl() {
        super(AITMod.id("land_type"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        if (leftClick) {
            tardis.travel().horizontalSearch().flatMap(value -> {
                value = !value;
                messageXPlayer(player, value);
                return value;
            });

            return Result.SUCCESS_ALT;
        }

        tardis.travel().verticalSearch().flatMap(value -> {
            value = value.next();
            messageYPlayer(player, value);
            return value;
        });

        return Result.SUCCESS;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.LAND_TYPE;
    }

    public void messageYPlayer(ServerPlayer player, SafePosSearch.Kind value) {
        player.displayClientMessage(Component.translatable("message.ait.control.ylandtype", value.text()), true);
    }

    public void messageXPlayer(ServerPlayer player, boolean var) {
        Component on = Component.translatable("message.ait.control.xlandtype.on");
        Component off = Component.translatable("message.ait.control.xlandtype.off");
        player.displayClientMessage(var ? on : off, true);
    }

    private boolean isRenaissanceVariant(ConsoleBlockEntity consoleBlockEntity) {
        return consoleBlockEntity.getVariant() instanceof RenaissanceTokamakVariant ||
                consoleBlockEntity.getVariant() instanceof RenaissanceVariant ||
                consoleBlockEntity.getVariant() instanceof RenaissanceIdentityVariant ||
                consoleBlockEntity.getVariant() instanceof RenaissanceIndustriousVariant ||
                consoleBlockEntity.getVariant() instanceof RenaissanceFireVariant;
    }

    private boolean isCoralVariant(ConsoleBlockEntity consoleBlockEntity) {
        return consoleBlockEntity.getVariant() instanceof CoralVariant ||
                consoleBlockEntity.getVariant() instanceof WhiteCoralVariant ||
                consoleBlockEntity.getVariant() instanceof CoralSithVariant ||
                consoleBlockEntity.getVariant() instanceof BlueCoralVariant ||
                consoleBlockEntity.getVariant() instanceof CoralDecayedVariant;
    }
}
