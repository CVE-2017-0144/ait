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
import dev.amble.lib.data.DirectedBlockPos;
import java.util.UUID;

public final class BOTIChunkRequestC2SPacket {

    public static final ResourceLocation ID = AITMod.id("request_chunk_data");

    private BOTIChunkRequestC2SPacket() {}

    public static void init() {
        AitNetworking.registerServerReceiver(ID,
                ServerTardisManager.receiveTardis((tardis, server, player, handler, buf, responseSender) -> {
                    BlockPos exteriorPos = buf.readBlockPos();

                    server.execute(() -> {
                        if (tardis == null)
                            return;

                        if (!player.level().isLoaded(exteriorPos)
                                || player.distanceToSqr(exteriorPos.getCenter()) > 128 * 128)
                            return;

                        DirectedBlockPos door = tardis.getDesktop().getDoorPos();

                        if (door == null)
                            return;

                        ServerLevel interior = tardis.world();

                        if (interior == null)
                            return;

                        BOTIDataS2CPacket.send(player, exteriorPos, door,
                                BOTISnapshot.capture(interior, door.getPos()));
                    });
                }));

        TardisEvents.DOOR_MOVE.register((tardis, newPos, oldPos) -> BOTISyncS2CPacket.invalidate(tardis));
    }

    @OnlyIn(Dist.CLIENT)
    public static void send(UUID tardis, BlockPos exteriorPos) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeUUID(tardis);
        buf.writeBlockPos(exteriorPos);

        AitNetworking.send(ID, buf);
    }
}
