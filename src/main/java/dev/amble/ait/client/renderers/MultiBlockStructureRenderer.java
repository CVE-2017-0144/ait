package dev.amble.ait.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.core.engine.block.multi.MultiBlockStructure;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;


public class MultiBlockStructureRenderer {

    private final Minecraft client;
    private final ProfilerFiller profiler;

    protected MultiBlockStructureRenderer(Minecraft client) {
        this.client = client;
        this.profiler = client.getProfiler();
    }

    private MultiBlockStructureRenderer() {
        this(Minecraft.getInstance());
    }

    public void render(MultiBlockStructure structure, BlockPos centre, BlockAndTintGetter view, PoseStack matrices, MultiBufferSource provider, boolean holographic) {
        profiler.push("multi_block_structure");
        profiler.push("iterate_offsets");
        structure.forEach(offset -> renderOffset(offset, centre, view, matrices, provider, holographic));
    }

    public void renderForInterior(MultiBlockStructure structure, BlockPos centre, BlockAndTintGetter view, PoseStack matrices, MultiBufferSource provider, boolean holographic) {
        profiler.push("multi_block_structure");
        profiler.push("iterate_offsets");
        structure.forEach(offset -> renderOffsetInterior(offset, centre, view, matrices, provider, holographic));
        profiler.pop();
        profiler.pop();
    }

    public void renderOffsetInterior(MultiBlockStructure.BlockOffset offset, BlockPos centre, BlockAndTintGetter view, PoseStack matrices, MultiBufferSource provider, boolean holographic) {
        ClientLevel world = Minecraft.getInstance().level;
        if (world == null) return;

        BlockPos pos = centre.offset(offset.offset());
        BlockPos diff = pos.subtract(centre);
        BlockState state = this.getBlock(offset.block()).defaultBlockState();
        BlockEntity entity = world.getBlockEntity(offset.offset());

        matrices.pushPose();
        matrices.translate(diff.getX(), diff.getY(), diff.getZ());

        if (holographic) {
            matrices.scale(0.35f, 0.35f, 0.35f);
            matrices.translate(0.95, 1, 1);
        }

        renderCulledBlocks(state, pos, view, matrices, provider);
        if (entity != null) {
            renderBlockEntities(entity, matrices, provider);
        }
        matrices.popPose();
    }

    private void renderCulledBlocks(BlockState state, BlockPos pos, BlockAndTintGetter view, PoseStack matrices, MultiBufferSource provider) {
        BakedModel model = client.getBlockRenderer().getBlockModel(state);
        RandomSource random = client.level.random;
        random.setSeed(state.getSeed(pos));

        for (RenderType layer : model.getRenderTypes(state, random, ModelData.EMPTY)) {
            client.getBlockRenderer().renderBatched(state, pos, view, matrices, provider.getBuffer(layer), true,
                    random, ModelData.EMPTY, layer);
        }
    }

    private void renderBlockEntities(BlockEntity entity,  PoseStack matrices, MultiBufferSource provider) {
        client.getBlockEntityRenderDispatcher().render(entity, Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true), matrices, provider);
    }

    public void renderOffset(MultiBlockStructure.BlockOffset offset, BlockPos centre, BlockAndTintGetter view, PoseStack matrices, MultiBufferSource provider, boolean holographic) {
        BlockPos pos = centre.offset(offset.offset());

        BlockPos diff = pos.subtract(centre);
        BlockState state = this.getBlock(offset.block()).defaultBlockState();

        matrices.pushPose();
        matrices.translate(diff.getX(), diff.getY(), diff.getZ());

        if (holographic) {
            matrices.scale(0.35f, 0.35f, 0.35f);
            matrices.translate(0.95, 1, 1);
        }

        renderBlock(state, pos, view, matrices, provider);
        matrices.popPose();
    }

    private void renderBlock(BlockState state, BlockPos pos, BlockAndTintGetter view, PoseStack matrices, MultiBufferSource provider) {
        BakedModel model = client.getBlockRenderer().getBlockModel(state);
        RandomSource random = client.level.random;
        long seed = state.getSeed(pos);
        random.setSeed(seed);

        for (RenderType layer : model.getRenderTypes(state, random, ModelData.EMPTY)) {
            client.getBlockRenderer().getModelRenderer().tesselateBlock(view, model, state, pos, matrices,
                    provider.getBuffer(layer), false, random, seed, OverlayTexture.NO_OVERLAY,
                    ModelData.EMPTY, layer);
        }
    }

    private Block getBlock(MultiBlockStructure.AllowedBlocks block) {
        return (Block) block.toArray()[0];
    }

    private static MultiBlockStructureRenderer INSTANCE;

    public static MultiBlockStructureRenderer instance() {
        if (INSTANCE == null) {
            INSTANCE = new MultiBlockStructureRenderer();
        }
        return INSTANCE;
    }
}
