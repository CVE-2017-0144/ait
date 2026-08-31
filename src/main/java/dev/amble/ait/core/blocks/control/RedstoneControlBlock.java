package dev.amble.ait.core.blocks.control;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.blockentities.control.RedstoneControlBlockEntity;
import dev.amble.ait.core.tardis.Tardis;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class RedstoneControlBlock extends ControlBlock {
    private static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final IntegerProperty MODE = IntegerProperty.create("mode", 0, Mode.values().length - 1);

    public RedstoneControlBlock(Properties settings) {
        super(settings);

        this.registerDefaultState(
                this.getStateDefinition().any().setValue(POWERED, false).setValue(MODE, 0)
        );
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RedstoneControlBlockEntity(pos, state);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!(world.getBlockEntity(pos) instanceof RedstoneControlBlockEntity entity))
            return;

        if (world.isClientSide())
            return;

        if (!entity.isLinked())
            return;

        Tardis tardis = entity.tardis().get();
        Player user = tardis.loyalty().getLoyalPlayerInside();

        if (user == null)
            return;

        boolean wasPowered = state.getValue(POWERED);
        boolean powered = world.hasNeighborSignal(pos) || world.hasNeighborSignal(pos.above());

        if (wasPowered == powered)
            return;

        state = state.setValue(POWERED, powered);
        world.setBlock(pos, state, Block.UPDATE_ALL);

        if (!powered)
            return;

        entity.run((ServerPlayer) user, Mode.get(state));
    }

    @Override
    public Item asItem() {
        return AITItems.REDSTONE_CONTROL;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);

        builder.add(POWERED, MODE);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        if (isHoldingScanningSonic(player)) {
            sendSonicMessage((ServerPlayer) player, (RedstoneControlBlockEntity) world.getBlockEntity(pos));
            return InteractionResult.SUCCESS;
        }

        world.setBlockAndUpdate(pos, Mode.set(state, Mode.get(state).next())); // set to next mode
        world.playSound(null, pos, SoundEvents.STONE_BUTTON_CLICK_ON, SoundSource.BLOCKS, 0.1f, 0.5f);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void attack(BlockState state, Level world, BlockPos pos, Player player) {
        // dont run control
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx).setValue(FACING, Direction.NORTH); // i cba
    }

    public enum Mode {
        USE,
        PUNCH;

        Mode next() {
            return Mode.values()[(this.ordinal() + 1) % Mode.values().length];
        }

        static Mode get(BlockState state) {
            return Mode.values()[state.getValue(MODE)];
        }
        static BlockState set(BlockState state, Mode mode) {
            return state.setValue(MODE, mode.ordinal());
        }
    }
}
