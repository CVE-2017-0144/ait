package dev.amble.ait.core.world;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.events.ServerChunkEvents;
import dev.amble.lib.data.CachedDirectedGlobalPos;

@SuppressWarnings("UnstableApiUsage")
public record RiftChunkManager(ServerLevel world) {

    private static final int MIN_ARTRON_AMOUNT = 2000;
    private static final int MAX_ARTRON_AMOUNT = 4000;

    private static final AttachmentType<Double> ARTRON = AttachmentRegistry.createPersistent(
            AITMod.id("artron"), Codec.DOUBLE
    );

    private static final AttachmentType<Double> MAX_ARTRON = AttachmentRegistry.createPersistent(
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

            if (manager.getMaxArtron(pos) < manager.getArtron(pos))
                manager.addFuel(chunk.getPos(), 1);
        });
    }

    public static RiftChunkManager getInstance(ServerLevel world) {
        return new RiftChunkManager(world);
    }

    public double getArtron(ChunkPos pos) {
        if (!this.isRiftChunk(pos))
            return 0;

        ChunkAccess shouldBeProtoChunk = this.world.getChunkSource().getChunk(pos.x, pos.z, ChunkStatus.STRUCTURE_STARTS, true);

        if (!(shouldBeProtoChunk instanceof ProtoChunk protoChunk))
            return 0;

        return protoChunk.getAttachedOrCreate(ARTRON, () -> (double) world.getRandom().nextIntBetweenInclusive(MIN_ARTRON_AMOUNT, MAX_ARTRON_AMOUNT));
    }

    public double getMaxArtron(ChunkPos pos) {
        if (!this.isRiftChunk(pos))
            return 0;

        ChunkAccess shouldBeProtoChunk = this.world.getChunkSource().getChunk(pos.x, pos.z, ChunkStatus.STRUCTURE_STARTS, true);

        if (!(shouldBeProtoChunk instanceof ProtoChunk protoChunk))
            return 0;

        return protoChunk.getAttachedOrCreate(ARTRON, () -> (double) world.getRandom().nextIntBetweenInclusive(MIN_ARTRON_AMOUNT, MAX_ARTRON_AMOUNT));
    }

    public double removeFuel(ChunkPos pos, double amount) {
        if (!this.isRiftChunk(pos))
            return 0;

        double artron = this.getArtron(pos);
        artron -= artron < amount ? 0 : amount;

        ChunkAccess shouldBeProtoChunk = this.world.getChunkSource().getChunk(pos.x, pos.z, ChunkStatus.STRUCTURE_STARTS, true);
        if (shouldBeProtoChunk instanceof ProtoChunk protoChunk) {
            protoChunk.setAttached(ARTRON, artron);
        }
        return artron - amount;
    }

    public void addFuel(ChunkPos pos, double amount) {
        if (!this.isRiftChunk(pos))
            return;

        RiftChunkManager.addFuel(this.world, pos, amount);
    }

    public void setCurrentFuel(ChunkPos pos, double amount) {
        ChunkAccess shouldBeProtoChunk = this.world.getChunkSource().getChunk(pos.x, pos.z, ChunkStatus.STRUCTURE_STARTS, true);

        if (shouldBeProtoChunk instanceof ProtoChunk protoChunk) {
            protoChunk.modifyAttached(ARTRON, d -> amount);
        }
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

    private static void addFuel(ServerLevelAccessor world, ChunkPos pos, double amount) {
        ChunkAccess shouldBeProtoChunk = world.getChunkSource().getChunk(pos.x, pos.z, ChunkStatus.STRUCTURE_STARTS, true);

        if (shouldBeProtoChunk instanceof ProtoChunk protoChunk) {
            protoChunk.modifyAttached(ARTRON, d -> d + amount);
        }
    }

    public static double getFuel(ServerLevel world, ChunkPos pos) {
        if (!isRiftChunk(world, pos))
            return 0;

        ChunkAccess shouldBeProtoChunk = world.getChunkSource().getChunk(pos.x, pos.z, ChunkStatus.STRUCTURE_STARTS, true);

        if (!(shouldBeProtoChunk instanceof ProtoChunk protoChunk))
            return 0;

        return protoChunk.getAttachedOrCreate(ARTRON, () -> (double) world.getRandom().nextIntBetweenInclusive(MIN_ARTRON_AMOUNT, MAX_ARTRON_AMOUNT));
    }

    public static double getMaxFuel(ServerLevel world, ChunkPos pos) {
        if (!isRiftChunk(world, pos))
            return 0;

        ChunkAccess shouldBeProtoChunk = world.getChunkSource().getChunk(pos.x, pos.z, ChunkStatus.STRUCTURE_STARTS, true);

        if (!(shouldBeProtoChunk instanceof ProtoChunk protoChunk))
            return 0;

        return protoChunk.getAttachedOrCreate(MAX_ARTRON, () -> (double) world.getRandom().nextIntBetweenInclusive(MIN_ARTRON_AMOUNT, MAX_ARTRON_AMOUNT));
    }
}
