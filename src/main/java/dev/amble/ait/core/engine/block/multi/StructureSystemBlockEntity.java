package dev.amble.ait.core.engine.block.multi;

import dev.amble.ait.core.engine.DurableSubSystem;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.engine.block.SubSystemBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class StructureSystemBlockEntity extends SubSystemBlockEntity {
    protected StructureSystemBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, SubSystem.IdLike id) {
        super(type, pos, state, id);
    }

    public boolean isStructureComplete() {
        return this.isStructureComplete(this.getLevel(), this.getBlockPos());
    }
    public boolean isStructureComplete(Level world, BlockPos pos) {
        return this.getStructure().check(world, pos);
    }
    protected boolean shouldRefresh(ServerLevel world, BlockPos pos) {
        return world.getServer().getTickCount() % 40 == 0; // every 2 seconds
    }
    protected abstract MultiBlockStructure getStructure();

    @Override
    public void onGainFluid() {
        if (this.hasLevel() && this.isStructureComplete()) {
            super.onGainFluid();
        }
    }

    @Override
    public void tick(Level world, BlockPos pos, BlockState state) {
        super.tick(world, pos, state);

        if (world.isClientSide()) return;

        if (this.shouldRefresh((ServerLevel) world, pos)) {
            this.processStructure();
        }
    }

    protected void processStructure() {
        boolean powered = this.isPowered();
        if (!powered) return;

        boolean complete = this.isStructureComplete();
        boolean broken = (this.system() instanceof DurableSubSystem durable) && durable.isBroken();
        boolean enabled = this.system().isEnabled();

        if (!complete && enabled) {
            this.onLoseFluid();
        }
        if (complete && !enabled && !broken) {
            this.onGainFluid();
        }
    }
}
