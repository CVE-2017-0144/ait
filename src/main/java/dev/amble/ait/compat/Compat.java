package dev.amble.ait.compat;


import dev.amble.ait.api.AITModInitializer;
import dev.amble.ait.compat.gravity.GravityHandler;
import dev.amble.lib.platform.ClientModEntrypoint;

public class Compat implements AITModInitializer, ClientModEntrypoint {

    @Override
    public void onInitializeAIT() {
        if (DependencyChecker.hasGravity())
            GravityHandler.init();

    }

    @Override
    public void onInitializeClient() {
        if (DependencyChecker.hasGravity())
            GravityHandler.clientInit();

    }
}
