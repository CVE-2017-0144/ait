package dev.drtheo.multidim.mixin;

import dev.drtheo.multidim.api.MutableRegistry;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

@Mixin(MappedRegistry.class)
public abstract class SimpleRegistryMixin<T> implements MutableRegistry<T> {

    @Shadow @Final private Map<ResourceLocation, Holder.Reference<T>> byLocation;

    @Shadow @Final private Map<T, Holder.Reference<T>> byValue;

    @Shadow @Final private Reference2IntMap<T> toId;

    @Shadow @Final private ObjectList<Holder.Reference<T>> byId;

    @Shadow @Final private Map<ResourceKey<T>, Holder.Reference<T>> byKey;

    @Shadow @Final private Map<ResourceKey<T>, RegistrationInfo> registrationInfos;

    @Shadow private boolean frozen;

    @Shadow public abstract Holder.Reference<T> register(ResourceKey<T> key, T entry, RegistrationInfo info);

    @Shadow public abstract boolean containsKey(ResourceKey<T> key);

    @Override
    public boolean multidim$remove(T entry) {
        Holder.Reference<T> registryEntry = this.byValue.get(entry);
        int rawId = this.toId.removeInt(entry);

        if (rawId == -1)
            return false;

        try {
            this.byId.set(rawId, null);

            this.byLocation.remove(registryEntry.key().location());
            this.byKey.remove(registryEntry.key());

            this.registrationInfos.remove(registryEntry.key());
            this.byValue.remove(entry);

            return true;
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean multidim$remove(ResourceLocation key) {
        Holder.Reference<T> entry = this.byLocation.get(key);
        return entry != null && entry.isBound() && this.multidim$remove(entry.value());
    }

    @Override
    public void multidim$freeze() {
        this.frozen = true;
    }

    @Override
    public void multidim$unfreeze() {
        this.frozen = false;
    }

    @Override
    public boolean multidim$isFrozen() {
        return this.frozen;
    }

    @Override
    public boolean multidim$contains(ResourceKey<T> key) {
        return this.containsKey(key);
    }

    @Override
    public Holder.Reference<T> multidim$add(ResourceKey<T> key, T entry, RegistrationInfo info) {
        return this.register(key, entry, info);
    }
}
