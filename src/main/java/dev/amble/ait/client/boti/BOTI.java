package dev.amble.ait.client.boti;

import java.util.LinkedList;
import java.util.Queue;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.blockentities.DoorBlockEntity;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.entities.BOTIPaintingEntity;
import dev.amble.ait.core.entities.RiftEntity;

public class BOTI {
    public static final Minecraft client = Minecraft.getInstance();
    public static final Queue<RiftEntity> RIFT_RENDERING_QUEUE = new LinkedList<>();
    public static BOTIInit BOTI_HANDLER = new BOTIInit();
    public static AITBufferBuilderStorage AIT_BUF_BUILDER_STORAGE = new AITBufferBuilderStorage();
    public static Queue<DoorBlockEntity> DOOR_RENDER_QUEUE = new LinkedList<>();
    public static Queue<BOTIPaintingEntity> GALLIFREYAN_RENDER_QUEUE = new LinkedList<>();
    public static Queue<BOTIPaintingEntity> TRENZALORE_PAINTING_QUEUE = new LinkedList<>();
    public static Queue<ExteriorBlockEntity> EXTERIOR_RENDER_QUEUE = new LinkedList<>();
    private static boolean HAS_BEEN_WARNED = false;

    public static void clearAll() {
        RIFT_RENDERING_QUEUE.clear();
        DOOR_RENDER_QUEUE.clear();
        GALLIFREYAN_RENDER_QUEUE.clear();
        TRENZALORE_PAINTING_QUEUE.clear();
        EXTERIOR_RENDER_QUEUE.clear();
    }

    public static void copyFramebuffer(RenderTarget src, RenderTarget dest) {
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height, 0, 0, dest.width, dest.height, GlConst.GL_DEPTH_BUFFER_BIT | GlConst.GL_COLOR_BUFFER_BIT, GlConst.GL_NEAREST);
    }

    public static void copyColor(RenderTarget src, RenderTarget dest) {
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height, 0, 0, dest.width, dest.height, GlConst.GL_COLOR_BUFFER_BIT, GlConst.GL_NEAREST);
    }

    public static void copyDepth(RenderTarget src, RenderTarget dest) {
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height, 0, 0, dest.width, dest.height, GlConst.GL_DEPTH_BUFFER_BIT, GlConst.GL_NEAREST);
    }

    public static void setFramebufferColor(RenderTarget src, float r, float g, float b, float a) {
        src.setClearColor(r, g, b, a);
    }

    /**
     * Warns the user if they are missing Indium and have a non-Nvidia card.
     */
    public static void tryWarn(Minecraft client) {
        if (HAS_BEEN_WARNED)
            return;

        if (warn(client)) AITModClient.CONFIG.enableTardisBOTI = false;

        HAS_BEEN_WARNED = true;
    }

    /**
     * @return {@code true} if successfully warned the player, {@code false} otherwise
     */
    private static boolean warn(Minecraft client) {
        if (DependencyChecker.hasMacOs()) {
            tryWarnMac(client);
            return true;
        }

        if (DependencyChecker.hasIndium())
            return false;

        if (!DependencyChecker.hasNvidiaCard()) {
            tryWarnAmd(client);
            return true;
        }

        return false;
    }

    private static void tryWarnMac(Minecraft client) {
        client.player.displayClientMessage(Component.translatable("message.ait.boti.indium_required.mac").withStyle(ChatFormatting.RED), false);
    }

    private static void tryWarnAmd(Minecraft client) {
        client.player.displayClientMessage(Component.translatable("message.ait.boti.indium_required.amd").withStyle(ChatFormatting.RED), false);
    }
}
