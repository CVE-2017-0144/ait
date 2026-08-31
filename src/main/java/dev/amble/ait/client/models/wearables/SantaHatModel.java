package dev.amble.ait.client.models.wearables;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class SantaHatModel extends EntityModel {
    public final ModelPart hat;
    public SantaHatModel(ModelPart root) {
        this.hat = root.getChild("hat");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition hat = modelPartData.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(2, 0).addBox(-4.0F, -9.4413F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.4F))
                .texOffs(36, 38).addBox(-1.0F, -13.1913F, 4.2381F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 23.9413F, 0.3741F));

        PartDefinition cube_r1 = hat.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 25).addBox(-1.5F, -0.3667F, 0.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.5F, -13.0747F, 0.8142F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r2 = hat.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(2, 27).addBox(-2.5F, -3.708F, -1.65F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.5F, -10.7334F, -1.2851F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r3 = hat.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(2, 14).addBox(-3.5F, -3.35F, -2.65F, 8.0F, 4.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.5F, -8.5913F, -1.5369F, -0.3927F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        hat.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }
}