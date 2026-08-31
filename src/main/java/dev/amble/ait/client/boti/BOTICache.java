package dev.amble.ait.client.boti;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import dev.amble.ait.core.tardis.util.network.BOTISnapshot;
import dev.amble.ait.core.tardis.util.network.c2s.BOTIChunkRequestC2SPacket;

@Environment(EnvType.CLIENT)
public final class BOTICache {

    private static final long REFRESH_MILLIS = 30_000;
    private static final long RETRY_MILLIS = 3_000;

    private static final Map<BlockPos, Entry> ENTRIES = new HashMap<>();

    private BOTICache() {}

    private static final class Entry {
        BOTIVBO vbo;
        byte rotation;
        long requestedAt = Long.MIN_VALUE;
        long receivedAt = Long.MIN_VALUE;
    }

    public static void accept(BlockPos exteriorPos, byte rotation, BOTISnapshot snapshot) {
        Entry entry = ENTRIES.computeIfAbsent(exteriorPos.immutable(), key -> new Entry());

        if (entry.vbo == null)
            entry.vbo = new BOTIVBO();

        entry.vbo.bake(snapshot);
        entry.rotation = rotation;
        entry.receivedAt = System.currentTimeMillis();
    }

    public static void invalidate(BlockPos exteriorPos) {
        Entry entry = ENTRIES.remove(exteriorPos);

        if (entry != null && entry.vbo != null)
            entry.vbo.close();
    }

    public static void clear() {
        for (Entry entry : ENTRIES.values()) {
            if (entry.vbo != null)
                entry.vbo.close();
        }

        ENTRIES.clear();
    }

    public static void prune() {
        Minecraft client = Minecraft.getInstance();

        if (client.level == null) {
            clear();
            return;
        }

        Iterator<Map.Entry<BlockPos, Entry>> it = ENTRIES.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<BlockPos, Entry> pair = it.next();

            if (client.level.isLoaded(pair.getKey()))
                continue;

            if (pair.getValue().vbo != null)
                pair.getValue().vbo.close();

            it.remove();
        }
    }

    public static boolean render(UUID tardis, BlockPos exteriorPos, float exteriorDegrees, PoseStack stack) {
        Entry entry = ENTRIES.computeIfAbsent(exteriorPos.immutable(), key -> new Entry());
        long now = System.currentTimeMillis();

        boolean stale = entry.vbo == null
                ? now - entry.requestedAt > RETRY_MILLIS
                : now - entry.receivedAt > REFRESH_MILLIS;

        if (stale) {
            entry.requestedAt = now;
            BOTIChunkRequestC2SPacket.send(tardis, exteriorPos);
        }

        if (entry.vbo == null || entry.vbo.isEmpty())
            return false;

        float interiorDegrees = entry.rotation * 360f / 16f;

        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(interiorDegrees - exteriorDegrees - 180f));
        stack.scale(1, -1, -1);
        stack.translate(-0.5, 0, -0.5);

        entry.vbo.draw(stack);

        stack.popPose();
        return true;
    }
}
