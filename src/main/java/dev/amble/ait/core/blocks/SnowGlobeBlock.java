package dev.amble.ait.core.blocks;

import dev.amble.ait.core.blockentities.SnowGlobeBlockEntity;
import dev.amble.ait.core.blocks.types.HorizontalDirectionalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
public class SnowGlobeBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public SnowGlobeBlock(Properties settings) {
        super(settings);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SnowGlobeBlockEntity(pos, state);
    }
}
