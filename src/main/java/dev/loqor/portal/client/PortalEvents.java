package dev.loqor.portal.client;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public interface PortalEvents {

    Event<PortalUpdateEvent> UPDATE = EventFactory.createArrayBacked(PortalUpdateEvent.class, events -> (data) -> {
        for (PortalUpdateEvent event : events) {
            event.onPortalUpdate(data);
        }
    });

    @FunctionalInterface
    interface PortalUpdateEvent {
        void onPortalUpdate(PortalData data);
    }
}
