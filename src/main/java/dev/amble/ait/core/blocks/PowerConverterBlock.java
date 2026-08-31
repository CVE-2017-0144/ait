package dev.amble.ait.core.blocks;

import static dev.amble.ait.client.util.TooltipUtil.addShiftHiddenTooltip;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import dev.amble.ait.api.ConsumableBlock;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.core.engine.link.block.FluidLinkBlockEntity;
import dev.amble.ait.core.engine.link.block.HorizontalFluidLinkBlock;

public class PowerConverterBlock extends HorizontalFluidLinkBlock implements ConsumableBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape Y_SHAPE = Block.box(
            4.0,
            0.0,
            2.5,
            12.0,
            32.0,
            13.5
    );


    public PowerConverterBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Y_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Y_SHAPE;
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (world.getBlockEntity(pos) instanceof FluidLinkBlockEntity be) {
            if (world.isClientSide()) return InteractionResult.SUCCESS;
            if (!(be.isPowered())) return InteractionResult.FAIL;
            if (!stack.is(AITTags.Items.IS_TARDIS_FUEL) && !stack.has(DataComponents.FOOD)) return InteractionResult.FAIL;

            if (!player.isShiftKeyDown()) {
                be.source().addLevel(175);
                stack.shrink(1);
            } else {
                int count = stack.getCount();

                be.source().addLevel(175 * count);
                stack.shrink(count);
            }

            if (stack.has(DataComponents.FOOD)) {
                TardisCriterions.FEED_POWER_CONVERTER.trigger((ServerPlayer) player);
            }

            world.playSound(null, pos, AITSounds.POWER_CONVERT, SoundSource.BLOCKS, 1.0F, 1.0F);

            return InteractionResult.SUCCESS;
        }

        return super.useWithoutItem(state, world, pos, player, hit);
    }

    @Override
    public boolean canAcceptItem(Level world, BlockPos pos, ItemStack stack, Direction from) {
        return stack.is(AITTags.Items.IS_TARDIS_FUEL);
    }

    @Override
    public ItemStack insertItem(Level world, BlockPos pos, ItemStack stack, Direction from, boolean simulate) {
        if (!(world.getBlockEntity(pos) instanceof FluidLinkBlockEntity be)) return stack;

        if (!be.isPowered()) return stack;

        if (!simulate && !world.isClientSide) {
            if (be.source() == null) return stack;

            be.source().addLevel(175);
            world.playSound(null, pos, AITSounds.POWER_CONVERT, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        ItemStack leftover = stack.copy();
        leftover.shrink(1);

        return leftover.isEmpty() ? ItemStack.EMPTY : leftover;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public static class BlockEntity extends FluidLinkBlockEntity {
        public BlockEntity(BlockPos pos, BlockState state) {
            super(AITBlockEntityTypes.POWER_CONVERTER_BLOCK_TYPE, pos, state);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, tooltipContext, tooltip, options);


        addShiftHiddenTooltip(stack, tooltip, tooltips -> {
            tooltip.add(Component.translatable("tooltip.ait.power_converter").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        });
    }
}
