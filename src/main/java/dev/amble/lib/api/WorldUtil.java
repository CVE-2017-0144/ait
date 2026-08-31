package dev.amble.lib.api;

import dev.amble.lib.data.DirectedGlobalPos;
import dev.amble.lib.util.ServerLifecycleHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class WorldUtil {
    private static final int SAFE_RADIUS = 3;

    public static DirectedGlobalPos locateSafe(DirectedGlobalPos cached,
                                               GroundSearch vSearch, boolean hSearch) {
        ServerLevel world = ServerLifecycleHooks.get().getLevel(cached.getDimension());
        BlockPos pos = cached.getPos();

        if (isSafe(world, pos))
            return cached;

        if (hSearch) {
            BlockPos temp = findSafeXZ(world, pos, SAFE_RADIUS);

            if (temp != null)
                return cached.pos(temp);
        }

        int x = pos.getX();
        int z = pos.getZ();

        int y = switch (vSearch) {
            case CEILING -> findSafeTopY(world, pos);
            case FLOOR -> findSafeBottomY(world, pos);
            case MEDIAN -> findSafeMedianY(world, pos);
            case NONE -> pos.getY();
        };

        return cached.pos(x, y, z);
    }

    private static BlockPos findSafeXZ(ServerLevel world, BlockPos original, int radius) {
        BlockPos.MutableBlockPos pos = original.mutable();

        int minX = pos.getX() - radius;
        int maxX = pos.getX() + radius;

        int minZ = pos.getZ() - radius;
        int maxZ = pos.getZ() + radius;

        for (int x = minX; x < maxX; x++) {
            for (int z = minZ; z < maxZ; z++) {
                pos.setX(x).setZ(z);

                if (isSafe(world, pos))
                    return pos;
            }
        }

        return null;
    }

    private static int findSafeMedianY(ServerLevel world, BlockPos pos) {
        BlockPos upCursor = pos;
        BlockState floorUp = world.getBlockState(upCursor.below());
        BlockState curUp = world.getBlockState(upCursor);
        BlockState aboveUp = world.getBlockState(upCursor.above());

        BlockPos downCursor = pos;
        BlockState floorDown = world.getBlockState(downCursor.below());
        BlockState curDown = world.getBlockState(downCursor);
        BlockState aboveDown = world.getBlockState(downCursor.above());

        while (true) {
            boolean canGoUp = upCursor.getY() < world.getMaxBuildHeight();
            boolean canGoDown = downCursor.getY() > world.getMinBuildHeight();

            if (!canGoUp && !canGoDown)
                return pos.getY();

            if (canGoUp) {
                if (isSafe(floorUp, curUp, aboveUp))
                    return upCursor.getY() - 1;

                upCursor = upCursor.above();

                floorUp = curUp;
                curUp = aboveUp;
                aboveUp = world.getBlockState(upCursor);
            }

            if (canGoDown) {
                if (isSafe(floorDown, curDown, aboveDown))
                    return downCursor.getY() + 1;

                downCursor = downCursor.below();

                curDown = aboveDown;
                aboveDown = floorDown;
                floorDown = world.getBlockState(downCursor);
            }
        }
    }

    private static int findSafeBottomY(ServerLevel world, BlockPos pos) {
        BlockPos cursor = pos.atY(world.getMinBuildHeight() + 2);

        BlockState floor = world.getBlockState(cursor.below());
        BlockState current = world.getBlockState(cursor);
        BlockState above = world.getBlockState(cursor.above());

        while (true) {
            if (cursor.getY() > world.getMaxBuildHeight())
                return pos.getY();

            if (isSafe(floor, current, above))
                return cursor.getY() - 1;

            cursor = cursor.above();

            floor = current;
            current = above;
            above = world.getBlockState(cursor);
        }
    }

    private static int findSafeTopY(ServerLevel world, BlockPos pos) {
        int x = pos.getX();
        int z = pos.getZ();

        return world.getChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z))
                .getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x & 15, z & 15) + 1;
    }

    private static boolean isSafe(BlockState floor, BlockState block1, BlockState block2) {
        return isFloor(floor) && !block1.blocksMotion() && !block2.blocksMotion();
    }

    private static boolean isSafe(BlockState block1, BlockState block2) {
        return !block1.blocksMotion() && !block2.blocksMotion();
    }

    private static boolean isFloor(BlockState floor) {
        return floor.blocksMotion();
    }

    private static boolean isSafe(Level world, BlockPos pos) {
        BlockState floor = world.getBlockState(pos.below());

        if (!isFloor(floor))
            return false;

        BlockState curUp = world.getBlockState(pos);
        BlockState aboveUp = world.getBlockState(pos.above());

        return isSafe(curUp, aboveUp);
    }

    public enum GroundSearch implements StringRepresentable {
        NONE {
            @Override
            public GroundSearch next() {
                return FLOOR;
            }
        },
        FLOOR {
            @Override
            public GroundSearch next() {
                return CEILING;
            }
        },
        CEILING {
            @Override
            public GroundSearch next() {
                return MEDIAN;
            }
        },
        MEDIAN {
            @Override
            public GroundSearch next() {
                return NONE;
            }
        };

        @Override
        public String getSerializedName() {
            return toString();
        }

        public abstract GroundSearch next();
    }

    public static Component worldText(ResourceKey<Level> key) {
        return Component.translatableWithFallback(key.location().toLanguageKey("dimension"), fakeTranslate(key));
    }

    private static String fakeTranslate(ResourceKey<Level> id) {
        return fakeTranslate(id.location());
    }

    private static String fakeTranslate(ResourceLocation id) {
        return fakeTranslate(id.getPath());
    }

    private static String fakeTranslate(String path) {
        // Split the string into words
        String[] words = path.split("_");

        // Capitalize the first letter of each word
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
        }

        // Join the words back together with spaces
        return String.join(" ", words);
    }
}