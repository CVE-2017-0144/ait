package dev.amble.ait.core.tardis.util;

import java.util.*;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.link.LinkableItem;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.util.ServerLifecycleHooks;

public class NetworkUtil {

    public static <T> void send(ServerPlayer player, FriendlyByteBuf buf, ResourceLocation id, Codec<T> codec, T t) {
        DataResult<Tag> result = codec.encodeStart(NbtOps.INSTANCE, t);
        Tag nbt = result.resultOrPartial(AITMod.LOGGER::error).orElseThrow();

        buf.writeNbt((CompoundTag) nbt);
        send(player, id, buf);
    }

    public static void send(ServerPlayer player, ResourceLocation id, FriendlyByteBuf buf) {
        if (player == null)
            return;

        AitNetworking.send(player, id, buf);
    }

    public static <T> T receive(Codec<T> codec, FriendlyByteBuf buf) {
        return codec.decode(NbtOps.INSTANCE, buf.readNbt())
                .resultOrPartial(AITMod.LOGGER::error)
                .orElseThrow().getFirst();
    }

    public static void sendToInterior(ServerTardis tardis, ResourceLocation id, FriendlyByteBuf buf) {
        if (!tardis.hasWorld()) return;

        for (ServerPlayer player : tardis.world().players()) {
            send(player, id, buf);
        }
    }

    public static Collection<ServerPlayer> getLinkedPlayers(ServerTardis tardis) {
        List<ServerPlayer> players = new ArrayList<>();

        for (ServerPlayer player : ServerLifecycleHooks.get().getPlayerList().getPlayers()) {
            if (hasLinkedItem(tardis, player)) {
                players.add(player);
            }
        }

        return players;
    }

    public static boolean hasLinkedItem(Tardis tardis, ServerPlayer player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.isEmpty())
                continue;

            if (!(stack.getItem() instanceof LinkableItem))
                continue;

            if (!LinkableItem.isOfStatic(stack, tardis))
                continue;

            return true;
        }

        return false;
    }

    public static Set<ServerTardis> findLinkedItems(ServerPlayer player) {
        Set<ServerTardis> ids = new HashSet<>();

        for (ItemStack stack : player.getInventory().items) {
            if (stack.isEmpty())
                continue;

            if (!(stack.getItem() instanceof LinkableItem item))
                continue;

            Tardis tardis = item.getTardis(player.level(), stack);

            if (tardis == null)
                continue;

            ids.add(tardis.asServer());
        }

        return ids;
    }

    public static Stream<ServerPlayer> getSubscribedPlayers(ServerTardis tardis) {
        Stream<ServerPlayer> result = tardis.hasWorld() ? tardis.world().players().stream() : Stream.empty();
        CachedDirectedGlobalPos exteriorPos = tardis.travel().position();

        if (exteriorPos == null || exteriorPos.getWorld() == null)
            return result;

        ChunkPos chunkPos = new ChunkPos(exteriorPos.getPos());
        return Stream.concat(result, PlayerLookup.tracking(exteriorPos.getWorld(), chunkPos).stream());
    }

    /**
     * plays a sound, ignoring whether it exists or not.
     */
    public static void playSound(ResourceKey<Level> worldKey, BlockPos pos, ResourceLocation soundId, SoundSource category, float volume) {
        if (!ServerLifecycleHooks.isServer()) return;

        Holder<SoundEvent> soundEntry = Holder.direct(SoundEvent.createVariableRangeEvent(soundId));
        long seed = ServerLifecycleHooks.get().overworld().getRandom().nextLong();

        ServerLifecycleHooks.get()
                .getPlayerList()
                .broadcast(
                        null,
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        volume > 1.0F ? 16.0F * volume : 16.0F,
                        worldKey,
                        new ClientboundSoundPacket(soundEntry, category, pos.getX(), pos.getY(), pos.getZ(), volume, 1f, seed)
                );
    }

    @Environment(EnvType.CLIENT)
    public static boolean canClientSendPackets() {
        return Minecraft.getInstance().getConnection() != null;
    }
}
