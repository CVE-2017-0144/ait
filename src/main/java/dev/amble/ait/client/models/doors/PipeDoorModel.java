package dev.amble.ait.client.models.doors;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.tardis.ClientTardis;

public class PipeDoorModel extends DoorModel {
    private final ModelPart tardis;
    public PipeDoorModel(ModelPart root) {
        this.tardis = root.getChild("tardis");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition tardis = modelPartData.addOrReplaceChild("tardis", CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -14.5F, -7.0F, 15.0F, 7.0F, 15.0F, new CubeDeformation(0.0F))
        .texOffs(0, 22).addBox(-6.5F, -8.0F, -6.0F, 13.0F, 8.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.5F, -0.5F, 0.0F, 0.0F, -3.1416F));
        return LayerDefinition.create(modelData, 128, 128);
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        tardis.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        matrices.pushPose();
        matrices.translate(0, -1f, 0);
        matrices.mulPose(Axis.XN.rotationDegrees(90f));

        this.tardis.y = !tardis.door().isOpen() ? -22f : -8f;
        super.renderWithAnimations(tardis, linkableBlockEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return tardis;
    }

}