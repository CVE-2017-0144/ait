package dev.amble.lib.block.behavior.horizontal;

import dev.amble.lib.block.behavior.base.BlockPlacementBehavior;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class HorizontalBlockPlacementBehavior extends BlockPlacementBehavior {

    public static final HorizontalBlockPlacementBehavior behaviorFacePlayer = new HorizontalBlockPlacementBehavior(true);
    public static final HorizontalBlockPlacementBehavior behavior = new HorizontalBlockPlacementBehavior(false);

    public static HorizontalBlockPlacementBehavior get(boolean facePlayer) {
        return facePlayer ? behaviorFacePlayer : behavior;
    }

    private final boolean facePlayer;

    public HorizontalBlockPlacementBehavior(boolean facePlayer) {
        this.facePlayer = facePlayer;
    }

    @Override
    public BlockState getPlacementState(BlockState state, BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalDirection();
        return state.setValue(HorizontalBlockBehavior.FACING, facePlayer ? direction.getOpposite() : direction);
    }
}
