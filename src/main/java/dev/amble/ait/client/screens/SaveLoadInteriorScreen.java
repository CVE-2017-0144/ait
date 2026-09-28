package dev.amble.ait.client.screens;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.OffScreenCull;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.Vec3;

public class SaveLoadInteriorScreen extends ConsoleScreen {
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/gui/tardis/monitor/security_menu.png");
    private final List<Button> buttons = Lists.newArrayList();
    int bgHeight = 138;
    int bgWidth = 216;
    int left, top;
    int choicesCount = 0;
    private final Screen parent;
    private final int APPLY_BAR_BUTTON_WIDTH = 53;
    private final int APPLY_BAR_BUTTON_HEIGHT = 12;
    private final BlockPos console;

    // Rendering fields
    private float rotationX = 30.0f;
    private float rotationY = 0.0f;
    private float zoom = 4.0f;
    private boolean isDragging = false;
    private boolean isPanning = false;
    private double lastMouseX = 0;
    private double lastMouseY = 0;
    private float offsetX = 0.0f;
    private float offsetY = 0.0f;

    // Ultra-optimized caching
    private final Set<BlockPos> visibleBlocks = new HashSet<>();
    private final Set<BlockPos> blockEntityPositions = new HashSet<>();
    private long lastCacheUpdate = 0;
    private static final long CACHE_UPDATE_INTERVAL = 50; // Reduced to 50ms for smoother updates

    // Frustum culling cache
    private Vec3 lastCameraDir = Vec3.ZERO;
    private float lastRotationX = 0;
    private float lastRotationY = 0;
    private boolean cacheInvalidated = true;

    // Performance settings
    private static final int MAX_BATCH_SIZE = 256; // Larger batches = fewer draw calls
    private static final int MAX_RENDER_DISTANCE = 25;
    private static final boolean ENABLE_BLOCK_ENTITIES = true;
    private static final boolean AGGRESSIVE_CULLING = true;

    // Mutable BlockPos for iteration (reduces allocations)
    private final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

    public SaveLoadInteriorScreen(ClientTardis tardis, BlockPos console, Screen parent) {
        super(Component.translatable("screen." + AITMod.MOD_ID + ".loadsaveinterior.title"), tardis, console);
        this.console = console;
        this.parent = parent;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        this.top = (this.height - this.bgHeight) / 2;
        this.left = (this.width - this.bgWidth) / 2;
        this.createButtons();

        // Pre-cache on init
        cacheInvalidated = true;

        super.init();
    }

    private void createButtons() {
        choicesCount = 0;
        this.buttons.clear();

        this.addButton(new AITPressableTextWidget((int) (left + (bgWidth * 0.139f)), (int) (top + (bgHeight * 0.839f)),
                APPLY_BAR_BUTTON_WIDTH, APPLY_BAR_BUTTON_HEIGHT, Component.empty(), button -> {
            sendSaveInteriorPacket();
        }, this.font));
    }

    public void backToInteriorSettings() {
        Minecraft.getInstance().setScreen(this.parent);
    }

    public void sendSaveInteriorPacket() {
        if (!(Minecraft.getInstance().level.getBlockEntity(this.console) instanceof ConsoleBlockEntity consoleBlockEntity))
            return;
    }

    private <T extends AbstractWidget> void addButton(T button) {
        this.addRenderableWidget(button);
        button.active = true;
        this.buttons.add((Button) button);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        // Clear buffers efficiently
        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, Minecraft.ON_OSX);

        // Render GUI background first
        super.render(context, mouseX, mouseY, delta);

        Minecraft client = Minecraft.getInstance();
        BlockPos playerPos = client.player.blockPosition();

        // Setup render state
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.depthMask(true);
        RenderSystem.enableCull(); // Enable backface culling for performance

        PoseStack matrices = context.pose();
        matrices.pushPose();

        // Apply transformations
        matrices.translate(this.width / 2.0 + offsetX, this.height / 2.0 + offsetY, 500);
        matrices.scale(zoom, -zoom, zoom);
        matrices.mulPose(Axis.XP.rotationDegrees(rotationX));
        matrices.mulPose(Axis.YP.rotationDegrees(rotationY));

        // Get vertex consumer
        MultiBufferSource.BufferSource immediate = client.renderBuffers().bufferSource();
        BlockRenderDispatcher blockRenderManager = client.getBlockRenderer();
        Level world = client.level;

        // Calculate camera direction
        Vec3 cameraDir = getCameraDirection();

        // Check if cache needs update
        long currentTime = System.currentTimeMillis();
        boolean rotationChanged = Math.abs(rotationX - lastRotationX) > 0.1f || Math.abs(rotationY - lastRotationY) > 0.1f;

