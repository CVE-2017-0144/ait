package dev.amble.lib.platform.clientlifecycle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.chunk.LevelChunk;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

@Environment(EnvType.CLIENT)
public final class ClientEvents {

    private ClientEvents() {}

    public interface Tick {
        void onTick(Minecraft client);
    }

    public interface Started {
        void onClientStarted(Minecraft client);
    }

    public interface Join {
        void onPlayReady(Minecraft client);
    }

    public interface Disconnect {
        void onPlayDisconnect(Minecraft client);
    }

    public interface Chunk {
        void onChunk(ClientLevel world, LevelChunk chunk);
    }

    public static final Event<Tick> END_CLIENT_TICK = EventFactory.createArrayBacked(Tick.class,
            callbacks -> client -> {
                for (Tick callback : callbacks) {
                    callback.onTick(client);
                }
            });

    public static final Event<Started> CLIENT_STARTED = EventFactory.createArrayBacked(Started.class,
            callbacks -> client -> {
                for (Started callback : callbacks) {
                    callback.onClientStarted(client);
                }
            });

    public static final Event<Join> JOIN = EventFactory.createArrayBacked(Join.class,
            callbacks -> client -> {
                for (Join callback : callbacks) {
                    callback.onPlayReady(client);
                }
            });

    public static final Event<Disconnect> DISCONNECT = EventFactory.createArrayBacked(Disconnect.class,
            callbacks -> client -> {
                for (Disconnect callback : callbacks) {
                    callback.onPlayDisconnect(client);
                }
            });

    public static final Event<Chunk> CHUNK_LOAD = EventFactory.createArrayBacked(Chunk.class,
            callbacks -> (world, chunk) -> {
                for (Chunk callback : callbacks) {
                    callback.onChunk(world, chunk);
                }
            });

    public static final Event<Chunk> CHUNK_UNLOAD = EventFactory.createArrayBacked(Chunk.class,
            callbacks -> (world, chunk) -> {
                for (Chunk callback : callbacks) {
                    callback.onChunk(world, chunk);
                }
            });

    static {
        bootstrap();
    }

    private static void bootstrap() {
        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK
                .register(client -> END_CLIENT_TICK.invoker().onTick(client));
        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.CLIENT_STARTED
                .register(client -> CLIENT_STARTED.invoker().onClientStarted(client));
        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents.CHUNK_LOAD
                .register((world, chunk) -> CHUNK_LOAD.invoker().onChunk(world, chunk));
        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents.CHUNK_UNLOAD
                .register((world, chunk) -> CHUNK_UNLOAD.invoker().onChunk(world, chunk));

        net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.JOIN
                .register((handler, sender, client) -> JOIN.invoker().onPlayReady(client));
        net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.DISCONNECT
                .register((handler, client) -> DISCONNECT.invoker().onPlayDisconnect(client));
        net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents.DISCONNECT
                .register((handler, client) -> DISCONNECT.invoker().onPlayDisconnect(client));
    }
}
