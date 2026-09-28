package dev.amble.ait.core.blockentities;


import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.engine.block.SubSystemBlockEntity;
import dev.amble.ait.core.engine.link.IFluidLink;
import dev.amble.ait.core.engine.link.IFluidSource;
import dev.amble.ait.core.engine.link.ITardisSource;
import dev.amble.ait.core.engine.link.tracker.FluidNetwork;
import dev.amble.ait.core.tardis.Tardis;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EngineBlockEntity extends SubSystemBlockEntity implements ITardisSource {
    private boolean firstTickHandled;

    public EngineBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.ENGINE_BLOCK_ENTITY_TYPE, pos, state, SubSystem.Id.ENGINE);

        if (!this.hasLevel()) return;
    }

    @Override
    public void tick(Level world, BlockPos pos, BlockState state) {
        super.tick(world, pos, state);
        if (!firstTickHandled && !world.isClientSide()) {
            firstTickHandled = true;
            FluidNetwork.rebuildFrom((ServerLevel) world, pos);
        }
    }

    @Override
    public void onPlaced(Level world, BlockPos pos, @Nullable LivingEntity placer) {
        super.onPlaced(world, pos, placer);
        if (world.isClientSide())
            return;

        this.tardis().ifPresent(tardis -> tardis.subsystems().engine().setEnabled(true));

        this.tryPlaceFillBlocks();
        this.rebuildOwnNetwork();
    }

    @Override
    public void onBroken(Level world, BlockPos pos) {
        this.onLoseFluid(); // always.
        this.tryRemoveFillBlocks();

        super.onBroken(world, pos);
    }

    /**
     * Places cable blocks adjacent and barrier blocks in corners
     * @return true if all blocks were placed
     */
    private boolean tryPlaceFillBlocks() {
        if (this.getLevel().isClientSide()) return false;

        boolean success = true;

        BlockPos centre = this.getBlockPos();
        ServerLevel world = (ServerLevel) this.getLevel();

        // place cable blocks adjacent
        for (Direction dir : Direction.values()) {
            if (dir == Direction.UP || dir == Direction.DOWN) continue;

            BlockPos offset = centre.relative(dir);
            success = success && tryPlace(world, offset, AITBlocks.CABLE_BLOCK.defaultBlockState());
        }

        // place barrier blocks in corners
        BlockPos corner = centre.offset(1, 0, 1);
        success = success && tryPlace(world, corner, Blocks.BARRIER.defaultBlockState());

        corner = centre.offset(-1, 0, 1);
        success = success && tryPlace(world, corner, Blocks.BARRIER.defaultBlockState());

        corner = centre.offset(1, 0, -1);
        success = success && tryPlace(world, corner, Blocks.BARRIER.defaultBlockState());

        corner = centre.offset(-1, 0, -1);
        success = success && tryPlace(world, corner, Blocks.BARRIER.defaultBlockState());

        return success;
    }

    private boolean tryPlace(ServerLevel world, BlockPos pos, BlockState state) {
        if (world.getBlockState(pos).canBeReplaced()) {
            world.setBlockAndUpdate(pos, state);
            return true;
        }
        return false;
    }

    /**
     * Removes cable blocks adjacent and barrier blocks in corners
     * @return true if all blocks were removed
     */
    private void tryRemoveFillBlocks() {
        if (this.getLevel().isClientSide())
            return;

        BlockPos centre = this.getBlockPos();
        ServerLevel world = (ServerLevel) this.getLevel();

        // place cable blocks adjacent
        for (Direction dir : Direction.values()) {
            if (dir == Direction.UP || dir == Direction.DOWN) continue;

            BlockPos offset = centre.relative(dir);
            tryRemoveIfMatches(world, offset, AITBlocks.CABLE_BLOCK);
        }

        // place barrier blocks in corners
        BlockPos corner = centre.offset(1, 0, 1);
        tryRemoveIfMatches(world, corner, Blocks.BARRIER);

        corner = centre.offset(-1, 0, 1);
        tryRemoveIfMatches(world, corner, Blocks.BARRIER);

        corner = centre.offset(1, 0, -1);
        tryRemoveIfMatches(world, corner, Blocks.BARRIER);

        corner = centre.offset(-1, 0, -1);
        tryRemoveIfMatches(world, corner, Blocks.BARRIER);
    }

    /**
     * Removes a block if it matches the expected block
     */
    private void tryRemoveIfMatches(ServerLevel world, BlockPos pos, Block expected) {
        BlockState state = world.getBlockState(pos);

        if (!state.is(expected))
            return;

        world.removeBlock(pos, false);
    }

    @Override
    public void onLinked() {
        this.tardis().ifPresent(tardis -> tardis.getDesktop().setEnginePos(this));
    }

    @Override
    public void onGainFluid() {
        super.onGainFluid();
        this.rebuildOwnNetwork();
    }

    @Override
    public void onLoseFluid() {
        super.onLoseFluid();
        this.rebuildOwnNetwork();
    }

    private void rebuildOwnNetwork() {
        if (this.hasLevel() && !this.getLevel().isClientSide()) {
            FluidNetwork.rebuildFrom((ServerLevel) this.getLevel(), this.getBlockPos());
        }
    }

    @Override
    public Tardis getTardisForFluid() {
        return this.tardis().get();
    }

    @Override
    public void setSource(IFluidSource source) {

    }

    @Override
    public void setLast(IFluidLink last) {

    }

    @Override
    public IFluidSource source(boolean search) {
        return this;
    }

    @Override
    public IFluidLink last() {
        return this;
    }

    @Override
    public BlockPos getLastPos() {
        return this.getBlockPos();
    }
}
