package dev.amble.ait.compat.portal;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientCommonPacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import qouteall.q_misc_util.MiscNetworking;

public final class PortalsDimSync {

    private PortalsDimSync() {}

    public static void sync(MinecraftServer server) {
        if (server == null)
            return;

        Packet<ClientCommonPacketListener> packet = MiscNetworking.DimIdSyncPacket.createPacket(server);

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            player.connection.send(packet);
        }
    }
}
