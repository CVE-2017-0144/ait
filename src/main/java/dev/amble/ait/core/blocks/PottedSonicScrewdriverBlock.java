package dev.amble.ait.core.blocks;

import com.mojang.serialization.MapCodec;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.blockentities.PottedSonicScrewdriverBlockEntity;
import dev.amble.ait.core.item.SonicItem;

public class PottedSonicScrewdriverBlock extends BaseEntityBlock {
    public static final int MAX_SONICS = 6;
    protected static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 6, 11);

    @Override
    public MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PottedSonicScrewdriverBlock::new);
    }

    public PottedSonicScrewdriverBlock(Properties settings) {
        super(settings);
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PottedSonicScrewdriverBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SonicItem)
            return InteractionResult.PASS;

        if (!world.isClientSide && world.getBlockEntity(pos) instanceof PottedSonicScrewdriverBlockEntity pot) {
            ItemStack sonic = pot.removeLast();

            if (!sonic.isEmpty()) {
                if (!player.addItem(sonic))
                    player.drop(sonic, false);
            }

            if (pot.count() == 0)
                world.setBlock(pos, Blocks.FLOWER_POT.defaultBlockState(), Block.UPDATE_ALL);

            world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        }

        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(Items.FLOWER_POT));

        if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof PottedSonicScrewdriverBlockEntity pot) {
            for (ItemStack sonic : pot.getSonics())
                drops.add(sonic.copy());
        }

        return drops;
    }
}
