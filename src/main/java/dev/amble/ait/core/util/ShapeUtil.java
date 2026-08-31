package dev.amble.ait.core.util;

import dev.amble.ait.data.ShapeMap;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShapeUtil {

    public static VoxelShape rect(double x, double y, double z, double width, double height, double length) {
        return Block.box(x, y, z, x + width, y + height, z + length);
    }

    public static VoxelShape rotate(Direction from, Direction to, VoxelShape shape) {
        if (from == to)
            return shape;

        var ref = new Object() {
            VoxelShape buffer = Shapes.empty();
        };

        int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;

        for (int i = 0; i < times; i++) {
            shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> ref.buffer = Shapes.joinUnoptimized(ref.buffer,
                    Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX), BooleanOp.OR));

            shape = ref.buffer;
            ref.buffer = Shapes.empty();
        }

        return shape;
    }

    public static ShapeMap.Builder rotations(Direction from, VoxelShape shape) {
        ShapeMap.Builder builder = new ShapeMap.Builder();

        for (Direction direction : Direction.values()) {
            builder.add(direction, ShapeUtil.rotate(from, direction, shape));
        }

        return builder;
    }

    public static AABB cloneBox(AABB box) {
        return new AABB(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }
}
