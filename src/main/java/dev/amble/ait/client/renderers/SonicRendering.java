package dev.amble.ait.client.renderers;

import java.util.Locale;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.blocks.DetectorBlock;
import dev.amble.ait.core.engine.DurableSubSystem;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.engine.block.SubSystemBlockEntity;
import dev.amble.ait.core.engine.impl.EngineSystem;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.item.sonic.SonicMode;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.lib.platform.render.WorldRenderContext;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class SonicRendering {
    private static final ResourceLocation SELECTED = AITMod.id("textures/marker/landing.png");
    public static final ResourceLocation SELECTED_RED = AITMod.id("textures/marker/landing_red.png");

    private final Minecraft client;
    private final ProfilerFiller profiler;

    public SonicRendering(Minecraft client) {
        this.client = client;
        this.profiler = client.getProfiler();
    }
    public SonicRendering() {
        this(Minecraft.getInstance());
    }

    public static void renderFloorTexture(BlockPos pos, ResourceLocation texture, @Nullable ResourceLocation previous, boolean spinning) {
        renderFloorTexture(new Vec3(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1), texture, previous, spinning);
    }

    public static void renderFloorTexture(Vec3 target, ResourceLocation texture, @Nullable ResourceLocation previous, boolean spinning) {
        ProfilerFiller profiler = Minecraft.getInstance().level.getProfiler();

        profiler.push("get");
        Minecraft client = Minecraft.getInstance();
        Camera camera = client.gameRenderer.getMainCamera();
        PoseStack matrices = new PoseStack();
        Tesselator tessellator = Tesselator.getInstance();
        Matrix4f positionMatrix = matrices.last().pose();

        profiler.popPush("transform");
        Vec3 transform = target.subtract(camera.getPosition());

        matrices.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        matrices.mulPose(Axis.YP.rotationDegrees(camera.getYRot() + 180f));
        matrices.translate(transform.x - 0.5f, transform.y + 0.05f, transform.z - 0.5f);
        matrices.mulPose(Axis.XP.rotationDegrees(90f));

        if (spinning) {
            matrices.mulPose(Axis.ZP.rotationDegrees(client.player.tickCount / 200f * 360f));
        }
        matrices.translate(-0.5f, 0.5f, 0f);

        profiler.popPush("vertexes");

        BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

        buffer.addVertex(positionMatrix, 0, 0, 0).setUv(0f, 0f).setColor(1f, 1f, 1f, 1f);
        buffer.addVertex(positionMatrix, 0, -1, 0).setUv(0f, 1f).setColor(1f, 1f, 1f, 1f);
        buffer.addVertex(positionMatrix, 1, -1, 0).setUv(1f, 1f).setColor(1f, 1f, 1f, 1f);
        buffer.addVertex(positionMatrix, 1, 0, 0).setUv(1f, 0f).setColor(1f, 1f, 1f, 1f);

        boolean shouldRender = !texture.equals(previous);
        MeshData mesh = buffer.buildOrThrow();

        if (shouldRender) {
            profiler.popPush("draw");
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, texture);
            RenderSystem.disableCull();
            RenderSystem.depthFunc(GL11.GL_ALWAYS);

            BufferUploader.drawWithShader(mesh);

            RenderSystem.depthFunc(GL11.GL_LEQUAL);
            RenderSystem.enableCull();
        } else {
            mesh.close();
        }

        profiler.pop();
    }

    public void renderWorld(WorldRenderContext context) {
        ProfilerFiller worldProfiler = context.profiler();
        worldProfiler.push("sonic");
        worldProfiler.push("world");

        if (client.player == null)
            return;

        if (isPlayerHoldingSonicOf(SonicMode.Modes.TARDIS) && !TardisServerWorld.isTardisDimension(client.player.level()))
            renderSelectedBlock(context);

        worldProfiler.pop();
        worldProfiler.pop();
    }

    private void renderSelectedBlock(WorldRenderContext context) {
        ProfilerFiller worldProfiler = context.profiler();
        worldProfiler.push("target");

        if (!(client.hitResult instanceof BlockHitResult crosshair)) {
            profiler.pop();
            profiler.pop();
            return;
        }

        if (client.player == null || client.level == null) {
            profiler.pop();
            return;
        }

        BlockPos targetPos = crosshair.getBlockPos();
        BlockState state = client.level.getBlockState(targetPos.below());
        if (state.isAir()) {
            profiler.pop();
            return;
        }

        Tardis tardis = SonicItem.getTardisStatic(client.level, getSonicStack(client.player));

        if (tardis == null) {
            profiler.pop();
            return;
        }

        double distance = TardisUtil.distanceFromTardis(client.player, tardis);
        boolean hasEnoughFuel = tardis.fuel().getCurrentFuel() > TardisUtil.estimatedFuelCost(client.player, tardis, distance);

        if(!hasEnoughFuel) {
            renderFloorTexture(targetPos, SELECTED_RED, null, false);
            return;
        }

        renderFloorTexture(targetPos, SELECTED, null, false);

        worldProfiler.pop();
        worldProfiler.pop();
    }

    public void renderGui(GuiGraphics context, float delta) {
        if (client.level == null) return;
        if (!isPlayerHoldingScanningSonic()) return;

        profiler.popPush("sonic");
        profiler.push("gui");

        profiler.push("target");;
        if (!(client.hitResult instanceof BlockHitResult crosshair)) {
            profiler.pop();
            profiler.pop();
            return;
        }
        BlockPos targetPos = crosshair.getBlockPos();
        BlockState state = client.level.getBlockState(targetPos);

        profiler.popPush("redstone");
        renderRedstone(context, state, targetPos);
        profiler.popPush("subsystem_info");
        renderSubSystemInfo(context, targetPos);
        profiler.popPush("detector_type");
        renderDetectorState(context, targetPos);

        profiler.pop();
        profiler.pop();
    }

    private void renderRedstone(GuiGraphics context, BlockState state, BlockPos pos) {
        profiler.push("power");
        renderPower(context, pos);
        profiler.pop();
    }

    private void renderPower(GuiGraphics context, BlockPos pos) {
        int power = this.client.level.getBestNeighborSignal(pos);
        if (power == 0) return;

        context.drawCenteredString(client.font, "" + power, getCentreX(), (int) (getMaxY() * 0.4), CommonColors.WHITE);
    }

    private void renderDetectorState(GuiGraphics context, BlockPos pos) {
        ClientLevel world = client.level;
        if (world == null) return;
        BlockState state = world.getBlockState(pos);
        if (!(state.getBlock() instanceof DetectorBlock)) return;
        DetectorBlock.Type type = state.getValue(DetectorBlock.TYPE);
        context.drawCenteredString(client.font,
                Component.translatable("block.ait.detector.type." + type.name().toLowerCase(Locale.ROOT)), getCentreX(),
                (int) (getMaxY() * 0.4), CommonColors.WHITE);
    }

    private void renderSubSystemInfo(GuiGraphics context, BlockPos pos) {
        if (!(client.level.getBlockEntity(pos) instanceof SubSystemBlockEntity be)) return;

        SubSystem system = be.system();
        if (system == null) return;

        Component text = Component.empty();

        if (system instanceof DurableSubSystem) {
            text = Component.literal((Math.round(((DurableSubSystem) be.system()).durability())) + " / " + DurableSubSystem.MAX_DURABILITY);
        }
        if (!system.isEnabled() && !(system instanceof EngineSystem)) {
            text = Component.translatable("tardis.message.subsystem.requires_link");
        }

        context.drawCenteredString(client.font, text, getCentreX(), (int) (getMaxY() * 0.42), CommonColors.WHITE);

        text = system.name();
        context.drawCenteredString(client.font, text, getCentreX(), (int) (getMaxY() * 0.46), CommonColors.WHITE);
    }

    private int getMaxX() {
        return client.getWindow().getGuiScaledWidth();
    }

    private int getMaxY() {
        return client.getWindow().getGuiScaledHeight() ;
    }


    private int getCentreX() {
        return getMaxX() / 2;
    }

    private int getCentreY() {
        return getMaxY() / 2;
    }

    private int getTextWidth(String text) {
        return client.font.width(text);
    }

    private static SonicRendering INSTANCE;

    public static SonicRendering getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SonicRendering();

        return INSTANCE;
    }

    public static boolean isScanningSonic(ItemStack sonic) {
        return isSonicOf(SonicMode.Modes.SCANNING, sonic);
    }

    public static boolean isSonicOf(SonicMode mode, ItemStack sonic) {
        if (sonic.getItem() instanceof SonicItem)
            return SonicItem.mode(sonic) == mode;

        return false;
    }

    public static boolean isPlayerHoldingScanningSonic() {
        return isPlayerHoldingSonicOf(SonicMode.Modes.SCANNING);
    }

    public static boolean isPlayerHoldingSonicOf(SonicMode mode) {
        Player player = Minecraft.getInstance().player;

        if (player == null)
            return false;

        ItemStack sonic = getSonicStack(player);

        if (sonic == null)
            return false;

        return isSonicOf(mode, sonic);
    }

    public static ItemStack getSonicStack(Player player) {
        if (player.getMainHandItem().getItem() instanceof SonicItem)
            return player.getMainHandItem();

        if (player.getOffhandItem().getItem() instanceof SonicItem)
            return player.getOffhandItem();

        return null;
    }
}
