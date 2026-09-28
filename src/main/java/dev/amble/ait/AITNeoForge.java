package dev.amble.ait;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.config.AITModMenu;
import dev.amble.ait.datagen.AITModDataGenerator;
import dev.amble.lib.platform.datagen.PlatformDataGenerator;
import dev.amble.ait.compat.Compat;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.compat.permissionapi.PermissionAPICompat;
import dev.amble.lib.AmbleKit;
import dev.amble.lib.client.AmbleKitClient;
import dev.amble.lib.platform.Platform;
import dev.amble.lib.platform.registry.RegistryFreeze;
import dev.amble.lib.platform.worldgen.BiomeModifications;
import dev.amble.plushies.Plushies;
import dev.amble.plushies.client.PlushiesClient;
import dev.drtheo.multidim.MultiDimMod;
import dev.drtheo.scheduler.SchedulerMod;
import dev.drtheo.scheduler.client.SchedulerClientMod;
import dev.loqor.portal.BiggerOnTheInside;

@Mod(AITMod.MOD_ID)
public final class AITNeoForge {

    public AITNeoForge(IEventBus modBus, ModContainer container) {
        // first, the shims read it
        Platform.setModBus(modBus);
        BiomeModifications.init();
        AitNetworking.init();

        // libs before the mod
        RegistryFreeze.withRegistriesUnfrozen(() -> {
            new SchedulerMod().onInitialize();
            new MultiDimMod().onInitialize();
            new AmbleKit().onInitialize();
            new Plushies().onInitialize();
            new AITMod().onInitialize();
            new PermissionAPICompat().onInitialize();
            new BiggerOnTheInside().onInitialize();
        });

        // datagen registers tabs after the freeze
        modBus.addListener(GatherDataEvent.class, event -> RegistryFreeze.withRegistriesUnfrozen(
                () -> new AITModDataGenerator()
                        .onInitializeDataGenerator(new PlatformDataGenerator(event, AITMod.MOD_ID))));

        if (Platform.isClient())
            RegistryFreeze.withRegistriesUnfrozen(() -> Client.init(container));
    }

    // keeps client classes off the server
    private static class Client {

        static void init(ModContainer container) {
            AITModMenu.register(container);

            new SchedulerClientMod().onInitializeClient();
            new AmbleKitClient().onInitializeClient();
            new PlushiesClient().onInitializeClient();
            new AITModClient().onInitializeClient();
            new Compat().onInitializeClient();
        }
    }
}
