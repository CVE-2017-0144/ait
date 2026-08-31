package dev.amble.ait.mixin.server;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.data.Loyalty;

@Mixin(BedBlock.class)
public class BedInTardisMixin {
    @Inject(at = @At("HEAD"), method = "useWithoutItem")
    private void ait$useOn(BlockState state, Level world, BlockPos pos, Player player,
                           BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if (world.isClientSide()) { this.onClientSleep(player); }
    }

    @Unique @Environment(EnvType.CLIENT)
    private void onClientSleep(Player player) {
        Tardis tardis = ClientTardisUtil.getCurrentTardis();
        if (tardis == null) return;

        Loyalty loyalty = tardis.loyalty().get(player);

        Component message = switch (loyalty.type()) {
            case REJECT -> Component.translatable("tardis.loyalty.message.reject");
            case NEUTRAL -> Component.translatable("tardis.loyalty.message.neutral");
            case COMPANION -> Component.translatable("tardis.loyalty.message.companion");
            case PILOT -> Component.translatable("tardis.loyalty.message.pilot");
            case OWNER -> Component.translatable("tardis.loyalty.message.owner");
        };
        player.displayClientMessage(message, false);

        SoundEvent sound = switch(loyalty.type()) {
            case OWNER -> AITSounds.OWNER_BED;
            case PILOT -> AITSounds.PILOT_BED;
            case COMPANION -> AITSounds.COMPANION_BED;
            case NEUTRAL -> AITSounds.NEUTRAL_BED;
            case REJECT -> AITSounds.GROAN;
        };

        // todo ClientScheduler with delay of 1sec was here, but was removed for addon compat
        // Mixin transformation of net.minecraft.block.BedBlock failed
        // Cannot load ClientScheduler
        player.playSound(sound, 1f, 1f);
    }
}