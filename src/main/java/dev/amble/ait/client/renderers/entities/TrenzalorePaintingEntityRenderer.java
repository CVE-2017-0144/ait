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
public class TrenzalorePaintingEntityRenderer
        extends EntityRenderer<BOTIPaintingEntity> {
    public static final ResourceLocation TRENZALORE_PAINTING_TEXTURE = AITMod.id("textures/painting/trenzalore/trenzalore.png");
    public static final ResourceLocation TRENZALORE_FRAME_TEXTURE = AITMod.id("textures/painting/trenzalore/trenzalore_frame.png");
    public TrenzalorePaintingEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(BOTIPaintingEntity paintingEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        if (!AITModClient.skipPaintingBOTI() && !PortalsAPI.RENDERING_PORTAL.getAsBoolean()) BOTI.TRENZALORE_PAINTING_QUEUE.add(paintingEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(BOTIPaintingEntity entity) {
        return null;
    }

}
