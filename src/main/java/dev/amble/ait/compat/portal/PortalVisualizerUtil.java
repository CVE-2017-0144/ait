package dev.amble.ait.compat.portal;

import java.util.Optional;
import java.util.WeakHashMap;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.net.AitNetworking;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import qouteall.imm_ptl.core.ClientWorldLoader;
import qouteall.imm_ptl.core.api.PortalAPI;
import qouteall.imm_ptl.core.chunk_loading.ChunkLoader;
import qouteall.imm_ptl.core.chunk_loading.DimensionalChunkPos;
import qouteall.imm_ptl.core.render.GuiPortalRendering;
import qouteall.imm_ptl.core.render.MyRenderHelper;
import qouteall.imm_ptl.core.render.context_management.WorldRenderInfo;
import qouteall.q_misc_util.my_util.DQuaternion;

public class PortalVisualizerUtil {

    public static final ResourceLocation OPEN_VISUALIZER = AITMod.id("ip/visualizer/open");
    public static final ResourceLocation CLOSE_VISUALIZER = AITMod.id("ip/visualizer/close");

    private static final WeakHashMap<ServerPlayer, ChunkLoader>
        chunkLoaderMap = new WeakHashMap<>();

    public static void init() {
        PortalsAPI.VISUALIZER = Optional.of(PortalVisualizerUtil::open);

        AitNetworking.registerServerReceiver(CLOSE_VISUALIZER, (server, player, handler, buf, sender) -> {
            server.execute(() -> removeChunkLoaderFor(player));
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientInit() {
        GuiPortalScreen.clientInit();
    }

    private static void removeChunkLoaderFor(ServerPlayer player) {
        ChunkLoader chunkLoader = chunkLoaderMap.remove(player);
        if (chunkLoader != null) {
            PortalAPI.removeChunkLoaderForPlayer(player, chunkLoader);
        }
    }

    public static void open(ServerPlayer player, ServerLevel world, BlockPos pos) {
        removeChunkLoaderFor(player);

        ChunkLoader chunkLoader = new ChunkLoader(
            new DimensionalChunkPos(
                world.dimension(), new ChunkPos(pos)
            ),
            8
        );

        // Add the per-player additional chunk loader
        PortalAPI.addChunkLoaderForPlayer(player, chunkLoader);
        chunkLoaderMap.put(player, chunkLoader);

        // Tell the client to open the screen
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeResourceKey(world.dimension());
        buf.writeBlockPos(pos);

        AitNetworking.send(player, OPEN_VISUALIZER, buf);
    }

    @OnlyIn(Dist.CLIENT)
    public static class GuiPortalScreen extends Screen {

        private static final ResourceLocation TEXTURE = AITMod.id("textures/gui/tardis/monitor/visualizer_menu.png");
        private static final ResourceLocation OVERLAY = AITMod.id("textures/gui/tardis/monitor/visualizer_overlay.png");

        private static final int bgHeight = 154;
        private static final int bgWidth = 256;

        private static final int bgBorder = 9;

        private final ResourceKey<Level> viewingDimension;

        private final Vec3 viewingPosition;

        /**
         * The Framebuffer that the GUI portal is going to render onto
         */
        private static RenderTarget frameBuffer;

        public static void clientInit() {
            AitNetworking.registerClientReceiver(OPEN_VISUALIZER, (client, handler, buf, sender) -> {
                ResourceKey<Level> dim = buf.readResourceKey(Registries.DIMENSION);
                BlockPos pos = buf.readBlockPos();

                client.execute(() -> {
                    if (frameBuffer == null)
                        frameBuffer = new TextureTarget(2, 2, true, true);
                    client.setScreen(new GuiPortalScreen(dim, pos.getCenter()));
                });
            });
        }

        public GuiPortalScreen(ResourceKey<Level> viewingDimension, Vec3 viewingPosition) {
            super(Component.translatable("screen.ait.visualizer.title"));

            this.viewingDimension = viewingDimension;
            this.viewingPosition = viewingPosition;
        }

        @Override
        public void onClose() {
            super.onClose();

            AitNetworking.send(CLOSE_VISUALIZER, AitNetworking.buf());
        }

        @Override
        public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
            int top = (this.height - bgHeight) / 2;
            int left = (this.width - bgWidth) / 2;

            context.blit(TEXTURE, left, top, 0, 0, bgWidth, bgHeight);

            double t1 = delta + Minecraft.getInstance().player.tickCount;

            // Determine the camera transformation
            Matrix4f cameraTransformation = new Matrix4f();
            cameraTransformation.identity();
            cameraTransformation.mul(
                    DQuaternion.rotationByDegrees(
                            new Vec3(0, 1, 0).normalize(),
                            Mth.wrapDegrees(t1)
                    ).toMatrix()
            );

            // Create the world render info
            WorldRenderInfo worldRenderInfo = new WorldRenderInfo.Builder()
                    .setWorld(ClientWorldLoader.getWorld(viewingDimension))
                    .setCameraPos(viewingPosition)
                    .setCameraTransformation(cameraTransformation)
                    .setOverwriteCameraTransformation(true) // do not apply camera transformation to existing player camera transformation
                    .setDescription(null)
                    .setRenderDistance(minecraft.options.getEffectiveRenderDistance())
                    .setDoRenderHand(false)
                    .setEnableViewBobbing(false)
                    .setDoRenderSky(false)
                    .setHasFog(false)
                    .build();

            // Ask it to render the world into the framebuffer the next frame
            GuiPortalRendering.submitNextFrameRendering(worldRenderInfo, frameBuffer);

            float scale = (float) minecraft.getWindow().getGuiScale();

            // Draw the framebuffer
            MyRenderHelper.drawFramebuffer(
                    frameBuffer,
                    true, // enable alpha blend
                    false, // don't modify alpha
                    (left + bgBorder) * scale, (left + bgWidth - bgBorder) * scale,
                    (top + bgBorder) * scale, (top + bgHeight - bgBorder) * scale
            );

            context.blit(OVERLAY, left, top, 0, 0, bgWidth, bgHeight);
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }

        // close when E is pressed
        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (super.keyPressed(keyCode, scanCode, modifiers)) {
                return true;
            }

            if (minecraft.options.keyInventory.matches(keyCode, scanCode)) {
                this.onClose();
                return true;
            }

            return false;
        }
    }
}
