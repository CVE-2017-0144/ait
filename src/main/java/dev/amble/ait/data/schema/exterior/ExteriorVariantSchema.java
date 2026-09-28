package dev.amble.ait.data.schema.exterior;

import java.lang.reflect.Type;
import java.util.Optional;

import com.google.gson.*;
import dev.amble.ait.AITMod;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.BasicSchema;
import dev.amble.ait.data.schema.door.DoorSchema;
import dev.amble.ait.registry.impl.CategoryRegistry;
import dev.amble.ait.registry.impl.exterior.ClientExteriorVariantRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import dev.amble.lib.register.unlockable.Unlockable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A variant for a {@link ExteriorCategorySchema} which provides a model,
 * texture, emission, and {@link DoorSchema} <br>
 * <br>
 * This should be registered in {@link ExteriorVariantRegistry} <br>
 * <br>
 * This should <b>ONLY</b> be created once in registry, you should grab the
 * class via {@link ExteriorVariantRegistry#get(ResourceLocation)}, the identifier
 * being this variants id variable. <br>
 * <br>
 * It is recommended for implementations of this class to have a static
 * "REFERENCE" {@link ResourceLocation} variable which other things can use to get
 * this from the {@link ExteriorVariantRegistry}
 *
 * @author duzo
 * @see ExteriorVariantRegistry
 */
public abstract class ExteriorVariantSchema extends BasicSchema implements Unlockable {
    private final ResourceLocation category;
    private final ResourceLocation id;
    private final Loyalty loyalty;

    public static final double DEFAULT_SEAT_FORWARD_TRANSLATION = 0.5;
    public static final Vec3 DEFAULT_SEAT_POS = new Vec3(0.5, 1, 0.5);
    @OnlyIn(Dist.CLIENT)
    private ClientExteriorVariantSchema cachedSchema;

    protected ExteriorVariantSchema(ResourceLocation category, ResourceLocation id, Optional<Loyalty> loyalty) {
        super("exterior");
        this.category = category;

        this.id = id;
        this.loyalty = loyalty.orElse(null);
    }

    protected ExteriorVariantSchema(ResourceLocation category, ResourceLocation id, Loyalty loyalty) {
        this(category, id, Optional.of(loyalty));
    }
    protected ExteriorVariantSchema(ResourceLocation category, ResourceLocation id) {
        this(category, id, Optional.empty());
    }

    public static Object serializer() {
        return new Serializer();
    }

    @Override
    public ResourceLocation id() {
        return id;
    }

    @Override
    public Optional<Loyalty> requirement() {
        return Optional.ofNullable(loyalty);
    }

    @Override
    public UnlockType unlockType() {
        return UnlockType.EXTERIOR;
    }

    public ResourceLocation categoryId() {
        return this.category;
    }

    public abstract Vec3 seatTranslations();

    public double seatForwardTranslation() {
        return DEFAULT_SEAT_FORWARD_TRANSLATION;
    }

    public ExteriorCategorySchema category() {
        return CategoryRegistry.getInstance().get(this.categoryId());
    }

    @OnlyIn(Dist.CLIENT)
    public ClientExteriorVariantSchema getClient() {
        if (this.cachedSchema == null)
            this.cachedSchema = ClientExteriorVariantRegistry.withParent(this);

        return cachedSchema;
    }

    public VoxelShape bounding(Direction dir) {
        return null;
    }

    public abstract DoorSchema door();

    public boolean hasPortals() {
        return this.category().hasPortals();
    }

    /**
     * @deprecated {@link #getPortalPosition()}
     */
    @Deprecated(forRemoval = true)
    public Vec3 adjustPortalPos(Vec3 pos, byte direction) {
        return pos; // just cus some dont have portals
    }

    @Nullable public Vec3 getPortalPosition() {
        return adjustPortalPos(Vec3.ZERO, (byte) 0);
    }

    @NotNull public Vec3 getPortalPosition(Vec3 origin, float angle) {
        Vec3 pos = getPortalPosition();
        if (pos == null) return origin;

        return pos.xRot((float) Math.toRadians(180)).yRot((float) Math.toRadians(180 - angle)).multiply(1, -1, 1).add(origin);
    }

    public double portalWidth() {
        return 1d;
    }

    public double portalHeight() {
        return 2d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        return o instanceof ExteriorVariantSchema other && id.equals(other.id);
    }

    private static class Serializer
            implements
            JsonSerializer<ExteriorVariantSchema>,
            JsonDeserializer<ExteriorVariantSchema> {

        @Override
        public ExteriorVariantSchema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            ResourceLocation id;

            try {
                id = ResourceLocation.parse(json.getAsJsonPrimitive().getAsString());
            } catch (ResourceLocationException e) {
                id = AITMod.id("capsule_default");
            }

            return ExteriorVariantRegistry.getInstance().get(id);
        }

        @Override
        public JsonElement serialize(ExteriorVariantSchema src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.id().toString());
        }
    }
}
