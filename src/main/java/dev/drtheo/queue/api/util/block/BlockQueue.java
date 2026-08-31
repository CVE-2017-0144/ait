package dev.drtheo.queue.api.util.block;

import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import dev.drtheo.queue.api.ActionQueue;
import dev.drtheo.scheduler.api.TimeUnit;

import dev.drtheo.scheduler.api.common.TaskStage;

public abstract class BlockQueue {

    public ActionQueue schedulePlace(ServerLevel world, TimeUnit unit, int period, int maxTime, int flags) {
        return this.schedulePlace(new ActionQueue(), world, unit, period, maxTime, flags);
    }

    /**
     * @param maxTime Max time (in ms) a single cycle can perform
     */
    public ActionQueue schedulePlace(ActionQueue queue, ServerLevel world, TimeUnit unit, int period, int maxTime, int flags) {
        return queue.thenRunSteps(() -> {
            BlockData block = this.pollBlock();

            if (block == null)
                return true;

            int blockFlags = block.flags() == -1 ? flags : block.flags();

            world.setBlock(block.pos(), block.state(), blockFlags);
            return false;
        }, TaskStage.startWorldTick(world), unit, period, maxTime);
    }

    protected abstract BlockData pollBlock();

    public static class Simple extends BlockQueue {

        private final Deque<BlockData> blocks = new ArrayDeque<>();

        @Override
        protected BlockData pollBlock() {
            return this.blocks.poll();
        }

        public void set(BlockPos pos, BlockState state) {
            this.set(pos, state, -1);
        }

        public void set(BlockPos pos, BlockState state, int flags) {
            this.blocks.add(new BlockData(state, pos, flags));
        }
    }
}