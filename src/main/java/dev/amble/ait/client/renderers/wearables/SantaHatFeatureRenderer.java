package dev.amble.ait.client.renderers.wearables;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.wearables.SantaHatModel;

@Environment(value = EnvType.CLIENT)
public class SantaHatFeatureRenderer<T extends LivingEntity, M extends PlayerModel<T>>
        extends
            RenderLayer<T, M> {

    private static final ResourceLocation SANTA_HAT = new ResourceLocation(AITMod.MOD_ID,
            "textures/entity/wearables/santa_hat.png");
    private final SantaHatModel model;

    public SantaHatFeatureRenderer(RenderLayerParent<T, M> context, EntityModelSet loader) {
        super(context);
        this.model = new SantaHatModel(SantaHatModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T livingEntity,
            float f, float g, float h, float j, float k, float l) {
        ItemStack stack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);

        /*if (!(stack.isOf(AITItems.SANTA_HAT)))
            return;*/

        if (!(livingEntity instanceof AbstractClientPlayer))
            return;

        matrixStack.pushPose();



        this.model.hat.copyFrom(this.getParentModel().head);
        this.model.setupAnim(livingEntity, f, g, j, k, l);

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entitySmoothCutout(SANTA_HAT));
        this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1f);

        matrixStack.popPose();
    }
}
