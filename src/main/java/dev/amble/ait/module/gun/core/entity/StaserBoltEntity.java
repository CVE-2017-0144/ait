package dev.amble.ait.module.gun.core.entity;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.devteam.DevTeam;
import dev.amble.ait.module.gun.core.item.GunItems;
import dev.amble.ait.module.planet.core.util.ISpaceImmune;

public class StaserBoltEntity extends AbstractArrow implements ISpaceImmune {
    public StaserBoltEntity(EntityType<? extends StaserBoltEntity> entityType, Level world) {
        super(GunEntityTypes.STASER_BOLT_ENTITY_TYPE, world);
    }

    private StaserBoltEntity(Level world, double x, double y, double z) {
        super(GunEntityTypes.STASER_BOLT_ENTITY_TYPE, x, y, z, world,
                new ItemStack(GunItems.STASER_BOLT_MAGAZINE), ItemStack.EMPTY);
    }

    private StaserBoltEntity(Level world, LivingEntity shooter) {
        super(GunEntityTypes.STASER_BOLT_ENTITY_TYPE, shooter, world,
                new ItemStack(GunItems.STASER_BOLT_MAGAZINE), ItemStack.EMPTY);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(GunItems.STASER_BOLT_MAGAZINE);
    }

    // this exists because I forgot how to do the constructors so the registry doesn't scream at me - Loqor
    public StaserBoltEntity createFromConstructor(Level world, LivingEntity shooter) {
        return new StaserBoltEntity(world, shooter);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(GunItems.STASER_BOLT_MAGAZINE);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void onHit(HitResult hitResult) {
        // Handle block collisions and block breaking safely
        if (hitResult.getType() == HitResult.Type.BLOCK && this.level() instanceof ServerLevel world) {
            boolean allowGriefing = world.getServer().getGameRules().getBoolean(AITMod.STASER_GRIEFING);
            BlockHitResult result = (BlockHitResult) hitResult;
            Block block = this.level().getBlockState(result.getBlockPos()).getBlock();
            if (allowGriefing && (block instanceof IceBlock || block instanceof LanternBlock || block instanceof TorchBlock || this.level().getBlockState(result.getBlockPos()).canBeReplaced() || block instanceof TransparentBlock || block instanceof IronBarsBlock || block instanceof StainedGlassBlock)) {
                this.level().destroyBlock(result.getBlockPos(), false);
            }
            this.level().playSound(null, result.getBlockPos(), AITSounds.STASER, SoundSource.BLOCKS, 0.25f, 0.5f);
            this.remove(RemovalReason.DISCARDED);
        }

        super.onHit(hitResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (!this.level().isClientSide() && this.getOwner() instanceof Player player) {

            if (player.getMainHandItem().getItem() == GunItems.CULT_STASER_RIFLE) {
                if (DevTeam.isDev(player.getUUID())) {
                    this.setBaseDamage(250d);
                } else {
                    this.setBaseDamage(10d);
                }
            } else {
                this.setBaseDamage(6.5d);
            }
        }
        super.onHitEntity(entityHitResult);
    }

    @Override
    public void tick() {
        Vec3 vec3d2;
        VoxelShape voxelShape;
        super.tick();
        Vec3 vec3d = this.getDeltaMovement();
        if (this.xRotO == 0.0f && this.yRotO == 0.0f) {
            double d = vec3d.horizontalDistance();
            this.setYRot((float)(Mth.atan2(vec3d.x, vec3d.z) * 57.2957763671875));
            this.setXRot((float)(Mth.atan2(vec3d.y, d) * 57.2957763671875));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
        BlockPos blockPos = this.blockPosition();
        BlockState blockState = this.level().getBlockState(blockPos);
        if (!(blockState.isAir() || (voxelShape = blockState.getCollisionShape(this.level(), blockPos)).isEmpty())) {
            vec3d2 = this.position();
            for (AABB box : voxelShape.toAabbs()) {
                if (!box.move(blockPos).contains(vec3d2)) continue;
                this.inGround = true;
                break;
            }
        }
        this.inGroundTime = 0;
        Vec3 vec3d3 = this.position();
        vec3d2 = vec3d3.add(vec3d);
        HitResult hitResult = this.level().clip(new ClipContext(vec3d3, vec3d2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (hitResult.getType() != HitResult.Type.MISS) {
            vec3d2 = hitResult.getLocation();
        }
        while (!this.isRemoved()) {
            EntityHitResult entityHitResult = this.findHitEntity(vec3d3, vec3d2);
            if (entityHitResult != null) {
                hitResult = entityHitResult;
            }
            if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                Entity entity = ((EntityHitResult)hitResult).getEntity();
                Entity entity2 = this.getOwner();
                if (entity instanceof Player && entity2 instanceof Player && !((Player)entity2).canHarmPlayer((Player)entity)) {
                    hitResult = null;
                    entityHitResult = null;
                }
            }
            if (hitResult != null) {
                this.onHit(hitResult);
                this.hasImpulse = true;
            }
            if (entityHitResult == null || this.getPierceLevel() <= 0) break;
            hitResult = null;
        }
        vec3d = this.getDeltaMovement();
        double e = vec3d.x;
        double f = vec3d.y;
        double g = vec3d.z;
        double h = this.getX() + e;
        double j = this.getY() + f;
        double k = this.getZ() + g;
        double l = vec3d.horizontalDistance();
        this.setYRot((float)(Mth.atan2(e, g) * 57.2957763671875));
        this.setXRot((float)(Mth.atan2(f, l) * 57.2957763671875));
        this.setXRot(AbstractArrow.lerpRotation(this.xRotO, this.getXRot()));
        this.setYRot(AbstractArrow.lerpRotation(this.yRotO, this.getYRot()));
        if (this.isInWater()) {
            for (int o = 0; o < 4; ++o) {
                this.level().addParticle(ParticleTypes.BUBBLE, h - e * 0.25, j - f * 0.25, k - g * 0.25, e, f, g);
            }
        }
        this.setDeltaMovement(vec3d);
        if (!this.isNoGravity()) {
            Vec3 vec3d4 = this.getDeltaMovement();
            this.setDeltaMovement(vec3d4.x, vec3d4.y - (double)0.05f, vec3d4.z);
        }
        this.setPos(h, j, k);
        this.checkInsideBlocks();

        if (this.tickCount > 100) {
            this.discard();
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.CANDLE_EXTINGUISH;
    }
}
