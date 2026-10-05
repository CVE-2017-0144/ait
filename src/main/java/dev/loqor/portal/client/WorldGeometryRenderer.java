package dev.loqor.portal.client;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexSorting;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.boti.PortalParticleManager;
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.blockentities.DoorBlockEntity;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.world.TardisServerWorld;
import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorldGeometryRenderer {
    private static final int BUILD_BUDGET = 6;

    private static final long LIGHT_BUDGET_NANOS = 2_000_000L;

    private static final int POOL_RETAIN_FRAMES = 600;
    private int idleFrames = 0;

    private long lastRenderNanos = 0L;

    private boolean skyPassErrorLogged = false;

    private boolean skyInjectErrorLogged = false;

    private static final long BUILD_THREAD_STACK = 32L * 1024 * 1024;

    private final Map<SectionPos, Map<RenderType, VertexBuffer>> sectionBuffers = new HashMap<>();
    private final Map<SectionPos, List<BlockEntity>> sectionBlockEntities = new HashMap<>();

    private final Set<SectionPos> dirtySections = ConcurrentHashMap.newKeySet();
    private boolean needsFullRebuild = true;

    private static final int MAX_BUILD_ATTEMPTS = 3;
    private final Map<SectionPos, Integer> buildAttempts = new ConcurrentHashMap<>();

    private CompletableFuture<Void> buildFuture = null;

    private volatile boolean closed = false;

    private final List<Map<RenderType, ByteBufferBuilder>> builderPool = new ArrayList<>();

    private final ExecutorService buildExecutor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(null, runnable, "BOTI-Geometry-Builder", BUILD_THREAD_STACK);
        thread.setDaemon(true);
        thread.setPriority(Thread.MIN_PRIORITY);
        return thread;
    });

    private final int renderDistance;

    private Vec3 doorNormal = new Vec3(0, 0, -1);
    private Vec3 lastDoorNormal = null;

    private BlockPos centerPos = BlockPos.ZERO;
    private BlockPos lastBuiltCenter = null;
    private Matrix4f portalView = new Matrix4f();
    private Matrix4f portalProjection = new Matrix4f();
    private Matrix4f portalRot = new Matrix4f();
    private Frustum frustum = null;

    private Camera lastPortalCamera = null;
    private ClientLevel lastPortalWorld = null;

    private static final float SKY_FAR_PLANE = 65536.0f * 4.0f;

    private final ByteBufferBuilder immediateBuffer = new ByteBufferBuilder(256);

    private final MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(immediateBuffer);

    private static Vec3 portalSkyCameraPos = null;

    private Vec3 lastExteriorFogColor = null;

    private Vec3 lastEyeWorldPos = null;

    public WorldGeometryRenderer(int renderDistance) {
        this.renderDistance = renderDistance;
    }

    public void markDirty() {
        this.needsFullRebuild = true;
    }

    public void markSectionDirty(SectionPos pos) {
        this.buildAttempts.remove(pos);
        this.dirtySections.add(pos);
    }

    public boolean reclaimIfIdle(long idleNanos) {
        if (closed || sectionBuffers.isEmpty())
            return false;
        if (System.nanoTime() - lastRenderNanos < idleNanos)
            return false;
        if (buildFuture != null && !buildFuture.isDone())
            return false;

        for (Map<RenderType, VertexBuffer> layerBuffers : sectionBuffers.values())
            for (VertexBuffer vbo : layerBuffers.values())
                vbo.close();
        sectionBuffers.clear();
        sectionBlockEntities.clear();
        dirtySections.clear();
        buildAttempts.clear();
        needsFullRebuild = true;
        return true;
    }

    public void setDoorFacing(Direction facing) {
        setDoorNormal(Vec3.atLowerCornerOf(facing.getNormal()));
    }

    public void setDoorNormal(Vec3 normal) {
        Vec3 n = normal.normalize();
        if (lastDoorNormal == null || lastDoorNormal.distanceToSqr(n) > 1.0e-4)
            markDirty();
        this.doorNormal = n;
        this.lastDoorNormal = n;
    }

    public BlockPos centerPos() {
        return this.centerPos;
    }

    public int renderDistance() {
        return this.renderDistance;
    }

    public Vec3 eyeWorldPos() {
        return this.lastEyeWorldPos;
    }

    public Vec3 doorNormal() {
        return this.doorNormal;
    }

    public void render(UUID id, ClientLevel portalWorld, BlockPos centerPos, Vec3 eyeRelToCenter,
                       float portalYaw, float portalPitch, float tickDelta, boolean checkBehindPortal, boolean draw) {
        this.centerPos = centerPos;
        this.lastRenderNanos = System.nanoTime();

        if (!centerPos.equals(this.lastBuiltCenter)) {
            this.lastBuiltCenter = centerPos.immutable();
            markDirty();
        }

        Minecraft client = Minecraft.getInstance();
        GameRenderer gameRenderer = client.gameRenderer;

        this.portalProjection = new Matrix4f(RenderSystem.getProjectionMatrix());

        Matrix4f portalRot = buildPortalRotation(portalYaw, portalPitch);
        this.portalRot = portalRot;
        this.portalView = buildPortalView(portalRot, eyeRelToCenter);

        this.frustum = new Frustum(portalRot, portalProjection);
        this.frustum.prepare(eyeRelToCenter.x, eyeRelToCenter.y, eyeRelToCenter.z);

        pumpBuilds(portalWorld, checkBehindPortal);

        Camera portalCamera = new Camera();
        portalCamera.setPosition(centerPos.getX(), centerPos.getY(), centerPos.getZ());
        portalCamera.setRotation(portalYaw, portalPitch, 0.0F);

        this.lastPortalCamera = portalCamera;
        this.lastPortalWorld = portalWorld;

        Matrix4f originalProjection = new Matrix4f(RenderSystem.getProjectionMatrix());
        RenderSystem.setProjectionMatrix(portalProjection, VertexSorting.DISTANCE_TO_ORIGIN);

        Vec3 eyeWorldPos = new Vec3(centerPos.getX() + eyeRelToCenter.x, centerPos.getY() + eyeRelToCenter.y,
                centerPos.getZ() + eyeRelToCenter.z);
        this.lastEyeWorldPos = eyeWorldPos;

        float[] previousFogColor = RenderSystem.getShaderFogColor().clone();
        float previousFogStart = RenderSystem.getShaderFogStart();
        float previousFogEnd = RenderSystem.getShaderFogEnd();
        FogShape previousFogShape = RenderSystem.getShaderFogShape();
        try {
            this.lastExteriorFogColor = updateExteriorFog(portalWorld, eyeWorldPos, portalYaw, portalPitch, tickDelta, Math.min(client.options.getEffectiveRenderDistance(), this.renderDistance()));
        } catch (Exception e) {
            AITMod.LOGGER.error("BOTI: failed to compute exterior fog", e);
        }

        if (!draw) {
            RenderSystem.setProjectionMatrix(originalProjection, VertexSorting.DISTANCE_TO_ORIGIN);
            restoreFog(client, tickDelta, previousFogColor, previousFogStart, previousFogEnd, previousFogShape);
            return;
        }

        if (!dev.amble.ait.compat.DependencyChecker.isIrisShaderPackInUse()) {
            try {
                renderSky(id, portalWorld, portalRot, portalCamera, eyeWorldPos, tickDelta);
            } catch (Throwable t) {
                if (!skyPassErrorLogged) {
                    AITMod.LOGGER.error("BOTI: sky pass failed (expected under Iris shaders at the END phase - "
                            + "the exterior-fog fill stands in for the sky); further occurrences suppressed", t);
                    skyPassErrorLogged = true;
                }
            }
        }

        LightTexture lightmap = gameRenderer.lightTexture();
        ClientLevel previousLightmapWorld = client.level;
        client.level = portalWorld;
        lightmap.tick();
        lightmap.updateLightTexture(tickDelta);
        client.level = previousLightmapWorld;

        float terrainFogView = Math.max(client.gameRenderer.getRenderDistance(), 32.0f);
        RenderSystem.setShaderFogStart(terrainFogView - Mth.clamp(terrainFogView / 10.0f, 4.0f, 64.0f));
        RenderSystem.setShaderFogEnd(terrainFogView);
        RenderSystem.setShaderFogShape(FogShape.CYLINDER);

        Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        try {
            modelViewStack.set(portalView);
            RenderSystem.applyModelViewMatrix();

            if (!sectionBuffers.isEmpty()) {
                runPass("terrain", this::renderTerrain);
            }

            runPass("block entities", () -> renderBlockEntities(portalWorld, tickDelta, portalCamera));
            runPass("entities", () -> renderEntities(portalWorld, tickDelta, portalCamera));
            runPass("particles", () -> renderParticles(id, portalCamera, tickDelta));
        } finally {
            modelViewStack.popMatrix();
            RenderSystem.applyModelViewMatrix();
            RenderSystem.setProjectionMatrix(originalProjection, VertexSorting.DISTANCE_TO_ORIGIN);

            Camera mainCamera = client.gameRenderer.getMainCamera();
            client.getBlockEntityRenderDispatcher().prepare(previousLightmapWorld, mainCamera, client.hitResult);
            client.getEntityRenderDispatcher().prepare(previousLightmapWorld, mainCamera, client.crosshairPickEntity);


            lightmap.tick();
            lightmap.updateLightTexture(tickDelta);

            restoreFog(client, tickDelta, previousFogColor, previousFogStart, previousFogEnd, previousFogShape);
        }
    }

    private static void restoreFog(Minecraft client, float tickDelta, float[] color, float start, float end, FogShape shape) {
        try {
            FogRenderer.setupColor(client.gameRenderer.getMainCamera(), tickDelta, client.level,
                    client.options.getEffectiveRenderDistance(), client.gameRenderer.getDarkenWorldAmount(tickDelta));
        } catch (Exception e) {
            AITMod.LOGGER.error("BOTI: failed to restore interior fog", e);
        }
        RenderSystem.setShaderFogColor(color[0], color[1], color[2], color[3]);
        RenderSystem.setShaderFogStart(start);
        RenderSystem.setShaderFogEnd(end);
        RenderSystem.setShaderFogShape(shape);
    }

    private void runPass(String name, Runnable pass) {
        try {
            pass.run();
        } catch (Throwable t) {
            AITMod.LOGGER.error("BOTI: '{}' pass failed", name, t);
        }
    }

    private static Matrix4f buildPortalRotation(float yaw, float pitch) {
        return new Matrix4f()
                .rotateX((float) Math.toRadians(pitch))
                .rotateY((float) Math.toRadians(yaw + 180.0f));
    }

    private static Matrix4f buildPortalView(Matrix4f portalRotation, Vec3 eyeRelToCenter) {
        return new Matrix4f(portalRotation).translate(
                (float) -eyeRelToCenter.x, (float) -eyeRelToCenter.y, (float) -eyeRelToCenter.z);
    }

    private void pumpBuilds(Level world, boolean checkBehindPortal) {
        boolean idle = buildFuture == null || buildFuture.isDone();
        if (!idle)
            return;

        if (needsFullRebuild) {
            needsFullRebuild = false;
            enqueueVolume();
        }

        if (dirtySections.isEmpty()) {
            if (!builderPool.isEmpty() && ++idleFrames > POOL_RETAIN_FRAMES) {
                freePool();
                idleFrames = 0;
            }
            return;
        }

        idleFrames = 0;
        List<SectionPos> batch = drainBatch(BUILD_BUDGET);
        if (!batch.isEmpty())
            dispatchBuild(world, batch, checkBehindPortal);
    }

    private void enqueueVolume() {
        buildAttempts.clear();
        Set<SectionPos> volume = computeVolumeSections();

        Iterator<Map.Entry<SectionPos, Map<RenderType, VertexBuffer>>> it = sectionBuffers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<SectionPos, Map<RenderType, VertexBuffer>> entry = it.next();
            if (!volume.contains(entry.getKey())) {
                for (VertexBuffer vbo : entry.getValue().values())
                    vbo.close();
                sectionBlockEntities.remove(entry.getKey());
                it.remove();
            }
        }

        dirtySections.addAll(volume);
    }

    private List<SectionPos> drainBatch(int budget) {
        List<SectionPos> candidates = new ArrayList<>();

        Iterator<SectionPos> it = dirtySections.iterator();
        while (it.hasNext()) {
            SectionPos pos = it.next();
            if (!isSectionInVolume(pos)) {
                it.remove();
                continue;
            }
            candidates.add(pos);
        }

        candidates.sort(Comparator.comparingDouble(this::sectionDistanceSq));

        List<SectionPos> batch = new ArrayList<>(Math.min(budget, candidates.size()));
        for (SectionPos pos : candidates) {
            if (batch.size() >= budget)
                break;
            batch.add(pos);
            dirtySections.remove(pos);
        }

        return batch;
    }

    private void dispatchBuild(Level world, List<SectionPos> batch, boolean checkBehindPortal) {
        if (closed)
            return;

        LevelLightEngine lightingProvider = world.getLightEngine();
        BlockPos.MutableBlockPos lightPos = new BlockPos.MutableBlockPos();
        List<SectionPos> ready = new ArrayList<>(batch.size());
        List<Long2ObjectFunction<ModelData>> modelData = new ArrayList<>(batch.size());
        long lightDeadline = System.nanoTime() + LIGHT_BUDGET_NANOS;
        int scanned = 0;
        for (; scanned < batch.size(); scanned++) {
            SectionPos sectionPos = batch.get(scanned);
            if (world.getChunk(sectionPos.x(), sectionPos.z(), ChunkStatus.FULL, false) == null) {
                dirtySections.add(sectionPos);
                continue;
            }

            int startX = sectionPos.minBlockX(), startY = sectionPos.minBlockY(), startZ = sectionPos.minBlockZ();
            for (int x = startX; x <= startX + 15; x++)
                for (int y = startY; y <= startY + 15; y++)
                    for (int z = startZ; z <= startZ + 15; z++)
                        lightingProvider.checkBlock(lightPos.set(x, y, z));
            ready.add(sectionPos);
            modelData.add(world.getModelDataManager().snapshotSectionRegion(sectionPos.x(), sectionPos.y(), sectionPos.z(),
                    sectionPos.x(), sectionPos.y(), sectionPos.z()));

            if (System.nanoTime() >= lightDeadline)
                break;
        }
        for (int i = scanned + 1; i < batch.size(); i++)
            dirtySections.add(batch.get(i));

        if (ready.isEmpty())
            return;

        lightingProvider.runLightUpdates();
        final List<SectionPos> buildBatch = ready;

        CompletableFuture<Void> applied = new CompletableFuture<>();
        buildFuture = applied;

        buildExecutor.execute(() -> {
            BlockRenderDispatcher blockRenderManager = Minecraft.getInstance().getBlockRenderer();
            RandomSource random = RandomSource.create();

            List<SectionResult> results = new ArrayList<>(buildBatch.size());
            for (int slot = 0; slot < buildBatch.size(); slot++) {
                SectionPos sectionPos = buildBatch.get(slot);

                if (world.getChunk(sectionPos.x(), sectionPos.z(), ChunkStatus.FULL, false) == null) {
                    dirtySections.add(sectionPos);
                    continue;
                }

                try {
                    results.add(buildSection(world, sectionPos, modelData.get(slot), builderSet(slot), blockRenderManager, random, checkBehindPortal));
                } catch (Throwable t) {
                    resetBuilderSet(slot);

                    int attempts = buildAttempts.merge(sectionPos, 1, Integer::sum);
                    if (attempts == 1)
                        AITMod.LOGGER.error("BOTI: failed to build section {} (attempt {})", sectionPos, attempts, t);

                    if (attempts < MAX_BUILD_ATTEMPTS)
                        dirtySections.add(sectionPos);
                }
            }

            Minecraft.getInstance().execute(() -> {
                try {
                    if (closed) {
                        for (SectionResult result : results)
                            for (MeshData built : result.buffers().values())
                                if (built != null)
                                    built.close();
                        return;
                    }
                    for (SectionResult result : results)
                        applySection(result);
                } finally {
                    applied.complete(null);
                }
            });
        });
    }

    private Map<RenderType, ByteBufferBuilder> builderSet(int slot) {
        while (builderPool.size() <= slot)
            builderPool.add(newBuilderSet());
        return builderPool.get(slot);
    }

    private static Map<RenderType, ByteBufferBuilder> newBuilderSet() {
        Map<RenderType, ByteBufferBuilder> set = new HashMap<>();
        for (RenderType layer : RenderType.chunkBufferLayers())
            set.put(layer, new ByteBufferBuilder(layer.bufferSize()));
        return set;
    }

    private void resetBuilderSet(int slot) {
        if (slot < builderPool.size())
            free(builderPool.set(slot, newBuilderSet()));
    }

    private void freePool() {
        for (Map<RenderType, ByteBufferBuilder> set : builderPool)
            free(set);
        builderPool.clear();
    }

    private static void free(Map<RenderType, ByteBufferBuilder> set) {
        for (ByteBufferBuilder builder : set.values())
            builder.close();
    }

    private Set<SectionPos> computeVolumeSections() {
        Set<SectionPos> sections = new HashSet<>();

        int minSectionX = (centerPos.getX() - renderDistance) >> 4;
        int minSectionY = (centerPos.getY() - renderDistance) >> 4;
        int minSectionZ = (centerPos.getZ() - renderDistance) >> 4;
        int maxSectionX = (centerPos.getX() + renderDistance) >> 4;
        int maxSectionY = (centerPos.getY() + renderDistance) >> 4;
        int maxSectionZ = (centerPos.getZ() + renderDistance) >> 4;

        for (int x = minSectionX; x <= maxSectionX; x++)
            for (int y = minSectionY; y <= maxSectionY; y++)
                for (int z = minSectionZ; z <= maxSectionZ; z++) {
                    SectionPos pos = SectionPos.of(x, y, z);
                    if (isSectionInVolume(pos))
                        sections.add(pos);
                }

        return sections;
    }

    private boolean isSectionInVolume(SectionPos pos) {
        double dx = pos.minBlockX() + 8 - centerPos.getX();
        double dy = pos.minBlockY() + 8 - centerPos.getY();
        double dz = pos.minBlockZ() + 8 - centerPos.getZ();

        double reach = renderDistance + 16.0;
        if (dx * dx + dy * dy + dz * dz > reach * reach)
            return false;

        double inFront = dx * doorNormal.x + dy * doorNormal.y + dz * doorNormal.z;
        return inFront > -16.0;
    }

    private double sectionDistanceSq(SectionPos pos) {
        double dx = pos.minBlockX() + 8 - centerPos.getX();
        double dy = pos.minBlockY() + 8 - centerPos.getY();
        double dz = pos.minBlockZ() + 8 - centerPos.getZ();
        return dx * dx + dy * dy + dz * dz;
    }

    private boolean isSectionVisible(SectionPos pos) {
        if (frustum == null)
            return true;

        double minX = pos.minBlockX() - centerPos.getX();
        double minY = pos.minBlockY() - centerPos.getY();
        double minZ = pos.minBlockZ() - centerPos.getZ();

        return frustum.isVisible(new AABB(minX, minY, minZ, minX + 16, minY + 16, minZ + 16));
    }

    public void updatePortalView(Vec3 eyeRelToCenter, float portalYaw, float portalPitch) {
        this.portalProjection = new Matrix4f(RenderSystem.getProjectionMatrix());
        Matrix4f portalRot = buildPortalRotation(portalYaw, portalPitch);
        this.portalRot = portalRot;
        this.portalView = buildPortalView(portalRot, eyeRelToCenter);
        this.frustum = new Frustum(portalRot, portalProjection);
        this.frustum.prepare(eyeRelToCenter.x, eyeRelToCenter.y, eyeRelToCenter.z);
    }

    public void injectSky(UUID id, ClientLevel portalWorld, float tickDelta) {
        if (lastPortalCamera == null || lastEyeWorldPos == null || centerPos == null)
            return;
        PortalData data = PortalDataManager.get(id);
        if (data == null || data.renderer() == null)
            return;

        Matrix4f savedProjection = new Matrix4f(RenderSystem.getProjectionMatrix());
        VertexSorting savedSorter = RenderSystem.getVertexSorting();
        float[] savedFogColor = RenderSystem.getShaderFogColor().clone();
        float savedFogStart = RenderSystem.getShaderFogStart();
        float savedFogEnd = RenderSystem.getShaderFogEnd();
        FogShape savedFogShape = RenderSystem.getShaderFogShape();
        float[] savedShaderColor = RenderSystem.getShaderColor().clone();
        boolean savedBlend = GL11.glIsEnabled(GL11.GL_BLEND);
        boolean savedCull = GL11.glIsEnabled(GL11.GL_CULL_FACE);
        boolean savedDepthTest = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
        boolean savedDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
        int savedDepthFunc = GL11.glGetInteger(GL11.GL_DEPTH_FUNC);

        Object prevPipeline = dev.amble.ait.client.boti.iris.IrisSkyCompat.installMainPipeline(data.renderer());

        Minecraft mc = Minecraft.getInstance();
        ClientLevel prevWorld = mc.level;
        mc.level = portalWorld;
        dev.amble.ait.client.boti.iris.IrisSkyCompat.resampleFrameUniforms();
        try {
            renderSky(id, portalWorld, portalRot, lastPortalCamera, lastEyeWorldPos, tickDelta);
        } catch (Throwable t) {
            if (!skyInjectErrorLogged) {
                AITMod.LOGGER.error("BOTI: doorway sky injection (AFTER_ENTITIES) failed; falling back to the fog "
                        + "backdrop; further occurrences suppressed", t);
                skyInjectErrorLogged = true;
            }
        } finally {
            mc.level = prevWorld;
            dev.amble.ait.client.boti.iris.IrisSkyCompat.resampleFrameUniforms();
            dev.amble.ait.client.boti.iris.IrisSkyCompat.restore(data.renderer(), prevPipeline);

            RenderSystem.setProjectionMatrix(savedProjection, savedSorter);
            RenderSystem.setShaderFogColor(savedFogColor[0], savedFogColor[1], savedFogColor[2], savedFogColor[3]);
            RenderSystem.setShaderFogStart(savedFogStart);
            RenderSystem.setShaderFogEnd(savedFogEnd);
            RenderSystem.setShaderFogShape(savedFogShape);
            RenderSystem.setShaderColor(savedShaderColor[0], savedShaderColor[1], savedShaderColor[2], savedShaderColor[3]);
            if (savedBlend) RenderSystem.enableBlend(); else RenderSystem.disableBlend();
            if (savedCull) RenderSystem.enableCull(); else RenderSystem.disableCull();
            if (savedDepthTest) RenderSystem.enableDepthTest(); else RenderSystem.disableDepthTest();
            RenderSystem.depthMask(savedDepthMask);
            RenderSystem.depthFunc(savedDepthFunc);
        }
    }

    private void renderTerrain() {
        List<Map<RenderType, VertexBuffer>> visible = new ArrayList<>();
        for (Map.Entry<SectionPos, Map<RenderType, VertexBuffer>> entry : sectionBuffers.entrySet()) {
            if (isSectionVisible(entry.getKey()))
                visible.add(entry.getValue());
        }

        if (visible.isEmpty())
            return;

        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        for (RenderType layer : RenderType.chunkBufferLayers()) {
            if (layer == RenderType.translucent())
                continue;

            drawLayer(layer, visible);
        }

        drawLayer(RenderType.translucent(), visible);

        RenderSystem.disableBlend();
    }

    private void drawLayer(RenderType layer, List<Map<RenderType, VertexBuffer>> visible) {
        int target = GlStateManager.getBoundFramebuffer();
        layer.setupRenderState();
        restoreTarget(target);

        for (Map<RenderType, VertexBuffer> layerBuffers : visible) {
            VertexBuffer vbo = layerBuffers.get(layer);
            if (vbo != null) {
                vbo.bind();
                vbo.drawWithShader(portalView, portalProjection, RenderSystem.getShader());
            }
        }

        VertexBuffer.unbind();
        layer.clearRenderState();
        restoreTarget(target);
    }

    private static void restoreTarget(int target) {
        if (Minecraft.useShaderTransparency())
            GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, target);
    }

    public void debugInjectTerrainIntoGbuffer() {
        if (sectionBuffers.isEmpty())
            return;

        List<Map<RenderType, VertexBuffer>> visible = new ArrayList<>();
        for (Map.Entry<SectionPos, Map<RenderType, VertexBuffer>> entry : sectionBuffers.entrySet()) {
            if (isSectionVisible(entry.getKey()))
                visible.add(entry.getValue());
        }
        if (visible.isEmpty())
            return;

        RenderSystem.enableDepthTest();
        boolean prevDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
        RenderSystem.depthMask(true);

        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);

        boolean phased = dev.amble.ait.client.boti.iris.IrisPhase.setTerrainSolid();
        try {
            drawLayer(RenderType.solid(), visible);
        } finally {
            if (phased)
                dev.amble.ait.client.boti.iris.IrisPhase.reset();
        }

        phased = dev.amble.ait.client.boti.iris.IrisPhase.setTerrainCutoutMipped();
        try {
            drawLayer(RenderType.cutoutMipped(), visible);
        } finally {
            if (phased)
                dev.amble.ait.client.boti.iris.IrisPhase.reset();
        }

        phased = dev.amble.ait.client.boti.iris.IrisPhase.setTerrainCutout();
        try {
            drawLayer(RenderType.cutout(), visible);
        } finally {
            if (phased)
                dev.amble.ait.client.boti.iris.IrisPhase.reset();
        }

        RenderSystem.depthMask(prevDepthMask);
    }

    public void debugInjectTranslucentIntoGbuffer() {
        if (sectionBuffers.isEmpty())
            return;

        List<Map<RenderType, VertexBuffer>> visible = new ArrayList<>();
        for (Map.Entry<SectionPos, Map<RenderType, VertexBuffer>> entry : sectionBuffers.entrySet()) {
            if (isSectionVisible(entry.getKey()))
                visible.add(entry.getValue());
        }
        if (visible.isEmpty())
            return;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        boolean prevDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
        RenderSystem.depthMask(false);

        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);

        boolean phased = dev.amble.ait.client.boti.iris.IrisPhase.setTerrainTranslucent();
        try {
            drawLayer(RenderType.translucent(), visible);
        } finally {
            if (phased)
                dev.amble.ait.client.boti.iris.IrisPhase.reset();
        }

        RenderSystem.depthMask(prevDepthMask);
        RenderSystem.disableBlend();
    }

    public void injectBlockEntitiesAndEntities(float tickDelta) {
        if (lastPortalCamera == null || lastPortalWorld == null || centerPos == null)
            return;

        RenderSystem.enableDepthTest();
        boolean prevDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
        RenderSystem.depthMask(true);

        Matrix4f originalProjection = new Matrix4f(RenderSystem.getProjectionMatrix());
        RenderSystem.setProjectionMatrix(portalProjection, VertexSorting.DISTANCE_TO_ORIGIN);

        Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        try {
            modelViewStack.set(portalView);
            RenderSystem.applyModelViewMatrix();

            boolean p1 = dev.amble.ait.client.boti.iris.IrisPhase.setBlockEntities();
            try {
                renderBlockEntities(lastPortalWorld, tickDelta, lastPortalCamera);
            } finally {
                if (p1) dev.amble.ait.client.boti.iris.IrisPhase.reset();
            }

            boolean p2 = dev.amble.ait.client.boti.iris.IrisPhase.setEntities();
            try {
                renderEntities(lastPortalWorld, tickDelta, lastPortalCamera);
            } finally {
                if (p2) dev.amble.ait.client.boti.iris.IrisPhase.reset();
            }

        } finally {
            modelViewStack.popMatrix();
            RenderSystem.applyModelViewMatrix();
            RenderSystem.setProjectionMatrix(originalProjection, VertexSorting.DISTANCE_TO_ORIGIN);

            Minecraft client = Minecraft.getInstance();
            Camera mainCamera = client.gameRenderer.getMainCamera();
            client.getBlockEntityRenderDispatcher().prepare(client.level, mainCamera, client.hitResult);
            client.getEntityRenderDispatcher().prepare(client.level, mainCamera, client.crosshairPickEntity);

            RenderSystem.depthMask(prevDepthMask);
        }
    }

    private void renderSky(UUID id, ClientLevel portalWorld, Matrix4f portalRotation, Camera portalCamera,
                           Vec3 eyeWorldPos, float tickDelta) {
        PortalData data = PortalDataManager.get(id);
        if (data == null || data.renderer() == null)
            return;

        Minecraft client = Minecraft.getInstance();
        ClientLevel previousWorld = client.level;
        // renderLevel never runs on this renderer, so iris never hands it a pipeline
        Object irisPipeline = dev.amble.ait.compat.DependencyChecker.hasIris()
                ? dev.amble.ait.client.boti.iris.IrisSkyCompat.installMainPipeline(data.renderer()) : null;

        Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        modelViewStack.identity();
        RenderSystem.applyModelViewMatrix();

        Camera gameCamera = client.gameRenderer.getMainCamera();
        Vec3 savedCamPos = gameCamera.getPosition();
        float savedCamYaw = gameCamera.getYRot();
        float savedCamPitch = gameCamera.getXRot();
        float savedCamRoll = gameCamera.getRoll();
        gameCamera.setPosition(eyeWorldPos.x, eyeWorldPos.y, eyeWorldPos.z);
        gameCamera.setRotation(portalCamera.getYRot(), portalCamera.getXRot(), 0.0F);

        try {
            client.level = portalWorld;
            portalSkyCameraPos = eyeWorldPos;

            PoseStack skyStack = new PoseStack();
            skyStack.mulPose(portalRotation);

            RenderSystem.depthMask(false);

            Matrix4f skyProjection = portalProjection;
            try {
                double fovDeg = client.gameRenderer.getFov(portalCamera, tickDelta, true);
                float aspect = (float) client.getWindow().getWidth()
                        / (float) client.getWindow().getHeight();
                skyProjection = new Matrix4f().setPerspective((float) (fovDeg * (Math.PI / 180.0)), aspect, 0.05f,
                        SKY_FAR_PLANE);
            } catch (Exception e) {
                AITMod.LOGGER.error("BOTI: failed to build sky projection; far skyboxes may be clipped", e);
            }
            RenderSystem.setProjectionMatrix(skyProjection, VertexSorting.DISTANCE_TO_ORIGIN);

            RenderSystem.setShader(GameRenderer::getPositionShader);

            float viewDistanceBlocks = Math.max(client.gameRenderer.getRenderDistance(), 32.0f);
            data.renderer().renderSky(skyStack.last().pose(), skyProjection, tickDelta, portalCamera, false, () -> {
                RenderSystem.setShaderFogStart(0.0f);
                RenderSystem.setShaderFogEnd(viewDistanceBlocks);
                RenderSystem.setShaderFogShape(FogShape.CYLINDER);
            });

            RenderSystem.setShaderFogStart(viewDistanceBlocks - Mth.clamp(viewDistanceBlocks / 10.0f, 4.0f, 64.0f));
            RenderSystem.setShaderFogEnd(viewDistanceBlocks);
            RenderSystem.setShaderFogShape(FogShape.CYLINDER);

            if (client.options.getCloudsType() != CloudStatus.OFF
                    && !TardisServerWorld.isTardisDimension(portalWorld) && portalWorld.dimension() != AITDimensions.TIME_VORTEX_WORLD)
                renderPortalClouds(portalWorld, portalRotation, tickDelta, eyeWorldPos);
        } finally {
            if (irisPipeline != null)
                dev.amble.ait.client.boti.iris.IrisSkyCompat.restore(data.renderer(), irisPipeline);
            portalSkyCameraPos = null;
            client.level = previousWorld;
            gameCamera.setPosition(savedCamPos.x, savedCamPos.y, savedCamPos.z);
            gameCamera.setRotation(savedCamYaw, savedCamPitch, savedCamRoll);
            modelViewStack.popMatrix();
            RenderSystem.applyModelViewMatrix();
            RenderSystem.depthMask(true);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setProjectionMatrix(portalProjection, VertexSorting.DISTANCE_TO_ORIGIN);
        }
    }

    public static Vec3 updateExteriorFog(ClientLevel portalWorld, Vec3 eyePos, float yaw, float pitch,
                                         float tickDelta, int renderDistance) {
        Minecraft client = Minecraft.getInstance();

        Camera fogCamera = new Camera();
        double fogY = Math.max(eyePos.y, portalWorld.getMinBuildHeight() + 34.0);
        fogCamera.setPosition(eyePos.x, fogY, eyePos.z);
        fogCamera.setRotation(yaw, pitch, 0.0F);

        FogRenderer.setupColor(fogCamera, tickDelta, portalWorld, renderDistance,
                client.gameRenderer.getDarkenWorldAmount(tickDelta));
        FogRenderer.levelFogColor();

        float[] fog = RenderSystem.getShaderFogColor();
        return new Vec3(fog[0], fog[1], fog[2]);
    }

    public Vec3 exteriorFogColor() {
        return this.lastExteriorFogColor;
    }

    public static Vec3 getPortalSkyCameraPos() {
        return portalSkyCameraPos;
    }

    private static final ResourceLocation CLOUDS_TEXTURE = ResourceLocation.parse("textures/environment/clouds.png");

    private void renderPortalClouds(ClientLevel world, Matrix4f cloudRotation, float tickDelta, Vec3 eyePos) {
        float cloudHeight = world.effects().getCloudHeight();
        if (Float.isNaN(cloudHeight))
            return;

        double drift = (world.getGameTime() + tickDelta) * 0.03;
        double ox = (eyePos.x + drift) / 12.0;
        double oy = cloudHeight - eyePos.y + 0.33;
        double oz = eyePos.z / 12.0 + 0.33;
        ox -= Mth.floor(ox / 2048.0) * 2048;
        oz -= Mth.floor(oz / 2048.0) * 2048;
        float fracX = (float) (ox - Mth.floor(ox));
        float fracY = (float) (oy / 4.0 - Mth.floor(oy / 4.0)) * 4.0F;
        float fracZ = (float) (oz - Mth.floor(oz));

        Vec3 color = world.getCloudColor(tickDelta);
        float cr = (float) color.x, cg = (float) color.y, cb = (float) color.z;
        float g = 0.00390625F;
        float texX = Mth.floor(ox) * g;
        float texZ = Mth.floor(oz) * g;
        float y = (float) Math.floor(oy / 4.0) * 4.0F;

        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL);
        for (int qx = -32; qx < 32; qx += 32) {
            for (int qz = -32; qz < 32; qz += 32) {
                builder.addVertex(qx, y, qz + 32).setUv(qx * g + texX, (qz + 32) * g + texZ).setColor(cr, cg, cb, 0.8F).setNormal(0.0F, -1.0F, 0.0F);
                builder.addVertex(qx + 32, y, qz + 32).setUv((qx + 32) * g + texX, (qz + 32) * g + texZ).setColor(cr, cg, cb, 0.8F).setNormal(0.0F, -1.0F, 0.0F);
                builder.addVertex(qx + 32, y, qz).setUv((qx + 32) * g + texX, qz * g + texZ).setColor(cr, cg, cb, 0.8F).setNormal(0.0F, -1.0F, 0.0F);
                builder.addVertex(qx, y, qz).setUv(qx * g + texX, qz * g + texZ).setColor(cr, cg, cb, 0.8F).setNormal(0.0F, -1.0F, 0.0F);
            }
        }
        MeshData built = builder.buildOrThrow();

        RenderSystem.setShader(GameRenderer::getRendertypeCloudsShader);
        RenderSystem.setShaderTexture(0, CLOUDS_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);

        Matrix4fStack modelView = RenderSystem.getModelViewStack();
        modelView.pushMatrix();
        modelView.set(cloudRotation);
        modelView.scale(12.0F, 1.0F, 12.0F);
        modelView.translate(-fracX, fracY, -fracZ);
        RenderSystem.applyModelViewMatrix();
        try {
            BufferUploader.drawWithShader(built);
        } finally {
            modelView.popMatrix();
            RenderSystem.applyModelViewMatrix();
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        }
    }

    private void renderBlockEntities(ClientLevel portalWorld, float tickDelta, Camera portalCamera) {
        Minecraft client = Minecraft.getInstance();
        BlockEntityRenderDispatcher dispatcher = client.getBlockEntityRenderDispatcher();

        dispatcher.prepare(portalWorld, portalCamera, client.hitResult);

        int target = GlStateManager.getBoundFramebuffer();
        PoseStack matrices = new PoseStack();
        AABB cameraBox = new AABB(portalCamera.getBlockPosition());

        for (List<BlockEntity> sectionEntities : sectionBlockEntities.values()) {
            for (BlockEntity blockEntity : sectionEntities) {
                BlockPos blockPos = blockEntity.getBlockPos();

                if (!isWithinRenderBounds(blockPos))
                    continue;

                if ((blockEntity instanceof DoorBlockEntity || blockEntity instanceof ExteriorBlockEntity) && cameraBox.contains(blockPos.getCenter()))
                    continue;

                matrices.pushPose();
                matrices.translate(
                        blockPos.getX() - centerPos.getX(),
                        blockPos.getY() - centerPos.getY(),
                        blockPos.getZ() - centerPos.getZ());

                try {
                    restoreTarget(target);
                    dispatcher.render(blockEntity, tickDelta, matrices, immediate);
                } catch (Throwable t) {
                    AITMod.LOGGER.error("BOTI: failed to render block entity {}", blockEntity, t);
                } finally {
                    matrices.popPose();
                }
            }
        }

        immediate.endBatch();
        restoreTarget(target);
    }

    private void renderEntities(ClientLevel portalWorld, float tickDelta, Camera portalCamera) {
        Minecraft client = Minecraft.getInstance();
        EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();

        dispatcher.prepare(portalWorld, portalCamera, client.crosshairPickEntity);

        int target = GlStateManager.getBoundFramebuffer();
        RenderSystem.polygonOffset(-1.0f, -10.0f);
        RenderSystem.enablePolygonOffset();
        try {
            PoseStack matrices = new PoseStack();

            for (Entity entity : portalWorld.entitiesForRendering()) {
                if (entity == null || !isWithinRenderBounds(entity.blockPosition()))
                    continue;

                double x = Mth.lerp(tickDelta, entity.xOld, entity.getX()) - centerPos.getX();
                double y = Mth.lerp(tickDelta, entity.yOld, entity.getY()) - centerPos.getY();
                double z = Mth.lerp(tickDelta, entity.zOld, entity.getZ()) - centerPos.getZ();
                float yaw = Mth.lerp(tickDelta, entity.yRotO, entity.getYRot());

                try {
                    int light = dispatcher.getPackedLightCoords(entity, tickDelta);
                    restoreTarget(target);
                    dispatcher.render(entity, x, y, z, yaw, tickDelta, matrices, immediate, light);
                } catch (Throwable t) {
                    AITMod.LOGGER.error("BOTI: failed to render entity {}", entity, t);
                }
            }

            immediate.endBatch();
            restoreTarget(target);
        } finally {
            RenderSystem.polygonOffset(0.0f, 0.0f);
            RenderSystem.disablePolygonOffset();
        }
    }

    private void renderParticles(UUID id, Camera portalCamera, float tickDelta) {
        PortalParticleManager manager = PortalDataManager.particles(id);
        if (manager == null)
            return;

        Minecraft client = Minecraft.getInstance();

        int target = GlStateManager.getBoundFramebuffer();
        manager.render(client.gameRenderer.lightTexture(), portalCamera, tickDelta);
        immediate.endBatch();
        restoreTarget(target);
    }

    private boolean isWithinRenderBounds(BlockPos blockPos) {
        return blockPos.getX() >= centerPos.getX() - renderDistance
                && blockPos.getX() <= centerPos.getX() + renderDistance
                && blockPos.getY() >= centerPos.getY() - renderDistance
                && blockPos.getY() <= centerPos.getY() + renderDistance
                && blockPos.getZ() >= centerPos.getZ() - renderDistance
                && blockPos.getZ() <= centerPos.getZ() + renderDistance;
    }

    private SectionResult buildSection(Level world, SectionPos sectionPos, Long2ObjectFunction<ModelData> modelData, Map<RenderType, ByteBufferBuilder> buffers,
                                       BlockRenderDispatcher blockRenderManager, RandomSource random, boolean checkBehindPortal) {

        int startX = sectionPos.minBlockX();
        int startY = sectionPos.minBlockY();
        int startZ = sectionPos.minBlockZ();
        int endX = startX + 15;
        int endY = startY + 15;
        int endZ = startZ + 15;

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        Set<RenderType> usedLayers = new HashSet<>();

        Map<RenderType, BufferBuilder> builders = new HashMap<>();
        for (RenderType layer : RenderType.chunkBufferLayers())
            builders.put(layer, new BufferBuilder(buffers.get(layer), VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK));

        PoseStack matrices = new PoseStack();
        List<BlockEntity> foundBlockEntities = new ArrayList<>();

        double fluidOffsetX = startX - centerPos.getX();
        double fluidOffsetY = startY - centerPos.getY();
        double fluidOffsetZ = startZ - centerPos.getZ();
        Map<RenderType, OffsetVertexConsumer> fluidConsumers = new HashMap<>();

        boolean hasBlocks = false;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    mutablePos.set(x, y, z);

                    BlockState state = world.getBlockState(mutablePos);

                    if (state.isAir())
                        continue;

                    double relX = x - centerPos.getX();
                    double relY = y - centerPos.getY();
                    double relZ = z - centerPos.getZ();

                    if (checkBehindPortal && isBehindPortal(relX, relY, relZ))
                        continue;

                    if (isFullySurrounded(world, mutablePos))
                        continue;

                    hasBlocks = true;

                    if (state.hasBlockEntity()) {
                        BlockEntity blockEntity = world.getBlockEntity(mutablePos);
                        if (blockEntity != null)
                            foundBlockEntities.add(blockEntity);
                    }

                    FluidState fluidState = state.getFluidState();
                    if (!fluidState.isEmpty()) {
                        RenderType fluidLayer = ItemBlockRenderTypes.getRenderLayer(fluidState);
                        usedLayers.add(fluidLayer);

                        OffsetVertexConsumer fluidConsumer = fluidConsumers.computeIfAbsent(fluidLayer,
                                layer -> new OffsetVertexConsumer(builders.get(layer),
                                        fluidOffsetX, fluidOffsetY, fluidOffsetZ));

                        blockRenderManager.renderLiquid(mutablePos, world, fluidConsumer, state, fluidState);
                    }

                    if (state.getRenderShape() != RenderShape.INVISIBLE) {
                        BakedModel model = blockRenderManager.getBlockModel(state);
                        ModelData data = model.getModelData(world, mutablePos, state, modelData.get(mutablePos.asLong()));
                        random.setSeed(state.getSeed(mutablePos));

                        for (RenderType blockLayer : model.getRenderTypes(state, random, data)) {
                            BufferBuilder builder = builders.get(blockLayer);
                            usedLayers.add(blockLayer);

                            if (state.getRenderShape() != RenderShape.MODEL)
                                continue;

                            matrices.pushPose();
                            matrices.translate(relX, relY, relZ);

                            blockRenderManager.renderBatched(state, mutablePos, world, matrices, builder, true, random,
                                    data, blockLayer);

                            matrices.popPose();
                        }
                    }
                }
            }
        }

        Map<RenderType, MeshData> builtBuffers = new HashMap<>();

        for (RenderType layer : RenderType.chunkBufferLayers()) {
            MeshData built = builders.get(layer).build();

            // an empty layer builds to null but still keeps the section and its block entities
            if (hasBlocks && usedLayers.contains(layer))
                builtBuffers.put(layer, built);
            else if (built != null)
                built.close();
        }

        return new SectionResult(sectionPos, builtBuffers, foundBlockEntities);
    }

    private boolean isBehindPortal(double relX, double relY, double relZ) {
        return relX * doorNormal.x + relY * doorNormal.y + relZ * doorNormal.z < 0.0;
    }

    private void applySection(SectionResult result) {
        SectionPos pos = result.pos();
        buildAttempts.remove(pos);

        Map<RenderType, VertexBuffer> old = sectionBuffers.remove(pos);
        if (old != null) {
            for (VertexBuffer vbo : old.values())
                vbo.close();
        }

        if (result.buffers().isEmpty()) {
            sectionBlockEntities.remove(pos);
            return;
        }

        Map<RenderType, VertexBuffer> layerBuffers = new HashMap<>();
        for (Map.Entry<RenderType, MeshData> entry : result.buffers().entrySet()) {
            if (entry.getValue() == null)
                continue;

            VertexBuffer vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
            vbo.bind();
            vbo.upload(entry.getValue());
            VertexBuffer.unbind();

            layerBuffers.put(entry.getKey(), vbo);
        }

        sectionBuffers.put(pos, layerBuffers);

        if (result.blockEntities().isEmpty())
            sectionBlockEntities.remove(pos);
        else
            sectionBlockEntities.put(pos, result.blockEntities());
    }

    public void dropSection(SectionPos pos) {
        dirtySections.remove(pos);
        buildAttempts.remove(pos);

        Map<RenderType, VertexBuffer> old = sectionBuffers.remove(pos);
        if (old != null) {
            for (VertexBuffer vbo : old.values())
                vbo.close();
        }

        sectionBlockEntities.remove(pos);
    }

    private void clearBuffers() {
        for (Map<RenderType, VertexBuffer> layerMap : sectionBuffers.values()) {
            for (VertexBuffer vbo : layerMap.values())
                vbo.close();
        }

        sectionBuffers.clear();
        sectionBlockEntities.clear();
    }

    private boolean isFullySurrounded(Level world, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos adjacent = pos.relative(dir);
            BlockState adjacentState = world.getBlockState(adjacent);
            if (!adjacentState.isSolidRender(world, adjacent))
                return false;
        }
        return true;
    }

    public void close() {
        closed = true;
        clearBuffers();
        immediateBuffer.close();
        buildExecutor.execute(this::freePool);
        buildExecutor.shutdown();
    }

    public int getSectionCount() {
        return sectionBuffers.size();
    }

    public int getBlockEntityCount() {
        int count = 0;
        for (List<BlockEntity> sectionEntities : sectionBlockEntities.values())
            count += sectionEntities.size();
        return count;
    }

    private record SectionResult(SectionPos pos, Map<RenderType, MeshData> buffers,
                                 List<BlockEntity> blockEntities) {
    }

    private class HybridRenderView implements BlockAndTintGetter {
        private final Level fakeWorld;
        private final Level realWorld;

        public HybridRenderView(Level fakeWorld) {
            this.fakeWorld = fakeWorld;
            this.realWorld = Minecraft.getInstance().level;
        }

        @Override
        public int getBrightness(LightLayer type, BlockPos pos) {
            if (realWorld == null) return 15;
            return realWorld.getBrightness(type, pos);
        }

        @Override
        public int getRawBrightness(BlockPos pos, int ambientDarkness) {
            if (realWorld == null) return 15728880;
            return realWorld.getRawBrightness(pos, ambientDarkness);
        }

        @Override
        public float getShade(Direction direction, boolean shaded) {
            if (realWorld == null) return 1.0f;
            return realWorld.getShade(direction, shaded);
        }

        @Override
        public LevelLightEngine getLightEngine() {
            return realWorld != null ? realWorld.getLightEngine() : fakeWorld.getLightEngine();
        }

        @Nullable
        @Override
        public BlockEntity getBlockEntity(BlockPos pos) {
            return fakeWorld.getBlockEntity(pos);
        }

        @Override
        public BlockState getBlockState(BlockPos pos) {
            return fakeWorld.getBlockState(pos);
        }

        @Override
        public FluidState getFluidState(BlockPos pos) {
            return fakeWorld.getFluidState(pos);
        }

        @Override
        public int getBlockTint(BlockPos pos, ColorResolver colorResolver) {
            return fakeWorld.getBlockTint(pos, colorResolver);
        }

        @Override
        public int getHeight() { return fakeWorld.getHeight(); }

        @Override
        public int getMinBuildHeight() { return fakeWorld.getMinBuildHeight(); }
    }
}
