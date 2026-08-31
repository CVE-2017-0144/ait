package dev.amble.ait.core.item;

import java.util.function.Consumer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.world.RiftChunkManager;
import dev.amble.ait.core.world.TardisServerWorld;

public class RiftScannerItem extends Item {
    private static final int MAX_ITERATIONS = 32;
    private static final String NBT_X = "X";
    private static final String NBT_Z = "Z";
    private static final String NBT_DINGED = "Dinged";

    public RiftScannerItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!(world instanceof ServerLevel serverWorld))
            return InteractionResultHolder.pass(user.getItemInHand(hand));

        if (TardisServerWorld.isTardisDimension(serverWorld))
            return InteractionResultHolder.fail(user.getItemInHand(hand));

        ItemStack stack = user.getItemInHand(hand);
        user.getCooldowns().addCooldown(this, 100);

        findNearestRift(serverWorld, new ChunkPos(user.blockPosition()), (chunk) -> setTarget(stack, chunk));

        user.displayClientMessage(Component.translatable("riftchunk.ait.tracking"), true);
        return InteractionResultHolder.success(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (world.isClientSide) return;

        ChunkPos target = getTarget(stack);
        if (target == null || target.equals(ChunkPos.ZERO)) return;

        boolean hasDinged = stack.getOrCreateTag().getBoolean(NBT_DINGED);

        if (entity.chunkPosition().equals(target)) {
            if (!hasDinged) {
                // Bling sound is kinda quiet so it should be set to about a volume of 3
                world.playSound(null, entity.blockPosition(), AITSounds.TARDIS_BLING, SoundSource.PLAYERS, 3f, 1f);
                stack.getOrCreateTag().putBoolean(NBT_DINGED, true);
            }
        } else {
            if (hasDinged) {
                stack.getOrCreateTag().putBoolean(NBT_DINGED, false);
            }
        }
    }

    public static void findNearestRift(ServerLevel world, ChunkPos source, Consumer<ChunkPos> found) {
        int steps = 1;
        RiftChunkManager manager = RiftChunkManager.getInstance(world);

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            if (steps % 2 != 0) {
                if (trySearch(manager, steps, source, Direction.EAST, found)) return;
                if (trySearch(manager, steps, source, Direction.SOUTH, found)) return;
            } else {
                if (trySearch(manager, steps, source, Direction.WEST, found)) return;
                if (trySearch(manager, steps, source, Direction.NORTH, found)) return;
            }
            steps++;
        }
    }

    private static boolean trySearch(RiftChunkManager manager, int limit, ChunkPos source, Direction direction, Consumer<ChunkPos> found) {
        for (int b = 0; b <= limit; b++) {
            source = getChunkInDirection(source, direction);
            if (isConsumable(manager, source)) {
                found.accept(source);
                return true;
            }
        }
        return false;
    }

    private static boolean isConsumable(RiftChunkManager manager, ChunkPos pos) {
        return manager.isRiftChunk(pos) && manager.getArtron(pos) >= 250;
    }

    private static ChunkPos getChunkInDirection(ChunkPos pos, Direction dir) {
        return new ChunkPos(pos.x + (dir.getStepX()), pos.z + (dir.getStepZ()));
    }

    private static void setTarget(ItemStack stack, ChunkPos pos) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt(NBT_X, pos.x);
        nbt.putInt(NBT_Z, pos.z);
        nbt.putBoolean(NBT_DINGED, false);
    }

    public static ChunkPos getTarget(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!(nbt.contains(NBT_X) && nbt.contains(NBT_Z)))
            return ChunkPos.ZERO;
        return new ChunkPos(nbt.getInt(NBT_X), nbt.getInt(NBT_Z));
    }
}