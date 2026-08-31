package dev.amble.ait.data.gson;

import java.lang.reflect.Type;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import com.google.gson.*;

public class RegistryKeySerializer implements JsonSerializer<ResourceKey<?>>, JsonDeserializer<ResourceKey<?>> {

    @Override
    public ResourceKey<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        ResourceLocation registry = context.deserialize(object.get("registry"), ResourceLocation.class);
        ResourceLocation value = context.deserialize(object.get("value"), ResourceLocation.class);

        return ResourceKey.create(ResourceKey.createRegistryKey(registry), value);
    }

    @Override
    public JsonElement serialize(ResourceKey<?> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("registry", context.serialize(src.registry()));
        object.add("value", context.serialize(src.location()));

        return object;
    }
}
