package dev.amble.ait.mixin.server;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.amble.ait.core.events.WorldSaveEvent;
import dev.amble.ait.core.item.SiegeTardisItem;
import dev.amble.ait.core.tardis.Tardis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

@Mixin(ServerLevel.class)
public class ServerWorldMixin {

    @Inject(method = "saveLevelData", at = @At("HEAD"))
    private void saveLevel(CallbackInfo ci) {
        WorldSaveEvent.EVENT.invoker().onWorldSave((ServerLevel) (Object) this);
    }

    @Inject(method = "addFreshEntity", at = @At("RETURN"))
    public void spawnEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) return;
        if (!(entity instanceof ItemEntity itemEntity)) return;

        ItemStack stack = itemEntity.getItem();

        if (stack.getItem() instanceof SiegeTardisItem item) {
            Tardis found = item.getTardis(entity.level(), stack);

            if (found == null)
                return;

            // kill ourselves and place down the exterior
            SiegeTardisItem.placeTardis(found, SiegeTardisItem.fromEntity(entity));
            entity.kill();
        }
    }
}
