package dev.amble.ait.mixin.server;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.item.sonic.SonicMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BellBlock;

@Mixin(value = BellBlock.class)
public class BellBlockMixin {

    @Inject(method = "attemptToRing(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"), cancellable = true)
    public void ait$playSound(Entity entity, Level world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {

        // sonic summon used on bell (locator feature) -> play cloister sound
        if (!world.isClientSide() && entity instanceof Player player) {
            ItemStack itemInHand = player.getMainHandItem();

            if (itemInHand.getItem() instanceof SonicItem && SonicItem.mode(itemInHand) == SonicMode.Modes.SCANNING) {
                world.playSound(null, pos, AITSounds.CLOISTER, SoundSource.BLOCKS, 2.0F, 1.0F);
                itemInHand.use(world, player, player.getUsedItemHand());
                cir.cancel();
            }
        }
    }
}
