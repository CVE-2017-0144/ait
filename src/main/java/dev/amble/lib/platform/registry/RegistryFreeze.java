package dev.amble.lib.platform.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

import dev.drtheo.multidim.api.MutableRegistry;

// registries are frozen before mod ctor, static Block fields need them open for intrusive holders
public class RegistryFreeze {

    public static void withRegistriesUnfrozen(Runnable action) {
        List<MutableRegistry<?>> thawed = new ArrayList<>();

        for (Registry<?> r : BuiltInRegistries.REGISTRY) {
            if (!(r instanceof MutableRegistry<?> m) || !m.multidim$isFrozen())
                continue;

            m.multidim$unfreeze();
            thawed.add(m);
        }

        try {
            action.run();
        } finally {
            // flag only, neoforge does the real freeze after RegisterEvent
            thawed.forEach(MutableRegistry::multidim$freeze);
        }
    }
}
