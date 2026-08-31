package dev.amble.ait.core.tardis.handler;

import java.util.ArrayList;
import java.util.List;

import dev.amble.ait.core.AITSounds;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.common.Scheduler;
import dev.drtheo.scheduler.api.common.TaskStage;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.api.tardis.TardisTickable;
import dev.amble.ait.core.AITDamageTypes;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.lock.LockedDimension;
import dev.amble.ait.core.lock.LockedDimensionRegistry;
import dev.amble.ait.core.tardis.control.impl.SecurityControl;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.data.Exclude;
import dev.amble.ait.data.properties.Property;
import dev.amble.ait.data.properties.Value;
import dev.amble.ait.data.properties.bool.BoolProperty;
import dev.amble.ait.data.properties.bool.BoolValue;
import dev.amble.ait.data.properties.integer.IntProperty;
import dev.amble.ait.data.properties.integer.IntValue;
import dev.amble.ait.data.schema.desktop.TardisDesktopSchema;
import dev.amble.ait.registry.impl.CategoryRegistry;
import dev.amble.ait.registry.impl.DesktopRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import dev.amble.lib.data.DirectedGlobalPos;

public class InteriorChangingHandler extends KeyedTardisComponent implements TardisTickable {
    public static final ResourceLocation CHANGE_DESKTOP = AITMod.id("change_desktop");
    private static final Property<ResourceLocation> QUEUED_INTERIOR_PROPERTY = new Property<>(Property.IDENTIFIER, "queued_interior", new ResourceLocation(""));
    private static final BoolProperty QUEUED = new BoolProperty("queued");
    private static final BoolProperty REGENERATING = new BoolProperty("regenerating");
    private static final int MIN_FUEL_COST = 5000;

