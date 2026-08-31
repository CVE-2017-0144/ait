package dev.drtheo.queue.api.util.block;

import dev.drtheo.queue.api.ActionQueue;
import dev.drtheo.queue.api.util.Value;
import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.common.TaskStage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunkSection;

public class ChunkEraser {

    private static ActionQueue erase(TimeUnit unit, int period, int maxTime, ServerLevel world, int chunkX1, int chunkZ1, int chunkX2, int chunkZ2, int flags, boolean loadChunks) {
        BlockQueue.Simple queue = new BlockQueue.Simple();
        BlockState state = Blocks.AIR.defaultBlockState();

        int minX = Math.min(chunkX1, chunkX2);
        int maxX = Math.max(chunkX1, chunkX2);

        int minZ = Math.min(chunkZ1, chunkZ2);
        int maxZ = Math.max(chunkZ1, chunkZ2);

        Value<Integer> x = new Value<>(minX);
        ActionQueue result = new ActionQueue()
                .thenRunSteps(() -> {
                    for (int z = minZ; z < maxZ; z++) {
                        ChunkAccess chunk = world.getChunk(x.value, z, ChunkStatus.EMPTY, loadChunks);

                        if (chunk == null)
                            return false;

                        for (int y = chunk.getSectionIndex(world.getMinBuildHeight()); y < chunk.getSectionIndex(world.getMaxBuildHeight()); y++) {
                            LevelChunkSection section = chunk.getSections()[y];

                            if (!section.hasOnlyAir())
                                markBlocks(queue, state, x.value, z, chunk.getSectionYFromSectionIndex(y));
                        }
                    }

                    x.value++;
                    return x.value >= maxX;
                    }, TaskStage.startWorldTick(world), unit, period, maxTime);

        return queue.schedulePlace(result, world, unit, period, maxTime, flags);
    }

    private static void markBlocks(BlockQueue.Simple queue, BlockState state, int chunkX, int chunkZ, int chunkY) {
        int startX = SectionPos.sectionToBlockCoord(chunkX);
        int startZ = SectionPos.sectionToBlockCoord(chunkZ);
        int startY = SectionPos.sectionToBlockCoord(chunkY);

        int endX = startX + 16;
        int endZ = startZ + 16;
        int endY = startY + 16;

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                for (int z = startZ; z < endZ; z++) {
                    queue.set(new BlockPos(x, y, z), state);
                }
            }
        }
    }

    public static class Builder {

        private TimeUnit unit = TimeUnit.TICKS;
        private int period = 1;
        private int maxTime = 20;

        private int flags = Block.UPDATE_KNOWN_SHAPE;
        private boolean loadChunks = true;

        public Builder every(TimeUnit unit, int period) {
            this.unit = unit;
            this.period = period;
            return this;
        }

        public Builder withMaxTime(int maxTime) {
            this.maxTime = maxTime;
            return this;
        }

        public Builder withFlags(int flags) {
            this.flags = flags;
            return this;
        }

        public Builder loadChunks(boolean loadChunks) {
            this.loadChunks = loadChunks;
            return this;
        }

        public ActionQueue build(ServerLevel world, int x1, int z1, int x2, int z2) {
            return ChunkEraser.erase(this.unit, this.period, this.maxTime,
                    world, x1, z1, x2, z2, this.flags, this.loadChunks);
        }

        public ActionQueue build(ServerLevel world, ChunkPos from, ChunkPos to) {
            return this.build(world, from.x, from.z, to.x, to.z);
        }
    }
}