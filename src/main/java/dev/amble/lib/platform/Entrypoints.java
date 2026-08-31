package dev.amble.lib.platform;

import java.util.ServiceLoader;
import java.util.function.Consumer;

public class Entrypoints {

    public static <T> void invoke(Class<T> type, Consumer<? super T> action) {
        for (T impl : ServiceLoader.load(type, type.getClassLoader())) {
            try {
                action.accept(impl);
            } catch (Throwable e) {
                throw new RuntimeException("Entrypoint " + impl.getClass().getName()
                        + " for " + type.getSimpleName() + " failed", e);
            }
        }
    }
}
