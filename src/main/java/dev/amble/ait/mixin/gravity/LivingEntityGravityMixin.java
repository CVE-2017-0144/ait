package dev.amble.ait.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import dev.amble.ait.core.gravity.AitGravity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityGravityMixin {

    @Redirect(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getGravity()D"), require = 1)
    private double ait$dropVanillaGravity(LivingEntity self) {
        return ((AitGravity.Holder) self).ait$gravity() == Direction.DOWN ? self.getGravity() : 0;
    }

    @Inject(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At("TAIL"), require = 1)
    private void ait$applyDirectedGravity(Vec3 movement, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        Direction direction = ((AitGravity.Holder) self).ait$gravity();

        // tail is outside vanilla's isControlledByLocalInstance gate
        if (direction == Direction.DOWN || !self.isControlledByLocalInstance())
            return;

        double gravity = self.getGravity();

        if (gravity == 0)
            return;

        Vec3i normal = direction.getNormal();
        self.setDeltaMovement(self.getDeltaMovement().add(normal.getX() * gravity, normal.getY() * gravity,
                normal.getZ() * gravity));
    }
}
