package dev.amble.ait.client.boti;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import dev.amble.ait.core.tardis.util.network.BOTISnapshot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public class BOTIVBO implements AutoCloseable {

    private final Map<RenderType, VertexBuffer> buffers = new HashMap<>();

    public boolean isEmpty() {
        return this.buffers.isEmpty();
    }

    public void bake(BOTISnapshot snapshot) {
        this.close();

        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
        BOTISnapshotView view = new BOTISnapshotView(snapshot);
        RandomSource random = RandomSource.create();
        PoseStack pose = new PoseStack();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (RenderType layer : RenderType.chunkBufferLayers()) {
            ByteBufferBuilder allocation = new ByteBufferBuilder(layer.bufferSize());
            BufferBuilder builder = new BufferBuilder(allocation, layer.mode(), layer.format());
            boolean any = false;

            for (int y = -BOTISnapshot.BELOW; y <= BOTISnapshot.ABOVE; y++) {
                for (int z = -BOTISnapshot.RADIUS_XZ; z <= BOTISnapshot.RADIUS_XZ; z++) {
                    for (int x = -BOTISnapshot.RADIUS_XZ; x <= BOTISnapshot.RADIUS_XZ; x++) {
                        BlockState state = snapshot.get(x, y, z);

                        if (state.isAir() || state.getRenderShape() != RenderShape.MODEL)
                            continue;

                        if (ItemBlockRenderTypes.getChunkRenderType(state) != layer)
                            continue;

                        cursor.set(x, y, z);
                        pose.pushPose();
                        pose.translate(x, y, z);
                        dispatcher.renderBatched(state, cursor, view, pose, builder, true, random);
                        pose.popPose();

                        any = true;
                    }
                }
            }

            MeshData mesh = any ? builder.build() : null;

            if (mesh == null) {
                allocation.close();
                continue;
            }

            VertexBuffer buffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
            buffer.bind();
            buffer.upload(mesh);
            VertexBuffer.unbind();
            allocation.close();

            this.buffers.put(layer, buffer);
        }
    }

    public void draw(PoseStack stack) {
        if (this.buffers.isEmpty())
            return;

        Matrix4f modelView = new Matrix4f(RenderSystem.getModelViewMatrix()).mul(stack.last().pose());
        Matrix4f projection = RenderSystem.getProjectionMatrix();

        for (Map.Entry<RenderType, VertexBuffer> entry : this.buffers.entrySet()) {
            RenderType layer = entry.getKey();
            layer.setupRenderState();

            ShaderInstance shader = RenderSystem.getShader();

            if (shader != null) {
                entry.getValue().bind();
                entry.getValue().drawWithShader(modelView, projection, shader);
                VertexBuffer.unbind();
            }

            layer.clearRenderState();
        }
    }

    @Override
    public void close() {
        this.buffers.values().forEach(VertexBuffer::close);
        this.buffers.clear();
    }
}
