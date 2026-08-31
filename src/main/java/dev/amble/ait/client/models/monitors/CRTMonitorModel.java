package dev.amble.ait.client.models.monitors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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
import net.minecraft.world.entity.Entity;

public class CRTMonitorModel extends HierarchicalModel {
    private final ModelPart crt;

    public CRTMonitorModel(ModelPart root) {
        this.crt = root.getChild("crt");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition crt = modelPartData.addOrReplaceChild("crt", CubeListBuilder.create(),
                PartPose.offset(-4.0F, 24.0F, 4.0F));

        PartDefinition cube_r1 = crt.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(0, 21).addBox(-6.0F, -14.0F, -6.0F, 3.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
                        .texOffs(19, 24).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(31, 11)
                        .addBox(-2.0F, -4.0F, -5.0F, 8.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(31, 24)
                        .addBox(-3.0F, -4.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-3.0F, -13.0F, -5.5F, 9.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(30, 0)
                        .addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.0F, 0.0F, -4.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition gallifreyan = crt.addOrReplaceChild("gallifreyan", CubeListBuilder.create().texOffs(31, 38).addBox(-3.0F,
                -3.0F, -0.1F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(4.0F, -8.0F, -10.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        matrices.pushPose();
        matrices.mulPose(Axis.YN.rotationDegrees(180));

        crt.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return crt;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
    }
}
