package dev.amble.ait.core;

import dev.drtheo.multidim.MultiDim;
import dev.drtheo.multidim.api.VoidChunkGenerator;
import dev.drtheo.multidim.api.WorldBlueprint;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.lib.platform.lifecycle.ServerLifecycleEvents;

public class AITDimensions {
    public static final ResourceKey<Level> TIME_VORTEX_WORLD = ResourceKey.create(Registries.DIMENSION,
            AITMod.id("time_vortex"));

    public static final ResourceKey<Level> MARS = ResourceKey.create(Registries.DIMENSION,
            AITMod.id("mars"));
    public static final ResourceKey<Level> MOON = ResourceKey.create(Registries.DIMENSION,
            AITMod.id("moon"));
    public static final ResourceKey<Level> SPACE = ResourceKey.create(Registries.DIMENSION,
            AITMod.id("space"));

    public static WorldBlueprint TARDIS_WORLD_BLUEPRINT;

    public static void init() {
        Registry.register(BuiltInRegistries.CHUNK_GENERATOR, AITMod.id("void"), VoidChunkGenerator.CODEC);

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            TARDIS_WORLD_BLUEPRINT = new WorldBlueprint(AITMod.id("tardis"))
                    .setPersistent(false).shouldTickTime(false)
                    .setAutoLoad(false).withCreator(TardisServerWorld::new)
                    .withType(AITMod.id("tardis_dimension_type"))
                    .withGenerator(new VoidChunkGenerator(
                            server.registryAccess().registryOrThrow(Registries.BIOME),
                            ResourceKey.create(Registries.BIOME, AITMod.id("tardis"))
                    ));

            MultiDim.get(server).register(TARDIS_WORLD_BLUEPRINT);
        });
    }
}
