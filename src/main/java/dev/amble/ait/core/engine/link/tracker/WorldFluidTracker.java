package dev.amble.ait.core.engine.link.tracker;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import dev.amble.ait.core.engine.link.IFluidLink;
import org.jetbrains.annotations.Nullable;

public class WorldFluidTracker {
    public static HashMap<Direction, IFluidLink> getConnections(ServerLevel world, BlockPos pos, @Nullable Direction ignore) {
        // get all fluid links around the given position
        HashMap<Direction, IFluidLink> connections = new HashMap<>();

        for (Direction dir : Direction.values()) {
            if (dir == ignore) continue;

            IFluidLink found = query(world, pos.relative(dir));
            if (found == null) continue;

            connections.put(dir, found);
        }

        return connections;
    }
    public static LinkedList<IFluidLink> getAllConnections(ServerLevel world, BlockPos pos, @Nullable Direction ignore, HashSet<BlockPos> checkedPositions) {
        LinkedList<IFluidLink> list = new LinkedList<>();
        HashMap<Direction, IFluidLink> connections;

        IFluidLink here = query(world, pos);
        if (here == null) {
            return list;
        }

        if (checkedPositions == null) checkedPositions = new HashSet<>();
        checkedPositions.add(pos);

        LinkedList<BlockPos> toCheck = new LinkedList<>();
        toCheck.add(pos);

        while (!toCheck.isEmpty()) {
            BlockPos currentPos = toCheck.poll();
            connections = getConnections(world, currentPos, ignore);

            for (Direction direction : connections.keySet()) {
                if (direction == ignore) continue;

                BlockPos newPos = currentPos.relative(direction);
                if (checkedPositions.contains(newPos)) continue;
                if (checkedPositions.add(newPos)) {
                    toCheck.add(newPos);
                    list.add(connections.get(direction));
                }
            }
        }

        return list;
    }
    public static IFluidLink query(ServerLevel world, BlockPos pos) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof IFluidLink link && !(be.isRemoved())) {
            return link;
        }

        return null;
    }

    /**
     * Breadth-first traversal of every {@link IFluidLink} reachable from {@code start}.
     * Insertion order is BFS order, so iterating the result yields nodes by distance from {@code start}.
     * Stops cleanly at {@code maxNodes} to bound worst-case cost on pathological networks.
     *
     * The returned map is mutable and owned by the caller. Keys are stored as immutable {@link BlockPos}.
     */
    public static LinkedHashMap<BlockPos, IFluidLink> bfs(ServerLevel world, BlockPos start, int maxNodes) {
        LinkedHashMap<BlockPos, IFluidLink> visited = new LinkedHashMap<>();
        BlockPos rootPos = start.immutable();
        IFluidLink first = query(world, rootPos);
        if (first == null) return visited;

        Deque<BlockPos> queue = new ArrayDeque<>();
        queue.add(rootPos);
        visited.put(rootPos, first);

        while (!queue.isEmpty() && visited.size() < maxNodes) {
            BlockPos cur = queue.poll();
            for (Direction dir : Direction.values()) {
                BlockPos next = cur.relative(dir).immutable();
                if (visited.containsKey(next)) continue;
                IFluidLink link = query(world, next);
                if (link == null) continue;
                visited.put(next, link);
                queue.add(next);
                if (visited.size() >= maxNodes) break;
            }
        }

        return visited;
    }
}
