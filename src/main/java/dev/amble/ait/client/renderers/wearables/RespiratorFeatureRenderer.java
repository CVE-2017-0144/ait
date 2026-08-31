package dev.amble.ait.client.renderers.wearables;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
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
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.wearables.RespiratorModel;
import dev.amble.ait.core.AITItems;

@OnlyIn(Dist.CLIENT)
public class RespiratorFeatureRenderer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel>
        extends
            RenderLayer<T, M> {

    private static final ResourceLocation RESPIRATOR = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/entity/wearables/respirator.png");
    private static final ResourceLocation FACELESS_RESPIRATOR = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/entity/wearables/faceless_respirator.png");
    private final RespiratorModel model;

    public RespiratorFeatureRenderer(RenderLayerParent<T, M> context, EntityModelSet loader) {
        super(context);
        this.model = new RespiratorModel(RespiratorModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T livingEntity,
            float f, float g, float h, float j, float k, float l) {
        ItemStack stack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);

        if (!(stack.is(AITItems.RESPIRATOR) || stack.is(AITItems.FACELESS_RESPIRATOR)))
            return;

        if (livingEntity instanceof AbstractClientPlayer || livingEntity instanceof ArmorStand) {

            matrixStack.pushPose();

            this.model.mask.copyFrom(((HumanoidModel)this.getParentModel()).head);
            this.model.setupAnim(livingEntity, f, g, j, k, l);

            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entitySmoothCutout(
                    stack.getItem() == AITItems.RESPIRATOR ? RESPIRATOR : FACELESS_RESPIRATOR));
            this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

            matrixStack.popPose();
        }
    }
}
