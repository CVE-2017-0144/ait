package dev.amble.lib.register.datapack;

import java.io.InputStream;
import java.util.function.Function;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.lib.AmbleKit;
import dev.amble.lib.api.Identifiable;
import dev.amble.lib.platform.clientlifecycle.ClientEvents;
import dev.amble.lib.platform.lifecycle.ServerLifecycleEvents;
import dev.amble.lib.platform.resource.ReloadListeners;
import dev.amble.lib.platform.resource.SimpleReloadListener;
import dev.amble.lib.util.ServerLifecycleHooks;

public abstract class SimpleDatapackRegistry<T extends Identifiable> extends DatapackRegistry<T>
        implements
            SimpleReloadListener {

    private final Function<InputStream, T> deserializer;
    private final Codec<T> codec;
    protected final ResourceLocation packet;
    private final ResourceLocation name;
    private final boolean sync;

    public SimpleDatapackRegistry(Function<InputStream, T> deserializer, Codec<T> codec, ResourceLocation packet,
            ResourceLocation name, boolean sync) {
        this.deserializer = deserializer;
        this.codec = codec;
        this.packet = packet;
        this.name = name;
        this.sync = sync;
    }

    protected SimpleDatapackRegistry(Function<InputStream, T> deserializer, Codec<T> codec, String packet, String name,
            boolean sync, String modid) {
        this(deserializer, codec, ResourceLocation.fromNamespaceAndPath(modid, "sync_" + packet), ResourceLocation.fromNamespaceAndPath(modid, name),
                sync);
    }

    protected SimpleDatapackRegistry(Function<InputStream, T> deserializer, Codec<T> codec, String name, boolean sync, String modid) {
        this(deserializer, codec, name, name, sync, modid);
    }

    /**
     * @deprecated Use {@link #SimpleDatapackRegistry(Function, Codec, String, boolean, String)} instead and provide your own modid
     */
    @Deprecated(forRemoval = true, since = "1.0.11")
    protected SimpleDatapackRegistry(Function<InputStream, T> deserializer, Codec<T> codec, String name) {
        this(deserializer, codec, name, name, true, AmbleKit.MOD_ID);
    }

    public void onClientInit() {
        if (!this.sync)
            return;

        // already on client thread, another execute lands after the recipes packet
        AitNetworking.registerClientReceiver(this.packet, (client, handler, buf, responseSender) -> this.readFromServer(buf));

        ClientEvents.DISCONNECT.register((client) -> {
            this.clearCache();
            this.defaults();
        });
    }

    /**
     * @implNote Currently not implemented as there's no dedicated server-side logic
     */
    public void onServerInit() {
    }

    public void onCommonInit() {
        ReloadListeners.register(PackType.SERVER_DATA, this);

        if (!this.sync)
            return;

        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> this.syncToClient(player));
    }

    @Override
    public void syncToEveryone() {
        if (!this.sync || ServerLifecycleHooks.get() == null)
            return;

        super.syncToEveryone();
    }

    @Override
    public void syncToClient(ServerPlayer player) {
        if (!this.sync)
            return;

        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeInt(REGISTRY.size());

        for (T schema : REGISTRY.values()) {
            buf.writeJsonWithCodec(this.codec, schema);
        }

        AitNetworking.send(player, this.packet, buf);
    }

    @Override
    public void readFromServer(RegistryFriendlyByteBuf buf) {
        if (!this.sync)
            return;

        this.clearCache();
        this.defaults();
        int size = buf.readInt();

        for (int i = 0; i < size; i++) {
            this.register(buf.readJsonWithCodec(this.codec));
        }

        AmbleKit.LOGGER.info("Read {} {} from server", size, this.name);
    }

    protected abstract void defaults();

    protected T read(InputStream stream) {
        return this.deserializer.apply(stream);
    }

    @Override
    public ResourceLocation getReloadId() {
        return SimpleDatapackRegistry.this.name;
    }

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        // this.clearCache();
        this.defaults();

        for (ResourceLocation id : manager
                .listResources(this.name.getPath(), filename -> filename.getPath().endsWith(".json")).keySet()) {
            try (InputStream stream = manager.getResource(id).get().open()) {
                T created = this.read(stream);

                if (created == null) {
                    stream.close();
                    continue;
                }

                this.register(created);
                AmbleKit.LOGGER.info("Loaded datapack {} {}", this.name, created.id().toString());
            } catch (Exception e) {
                AmbleKit.LOGGER.error("Error occurred while loading resource json {}", id.toString(), e);
            }
        }

        this.syncToEveryone();
    }
}
