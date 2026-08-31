package dev.amble.ait.data.gson;

import java.lang.reflect.Type;
import net.minecraft.resources.ResourceLocation;
import com.google.gson.*;

/**
 * A more compact identifier serializer.
 */
public class IdentifierSerializer implements JsonSerializer<ResourceLocation>, JsonDeserializer<ResourceLocation> {

    @Override
    public ResourceLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return ResourceLocation.tryParse(json.getAsString());
    }

    @Override
    public JsonElement serialize(ResourceLocation src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
