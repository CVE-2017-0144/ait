package dev.amble.ait.core;

import java.util.UUID;
import java.util.function.BiFunction;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.item.ArtronCollectorItem;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.item.TardisMatrixItem;
import dev.amble.ait.module.planet.core.item.SpacesuitItem;

public class AITDataComponents {

    public static final DataComponentType<Double> AU_LEVEL = register("au_level", Codec.DOUBLE, ByteBufCodecs.DOUBLE);
    public static final DataComponentType<Integer> B = register("b", Codec.INT, ByteBufCodecs.VAR_INT);
    public static final DataComponentType<Integer> G = register("g", Codec.INT, ByteBufCodecs.VAR_INT);
    public static final DataComponentType<UUID> ITEM_UUID = register("uuid", UUIDUtil.CODEC, UUIDUtil.STREAM_CODEC);
    public static final DataComponentType<Integer> MODE = register("mode", Codec.INT, ByteBufCodecs.VAR_INT);
    public static final DataComponentType<Double> OXYGEN = register("oxygen", Codec.DOUBLE, ByteBufCodecs.DOUBLE);
    public static final DataComponentType<Integer> R = register("r", Codec.INT, ByteBufCodecs.VAR_INT);
    public static final DataComponentType<Integer> SIEGE_CURRENT_TEXTURE = register("siege_current_texture", Codec.INT, ByteBufCodecs.VAR_INT);
    public static final DataComponentType<String> SONIC_TYPE = register("sonic_type", Codec.STRING, ByteBufCodecs.STRING_UTF8);

    public static void init() {}

    public static void migrate(ItemStack stack) {
        if (!stack.has(DataComponents.CUSTOM_DATA))
            return;

        Item item = stack.getItem();

        if (item instanceof ArtronCollectorItem) {
            move(stack, "au_level", AU_LEVEL, CompoundTag::getDouble);
            move(stack, "uuid", ITEM_UUID, CompoundTag::getUUID);
        } else if (item instanceof SonicItem) {
            move(stack, "mode", MODE, CompoundTag::getInt);
            move(stack, "sonic_type", SONIC_TYPE, CompoundTag::getString);
        } else if (item instanceof TardisMatrixItem) {
            move(stack, "g", G, CompoundTag::getInt);
            move(stack, "r", R, CompoundTag::getInt);
            move(stack, "b", B, CompoundTag::getInt);
        } else if (item instanceof SpacesuitItem) {
            move(stack, "oxygen", OXYGEN, CompoundTag::getDouble);
        }
    }

    private static <T> void move(ItemStack stack, String key, DataComponentType<T> type, BiFunction<CompoundTag, String, T> read) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null || !data.contains(key))
            return;

        stack.set(type, read.apply(data.copyTag(), key));
        CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.remove(key));
    }

    private static <T> DataComponentType<T> register(String name, Codec<T> codec,
            StreamCodec<? super RegistryFriendlyByteBuf, T> stream) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, AITMod.id(name),
                DataComponentType.<T>builder().persistent(codec).networkSynchronized(stream).build());
    }
}
