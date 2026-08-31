package dev.amble.lib.data;

import java.lang.reflect.Type;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class DirectedGlobalPos {

    public static final Codec<DirectedGlobalPos> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(Level.RESOURCE_KEY_CODEC.fieldOf("dimension").forGetter(DirectedGlobalPos::getDimension),
                    BlockPos.CODEC.fieldOf("pos").forGetter(DirectedGlobalPos::getPos),
                    Codec.BYTE.fieldOf("rotation").forGetter(DirectedGlobalPos::getRotation))
            .apply(instance, DirectedGlobalPos::create));

    private final ResourceKey<Level> dimension;
    private final BlockPos pos;
    private final byte rotation;

    protected DirectedGlobalPos(ResourceKey<Level> dimension, BlockPos pos, byte rotation) {
        this.dimension = dimension;
        this.pos = pos;

        this.rotation = rotation;
    }

    public DirectedGlobalPos pos(int x, int y, int z) {
        return this.pos(new BlockPos(x, y, z));
    }

    public DirectedGlobalPos pos(BlockPos pos) {
        return DirectedGlobalPos.create(this.dimension, pos, this.rotation);
    }

    public DirectedGlobalPos offset(int x, int y, int z) {
        return DirectedGlobalPos.create(this.dimension, this.pos.offset(x, y, z), this.rotation);
    }
    public DirectedGlobalPos offset(Direction dir) {
        return DirectedGlobalPos.create(this.dimension, this.pos.relative(dir), this.rotation);
    }

    public DirectedGlobalPos rotation(byte rotation) {
        return DirectedGlobalPos.create(this.dimension, this.pos, rotation);
    }

    public DirectedGlobalPos world(ResourceKey<Level> world) {
        return DirectedGlobalPos.create(world, this.pos, this.rotation);
    }

    public static DirectedGlobalPos create(ResourceKey<Level> dimension, BlockPos pos, byte rotation) {
        return new DirectedGlobalPos(dimension, pos, rotation);
    }

    public ResourceKey<Level> getDimension() {
        return this.dimension;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public byte getRotation() {
        return this.rotation;
    }
    public float getRotationDegrees() {
        return RotationSegment.convertToDegrees(this.getRotation());
    }
    public Direction getRotationDirection() {
        return Direction.fromYRot(this.getRotationDegrees());
    }

    public Vec3i getVector() {
        return switch (this.rotation) {
            case 0 -> Direction.NORTH.getNormal();
            case 1, 2, 3 -> Direction.NORTH.getNormal().offset(Direction.EAST.getNormal());
            case 4 -> Direction.EAST.getNormal();
            case 5, 6, 7 -> Direction.EAST.getNormal().offset(Direction.SOUTH.getNormal());
            case 8 -> Direction.SOUTH.getNormal();
            case 9, 10, 11 -> Direction.SOUTH.getNormal().offset(Direction.WEST.getNormal());
            case 12 -> Direction.WEST.getNormal();
            case 13, 14, 15 -> Direction.NORTH.getNormal().offset(Direction.SOUTH.getNormal());
            default -> new Vec3i(0, 0, 0);
        };
    }

    public DistanceInformation distanceTo(DirectedGlobalPos other) {
        double distance = Math.sqrt(this.pos.distSqr(other.pos));
        boolean dimChange = !this.dimension.equals(other.dimension);
        boolean rotChange = this.rotation != other.rotation;

        return new DistanceInformation(distance, dimChange, rotChange);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof DirectedGlobalPos globalPos))
            return false;

        return Objects.equals(this.dimension, globalPos.dimension) && Objects.equals(this.pos, globalPos.pos)
                && Objects.equals(this.rotation, globalPos.rotation);
    }

    public int hashCode() {
        return Objects.hash(this.dimension, this.pos, this.rotation);
    }

    public String toString() {
        return this.dimension + " " + this.pos + " " + this.rotation;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeResourceKey(this.dimension);
        buf.writeBlockPos(this.pos);
        buf.writeByte(this.rotation);
    }

    public static DirectedGlobalPos read(FriendlyByteBuf buf) {
        ResourceKey<Level> registryKey = buf.readResourceKey(Registries.DIMENSION);
        BlockPos blockPos = buf.readBlockPos();
        byte rotation = buf.readByte();

        return DirectedGlobalPos.create(registryKey, blockPos, rotation);
    }

    public CompoundTag toNbt() {
        CompoundTag compound = NbtUtils.writeBlockPos(this.pos);
        compound.putString("dimension", this.dimension.location().toString());
        compound.putByte("rotation", this.rotation);

        return compound;
    }

    public static DirectedGlobalPos fromNbt(CompoundTag compound) {
        BlockPos pos = NbtUtils.readBlockPos(compound);
        ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION,
                new ResourceLocation(compound.getString("dimension")));

        byte rotation = compound.getByte("rotation");
        return DirectedGlobalPos.create(dimension, pos, rotation);
    }

    // TODO: make directedglobalpos use directedblockpos
    public DirectedBlockPos toPos() {
        return DirectedBlockPos.create(this.pos, this.rotation);
    }

    public static int getNextGeneralizedRotation(int rotation) {
        return (rotation + 2) % 16;
    }

    public static int getPreviousGeneralizedRotation(int rotation) {
        return (rotation - 2) % 16;
    }

    public static byte getGeneralizedRotation(int rotation) {
        if (rotation % 2 != 0 && rotation < 15)
            return (byte) (rotation + 1);

        if (rotation == 15)
            return 0;

        return (byte) rotation;
    }
    public static byte getGeneralizedRotation(Direction dir) {
        return getGeneralizedRotation(RotationSegment.convertToSegment(dir));
    }

    public static String rotationForArrow(int currentRot) {
        return switch (currentRot) {
            case 1, 2, 3 -> "↗";
            case 4 -> "→";
            case 5, 6, 7 -> "↘";
            case 8 -> "↓";
            case 9, 10, 11 -> "↙";
            case 12 -> "←";
            case 13, 14, 15 -> "↖";
            default -> "↑";
        };
    }

    public static byte wrap(byte value, byte max) {
        return (byte) ((value % max + max) % max);
    }

    public static Object serializer() {
        return new Serializer();
    }

    private static class Serializer implements JsonDeserializer<DirectedGlobalPos>, JsonSerializer<DirectedGlobalPos> {

        @Override
        public DirectedGlobalPos deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();

            ResourceKey<Level> dimension = context.deserialize(obj.get("dimension"), ResourceKey.class);

            int x = obj.get("x").getAsInt();
            int y = obj.get("y").getAsInt();
            int z = obj.get("z").getAsInt();
            byte rotation = obj.get("rotation").getAsByte();

            return DirectedGlobalPos.create(dimension, new BlockPos(x, y, z), rotation);
        }

        @Override
        public JsonElement serialize(DirectedGlobalPos src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();

            result.addProperty("dimension", src.getDimension().location().toString());
            result.addProperty("x", src.getPos().getX());
            result.addProperty("y", src.getPos().getY());
            result.addProperty("z", src.getPos().getZ());
            result.addProperty("rotation", src.getRotation());

            return result;
        }
    }
}
