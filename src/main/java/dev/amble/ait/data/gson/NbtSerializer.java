package dev.amble.ait.data.gson;

import java.lang.reflect.Type;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTagVisitor;
import net.minecraft.nbt.TagParser;
import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.amble.ait.AITMod;

public class NbtSerializer implements JsonSerializer<CompoundTag>, JsonDeserializer<CompoundTag> {
    @Override
    public CompoundTag deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            return TagParser.parseTag(json.getAsString());
        } catch (CommandSyntaxException e) {
            AITMod.LOGGER.error("Invalid NBT string: {}", json.getAsString());
            return new CompoundTag();
        }
    }

    @Override
    public JsonElement serialize(CompoundTag src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(new StringTagVisitor().visit(src));
    }
}
