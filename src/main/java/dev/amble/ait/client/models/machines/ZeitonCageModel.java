package dev.amble.ait.client.models.machines;

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

public class ZeitonCageModel extends HierarchicalModel {
    private final ModelPart cage;
    private final ModelPart cube;
    public ZeitonCageModel(ModelPart root) {
        this.cage = root.getChild("cage");
        this.cube = this.cage.getChild("cube");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition cage = modelPartData.addOrReplaceChild("cage", CubeListBuilder.create().texOffs(64, 0).addBox(-16.0F, -2.0F, 0.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

        PartDefinition cube_r1 = cage.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(64, 18).addBox(-1.0F, -9.0F, 0.0F, 3.0F, 9.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.0F, -2.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r2 = cage.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 18).mirror().addBox(-2.0F, -9.0F, 0.0F, 3.0F, 9.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-14.0F, -2.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r3 = cage.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 18).mirror().addBox(-2.0F, -9.0F, 0.0F, 3.0F, 9.0F, 0.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-14.0F, -2.0F, 16.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r4 = cage.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(64, 18).addBox(-1.0F, -9.0F, 0.0F, 3.0F, 9.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.0F, -2.0F, 16.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube = cage.addOrReplaceChild("cube", CubeListBuilder.create(), PartPose.offset(-8.0F, -13.0F, 8.0F));

        PartDefinition cube_r5 = cube.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 64).addBox(-8.0F, -1.0F, -8.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
        .texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(-1.0F))
        .texOffs(0, 32).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        cage.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return cage;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}