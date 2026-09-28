package dev.drtheo.portal;

import net.minecraft.network.protocol.Packet;

public interface ProxyPacketListener {
    void onPacket(Packet<?> packet);
}
