package dev.amble.ait.core.entities;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.link.LinkableLivingEntity;
import dev.amble.ait.client.util.ClientShakeUtil;
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.AITEntityTypes;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.TardisDesktop;
import dev.amble.ait.core.tardis.control.impl.DirectionControl;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.mixin.rwf.LivingEntityAccessor;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class FlightTardisEntity extends LinkableLivingEntity implements PlayerRideableJumping {

    private static final List<ItemStack> EMPTY = List.of();
    private static final ItemStack AIR = new ItemStack(Items.AIR);
    public float speedPitch;
    private Vec3 lastVelocity;
    private BlockPos interiorPos;

    public FlightTardisEntity(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);

        this.setInvulnerable(true);
        this.lastVelocity = Vec3.ZERO;
    }

    private FlightTardisEntity(BlockPos riderPos, CachedDirectedGlobalPos pos, ServerTardis tardis) {
        this(AITEntityTypes.FLIGHT_TARDIS_TYPE, pos.getWorld());

        this.interiorPos = riderPos;

        this.link(tardis);
        this.setPos(pos.getPos().getCenter());
        this.setDeltaMovement(Vec3.ZERO);

        this.setRot(RotationSegment.convertToDegrees(
                DirectionControl.getGeneralizedRotation(pos.getRotation())
        ), 0);
    }

    public static FlightTardisEntity createAndSpawn(ServerPlayer player, ServerTardis tardis) {
        CachedDirectedGlobalPos exteriorPos = tardis.travel().position();

        FlightTardisEntity entity = new FlightTardisEntity(
                player.blockPosition(), exteriorPos, tardis
        );

        exteriorPos.getWorld().addFreshEntity(entity);
        return entity;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected float getFlyingSpeed() {
        if (this.isLinked()  && this.tardis().get().travel() != null) {
            float spaceSpeed = this.level().dimension().equals(AITDimensions.SPACE) ? 0.1f : 0.05f;
            return this.getSpeed() * (this.tardis().get().travel().speed() * spaceSpeed);
        }
        return super.getFlyingSpeed();
    }

    @Override
    public void tick() {
        this.lastVelocity = this.getDeltaMovement();
        this.setRot(0, 0);
        super.tick();

        Player player = this.getPlayer();

        if (player == null)
            return;

        if (!this.isLinked())
            return;

        Tardis tardis = this.tardis().get();

        if (player.isShiftKeyDown() && (this.onGround() || tardis.travel().antigravs().get())
                && this.level().isInWorldBounds(this.blockPosition()))
            this.finishLand(tardis, player);

        if (this.level().isClientSide()) {
            Minecraft client = Minecraft.getInstance();

            if (client.player == this.getControllingPassenger()) {
                client.options.setCameraType(CameraType.THIRD_PERSON_BACK);

                if (!this.verticalCollisionBelow)
                    ClientShakeUtil.shake((float) (tardis.travel().speed() + this.getDeltaMovement().horizontalDistance()) / tardis.travel().maxSpeed().get());
            }

            return;
        }

        if (!player.isInvisible())
            player.setInvisible(true);

        if (!player.isInvulnerable())
            player.setInvulnerable(true);

        tardis.flight().tickFlight((ServerPlayer) player);

        if (tardis.door().isOpen()) {
            this.level().getEntities(this, this.getBoundingBox(), entity
                    -> !entity.isSpectator() && entity != player && entity instanceof LivingEntity).forEach(
                    entity -> TardisUtil.teleportInside(tardis.asServer(), entity)
            );
        }
    }

    @Override
    public void setOnGroundWithMovement(boolean onGround, Vec3 movement) {
        if (!this.onGround() && onGround)
            this.playThud();

        super.setOnGroundWithMovement(onGround, movement);
    }

    @Override
    public void setOnGround(boolean onGround) {
        if (!this.onGround() && onGround)
            this.playThud();

        super.setOnGround(onGround);
    }

    private void playThud() {
        this.level().playSound(null, this.blockPosition(), AITSounds.LAND_THUD, SoundSource.BLOCKS, 2F, 1F / (AITMod.RANDOM.nextFloat() * 0.4F + 0.8F));
    }

    private void finishLand(Tardis tardis, Player player) {
        if (this.level().isClientSide()) {
            Minecraft client = Minecraft.getInstance();
            client.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            client.options.hideGui = false;
            return;
        }

        if (!(player instanceof ServerPlayer serverPlayer))
            return;

        if (this.interiorPos == null) {
            TardisUtil.teleportInside(tardis.asServer(), serverPlayer);
        } else {
            TardisUtil.teleportToInteriorPosition(tardis.asServer(), serverPlayer, this.interiorPos);
        }

        tardis.flight().exitFlight(serverPlayer);
        tardis.travel().speed(0);
        this.discard();
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return EMPTY;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return AIR;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) { }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public Player getPlayer() {
        if (this.getControllingPassenger() instanceof Player player)
            return player;

        return null;
    }

    @Nullable @Override
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();

        if (entity instanceof LivingEntity living)
            return living;

        return null;
    }

    @Override
    protected Vec3 getRiddenInput(Player controllingPlayer, Vec3 movementInput) {
        if (!this.isLinked() || !this.tardis().get().fuel().hasPower()) return new Vec3(0, 0, 0);
        float f = controllingPlayer.xxa * this.tardis().get().travel().speed();
        float g = controllingPlayer.zza * this.tardis().get().travel().speed();

        float speedVal = this.isUnderWater() ? 30f : 10f;

        Planet planet = PlanetRegistry.getInstance().get(this.level());
        boolean canFall = this.tardis().get().travel().antigravs().get() || planet != null && planet.zeroGravity();

        double v = ((LivingEntityAccessor) controllingPlayer).getJumping() ? speedVal :
                controllingPlayer.isShiftKeyDown() ? -speedVal :
                        canFall ? 0.0f : f > 0 || g > 0 ? -0.5f : -2f;

        if (v < 0 && this.onGround())
            return Vec3.ZERO.add(0, -0.4f, 0);
        Vec3 yourmom = new Vec3(f, v, g);
        return yourmom;//return this.isOnGround() ? new Vec3d(0, 0, 0) : new Vec3d(f, v * 4f, g);
    }

    @Override
    protected float getRiddenSpeed(Player controllingPlayer) {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    @Override
    public double getPassengersRidingOffsetUnused() {
        return 0.5f;
    }

    public float getRotation(float tickDelta) {
        return ((float) this.tickCount + tickDelta) / 20.0f;
    }

    public Vec3 lerpVelocity(float tickDelta) {
        return this.lastVelocity.lerp(this.getDeltaMovement(), tickDelta);
    }

    @Override
    protected void tickRidden(Player controllingPlayer, Vec3 movementInput) {
        Vec2 vec2f = new Vec2(0, controllingPlayer.getYRot());
        this.setRot(vec2f.y, vec2f.x);

        this.setYBodyRot(180.0f - this.getRotation(0.5f) / (float) Math.PI * 180f);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);

        if (nbt.contains("InteriorPos")) {
            this.interiorPos = BlockPos.of(nbt.getLong("InteriorPos"));
        } else {
            this.interiorPos = BlockPos.ZERO;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (this.level().isClientSide()) return;
        if (!this.isLinked()) return;


        TardisDesktop desktop = tardis().get().getDesktop();

        if (interiorPos == null)
            interiorPos = desktop.getConsolePos().iterator().next();

        if (interiorPos == null)
            interiorPos = desktop.getDoorPos().getPos();

        if (interiorPos == null)
            interiorPos = new BlockPos(0, 0, 0);

        nbt.putLong("InteriorPos", interiorPos.asLong());
    }

    public static AttributeSupplier.Builder createDummyAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 1)
                .add(Attributes.MAX_HEALTH, 20.0).add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.FLYING_SPEED, 5);
    }

    @Override
    public void onPlayerJump(int strength) {
    }

    @Override
    public boolean canJump() {
        return false;
    }

    @Override
    public void handleStartJump(int height) { }

    @Override
    public void handleStopJump() { }
}
