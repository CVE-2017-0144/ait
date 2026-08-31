package dev.drtheo.multidim.api;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.Executor;

public class MultiDimServerWorld extends ServerLevel {

    private final WorldBlueprint blueprint;

    public MultiDimServerWorld(WorldBlueprint blueprint, MinecraftServer server, Executor workerExecutor, LevelStorageSource.LevelStorageAccess session, ServerLevelData properties, ResourceKey<Level> worldKey, LevelStem dimensionOptions, ChunkProgressListener worldGenerationProgressListener, List<CustomSpawner> spawners, @Nullable RandomSequences randomSequencesState, boolean created) {
        super(server, workerExecutor, session, properties, worldKey, dimensionOptions, worldGenerationProgressListener, false, blueprint.seed(), spawners, blueprint.shouldTickTime(), randomSequencesState);

        this.blueprint = blueprint;
    }

    public WorldBlueprint getBlueprint() {
        return blueprint;
    }
}
