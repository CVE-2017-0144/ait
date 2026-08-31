package dev.amble.lib.platform;

import java.util.Collection;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;

public class PlayerLookup {

    public static Collection<ServerPlayer> tracking(ServerLevel world, ChunkPos pos) {
        List<ServerPlayer> players = world.getChunkSource().chunkMap.getPlayers(pos, false);
        return players == null ? List.of() : players;
    }

    public static Collection<ServerPlayer> tracking(ServerLevel world, BlockPos pos) {
        return tracking(world, new ChunkPos(pos));
    }
}
