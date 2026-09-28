package dev.amble.ait.core.entities;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.module.planet.core.util.ISpaceImmune;
import org.jetbrains.annotations.Nullable;

public abstract class BOTIPaintingEntity extends HangingEntity implements ISpaceImmune {
    private static final int WIDTH = 48;
    private static final int HEIGHT = 32;

    public BOTIPaintingEntity(EntityType<? extends BOTIPaintingEntity> entityType, Level world) {
        super(entityType, world);
    }


    public static Optional<BOTIPaintingEntity> placePainting(EntityType<? extends BOTIPaintingEntity> entityType, Level world, BlockPos pos, Direction facing) {
        BOTIPaintingEntity paintingEntity = entityType.create(world);

        if (paintingEntity == null) return Optional.empty();
        paintingEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
        paintingEntity.setDirection(facing);

        if (paintingEntity.survives()) {
            return Optional.of(paintingEntity);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putByte("facing", (byte) this.direction.get2DDataValue());
        super.addAdditionalSaveData(nbt);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        this.direction = Direction.from2DDataValue(nbt.getByte("facing"));
        super.readAdditionalSaveData(nbt);
        this.setDirection(this.direction);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    protected AABB calculateBoundingBox(BlockPos pos, Direction direction) {
        double half = 0.46875;
        double x = pos.getX() + 0.5 - direction.getStepX() * half;
        double y = pos.getY() + 1.0;
        double z = pos.getZ() + 0.5 - direction.getStepZ() * half;

        double w = (direction.getAxis() == Direction.Axis.Z ? WIDTH : 1.0) / 32.0;
        double d = (direction.getAxis() == Direction.Axis.Z ? 1.0 : WIDTH) / 32.0;

        return new AABB(x - w, y - HEIGHT / 32.0, z - d, x + w, y + HEIGHT / 32.0, z + d);
    }

    @Override
    public void dropItem(@Nullable Entity entity) {

    }

    @Override
    public void moveTo(double x, double y, double z, float yaw, float pitch) {
        this.setPos(x, y, z);
    }

    @Override
    public void lerpTo(double x, double y, double z, float yaw, float pitch, int interpolationSteps) {
        this.setPos(x, y, z);
    }

    @Override
    public Vec3 trackingPosition() {
        return Vec3.atLowerCornerOf(this.pos);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        return new ClientboundAddEntityPacket(this, this.direction.get3DDataValue(), this.getPos());
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.setDirection(Direction.from3DDataValue(packet.getData()));
    }

    @Override
    public void playPlacementSound() {
        this.playSound(SoundEvents.PAINTING_PLACE, 1.0f, 1.0f);
    }
}
