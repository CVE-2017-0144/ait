package dev.amble.ait.core.world;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.BooleanSupplier;

import dev.drtheo.multidim.MultiDim;
import dev.drtheo.multidim.MultiDimMod;
import dev.drtheo.multidim.api.MultiDimServerWorld;
import dev.drtheo.multidim.api.WorldBlueprint;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.portal.PortalPairs;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.lib.util.ServerLifecycleHooks;

public class TardisServerWorld extends MultiDimServerWorld {

    public static final String NAMESPACE = AITMod.MOD_ID + "-tardis";

    private ServerTardis tardis;
    private Holder<Biome> cachedBiome;

    public TardisServerWorld(WorldBlueprint blueprint, MinecraftServer server, Executor workerExecutor, LevelStorageSource.LevelStorageAccess session, ServerLevelData properties, ResourceKey<Level> worldKey, LevelStem dimensionOptions, ChunkProgressListener worldGenerationProgressListener, List<CustomSpawner> spawners, @Nullable RandomSequences randomSequencesState, boolean created) {
        super(blueprint, server, workerExecutor, session, properties, worldKey, dimensionOptions, worldGenerationProgressListener, spawners, randomSequencesState, created);
        this.setSpawnSettings(false, false);
    }

    @Override
    public void tick(BooleanSupplier shouldKeepTicking) {
        if (this.shouldTick()) {
            super.tick(shouldKeepTicking);
        }
    }

    public boolean shouldTick() {
        return this.tardis != null && (
                !MultiDim.get(this.getServer()).isWorldUnloaded(this)
                || this.tardis.interiorChanging().queued().get()
                || this.tardis.getDesktop().isChanging()
                || PortalPairs.isOpen(this.tardis)
        );
    }

    @Override
    public String toString() {
        return "Tardis" + super.toString();
    }

    @Override
    public Holder<Biome> getBiome(BlockPos pos) {
        if (this.cachedBiome != null)
            return cachedBiome;

        this.cachedBiome = super.getBiome(pos);
        return cachedBiome;
    }

    public void setTardis(ServerTardis tardis) {
        this.tardis = tardis;
    }

    public ServerTardis getTardis() {
        return tardis;
    }

    public static TardisServerWorld create(ServerTardis tardis) {
        MinecraftServer server = ServerLifecycleHooks.get();
        if (Thread.currentThread() != server.getRunningThread()) {
            AITMod.LOGGER.error("Tried creating a TARDIS world when not on the server thread", new Throwable());
            CompletableFuture<TardisServerWorld> future = new CompletableFuture<>();
            server.execute(() -> {
                try {
                    future.complete(create(tardis));
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            });

            try {
                return future.get(5, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                throw new RuntimeException(e.getCause() != null ? e.getCause() : e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException("Timed out waiting for server thread to create TARDIS world", e);
            }
        }

        AITMod.LOGGER.info("Creating a dimension for TARDIS {}", tardis.getUuid());
        TardisServerWorld created = (TardisServerWorld) MultiDim.get(ServerLifecycleHooks.get())
                .add(AITDimensions.TARDIS_WORLD_BLUEPRINT, idForTardis(tardis));

        created.setTardis(tardis);
        return created;
    }

    public static TardisServerWorld getOrLoad(ServerTardis tardis) {
        MinecraftServer server = ServerLifecycleHooks.get();
        ResourceKey<Level> key = keyForTardis(tardis);

        TardisServerWorld result = (TardisServerWorld) server.getLevel(key);

        if (result != null) {
            result.setTardis(tardis);
            return result;
        }

        return load(server, tardis);
    }

    public static TardisServerWorld load(MinecraftServer server, ServerTardis tardis) {
        if (Thread.currentThread() != server.getRunningThread()) {
            AITMod.LOGGER.error("Tried loading a TARDIS world when not on the server thread", new Throwable());
            CompletableFuture<TardisServerWorld> future = new CompletableFuture<>();
            server.execute(() -> {
                try {
                    future.complete(load(server, tardis));
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            });

            try {
                return future.get(5, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                throw new RuntimeException(e.getCause() != null ? e.getCause() : e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException("Timed out waiting for server thread to load TARDIS world", e);
            }
        }

        MultiDim multidim = MultiDim.get(server);

        ResourceKey<Level> key = keyForTardis(tardis);
        TardisServerWorld result = (TardisServerWorld) multidim.load(AITDimensions.TARDIS_WORLD_BLUEPRINT, key);

        if (result == null) {
            MultiDimMod.LOGGER.info("Failed to load the sub-world, creating a new one instead");
            result = create(tardis);
        } else {
            result.setTardis(tardis);
        }

        return result;
    }

    public static ResourceKey<Level> keyForTardis(ServerTardis tardis) {
        return ResourceKey.create(Registries.DIMENSION, idForTardis(tardis));
    }

    private static ResourceLocation idForTardis(ServerTardis tardis) {
        return ResourceLocation.fromNamespaceAndPath(NAMESPACE, tardis.getUuid().toString());
    }

    public static boolean isTardisDimension(ResourceKey<Level> key) {
        return NAMESPACE.equals(key.location().getNamespace());
    }

    public static boolean isTardisDimension(Level world) {
        return world.isClientSide() ? isTardisDimension((ClientLevel) world) : isTardisDimension((ServerLevel) world);
    }

    public static boolean isTardisDimension(ServerLevel world) {
        return world instanceof TardisServerWorld;
    }

    @Nullable public static UUID getTardisId(@Nullable Level world) {
        if (world == null || !isTardisDimension(world))
            return null;

        return getTardisId(world.dimension());
    }

    public static UUID getTardisId(ResourceKey<Level> key) {
        return UUID.fromString(key.location().getPath());
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isTardisDimension(ClientLevel world) {
        return isTardisDimension(world.dimension());
    }
}
