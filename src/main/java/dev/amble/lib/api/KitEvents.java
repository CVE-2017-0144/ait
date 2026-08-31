package dev.amble.lib.api;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public class KitEvents {
    public static final Event<PreDatapackLoad> PRE_DATAPACK_LOAD = EventFactory.createArrayBacked(PreDatapackLoad.class, callbacks -> () -> {
        for (PreDatapackLoad callback : callbacks) {
            callback.load();
        }
    });

    /**
     * Called when just before datapacks are loaded
     */
    @FunctionalInterface
    public interface PreDatapackLoad {
        void load();
    }
}
