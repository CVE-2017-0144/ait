package dev.amble.lib.platform;

import java.util.Collection;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;

public final class PlayerLookup {

    private PlayerLookup() {}

    public static Collection<ServerPlayer> tracking(ServerLevel world, ChunkPos pos) {
        return net.fabricmc.fabric.api.networking.v1.PlayerLookup.tracking(world, pos);
    }

    public static Collection<ServerPlayer> tracking(ServerLevel world, BlockPos pos) {
        return net.fabricmc.fabric.api.networking.v1.PlayerLookup.tracking(world, pos);
    }
}
