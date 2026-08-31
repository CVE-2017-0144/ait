package dev.amble.ait.module.planet.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


@Mixin({AbstractMinecart.class, ItemEntity.class, PrimedTnt.class})
public abstract class AssortedEntityMixin extends Entity {

    public AssortedEntityMixin(EntityType<?> type, Level world) {
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
        this.setDeltaMovement(movement.x, movement.y + (planet.gravity() == 0.075f || planet.gravity() == 0.8 ? 0.025f : planet.gravity() - 0.02f), movement.z); // todo - gravity broken on this
    }
}
