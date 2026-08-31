package dev.amble.ait.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.entities.FlightTardisEntity;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRenderInfo;
import dev.amble.ait.module.planet.core.space.system.Space;
import dev.amble.lib.platform.lifecycle.ServerTickEvents;

// todo - all this code is very sucky
public class SpaceUtils {
    static {
        ServerTickEvents.END_WORLD_TICK.register(SpaceUtils::onWorldTick);
    }

    public static void init() {

    }

    private static void onWorldTick(ServerLevel world) {
        checkPlayerTeleportation(world);
    }

    public static void checkPlayerTeleportation(ServerLevel world) {
        List<ServerPlayer> playersToTeleport = new ArrayList<>();
        List<ServerPlayer> playersToSuck = new ArrayList<>();

        for (ServerPlayer player : world.players()) {
            if (!(player.level().dimension() == AITDimensions.SPACE)) {
                continue;
            }

            Vec3 playerPos = player.position();

            for (Planet planet : Space.getInstance().getPlanets()) {
                PlanetRenderInfo planetInfo = planet.render();
                Vec3 planetPos = planetInfo.position();
                double planetRadius = planetInfo.radius();
                double suctionRadius = planetInfo.suctionRadius();

                double distance = planetPos.distanceTo(playerPos);

                if (distance < suctionRadius) {
                    playersToSuck.add(player);
                    applySuction(playersToSuck, planetPos);
                }
                if (distance < planetRadius) {
                    playersToSuck.remove(player);
                    playersToTeleport.add(player);
                    break;
                }
            }
        }

        for (ServerPlayer player : playersToTeleport) {
            teleportPlayerToTouchingPlanet(player);
        }

        /*for (Entity entity : world.iterateEntities()) {
            if (!(entity instanceof LivingEntity)) continue;

            // get planet
            Planet planet = PlanetRegistry.getInstance().get(entity.getWorld());
            if (planet == null) continue;

            // get transitions
            PlanetTransition transition = planet.transition();

            if (entity.getBlockPos().getY() < transition.height()) continue;

            transition.run((LivingEntity) entity);
        }*/
    }

    private static void applySuction(List<ServerPlayer> player, Vec3 planetPos) {
        player.forEach(entity -> {
            if (entity.isSpectator())
                return;
            if (entity.getVehicle() instanceof FlightTardisEntity tardis) {
                if (tardis.tardis() != null && tardis.tardis().get().travel().antigravs().get()) return;
                Vec3 motion = planetPos.subtract(tardis.position()).normalize().scale(0.1f);
                tardis.setDeltaMovement(tardis.getDeltaMovement().add(motion));
                tardis.hasImpulse = true;
                tardis.hurtMarked = true;
            } else {
                Vec3 motion = planetPos.subtract(entity.position()).normalize().scale(0.1f);
                entity.setDeltaMovement(entity.getDeltaMovement().add(motion));
                entity.hasImpulse = true;
                entity.hurtMarked = true;
            }
        });
    }

    private static void teleportPlayerToTouchingPlanet(ServerPlayer player) {
        MinecraftServer server = player.getServer();
        if (server == null) return;

        ResourceLocation target = getTouchingPlanet(player).orElse(null);
        if (target == null) return;

        ResourceKey<Level> targetWorldKey = ResourceKey.create(Registries.DIMENSION, target);
        ServerLevel targetWorld = server.getLevel(targetWorldKey);

        if (targetWorld != null) {
            player.teleportTo(targetWorld, player.getX(), targetWorld.getMaxBuildHeight(), player.getZ(), Set.of(), player.getYRot(), player.getXRot());
        } else {
            AITMod.LOGGER.error("Teleporting to planets -> Dimension {} not found!", target);
        }
    }


    /**
     * @param entity The entity to check
     * @return The dimension of the planet the entity is touching
     */
    private static Optional<ResourceLocation> getTouchingPlanet(Entity entity) {
        for (Planet planet : Space.getInstance().getPlanets()) {
            PlanetRenderInfo planetInfo = planet.render();
            Vec3 planetPos = planetInfo.position();
            double planetRadius = planetInfo.radius();

            double distance = planetPos.distanceTo(entity.position());

            if (distance < planetRadius && planet.hasLandableSurface()) {
                return Optional.of(planet.dimension());
            }
        }

        return Optional.empty();
    }
}
