package dev.amble.ait.core.net;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.buffer.Unpooled;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import dev.amble.ait.AITMod;
import dev.amble.lib.platform.Platform;
import dev.amble.lib.util.ServerLifecycleHooks;

public class AitNetworking {

    private static final CustomPacketPayload.Type<Payload> TYPE =
            new CustomPacketPayload.Type<>(AITMod.id("channel"));

    public record Payload(ResourceLocation channel, byte[] data) implements CustomPacketPayload {

        @Override
        public CustomPacketPayload.Type<Payload> type() {
            return TYPE;
        }
    }

    private static final StreamCodec<RegistryFriendlyByteBuf, Payload> CODEC = CustomPacketPayload.codec(
            (p, out) -> {
                out.writeResourceLocation(p.channel());
                out.writeByteArray(p.data());
            },
            in -> new Payload(in.readResourceLocation(), in.readByteArray()));

    private static final Map<ResourceLocation, ServerHandler> SERVER = new ConcurrentHashMap<>();
    private static final Map<ResourceLocation, ClientHandler> CLIENT = new ConcurrentHashMap<>();

    public static void init() {
        IEventBus bus = Platform.modBus();

        if (bus == null)
            return;

        bus.addListener(RegisterPayloadHandlersEvent.class, e -> e
                .registrar(AITMod.MOD_ID).versioned("1").optional()
                .playBidirectional(TYPE, CODEC, (p, ctx) -> {
                    if (ctx.flow().isServerbound()) {
                        ServerHandler h = SERVER.get(p.channel());

                        if (h == null || !(ctx.player() instanceof ServerPlayer sp))
                            return;

                        h.receive(sp.getServer(), sp, sp.connection,
                                wrap(p.data(), sp.registryAccess()), null);
                        return;
                    }

                    ClientReceiving.accept(p);
                }));
    }

    public static RegistryFriendlyByteBuf buf(RegistryAccess registries) {
        return new RegistryFriendlyByteBuf(Unpooled.buffer(), registries);
    }

    // holder class keeps Minecraft off the dedicated server classpath
    public static RegistryFriendlyByteBuf buf() {
        MinecraftServer server = ServerLifecycleHooks.get();
        return server != null ? buf(server.registryAccess()) : Client.buf();
    }

    private static RegistryFriendlyByteBuf wrap(byte[] data, RegistryAccess registries) {
        return new RegistryFriendlyByteBuf(Unpooled.wrappedBuffer(data), registries);
    }

    private static byte[] drain(RegistryFriendlyByteBuf buf) {
        byte[] data = new byte[buf.writerIndex()];
        buf.getBytes(0, data);
        return data;
    }

    @OnlyIn(Dist.CLIENT)
    private static final class Client {
        static RegistryFriendlyByteBuf buf() {
            return AitNetworking.buf(Minecraft.getInstance().level.registryAccess());
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static final class ClientReceiving {
        static void accept(Payload p) {
            ClientHandler h = CLIENT.get(p.channel());
            Minecraft client = Minecraft.getInstance();

            if (h == null || client.level == null)
                return;

            h.receive(client, client.getConnection(),
                    wrap(p.data(), client.level.registryAccess()), null);
        }
    }

    public interface PacketSender {}

    @FunctionalInterface
    public interface ServerHandler {
        void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler,
                RegistryFriendlyByteBuf buf, PacketSender responseSender);
    }

    @FunctionalInterface
    @OnlyIn(Dist.CLIENT)
    public interface ClientHandler {
        void receive(Minecraft client, ClientPacketListener handler, RegistryFriendlyByteBuf buf,
                PacketSender responseSender);
    }

    public static void registerServerReceiver(ResourceLocation id, ServerHandler handler) {
        SERVER.put(id, handler);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerClientReceiver(ResourceLocation id, ClientHandler handler) {
        CLIENT.put(id, handler);
    }

    public static void send(ServerPlayer player, ResourceLocation id, RegistryFriendlyByteBuf buf) {
        PacketDistributor.sendToPlayer(player, new Payload(id, drain(buf)));
    }

    @OnlyIn(Dist.CLIENT)
    public static void send(ResourceLocation id, RegistryFriendlyByteBuf buf) {
        PacketDistributor.sendToServer(new Payload(id, drain(buf)));
    }
}
