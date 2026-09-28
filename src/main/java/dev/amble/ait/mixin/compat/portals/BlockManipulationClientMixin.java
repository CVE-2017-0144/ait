package dev.amble.ait.mixin.compat.portals;

import dev.amble.ait.core.blocks.DoorBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qouteall.imm_ptl.core.block_manipulation.BlockManipulationClient;

@Mixin(value = BlockManipulationClient.class, remap = false)
public class BlockManipulationClientMixin {

    // door sits in the portal plane so vanilla's hit always wins, push it out of range and let ip pick through
    // ray's still capped at reach, server rechecks
    @Inject(method = "getCurrentTargetDistance", at = @At("HEAD"), cancellable = true)
    private static void ignoreDoor(CallbackInfoReturnable<Double> cir) {
        Minecraft client = Minecraft.getInstance();

        if (client.hitResult instanceof BlockHitResult hit
                && client.level.getBlockState(hit.getBlockPos()).getBlock() instanceof DoorBlock)
            cir.setReturnValue(Double.MAX_VALUE);
    }
}
