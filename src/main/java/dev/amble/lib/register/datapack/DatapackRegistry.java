package dev.amble.lib.register.datapack;

import java.util.*;
import java.util.function.Supplier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import dev.amble.lib.AmbleKit;
import dev.amble.lib.api.Identifiable;
import dev.amble.lib.register.Registry;
import dev.amble.lib.util.ServerLifecycleHooks;

/**
 * A registry which is compatible with datapack registering
 */
public abstract class DatapackRegistry<T extends Identifiable> implements Registry {

    protected static final Random RANDOM = new Random();
    protected final HashMap<ResourceLocation, T> REGISTRY = new HashMap<>();

    public abstract T fallback();

    public T register(T schema) {
        return register(schema, schema.id());
    }

    public T register(T schema, ResourceLocation id) {
        REGISTRY.put(id, schema);
        return schema;
    }

    protected static <T> T getRandom(List<T> elements, Random random, Supplier<T> fallback) {
        if (elements.isEmpty())
            return fallback.get();

        int randomized = random.nextInt(elements.size());

        return elements.get(randomized);
    }

    public T getRandom(Random random) {
        return DatapackRegistry.getRandom(this.toList(), random, this::fallback);
    }

    public T getRandom() {
        return this.getRandom(RANDOM);
    }

    public List<T> getRandom(int count) {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            list.add(this.getRandom());
        }

        return list;
    }

    public T get(ResourceLocation id) {
        return REGISTRY.get(id);
    }
    public T getOrElse(ResourceLocation id, T fallback) {
        return REGISTRY.getOrDefault(id, fallback);
    }
    public T getOrFallback(ResourceLocation id) {
        return this.getOrElse(id, this.fallback());
    }
    public Optional<T> getOptional(ResourceLocation id) {
        return Optional.ofNullable(this.get(id));
    }

    public List<T> toList() {
        return List.copyOf(REGISTRY.values());
    }

    public Iterator<T> iterator() {
        return REGISTRY.values().iterator();
    }

    public int size() {
        return REGISTRY.size();
    }

    public void syncToEveryone() {
        if (ServerLifecycleHooks.get() == null) {
            AmbleKit.LOGGER.warn("ServerLifecycleHooks is null, cannot sync");
            return;
        }

        for (ServerPlayer player : ServerLifecycleHooks.get().getPlayerList().getPlayers()) {
            this.syncToClient(player);
        }
    }

    public abstract void syncToClient(ServerPlayer player);

    public abstract void readFromServer(RegistryFriendlyByteBuf buf);

    @Override
    public void onCommonInit() {
        // this.clearCache();
    }

    public void clearCache() {
        REGISTRY.clear();
    }
}
