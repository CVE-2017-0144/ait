package dev.amble.ait.core.portal;

import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.Vec3;

import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.TardisDesktop;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.data.DirectedBlockPos;

public final class PortalPair {

    public static final class Side {

        private final ServerLevel level;
        private final BlockPos pos;
        private final byte rotation;
        private final float yaw;
        private final Vec3 outwardNormal;
        private final Vec3 anchor;
        private final ChunkPos chunk;

        private Side(ServerLevel level, BlockPos pos, byte rotation, float yaw) {
            this.level = level;
            this.pos = pos;
            this.rotation = rotation;
            this.yaw = Mth.wrapDegrees(yaw);
            this.outwardNormal = fromYaw(this.yaw);
            this.anchor = pos.getCenter();
            this.chunk = new ChunkPos(pos);
        }

        private static Side interior(ServerLevel level, DirectedBlockPos door) {
            return new Side(level, door.getPos(), door.getRotation(),
                    RotationSegment.convertToDegrees(door.getRotation()));
        }

        private static Side exterior(ServerLevel level, CachedDirectedGlobalPos exterior) {
            return new Side(level, exterior.getPos(), exterior.getRotation(),
                    RotationSegment.convertToDegrees(exterior.getRotation()) + 180f);
        }

        public ServerLevel level() {
            return this.level;
        }

        public ResourceKey<Level> dimension() {
            return this.level.dimension();
        }

        public BlockPos pos() {
            return this.pos;
        }

        public byte rotation() {
            return this.rotation;
        }

        public float yaw() {
            return this.yaw;
        }

        public Vec3 outwardNormal() {
            return this.outwardNormal;
        }

        public Vec3 anchor() {
            return this.anchor;
        }

        public ChunkPos chunk() {
            return this.chunk;
        }

        @Override
        public String toString() {
            return this.level.dimension().location() + " " + this.pos + " rot " + this.rotation;
        }
    }

    private final ServerTardis tardis;
    private final Side interior;
    private final Side exterior;

    private final float yawDelta;
    private final double sin;
    private final double cos;

    private PortalPair(ServerTardis tardis, Side interior, Side exterior) {
        this.tardis = tardis;
        this.interior = interior;
        this.exterior = exterior;

        this.yawDelta = Mth.wrapDegrees(exterior.yaw() - interior.yaw() - 180f);

        double radians = Math.toRadians(this.yawDelta);
        this.sin = Math.sin(radians);
        this.cos = Math.cos(radians);
    }

    public static @Nullable PortalPair resolve(ServerTardis tardis) {
        if (tardis.isRemoved() || tardis.travel().inFlight())
            return null;

        TardisDesktop desktop = tardis.getDesktop();
        DirectedBlockPos door = desktop.getDoorPos();

        BlockPos interiorPos = door.getPos();

        if (interiorPos.equals(BlockPos.ZERO) || desktop.getConsolePos().contains(interiorPos))
            return null;

        CachedDirectedGlobalPos exterior = tardis.travel().position();

        if (exterior == null)
            return null;

        ServerLevel exteriorLevel = exterior.getWorld();

        if (exteriorLevel == null)
            return null;

        ServerLevel interiorLevel = tardis.world();

        if (interiorLevel == null || interiorLevel == exteriorLevel)
            return null;

        return new PortalPair(tardis, Side.interior(interiorLevel, door), Side.exterior(exteriorLevel, exterior));
    }

    public ServerTardis tardis() {
        return this.tardis;
    }

    public Side interior() {
        return this.interior;
    }

    public Side exterior() {
        return this.exterior;
    }

    public Side opposite(Side side) {
        return side == this.interior ? this.exterior : this.interior;
    }

    public @Nullable Side sideAt(ServerLevel level, BlockPos pos) {
        if (this.interior.level() == level && this.interior.pos().equals(pos))
            return this.interior;

        if (this.exterior.level() == level && this.exterior.pos().equals(pos))
            return this.exterior;

        return null;
    }

    public float yawDelta(Side from) {
        return from == this.interior ? this.yawDelta : Mth.wrapDegrees(-this.yawDelta);
    }

    public Vec3 toFar(Side from, Vec3 pos) {
        Side to = this.opposite(from);

        double dx = pos.x - from.anchor().x;
        double dz = pos.z - from.anchor().z;

        double s = from == this.interior ? this.sin : -this.sin;

        return new Vec3(to.anchor().x + dx * this.cos - dz * s, to.anchor().y + (pos.y - from.anchor().y),
                to.anchor().z + dx * s + dz * this.cos);
    }

    private static Vec3 fromYaw(float yaw) {
        double radians = Math.toRadians(yaw);
        return new Vec3(-Math.sin(radians), 0, Math.cos(radians));
    }

    static {
        for (Direction facing : Direction.Plane.HORIZONTAL) {
            Vec3 normal = fromYaw(RotationSegment.convertToDegrees(RotationSegment.convertToSegment(facing)));

            if (normal.distanceToSqr(Vec3.atLowerCornerOf(facing.getNormal())) > 1.0E-6)
                throw new IllegalStateException("RotationSegment no longer yields a yaw for " + facing);
        }
    }
}
