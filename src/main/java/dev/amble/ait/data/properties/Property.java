package dev.amble.ait.data.properties;

import java.util.HashSet;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Vector2i;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.data.DirectedGlobalPos;

public class Property<T> {

    private final PropertyType<T> type;
    private final String name;

    protected final Function<KeyedTardisComponent, T> def;

    public Property(PropertyType<T> type, String name, Function<KeyedTardisComponent, T> def) {
        this.type = type;
        this.name = name;
        this.def = def;
    }

    public Property(PropertyType<T> type, String name, T def) {
        this(type, name, o -> def);
    }

    public Property(PropertyType.Nullable<T> type, String name) {
        this(type, name, o -> null);
    }

    public Value<T> create(KeyedTardisComponent holder) {
        T t = this.def == null ? null : this.def.apply(holder);
        Value<T> result = this.create(t);

        result.of(holder, this);
        return result;
    }

    protected Value<T> create(T t) {
        return new Value<>(t);
    }

    public String getName() {
        return name;
    }

    public PropertyType<T> getType() {
        return type;
    }

    public Property<T> copy(String name) {
        return new Property<>(this.type, name, this.def);
    }

    public Property<T> copy(String name, T def) {
        return new Property<>(this.type, name, def);
    }

    public static <T extends Enum<T>> Property<T> forEnum(String name, Class<T> clazz, T def) {
        return new Property<>(PropertyType.forEnum(clazz), name, def);
    }

    public static final PropertyType.Nullable<DirectedGlobalPos> DIRECTED_GLOBAL_POS = new PropertyType.Nullable<>(DirectedGlobalPos.class,
            (buf, pos) -> pos.write(buf), DirectedGlobalPos::read);

    public static final PropertyType.Nullable<BlockPos> BLOCK_POS = new PropertyType.Nullable<>(BlockPos.class, (buf, pos) -> buf.writeBlockPos(pos),
            buf -> buf.readBlockPos());

    public static final PropertyType.Nullable<CachedDirectedGlobalPos> CDIRECTED_GLOBAL_POS = new PropertyType.Nullable<>(
            CachedDirectedGlobalPos.class, (buf, pos) -> pos.write(buf), CachedDirectedGlobalPos::read);

    public static final PropertyType.Nullable<ResourceLocation> IDENTIFIER = new PropertyType.Nullable<>(ResourceLocation.class, FriendlyByteBuf::writeResourceLocation,
            FriendlyByteBuf::readResourceLocation);

    public static final PropertyType.Nullable<Long> LONG = new PropertyType.Nullable<>(Long.class, FriendlyByteBuf::writeLong, FriendlyByteBuf::readLong);

    public static final PropertyType.Nullable<ResourceKey<Level>> WORLD_KEY = new PropertyType.Nullable<>(ResourceKey.class,
            FriendlyByteBuf::writeResourceKey, buf -> buf.readResourceKey(Registries.DIMENSION));

    public static final PropertyType<Direction> DIRECTION = PropertyType.forEnum(Direction.class);

    public static final PropertyType.Nullable<Vector2i> VEC2I = new PropertyType.Nullable<>(Vector2i.class, (buf, vector2i) -> {
        buf.writeInt(vector2i.x);
        buf.writeInt(vector2i.y);
    }, buf -> new Vector2i(buf.readInt(), buf.readInt()));

    public static final PropertyType.Nullable<String> STR = new PropertyType.Nullable<>(String.class, FriendlyByteBuf::writeUtf,
            FriendlyByteBuf::readUtf);

    public static final PropertyType.Nullable<UUID> UUID = new PropertyType.Nullable<>(UUID.class, (buf, value) -> buf.writeUUID(value), buf -> buf.readUUID());

    public static final PropertyType.Nullable<Double> DOUBLE = new PropertyType.Nullable<>(Double.class, FriendlyByteBuf::writeDouble,
            FriendlyByteBuf::readDouble);

    public static final PropertyType.Nullable<HashSet<String>> STR_SET = new PropertyType.Nullable<>(HashSet.class, (buf, strings) -> {
        buf.writeVarInt(strings.size());

        for (String str : strings)
            buf.writeUtf(str);
    }, buf -> {
        HashSet<String> result = new HashSet<>();
        int size = buf.readVarInt();

        for (int i = 0; i < size; i++) {
            result.add(buf.readUtf());
        }

        return result;
    });

    public static final PropertyType.Nullable<ItemStack> ITEM_STACK = new PropertyType.Nullable<>(ItemStack.class, (buf, stack) -> ItemStack.STREAM_CODEC.encode(buf, stack),
            ItemStack.STREAM_CODEC::decode);
}
