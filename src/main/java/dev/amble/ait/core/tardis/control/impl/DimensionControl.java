package dev.amble.ait.core.tardis.control.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.lock.LockedDimensionRegistry;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.control.impl.pos.PosType;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.util.AsyncLocatorUtil;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class DimensionControl extends Control {

    public static final ResourceLocation ID = AITMod.id("dimension");

    public DimensionControl() {
        super(ID);
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        TravelHandler travel = tardis.travel();
        CachedDirectedGlobalPos dest = travel.destination();
        List<ServerLevel> dims = WorldUtil.getTravelWorlds();

        if (dims.isEmpty()) {
            player.displayClientMessage(Component.translatableWithFallback("message.ait.tardis.control.dimension.none_available",
                    "No travel dimensions are currently allowed."), true);
            return Result.FAILURE;
        }

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            int index = Math.max(0, WorldUtil.travelWorldIndex(dest.getWorld()));

            if (leftClick) {
                index = (dims.size() + index - 1) % dims.size();
            } else {
                index = (index + 1) % dims.size();
            }

            return dims.get(index);
        }).thenAccept(destWorld -> {
            travel.destination(cached -> {
                CachedDirectedGlobalPos cachedPos = cached.world(destWorld);
                BlockPos clampedPos = PosType.clamp(cachedPos.getPos(), 0, destWorld);
                return cachedPos.pos(clampedPos);
            });
            messagePlayer(player, destWorld, LockedDimensionRegistry.getInstance().isUnlocked(tardis, destWorld));
        });

        AsyncLocatorUtil.LOCATING_EXECUTOR_SERVICE.submit(() -> future);
        return Result.SUCCESS;
    }

    private void messagePlayer(ServerPlayer player, ServerLevel world, boolean unlocked) {
        MutableComponent message = Component.translatable("message.ait.tardis.control.dimension.info")
                .append(WorldUtil.worldText(world.dimension(), false)).withStyle(unlocked ? ChatFormatting.WHITE : ChatFormatting.GRAY);

        if (!unlocked) message.append(Component.literal(" \uD83D\uDD12"));

        player.displayClientMessage(message, true);
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.DIMENSION;
    }
}
