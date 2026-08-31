package dev.amble.ait.core.blockentities;

import dev.amble.ait.api.ArtronHolder;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITEntityTypes;
import dev.amble.ait.core.blocks.UntemperedSchismBlock;
import dev.amble.ait.core.engine.link.IFluidLink;
import dev.amble.ait.core.engine.link.IFluidSource;
import dev.amble.ait.core.engine.link.block.FluidLinkBlockEntity;
import dev.amble.ait.core.engine.link.tracker.FluidNetwork;
import dev.amble.ait.core.entities.RiftEntity;
import dev.amble.ait.core.util.EntityRef;
import dev.amble.ait.core.world.RiftChunkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class UntemperedSchismBlockEntity extends FluidLinkBlockEntity implements BlockEntityTicker<UntemperedSchismBlockEntity>, ArtronHolder, IFluidSource {

    private boolean firstTickHandled;
    public double artronAmount = 0;
    public boolean hasCreatedRift = false;
    private EntityRef<RiftEntity> riftRef;

    public UntemperedSchismBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.RIFT_RIPPER_BLOCK_ENTITY_TYPE, pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putDouble("artronAmount", this.artronAmount);
        nbt.putBoolean("hasCreatedRift", this.hasCreatedRift);
        if (this.riftRef != null) {
            nbt.putUUID("riftId", this.riftRef.getId());
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        if (nbt.contains("artronAmount"))
            this.setCurrentFuel(nbt.getDouble("artronAmount"));
        if (nbt.contains("hasCreatedRift"))
            this.hasCreatedRift = nbt.getBoolean("hasCreatedRift");
        if (nbt.contains("riftId"))
            this.riftRef = new EntityRef<>(null, nbt.getUUID("riftId"));
        super.load(nbt);
    }

    @Override
    public void setCurrentFuel(double artronAmount) {
        this.artronAmount = artronAmount;
        this.updateListeners(this.getBlockState());
    }

    @Override
    public double getMaxFuel() {
        return 10 * 20 * UntemperedSchismBlock.ARTRON_PER_TICK;
    }

    @Override
    public double getCurrentFuel() {
        return this.artronAmount;
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbtCompound = super.getUpdateTag();
        nbtCompound.putDouble("artronAmount", this.artronAmount);
        return nbtCompound;
    }

    @Override
    public void tick(Level world, BlockPos pos, BlockState state, UntemperedSchismBlockEntity blockEntity) {
        if (!(world instanceof ServerLevel serverWorld))
            return;

        if (this.hasCreatedRift)
            return;

        if (!firstTickHandled) {
            firstTickHandled = true;
            FluidNetwork.rebuildFrom(serverWorld, pos);
        }

        int centerX = pos.getX();
        int centerZ = pos.getZ();

        double targetY = pos.getY() + 2.5d;

        double endX = centerX + 0.5;
        double endZ = centerZ + 0.5;

        RiftChunkManager manager = RiftChunkManager.getInstance(serverWorld);
        if (this.getCurrentFuel() >= this.getMaxFuel()) {
            RiftEntity riftEntity = new RiftEntity(serverWorld);
            this.riftRef = new EntityRef<>(serverWorld, riftEntity);

            float rotation = this.getBlockState().getValue(HorizontalDirectionalBlock.FACING).toYRot();

            float adjustedRotation = rotation + 180.0f;

            riftEntity.absMoveTo(endX, targetY, endZ, adjustedRotation, 0);

            riftEntity.setYRot(adjustedRotation);
            riftEntity.setYHeadRot(adjustedRotation);
            riftEntity.setYBodyRot(adjustedRotation);

            serverWorld.addFreshEntity(riftEntity);
            this.hasCreatedRift = true;

            serverWorld.setBlockAndUpdate(pos, state.setValue(UntemperedSchismBlock.ENABLED, true));
            this.updateListeners(state);

            serverWorld.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(),
                    SoundSource.BLOCKS, 1.5f, 0.5f);
        } else if (manager.getArtron(new ChunkPos(pos)) > UntemperedSchismBlock.ARTRON_PER_TICK && serverWorld.getServer().getTickCount() % 20 == 4 && !state.getValue(UntemperedSchismBlock.ENABLED)) {
            double percentage = (this.getCurrentFuel() * 100d) / this.getMaxFuel();
            serverWorld.playSound(null, this.getBlockPos(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 5.0f, 0.5f + (float) percentage / 40);
        }

        this.updateListeners(state);
    }

    @Override
    public void onBroken(Level world, BlockPos pos) {
        this.onLoseFluid(); // always.

        if (this.riftRef != null && world instanceof ServerLevel serverWorld) {
            this.riftRef.setWorld(serverWorld);
            if (this.riftRef.get() != null)
                this.riftRef.get().discard();
        }

        super.onBroken(world, pos);
    }

    private void updateListeners(BlockState state) {
        this.setChanged();

        if (!this.hasLevel())
            return;

        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), state, Block.UPDATE_ALL);
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
        if (this.getLevel() instanceof ServerLevel serverWorld) {
            FluidNetwork.rebuildFrom(serverWorld, this.getBlockPos());
        }
    }

    @Override
    public double level() {
        return this.getCurrentFuel();
    }

    @Override
    public void setLevel(double level) {
        this.setCurrentFuel(level);
    }

    @Override
    public double maxLevel() {
        return this.getMaxFuel();
    }

    @Override
    public void setSource(IFluidSource source) {

    }

    @Override
    public void setLast(IFluidLink last) {

    }

    @Override
    public BlockPos getLastPos() {
        return this.getBlockPos();
    }
}
