package dev.amble.ait.core.blocks;

import static dev.amble.ait.client.util.TooltipUtil.addShiftHiddenTooltip;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.blockentities.EnvironmentProjectorBlockEntity;
import dev.amble.ait.core.blocks.types.HorizontalDirectionalBlock;
import dev.amble.ait.core.tardis.Tardis;

@SuppressWarnings("deprecation")
public class EnvironmentProjectorBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty SILENT = BooleanProperty.create("silent");

    public EnvironmentProjectorBlock(Properties settings) {
        super(settings.emissiveRendering((state, world, pos) -> state.getValue(EnvironmentProjectorBlock.ENABLED)).noOcclusion()
                .lightLevel(value -> value.getValue(EnvironmentProjectorBlock.ENABLED) ? 9 : 3));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = this.defaultBlockState();
        boolean powered = ctx.getLevel().hasNeighborSignal(ctx.getClickedPos());

        return blockState.setValue(ENABLED, powered).setValue(POWERED, powered).setValue(SILENT,
                ctx.getLevel().getBlockState(ctx.getClickedPos().below()).is(BlockTags.WOOL))
                .setValue(FACING, ctx.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ENABLED, POWERED, SILENT);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos,
                               boolean notify) {
        if (world.isClientSide())
            return;

        if (world.getBlockEntity(pos) instanceof EnvironmentProjectorBlockEntity projector)
            projector.neighborUpdate(state, world, pos);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
                              BlockHitResult hit) {
        if (world.isClientSide())
            return InteractionResult.PASS;

        if (hand != InteractionHand.MAIN_HAND)
            return InteractionResult.PASS;

        if(player.isShiftKeyDown()) {
            if (world.getBlockEntity(pos) instanceof EnvironmentProjectorBlockEntity proj){
                proj.onUse(state, world, pos, player);
            }
            return InteractionResult.SUCCESS;
        }

        if (world.getBlockEntity(pos) instanceof EnvironmentProjectorBlockEntity) {
            player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1.0F, 1.0F);
            AITMod.openScreen((ServerPlayer) player, 3, pos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public static void toggle(Tardis tardis, @Nullable Player player, Level world, BlockPos pos, BlockState state,
                              boolean active) {
        if (world.getBlockEntity(pos) instanceof EnvironmentProjectorBlockEntity projector)
            projector.toggle(tardis, state, active);

        if (state.getValue(SILENT))
            return;

        world.playSound(player, pos, active ? SoundEvents.BEACON_ACTIVATE : SoundEvents.BEACON_DEACTIVATE,
                SoundSource.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.1f + 0.9f);

        world.gameEvent(player, active ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnvironmentProjectorBlockEntity(pos, state);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, tooltipContext, tooltip, options);

        addShiftHiddenTooltip(stack, tooltip, tooltips -> {
            tooltip.add(Component.translatable("tooltip.ait.use_in_tardis").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        });
    }
}
