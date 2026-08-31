package dev.drtheo.scheduler.client;

import dev.amble.lib.platform.ClientModEntrypoint;
import dev.drtheo.scheduler.api.client.ClientScheduler;

public class SchedulerClientMod implements ClientModEntrypoint {

    @Override
    public void onInitializeClient() {
        ClientScheduler.init();
    }
}
