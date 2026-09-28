package dev.amble.ait.core.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.events.ServerChunkEvents;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.platform.registry.Attachments;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public record RiftChunkManager(ServerLevel world) {

    private static final int MIN_ARTRON_AMOUNT = 2000;
    private static final int MAX_ARTRON_AMOUNT = 4000;

    private static final Attachments.Type<Double> ARTRON = Attachments.createPersistent(
            AITMod.id("artron"), Codec.DOUBLE
    );

    private static final Attachments.Type<Double> MAX_ARTRON = Attachments.createPersistent(
            AITMod.id("max_artron"), Codec.DOUBLE
    );

    public static void init() {
        ServerChunkEvents.TICK.register((world, chunk) -> {
            if (world.getServer().getTickCount() % 20 != 0)
                return;

            RiftChunkManager manager = RiftChunkManager.getInstance(world);
            ChunkPos pos = chunk.getPos();

            if (!manager.isRiftChunk(pos))
                return;

            if (manager.getArtron(pos) < manager.getMaxArtron(pos))
                manager.addFuel(pos, 1);
        });
    }

    public static RiftChunkManager getInstance(ServerLevel world) {
        return new RiftChunkManager(world);
    }

    private @Nullable ChunkAccess chunk(ChunkPos pos) {
        return this.world.getChunkSource().getChunk(pos.x, pos.z, ChunkStatus.STRUCTURE_STARTS, true);
    }

    private static double maxArtron(LevelAccessor world, ChunkAccess chunk) {
        return Attachments.getOrCreate(chunk, MAX_ARTRON,
                () -> (double) world.getRandom().nextIntBetweenInclusive(MIN_ARTRON_AMOUNT, MAX_ARTRON_AMOUNT));
    }

    private static double artron(LevelAccessor world, ChunkAccess chunk) {
        return Attachments.getOrCreate(chunk, ARTRON, () -> maxArtron(world, chunk));
    }

    public double getArtron(ChunkPos pos) {
        if (!this.isRiftChunk(pos))
            return 0;

        ChunkAccess chunk = this.chunk(pos);
        return chunk == null ? 0 : artron(this.world, chunk);
    }

    public double getMaxArtron(ChunkPos pos) {
        if (!this.isRiftChunk(pos))
            return 0;

        ChunkAccess chunk = this.chunk(pos);
        return chunk == null ? 0 : maxArtron(this.world, chunk);
    }

    public double removeFuel(ChunkPos pos, double amount) {
        if (!this.isRiftChunk(pos))
            return 0;

        ChunkAccess chunk = this.chunk(pos);

        if (chunk == null)
            return 0;

        double artron = artron(this.world, chunk);
        artron -= artron < amount ? 0 : amount;

        Attachments.set(chunk, ARTRON, artron);
        return artron;
    }

    public void addFuel(ChunkPos pos, double amount) {
        if (!this.isRiftChunk(pos))
            return;

        ChunkAccess chunk = this.chunk(pos);

        if (chunk == null)
            return;

        Attachments.set(chunk, ARTRON,
                Math.min(artron(this.world, chunk) + amount, maxArtron(this.world, chunk)));
    }

    public void setCurrentFuel(ChunkPos pos, double amount) {
        ChunkAccess chunk = this.chunk(pos);

        if (chunk == null)
            return;

        Attachments.set(chunk, ARTRON, amount);
    }

    public boolean isRiftChunk(ChunkPos chunkPos) {
        return RiftChunkManager.isRiftChunk(this.world, chunkPos);
    }

    public boolean isRiftChunk(BlockPos pos) {
        return RiftChunkManager.isRiftChunk(world, pos);
    }

    public static boolean isRiftChunk(CachedDirectedGlobalPos cached) {
        return isRiftChunk(cached.getWorld(), cached.getPos());
    }

    public static boolean isRiftChunk(WorldGenLevel world, BlockPos pos) {
        return isRiftChunk(world, new ChunkPos(pos));
    }

    public static boolean isRiftChunk(WorldGenLevel world, ChunkPos pos) {
        if (world == null) return false;
        return WorldgenRandom.seedSlimeChunk(pos.x, pos.z,
                world.getSeed(), 987234910L
        ).nextInt(8) == 0;
    }

    public static double getFuel(ServerLevel world, ChunkPos pos) {
        return getInstance(world).getArtron(pos);
    }

    public static double getMaxFuel(ServerLevel world, ChunkPos pos) {
        return getInstance(world).getMaxArtron(pos);
    }
}
