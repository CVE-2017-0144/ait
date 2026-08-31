package dev.amble.ait.compat;

import net.fabricmc.api.ClientModInitializer;

import dev.amble.ait.api.AITModInitializer;
import dev.amble.ait.compat.portal.PortalsHandler;

public class Compat implements AITModInitializer, ClientModInitializer {

    @Override
    public void onInitializeAIT() {
        if (DependencyChecker.hasPortals())
            PortalsHandler.init();
    }

    @Override
    public void onInitializeClient() {
        if (DependencyChecker.hasPortals())
            PortalsHandler.clientInit();
    }
}
