package dev.amble.lib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.lib.item.AItem;
import dev.amble.lib.item.AItemSettings;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

@Mixin(Item.class)
public class ItemMixin implements AItem {

    @Unique
    private CreativeModeTab amble$group;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(Item.Properties settings, CallbackInfo ci) {
        if (settings instanceof AItemSettings ais)
            this.amble$group = ais.group();
    }

    @Override
    public CreativeModeTab amble$group() {
        return this.amble$group;
    }
}
