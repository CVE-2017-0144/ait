package dev.amble.lib.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.amble.lib.api.ICantBreak;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

@Mixin(MultiPlayerGameMode.class)
public class ClientPlayerInteractionManagerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "destroyBlock", at = @At(value = "HEAD"), cancellable = true)
    public void ait$breakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Level world = this.minecraft.level;
        if (world == null)
            return;

        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof ICantBreak cantBreak) {
            cantBreak.onTryBreak(world, pos, world.getBlockState(pos), this.minecraft.player);
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
