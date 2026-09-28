package dev.amble.ait.core.tardis.util.network.c2s;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.core.tardis.util.network.BOTISnapshot;
import dev.amble.ait.core.tardis.util.network.s2c.BOTIDataS2CPacket;
import dev.amble.ait.core.tardis.util.network.s2c.BOTISyncS2CPacket;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.data.DirectedBlockPos;
import java.util.UUID;

public final class BOTIChunkRequestC2SPacket {

    public static final ResourceLocation ID = AITMod.id("request_chunk_data");

    private BOTIChunkRequestC2SPacket() {}

    public static void init() {
        AitNetworking.registerServerReceiver(ID,
                ServerTardisManager.receiveTardis((tardis, server, player, handler, buf, responseSender) -> {
                    BlockPos anchor = buf.readBlockPos();
                    boolean fromInside = buf.readBoolean();

                    server.execute(() -> {
                        if (tardis == null)
                            return;

                        if (!player.level().isLoaded(anchor)
                                || player.distanceToSqr(anchor.getCenter()) > 128 * 128)
                            return;

                        ServerLevel source;
                        BlockPos origin;
                        byte rotation;

                        if (fromInside) {
                            CachedDirectedGlobalPos exterior = tardis.travel().position();

                            if (exterior == null)
                                return;

                            source = exterior.getWorld();
                            origin = exterior.getPos();
                            rotation = exterior.getRotation();
                        } else {
                            DirectedBlockPos door = tardis.getDesktop().getDoorPos();

                            if (door == null)
                                return;

                            source = tardis.world();
                            origin = door.getPos();
                            rotation = door.getRotation();
                        }

                        if (source == null)
                            return;

                        BOTISnapshot snapshot = BOTISnapshot.capture(source, origin);

                        int[] d = snapshot.describe(rotation);
                        AITMod.LOGGER.info(
                                "[boti] answering {} from {} rot {} in {}: {} solid; x[{}..{}] y[{}..{}] z[{}..{}];"
                                        + " infront={} level={} behind={}",
                                anchor, origin, rotation, source.dimension().location(), snapshot.solidCount(),
                                d[0], d[1], d[2], d[3], d[4], d[5], d[6], d[7], d[8]);

                        BOTIDataS2CPacket.send(player, anchor, rotation,
                                source.getBiome(origin).value().getSkyColor(), snapshot);
                    });
                }));

        TardisEvents.DOOR_MOVE.register((tardis, newPos, oldPos) -> BOTISyncS2CPacket.invalidate(tardis));
    }

    @OnlyIn(Dist.CLIENT)
    public static void send(UUID tardis, BlockPos anchor, boolean fromInside) {
        AITMod.LOGGER.debug("[boti] requesting {} side for {}", fromInside ? "outer" : "inner", anchor);

        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeUUID(tardis);
        buf.writeBlockPos(anchor);
        buf.writeBoolean(fromInside);

        AitNetworking.send(ID, buf);
    }
}
