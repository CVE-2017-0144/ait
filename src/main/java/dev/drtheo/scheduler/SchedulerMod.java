package dev.drtheo.scheduler;

import dev.amble.lib.platform.ModEntrypoint;
import dev.drtheo.scheduler.api.common.Scheduler;

public class SchedulerMod implements ModEntrypoint {

    @Override
    public void onInitialize() {
        Scheduler.init();
    }
}
