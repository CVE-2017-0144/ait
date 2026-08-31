package dev.amble.ait.data;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.google.common.collect.Maps;

public class ShapeMap {

    private final Map<Direction, VoxelShape> map;

    private ShapeMap(Map<Direction, VoxelShape> map) {
        this.map = Maps.newEnumMap(map);
    }

    public VoxelShape get(Direction direction) {
        return this.map.get(direction);
    }

    public static class Builder {

        private final Map<Direction, VoxelShape> map;

        private Builder(Map<Direction, VoxelShape> map) {
            this.map = map;
        }

        public Builder() {
            this(new HashMap<>(6));
        }

        public Builder add(Direction direction, VoxelShape shape) {
            this.map.put(direction, shape);
            return this;
        }

        public Builder union(ShapeMap other) {
            for (Direction direction : Direction.values()) {
                VoxelShape own = this.map.get(direction);
                VoxelShape another = other.get(direction);

                this.add(direction, Shapes.or(own, another));
            }

            return this;
        }

        public ShapeMap build() {
            return new ShapeMap(this.map);
        }
    }
}
