package dev.amble.ait.module.planet.core.space.planet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.amble.ait.core.AITDimensions;
import dev.amble.lib.util.ServerLifecycleHooks;
import dev.amble.lib.util.TeleportUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Represents a transition between two planets.
 * @param target the planet to be teleported to
 * @param height the height at which the player should be teleported
 */
public record PlanetTransition(ResourceLocation target, int height) {
    public static final Codec<PlanetTransition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("target").forGetter(PlanetTransition::target),
            Codec.INT.fieldOf("height").forGetter(PlanetTransition::height)
    ).apply(instance, PlanetTransition::new));

    public ServerLevel getTargetWorld() {
        return ServerLifecycleHooks.get().getLevel(ResourceKey.create(Registries.DIMENSION, target));
    }

    /**
     * Teleports the entity to the target planet.
     * @param entity the entity to be teleported
     * @return true if the entity was teleported, false otherwise
     */
    public boolean run(LivingEntity entity) {
        if (this.isEmpty()) return false;

        // check if is to space
        if (target.equals(AITDimensions.SPACE.location())) {
            // teleport to that planets position in space
            Planet planet = PlanetRegistry.getInstance().get(entity.level());
            if (planet == null || planet.render().isEmpty()) {
                return false;
            }

            //if (entity.hasVehicle())
            //    if (entity.getVehicle() instanceof FlightTardisEntity)
            //        FabricDimensions.teleport(entity.getVehicle(), getTargetWorld(),
            //                new TeleportTarget(planet.render().position().add(0, height, 0),
            //                        entity.getVelocity(), entity.getBodyYaw(), entity.getPitch()));
            TeleportUtil.teleport(entity, getTargetWorld(), planet.render().position().add(0, height, 0), entity.getVisualRotationYInDegrees());
            return true;
        }

        //if (entity.hasVehicle())
        //    if (entity.getVehicle() instanceof FlightTardisEntity)
        //        FabricDimensions.teleport(entity.getVehicle(), getTargetWorld(),
        //                new TeleportTarget(new Vec3d(entity.getX(), height, entity.getZ()),
        //                        entity.getVelocity(), entity.getBodyYaw(), entity.getPitch()));
        TeleportUtil.teleport(entity, getTargetWorld(), new Vec3(entity.getX(), height, entity.getZ()), entity.getVisualRotationYInDegrees()); // height might not be the right place to teleport here, im not sure.
        return true;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }
    public static final PlanetTransition EMPTY = new PlanetTransition(new ResourceLocation("empty"), 0);
    public static PlanetTransition toSpace(int height) {
        return new PlanetTransition(AITDimensions.SPACE.location(), height);
    }
}
