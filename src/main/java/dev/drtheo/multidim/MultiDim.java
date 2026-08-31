package dev.drtheo.multidim;

import com.mojang.serialization.Lifecycle;
import dev.drtheo.multidim.api.MultiDimServer;
import dev.drtheo.multidim.api.MultiDimServerWorld;
import dev.drtheo.multidim.api.MutableRegistry;
import dev.drtheo.multidim.api.WorldBlueprint;
import dev.drtheo.multidim.impl.SimpleWorldProgressListener;
import dev.drtheo.multidim.util.MultiDimUtil;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MultiDim {

    private static MultiDim instance;
    private static boolean initialized = false;

    private final Map<ResourceLocation, WorldBlueprint> blueprints = new HashMap<>();
    protected final MinecraftServer server;

    private final Set<ServerLevel> toDelete = new ReferenceOpenHashSet<>();
    private final Set<ServerLevel> toUnload = new ReferenceOpenHashSet<>();

    public static void init() {
        if (initialized)
            return;

        MultiDimFileManager.init();

        ServerTickEvents.START_SERVER_TICK.register(server -> MultiDim.get(server).tick());
        initialized = true;
    }

    private MultiDim(MinecraftServer server) {
        this.server = server;
    }

    private void tick() {
        Set<ServerLevel> deletionQueue = this.toDelete;

        if (!deletionQueue.isEmpty())
            deletionQueue.removeIf(this::tickDeleteWorld);

        Set<ServerLevel> unloadingQueue = this.toUnload;

        if (!unloadingQueue.isEmpty())
            unloadingQueue.removeIf(this::tickUnloadWorld);
    }

    public boolean isWorldUnloaded(ServerLevel world) {
        return world.players().isEmpty() && world.getChunkSource().getLoadedChunksCount() <= 0;
    }

    private boolean prepareForUnload(ServerLevel world) {
        if (this.isWorldUnloaded(world))
            return true;

        this.kickPlayers(world);
        return false;
    }

    public void kickPlayers(ServerLevel world) {
        if (world.players().isEmpty())
            return;

        ServerLevel overworld = this.server.overworld();
        Vec3 spawnPos = overworld.getSharedSpawnPos().getCenter();

        for (ServerPlayer player : world.players()) {
            player.teleportTo(overworld, spawnPos.x(), spawnPos.y(), spawnPos.z(), player.getYRot(), player.getXRot());
        }
    }

    private boolean tickDeleteWorld(ServerLevel world) {
        if (!this.prepareForUnload(world))
            return false;

        this.remove(world.dimension());
        return true;
    }

    private boolean tickUnloadWorld(ServerLevel world) {
        if (!this.prepareForUnload(world))
            return false;

        this.unload(world.dimension());
        return true;
    }

    public void register(WorldBlueprint blueprint) {
        this.blueprints.put(blueprint.id(), blueprint);
    }

    public static MultiDim get(MinecraftServer server) {
        MultiDim.init();

        if (instance == null || instance.server != server)
            instance = new MultiDim(server);

        return instance;
    }

    public MultiDimServerWorld add(WorldBlueprint blueprint, ResourceLocation id) {
        return addOrLoad(blueprint, id, true);
    }

    public MultiDimServerWorld load(WorldBlueprint blueprint, ResourceLocation id) {
        return addOrLoad(blueprint, id, false);
    }

    public MultiDimServerWorld addOrLoad(WorldBlueprint blueprint, ResourceLocation id, boolean created) {
        return this.addOrLoad(blueprint, ResourceKey.create(Registries.DIMENSION, id), created);
    }

    public MultiDimServerWorld add(WorldBlueprint blueprint, ResourceKey<Level> id) {
        return addOrLoad(blueprint, id, true);
    }

    public MultiDimServerWorld load(WorldBlueprint blueprint, ResourceKey<Level> id) {
        return addOrLoad(blueprint, id, false);
    }

    public MultiDimServerWorld addOrLoad(WorldBlueprint blueprint, ResourceKey<Level> id, boolean created) {
        ServerLevel existing = this.server.getLevel(id);

        if (existing != null)
            return (MultiDimServerWorld) existing;

        MutableRegistry<LevelStem> dimensionsRegistry = MultiDimUtil.getMutableDimensionsRegistry(this.server);
        boolean wasFrozen = dimensionsRegistry.multidim$isFrozen();

        if (wasFrozen)
            dimensionsRegistry.multidim$unfreeze();

        LevelStem options = blueprint.createOptions(this.server);
        ResourceKey<LevelStem> key = ResourceKey.create(Registries.LEVEL_STEM, options.type()
                .unwrapKey().map(ResourceKey::location).orElse(blueprint.id()));

        if (!dimensionsRegistry.multidim$contains(key))
            dimensionsRegistry.multidim$add(key, options, RegistrationInfo.BUILT_IN);

        if (wasFrozen)
            dimensionsRegistry.multidim$freeze();

        MultiDimServerWorld world = blueprint.createWorld(this.server, id, options, created);
        this.load(world);

        return world;
    }

    public void queueUnload(MultiDimServerWorld world) {
        this.toUnload.add(world);
    }

    public void queueUnload(ResourceKey<Level> key) {
        this.toUnload.add(this.server.getLevel(key));
    }

    private void unload(ResourceKey<Level> key) {
        ServerLevel world = ((MultiDimServer) this.server).multidim$removeWorld(key);

        if (world == null)
            return;

        world.save(new SimpleWorldProgressListener(() -> {
            ServerWorldEvents.UNLOAD.invoker().onWorldUnload(this.server, world);
            MultiDimUtil.getMutableDimensionsRegistry(this.server).multidim$remove(key.location());
        }), true, false);
    }

    public void queueRemove(MultiDimServerWorld world) {
        this.toDelete.add(world);
    }

    public void queueRemove(ResourceKey<Level> key) {
        this.toDelete.add(this.server.getLevel(key));
    }

    private void remove(ResourceKey<Level> key) {
        ServerLevel world = ((MultiDimServer) this.server).multidim$removeWorld(key);

        if (world == null)
            return;

        ServerWorldEvents.UNLOAD.invoker().onWorldUnload(this.server, world);
        MultiDimUtil.getMutableDimensionsRegistry(this.server).multidim$remove(key.location());

        LevelStorageSource.LevelStorageAccess session = ((MultiDimServer) this.server).multidim$getSession();
        File worldDirectory = session.getDimensionPath(key).toFile();

        if (!worldDirectory.exists())
            return;

        try {
            FileUtils.deleteDirectory(worldDirectory);
        } catch (IOException e) {
            MultiDimMod.LOGGER.warn("Failed to delete world directory", e);

            try {
                FileUtils.forceDeleteOnExit(worldDirectory);
            } catch (IOException ignored) { }
        }
    }

    private void load(MultiDimServerWorld world) {
        MultiDimMod.LOGGER.info("Loading world {}", world.dimension().location());

        if (((MultiDimServer) this.server).multidim$hasWorld(world.dimension())) {
            MultiDimMod.LOGGER.warn("World {} is already loaded", world.dimension().location());
            return;
        }

        ((MultiDimServer) this.server).multidim$addWorld(world);

        ServerWorldEvents.LOAD.invoker().onWorldLoad(this.server, world);
        world.tick(() -> true);
    }

    public WorldBlueprint getBlueprint(ResourceLocation id) {
        return blueprints.get(id);
    }
}
