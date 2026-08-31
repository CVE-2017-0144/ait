package dev.amble.ait.core.entities;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITDamageTypes;
import dev.amble.ait.core.AITEntityTypes;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.blocks.ExteriorBlock;
import dev.amble.ait.core.entities.base.LinkableDummyEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;
import dev.amble.ait.module.planet.core.util.ISpaceImmune;

public class FallingTardisEntity extends LinkableDummyEntity implements ISpaceImmune {

    private static final int HURT_MAX = 100;
    private static final float HURT_AMOUNT = 40f;

    public int timeFalling;

    @Nullable public CompoundTag blockEntityData;

    private BlockState state;



    public FallingTardisEntity(EntityType<? extends Entity> entityType, Level world) {
        super(entityType, world);
    }

    private FallingTardisEntity(Level world, Vec3 pos, BlockState state, Tardis tardis) {
        super(AITEntityTypes.FALLING_TARDIS_TYPE, world);

        this.blocksBuilding = true;
        this.state = state;

        this.link(tardis);
        this.setPos(pos.subtract(0f, 0.5f, 0f));
        this.setDeltaMovement(Vec3.ZERO);
    }

    public static void spawnFromBlock(Level world, BlockPos pos, BlockState state) {
        if (!(world.getBlockEntity(pos) instanceof ExteriorBlockEntity exterior))
            return;

        Tardis tardis = exterior.tardis().get();

        FallingTardisEntity fallingBlockEntity = new FallingTardisEntity(world, pos.getCenter(),
                state.hasProperty(BlockStateProperties.WATERLOGGED) ? state.setValue(BlockStateProperties.WATERLOGGED, false) : state, tardis);

        world.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
        world.addFreshEntity(fallingBlockEntity);
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    protected void onBelowWorld() {
        this.stopFalling(true);
    }


    @Override
    public void tick() {
        this.timeFalling++;

        if (!this.isNoGravity())
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));

        this.move(MoverType.SELF, this.getDeltaMovement());


        if (!this.isLinked())
            return;

        Tardis tardis = this.tardis().get();

        this.setDeltaMovement(this.getDeltaMovement().scale(tardis.travel().isCrashing() ? 1.05f : 0.98f));

        if (this.getY() <= (double) this.level().getMinBuildHeight() + 2)
            this.onBelowWorld();

        if (this.level().isClientSide())
            return;

        if (this.timeFalling % 20 == 0)
            tardis.getDesktop().getConsolePos().forEach(console -> this.level().playSound(null, console,
                    SoundEvents.ELYTRA_FLYING, SoundSource.BLOCKS, 0.25F, 1.0F));

        Planet planet = PlanetRegistry.getInstance().get(this.level());
        boolean cannotFall = this.tardis().get().travel().antigravs().get() || planet != null && planet.zeroGravity();
        if (cannotFall) {
            this.stopFalling(true);
            return;
        }

        BlockPos blockPos = this.blockPosition();

        if (blockPos == null)
            return;

        // If it falls on top of an exterior whose collision shape is smaller than the exterior's blockspace itself,
        // (which is the case for a siege cube exterior), then make it stop 2 blocks above, so the door won't be blocked when un-sieged.
        if (this.level().getBlockEntity(blockPos) instanceof ExteriorBlockEntity) {
            this.setPos(blockPos.getCenter().add(0, 2, 0));
            this.stopFalling(false);
            return;
        }

        tardis.travel().forcePosition(cached -> cached.pos(blockPos).world(this.level().dimension()));

        if (this.onGround())
            this.stopFalling(false);
    }

    public void stopFalling(boolean antigravs) {
        Tardis tardis = this.tardis().get();
        TravelHandler travel = tardis.travel();

        if (tardis instanceof ClientTardis)
            return;

        if (antigravs)
            travel.antigravs().set(true);

        Block block = this.state.getBlock();
        BlockPos blockPos = this.blockPosition();

        boolean isCrashing = travel.isCrashing();

        tardis.asServer().world().players().forEach(player -> {
            SoundEvent sound = isCrashing ? SoundEvents.GENERIC_EXPLODE.value() : AITSounds.LAND_CRASH;
            float volume = isCrashing ? 1.0F : 3.0F;

            player.playSound(sound, volume, 1.0f);
        });

        if (isCrashing) {
            this.level().explode(this, null, TardisUtil.EXPLOSION_BEHAVIOR, this.position(), 10, TardisUtil.doCreateFire(this.level()),
                    Level.ExplosionInteraction.TNT);

            travel.setCrashing(false);
        }

        if (this.state.hasProperty(BlockStateProperties.WATERLOGGED)
                && this.level().getFluidState(blockPos).getType() == Fluids.WATER)
            this.state = this.state.setValue(BlockStateProperties.WATERLOGGED, true);

        if (block instanceof ExteriorBlock exterior)
            exterior.onLanding(tardis, (ServerLevel) this.level(), blockPos);

        travel.placeExterior(false);
        this.discard();
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        int i = Mth.ceil(fallDistance - 1.0F);

        if (i >= 0) {
            Predicate<Entity> predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR
                    .and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
            DamageSource damageSource2 = AITDamageTypes.of(level(), AITDamageTypes.TARDIS_SQUASH_DAMAGE_TYPE);
            float f = (float) Math.min(Mth.floor((float) i * HURT_AMOUNT), HURT_MAX);

            this.level().getEntities(this, this.getBoundingBox(), predicate).forEach(entity -> {
                if (entity instanceof Shulker shulker) {
                    shulker.kill();
                }
                entity.hurt(damageSource2, f);
            });
        }

        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);

        nbt.put("BlockState", NbtUtils.writeBlockState(this.state));
        nbt.putInt("Time", this.timeFalling);

        if (this.blockEntityData != null)
            nbt.put("TileEntityData", this.blockEntityData);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);

        this.state = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK),
                nbt.getCompound("BlockState"));
        this.timeFalling = nbt.getInt("Time");

        if (nbt.contains("TileEntityData", 10))
            this.blockEntityData = nbt.getCompound("TileEntityData");

        if (this.state.isAir())
            this.state = AITBlocks.EXTERIOR_BLOCK.defaultBlockState();
    }

    public BlockState getBlockState() {
        return this.state;
    }

    @Override
    protected Component getTypeName() {
        return Component.translatable("entity.minecraft.falling_block_type", AITBlocks.EXTERIOR_BLOCK.getName());
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    @Override
    public boolean isNoGravity() {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        return new ClientboundAddEntityPacket(this, entity, Block.getId(this.getBlockState()));
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);

        this.state = Block.stateById(packet.getData());
        this.blocksBuilding = true;

        double d = packet.getX();
        double e = packet.getY();
        double f = packet.getZ();

        this.setPos(d, e, f);
    }
}
