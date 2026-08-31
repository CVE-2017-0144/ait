package dev.amble.ait.client.data;

import java.util.HashMap;
import java.util.Map;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.util.NetworkUtil;
import dev.amble.ait.core.world.LandingPadManager;
import dev.amble.ait.data.landing.LandingPadRegion;

public class ClientLandingManager {

    private static ClientLandingManager instance;

    public static void init() {
        ClientPlayConnectionEvents.DISCONNECT.register(((handler, client)
                -> ClientLandingManager.getInstance().invalidate()));

        ClientChunkEvents.CHUNK_LOAD.register((world, chunk)
                -> ClientLandingManager.getInstance().request(world, chunk));

        ClientChunkEvents.CHUNK_UNLOAD.register((world, chunk)
                -> ClientLandingManager.getInstance().remove(chunk.getPos()));

        AitNetworking.registerClientReceiver(LandingPadManager.Network.SYNC, (client, handler, buf, responseSender) -> {
            ClientLandingManager.getInstance().receive(buf);
        });

        instance = new ClientLandingManager();
    }

    public static ClientLandingManager getInstance() {
        return instance;
    }

    private final Map<ChunkPos, LandingPadRegion> regions = new HashMap<>();

    public @Nullable LandingPadRegion getRegion(ChunkPos pos) {
        return this.regions.get(pos);
    }

    public void receive(RegistryFriendlyByteBuf buf) {
        LandingPadManager.Network.Action action = buf.readEnum(LandingPadManager.Network.Action.class);

        if (action == LandingPadManager.Network.Action.CLEAR) {
            this.invalidate();
            return;
        }

        ChunkPos chunkPos = buf.readChunkPos();

        if (action == LandingPadManager.Network.Action.ADD) {
            this.regions.put(chunkPos, NetworkUtil.receive(LandingPadRegion.CODEC, buf));
            return;
        }

        this.regions.remove(chunkPos);
    }

    private void invalidate() {
        this.regions.clear();
    }

    private void remove(ChunkPos pos) {
        this.regions.remove(pos);
    }

    private void request(ResourceKey<Level> world, long chunk) {
        CompoundTag data = new CompoundTag();

        data.putString("World", world.location().toString());
        data.putLong("Chunk", chunk);

        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeNbt(data);

        AitNetworking.send(LandingPadManager.Network.REQUEST, buf);
    }

    private void request(ClientLevel world, LevelChunk chunk) {
        if (!NetworkUtil.canClientSendPackets())
            return;

        this.request(world.dimension(), chunk.getPos().toLong());
    }
}
