package dev.amble.ait.core.tardis.util.network.c2s;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.tardis.util.network.s2c.BOTIDataS2CPacket;

public class BOTIChunkRequestC2SPacket implements FabricPacket {
    public static final PacketType<BOTIChunkRequestC2SPacket> TYPE = PacketType.create(AITMod.id("request_chunk_data"), BOTIChunkRequestC2SPacket::new);
    private final BlockPos botiPos;
    private final ResourceKey<Level> targetWorld;
    private final BlockPos targetPos;
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(botiPos);
        buf.writeResourceKey(targetWorld);
        buf.writeBlockPos(targetPos);
    }

    public BOTIChunkRequestC2SPacket(FriendlyByteBuf buf) {
        this.botiPos = buf.readBlockPos();
        this.targetWorld = buf.readResourceKey(Registries.DIMENSION);
        this.targetPos = buf.readBlockPos();
    }

    public BOTIChunkRequestC2SPacket(BlockPos botiPos, ResourceKey<Level> targetWorld, BlockPos targetPos) {
        this.botiPos = botiPos;
        this.targetWorld = targetWorld;
        this.targetPos = targetPos;
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    @SuppressWarnings("unchecked")
    public <T> boolean handle(ServerPlayer source, PacketSender response) {
        if (source == null) return false;

        MinecraftServer server = source.getServer();

        if (server == null) return false;

        ServerLevel world = server.getLevel(this.targetWorld);

        if (world == null) return false;

        ChunkPos chunkPos = new ChunkPos(this.targetPos);
        world.getChunkSource().getChunk(chunkPos.x, chunkPos.z, ChunkStatus.FULL, false);
        LevelChunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
        ServerPlayNetworking.send(source, new BOTIDataS2CPacket(this.botiPos, chunk, this.targetPos));
        return true;
    }
}
