package dev.amble.ait.core.tardis.util.network.s2c;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.boti.BOTICache;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.util.network.BOTISnapshot;

public final class BOTIDataS2CPacket {

    public static final ResourceLocation ID = AITMod.id("send_boti_data");

    private BOTIDataS2CPacket() {}

    public static void send(ServerPlayer player, BlockPos anchor, byte rotation, int skyColor,
            BOTISnapshot snapshot) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf(player.registryAccess());
        buf.writeBlockPos(anchor);
        buf.writeByte(rotation);
        buf.writeInt(skyColor);
        snapshot.write(buf);

        AitNetworking.send(player, ID, buf);
    }

    @OnlyIn(Dist.CLIENT)
    public static void init() {
        AitNetworking.registerClientReceiver(ID, (client, handler, buf, responseSender) -> {
            BlockPos anchor = buf.readBlockPos();
            byte rotation = buf.readByte();
            int skyColor = buf.readInt();
            BOTISnapshot snapshot = BOTISnapshot.read(buf);

            client.execute(() -> BOTICache.accept(anchor, rotation, skyColor, snapshot));
        });
    }
}
