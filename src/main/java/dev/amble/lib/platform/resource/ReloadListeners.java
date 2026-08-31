package dev.amble.lib.platform.resource;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.packs.PackType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import dev.amble.lib.platform.Platform;

public final class ReloadListeners {

    private ReloadListeners() {}

    private static final List<SimpleReloadListener> SERVER = new ArrayList<>();
    private static final List<SimpleReloadListener> CLIENT = new ArrayList<>();

    static {
        NeoForge.EVENT_BUS.addListener(AddReloadListenerEvent.class,
                event -> SERVER.forEach(event::addListener));

        IEventBus modBus = Platform.modBus();

        if (modBus != null && Platform.isClient())
            modBus.addListener(RegisterClientReloadListenersEvent.class,
                    event -> CLIENT.forEach(event::registerReloadListener));
    }

    public static void register(PackType type, SimpleReloadListener listener) {
        if (type == PackType.SERVER_DATA) {
            SERVER.add(listener);
            return;
        }

        CLIENT.add(listener);
    }
}
