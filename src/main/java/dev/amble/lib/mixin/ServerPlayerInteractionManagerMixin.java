package dev.amble.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.amble.lib.api.ICantBreak;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.block.Block;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerInteractionManagerMixin {

    @Shadow
    protected ServerLevel level;

    @Shadow
    protected ServerPlayer player;

    @Inject(method = "destroyBlock", at = @At(value = "HEAD"), cancellable = true)
    public void ait$tryBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Block block = this.level.getBlockState(pos).getBlock();
        if (block instanceof ICantBreak cantBreak) {
            cantBreak.onTryBreak(this.level, pos, this.level.getBlockState(pos), this.player);
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
