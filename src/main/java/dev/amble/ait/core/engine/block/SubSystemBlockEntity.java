package dev.amble.ait.core.engine.block;


import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.engine.DurableSubSystem;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.engine.link.block.FluidLinkBlockEntity;
import dev.amble.ait.core.engine.registry.SubSystemRegistry;
import dev.amble.ait.core.util.SoundData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SubSystemBlockEntity extends FluidLinkBlockEntity {
    protected SubSystem.IdLike id;

    public SubSystemBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, SubSystem.IdLike id) {
        super(type, pos, state);
        this.id = id;
    }

    public SubSystem system() {
        if (!(this.isLinked()) || this.id() == null) return null;

        return this.tardis().get().subsystems().get(this.id());
    }
    protected SubSystem.IdLike id() {
        if (this.id == null) {
            this.id = ((SubSystemBlock) this.getBlockState().getBlock()).getSystemId();
        }

        return this.id;
    }

    @Override
    public void onGainFluid() {
        super.onGainFluid();

        if (this.system() == null) return;
        if (this.system() instanceof DurableSubSystem durable) {
            if (durable.isBroken()) return;
        }
        this.system().setEnabled(true);
    }

    @Override
    public void onLoseFluid() {
        super.onLoseFluid();

        if (this.system() == null) return;
        this.system().setEnabled(false);
    }

    @Override
    public void onBroken(Level world, BlockPos pos) {
        super.onBroken(world, pos);

        if (this.isLinked())
            this.tardis().get().interiorChanging().addRestorationStack(new ItemStack(this.getBlockState().getBlock()));
    }

    @Override
    protected SoundData getGainPowerSound() {
        return new SoundData(AITSounds.SIEGE_DISABLE, SoundSource.BLOCKS, 0.25f, 1.0f);
    }

    @Override
    protected SoundData getLosePowerSound() {
        return new SoundData(AITSounds.SIEGE_ENABLE, SoundSource.BLOCKS, 0.25f, 1.0f);
    }

    public void tick(Level world, BlockPos pos, BlockState state) {}

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);

        if (this.id != null) {
            nbt.putString("SystemId", this.id.name());
        }
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);

        if (nbt.contains("SystemId")) {
            this.id = SubSystemRegistry.getInstance().get(nbt.getString("SystemId"));
        }
    }
}
