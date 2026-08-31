package dev.amble.lib.platform.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.mojang.serialization.Codec;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import dev.amble.lib.platform.Platform;

public class Attachments {

    public record Type<T>(ResourceLocation id, AttachmentType<T> handle) {}

    private static final List<Type<?>> PENDING = new ArrayList<>();

    static {
        IEventBus modBus = Platform.modBus();

        if (modBus != null)
            modBus.addListener(RegisterEvent.class, event -> event.register(
                    NeoForgeRegistries.Keys.ATTACHMENT_TYPES,
                    registry -> PENDING.forEach(type -> registry.register(type.id(), type.handle()))));
    }

    public static <T> Type<T> createPersistent(ResourceLocation id, Codec<T> codec) {
        AttachmentType<T> handle = AttachmentType.<T>builder(() -> null).serialize(codec).build();
        Type<T> type = new Type<>(id, handle);

        PENDING.add(type);
        return type;
    }

    public static <T> T get(ChunkAccess chunk, Type<T> type) {
        return chunk.getExistingData(type.handle()).orElse(null);
    }

    public static <T> boolean has(ChunkAccess chunk, Type<T> type) {
        return chunk.hasData(type.handle());
    }

    public static <T> void set(ChunkAccess chunk, Type<T> type, T value) {
        chunk.setData(type.handle(), value);
        chunk.setUnsaved(true);
    }

    public static <T> T remove(ChunkAccess chunk, Type<T> type) {
        T ret = chunk.removeData(type.handle());
        chunk.setUnsaved(true);
        return ret;
    }

    public static <T> T getOrCreate(ChunkAccess chunk, Type<T> type, Supplier<T> factory) {
        T cur = get(chunk, type);

        if (cur != null)
            return cur;

        T val = factory.get();
        set(chunk, type, val);
        return val;
    }

    public static <T> T modify(ChunkAccess chunk, Type<T> type, UnaryOperator<T> op) {
        T ret = op.apply(get(chunk, type));
        set(chunk, type, ret);
        return ret;
    }
}
