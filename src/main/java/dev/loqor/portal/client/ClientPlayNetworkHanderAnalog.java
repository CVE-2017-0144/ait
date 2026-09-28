package dev.loqor.portal.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;

public class ClientPlayNetworkHanderAnalog extends ClientPacketListener {
    public ClientPlayNetworkHanderAnalog(Minecraft client, Connection connection, CommonListenerCookie cookie) {
        super(client, connection, cookie);
    }
}
