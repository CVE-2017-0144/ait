package dev.amble.ait.module.planet.mixin.gravity;

import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin({Boat.class})
public abstract class BoatEntityMixin extends Entity {

    public BoatEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void ait$tick(CallbackInfo ci) {
        Planet planet = PlanetRegistry.getInstance().get(this.level());

        if (planet == null)
            return;

        if (!planet.hasGravityModifier())
            return;

        Vec3 movement = this.getDeltaMovement();

        // FIXME temp fix because im lazy :))))
        this.setDeltaMovement(movement.x, movement.y < 0 ? movement.y + planet.gravity() : movement.y, movement.z);
    }
}
