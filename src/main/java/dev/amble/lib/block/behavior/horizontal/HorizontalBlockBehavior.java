package dev.amble.lib.block.behavior.horizontal;

import dev.amble.lib.block.behavior.api.Archetype;
import dev.amble.lib.block.behavior.base.BlockRotationBehavior;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class HorizontalBlockBehavior extends BlockRotationBehavior {

    public static final HorizontalBlockBehavior behavior = new HorizontalBlockBehavior();
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    @Override
    public BlockState initDefaultState(Block block, BlockState state) {
        return state.setValue(FACING, Direction.NORTH);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public void appendProperties(List<Property<?>> list) {
        list.add(FACING);
    }

    public static Direction getFacing(BlockState state) {
        return state.getValue(FACING);
    }

    private static final Archetype archFacePlayer = new Archetype(behavior, HorizontalBlockPlacementBehavior.behaviorFacePlayer);
    private static final Archetype arch = new Archetype(behavior, HorizontalBlockPlacementBehavior.behavior);

    public static Archetype withPlacement(boolean facePlayer) {
        return facePlayer ? archFacePlayer : arch;
    }
}
