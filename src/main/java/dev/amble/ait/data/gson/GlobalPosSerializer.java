package dev.amble.ait.data.gson;

import java.lang.reflect.Type;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import com.google.gson.*;

public class GlobalPosSerializer implements JsonDeserializer<GlobalPos>, JsonSerializer<GlobalPos> {

    @Override
    public GlobalPos deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        ResourceKey<Level> dimension = context.deserialize(obj.get("dimension"), ResourceKey.class);

        int x = obj.get("x").getAsInt();
        int y = obj.get("y").getAsInt();
        int z = obj.get("z").getAsInt();

        return GlobalPos.of(dimension, new BlockPos(x, y, z));
    }

    @Override
    public JsonElement serialize(GlobalPos src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();

        result.add("dimension", context.serialize(src.dimension().location()));
        result.addProperty("x", src.pos().getX());
        result.addProperty("y", src.pos().getY());
        result.addProperty("z", src.pos().getZ());

        return result;
    }
}
