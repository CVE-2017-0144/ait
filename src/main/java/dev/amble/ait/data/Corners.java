package dev.amble.ait.data;

import java.lang.reflect.Type;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import com.google.gson.*;

public class Corners {
    @Exclude
    private final AABB box;

    private final BlockPos first;
    private final BlockPos second;

    public Corners(BlockPos first, BlockPos second) {
        this.box = new AABB(Vec3.atLowerCornerOf(first), Vec3.atLowerCornerOf(second));

        this.first = first;
        this.second = second;
    }

    public AABB getBox() {
        return box;
    }

    public BlockPos getFirst() {
        return first;
    }

    public BlockPos getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Corners{" + "box=" + box + ", first=" + first + ", second=" + second + '}';
    }

    public static Object serializer() {
        return new Serializer();
    }

    public CompoundTag toNbt() {
        CompoundTag nbt = new CompoundTag();

        nbt.put("first", NbtUtils.writeBlockPos(first));
        nbt.put("second", NbtUtils.writeBlockPos(second));

        return nbt;
    }

    public static Corners fromNbt(CompoundTag nbt) {
        return new Corners(NbtUtils.readBlockPos(nbt, "first").orElseThrow(),
                NbtUtils.readBlockPos(nbt, "second").orElseThrow());
    }

    private static class Serializer implements JsonDeserializer<Corners> {

        @Override
        public Corners deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject corners = json.getAsJsonObject();

            return new Corners(context.deserialize(corners.get("first"), BlockPos.class),
                    context.deserialize(corners.get("second"), BlockPos.class));
        }
    }
}
