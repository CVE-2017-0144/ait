package dev.amble.lib.util;

import java.util.Set;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import dev.amble.lib.data.DirectedGlobalPos;

public class TeleportUtil {
    public static void teleport(LivingEntity entity, DirectedGlobalPos pos) {
        teleport(entity, ServerLifecycleHooks.get().getLevel(pos.getDimension()), pos.getPos().getCenter(), pos.getRotationDegrees());
    }
    public static void teleport(LivingEntity entity, ServerLevel world, Vec3 pos, float yaw) {
        world.getServer().execute(() -> {
            if (entity instanceof ServerPlayer player) {
                teleportPlayer(player, world, pos, yaw, player.getXRot());
                return;
            }

            teleportNonPlayer(entity, world, pos, yaw, entity.getXRot());
        });
    }
    private static void teleportPlayer(ServerPlayer player, ServerLevel world, Vec3 pos, float yaw, float pitch) {
        player.teleportTo(world, pos.x, pos.y, pos.z, yaw, pitch);
        player.giveExperiencePoints(0);
        player.getActiveEffects().forEach(effect -> player.connection.send(new ClientboundUpdateMobEffectPacket(player.getId(), effect, false)));
        player.connection.send(new ClientboundSetEntityMotionPacket(player));
    }
    private static void teleportNonPlayer(LivingEntity entity, ServerLevel world, Vec3 pos, float yaw, float pitch) {
        if (entity.level().dimension() == world.dimension()) {
            entity.moveTo(pos.x, pos.y, pos.z, yaw, pitch);
            return;
        }

        entity.teleportTo(world, pos.x, pos.y, pos.z, Set.of(), yaw, pitch);
    }
}
