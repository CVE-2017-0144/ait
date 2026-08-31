package dev.amble.lib.block.behavior.base;

import dev.amble.lib.block.behavior.InvisibleBlockBehavior;
import dev.amble.lib.block.behavior.api.Archetype;
import dev.amble.lib.block.behavior.api.BlockBehavior;
import dev.amble.lib.block.behavior.api.BlockBehaviors;
import dev.amble.lib.blockentity.ABlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockWithEntityBehavior implements BlockBehavior {

    private final BiFunction<BlockPos, BlockState, ? extends BlockEntity> func;

    public BlockWithEntityBehavior(BiFunction<BlockPos, BlockState, ? extends BlockEntity> func) {
        this.func = func;
    }

    @Override
    public void init(Block block) {
        if (!(block instanceof EntityBlock))
            throw new IllegalStateException("Block " + block.getClass() + " does not implement a block entity provider!");
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return func.apply(pos, state);
    }

    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    @Override
    public int idx() {
        return BlockBehaviors.BLOCK_WITH_ENTITY;
    }

    public static class Ticking extends BlockWithEntityBehavior {

        public Ticking(BiFunction<BlockPos, BlockState, ? extends ABlockEntity> func) {
            super(func);
        }

        @Override
        public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
            return ABlockEntity::tick;
        }

        public static Archetype withInvisibleModel(BiFunction<BlockPos, BlockState, ? extends ABlockEntity> func) {
            return new Archetype(new Ticking(func), InvisibleBlockBehavior.behavior);
        }
    }
}
