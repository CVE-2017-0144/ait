package dev.amble.lib.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.amble.lib.block.ABlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;createBlockStateDefinition(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V"))
    public void init(BlockBehaviour.Properties settings, CallbackInfo ci, @Local StateDefinition.Builder<Block, BlockState> builder) {
        if (settings instanceof ABlockSettings abs && abs.properties() != null) builder.add(abs.properties());
    }
}
