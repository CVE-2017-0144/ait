package dev.amble.ait.client.boti;

import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.world.level.block.state.properties.RotationSegment;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import dev.amble.ait.core.tardis.util.network.BOTISnapshot;
import dev.amble.ait.core.tardis.util.network.c2s.BOTIChunkRequestC2SPacket;

@OnlyIn(Dist.CLIENT)
public final class BOTICache {

    private static final long REFRESH_MILLIS = 30_000;
    private static final long RETRY_MILLIS = 3_000;

    private static final Map<BlockPos, Entry> ENTRIES = new HashMap<>();

    private BOTICache() {}

    private static final class Entry {
        BOTIVBO vbo;
        byte rotation;
        long requestedAt = 0;
        long receivedAt = 0;
        boolean fromInside;
        int skyColor = -1;
        boolean logged = false;
    }

    public static void accept(BlockPos exteriorPos, byte rotation, int skyColor, BOTISnapshot snapshot) {
        Entry entry = ENTRIES.computeIfAbsent(exteriorPos.immutable(), key -> new Entry());

        if (entry.vbo == null)
            entry.vbo = new BOTIVBO();

        entry.vbo.bake(snapshot, rotation, entry.fromInside);
        entry.rotation = rotation;
        entry.skyColor = skyColor;
        entry.receivedAt = System.currentTimeMillis();

        dev.amble.ait.AITMod.LOGGER.debug("[boti] baked {} for {}: {} solid blocks, {} layers",
                exteriorPos, rotation, snapshot.solidCount(), entry.vbo.layerCount());
    }

    public static int skyColor(BlockPos anchor) {
        Entry entry = ENTRIES.get(anchor);

        return entry == null ? -1 : entry.skyColor;
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

    public static boolean render(UUID tardis, BlockPos anchor, float appliedDegrees, float anchorDegrees,
            boolean fromInside, PoseStack stack) {
        Entry entry = ENTRIES.computeIfAbsent(anchor.immutable(), key -> new Entry());
        long now = System.currentTimeMillis();

        boolean stale = now - entry.requestedAt > RETRY_MILLIS
                && (entry.vbo == null || now - entry.receivedAt > REFRESH_MILLIS);

        if (stale) {
            entry.requestedAt = now;
            entry.fromInside = fromInside;
            BOTIChunkRequestC2SPacket.send(tardis, anchor, fromInside);
        }

        if (entry.vbo == null || entry.vbo.isEmpty()) {
            if (entry.vbo != null && !entry.logged) {
                entry.logged = true;
                dev.amble.ait.AITMod.LOGGER.debug("[boti] nothing to draw for {}", anchor);
            }

            return false;
        }

        if (!entry.logged) {
            entry.logged = true;
            dev.amble.ait.AITMod.LOGGER.debug("[boti] drawing {} layers for {}",
                    entry.vbo.layerCount(), anchor);
        }

        float sourceDegrees = RotationSegment.convertToDegrees(entry.rotation);

        stack.pushPose();

        stack.mulPose(Axis.YP.rotationDegrees(-appliedDegrees));
        stack.scale(1, -1, -1);

        stack.mulPose(Axis.YP.rotationDegrees(sourceDegrees - anchorDegrees));

        stack.translate(-0.5, 0, -0.5);

        entry.vbo.draw(stack);

        stack.popPose();
        return true;
    }
}
