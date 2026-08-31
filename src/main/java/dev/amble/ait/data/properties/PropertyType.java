package dev.amble.ait.data.properties;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;

public class PropertyType<T> {

    private final Class<?> clazz;
    private final BiConsumer<FriendlyByteBuf, T> encoder;
    private final Function<FriendlyByteBuf, T> decoder;

    public PropertyType(Class<?> clazz, BiConsumer<FriendlyByteBuf, T> encoder, Function<FriendlyByteBuf, T> decoder) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public boolean isValid(T t) {
        return t != null;
    }

    public boolean equals(T first, T other) {
        return Objects.equals(first, other);
    }

    public void encode(FriendlyByteBuf buf, T value) {
        this.encoder.accept(buf, value);
    }

    public T decode(FriendlyByteBuf buf) {
        return this.decoder.apply(buf);
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public static <T extends Enum<T>> PropertyType<T> forEnum(Class<T> clazz) {
        return new PropertyType<>(clazz, FriendlyByteBuf::writeEnum, buf -> buf.readEnum(clazz));
    }

    public static class Nullable<T> extends PropertyType<T> {

        public Nullable(Class<?> clazz, BiConsumer<FriendlyByteBuf, T> encoder, Function<FriendlyByteBuf, T> decoder) {
            super(clazz, encoder, decoder);
        }

        @Override
        public boolean isValid(T t) {
            return true;
        }

        @Override
        public void encode(FriendlyByteBuf buf, T value) {
            buf.writeNullable(value, super::encode);
        }

        @Override
        public T decode(FriendlyByteBuf buf) {
            return buf.readNullable(super::decode);
        }
    }
}