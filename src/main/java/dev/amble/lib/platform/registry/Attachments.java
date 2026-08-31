package dev.amble.lib.platform.registry;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.mojang.serialization.Codec;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.ChunkAccess;

public final class Attachments {

    private Attachments() {}

    public record Type<T>(net.fabricmc.fabric.api.attachment.v1.AttachmentType<T> handle) {}

    public static <T> Type<T> createPersistent(ResourceLocation id, Codec<T> codec) {
        return new Type<>(AttachmentRegistry.createPersistent(id, codec));
    }

    public static <T> T get(ChunkAccess chunk, Type<T> type) {
        return chunk.getAttached(type.handle());
    }

    public static <T> boolean has(ChunkAccess chunk, Type<T> type) {
        return chunk.hasAttached(type.handle());
    }

    public static <T> void set(ChunkAccess chunk, Type<T> type, T value) {
        chunk.setAttached(type.handle(), value);
    }

    public static <T> T remove(ChunkAccess chunk, Type<T> type) {
        return chunk.removeAttached(type.handle());
    }

    public static <T> T getOrCreate(ChunkAccess chunk, Type<T> type, Supplier<T> factory) {
        return chunk.getAttachedOrCreate(type.handle(), factory);
    }

    public static <T> T modify(ChunkAccess chunk, Type<T> type, UnaryOperator<T> operator) {
        return chunk.modifyAttached(type.handle(), operator);
    }
}
