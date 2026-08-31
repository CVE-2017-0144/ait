package dev.amble.ait.module.planet.core.block;

import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.module.planet.core.blockentities.OxygenatorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class OxygenatorBlock extends BaseEntityBlock {
    @Override
    public MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(OxygenatorBlock::new);
    }

    public OxygenatorBlock(Properties settings) {
        super(settings);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OxygenatorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level world, @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return (world1, blockPos, blockState, ticker) -> {
            if (ticker instanceof OxygenatorBlockEntity oxygenatorBlockEntity) {
                oxygenatorBlockEntity.tick(world, blockPos, blockState, oxygenatorBlockEntity);
            }
        };
    }
}
