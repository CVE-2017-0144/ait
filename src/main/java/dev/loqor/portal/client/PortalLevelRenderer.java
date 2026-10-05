package dev.loqor.portal.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

// sky only, terrain is WorldGeometryRenderer's. a real setLevel builds a view area and sodium's whole chunk renderer
public class PortalLevelRenderer extends LevelRenderer {

    public PortalLevelRenderer(Minecraft client, EntityRenderDispatcher entities, BlockEntityRenderDispatcher blockEntities,
            RenderBuffers buffers) {
        super(client, entities, blockEntities, buffers);
    }

    @Override
    public void setLevel(@Nullable ClientLevel level) {
        this.level = level;
    }

    @Override
    public void blockChanged(BlockGetter level, BlockPos pos, BlockState oldState, BlockState newState, int flags) {
    }

    @Override
    public void setBlockDirty(BlockPos pos, BlockState oldState, BlockState newState) {
    }

    @Override
    public void setBlocksDirty(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
    }

    @Override
    public void setSectionDirtyWithNeighbors(int x, int y, int z) {
    }

    @Override
    public void setSectionDirty(int x, int y, int z) {
    }

    @Override
    public void onChunkLoaded(ChunkPos pos) {
    }
}
