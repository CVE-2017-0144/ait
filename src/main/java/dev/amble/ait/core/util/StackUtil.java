package dev.amble.ait.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class StackUtil {

    public static final ResourceLocation AIR_ID = BuiltInRegistries.ITEM.getKey(Items.AIR);
    public static final String AIR_STR_ID = AIR_ID.toString();

    public static boolean equals(Collection<ItemStack> as, Collection<ItemStack> bs) {
        if (as.size() != bs.size())
            return false;

        for (ItemStack a : as) {
            boolean found = false;

            for (ItemStack b : bs) {
                if (ItemStack.isSameItem(a, b)) {
                    found = true;
                    break;
                }
            }

            if (!found)
                return false;
        }

        return true;
    }

    public static <T extends Collection<ItemStack>> T copy(T t, Supplier<T> supplier) {
        T copy = supplier.get();

        for (ItemStack stack : t) {
            copy.add(stack.copy());
        }

        return copy;
    }

    public static void spawn(Level world, Position pos, ItemStack stack) {
        world.addFreshEntity(new ItemEntity(world, pos.x(), pos.y(), pos.z(), stack));
    }

    public static void spawn(Level world, BlockPos pos, ItemStack stack) {
        spawn(world, pos.getCenter(), stack);
    }

    public static void playBreak(Player player) {
        player.playSound(SoundEvents.ITEM_BREAK, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
    }

    public static void scatter(Level world, Position pos, Collection<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            Containers.dropItemStack(world, pos.x(), pos.y(), pos.z(), stack);
        }
    }

    public static void scatter(Level world, BlockPos pos, Collection<ItemStack> stacks) {
        scatter(world, pos.getCenter(), stacks);
    }

    public static CompoundTag writeUnordered(CompoundTag nbt, Collection<ItemStack> stacks) {
        ListTag nbtList = new ListTag();

        for (ItemStack stack : stacks) {
            if (stack == null || stack.isEmpty())
                continue;

            CompoundTag nbtCompound = new CompoundTag();

            stack.save(nbtCompound);
            nbtList.add(nbtCompound);
        }

        if (!nbtList.isEmpty())
            nbt.put("Items", nbtList);

        return nbt;
    }

    public static CompoundTag write(CompoundTag nbt, List<ItemStack> stacks) {
        ListTag nbtList = new ListTag();

        for (int i = 0; i < stacks.size(); i++) {
            ItemStack stack = stacks.get(i);

            if (stack == null)
                stack = new ItemStack(Items.AIR);

            if (stack.isEmpty())
                continue;

            CompoundTag nbtCompound = new CompoundTag();
            nbtCompound.putByte("Slot", (byte) i);

            stack.save(nbtCompound);
            nbtList.add(nbtCompound);
        }

        if (!nbtList.isEmpty())
            nbt.put("Items", nbtList);

        return nbt;
    }

    public static CompoundTag write(CompoundTag nbt, ItemStack... stacks) {
        ListTag nbtList = new ListTag();

        for (int i = 0; i < stacks.length; i++) {
            ItemStack stack = stacks[i];

            if (stack == null)
                stack = new ItemStack(Items.AIR);

            if (stack.isEmpty())
                continue;

            CompoundTag nbtCompound = new CompoundTag();
            nbtCompound.putByte("Slot", (byte) i);

            stack.save(nbtCompound);
            nbtList.add(nbtCompound);
        }

        if (!nbtList.isEmpty())
            nbt.put("Items", nbtList);

        return nbt;
    }

    public static void read(CompoundTag nbt, List<ItemStack> stacks) {
        ListTag nbtList = nbt.getList("Items", 10);

        for (int i = 0; i < nbtList.size(); i++) {
            CompoundTag nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;

            if (j < stacks.size()) {
                stacks.set(j, ItemStack.of(nbtCompound));
            }
        }
    }

    public static ItemStack[] read(CompoundTag nbt) {
        ListTag nbtList = nbt.getList("Items", 10);
        ItemStack[] stacks = new ItemStack[nbtList.size()];

        for (int i = 0; i < nbtList.size(); i++) {
            CompoundTag nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;

            if (j < stacks.length) {
                stacks[j] = ItemStack.of(nbtCompound);
            }
        }

        return stacks;
    }

    public static void readUnordered(CompoundTag nbt, Collection<ItemStack> stacks) {
        ListTag nbtList = nbt.getList("Items", 10);

        for (int i = 0; i < nbtList.size(); i++) {
            stacks.add(ItemStack.of(nbtList.getCompound(i)));
        }
    }

    public static void write(CompoundTag nbt, String key, Item item) {
        ResourceLocation identifier = item != null ? BuiltInRegistries.ITEM.getKey(item) : null;
        nbt.putString(key, identifier == null ? AIR_STR_ID : identifier.toString());
    }

    public static Item readItem(CompoundTag nbt, String key) {
        String raw = nbt.getString(key);

        if (raw.isEmpty())
            return null;

        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(raw));
    }

    public static Item readItemNonNull(CompoundTag nbt, String key) {
        Item result = readItem(nbt, key);
        return result != null ? result : Items.AIR;
    }

    public static ItemStack take(ItemStack other, int amount) {
        ItemStack result = other.copyWithCount(amount);
        other.shrink(amount);

        return result;
    }

    public static ItemStack take(ItemStack other) {
        return take(other, 1);
    }

    public static ItemStack air() {
        return new ItemStack(Items.AIR);
    }

    public static void writeItem(FriendlyByteBuf buf, Item item) {
        buf.writeId(BuiltInRegistries.ITEM, item);
    }

    public static Item readItem(FriendlyByteBuf buf) {
        return buf.readById(BuiltInRegistries.ITEM);
    }

    public static ItemStack orAir(ItemStack stack) {
        return stack == null ? air() : stack;
    }

    public static Item orAir(Item item) {
        return item == null ? Items.AIR : item;
    }

    public static List<ItemStack> cloneList(List<ItemStack> list) {
        List<ItemStack> clone = new ArrayList<>(list.size());
        for (ItemStack item : list) clone.add(item.copy());
        return clone;
    }
}
