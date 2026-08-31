package dev.amble.ait.core.net;

import dev.amble.ait.core.net.AitNetworking;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import io.netty.buffer.Unpooled;

public final class AitNetworking {

    private AitNetworking() {}

    public record Payload(CustomPacketPayload.Type<Payload> type, FriendlyByteBuf data)
            implements CustomPacketPayload {

        @Override
        public CustomPacketPayload.Type<Payload> type() {
            return this.type;
        }
    }

    private static final Map<ResourceLocation, CustomPacketPayload.Type<Payload>> C2S = new ConcurrentHashMap<>();
    private static final Map<ResourceLocation, CustomPacketPayload.Type<Payload>> S2C = new ConcurrentHashMap<>();

    private static StreamCodec<RegistryFriendlyByteBuf, Payload> codec(CustomPacketPayload.Type<Payload> type) {
        return CustomPacketPayload.codec(
                (payload, out) -> {
                    FriendlyByteBuf data = payload.data();
                    out.writeBytes(data.slice(0, data.writerIndex()));
                },
                in -> new Payload(type, new FriendlyByteBuf(in.readBytes(in.readableBytes()))));
    }

    private static CustomPacketPayload.Type<Payload> c2s(ResourceLocation id) {
        return C2S.computeIfAbsent(id, key -> {
            CustomPacketPayload.Type<Payload> type = new CustomPacketPayload.Type<>(key);
            PayloadTypeRegistry.playC2S().register(type, codec(type));
            return type;
        });
    }

    private static CustomPacketPayload.Type<Payload> s2c(ResourceLocation id) {
        return S2C.computeIfAbsent(id, key -> {
            CustomPacketPayload.Type<Payload> type = new CustomPacketPayload.Type<>(key);
            PayloadTypeRegistry.playS2C().register(type, codec(type));
            return type;
        });
    }

    public static FriendlyByteBuf buf() {
        return new FriendlyByteBuf(Unpooled.buffer());
    }

    @FunctionalInterface
    public interface ServerHandler {
        void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler,
                FriendlyByteBuf buf, PacketSender responseSender);
    }

    @FunctionalInterface
    @Environment(EnvType.CLIENT)
    public interface ClientHandler {
        void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf,
                PacketSender responseSender);
    }

    public static void registerServerReceiver(ResourceLocation id, ServerHandler handler) {
        ServerPlayNetworking.registerGlobalReceiver(c2s(id), (payload, context) -> handler.receive(
                context.server(), context.player(), context.player().connection, payload.data(),
                context.responseSender()));
    }

    @Environment(EnvType.CLIENT)
    public static void registerClientReceiver(ResourceLocation id, ClientHandler handler) {
        ClientPlayNetworking.registerGlobalReceiver(s2c(id), (payload, context) -> handler.receive(
                context.client(), context.client().getConnection(), payload.data(),
                context.responseSender()));
    }

    public static void send(ServerPlayer player, ResourceLocation id, FriendlyByteBuf buf) {
        ServerPlayNetworking.send(player, new Payload(s2c(id), buf));
    }

    @Environment(EnvType.CLIENT)
    public static void send(ResourceLocation id, FriendlyByteBuf buf) {
        ClientPlayNetworking.send(new Payload(c2s(id), buf));
    }
}
