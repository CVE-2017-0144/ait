package dev.amble.ait.core.tardis.util.network.s2c;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.boti.BOTICache;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.ServerTardis;

public final class BOTISyncS2CPacket {

    public static final ResourceLocation ID = AITMod.id("boti_sync");

    private BOTISyncS2CPacket() {}

    public static void invalidate(ServerTardis tardis) {
        BlockPos exteriorPos = tardis.travel().position().getPos();
        ServerLevel world = tardis.travel().position().getWorld();

        if (world == null)
            return;

        for (ServerPlayer player : world.players()) {
            if (player.distanceToSqr(exteriorPos.getCenter()) > 128 * 128)
                continue;

            RegistryFriendlyByteBuf buf = AitNetworking.buf(player.registryAccess());
            buf.writeBlockPos(exteriorPos);

            AitNetworking.send(player, ID, buf);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void init() {
        AitNetworking.registerClientReceiver(ID, (client, handler, buf, responseSender) -> {
            BlockPos exteriorPos = buf.readBlockPos();
            client.execute(() -> BOTICache.invalidate(exteriorPos));
        });
    }
}
