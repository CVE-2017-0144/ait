package dev.amble.lib.platform.event;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;

final class ArrayBackedEvent<T> extends Event<T> {

    private final Function<T[], T> factory;
    private final Object lock = new Object();

    private T[] handlers;

    @SuppressWarnings("unchecked")
    ArrayBackedEvent(Class<? super T> type, Function<T[], T> factory) {
        this.factory = factory;
        this.handlers = (T[]) Array.newInstance(type, 0);
        this.invoker = factory.apply(this.handlers);
    }

    @Override
    public void register(T listener) {

        synchronized (this.lock) {
            T[] grown = Arrays.copyOf(this.handlers, this.handlers.length + 1);
            grown[grown.length - 1] = listener;

            this.handlers = grown;
            this.invoker = this.factory.apply(grown);
        }
    }
}
