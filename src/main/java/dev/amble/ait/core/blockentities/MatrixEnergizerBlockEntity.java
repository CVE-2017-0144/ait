package dev.amble.ait.core.blockentities;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.core.blocks.MatrixEnergizerBlock;

public class MatrixEnergizerBlockEntity
        extends BlockEntity
        implements GameEventListener.Provider<VibrationSystem.Listener>,
        VibrationSystem {
    private static final Logger LOGGER = LogUtils.getLogger();
    private VibrationSystem.Data listenerData;
    private final VibrationSystem.Listener listener;
    private final VibrationSystem.User callback = this.createCallback();
    private int lastVibrationFrequency;

    protected MatrixEnergizerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.listenerData = new VibrationSystem.Data();
        this.listener = new VibrationSystem.Listener(this);
    }

    public MatrixEnergizerBlockEntity(BlockPos pos, BlockState state) {
        this(AITBlockEntityTypes.MATRIX_ENERGIZER_BLOCK_ENTITY_TYPE, pos, state);
    }

    public VibrationSystem.User createCallback() {
        return new VibrationCallback(this.getBlockPos());
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        this.lastVibrationFrequency = nbt.getInt("last_vibration_frequency");
        if (nbt.contains("listener", Tag.TAG_COMPOUND)) {
            VibrationSystem.Data.CODEC.parse(new Dynamic<>(
                    NbtOps.INSTANCE, nbt.getCompound("listener")))
                    .resultOrPartial(LOGGER::error).ifPresent(listener -> {
                this.listenerData = listener;
            });
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        nbt.putInt("last_vibration_frequency", this.lastVibrationFrequency);
        VibrationSystem.Data.CODEC.encodeStart(NbtOps.INSTANCE, this.listenerData)
                .resultOrPartial(LOGGER::error).ifPresent(listenerNbt -> nbt.put("listener", listenerNbt));
    }

    @Override
    public VibrationSystem.Data getVibrationData() {
        return this.listenerData;
    }

    @Override
    public VibrationSystem.User getVibrationUser() {
        return this.callback;
    }

    public int getLastVibrationFrequency() {
        return this.lastVibrationFrequency;
    }

    public void setLastVibrationFrequency(int lastVibrationFrequency) {
        this.lastVibrationFrequency = lastVibrationFrequency;
    }

    @Override
    public VibrationSystem.Listener getListener() {
        return this.listener;
    }

    protected class VibrationCallback
            implements VibrationSystem.User {
        protected final BlockPos pos;
        private final PositionSource positionSource;

        public VibrationCallback(BlockPos pos) {
            this.pos = pos;
            this.positionSource = new BlockPositionSource(pos);
        }

        @Override
        public int getListenerRadius() {
            return 1;
        }

        @Override
        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public boolean canTriggerAvoidVibration() {
            return true;
        }

        @Override
        public boolean canReceiveVibration(ServerLevel world, BlockPos pos, Holder<GameEvent> event, @Nullable GameEvent.Context emitter) {
            if (pos.equals(this.pos) && (event.is(GameEvent.BLOCK_DESTROY) || event.is(GameEvent.BLOCK_PLACE))) {
                return false;
            }
            return MatrixEnergizerBlock.isInactive(MatrixEnergizerBlockEntity.this.getBlockState());
        }

        @Override
        public TagKey<GameEvent> getListenableEvents() {
            return AITTags.GameEvents.MATRIX_CAN_LISTEN;
        }

        @Override
        public void onReceiveVibration(ServerLevel world, BlockPos pos, GameEvent event, @Nullable Entity sourceEntity, @Nullable Entity entity, float distance) {
            BlockState blockState = MatrixEnergizerBlockEntity.this.getBlockState();
            if (MatrixEnergizerBlock.isInactive(blockState)) {
                MatrixEnergizerBlockEntity.this.setLastVibrationFrequency(VibrationSystem.getGameEventFrequency(event));
                Block block = blockState.getBlock();
                if (event.equals(GameEvent.SHRIEK) && block instanceof MatrixEnergizerBlock matrixEnergizerBlock) {
                    matrixEnergizerBlock.setActive(world, this.pos, blockState,
                            MatrixEnergizerBlockEntity.this.getLastVibrationFrequency());
                }
            }
        }

        @Override
        public void onDataChanged() {
            MatrixEnergizerBlockEntity.this.setChanged();
        }

        @Override
        public boolean requiresAdjacentChunksToBeTicking() {
            return true;
        }
    }
}
