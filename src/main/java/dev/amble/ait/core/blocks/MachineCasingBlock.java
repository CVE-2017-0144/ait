package dev.amble.ait.core.blocks;

import dev.amble.ait.core.blockentities.MachineCasingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MachineCasingBlock extends Block implements EntityBlock {

    public MachineCasingBlock(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player,
            BlockHitResult hit) {
        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (world.getBlockEntity(pos) instanceof MachineCasingBlockEntity casing)
            casing.onUse(world, stack, player);

        return InteractionResult.CONSUME;
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (world.getBlockEntity(pos) instanceof MachineCasingBlockEntity casing)
            casing.onBreak(world);

        return super.playerWillDestroy(world, pos, state, player);
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MachineCasingBlockEntity(pos, state);
    }
}
