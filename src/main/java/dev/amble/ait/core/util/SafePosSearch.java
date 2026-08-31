package dev.amble.ait.core.util;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import dev.drtheo.queue.api.ActionQueue;
import dev.drtheo.queue.api.util.Value;
import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.common.TaskStage;
import org.jetbrains.annotations.Nullable;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class SafePosSearch {

    private static final int SAFE_RADIUS = 3;

    public static void wrapSafe(CachedDirectedGlobalPos globalPos, Kind vSearch,
                                boolean hSearch, Consumer<CachedDirectedGlobalPos> posConsumer) {
        Value<BlockPos> ref = new Value<>(null);
        ActionQueue queue = findSafe(globalPos, vSearch, hSearch, ref);

        if (queue != null) {
            queue.thenRun(() -> {
                CachedDirectedGlobalPos resultPos = globalPos;

                if (ref.value != null)
                    resultPos = resultPos.pos(ref.value);

                posConsumer.accept(resultPos);
            }).execute();
        } else {
            posConsumer.accept(globalPos);
        }
    }

    /**
     * @return {@literal null} when the position is already safe, {@link ActionQueue} otherwise.
     */
    @Nullable public static ActionQueue findSafe(CachedDirectedGlobalPos globalPos,
                                       Kind vSearch, boolean hSearch, Value<BlockPos> ref) {
        ServerLevel world = globalPos.getWorld();
        BlockPos pos = globalPos.getPos();

        final ChunkAccess chunk = globalPos.getWorld().getChunk(pos);

        if (isSafe(chunk, pos))
            return null;

        ActionQueue queue = new ActionQueue();

        if (hSearch) {
            queue = findSafeXZ(queue, ref, world, pos, SAFE_RADIUS).thenRun(() -> {
                if (ref.value != null)
                    globalPos.pos(ref.value);
            });
        }

        return switch (vSearch) {
            case CEILING -> findSafeCeiling(queue, ref, world, pos);
            case FLOOR -> findSafeFloor(queue, ref, world, pos);
            case MEDIAN -> findSafeMedian(queue, ref, world, pos);
            case NONE -> queue;
        };
    }

    private static ActionQueue findSafeCeiling(ActionQueue queue, Value<BlockPos> result, ServerLevel world, BlockPos original) {
        return queue.thenRun(() -> {
            if (result.value != null)
                return;

            int y = world.getChunk(original).getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    original.getX() & 15, original.getZ() & 15) + 1;

            result.value = original.atY(y);
        });
    }

    private static ActionQueue findSafeFloor(ActionQueue queue, Value<BlockPos> result, ServerLevel world, BlockPos original) {
        final SafeFloorHolder holder = new SafeFloorHolder(world, original);

        return queue.thenRunSteps(() -> {
            if (result.value != null)
                return true;

            Iter state = holder.checkAndAdvance();

            if (state == Iter.SUCCESS)
                result.value = holder.cursor;

            return state != Iter.CONTINUE;
        }, TaskStage.startWorldTick(world), TimeUnit.TICKS, 1, 3);
    }

    private static ActionQueue findSafeMedian(ActionQueue queue, Value<BlockPos> result, ServerLevel world, BlockPos original) {
        final SafeMedianHolder holder = new SafeMedianHolder(world, original);

        return queue.thenRunSteps(() -> {
            if (result.value != null)
                return true;

            DoubleIter state = holder.checkAndAdvance();

            if (state == DoubleIter.SUCCESS_A) {
                result.value = holder.upCursor;
            } else if (state == DoubleIter.SUCCESS_B) {
                result.value = holder.downCursor;
            }

            return state != DoubleIter.CONTINUE;
        }, TaskStage.startWorldTick(world), TimeUnit.TICKS, 1, 3);
    }

    private static ActionQueue findSafeXZ(ActionQueue queue, Value<BlockPos> result, ServerLevel world, BlockPos original, int radius) {
        BlockPos.MutableBlockPos pos = original.mutable();

        int minX = pos.getX() - radius;
        int maxX = pos.getX() + radius;

        int minZ = pos.getZ() - radius;
        int maxZ = pos.getZ() + radius;

        final SafeXZHolder holder = new SafeXZHolder(world, pos, maxX, maxZ, minX, minZ);

        return queue.thenRunSteps(() -> {
            Iter state = holder.checkAndAdvance();

            if (state == Iter.SUCCESS)
                result.value = holder.pos.immutable();

            return state != Iter.CONTINUE;
        }, TaskStage.startWorldTick(world), TimeUnit.TICKS, 1, 3); // every tick, while the taken time is less than 3ms (1tick = 50ms, 2/50 of a tick, which is 4%)
    }

    @SuppressWarnings("deprecation")
    private static boolean isSafe(ChunkAccess chunk, BlockPos pos) {
        BlockState floor = chunk.getBlockState(pos.below());

        if (!floor.blocksMotion())
            return false;

        BlockState curUp = chunk.getBlockState(pos);
        BlockState aboveUp = chunk.getBlockState(pos.above());

        return !curUp.blocksMotion() && !aboveUp.blocksMotion();
    }

    @SuppressWarnings("deprecation")
    private static boolean isSafe(BlockState floor, BlockState block1, BlockState block2) {
        return floor.blocksMotion() && !block1.blocksMotion() && !block2.blocksMotion();
    }

    static class SafeXZHolder {
        int x;
        int z;
        ChunkAccess prevChunk;
        final Level world;
        final BlockPos.MutableBlockPos pos;
        final int maxX;
        final int maxZ;
        final int minX;

        public SafeXZHolder(Level world, BlockPos.MutableBlockPos pos, int maxX, int maxZ, int minX, int minZ) {
            this.world = world;
            this.pos = pos;
            this.maxX = maxX;
            this.maxZ = maxZ;
            this.minX = minX;
            this.x = minX;
            this.z = minZ;
        }

        public Iter checkAndAdvance() {
            if (z >= maxZ)
                return Iter.FAIL;

            if (x >= maxX) {
                x = minX;
                z += 1;

                return Iter.CONTINUE;
            }

            pos.setX(x).setZ(z);

            ChunkPos tempPos = new ChunkPos(pos);
            if (prevChunk == null || !prevChunk.getPos().equals(tempPos))
                prevChunk = world.getChunk(tempPos.x, tempPos.z);

            if (isSafe(prevChunk, pos))
                return Iter.SUCCESS;

            x += 1;
            return Iter.CONTINUE;
        }

        public BlockPos pos() {
            return pos.immutable();
        }
    }

    static class SafeFloorHolder {
        BlockPos cursor;
        BlockState floor;
        BlockState current;
        BlockState above;

        final ChunkAccess chunk;
        final int maxY;

        public SafeFloorHolder(Level world, BlockPos pos) {
            this.chunk = world.getChunk(pos);
            this.maxY = chunk.getMaxBuildHeight();

            int minY = chunk.getMinBuildHeight();
            this.cursor = pos.atY(minY + 2);

            this.floor = chunk.getBlockState(cursor.below());
            this.current = chunk.getBlockState(cursor);
            this.above = chunk.getBlockState(cursor.above());
        }

        public Iter checkAndAdvance() {
            if (cursor.getY() >= maxY)
                return Iter.FAIL;

            if (isSafe(floor, current, above))
                return Iter.SUCCESS;

            cursor = cursor.above();

            floor = current;
            current = above;
            above = chunk.getBlockState(cursor);

            return Iter.CONTINUE;
        }
    }

    static class SafeMedianHolder {

        BlockPos upCursor;
        BlockState floorUp;
        BlockState curUp;
        BlockState aboveUp;

        BlockPos downCursor;
        BlockState floorDown;
        BlockState curDown;
        BlockState aboveDown;

        final ChunkAccess chunk;

        public SafeMedianHolder(Level world, BlockPos pos) {
            this.chunk = world.getChunk(pos);

            this.upCursor = pos.above();
            this.floorUp = chunk.getBlockState(upCursor.below());
            this.curUp = chunk.getBlockState(upCursor);
            this.aboveUp = chunk.getBlockState(upCursor.above());

            this.downCursor = pos.below();
            this.floorDown = chunk.getBlockState(downCursor.below());
            this.curDown = chunk.getBlockState(downCursor);
            this.aboveDown = chunk.getBlockState(downCursor.above());
        }

        public DoubleIter checkAndAdvance() {
            boolean canGoUp = upCursor.getY() < chunk.getMaxBuildHeight();
            boolean canGoDown = downCursor.getY() > chunk.getMinBuildHeight();

            if (!canGoUp && !canGoDown)
                return DoubleIter.FAIL;

            if (canGoUp) {
                if (isSafe(floorUp, curUp, aboveUp)) {
                    upCursor = upCursor.below();
                    return DoubleIter.SUCCESS_A;
                }

                upCursor = upCursor.above();

                floorUp = curUp;
                curUp = aboveUp;
                aboveUp = chunk.getBlockState(upCursor);
            }

            if (canGoDown) {
                if (isSafe(floorDown, curDown, aboveDown)) {
                    downCursor = downCursor.above();
                    return DoubleIter.SUCCESS_B;
                }

                downCursor = downCursor.below();

                curDown = aboveDown;
                aboveDown = floorDown;
                floorDown = chunk.getBlockState(downCursor);
            }

            return DoubleIter.CONTINUE;
        }
    }

    enum Iter {
        SUCCESS,
        FAIL,
        CONTINUE
    }

    enum DoubleIter {
        SUCCESS_A,
        SUCCESS_B,
        FAIL,
        CONTINUE
    }

    public enum Kind implements StringRepresentable {
        NONE {
            @Override
            public Kind next() {
                return FLOOR;
            }
        },
        FLOOR {
            @Override
            public Kind next() {
                return CEILING;
            }
        },
        CEILING {
            @Override
            public Kind next() {
                return MEDIAN;
            }
        },
        MEDIAN {
            @Override
            public Kind next() {
                return NONE;
            }
        };

        @Override
        public String getSerializedName() {
            return toString();
        }

        public MutableComponent text() {
            return Component.translatable("message.ait.control.ylandtype." + this.getSerializedName().toLowerCase());
        }

        public abstract Kind next();
    }
}
