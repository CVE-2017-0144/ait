package dev.amble.ait.core.tardis.manager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.api.tardis.WorldWithTardis;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.manager.old.DeprecatedServerTardisManager;
import dev.amble.ait.core.tardis.util.NetworkUtil;
import dev.amble.ait.data.properties.Value;
import dev.amble.ait.registry.impl.TardisComponentRegistry;
import dev.amble.lib.platform.lifecycle.ServerConnectionEvents;
import dev.amble.lib.platform.lifecycle.ServerLifecycleEvents;
import dev.amble.lib.platform.lifecycle.ServerTickEvents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.ChunkWatchEvent;

public class ServerTardisManager extends DeprecatedServerTardisManager {

    private static ServerTardisManager instance;

    private final Set<ServerTardis> delta = ConcurrentHashMap.newKeySet();
    private final Set<UUID> ids = new HashSet<>();

    public static void init() {
        instance = new ServerTardisManager();
    }

    private ServerTardisManager() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> this.ids.addAll(this.fileManager.getTardisList(server)));

        TardisEvents.SYNC_TARDIS.register(WorldWithTardis.forSync((player, tardisSet) -> {
            if (this.fileManager.isLocked())
                return;

            if (AITMod.CONFIG.sendBulk && tardisSet.size() >= 8) {
                this.sendTardisBulk(player, tardisSet);
                return;
            }

            this.sendTardisAll(player, tardisSet);
        }));

        NeoForge.EVENT_BUS.addListener(ChunkWatchEvent.Sent.class,
                event -> TardisEvents.SYNC_TARDIS.invoker().sync(event.getPlayer(), event.getPos()));

        ServerConnectionEvents.JOIN.register((player, server)
                -> this.sendTardisAll(player, NetworkUtil.findLinkedItems(player)));

        if (DEMENTIA) {
            TardisEvents.UNLOAD_TARDIS.register(WorldWithTardis.forDesync((player, tardisSet) -> {
                for (ServerTardis tardis : tardisSet) {
                    if (isInvalid(tardis))
                        continue;

                    this.sendTardisRemoval(player, tardis);
                }
            }));
        }

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (this.fileManager.isLocked())
                return;

            for (ServerTardis tardis : this.delta) {
                if (isInvalid(tardis))
                    continue;

                if (!tardis.hasDelta())
                    continue;

                RegistryFriendlyByteBuf buf = this.prepareSendDelta(tardis);
                tardis.consumeDelta(component -> this.writeComponent(component, buf));

                NetworkUtil.getSubscribedPlayers(tardis).forEach(
                        watching -> AitNetworking.send(watching, SEND_COMPONENT, buf)
                );
            }

            this.delta.clear();
        });
    }

    @Override
    public ServerTardis create(TardisBuilder builder) {
        if (this.isFull()) return null;

        ServerTardis result = super.create(builder);
        this.ids.add(result.getUuid());
        this.sendTardisAll(Set.of(result));

        return result;
    }

    private void sendTardis(ServerPlayer player, RegistryFriendlyByteBuf data) {
        AitNetworking.send(player, SEND, data);
    }

    private void writeSend(ServerTardis tardis, RegistryFriendlyByteBuf buf) {
        buf.writeUUID(tardis.getUuid());
        buf.writeUtf(this.networkGson.toJson(tardis, ServerTardis.class));
    }

    private void writeComponent(TardisComponent component, RegistryFriendlyByteBuf buf) {
        String rawId = TardisComponentRegistry.getInstance().get(component);

        buf.writeUtf(rawId);
        buf.writeUtf(this.networkGson.toJson(component));
    }

    private RegistryFriendlyByteBuf prepareSend(ServerTardis tardis) {
        RegistryFriendlyByteBuf data = AitNetworking.buf();
        this.writeSend(tardis, data);

        return data;
    }

    private RegistryFriendlyByteBuf prepareSendDelta(ServerTardis tardis) {
        RegistryFriendlyByteBuf data = AitNetworking.buf();

        data.writeUUID(tardis.getUuid());
        data.writeShort(tardis.getDeltaSize());

        return data;
    }

    protected void sendTardisBulk(ServerPlayer player, Set<ServerTardis> set) {
        RegistryFriendlyByteBuf data = AitNetworking.buf();
        data.writeInt(set.size());

        for (ServerTardis tardis : set) {
            if (isInvalid(tardis))
                continue;

            this.writeSend(tardis, data);
        }

        AitNetworking.send(player, SEND_BULK, data);
    }

    protected void sendTardisAll(ServerPlayer player, Set<ServerTardis> set) {
        for (ServerTardis tardis : set) {
            if (isInvalid(tardis))
                continue;

            TardisEvents.SEND_TARDIS.invoker().send(tardis, player);
            this.sendTardis(player, this.prepareSend(tardis));
        }
    }

    protected void sendTardisAll(Set<ServerTardis> set) {
        for (ServerTardis tardis : set) {
            if (isInvalid(tardis))
                continue;

            RegistryFriendlyByteBuf buf = this.prepareSend(tardis);

            NetworkUtil.getSubscribedPlayers(tardis).forEach(
                    watching -> {
                        TardisEvents.SEND_TARDIS.invoker().send(tardis, watching);
                        this.sendTardis(watching, buf);
                    }
            );
        }
    }

    public void mark(ServerLevel world, ServerTardis tardis, ChunkPos chunk) {
        ((WorldWithTardis) world).ait$lookup().put(chunk, tardis);

        NetworkUtil.getSubscribedPlayers(tardis).forEach(player ->
                TardisEvents.SYNC_TARDIS.invoker().sync(player, chunk));
    }

    public void unmark(ServerLevel world, ServerTardis tardis, ChunkPos chunk) {
        ((WorldWithTardis) world).ait$withLookup(lookup -> lookup.remove(chunk, tardis));
    }

    @Override
    public void markComponentDirty(TardisComponent component) {
        if (this.fileManager.isLocked())
            return;

        if (!(component.tardis() instanceof ServerTardis tardis))
            return;

        if (isInvalid(tardis))
            return;

        tardis.markDirty(component);
        this.delta.add(tardis);
    }

    @Override
    public void markPropertyDirty(ServerTardis tardis, Value<?> value) {
        this.markComponentDirty(value.getHolder());
    }

    @Override
    public void remove(MinecraftServer server, ServerTardis tardis) {
        super.remove(server, tardis);
        this.ids.remove(tardis.getUuid());
    }

    @Override
    public void reset() {
        this.delta.clear();
        this.ids.clear();
        super.reset();
    }

    public boolean isFull() {
        int max = AITMod.CONFIG.maxTardises;
        return max > 0 && this.ids.size() >= max;
    }

    private static boolean isInvalid(ServerTardis tardis) {
        return tardis == null || tardis.isRemoved();
    }

    public static ServerTardisManager getInstance() {
        return instance;
    }
}
