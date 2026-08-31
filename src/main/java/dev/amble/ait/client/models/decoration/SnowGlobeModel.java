package dev.amble.ait.client.models.decoration;

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

public class SnowGlobeModel extends HierarchicalModel {
    private final ModelPart group;
    public SnowGlobeModel(ModelPart root) {
        this.group = root.getChild("group");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition group = modelPartData.addOrReplaceChild("group", CubeListBuilder.create().texOffs(0, 42).addBox(-5.5F, -11.0F, -6.5F, 11.0F, 11.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-6.0F, 0.0F, -7.0F, 12.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(12, 7).addBox(-2.0F, -1.9F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(20, 16).addBox(1.0F, -3.0F, -6.5F, 0.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(45, 45).addBox(-5.0F, -8.0F, -5.5F, 4.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -9.0F, -3.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 1.0F));

        PartDefinition cube_r1 = group.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 69).addBox(-4.0F, -2.0F, -4.5F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.1858F, -10.0142F, -1.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r2 = group.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 1.0F, -4.5F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-5.1355F, -9.3071F, -1.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r3 = group.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(12, 7).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -0.5F, 3.5F, 0.0F, -0.7854F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        group.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return group;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}