        if (cacheInvalidated || rotationChanged || currentTime - lastCacheUpdate > CACHE_UPDATE_INTERVAL) {
            updateVisibleBlocksCache(world, playerPos, cameraDir);
            lastCacheUpdate = currentTime;
            lastRotationX = rotationX;
            lastRotationY = rotationY;
            lastCameraDir = cameraDir;
            cacheInvalidated = false;
        }

        // Render blocks in large batches
        renderBlocks(matrices, immediate, blockRenderManager, world, playerPos, delta);

        matrices.popPose();

        // Restore render state
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.disableCull();
    }

    private void renderBlocks(PoseStack matrices, MultiBufferSource.BufferSource immediate,
                              BlockRenderDispatcher blockRenderManager, Level world, BlockPos playerPos, float delta) {
        int batchSize = 0;
        int blockEntityBatch = 0;

        // Render regular blocks first (batched)
        for (BlockPos pos : visibleBlocks) {
            BlockState state = world.getBlockState(pos);

            if (!state.isAir() && state.getBlock() != Blocks.BLACK_CONCRETE && !isFullySurrounded(world, pos)) {
                matrices.pushPose();

                // Use relative coordinates
                matrices.translate(
                        pos.getX() - playerPos.getX(),
                        pos.getY() - playerPos.getY(),
                        pos.getZ() - playerPos.getZ()
                );

                // Render block
                blockRenderManager.renderSingleBlock(
                        state,
                        matrices,
                        immediate,
                        LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY
                );

                matrices.popPose();
                batchSize++;

                // Draw in large batches
                if (batchSize >= MAX_BATCH_SIZE) {
                    immediate.endBatch();
                    batchSize = 0;
                }
            }
        }

        // Flush remaining blocks
        if (batchSize > 0) {
            immediate.endBatch();
        }

        // Render block entities separately (they need immediate drawing)
        if (ENABLE_BLOCK_ENTITIES) {
            // This preview calls the block entity renderers itself, with the camera arithmetic above
            // and a matrix stack of its own, while the block entity render dispatcher still holds
            // whatever camera the last world pass left. The blocks are the real ones in
            // client.world, so the off screen cull cannot tell this apart from a world pass and
            // would reject whatever sits behind that stale camera.
            OffScreenCull.suspend();

            try {
                for (BlockPos pos : blockEntityPositions) {
                    BlockEntity blockEntity = world.getBlockEntity(pos);
                    if (blockEntity != null) {
                        matrices.pushPose();

                        matrices.translate(
                                pos.getX() - playerPos.getX(),
                                pos.getY() - playerPos.getY(),
                                pos.getZ() - playerPos.getZ()
                        );

                        renderBlockEntity(blockEntity, matrices, immediate, delta);

                        matrices.popPose();
                    }
                }
            } finally {
                OffScreenCull.resume();
            }
        }
    }

    private void updateVisibleBlocksCache(Level world, BlockPos playerPos, Vec3 cameraDir) {
        visibleBlocks.clear();
        blockEntityPositions.clear();

        int minX = playerPos.getX() - MAX_RENDER_DISTANCE;
        int minY = playerPos.getY() - MAX_RENDER_DISTANCE;
        int minZ = playerPos.getZ() - MAX_RENDER_DISTANCE;
        int maxX = playerPos.getX() + MAX_RENDER_DISTANCE;
        int maxY = playerPos.getY() + MAX_RENDER_DISTANCE;
        int maxZ = playerPos.getZ() + MAX_RENDER_DISTANCE;

        // Use mutable BlockPos to reduce allocations
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    mutablePos.set(x, y, z);

                    BlockState state = world.getBlockState(mutablePos);
                    if (state.isAir() || state.getBlock() == Blocks.BLACK_CONCRETE) {
                        continue;
                    }

                    // REMOVED: Skip if fully surrounded - we want to see everything
                    // REMOVED: Frustum culling - we want to see all blocks

                    // Add to visible set (immutable copy)
                    BlockPos immutablePos = mutablePos.immutable();
                    visibleBlocks.add(immutablePos);

                    // Track block entities separately
                    if (world.getBlockEntity(mutablePos) != null) {
                        blockEntityPositions.add(immutablePos);
                    }
                }
            }
        }
    }

    private void renderBlockEntity(BlockEntity blockEntity, PoseStack matrices,
                                   MultiBufferSource.BufferSource immediate, float delta) {
        Minecraft client = Minecraft.getInstance();
        BlockEntityRenderDispatcher dispatcher = client.getBlockEntityRenderDispatcher();

        try {
            BlockEntityRenderer<BlockEntity> renderer =
                    dispatcher.getRenderer(blockEntity);

            if (renderer != null) {
                renderer.render(
                        blockEntity,
                        delta,
                        matrices,
                        immediate,
                        LightTexture.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY
                );
                immediate.endBatch();
            }
        } catch (Exception e) {
            // Silently ignore rendering errors
        }
    }

    private Vec3 getCameraDirection() {
        double pitch = Math.toRadians(rotationX);
        double yaw = Math.toRadians(rotationY + 180);

        double x = -Math.sin(yaw) * Math.cos(pitch);
        double y = Math.sin(pitch);
        double z = Math.cos(yaw) * Math.cos(pitch);

        return new Vec3(x, y, z).normalize();
    }

    private boolean isBackFacing(Vec3 blockPos, Vec3 cameraDir, Level world, BlockPos pos) {
        // Normalize and check dot product
        double lengthSq = blockPos.lengthSqr();
        if (lengthSq < 0.001) return false;

        double invLength = 1.0 / Math.sqrt(lengthSq);
        double normalizedX = blockPos.x * invLength;
        double normalizedY = blockPos.y * invLength;
        double normalizedZ = blockPos.z * invLength;

        double dot = normalizedX * cameraDir.x + normalizedY * cameraDir.y + normalizedZ * cameraDir.z;

        // More aggressive culling
        if (dot > 0.7) {
            return true;
        }

        // Check exposed faces
        return !hasExposedFaceTowardsCamera(world, pos, cameraDir);
    }

    private boolean hasExposedFaceTowardsCamera(Level world, BlockPos pos, Vec3 cameraDir) {
        // Check all 6 directions efficiently
        for (Direction dir : Direction.values()) {
            mutablePos.set(pos).move(dir);
            BlockState adjacentState = world.getBlockState(mutablePos);

            if (!adjacentState.isSolidRender(world, mutablePos)) {
                // Quick dot product check
                int offsetX = dir.getStepX();
                int offsetY = dir.getStepY();
                int offsetZ = dir.getStepZ();

                double faceDot = offsetX * cameraDir.x + offsetY * cameraDir.y + offsetZ * cameraDir.z;

                if (faceDot < 0) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isFullySurrounded(Level world, BlockPos pos) {
        // Use mutable pos for checks
        mutablePos.setWithOffset(pos, Direction.UP);
        if (!world.getBlockState(mutablePos).isSolidRender(world, mutablePos)) return false;

        mutablePos.setWithOffset(pos, Direction.DOWN);
        if (!world.getBlockState(mutablePos).isSolidRender(world, mutablePos)) return false;

        mutablePos.setWithOffset(pos, Direction.NORTH);
        if (!world.getBlockState(mutablePos).isSolidRender(world, mutablePos)) return false;

        mutablePos.setWithOffset(pos, Direction.SOUTH);
        if (!world.getBlockState(mutablePos).isSolidRender(world, mutablePos)) return false;

        mutablePos.setWithOffset(pos, Direction.EAST);
        if (!world.getBlockState(mutablePos).isSolidRender(world, mutablePos)) return false;

        mutablePos.setWithOffset(pos, Direction.WEST);
        if (!world.getBlockState(mutablePos).isSolidRender(world, mutablePos)) return false;

        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            isDragging = true;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            return true;
        } else if (button == 2) {
            isPanning = true;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            isDragging = false;
            return true;
        } else if (button == 2) {
            isPanning = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (isDragging) {
            rotationY += (float) deltaX * 0.5f;
            rotationX += (float) deltaY * 0.5f;
            rotationX = Math.max(-90, Math.min(90, rotationX));
            cacheInvalidated = true; // Mark for immediate update
            return true;
        } else if (isPanning) {
            offsetX += (float) deltaX;
            offsetY += (float) deltaY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double amount) {
        zoom += (float) amount * 0.5f;
        zoom = Math.max(1.0f, Math.min(100.0f, zoom));
        return true;
    }

    private void drawBackground(GuiGraphics context) {
        context.blit(BACKGROUND, left, top, 0, 0, bgWidth, bgHeight);
    }

    public static class AITPressableTextWidget extends Button {
        private final Font textRenderer;
        private final Component text;

        public AITPressableTextWidget(int x, int y, int width, int height, Component text, Button.OnPress onPress,
                                      Font textRenderer) {
            super(x, y, width, height, text, onPress, DEFAULT_NARRATION);
            this.textRenderer = textRenderer;
            this.text = text;
        }

        public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
            Component text = this.text;
            context.drawString(this.textRenderer, text, this.getX(), this.getY(),
                    16777215 | Mth.ceil(this.alpha * 255.0F) << 24);
        }
    }
}