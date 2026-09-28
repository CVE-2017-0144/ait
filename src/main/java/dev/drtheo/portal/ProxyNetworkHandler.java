package dev.drtheo.portal;

import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundChunkBatchFinishedPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.PlayerChunkSender;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;

public class ProxyNetworkHandler extends ServerGamePacketListenerImpl {

    private static final Connection FAKE_CONNECTION = new Connection(PacketFlow.CLIENTBOUND);

    private ProxyPacketListener packetListener;

    private boolean batchSent;

    public ProxyNetworkHandler(ServerPlayer player) {
        super(player.getServer(), FAKE_CONNECTION, player, CommonListenerCookie.createInitial(player.getGameProfile(), false));
    }

    @Override
    public void send(Packet<?> packet, @Nullable PacketSendListener callbacks) {
        if (packet instanceof ClientboundChunkBatchFinishedPacket)
            this.batchSent = true;

        if (this.packetListener != null && !(packet instanceof ProxiedPacket))
            this.packetListener.onPacket(unwrapChunk(packet));
    }

    // no netty channel to read the negotiated payloads from
    @Override
    public boolean hasChannel(ResourceLocation payloadId) {
        return false;
    }

    public boolean sendChunkBatch() {
        this.batchSent = false;
        this.chunkSender.sendNextChunks(this.player);

        if (this.batchSent)
            this.chunkSender.onChunkBatchReceivedByClient(PlayerChunkSender.MAX_CHUNKS_PER_TICK);

        return this.batchSent;
    }

    // neoforge sends every chunk bundled with its auxiliary light data
    private static Packet<?> unwrapChunk(Packet<?> packet) {
        if (packet instanceof ClientboundBundlePacket bundle) {
            for (Packet<?> part : bundle.subPackets()) {
                if (part instanceof ClientboundLevelChunkWithLightPacket chunk)
                    return chunk;
            }
        }

        return packet;
    }

    public void setListener(ProxyPacketListener listener) {
        this.packetListener = listener;
    }
}
