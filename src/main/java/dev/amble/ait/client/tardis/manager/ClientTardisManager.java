package dev.amble.ait.client.tardis.manager;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.GsonBuilder;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.sounds.ClientSoundManager;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.TardisManager;
import dev.amble.ait.data.Exclude;
import dev.amble.ait.data.TardisMap;
import dev.amble.ait.registry.impl.TardisComponentRegistry;
import dev.amble.lib.platform.Platform;
import dev.amble.lib.platform.clientlifecycle.ClientEvents;
import dev.amble.lib.platform.lifecycle.ServerLifecycleEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import org.jetbrains.annotations.Nullable;

public class ClientTardisManager extends TardisManager<ClientTardis, Minecraft> {

    private static ClientTardisManager instance;

    private final TardisMap.Direct<ClientTardis> lookup = new TardisMap.Direct<>();
    public final Multimap<UUID, Consumer<ClientTardis>> subscribers = ArrayListMultimap.create();

    public static void init() {
        if (!Platform.isClient())
            throw new UnsupportedOperationException("Tried to initialize ClientTardisManager on the server!");

        instance = new ClientTardisManager();
    }

    private ClientTardisManager() {
        AitNetworking.registerClientReceiver(SEND, (client, handler, buf, responseSender) -> this.syncTardis(buf));

        AitNetworking.registerClientReceiver(SEND_BULK,
                (client, handler, buf, responseSender) -> this.syncBulk(buf));

        AitNetworking.registerClientReceiver(REMOVE, (client, handler, buf, responseSender) -> this.remove(buf));

        AitNetworking.registerClientReceiver(SEND_COMPONENT, (client, handler, buf, responseSender) -> this.syncDelta(buf));

        ClientEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.level == null)
                return;

            for (ClientTardis tardis : this.lookup.values()) {
                tardis.tick(client);
            }

            ClientSoundManager.tick(client);
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> this.reset());
        ClientEvents.DISCONNECT.register((reason) -> this.reset());
        ClientEvents.DISCONNECT.register((client) -> this.reset());
    }

    private void remove(RegistryFriendlyByteBuf buf) {
        this.lookup.remove(buf.readUUID());
    }

    @Override
    protected TardisMap.Direct<ClientTardis> lookup() {
        return lookup;
    }

    @Override
    @Deprecated
    public @Nullable ClientTardis demandTardis(Minecraft client, UUID uuid) {
        Objects.requireNonNull(uuid);
        return this.lookup.get(uuid);
    }

    @Deprecated
    public @Nullable ClientTardis demandTardis(UUID uuid) {
        return this.demandTardis(Minecraft.getInstance(), uuid);
    }

    public void getTardis(UUID uuid, Consumer<ClientTardis> consumer) {
        this.getTardis(Minecraft.getInstance(), uuid, consumer);
    }

    private void syncTardis(UUID uuid, String json) {
        try {
            ClientTardis tardis = this.networkGson.fromJson(json, ClientTardis.class);
            Tardis.init(tardis, TardisComponent.InitContext.deserialize());

            ClientTardis old = this.lookup.put(tardis);

            if (old != null)
                old.age();

            for (Consumer<ClientTardis> consumer : this.subscribers.removeAll(uuid)) {
                consumer.accept(tardis);
            }
        } catch (Throwable t) {
            AITMod.LOGGER.error("Received malformed JSON file {}", json);
            AITMod.LOGGER.error("Failed to deserialize TARDIS data: ", t);
        }
    }

    private void syncTardis(RegistryFriendlyByteBuf buf) {
        this.syncTardis(buf.readUUID(), buf.readUtf());
    }

    private void syncBulk(RegistryFriendlyByteBuf buf) {
        int count = buf.readInt();

        for (int i = 0; i < count; i++) {
            this.syncTardis(buf);
        }
    }

    private void syncDelta(RegistryFriendlyByteBuf buf) {
        UUID id = buf.readUUID();
        int count = buf.readShort();

        ClientTardis tardis = this.demandTardis(id);

        TardisComponent[] components = new TardisComponent[count];
        TardisComponent.IdLike[] ids = new TardisComponent.IdLike[count];

        if (tardis == null)
            return; // wait 'till the server sends a full update

        for (int i = 0; i < count; i++) {
            String rawId = buf.readUtf();
            TardisComponent.IdLike idLike = TardisComponentRegistry.getInstance().get(rawId);
            ids[i] = idLike;
            TardisComponent component = this.networkGson.fromJson(buf.readUtf(), idLike.clazz());
            if (component == null) {
                AITMod.LOGGER.error("Received null component for id {} in TARDIS {}", rawId, tardis.getUuid());
                continue;
            }
            components[i] = component;
        }
        Minecraft.getInstance().execute(() -> {
            for (int p = 0; p < components.length; p++) {
                TardisComponent component = components[p];
                TardisComponent.IdLike idLike = ids[p];
                idLike.set(tardis, component);
                TardisComponent.init(component, tardis, TardisComponent.InitContext.deserialize());
            }
        });
    }

    @Override
    protected GsonBuilder createGsonBuilder(Exclude.Strategy strategy) {
        return super.createGsonBuilder(strategy)
                .registerTypeAdapter(ClientTardis.class, ClientTardis.creator());
    }

    @Override
    public void getTardis(Minecraft client, UUID uuid, Consumer<ClientTardis> consumer) {
        if (uuid == null)
            return; // ugh

        ClientTardis result = this.lookup().get(uuid);

        if (result == null)
            return;

        consumer.accept(result);
    }

    @Override
    public void reset() {
        this.subscribers.clear();

        this.forEach(ClientTardis::dispose);
        super.reset();
    }

    @Override
    public void forEach(Consumer<ClientTardis> consumer) {
        this.lookup.forEach((uuid, tardis) -> consumer.accept(tardis));
    }

    public static ClientTardisManager getInstance() {
        return instance;
    }
}
