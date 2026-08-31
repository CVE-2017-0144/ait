package dev.amble.ait.core.entities;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.common.Scheduler;
import dev.drtheo.scheduler.api.common.TaskStage;
import io.netty.handler.codec.EncoderException;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITEntityTypes;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.entities.base.LinkableDummyEntity;
import dev.amble.ait.core.item.RepairToolItem;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.item.control.ControlBlockItem;
import dev.amble.ait.core.item.sonic.SonicMode;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.TardisManager;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.control.ControlTypes;
import dev.amble.ait.core.tardis.control.impl.HammerHangerControl;
import dev.amble.ait.data.schema.console.ConsoleTypeSchema;
import dev.amble.ait.registry.impl.ControlRegistry;

public class ConsoleControlEntity extends LinkableDummyEntity {
    private static final EntityDataAccessor<Float> WIDTH = SynchedEntityData.defineId(ConsoleControlEntity.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> HEIGHT = SynchedEntityData.defineId(ConsoleControlEntity.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Vector3f> OFFSET = SynchedEntityData.defineId(ConsoleControlEntity.class,
            EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Boolean> PART_OF_SEQUENCE = SynchedEntityData.defineId(ConsoleControlEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SEQUENCE_INDEX = SynchedEntityData.defineId(ConsoleControlEntity.class,
            EntityDataSerializers.INT); // <--->
    private static final EntityDataAccessor<Integer> SEQUENCE_LENGTH = SynchedEntityData.defineId(ConsoleControlEntity.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> WAS_SEQUENCED = SynchedEntityData.defineId(ConsoleControlEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ON_DELAY = SynchedEntityData.defineId(ConsoleControlEntity.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<BlockPos> CONSOLE_BLOCK_POS = SynchedEntityData.defineId(ConsoleControlEntity.class,
            EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<String> CONTROL_ID = SynchedEntityData.defineId(ConsoleControlEntity.class,
            EntityDataSerializers.STRING);
    private Control control;
    public static final float MAX_DURABILITY = 1.0f;

    public ConsoleControlEntity(EntityType<? extends Entity> entityType, Level world) {
        super(entityType, world);
    }

    private ConsoleControlEntity(Level world, Tardis tardis) {
        this(AITEntityTypes.CONTROL_ENTITY_TYPE, world);
        this.link(tardis);
    }

    public static ConsoleControlEntity create(Level world, Tardis tardis) {
        return new ConsoleControlEntity(world, tardis);
    }

    @Override
    public void onClientRemoval() {
        if (this.getConsoleBlockPos() == null) {
            super.onClientRemoval();
            return;
        }

        if (this.level().getBlockEntity(this.getConsoleBlockPos()) instanceof ConsoleBlockEntity console)
            console.markNeedsControl();
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(WIDTH, 0.125f);
        this.entityData.define(HEIGHT, 0.125f);
        this.entityData.define(OFFSET, new Vector3f(0));
        this.entityData.define(PART_OF_SEQUENCE, false);
        this.entityData.define(SEQUENCE_INDEX, 0);
        this.entityData.define(SEQUENCE_LENGTH, 0);
        this.entityData.define(WAS_SEQUENCED, false);
        this.entityData.define(ON_DELAY, false);
        this.entityData.define(CONSOLE_BLOCK_POS, BlockPos.ZERO);
        this.entityData.define(CONTROL_ID, "");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);

        nbt.put("console", NbtUtils.writeBlockPos(this.getConsoleBlockPos()));

        nbt.putFloat("width", this.getControlWidth());
        nbt.putFloat("height", this.getControlHeight());
        nbt.putFloat("offsetX", this.getOffset().x());
        nbt.putFloat("offsetY", this.getOffset().y());
        nbt.putFloat("offsetZ", this.getOffset().z());
        nbt.putBoolean("partOfSequence", this.isPartOfSequence());
        nbt.putInt("sequenceColor", this.getSequenceIndex());
        nbt.putBoolean("wasSequenced", this.wasSequenced());
        nbt.putFloat("durability", this.getDurability());
        nbt.putBoolean("sticky", this.isSticky());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);

        CompoundTag console = nbt.getCompound("console");

        if (nbt.contains("console")) {
            this.setConsolePos(NbtUtils.readBlockPos(console));
        }

        if (nbt.contains("width") && nbt.contains("height")) {
            this.setControlWidth(nbt.getFloat("width"));
            this.setControlHeight(nbt.getFloat("height"));
            this.refreshDimensions();
        }

        if (nbt.contains("offsetX") && nbt.contains("offsetY") && nbt.contains("offsetZ"))
            this.setOffset(new Vector3f(nbt.getFloat("offsetX"), nbt.getFloat("offsetY"), nbt.getFloat("offsetZ")));

        if (nbt.contains("partOfSequence"))
            this.setPartOfSequence(nbt.getBoolean("partOfSequence"));

        if (nbt.contains("sequenceColor"))
            this.setSequenceIndex(nbt.getInt("sequenceColor"));

        if (nbt.contains("wasSequenced"))
            this.setWasSequenced(nbt.getBoolean("wasSequenced"));

        if (nbt.contains("durability"))
            this.setDurability(nbt.getFloat("durability"));
        if (nbt.contains("sticky"))
            this.setSticky(nbt.getBoolean("sticky"));
    }

    public void setConsolePos(BlockPos consoleBlockPos) {
        this.entityData.set(CONSOLE_BLOCK_POS, consoleBlockPos);
    }

    @Override
    public void onSyncedDataUpdated(List<SynchedEntityData.DataValue<?>> dataEntries) {
        this.setScaleAndCalculate(this.getEntityData().get(WIDTH), this.getEntityData().get(HEIGHT));
    }

    @Override
    public boolean isPickable() {
        return !isRemoved();
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);

        if (handStack.is(AITItems.REPAIR_TOOL) && this.getDurability() < MAX_DURABILITY) {
            return InteractionResult.PASS;
        }

        if (player.getOffhandItem().is(Items.COMMAND_BLOCK)) {
            controlEditorHandler(player);
            return InteractionResult.SUCCESS;
        }

        if (handStack.is(AITBlocks.REDSTONE_CONTROL_BLOCK.asItem()) && this.getControl() != null) {
            CompoundTag nbt = handStack.getOrCreateTag();
            nbt.putString(ControlBlockItem.CONTROL_ID_KEY, this.getControl().id().toString());
            ConsoleBlockEntity consoleBlockEntity = this.getConsole();
            nbt.putString(ControlBlockItem.CONSOLE_TYPE_ID_KEY, consoleBlockEntity.getTypeSchema().id().toString());
            return InteractionResult.SUCCESS;
        }

        if (hand == InteractionHand.MAIN_HAND && !this.run(player, player.level(), false))
            this.playFailFx();

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof PrimedTnt)
            return false;

        if (source.getEntity() instanceof Player player) {
            if (source.getDirectEntity() instanceof Projectile)
                source.getDirectEntity().discard();

            if (player.getOffhandItem().is(Items.COMMAND_BLOCK))
                controlEditorHandler(player);
            else if (!this.run(player, player.level(), true))
                this.playFailFx();
        }

        return false;
    }

    private void playFailFx() {
        if (this.level().isClientSide())
            return;

        ServerLevel world = (ServerLevel) this.level();

        // spawn particle above the control - change from the ait particle to crit - Loqor
        world.sendParticles(ParticleTypes.CRIT, this.getX(), this.getY() + 0.25, this.getZ(), 1, 0.05, 0.05, 0.05, 0.025);

        // Changed from that horrible, ear-grating, nausea-inducing SHIELD BREAK to a much softer sound - Loqor
        world.playSound(null, this.blockPosition(), SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundSource.BLOCKS, 0.5F, AITMod.RANDOM.nextFloat(0.5F, 1F));
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (this.getEntityData().hasItem(WIDTH) && this.getEntityData().hasItem(HEIGHT))
            return EntityDimensions.scalable(this.getControlWidth(), this.getControlHeight());

        return super.getDimensions(pose);
    }

    @Override
    public void tick() {
        if (this.level().isClientSide())
            return;

        if (this.control == null && this.getConsoleBlockPos() != null)
            this.discard();

        switch (this.getDurabilityState(this.getDurability())) {
            case SPARKY -> this.spark();
            case OCCASIONALY_JAM -> this.smoke();
            case CATCH_FIRE -> this.onFire();
            // Don't have the COMPLETELY BROKEN one show anything, this is the player's fault
            default -> {}
        }
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }

    public float getControlWidth() {
        return this.entityData.get(WIDTH);
    }

    public float getControlHeight() {
        return this.entityData.get(HEIGHT);
    }

    public void setControlWidth(float width) {
        this.entityData.set(WIDTH, width);
    }

    public void setControlHeight(float height) {
        this.entityData.set(HEIGHT, height);
    }

    public Control getControl() {
        // The control field is only assigned server-side (see setControlData), so on the client we
        // resolve it lazily from the synced id.
        if (this.control == null) {
            String id = this.entityData.get(CONTROL_ID);

            if (!id.isEmpty())
                this.control = ControlRegistry.REGISTRY.get(ResourceLocation.tryParse(id));
        }

        return control;
    }

    public Vector3f getOffset() {
        return this.entityData.get(OFFSET);
    }

    public void setOffset(Vector3f offset) {
        this.entityData.set(OFFSET, offset);
    }

    public int getSequenceIndex() {
        return this.entityData.get(SEQUENCE_INDEX);
    }

    public void setSequenceIndex(int i) {
        this.entityData.set(SEQUENCE_INDEX, i);
    }

    public int getSequenceLength() {
        return this.entityData.get(SEQUENCE_LENGTH);
    }

    public void setSequenceLength(int n) {
        this.entityData.set(SEQUENCE_LENGTH, n);
    }

    public float getSequencePercentage() {
        return (this.getSequenceIndex() + 1f) / this.getSequenceLength();
    }

    public boolean wasSequenced() {
        return this.entityData.get(WAS_SEQUENCED);
    }

    public void setWasSequenced(boolean sequenced) {
        this.entityData.set(WAS_SEQUENCED, sequenced);
    }

    public void setPartOfSequence(boolean partOfSequence) {
        this.entityData.set(PART_OF_SEQUENCE, partOfSequence);
    }

    public boolean isPartOfSequence() {
        return this.entityData.get(PART_OF_SEQUENCE);
    }

    public boolean isOnDelay() {
        return this.entityData.get(ON_DELAY);
    }

    public float getDurability() {
        if (this.getControl() == null || this.getConsole() == null) return MAX_DURABILITY;
        return this.getConsole().controlStateMap.getOrDefault(this.getControl(), new Control.ControlState()).damage();//this.dataTracker.get(DURABILITY);
    }

    public boolean isSticky() {
        if (this.getControl() == null || this.getConsole() == null) return false;
        return this.getConsole().controlStateMap.getOrDefault(this.getControl(), new Control.ControlState()).sticky();//this.dataTracker.get(DURABILITY);
    }

    public DurabilityStates getDurabilityState(float durability) {
        return DurabilityStates.get(durability);
    }

    public void setDurability(float durability) {
        ConsoleBlockEntity console = this.getConsole();
        if (console != null && this.getControl() != null) {
            console.updateDurability(this.getControl(), durability);
            console.setChanged();
        }
    }

    public void setSticky(boolean sticky) {
        ConsoleBlockEntity console = this.getConsole();
        if (console != null && this.getControl() != null) {
            console.updateStickiness(this.getControl(), sticky);
            console.setChanged();
        }
    }

    public void addDurability(float durability) {
        this.setDurability(Math.min(this.getDurability() + durability, MAX_DURABILITY));
    }

    public void subtractDurability(float durability) {
        this.setDurability(Math.max(this.getDurability() - durability, 0));
    }

    public boolean run(Player player, Level world, boolean leftClick) {
        if (isSticky()) {
            if (player.getMainHandItem().is(Items.SHEARS)) {
                this.playSound(SoundEvents.SHEEP_SHEAR, 1, 1);
                this.setSticky(false);
                return true;
            }

            this.playSound(SoundEvents.SLIME_BLOCK_BREAK, 0.4f, 1);
            player.level().addParticle(
                    new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SLIME_BLOCK.defaultBlockState()),
                    this.getX(), this.getY(), this.getZ(),
                    0.2, 0.5, -0.1
            );

            return true;
        } else if (player.getMainHandItem().is(Items.SLIME_BALL)) {
            this.playSound(SoundEvents.SLIME_BLOCK_PLACE, 1, 1);
            this.setSticky(true);
            return true;
        }

        if (world.isClientSide())
            return false;

        if (player.getMainHandItem().is(AITItems.TARDIS_ITEM))
            this.discard();

        if (!this.isLinked()) {
            AITMod.LOGGER.warn("Discarding invalid control entity at {}; console pos: {}", this.position(),
                    this.getConsoleBlockPos());

            this.discard();
            return false;
        }

        Tardis tardis = this.tardis().get();

        ItemStack stack = player.getMainHandItem();
        if (tardis.travel().isLanded() && ((stack.is(AITItems.SONIC_SCREWDRIVER) && SonicItem.mode(stack) == SonicMode.Modes.TARDIS) || stack.getItem() instanceof RepairToolItem) && this.getDurability() < 1.0f) {
            Vec3 pos = this.position();
            this.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 1);
            ((ServerLevel) this.getCommandSenderWorld()).sendParticles(ParticleTypes.WAX_ON,
                    pos.x(), pos.y(), pos.z(), 2, 0.2, 0.4, 0.2, 0.02);
            this.addDurability(0.1f);
            return true;
        }

        control.runAnimation(tardis, (ServerPlayer) player, (ServerLevel) world);

        DurabilityStates state = this.getDurabilityState(this.getDurability());
        boolean hasMallet = player.getMainHandItem().is(AITItems.HAMMER);

        if (state == DurabilityStates.CATCH_FIRE && !hasMallet) {
            player.setRemainingFireTicks(random.nextIntBetweenInclusive(20*2, 20*6));
            player.hurt(world.damageSources().hotFloor(), 4);
        }

        if (this.isOnDelay())
            return false;

        if (!this.control.canRun(tardis, (ServerPlayer) player))
            return false;

        if (world.getRandom().nextIntBetweenInclusive(1, 10_000) == 72)
            this.level().playSound(null, this.blockPosition(), AITSounds.EVEN_MORE_SECRET_MUSIC, SoundSource.MASTER,
                    1F, 1F);

        if (hasMallet) {
            this.playSound(AITSounds.KNOCK, 1, 0.25f);
            Vec3 pos = this.position();
            ((ServerLevel) world).sendParticles(ParticleTypes.SCRAPE,
                    pos.x(), pos.y(), pos.z(), 2, 0.2, 0.4, 0.2, 0.02);

            if (!(this.getControl() instanceof HammerHangerControl)) {
                this.subtractDurability(random.nextIntBetweenInclusive(1, 4) / 25f * this.getDurability());
            }
        }

        if (state == DurabilityStates.JAMMED) {
            if (hasMallet && random.nextIntBetweenInclusive(0, 5) < 2)
                tardis.subsystems().engine().removeDurability(random.nextIntBetweenInclusive(0, 7));

            return false;
        }

        if (state == DurabilityStates.SPARKY && random.nextIntBetweenInclusive(0, 20) < 10) {
            if (hasMallet) {
                this.setDurability(state.next().durability);
            } else {
                return false;
            }
        }

        if (state == DurabilityStates.OCCASIONALY_JAM && random.nextIntBetweenInclusive(0, 25) < 5) {
            if (!hasMallet) return false;
        }

        if (this.control.shouldHaveDelay(tardis) && !this.isOnDelay()) {
            this.entityData.set(ON_DELAY, true);

            Scheduler.get().runTaskLater(() -> this.entityData.set(ON_DELAY, false),
                    TaskStage.END_SERVER_TICK, TimeUnit.TICKS, this.control.getDelayLength(tardis));
        }

        Control.Result result = this.control.handleRun(tardis, (ServerPlayer) player, (ServerLevel) world, this.getConsoleBlockPos(), leftClick);

        if (result == Control.Result.SEQUENCE) {
            if (random.nextIntBetweenInclusive(0, 8) == 5) {
                // Clamping the random variance to avoid extreme swings
                float variance = (float) random.nextGaussian() * 0.15f + 0.5f;
                this.subtractDurability(Mth.clamp(variance, 0.1f, 0.9f));
            }
        }

        ConsoleBlockEntity console = this.getConsole();
        if (console != null) {
            this.level().playSound(null, this.blockPosition(), this.control.getSound(console.getTypeSchema(), result), SoundSource.BLOCKS, 0.7f,
                    1f);
        }

        return result.isSuccess();
    }

    public ConsoleBlockEntity getConsole() {
        if (this.getConsoleBlockPos() == null)
            return null;

        BlockEntity blockEntity = this.level().getBlockEntity(this.getConsoleBlockPos());
        if (blockEntity instanceof ConsoleBlockEntity console)
            return console;

        AITMod.LOGGER.warn("Control entity at {} has no console block entity at {}", this.position(), this.getConsoleBlockPos());
        return null;
    }

    private void spark() {
        if (!(this.level() instanceof ServerLevel serverWorld)) return;
        Vec3 pos = this.position();
        if (serverWorld.getServer().getTickCount() % 15 == 0 && random.nextBoolean()) {
            this.playSound(SoundEvents.CHAIN_BREAK, 0.1f, random.nextBoolean() ? 1f : 2f);
            serverWorld.sendParticles(ParticleTypes.ELECTRIC_SPARK, pos.x(), pos.y(), pos.z(), 5, 0.2, 0.2, 0.2, 0.01);
            serverWorld.sendParticles(ParticleTypes.LAVA, pos.x(), pos.y(), pos.z(), 3, 0.1, 0.1, 0.1, 0.01);
        }
    }

    private void smoke() {
        if (!(this.level() instanceof ServerLevel serverWorld)) return;
        Vec3 pos = this.position();
        if (serverWorld.getServer().getTickCount() % 10 == 0)
            serverWorld.sendParticles(ParticleTypes.SMOKE, pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0.0f);
    }

    private void onFire() {
        if (!(this.level() instanceof ServerLevel serverWorld)) return;
        Vec3 pos = this.position();
        serverWorld.sendParticles(ParticleTypes.SMOKE, pos.x(), pos.y(), pos.z(), 1, 0, 0, 0, 0.0f);
        if (serverWorld.getServer().getTickCount() % 10 == 0)
            serverWorld.sendParticles(ParticleTypes.SMALL_FLAME, pos.x(), pos.y() + 0.2f, pos.z(), 1, 0, 0.075f, 0, 0);
    }

    public BlockPos getConsoleBlockPos() {
        return this.entityData.get(CONSOLE_BLOCK_POS);
    }

    public void setScaleAndCalculate(float width, float height) {
        this.setControlWidth(width);
        this.setControlHeight(height);
        this.refreshDimensions();
    }

    public void setControlData(ConsoleTypeSchema consoleType, ControlTypes type, BlockPos consoleBlockPosition, float durability, boolean sticky) {
        this.setConsolePos(consoleBlockPosition);
        this.control = type.getControl();
        this.entityData.set(CONTROL_ID, this.control.id().toString());

        super.setCustomName(this.control.getName(this.tardis().get()));

        if (consoleType != null) {
            this.setControlWidth(type.getScale().width);
            this.setControlHeight(type.getScale().height);
            this.setOffset(type.getOffset());
            this.setDurability(durability);
            this.setSticky(sticky);
        }
    }

    public void logConsoleJson() {
        // convert all controls into json
        JsonArray root = new JsonArray();

        for (ConsoleControlEntity entity : this.getConsole().controlEntities) {
            if (entity == null) continue;

            ControlTypes type = new ControlTypes(entity.getControl(), entity.getDimensions(Pose.STANDING), entity.getOffset());
            DataResult<JsonElement> dataResult = ControlTypes.CODEC.encodeStart(JsonOps.INSTANCE, type);
            JsonElement typeData = Util.getOrThrow(dataResult, error -> new EncoderException("Failed to encode: " + error + " " + type));

            root.add(typeData);
        }

        AITMod.LOGGER.info(TardisManager.getInstance(this).getFileGson().toJson(root));
    }

    public void controlEditorHandler(Player player) {
        if (!player.canUseGameMasterBlocks()) return;

        float increment = 0.0125f;

        if (player.getMainHandItem().getItem() == Items.PAPER && !player.level().isClientSide()) {
            logConsoleJson();

            player.sendSystemMessage(Component.translatable("message.ait.console_control.json_logged"));

            return;
        }

        if (player.getMainHandItem().getItem() == Items.EMERALD_BLOCK)
            this.setPos(this.position().add(player.isShiftKeyDown() ? -increment : increment, 0, 0));

        if (player.getMainHandItem().getItem() == Items.DIAMOND_BLOCK)
            this.setPos(this.position().add(0, player.isShiftKeyDown() ? -increment : increment, 0));

        if (player.getMainHandItem().getItem() == Items.REDSTONE_BLOCK)
            this.setPos(this.position().add(0, 0, player.isShiftKeyDown() ? -increment : increment));

        if (player.getMainHandItem().getItem() == Items.COD)
            this.setScaleAndCalculate(player.isShiftKeyDown()
                    ? this.getEntityData().get(WIDTH) - increment
                    : this.getEntityData().get(WIDTH) + increment, this.getEntityData().get(HEIGHT));

        if (player.getMainHandItem().getItem() == Items.COOKED_COD)
            this.setScaleAndCalculate(this.getEntityData().get(WIDTH),
                    player.isShiftKeyDown()
                            ? this.getEntityData().get(HEIGHT) - increment
                            : this.getEntityData().get(HEIGHT) + increment);

        if (this.getConsoleBlockPos() != null) {
            Vec3 centered = this.position().subtract(this.getConsoleBlockPos().getCenter());
            this.setOffset(centered.toVector3f());
            if (this.control != null)
                player.sendSystemMessage(Component.literal("EntityDimensions.changing(" + this.getControlWidth() + "f, "
                        + this.getControlHeight() + "f), new Vector3f(" + centered.x() + "f, " + centered.y()
                        + "f, " + centered.z() + "f)),"));
        }
    }

    @Override
    public void setCustomName(@Nullable Component name) {}

    public enum DurabilityStates {
        JAMMED(0.0f),
        CATCH_FIRE(0.25f),
        OCCASIONALY_JAM(0.5f),
        SPARKY(0.75f),
        FULL(ConsoleControlEntity.MAX_DURABILITY);
        public final float durability;
        DurabilityStates(float durabilityLevel) {
            this.durability = durabilityLevel;
        }

        public static DurabilityStates get(String id) {
            return DurabilityStates.valueOf(id.toUpperCase());
        }

        public static DurabilityStates get(float level) {
            level = DurabilityStates.normalize(level);

            for (int i = 0; i < values().length - 1; i++) {
                DurabilityStates current = values()[i];
                DurabilityStates next = values()[i + 1];

                if (current.durability <= level && level < next.durability)
                    return current;
            }

            return DurabilityStates.FULL;
        }

        public static float normalize(float durability) {
            return Math.min(Math.max(durability, DurabilityStates.JAMMED.durability), DurabilityStates.FULL.durability);
        }

        public DurabilityStates next() {
            return switch (this) {
                case JAMMED -> CATCH_FIRE;
                case CATCH_FIRE -> OCCASIONALY_JAM;
                case OCCASIONALY_JAM -> SPARKY;
                case SPARKY -> FULL;
                case FULL -> JAMMED;
            };
        }
    }
}
