package dev.amble.lib.platform.event;

import java.util.function.Function;

public class EventFactory {

    public static <T> Event<T> createArrayBacked(Class<? super T> type, Function<T[], T> factory) {
        return new ArrayBackedEvent<>(type, factory);
    }
}
