package dev.drtheo.portal;

import dev.amble.ait.AITMod;
import net.minecraft.network.ProtocolInfo;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.GameProtocols;
import net.minecraft.resources.ResourceLocation;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record WrappedPacketS2CPacket(UUID id, Packet<?> packet) implements ProxiedPacket {

    public static final ResourceLocation TYPE = AITMod.id("wrapped");

    private static final ProtocolInfo<ClientGamePacketListener> PLAY = GameProtocols.CLIENTBOUND_TEMPLATE.bind(RegistryFriendlyByteBuf.class::cast);

    public static WrappedPacketS2CPacket read(RegistryFriendlyByteBuf buf) {
        UUID id = buf.readUUID();
        Packet<?> packet = readPacket(buf);
        return new WrappedPacketS2CPacket(id, packet);
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeUUID(id);

        if (this.packet instanceof ClientboundBundlePacket bundle) {
            List<Packet<? super ClientGamePacketListener>> packets = (List<Packet<? super ClientGamePacketListener>>) bundle.subPackets();

            buf.writeVarInt(-1);
            buf.writeVarInt(packets.size());

            for (Packet<? super ClientGamePacketListener> packet : packets) {
                writePacket(packet, buf);
            }
        } else {
            writePacket(this.packet, buf);
        }
    }

    private static void writePacket(Packet<?> packet, RegistryFriendlyByteBuf buf) {
        //noinspection unchecked - source: trust me bro.
        PLAY.codec().encode(buf, (Packet<? super ClientGamePacketListener>) packet);
    }

    private static Packet<? super ClientGamePacketListener> readPacket(RegistryFriendlyByteBuf buf) {
        buf.markReaderIndex();
        int packetId = buf.readVarInt();

        if (packetId == -1) {
            int size = buf.readVarInt();
            List<Packet<? super ClientGamePacketListener>> packets = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
                packets.add(readPacket(buf));
            }

            return new ClientboundBundlePacket(packets);
        } else {
            buf.resetReaderIndex();
            return PLAY.codec().decode(buf);
        }
    }
}
