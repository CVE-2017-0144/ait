package dev.amble.ait.data.schema.exterior;

import java.lang.reflect.Type;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import com.google.gson.*;
import dev.amble.ait.data.schema.BasicSchema;
import dev.amble.ait.data.schema.exterior.category.CapsuleCategory;
import dev.amble.ait.registry.impl.CategoryRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;

/**
 * @author duzo
 */
public abstract class ExteriorCategorySchema extends BasicSchema {
    private final ResourceLocation id;

    protected ExteriorCategorySchema(ResourceLocation id, String name) {
        super("exterior");
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        return o instanceof ExteriorCategorySchema other && id.equals(other.id);
    }

    @Override
    public ResourceLocation id() {
        return this.id;
    }

    /**
     * The default exterior for this category
     */
    public ExteriorVariantSchema getDefaultVariant() {
        return ExteriorVariantRegistry.withParent(this).get(0);
    }

    @Deprecated // Replace with the exteriors own hasPortals method, they need to override it
    public boolean hasPortals() {
        return false;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static Object serializer() {
        return new Serializer();
    }

    private static class Serializer
            implements
                JsonSerializer<ExteriorCategorySchema>,
                JsonDeserializer<ExteriorCategorySchema> {

        @Override
        public ExteriorCategorySchema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            ResourceLocation id;

            try {
                id = new ResourceLocation(json.getAsJsonPrimitive().getAsString());
            } catch (ResourceLocationException e) {
                id = CapsuleCategory.REFERENCE;
            }

            return CategoryRegistry.getInstance().get(id);
        }

        @Override
        public JsonElement serialize(ExteriorCategorySchema src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.id().toString());
        }
    }
}
