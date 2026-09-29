package dev.loqor.portal.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;

public class PortalLevelRenderer extends LevelRenderer {

    public PortalLevelRenderer(Minecraft client, EntityRenderDispatcher entities, BlockEntityRenderDispatcher blockEntities,
            RenderBuffers buffers) {
        super(client, entities, blockEntities, buffers);
    }
}
