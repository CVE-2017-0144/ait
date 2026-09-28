package dev.amble.ait.core.engine.link.block;

import dev.amble.ait.api.tardis.link.v2.block.InteriorLinkableBlockEntity;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.engine.link.IFluidLink;
import dev.amble.ait.core.engine.link.IFluidSource;
import dev.amble.ait.core.engine.link.tracker.FluidNetwork;
import dev.amble.ait.core.util.SoundData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public abstract class FluidLinkBlockEntity extends InteriorLinkableBlockEntity implements IFluidLink {
    private boolean powered = false;
    private IFluidLink last;
    private IFluidSource source;
    private BlockPos lastPos;

    protected FluidLinkBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Nullable @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onGainFluid() {
        if (this.hasLevel() && this.getGainPowerSound() != null) {
            this.getGainPowerSound().play((ServerLevel) this.getLevel(), this.getBlockPos());
        }
    }

    @Override
    public void onLoseFluid() {
        if (this.hasLevel() && this.getLosePowerSound() != null) {
            this.getLosePowerSound().play((ServerLevel) this.getLevel(), this.getBlockPos());
        }
    }
    protected SoundData getLosePowerSound() {
        return new SoundData(AITSounds.SLOT_IN, SoundSource.BLOCKS, 0.1F, 0.75F);
    }
    protected SoundData getGainPowerSound() {
        return new SoundData(AITSounds.FLUID_LINK_CONNECT, SoundSource.BLOCKS, 0.1F, 0.75F);
    }

    public boolean isPowered() {
        return this.powered && this.source != null;
    }

    @Override
    public IFluidSource source(boolean search) {
        return this.source;
    }

    public IFluidSource source() {
        return this.source;
    }

    @Override
    public void setSource(IFluidSource source) {
        this.source = source;
    }

    @Override
    public IFluidLink last() {
        return this.last;
    }

    @Override
    public void setLast(IFluidLink last) {
        this.last = last;
    }

    public BlockPos getLastPos() {
        return lastPos;
    }

    public void setLastPos(BlockPos lastPos) {
        this.lastPos = lastPos;
    }

    /**
     * Applied by {@link FluidNetwork} during a rebuild. Writes the new upstream pointer / source /
     * powered state and fires gain/lose callbacks on transitions. Cables and subsystems must not
     * mutate these fields outside this method.
     */
    public void applyNetworkAssignment(@Nullable IFluidSource newSource, @Nullable IFluidLink newLast,
                                       @Nullable BlockPos newLastPos, boolean newPowered) {
        boolean changed = this.source != newSource || this.last != newLast
                || (this.lastPos == null ? newLastPos != null : !this.lastPos.equals(newLastPos));
        boolean wasPowered = this.powered;

        this.source = newSource;
        this.last = newLast;
        this.lastPos = newLastPos;
        this.powered = newPowered;

        if (wasPowered != newPowered) {
            if (newPowered) {
                this.onGainFluid();
            } else {
                this.onLoseFluid();
            }
        }

        if (changed || wasPowered != newPowered) {
            this.broadcastState();
        }
    }

    private void broadcastState() {
        if (!this.hasLevel()) return;

        this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(this.getBlockState()));
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }

    public void onBroken(Level world, BlockPos pos) {
        if (world.isClientSide())
            return;
        if (this.isPowered())
            this.onLoseFluid();

        this.source = null;
        this.last = null;
        this.lastPos = null;
        this.powered = false;

        FluidNetwork.rebuildAround((ServerLevel) world, pos);
    }

    public void onPlaced(Level world, BlockPos pos, @Nullable LivingEntity placer) {
        if (world.isClientSide())
            return;

        FluidNetwork.rebuildFrom((ServerLevel) world, pos);
    }

    public void onNeighborUpdate(Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos) {
        if (world.isClientSide())
            return;

        // Only react to changes from blocks that participate in the fluid-link graph;
        // a redstone clock or piston next door must not force a network rebuild.
        if (sourcePos != null && !(world.getBlockState(sourcePos).getBlock() instanceof IFluidLink)) {
            return;
        }

        FluidNetwork.rebuildFrom((ServerLevel) world, pos);
    }
}
