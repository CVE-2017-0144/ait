package dev.amble.ait.module.planet.client.renderers.wearables;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import dev.amble.ait.module.planet.client.models.wearables.SpacesuitModel;
import dev.amble.ait.module.planet.core.item.SpacesuitItem;

@Environment(value = EnvType.CLIENT)
public class SpacesuitFeatureRenderer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel>
        extends
            RenderLayer<T, M> {

    public static final ResourceLocation BLANK_SPACESUIT = AITMod.id(
            "textures/entity/wearables/spacesuit/nasa/blank_spacesuit.png");
    private final SpacesuitModel model;

    public SpacesuitFeatureRenderer(RenderLayerParent<T, M> context, EntityModelSet loader) {
        super(context);
        this.model = new SpacesuitModel(SpacesuitModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T livingEntity,
                       float f, float g, float h, float j, float k, float l) {

        if (!(livingEntity instanceof AbstractClientPlayer || livingEntity instanceof ArmorStand))
            return;

        matrixStack.pushPose();
        matrixStack.translate(0, -1.5f, 0);

        // god bless america
        for (BodyParts part : BodyParts.values()) {
            ItemStack stack = getModelForSlot(livingEntity, part);
            if (stack.getItem() instanceof SpacesuitItem) {
                enablePart(model, part);
            } else {
                disablePart(model, part);
            }
        }

        this.model.Head.copyFrom(((HumanoidModel) getParentModel()).head);
        this.model.Body.copyFrom(((HumanoidModel) getParentModel()).body);
        this.model.LeftArm.copyFrom(((HumanoidModel) getParentModel()).leftArm);
        this.model.RightArm.copyFrom(((HumanoidModel) getParentModel()).rightArm);
        this.model.LeftLeg.copyFrom(((HumanoidModel) getParentModel()).leftLeg);
        this.model.RightLeg.copyFrom(((HumanoidModel) getParentModel()).rightLeg);
        this.model.setupAnim(livingEntity, f, g, j, k, l);

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityCutoutNoCullZOffset(BLANK_SPACESUIT));
        this.model.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1f);

        matrixStack.popPose();
    }

    public static void enablePart(SpacesuitModel model, BodyParts part) {
        switch (part) {
            case HEAD:
                model.Head.visible = true;
                break;
            case CHEST:
                model.Body.visible = true;
                model.LeftArm.visible = true;
                model.RightArm.visible = true;
                break;
            case LEGS:
                model.LeftLeg.getChild("left_leg_pant").visible = true;
                model.RightLeg.getChild("right_leg_pant").visible = true;

                break;
            case FEET:
                model.LeftFoot.visible = true;
                model.RightFoot.visible = true;
                break;
        }
    }

    public static void disablePart(SpacesuitModel model, BodyParts part) {
        switch (part) {
            case HEAD:
                model.Head.visible = false;
                break;
            case CHEST:
                model.Body.visible = false;
                model.LeftArm.visible = false;
                model.RightArm.visible = false;
                break;
            case LEGS:
                model.LeftLeg.getChild("left_leg_pant").visible = false;
                model.RightLeg.getChild("right_leg_pant").visible = false;
                break;
            case FEET:
                model.LeftFoot.visible = false;
                model.RightFoot.visible = false;
                break;
        }
    }

    public static ItemStack getModelForSlot(LivingEntity entity, BodyParts parts) {
        return switch(parts) {
            default -> entity.getItemBySlot(EquipmentSlot.HEAD);
            case CHEST -> entity.getItemBySlot(EquipmentSlot.CHEST);
            case LEGS -> entity.getItemBySlot(EquipmentSlot.LEGS);
            case FEET -> entity.getItemBySlot(EquipmentSlot.FEET);
        };
    }


    public enum BodyParts {
        HEAD,
        CHEST,
        LEGS,
        FEET
    }
}
