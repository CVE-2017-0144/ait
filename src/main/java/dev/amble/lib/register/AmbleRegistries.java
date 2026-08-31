package dev.amble.lib.register;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import dev.amble.lib.AmbleKit;
import dev.amble.lib.api.KitEvents;
import dev.amble.lib.platform.Platform;
import dev.amble.lib.platform.clientlifecycle.ClientEvents;


// TODO: move all registries over to here
public class AmbleRegistries {

    private static AmbleRegistries INSTANCE;
    private final HashSet<Registry> registries = new HashSet<>();
    private final Set<InitType> initialized = new HashSet<>();

    static {
        KitEvents.PRE_DATAPACK_LOAD.register(() -> {
            AmbleRegistries.getInstance().subscribe(InitType.COMMON);
            AmbleRegistries.getInstance().subscribe(InitType.SERVER);
        });

        if (Platform.isClient()) {
            registerClientStart();
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerClientStart() {
        ClientEvents.CLIENT_STARTED.register(client -> {
            AmbleRegistries.getInstance().subscribe(InitType.CLIENT);
        });
    }

    private AmbleRegistries() {

    }


    protected void subscribe(InitType env) {
        if (env == InitType.CLIENT && !Platform.isClient())
            throw new UnsupportedOperationException("Cannot call onInitializeClient while not running a client!");

        if (initialized.contains(env))
            return;

        if (env == InitType.CLIENT) {
            this.subscribe(InitType.COMMON);
        }

        AmbleKit.LOGGER.info("Initializing {} side registries..", env);

        for (Registry registry : registries) {
            env.init(registry);
        }

        initialized.add(env);
    }

    public Registry register(Registry registry) {
        registries.add(registry);

        return registry;
    }

    public void registerAll(Registry... registries) {
        for (Registry registry : registries) {
            register(registry);
        }
    }

    public static AmbleRegistries getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AmbleRegistries();

        return INSTANCE;
    }

    public enum InitType {
        CLIENT(Registry::onClientInit), SERVER(Registry::onServerInit), COMMON(Registry::onCommonInit);

        private final Consumer<Registry> consumer;

        InitType(Consumer<Registry> consumer) {
            this.consumer = consumer;
        }

        public void init(Registry registry) {
            this.consumer.accept(registry);
        }
    }
}
