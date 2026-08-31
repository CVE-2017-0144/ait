package dev.drtheo.multidim.api;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Lifecycle;
import dev.drtheo.multidim.impl.AbstractWorldGenListener;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.Executor;

public class WorldBlueprint {

    private final ResourceLocation id;

    private long seed;
    private boolean tickTime = true;

    private ResourceLocation typeId;
    private DimensionType type;

    private WorldCreator creator = MultiDimServerWorld::new;
    private ChunkGenerator generator;

    private boolean autoLoad = true;
    private boolean persistent = true;
    private LevelStem options;

    public WorldBlueprint(ResourceLocation id) {
        this.id = id;
    }

    public WorldBlueprint withSeed(long seed) {
        this.seed = BiomeManager.obfuscateSeed(seed);
        return this;
    }

    public long seed() {
        return this.seed;
    }

    public WorldBlueprint withCreator(WorldCreator creator) {
        this.creator = creator;
        return this;
    }

    public WorldBlueprint withType(ResourceLocation id) {
        this.typeId = id;
        return this;
    }

    public WorldBlueprint withType(DimensionType type) {
        return this.withType(null, type);
    }

    public WorldBlueprint withType(ResourceLocation id, DimensionType type) {
        this.typeId = id;
        this.type = type;
        return this;
    }

    public WorldBlueprint withGenerator(ChunkGenerator generator) {
        this.generator = generator;
        return this;
    }

    public WorldBlueprint shouldTickTime(boolean tickTime) {
        this.tickTime = tickTime;
        return this;
    }

    public boolean shouldTickTime() {
        return this.tickTime;
    }

    public ResourceLocation id() {
        return this.id;
    }

    public WorldBlueprint setPersistent(boolean persistent) {
        this.persistent = persistent;
        return this;
    }

    public boolean persistent() {
        return this.persistent;
    }

    public WorldBlueprint setAutoLoad(boolean autoLoad) {
        this.autoLoad = autoLoad;
        return this;
    }

    public boolean autoLoad() {
        return autoLoad;
    }

    public MultiDimServerWorld createWorld(MinecraftServer server, ResourceKey<Level> key, LevelStem options, boolean created) {
        WorldData saveProps = server.getWorldData();

        return this.creator.create(
                this, server, Util.backgroundExecutor(), ((MultiDimServer) server).multidim$getSession(),
                new DerivedLevelData(saveProps, saveProps.overworldData()), key, options,
                new AbstractWorldGenListener(), ImmutableList.of(), null, created
        );
    }

    private Holder<DimensionType> resolveType(MinecraftServer server) {
        MappedRegistry<DimensionType> typeRegistry = (MappedRegistry<DimensionType>) server.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE);

        if (this.typeId == null)
            this.typeId = this.id;

        ResourceKey<DimensionType> typeKey = ResourceKey.create(Registries.DIMENSION_TYPE, this.typeId);

        if (this.type == null) {
            Holder<DimensionType> entry = typeRegistry.getHolder(typeKey).orElse(null);

            if (entry == null)
                return null;

            this.type = entry.value();
            return entry;
        }

        if (!typeRegistry.containsKey(typeKey))
            return typeRegistry.register(typeKey, this.type, Lifecycle.stable());

        return typeRegistry.getHolder(typeKey).orElse(null);
    }

    public LevelStem createOptions(MinecraftServer server) {
        if (this.options != null) return this.options;

        Holder<DimensionType> typeEntry = this.resolveType(server);

        if (typeEntry == null)
            throw new IllegalArgumentException("Dimension type is required to create dimension options!");

        return new LevelStem(typeEntry, this.generator);
    }

    public interface WorldCreator {
        MultiDimServerWorld create(WorldBlueprint blueprint, MinecraftServer server, Executor workerExecutor, LevelStorageSource.LevelStorageAccess session, ServerLevelData properties, ResourceKey<Level> worldKey, LevelStem dimensionOptions, ChunkProgressListener worldGenerationProgressListener, List<CustomSpawner> spawners, @Nullable RandomSequences randomSequencesState, boolean created);
    }
}
