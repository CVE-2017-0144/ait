package dev.drtheo.scheduler.api.common;

import dev.amble.lib.platform.lifecycle.ServerLifecycleEvents;
import dev.amble.lib.platform.lifecycle.ServerTickEvents;
import dev.amble.lib.platform.lifecycle.ServerWorldEvents;
import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.task.*;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Scheduler {

    private static final ExecutorService service = Util.backgroundExecutor();

    protected final Deque<Task<?>> endServerTickTasks = new ConcurrentLinkedDeque<>();
    protected final Deque<Task<?>> startServerTickTasks = new ConcurrentLinkedDeque<>();
    protected final Map<ServerLevel, Deque<Task<?>>> startWorldTickTasks = new ConcurrentHashMap<>();
    protected final Map<ServerLevel, Deque<Task<?>>> endWorldTickTasks = new ConcurrentHashMap<>();

    private static Scheduler self;

    private Scheduler() {
        ServerTickEvents.START_WORLD_TICK.register(world -> tickMap(world, startWorldTickTasks));
        ServerTickEvents.END_WORLD_TICK.register(world -> tickMap(world, endWorldTickTasks));
        ServerTickEvents.END_SERVER_TICK.register(server -> endServerTickTasks.removeIf(Task::tick));
        ServerTickEvents.END_SERVER_TICK.register(server -> startServerTickTasks.removeIf(Task::tick));

        ServerWorldEvents.UNLOAD.register((server, world) -> {
            this.startWorldTickTasks.remove(world);
            this.endWorldTickTasks.remove(world);
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            this.endServerTickTasks.clear();
            this.startServerTickTasks.clear();
            this.startWorldTickTasks.clear();
            this.endWorldTickTasks.clear();
        });
    }

    private static void tickMap(ServerLevel world, Map<ServerLevel, Deque<Task<?>>> taskMap) {
        Deque<Task<?>> tasks = taskMap.get(world);

        if (tasks == null)
            return;

        tasks.removeIf(Task::tick);
    }

    protected static Deque<Task<?>> getDeque(ServerLevel world, Map<ServerLevel, Deque<Task<?>>> taskMap) {
        return taskMap.computeIfAbsent(world, w -> new ConcurrentLinkedDeque<>());
    }

    public static void init() {
        if (self != null)
            return;

        self = new Scheduler();
    }

    public Task<?> runTaskLater(Runnable runnable, TaskStage stage, TimeUnit unit, long delay) {
        return stage.add(this, new SimpleTask(runnable, TimeUnit.TICKS.from(unit, delay)));
    }

    public Task<?> runAsyncTaskLater(Runnable runnable, TaskStage stage, TimeUnit unit, long delay) {
        return stage.add(this, new AsyncSimpleTask(service, runnable, TimeUnit.TICKS.from(unit, delay)));
    }

    public Task<?> runTaskTimer(Consumer<Task<?>> runnable, TaskStage stage, TimeUnit unit, long period) {
        return stage.add(this, new RepeatingSimpleTask(runnable, TimeUnit.TICKS.from(unit, period)));
    }

    public Task<?> runAsyncTaskTimer(Consumer<Task<?>> runnable, TaskStage stage, TimeUnit unit, long period) {
        return stage.add(this, new AsyncRepeatingSimpleTask(service, runnable, TimeUnit.TICKS.from(unit, period)));
    }

    public static Scheduler get() {
        return self;
    }
}