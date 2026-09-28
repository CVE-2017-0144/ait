package dev.amble.ait.client.renderers.entities;


import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.boti.BOTI;
import dev.amble.ait.compat.portal.PortalsAPI;
import dev.amble.ait.core.entities.BOTIPaintingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class GallifreyanPaintingEntityRenderer
        extends EntityRenderer<BOTIPaintingEntity> {
    public static final ResourceLocation GALLIFREY_PAINTING_TEXTURE = AITMod.id("textures/painting/gallifrey_falls/gallifrey_falls.png");
    public static final ResourceLocation GALLIFREY_FRAME_TEXTURE = AITMod.id("textures/painting/gallifrey_falls/gallifrey_falls_frame.png");
    public GallifreyanPaintingEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(BOTIPaintingEntity paintingEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        if (!AITModClient.skipPaintingBOTI() && !PortalsAPI.RENDERING_PORTAL.getAsBoolean()) BOTI.GALLIFREYAN_RENDER_QUEUE.add(paintingEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(BOTIPaintingEntity entity) {
        return null;
    }

}
