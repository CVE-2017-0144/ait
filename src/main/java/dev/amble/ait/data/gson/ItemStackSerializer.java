package dev.amble.ait.data.gson;

import java.lang.reflect.Type;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import com.google.gson.*;

public class ItemStackSerializer implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return ItemStack.of(context.deserialize(json, CompoundTag.class));
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.save(new CompoundTag()));
    }
}
