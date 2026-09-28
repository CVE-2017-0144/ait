package dev.amble.lib.data;


import dev.amble.ait.data.Exclude;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class CachedDirectedGlobalPos extends DirectedGlobalPos {

    @Exclude
    private ServerLevel world;

    private CachedDirectedGlobalPos(ResourceKey<Level> key, BlockPos pos, byte rotation) {
        super(key, pos, rotation);
    }

    private CachedDirectedGlobalPos(ServerLevel world, BlockPos pos, byte rotation) {
        this(world.dimension(), pos, rotation);
        this.world = world;
    }

    public static CachedDirectedGlobalPos create(ServerLevel world, BlockPos pos, byte rotation) {
        return new CachedDirectedGlobalPos(world, pos, rotation);
    }

    public static CachedDirectedGlobalPos create(ResourceKey<Level> world, BlockPos pos, byte rotation) {
        return new CachedDirectedGlobalPos(world, pos, rotation);
    }

    private static CachedDirectedGlobalPos createSame(ServerLevel world, ResourceKey<Level> dimension, BlockPos pos, byte rotation) {
        if (world == null)
            return new CachedDirectedGlobalPos(dimension, pos, rotation);

        return CachedDirectedGlobalPos.create(world, pos, rotation);
    }

    private static CachedDirectedGlobalPos createNew(ServerLevel lastWorld, ResourceKey<Level> newWorldKey, BlockPos pos,
                                                     byte rotation) {
        if (lastWorld == null)
            return new CachedDirectedGlobalPos(newWorldKey, pos, rotation);

        ServerLevel newWorld = lastWorld;

        if (lastWorld.dimension() != newWorldKey)
            newWorld = lastWorld.getServer().getLevel(newWorldKey);

        return CachedDirectedGlobalPos.create(newWorld, pos, rotation);
    }

    public void init(MinecraftServer server) {
        if (this.world == null)
            this.world = server.getLevel(this.getDimension());
    }

    public ServerLevel getWorld() { // TODO - this is often null
        return world;
    }

    @Override
    public CachedDirectedGlobalPos offset(int x, int y, int z) {
        return pos(this.getPos().offset(x, y, z));
    }

    @Override
    public CachedDirectedGlobalPos world(ResourceKey<Level> dimension) {
        return CachedDirectedGlobalPos.createNew(this.world, dimension, this.getPos(), this.getRotation());
    }

    @Override
    public CachedDirectedGlobalPos pos(BlockPos pos) {
        return CachedDirectedGlobalPos.createSame(this.world, this.getDimension(), pos, this.getRotation());
    }

    @Override
    public CachedDirectedGlobalPos pos(int x, int y, int z) {
        return pos(new BlockPos(x, y, z));
    }

    @Override
    public CachedDirectedGlobalPos rotation(byte rotation) {
        return CachedDirectedGlobalPos.createSame(this.world, this.getDimension(), this.getPos(), rotation);
    }

    public CachedDirectedGlobalPos world(ServerLevel world) {
        return CachedDirectedGlobalPos.create(world, this.getPos(), this.getRotation());
    }

    public static CachedDirectedGlobalPos fromNbt(CompoundTag compound) {
        BlockPos pos = NbtUtils.readBlockPos(compound, "Pos").orElseGet(() -> new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));
        ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION,
                ResourceLocation.parse(compound.getString("dimension")));

        byte rotation = compound.getByte("rotation");
        return createNew(null, dimension, pos, rotation);
    }

    public static CachedDirectedGlobalPos read(RegistryFriendlyByteBuf buf) {
        ResourceKey<Level> registryKey = buf.readResourceKey(Registries.DIMENSION);
        BlockPos blockPos = buf.readBlockPos();
        byte rotation = buf.readByte();

        return CachedDirectedGlobalPos.createNew(null, registryKey, blockPos, rotation);
    }
}
