package dev.amble.ait.data.schema.door;

import java.lang.reflect.Type;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.Vec3;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.data.schema.door.impl.CapsuleDoorVariant;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import dev.amble.lib.api.Identifiable;

/**
 * This class provides information about a door for an exterior <br>
 * <br>
 * It's information should be final and set once during creation. <br>
 * <br>
 * It should be registered in {@link DoorRegistry#OLD_REGISTRY} and only obtained
 * from there. <br>
 * <br>
 * This should be referenced by a {@link ExteriorVariantSchema} to be used
 *
 * @author duzo
 * @see DoorRegistry#OLD_REGISTRY
 */
public abstract class DoorSchema implements Identifiable {
    private final ResourceLocation id;

    protected DoorSchema(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        return o instanceof DoorSchema that && id.equals(that.id);
    }

    @Override
    public ResourceLocation id() {
        return id;
    }

    public abstract boolean isDouble();

    // fixme should this be in a "DoorSounds" type thing, also i dont like these
    // method names.
    public SoundEvent openSound() {
        return AITSounds.POLICE_BOX_DOOR_OPEN;
    }

    public SoundEvent closeSound() {
        return AITSounds.POLICE_BOX_DOOR_CLOSE;
    }

    /**
     * @deprecated {@link #getPortalPosition()}
     */
    @Deprecated(forRemoval = true)
    public Vec3 adjustPortalPos(Vec3 pos, Direction direction) {
        return pos; // just cus some dont have portals
    }

    @Nullable public Vec3 getPortalPosition() {
        return adjustPortalPos(Vec3.ZERO, Direction.NORTH);
    }

    @NotNull public Vec3 getPortalPosition(Vec3 origin, float angle) {
        Vec3 pos = getPortalPosition();
        if (pos == null) return origin;

        return pos.xRot((float) Math.toRadians(180)).yRot((float) Math.toRadians(180 - angle)).multiply(1, -1, 1).add(origin);
    }

    public static Object serializer() {
        return new Serializer();
    }

    private static class Serializer implements JsonSerializer<DoorSchema>, JsonDeserializer<DoorSchema> {

        @Override
        public DoorSchema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            ResourceLocation id;

            try {
                id = new ResourceLocation(json.getAsJsonPrimitive().getAsString());
            } catch (ResourceLocationException e) {
                id = CapsuleDoorVariant.REFERENCE;
            }

            return DoorRegistry.getInstance().get(id);
        }

        @Override
        public JsonElement serialize(DoorSchema src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.id().toString());
        }
    }
}
