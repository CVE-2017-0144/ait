package dev.amble.ait.core.tardis.util.network.s2c;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.boti.BOTICache;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.util.network.BOTISnapshot;
import dev.amble.lib.data.DirectedBlockPos;

public final class BOTIDataS2CPacket {

    public static final ResourceLocation ID = AITMod.id("send_boti_data");

    private BOTIDataS2CPacket() {}

    public static void send(ServerPlayer player, BlockPos exteriorPos, DirectedBlockPos door,
            BOTISnapshot snapshot) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf(player.registryAccess());
        buf.writeBlockPos(exteriorPos);
        buf.writeByte(door.getRotation());
        snapshot.write(buf);

        AitNetworking.send(player, ID, buf);
    }

    @Environment(EnvType.CLIENT)
    public static void init() {
        AitNetworking.registerClientReceiver(ID, (client, handler, buf, responseSender) -> {
            BlockPos exteriorPos = buf.readBlockPos();
            byte rotation = buf.readByte();
            BOTISnapshot snapshot = BOTISnapshot.read(buf);

            client.execute(() -> BOTICache.accept(exteriorPos, rotation, snapshot));
        });
    }
}
