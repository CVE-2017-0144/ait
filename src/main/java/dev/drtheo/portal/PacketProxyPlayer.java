package dev.drtheo.portal;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.util.FakePlayer;
import java.util.UUID;

public class PacketProxyPlayer extends FakePlayer {

    public PacketProxyPlayer(ServerLevel world) {
        super(world, new GameProfile(UUID.randomUUID(), "[Ptl Packet Proxy]"));
        this.connection = new ProxyNetworkHandler(this);
    }

    public void setPacketListener(ProxyPacketListener listener) {
        ((ProxyNetworkHandler) this.connection).setListener(listener);
    }

    public void onChunkEntered() {
        this.serverLevel().getChunkSource().move(this);
    }

    // not in the player list, nothing else sends their chunk batches
    public void sendChunks() {
        boolean sent = false;

        while (((ProxyNetworkHandler) this.connection).sendChunkBatch())
            sent = true;

        if (sent)
            this.onChunkEntered();
    }

    // chunk tracking is per player since 1.20.2, proxy never sends client options
    @Override
    public int requestedViewDistance() {
        return this.server.getPlayerList().getViewDistance();
    }

    @Override
    public boolean isSpectator() {
        return true;
    }
}
