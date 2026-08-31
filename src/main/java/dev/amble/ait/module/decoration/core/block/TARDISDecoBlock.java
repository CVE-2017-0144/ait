package dev.amble.ait.module.decoration.core.block;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class TARDISDecoBlock extends Block {
    public static final EnumProperty<Direction> FACING = EnumProperty.create("facing", Direction.class);
    private final String id;

    public TARDISDecoBlock(Properties settings, String DecoID) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.id = DecoID();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // Get the player's facing direction (this will set the block to face the direction the player is facing)
        return this.defaultBlockState().setValue(FACING, context.getPlayer().getDirection());
    }

    public String DecoID() {
        return id;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
        // Update the facing if a neighboring block is adjacent
        if (facing == state.getValue(FACING).getOpposite()) {
            return state.setValue(FACING, facing);
        }
        return super.updateShape(state, facing, facingState, world, pos, facingPos);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, tooltipContext, tooltip, options);

        tooltip.add(Component.translatable("tooltip.ait.tardisdeco_type").withStyle(ChatFormatting.GREEN, ChatFormatting.ITALIC));
        tooltip.add(Component.literal(DecoID()).withStyle(ChatFormatting.DARK_GREEN));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        // Add the facing property to the block's state manager
        builder.add(FACING);
    }

}
