package dev.amble.ait.data.gson;

import java.lang.reflect.Type;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import com.google.gson.*;
import dev.amble.lib.util.ServerLifecycleHooks;

public class ItemStackSerializer implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return ItemStack.parseOptional(registries(), context.deserialize(json, CompoundTag.class));
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.saveOptional(registries()));
    }

    private static HolderLookup.Provider registries() {
        MinecraftServer server = ServerLifecycleHooks.get();

        if (server == null)
            throw new IllegalStateException("Item stacks can only be (de)serialised with a server running");

        return server.registryAccess();
    }
}
