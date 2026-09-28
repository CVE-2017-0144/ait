package dev.amble.ait.core.blocks;

import dev.amble.ait.core.blockentities.UntemperedSchismBlockEntity;
import dev.amble.ait.core.engine.link.block.FluidLinkBlockEntity;
import dev.amble.ait.core.engine.link.block.HorizontalFluidLinkBlock;
import dev.amble.ait.core.world.RiftChunkManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * @author Loqor
 * The purpose of this block is to rip open rifts in rift chunks as opposed to the silly entities.
 * */
@SuppressWarnings("deprecation")
public class UntemperedSchismBlock extends HorizontalFluidLinkBlock implements EntityBlock {

    // 10 seconds = 200 ticks. We tick every 2 ticks, so 100 steps.
    public static final int TOTAL_STEPS = 30;
    public static final int TICK_INTERVAL = 2;
    public static final int ARTRON_PER_TICK = 10;

    public static final IntegerProperty CHARGE_TICK = IntegerProperty.create("charge_tick", 0, TOTAL_STEPS);
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;

    public static final Vector3f COLOR_FROM = new Vector3f(1.0f, 0.85f, 0.0f); // bright gold
    public static final Vector3f COLOR_TO = new Vector3f(1.0f, 1.0f, 0.6f);    // pale yellow

    public UntemperedSchismBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(CHARGE_TICK, 0).setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH).setValue(ENABLED, false));
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, ctx.getHorizontalDirection().getOpposite()).setValue(ENABLED, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CHARGE_TICK).add(HorizontalDirectionalBlock.FACING).add(ENABLED);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level world, @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return (world1, blockPos, blockState, ticker) -> {
            if (ticker instanceof UntemperedSchismBlockEntity ripper) {
                ripper.tick(world, blockPos, blockState, ripper);
            }
        };
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (!(world instanceof ServerLevel serverWorld)) return;

        RiftChunkManager manager = RiftChunkManager.getInstance(serverWorld);
        ChunkPos chunkPos = new ChunkPos(pos);

        if (!isConsumable(manager, chunkPos)) {
            if (placer != null) {
                placer.sendSystemMessage(Component.translatable("message.ait.riftscanner.info3"));
            }
            world.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE,
                    SoundSource.BLOCKS, 1, 0.5f);
            return;
        }

        world.playSound(null, pos, SoundEvents.BEACON_ACTIVATE,
                SoundSource.BLOCKS, 1, 1.5f);
    }

    private static boolean isConsumable(RiftChunkManager manager, ChunkPos pos) {
        return manager.isRiftChunk(pos);
    }

    @Override
    public FluidLinkBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new UntemperedSchismBlockEntity(pos, state);
    }
}