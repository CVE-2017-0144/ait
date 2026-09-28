package dev.amble.ait.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import dev.amble.ait.core.AITDataComponents;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    // the codecs build stacks through this one, copy() doesn't
    @Inject(method = "<init>(Lnet/minecraft/core/Holder;ILnet/minecraft/core/component/DataComponentPatch;)V", at = @At("TAIL"))
    private void ait$migrate(Holder<Item> item, int count, DataComponentPatch patch, CallbackInfo ci) {
        if (patch.get(DataComponents.CUSTOM_DATA) != null)
            AITDataComponents.migrate((ItemStack) (Object) this);
    }
}
