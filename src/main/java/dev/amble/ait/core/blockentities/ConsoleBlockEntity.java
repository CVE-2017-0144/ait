package dev.amble.ait.core.blockentities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.ArtronHolderItem;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.entities.ConsoleControlEntity;
import dev.amble.ait.core.item.ChargedZeitonCrystalItem;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.TardisDesktop;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.control.ControlTypes;
import dev.amble.ait.core.tardis.control.sequences.SequenceHandler;
import dev.amble.ait.core.tardis.handler.FuelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.core.util.ItemNbt;
import dev.amble.ait.core.world.RiftChunkManager;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.schema.console.ConsoleTypeSchema;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.ait.registry.impl.ControlRegistry;
import dev.amble.ait.registry.impl.console.ConsoleRegistry;
import dev.amble.ait.registry.impl.console.variant.ConsoleVariantRegistry;
import dev.amble.lib.util.ServerLifecycleHooks;
import org.joml.Vector3f;

public class ConsoleBlockEntity extends AbstractConsoleBlockEntity implements BlockEntityTicker<ConsoleBlockEntity>, ArtronHolderItem {

    private ItemStack sonicScrewdriver = ItemStack.EMPTY;
    private NonNullList<ItemStack> inventory = NonNullList.withSize(54, ItemStack.EMPTY);

    public final List<ConsoleControlEntity> controlEntities = new ArrayList<>();
    public final Map<Control, Control.ControlState> controlStateMap = new HashMap<>();
    public final AnimationState ANIM_STATE = new AnimationState();

    private boolean needsControls = true;

    private ConsoleTypeSchema type;
    private ConsoleVariantSchema variant;

    public int age;