    public static final int MAX_PLASMIC_MATERIAL_AMOUNT = 8;
    private static final Component HINT_TEXT = Component.translatable("tardis.message.growth.hint").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC);

    private final Value<ResourceLocation> queuedInterior = QUEUED_INTERIOR_PROPERTY.create(this);
    private static final IntProperty PLASMIC_MATERIAL_AMOUNT = new IntProperty("plasmic_material_amount");
    private final IntValue plasmicMaterialAmount = PLASMIC_MATERIAL_AMOUNT.create(this);
    private static final BoolProperty HAS_CAGE = new BoolProperty("has_cage");
    private final BoolValue hasCage = HAS_CAGE.create(this);
    private final BoolValue queued = QUEUED.create(this);
    private final BoolValue regenerating = REGENERATING.create(this);

    @Exclude
    private List<ItemStack> restorationChestContents;

    public InteriorChangingHandler() {
        super(Id.INTERIOR);
    }

    @Override
    public void onLoaded() {
        plasmicMaterialAmount.of(this, PLASMIC_MATERIAL_AMOUNT);
        hasCage.of(this, HAS_CAGE);
        queuedInterior.of(this, QUEUED_INTERIOR_PROPERTY);
        queued.of(this, QUEUED);
        regenerating.of(this, REGENERATING);

        if (!this.isServer() || !this.regenerating.get())
            return;

        this.regenerating.set(false);

        TardisDesktopSchema queuedSchema = this.getQueuedInterior();

        if (queuedSchema == null)
            return;

        tardis.interiorChangingHandler().queueInteriorChange(queuedSchema);
    }

    static {
        TardisEvents.DEMAT.register(tardis -> {
            if (tardis.isGrowth()
                    || (tardis.interiorChangingHandler().queued().get() && tardis.alarm().isEnabled()))
                return TardisEvents.Interaction.FAIL;

            return TardisEvents.Interaction.PASS;
        });

        TardisEvents.MAT.register(tardis -> {
            if (!tardis.isGrowth())
                return TardisEvents.Interaction.PASS;

            tardis.travel().autopilot(false);
            tardis.getExterior().setType(CategoryRegistry.CAPSULE);
            tardis.getExterior().setVariant(ExteriorVariantRegistry.CAPSULE_DEFAULT);
            return TardisEvents.Interaction.SUCCESS;
        });

        TardisEvents.LOSE_POWER.register(tardis -> tardis.interiorChangingHandler().queued.set(false));

        ServerPlayNetworking.registerGlobalReceiver(InteriorChangingHandler.CHANGE_DESKTOP,
                ServerTardisManager.receiveTardis(SecurityControl.withLoyaltyCheck((tardis, server, player, handler, buf, responseSender) -> {
                    TardisDesktopSchema desktop = DesktopRegistry.getInstance().get(buf.readResourceLocation());

                    if (tardis == null || desktop == null)
                        return;

                    if (tardis.travel().getState() != TravelHandler.State.LANDED)
                        return;

                    TardisCriterions.REDECORATE.trigger(player);
                    tardis.interiorChangingHandler().queueInteriorChange(desktop);
                    tardis.alarm().enable();
                })));
    }

    public BoolValue queued() {
        return queued;
    }

    public int plasmicMaterialAmount() {
        return plasmicMaterialAmount.get();
    }

    public boolean hasCage() {
        return hasCage.get();
    }

    public void setHasCage(boolean value) {
        hasCage.set(value);
    }

    public void setPlasmicMaterialAmount(int amount) {
        plasmicMaterialAmount.set(amount);
    }

    public void addPlasmicMaterial(int amount) {
        plasmicMaterialAmount.set(Math.min(plasmicMaterialAmount() + amount, MAX_PLASMIC_MATERIAL_AMOUNT));
    }

    public BoolValue regenerating() {
        return regenerating;
    }

    public TardisDesktopSchema getQueuedInterior() {
        return DesktopRegistry.getInstance().get(queuedInterior.get());
    }

    public void queueInteriorChange(TardisDesktopSchema schema) {
        if (!this.canQueue())
            return;

        if (tardis.fuel().getCurrentFuel() < (MIN_FUEL_COST * tardis.travel().instability())) {
            tardis.asServer().world().players().forEach(player -> player.displayClientMessage(
                    Component.translatable("tardis.message.interiorchange.not_enough_fuel").withStyle(ChatFormatting.RED),
                    true));

            return;
        }

        if (tardis.subsystems().isEnabled()) {
            tardis.asServer().world().players().forEach(player -> {
                int count = 0;

                for (SubSystem subSystem : tardis.subsystems()) {
                    if (subSystem.isEnabled())
                        count++;
                }

                player.displayClientMessage(
                        Component.translatable("tardis.message.interiorchange.subsystems_enabled", count)
                                .withStyle(ChatFormatting.RED), false);
            });
        }

        AITMod.LOGGER.info("Queueing interior change for {} to {}", this.tardis, schema);

        this.queuedInterior.set(schema.id());
        this.queued.set(true);

        TravelHandler travel = this.tardis.travel();

        if (travel.getState() == TravelHandler.State.FLIGHT && !travel.isCrashing() && !tardis.isGrowth())
            travel.crash();

        restorationChestContents = new ArrayList<>();

        for (SubSystem system : tardis.subsystems()) {
            if (!system.isReal())
                continue;

            restorationChestContents.addAll(system.toStacks());
            AITMod.LOGGER.debug("Storing Subsystem, {} ({}) => {}", system.getId(), system.isEnabled(), system.toStacks());
        }
    }

    private void changeInterior() {
        tardis.getDesktop().changeInterior(this.getQueuedInterior(), true, true)
                .thenRun(() -> {
                    this.queued.set(false);
                    this.regenerating.set(false);

                    if (tardis.hasGrowthExterior()) {
                        TravelHandler travel = tardis.travel();

                        travel.autopilot(true);

                        LockedDimension worldID = LockedDimensionRegistry.getInstance().get(travel.position().getWorld());
                        if (worldID != null) {
                            tardis.stats().unlock(worldID);
                        }

                        travel.forceDemat();
                        this.replaceAllConsolesWithGrowth();
                    } else {
                        tardis.removeFuel(MIN_FUEL_COST * tardis.travel().instability());
                    }

                    TardisUtil.sendMessageToLinked(tardis.asServer(), Component.translatable("tardis.message.interiorchange.success", tardis.stats().getName(), tardis.getDesktop().getSchema().name()));

                    this.restoreSubsystemsToConsole();
                    this.playReconfigureCompleteSound();

                    ParticleOptions particle = ParticleTypes.CLOUD;
                    tardis.door().setDoorParticles(particle);
                    tardis.door().setLocked(false);
                    Scheduler.get().runTaskLater(() -> tardis.door().setDoorParticles(null), TaskStage.END_SERVER_TICK, TimeUnit.SECONDS, 3);
                }).execute();
    }

    private void playReconfigureCompleteSound() {
        CachedDirectedGlobalPos position = tardis.travel().position();
        ServerLevel world = position.getWorld();
        BlockPos pos = position.getPos();

        world.playSound(null, pos, AITSounds.TARDIS_BLING, SoundSource.BLOCKS, 10.0F, 1.0F);
    }

    private void restoreSubsystemsToConsole() {
        if (restorationChestContents == null || restorationChestContents.isEmpty()) {
            AITMod.LOGGER.debug("No contents to save in recovery inventory in console for {}", this.tardis);
            return;
        }

        this.tardis.getDesktop().getConsolePos().stream().findFirst().ifPresent(blockPos -> {
            if (!(this.tardis.asServer().world().getBlockEntity(blockPos) instanceof ConsoleBlockEntity consoleBlockEntity))
                return;

            for (int i = 0; i < restorationChestContents.size() && i < consoleBlockEntity.getInventory().size(); i++) {
                consoleBlockEntity.getInventory().set(i, restorationChestContents.get(i));
            }
        });
    }

    private void replaceConsoleWithGrowth(BlockPos cPos) {
        ServerLevel world = tardis.asServer().world();

        if (!(world.getBlockEntity(cPos) instanceof ConsoleBlockEntity console))
            return;

        world.setBlockAndUpdate(cPos, Blocks.AIR.defaultBlockState());
        world.setBlockAndUpdate(cPos.below(), Blocks.SOUL_SAND.defaultBlockState());

        console.onBroken();
    }

    private void replaceAllConsolesWithGrowth() {
        for (BlockPos cPos : tardis.getDesktop().getConsolePos()) {
            replaceConsoleWithGrowth(cPos);
        }
    }

    @Override
    public void tick(MinecraftServer server) {
        this.tickGrowth(server);

        if (!this.queued.get())
            return;

        if (!this.canQueue()) {
            this.queued.set(false);
            this.regenerating.set(false);
            tardis.alarm().disable();
            return;
        }

        if (!TardisUtil.isInteriorEmpty(tardis.asServer())) {
            if (this.regenerating.get()) {
                Player target = TardisUtil.getAnyPlayerInsideInterior(tardis.asServer().world());

                if (this.tardis().subsystems().lifeSupport().isEnabled()) {
                    TardisUtil.teleportOutside(tardis.asServer(), target);
                } else {
                    target.hurt(AITDamageTypes.of(target.level(), AITDamageTypes.INTERIOR_CHANGE), Float.MAX_VALUE);
                }
            }

            TardisUtil.sendMessageToInterior(tardis.asServer(),
                    Component.translatable("tardis.message.interiorchange.warning").withStyle(ChatFormatting.RED));
            return;
        }

        if (!this.regenerating.get()) {
            tardis.getDesktop().startQueue(true);
            Scheduler.get().runTaskLater(this::changeInterior, TaskStage.END_SERVER_TICK, TimeUnit.SECONDS, 5);
            this.regenerating.set(true);
        }
    }

    private void tickGrowth(MinecraftServer server) {
        if (server.getTickCount() % 10 != 0 || !this.tardis.isGrowth())
            return;

        this.generateInteriorWithItem();

        if (this.queued.get())
            return;

        if (server.getTickCount() % 200 == 0 && this.hasEnoughPlasmicMaterial())
            this.tardis.asServer().world().players().forEach(player -> player.displayClientMessage(HINT_TEXT, true));

        if (this.tardis.door().isClosed()) {
            this.tardis.door().openDoors();
        } else {
            this.tardis.door().setLocked(false);
        }
    }

    public boolean hasEnoughPlasmicMaterial() {
        return this.plasmicMaterialAmount() == MAX_PLASMIC_MATERIAL_AMOUNT;
    }

    protected void generateInteriorWithItem() {
        if (!hasEnoughPlasmicMaterial()) {
            TardisUtil.sendMessageToInterior(tardis.asServer(), Component.translatable("tardis.message.interiorchange.not_enough_plasmic_material", this.plasmicMaterialAmount()).withStyle(ChatFormatting.GRAY));
            return;
        }

        TardisUtil.getEntitiesInInterior(this.tardis, 50).stream()
                .filter(entity -> entity instanceof ItemEntity item
                        && (item.getItem().getItem() == AITItems.TARDIS_MATRIX)
                        && entity.isInWater())
                .forEach(entity -> {
                    ItemEntity item = (ItemEntity) entity;
                    ItemStack stack = item.getItem();
                    DirectedGlobalPos position = this.tardis.travel().position();

                    if (position == null)
                        return;

                    this.tardis.setFuelCount(8000);

                    entity.level().playSound(null, entity.blockPosition(), SoundEvents.BEACON_POWER_SELECT,
                            SoundSource.BLOCKS, 10.0F, 0.75F);
                    entity.level().playSound(null, position.getPos(), SoundEvents.BEACON_POWER_SELECT,
                            SoundSource.BLOCKS, 10.0F, 0.75F);

                    this.queueInteriorChange(DesktopRegistry.getInstance().get(AITMod.id("cave")));

                    if (stack.is(AITItems.TARDIS_MATRIX)) {
                        CompoundTag nbt = stack.getOrCreateTag();
                        if (nbt.contains("name")) {
                            this.tardis.stats().setName(nbt.getString("name"));
                        }
                    }

                    if (this.queued.get())
                        entity.discard();
                });
    }

    private boolean canQueue() {
        return tardis.isGrowth() || tardis.fuel().hasPower() || tardis.crash().isToxic();
    }
}