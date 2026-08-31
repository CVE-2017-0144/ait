package dev.amble.ait.api;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public class AITRegistryEvents {
    public static final Event<Defaults> EXTERIOR_DEFAULTS = EventFactory.createArrayBacked(Defaults.class, callbacks -> () -> {
        for (Defaults callback : callbacks) {
            callback.defaults();
        }
    });

    /**
     * Called when registries are initialized
     */
    @FunctionalInterface
    public interface Defaults {
        void defaults();
    }
}
