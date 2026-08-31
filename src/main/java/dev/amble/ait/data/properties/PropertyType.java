package dev.amble.ait.data.properties;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class PropertyType<T> {

    private final Class<?> clazz;
    private final BiConsumer<RegistryFriendlyByteBuf, T> encoder;
    private final Function<RegistryFriendlyByteBuf, T> decoder;

    public PropertyType(Class<?> clazz, BiConsumer<RegistryFriendlyByteBuf, T> encoder, Function<RegistryFriendlyByteBuf, T> decoder) {
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

    public void encode(RegistryFriendlyByteBuf buf, T value) {
        this.encoder.accept(buf, value);
    }

    public T decode(RegistryFriendlyByteBuf buf) {
        return this.decoder.apply(buf);
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public static <T extends Enum<T>> PropertyType<T> forEnum(Class<T> clazz) {
        return new PropertyType<>(clazz, FriendlyByteBuf::writeEnum, buf -> buf.readEnum(clazz));
    }

    public static class Nullable<T> extends PropertyType<T> {

        public Nullable(Class<?> clazz, BiConsumer<RegistryFriendlyByteBuf, T> encoder, Function<RegistryFriendlyByteBuf, T> decoder) {
            super(clazz, encoder, decoder);
        }

        @Override
        public boolean isValid(T t) {
            return true;
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, T value) {
            buf.writeNullable(value, (b, v) -> super.encode((RegistryFriendlyByteBuf) b, v));
        }

        @Override
        public T decode(RegistryFriendlyByteBuf buf) {
            return buf.readNullable(b -> super.decode((RegistryFriendlyByteBuf) b));
        }
    }
}