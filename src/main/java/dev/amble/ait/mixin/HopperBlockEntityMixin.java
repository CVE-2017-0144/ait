package dev.amble.ait.mixin;

import dev.amble.ait.api.ConsumableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {

    @Inject(method = "ejectItems(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/HopperBlockEntity;)Z",
            at = @At("HEAD"), cancellable = true)
    private static void ait$insertIntoConsumableBlock(
            Level world, BlockPos pos, HopperBlockEntity hopper, CallbackInfoReturnable<Boolean> cir
    ) {
        BlockState state = world.getBlockState(pos);

        if (!(state.getBlock() instanceof HopperBlock))
            return;

        Container inventory = hopper;
        Direction direction = state.getValue(HopperBlock.FACING);
        BlockPos targetPos = pos.relative(direction);
        BlockState targetState = world.getBlockState(targetPos);

        if (!(targetState.getBlock() instanceof ConsumableBlock block)) return;

        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (stack.isEmpty()) continue;

            if (!block.canAcceptItem(world, targetPos, stack, direction.getOpposite())) continue;

            ItemStack attempt = stack.copyWithCount(1);
            ItemStack leftover = block.insertItem(world, targetPos, attempt, direction.getOpposite(), false);

            if (leftover.isEmpty()) {
                stack.shrink(1);
                cir.setReturnValue(true);
                return;
            }
        }
    }

}
