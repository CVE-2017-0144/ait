package dev.amble.ait.core.blockentities.control;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import dev.amble.ait.api.tardis.link.v2.block.InteriorLinkableBlockEntity;
import dev.amble.ait.core.blocks.control.RedstoneControlBlock;
import dev.amble.ait.core.item.control.ControlBlockItem;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.data.schema.console.ConsoleTypeSchema;
import dev.amble.ait.registry.impl.ControlRegistry;
import dev.amble.ait.registry.impl.console.ConsoleRegistry;
import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.common.Scheduler;
import dev.drtheo.scheduler.api.common.TaskStage;

public abstract class ControlBlockEntity extends InteriorLinkableBlockEntity {

    private Control control;
    private ConsoleTypeSchema consoleType;
    private boolean onDelay = false;

    protected ControlBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);

        if (this.getControl() != null)
            nbt.putString(ControlBlockItem.CONTROL_ID_KEY, this.getControl().id().toString());

        if (this.getConsoleType() != null)
            nbt.putString(ControlBlockItem.CONSOLE_TYPE_ID_KEY, this.getConsoleType().id().toString());
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);

        if (nbt.contains(ControlBlockItem.CONTROL_ID_KEY))
            this.setControlId(ResourceLocation.parse(nbt.getString(ControlBlockItem.CONTROL_ID_KEY)));

        if (nbt.contains(ControlBlockItem.CONSOLE_TYPE_ID_KEY))
            this.setConsoleId(ResourceLocation.parse(nbt.getString(ControlBlockItem.CONSOLE_TYPE_ID_KEY)));
    }

    /**
     * Gets the control Can be null if this hasnt been linked
     *
     * @return control
     */
    public Control getControl() {
        return this.control;
    }

    public ConsoleTypeSchema getConsoleType() {
        if (this.consoleType == null) {
            // default
            this.consoleType = ConsoleRegistry.HARTNELL;
        }

        return this.consoleType;
    }

    public void setControlId(ResourceLocation id) {
        Optional<Control> found = ControlRegistry.fromId(id);

        if (found.isEmpty())
            return;

        this.control = found.get();
    }

    public void setConsoleId(ResourceLocation id) {
        Optional<ConsoleTypeSchema> found = ConsoleRegistry.getInstance().getOptional(id);

        if (found.isEmpty())
            return;

        this.consoleType = found.get();
    }

    public boolean run(ServerPlayer user, boolean isMine) {
        if (this.getControl() == null || this.onDelay)
            return false;

        if (!this.isLinked() || !(this.tardis().get() instanceof ServerTardis tardis))
            return false;

        if (!this.control.canRun(tardis, user))
            return false;

        if (this.control.shouldHaveDelay(tardis) && !this.onDelay)
            this.createDelay(this.control.getDelayLength(tardis));

        Control.Result result = this.control.handleRun(tardis, user, user.serverLevel(), this.worldPosition, isMine);
        this.getLevel().playSound(null, worldPosition, this.control.getSound(this.getConsoleType(), result), SoundSource.BLOCKS, 0.7f, 1f);

        return result.isSuccess();
    }

    public boolean run(ServerPlayer user, RedstoneControlBlock.Mode mode) {
        return this.run(user, mode == RedstoneControlBlock.Mode.PUNCH);
    }

    public void createDelay(long ticks) {
        this.onDelay = true;

        Scheduler.get().runTaskLater(() -> this.onDelay = false, TaskStage.END_SERVER_TICK, TimeUnit.TICKS, ticks);
    }
}