    public ConsoleBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.CONSOLE_BLOCK_ENTITY_TYPE, pos, state);
    }

    @Override
    public void onLinked() {
        if (this.getLevel() == null || !TardisServerWorld.isTardisDimension(this.getLevel())) return;
        if (this.tardis().isEmpty())
            return;

        Tardis tardis = this.tardis().get();

        if (tardis instanceof ClientTardis)
            return;

        tardis.getDesktop().getConsolePos().add(this.worldPosition);
        tardis.asServer().markDirty(tardis.getDesktop());
        this.markNeedsControl();
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);

        nbt.putString("type", this.getTypeSchema().id().toString());
        nbt.putString("variant", this.getVariant().id().toString());
        ContainerHelper.saveAllItems(nbt, this.inventory, registries);
        if (this.sonicScrewdriver != null) {
            nbt.put("sonic_screwdriver", this.sonicScrewdriver.saveOptional(registries));
        }

        CompoundTag controlNbt = new CompoundTag();

        this.controlStateMap.forEach((control, state) -> {
            controlNbt.put(control.id().toString(), state.writeNbt());
        });

        nbt.put("ControlStates", controlNbt);
    }

    public void updateDurability(Control control, float durability) {
        if (control == null) return;
        if (durability >= ConsoleControlEntity.MAX_DURABILITY) {
            Control.ControlState state = this.controlStateMap.get(control);
            if (state != null && state.sticky()) {
                state.setDamage(durability);
            } else {
                this.controlStateMap.remove(control);
            }
            this.setChanged();
            return;
        }

        this.getOrCreateState(control).setDamage(durability);
        this.setChanged();
    }

    public void updateStickiness(Control control, boolean sticky) {
        if (control == null) return;

        if (!sticky) {
            Control.ControlState state = this.controlStateMap.get(control);
            if (state == null) {
                return;
            }
            state.setSticky(false);
            if (state.damage() >= ConsoleControlEntity.MAX_DURABILITY) {
                this.controlStateMap.remove(control);
            }
            this.setChanged();
            return;
        }

        this.getOrCreateState(control).setSticky(true);
        this.setChanged();
    }

    @Override
    protected Component getContainerName() {
        return tardis().isPresent()
                ? Component.literal(tardis().get().stats().getName())
                : Component.translatable("ait.console.inventory");
    }

    @Override
    protected AbstractContainerMenu createScreenHandler(int syncId, Inventory playerInventory) {
        return ChestMenu.sixRows(syncId, playerInventory, this);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);

        this.setType(ConsoleRegistry.getInstance().get(ResourceLocation.tryParse(nbt.getString("type"))));

        this.setVariant(ConsoleVariantRegistry.getInstance().get(ResourceLocation.tryParse(nbt.getString("variant"))));

        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.inventory, registries);
        if (nbt.contains("sonic_screwdriver")) {
            this.sonicScrewdriver = ItemStack.parseOptional(registries, nbt.getCompound("sonic_screwdriver"));
        }

        if (!nbt.contains("ControlStates", CompoundTag.TAG_COMPOUND)) return;

        CompoundTag controlStatesNbt = nbt.getCompound("ControlStates");

        for (String key : controlStatesNbt.getAllKeys()) {
            ResourceLocation id = ResourceLocation.tryParse(key);

            if (id == null) continue;

            Control control = ControlRegistry.REGISTRY.get(id);

            if (control == null) continue;

            if (!controlStatesNbt.contains(id.toString(), CompoundTag.TAG_COMPOUND)) return;

            CompoundTag compound1 = controlStatesNbt.getCompound(id.toString());
            Control.ControlState existingState = controlStateMap.get(control);

            if (existingState != null) {
                existingState.readNbt(compound1);
            } else {
                controlStateMap.put(control, new Control.ControlState().readNbt(compound1));
            }
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        if (level.dimension().equals(Level.OVERWORLD)) {
            return super.getUpdateTag(registries);
        }
        this.markNeedsControl();
        return super.getUpdateTag(registries);
    }

    public ConsoleTypeSchema getTypeSchema() {
        if (type == null)
            this.setType(ConsoleRegistry.HARTNELL);

        return type;
    }

    public void setType(ConsoleTypeSchema schema) {
        this.type = schema;
        this.setChanged();
    }

    public void setVariant(ConsoleVariantSchema schema) {
        this.variant = schema;
        this.setChanged();
    }

    public ConsoleVariantSchema getVariant() {
        if (variant == null)
            this.setVariant(this.getTypeSchema().getDefaultVariant());

        return variant;
    }

    public int getAge() {
        return age;
    }

    public void useOn(Level world, boolean sneaking, Player player) {
        if (world.isClientSide())
            return;

        if (this.tardis().isEmpty())
            return;

        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem() == AITBlocks.ZEITON_CLUSTER.asItem()) {
            this.tardis().get().addFuel(15);

            if (!player.isCreative())
                itemStack.shrink(1);

            return;
        }

        if (itemStack.getItem() instanceof ChargedZeitonCrystalItem) {
            CompoundTag nbt = ItemNbt.get(itemStack);

            if (!nbt.contains(ChargedZeitonCrystalItem.FUEL_KEY))
                return;

            this.tardis().get().addFuel(nbt.getDouble(ChargedZeitonCrystalItem.FUEL_KEY));
            nbt.putDouble(ChargedZeitonCrystalItem.FUEL_KEY, 0);
            ItemNbt.set(itemStack, nbt);
        }
    }

    @Override
    public void setRemoved() {
        this.killControls();
        super.setRemoved();
    }

    public void onBroken() {
        this.killControls();
        if (this.tardis().isEmpty())
            return;

        Tardis tardis = this.tardis().get();
        TardisDesktop desktop = tardis.getDesktop();

        desktop.getConsolePos().remove(this.worldPosition);
        tardis.asServer().markDirty(desktop);
    }

    public void killControls() {
        for (ConsoleControlEntity entity : controlEntities) {
            Control control = entity.getControl();
            if (control != null) {
                this.updateStateFromEntity(control, entity.getDurability(), entity.isSticky());
            }
        }

        controlEntities.forEach(Entity::discard);
        controlEntities.clear();
        this.setChanged();
    }

    public void spawnControls() {
        BlockPos current = this.getBlockPos();

        if (!(this.level instanceof ServerLevel serverWorld))
            return;

        if (!TardisServerWorld.isTardisDimension((ServerLevel) this.getLevel()))
            return;

        this.killControls();
        ConsoleTypeSchema consoleType = this.getTypeSchema();
        ControlTypes[] controls = consoleType.getControlTypes();

        for (ControlTypes control : controls) {
            ConsoleControlEntity controlEntity = ConsoleControlEntity.create(this.level, this.tardis().get());

            Vector3f position = current.getCenter().toVector3f().add(control.getOffset().x(), control.getOffset().y(),
                    control.getOffset().z());
            controlEntity.setPos(position.x(), position.y(), position.z());
            controlEntity.setYRot(0.0f);
            controlEntity.setXRot(0.0f);

            Control controlKey = control.getControl();
            Control.ControlState state = controlKey == null ? null : this.controlStateMap.get(controlKey);
            float durability = state == null ? ConsoleControlEntity.MAX_DURABILITY : state.damage();
            boolean sticky = state != null && state.sticky();

            controlEntity.setControlData(consoleType, control, this.getBlockPos(), durability, sticky);

            serverWorld.addFreshEntity(controlEntity);
            this.controlEntities.add(controlEntity);
        }

        this.needsControls = false;
    }

    public void markNeedsControl() {
        this.needsControls = true;
    }

    @Override
    public void tick(Level world, BlockPos pos, BlockState state, ConsoleBlockEntity blockEntity) {
        if (!TardisServerWorld.isTardisDimension(world)) {
            return;
        }

        if (this.needsControls)
            this.spawnControls();

        if (!(world instanceof ServerLevel serverWorld)) {
            this.age++;

            ANIM_STATE.startIfStopped(this.age);
            return;
        }

        if (!TardisServerWorld.isTardisDimension((ServerLevel) this.getLevel()))
            this.setRemoved();

        if (!this.isLinked())
            return;

        SequenceHandler handler = this.tardis().get().sequence();
        TravelHandlerBase.State travelState = this.tardis().get().travel().getState();

        if (travelState == TravelHandlerBase.State.FLIGHT || travelState == TravelHandlerBase.State.MAT) {
            if (handler.hasActiveSequence() && handler.getActiveSequence() != null) {
                List<Control> sequence = handler.getActiveSequence().getControls();

                this.controlEntities.forEach(entity -> {
                    int index = sequence.indexOf(entity.getControl());

                    Control control = entity.getControl();
                    entity.setPartOfSequence(index != -1);
                    entity.setWasSequenced(handler.doesControlIndexMatch(control));
                    entity.setSequenceIndex(index);
                    entity.setSequenceLength(sequence.size());
                });
            } else {
                this.controlEntities.forEach(entity -> entity.setPartOfSequence(false));
            }
        }

        world.updateNeighborsAt(pos, state.getBlock());

        ServerTardis tardis = (ServerTardis) this.tardis().get();
        boolean isRiftChunk = RiftChunkManager.isRiftChunk(tardis.travel().position());

        boolean moreThanAFew = this.controlEntities.stream()
                .filter(controlEntity -> controlEntity.getDurability() <
                        ConsoleControlEntity.DurabilityStates.FULL.durability)
                .count() > 5;

        if (tardis.travel().isCrashing() || moreThanAFew) {
            serverWorld.sendParticles(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5f, pos.getY() + 1.25,
                    pos.getZ() + 0.5f, 5, 0, 0, 0, 0.025f);

            serverWorld.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, pos.getX() + 0.5f, pos.getY() + 1.85,
                    pos.getZ() + 0.5f, 2, 0.2f, 0.5f, 0.2f, 0.01f);

            serverWorld.sendParticles(
                    new DustColorTransitionOptions(new Vector3f(0.75f, 0.75f, 0.75f),
                            new Vector3f(0.1f, 0.1f, 0.1f), 1),
                    pos.getX() + 0.5f, pos.getY() + 1.25, pos.getZ() + 0.5f, 1, 0, 0, 0, 0.01f);
        }

        if (tardis.crash().isToxic() || tardis.crash().isUnstable()) {
            serverWorld.sendParticles(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5f, pos.getY() + 1.25,
                    pos.getZ() + 0.5f, 5, 0, 0, 0, 0.025f);

            serverWorld.sendParticles(ParticleTypes.CLOUD, pos.getX() + 0.5f, pos.getY() + 1.25, pos.getZ() + 0.5f, 1,
                    0, 0.05f, 0, 0.025f);
        }

        if (tardis.crash().isToxic()) {
            serverWorld.sendParticles(
                    new DustColorTransitionOptions(new Vector3f(0.75f, 0.85f, 0.75f),
                            new Vector3f(0.15f, 0.25f, 0.15f), 1),
                    pos.getX() + 0.5f, pos.getY() + 1.25, pos.getZ() + 0.5f, 1,
                    AITMod.RANDOM.nextBoolean() ? 0.5f : -0.5f, 3f,
                    AITMod.RANDOM.nextBoolean() ? 0.5f : -0.5f, 0.025f);
        }

        if (tardis.isRefueling() && tardis.getFuel() < FuelHandler.TARDIS_MAX_FUEL) {
            serverWorld.sendParticles((isRiftChunk) ? ParticleTypes.FIREWORK : ParticleTypes.END_ROD,
                    pos.getX() + 0.5f, pos.getY() + 1.25, pos.getZ() + 0.5f, 1, 0, 0, 0,
                    (isRiftChunk) ? 0.05f : 0.025f);
        }

        if (ServerLifecycleHooks.get().getTickCount() % 10 != 0)
            return;

        if (!sonicScrewdriver.isEmpty()) {
            if (this.hasMaxFuel(sonicScrewdriver))
                return;

            if (!tardis.fuel().hasPower())
                return;

            this.addFuel(10, sonicScrewdriver);
            tardis.fuel().removeFuel(10);
        }
    }

    public static ConsoleTypeSchema previousConsole(ConsoleTypeSchema current) {
        List<ConsoleTypeSchema> list = ConsoleRegistry.getInstance().toList();
        int idx = list.indexOf(current);
        int size = list.size();

        return list.get((size + idx - 1) % size);
    }

    public static ConsoleTypeSchema nextConsole(ConsoleTypeSchema current) {
        List<ConsoleTypeSchema> list = ConsoleRegistry.getInstance().toList();
        int idx = list.indexOf(current);
        int size = list.size();

        return list.get((idx + 1) % size);
    }

    public static ConsoleVariantSchema previousVariant(ConsoleVariantSchema current) {
        List<ConsoleVariantSchema> list = ConsoleVariantRegistry.withParent(current.parent());
        int idx = list.indexOf(current);
        int size = list.size();

        return list.get((size + idx - 1) % size);
    }

    public static ConsoleVariantSchema nextVariant(ConsoleVariantSchema current) {
        List<ConsoleVariantSchema> list = ConsoleVariantRegistry.withParent(current.parent());
        int idx = list.indexOf(current);
        int size = list.size();

        return list.get((idx + 1) % size);
    }

    @Override
    public int getContainerSize() {
        return this.getInventory().size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.getInventory()) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.get(slot);
    }

    public NonNullList<ItemStack> getInventory() {
        return this.inventory;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(this.getInventory(), slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return this.getInventory().remove(slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.getInventory().set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public boolean canTakeItem(Container hopperInventory, int slot, ItemStack stack) {
        return false;
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player) && !this.isEmpty();
    }

    @Override
    public void clearContent() {
        this.getInventory().clear();
    }

    public void setSonicScrewdriver(ItemStack stack) {
        this.sonicScrewdriver = stack;
        this.sync();
        this.setChanged();
    }

    public ItemStack getSonicScrewdriver() {
        return this.sonicScrewdriver;
    }

    @Override
    public double getMaxFuel(ItemStack stack) {
        return SonicItem.MAX_FUEL;
    }

    private Control.ControlState getOrCreateState(Control control) {
        return this.controlStateMap.computeIfAbsent(control,
                entry -> new Control.ControlState());
    }

    private void updateStateFromEntity(Control control, float damage, boolean sticky) {
        if (damage >= ConsoleControlEntity.MAX_DURABILITY && !sticky) {
            this.controlStateMap.remove(control);
            return;
        }

        this.controlStateMap.put(control, new Control.ControlState().setDamage(damage).setSticky(sticky));
    }

    @Override
    public void sync() {
        this.setChanged();
        if (this.level != null && !this.level.isClientSide)
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), net.minecraft.world.level.block.Block.UPDATE_ALL);
    }
}