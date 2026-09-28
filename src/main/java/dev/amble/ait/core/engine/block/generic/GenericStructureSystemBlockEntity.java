package dev.amble.ait.core.engine.block.generic;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.engine.DurableSubSystem;
import dev.amble.ait.core.engine.StructureHolder;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.engine.block.multi.MultiBlockStructure;
import dev.amble.ait.core.engine.block.multi.StructureSystemBlockEntity;
import dev.amble.ait.core.engine.item.SubSystemItem;
import dev.amble.ait.core.util.StackUtil;
import dev.amble.ait.core.world.TardisServerWorld;

/**
 * a mutable version of the structure system block entity
 * it can have its id changed
 * usually set by the SubSystemItem
 * @see SubSystemItem
 * @author duzo
 */
public class GenericStructureSystemBlockEntity extends StructureSystemBlockEntity {
    private ItemStack idSource;

    protected GenericStructureSystemBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, null);
    }
    public GenericStructureSystemBlockEntity(BlockPos pos, BlockState state) {
        this(AITBlockEntityTypes.GENERIC_SUBSYSTEM_BLOCK_TYPE, pos, state);
    }

    public InteractionResult useOn(BlockState state, Level world, boolean sneaking, Player player, ItemStack hand) {
        if (!TardisServerWorld.isTardisDimension(world)) return InteractionResult.CONSUME;
        if (hand.isEmpty()) {
            if (this.system() != null && this.idSource != null) {
                if (this.system() instanceof DurableSubSystem durable && (durable.isBroken() || durable.durability() < DurableSubSystem.MAX_DURABILITY)) {
                    player.displayClientMessage(Component.translatable("tardis.message.engine.system_is_weakened"), true);
                    return InteractionResult.SUCCESS;
                }
                StackUtil.spawn(world, worldPosition, this.idSource.copyAndClear());
                if (this.tardis().isPresent() && this.id() != null) {
                    system().setEnabled(false);
                }
                world.playSound(null, this.getBlockPos(), AITSounds.WAYPOINT_ACTIVATE, SoundSource.BLOCKS, 1.0f, 0.1f);
                this.setChanged();
                this.id = null;
                return InteractionResult.SUCCESS;
            }
        }

        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        if (hand.getItem() instanceof SubSystemItem link) {
            if (this.system() != null && this.idSource != null) {
                if (tardis() != null) {
                    system().setEnabled(false);
                }
                StackUtil.spawn(world, worldPosition, this.idSource.copyAndClear());
            }
            this.setId(link.id());
            this.idSource = hand.copy();
            this.idSource.setCount(1);
            hand.shrink(1);
            world.playSound(null, this.getBlockPos(), AITSounds.WAYPOINT_ACTIVATE, SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private void setId(SubSystem.IdLike id) {
        this.id = id;
        this.onChangeId();
    }

    protected StructureHolder getHolder() {
        if (!(this.system() instanceof StructureHolder holder)) return null;

        return holder;
    }

    protected void onChangeId() {
        this.processStructure();
        this.setChanged();
        this.sync();
    }

    public boolean hasSystem() {
        return this.id() != null;
    }

    @Override
    protected MultiBlockStructure getStructure() {
        StructureHolder holder = this.getHolder();
        if (holder == null) return null;

        return holder.getStructure();
    }

    @Override
    public boolean isStructureComplete(Level world, BlockPos pos) {
        if (this.getStructure() == null) return false;

        return super.isStructureComplete(world, pos);
    }

    @Override
    protected boolean shouldRefresh(ServerLevel world, BlockPos pos) {
        if (this.getStructure() == null) return false;

        return super.shouldRefresh(world, pos);
    }

    @Override
    public void onLoseFluid() {
        if (this.system() == null) return;

        super.onLoseFluid();
    }

    @Override
    public void onBroken(Level world, BlockPos pos) {
        super.onBroken(world, pos);

        if (world.isClientSide() || this.idSource == null || this.idSource.isEmpty()) return;
        ItemStack stack = this.idSource.copyAndClear();

        if (this.isLinked() && this.tardis().get().interiorChanging().addRestorationStack(stack)) return;

        StackUtil.spawn(world, pos, stack);
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);

        if (this.idSource != null) {
            nbt.put("SourceStack", this.idSource.saveOptional(registries));
        }
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);

        if (nbt.contains("SourceStack")) {
            this.idSource = ItemStack.parseOptional(registries, nbt.getCompound("SourceStack"));
        }
    }

    /**
     * @return the source stack that was used to set the id
     */
    public Optional<ItemStack> getSourceStack() {
        return Optional.ofNullable(this.idSource);
    }
}
