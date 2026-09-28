package dev.amble.ait.data.gson;

import java.lang.reflect.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import com.google.gson.*;
import dev.amble.lib.platform.Platform;

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
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if (server != null)
            return server.registryAccess();

        HolderLookup.Provider registries = Platform.isClient() ? Client.get() : null;

        if (registries == null)
            throw new IllegalStateException("no registries");

        return registries;
    }

    @OnlyIn(Dist.CLIENT)
    private static final class Client {
        static HolderLookup.Provider get() {
            Minecraft client = Minecraft.getInstance();
            return client.level == null ? null : client.level.registryAccess();
        }
    }
}
