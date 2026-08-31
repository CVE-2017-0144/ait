package dev.amble.ait.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;

import dev.amble.ait.core.gravity.AitGravity;

@Mixin(Entity.class)
public abstract class EntityGravityMixin implements AitGravity.Holder {

    @Unique private Direction ait$gravity = Direction.DOWN;

    @Override
    public Direction ait$gravity() {
        return this.ait$gravity;
    }

    @Override
    public void ait$setGravity(Direction direction) {
        this.ait$gravity = direction == null ? Direction.DOWN : direction;
    }

    @Inject(method = "applyGravity", at = @At("HEAD"), cancellable = true)
    private void ait$applyDirectedGravity(CallbackInfo ci) {
        if (this.ait$gravity == Direction.DOWN)
            return;

        Entity self = (Entity) (Object) this;
        double gravity = self.getGravity();

        ci.cancel();

        if (gravity == 0)
            return;

        Vec3i normal = this.ait$gravity.getNormal();
        self.setDeltaMovement(self.getDeltaMovement().add(normal.getX() * gravity, normal.getY() * gravity,
                normal.getZ() * gravity));
    }
}
