package dev.amble.lib.platform.clientlifecycle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.ChunkEvent;

import dev.amble.lib.platform.Platform;
import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

@OnlyIn(Dist.CLIENT)
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
        NeoForge.EVENT_BUS.addListener(ClientTickEvent.Post.class,
                event -> END_CLIENT_TICK.invoker().onTick(Minecraft.getInstance()));
        NeoForge.EVENT_BUS.addListener(ClientPlayerNetworkEvent.LoggingIn.class,
                event -> JOIN.invoker().onPlayReady(Minecraft.getInstance()));
        NeoForge.EVENT_BUS.addListener(ClientPlayerNetworkEvent.LoggingOut.class,
                event -> DISCONNECT.invoker().onPlayDisconnect(Minecraft.getInstance()));

        NeoForge.EVENT_BUS.addListener(ChunkEvent.Load.class, event -> {
            if (event.getLevel() instanceof ClientLevel world && event.getChunk() instanceof LevelChunk chunk)
                CHUNK_LOAD.invoker().onChunk(world, chunk);
        });
        NeoForge.EVENT_BUS.addListener(ChunkEvent.Unload.class, event -> {
            if (event.getLevel() instanceof ClientLevel world && event.getChunk() instanceof LevelChunk chunk)
                CHUNK_UNLOAD.invoker().onChunk(world, chunk);
        });

        IEventBus modBus = Platform.modBus();

        if (modBus != null)
            modBus.addListener(FMLClientSetupEvent.class,
                    event -> event.enqueueWork(() -> CLIENT_STARTED.invoker()
                            .onClientStarted(Minecraft.getInstance())));
    }
}
