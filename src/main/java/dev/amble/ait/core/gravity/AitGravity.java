package dev.amble.ait.core.gravity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.entities.ConsoleControlEntity;
import dev.amble.ait.core.net.AitNetworking;

public final class AitGravity {

    public static final ResourceLocation SYNC = AITMod.id("sync_entity_gravity");
    private static final int RANGE = 128;

    private AitGravity() {}

    public interface Holder {
        Direction ait$gravity();

        void ait$setGravity(Direction direction);
    }

    public static Direction get(Entity entity) {
        return ((Holder) entity).ait$gravity();
    }

    public static void set(Entity entity, Direction direction) {
        if (entity instanceof ConsoleControlEntity)
            return;

        if (get(entity) == direction)
            return;

        ((Holder) entity).ait$setGravity(direction);

        if (entity.level() instanceof ServerLevel world)
            sync(world, entity, direction);
    }

    private static void sync(ServerLevel world, Entity entity, Direction direction) {
        for (ServerPlayer player : world.players()) {
            if (player.distanceToSqr(entity) > RANGE * RANGE)
                continue;

            RegistryFriendlyByteBuf buf = AitNetworking.buf(player.registryAccess());
            buf.writeVarInt(entity.getId());
            buf.writeEnum(direction);

            AitNetworking.send(player, SYNC, buf);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        AitNetworking.registerClientReceiver(SYNC, (client, h, buf, rs) -> {
            int id = buf.readVarInt();
            Direction direction = buf.readEnum(Direction.class);

            client.execute(() -> {
                if (client.level == null)
                    return;

                Entity entity = client.level.getEntity(id);

                if (entity != null)
                    ((Holder) entity).ait$setGravity(direction);
            });
        });
    }
}
