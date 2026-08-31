package dev.amble.ait.client.models.decoration;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports


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

public class GallifreyFallsModel extends HierarchicalModel {
    private final ModelPart painting;
    public GallifreyFallsModel(ModelPart root) {
        this.painting = root.getChild("painting");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition painting = modelPartData.addOrReplaceChild("painting", CubeListBuilder.create().texOffs(689, 65).addBox(-39.0F, -64.0F, 49.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(322, 682).addBox(-39.0F, -64.0F, 37.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(644, 667).addBox(-39.0F, -64.0F, 22.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(482, 244).addBox(-39.0F, -64.0F, 8.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(482, 163).addBox(-39.0F, -64.0F, 5.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(643, 342).addBox(-39.0F, -64.0F, -5.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(483, 617).addBox(-39.0F, -64.0F, -18.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(0, 616).addBox(-39.0F, -64.0F, -12.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(482, 406).addBox(-39.0F, -64.0F, 1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(322, 552).addBox(-39.0F, -64.0F, -31.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(483, 487).addBox(-39.0F, -64.0F, -49.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(241, 406).addBox(-39.0F, -64.0F, -31.0F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.0F))
        .texOffs(241, 325).addBox(-119.0F, -64.0F, -31.0F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.0F))
        .texOffs(0, 405).addBox(41.0F, -64.0F, -31.0F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.0F))
        .texOffs(0, 243).addBox(-119.0F, -64.0F, -111.0F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.0F))
        .texOffs(241, 244).addBox(-39.0F, -64.0F, -111.0F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.0F))
        .texOffs(0, 324).addBox(41.0F, -64.0F, -111.0F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.0F))
        .texOffs(0, 0).addBox(-119.0F, 0.0F, -31.0F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.0F))
        .texOffs(0, 81).addBox(-39.0F, 0.0F, -31.0F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.0F))
        .texOffs(0, 162).addBox(41.0F, 0.0F, -31.0F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.0F))
        .texOffs(241, 1).addBox(41.0F, 0.0F, -111.0F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.0F))
        .texOffs(241, 82).addBox(-39.0F, 0.0F, -111.0F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.0F))
        .texOffs(241, 163).addBox(-119.0F, 0.0F, -111.0F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 40.0F, 103.0F));

        PartDefinition cube_r1 = painting.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(643, 212).addBox(-70.0F, -64.0F, -3.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(98.5675F, 0.0F, -100.5685F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = painting.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(643, 407).addBox(-8.0F, -64.0F, -3.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-96.5685F, 0.0F, -102.5685F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r3 = painting.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(322, 487).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-68.6985F, 0.0F, -77.2843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r4 = painting.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(161, 487).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(69.2843F, 0.0F, -75.8701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r5 = painting.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(161, 552).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-68.6985F, 0.0F, -59.2843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r6 = painting.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 551).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(69.2843F, 0.0F, -57.8701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r7 = painting.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 486).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-68.6985F, 0.0F, -27.2843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r8 = painting.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(482, 325).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(69.2843F, 0.0F, -25.8701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r9 = painting.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(161, 617).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-68.6985F, 0.0F, -40.2843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r10 = painting.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(483, 552).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(69.2843F, 0.0F, -38.8701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r11 = painting.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(643, 147).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-68.6985F, 0.0F, -46.2843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r12 = painting.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(322, 617).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(69.2843F, 0.0F, -44.8701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r13 = painting.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(644, 472).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-68.6985F, 0.0F, -33.2843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r14 = painting.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(643, 277).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(69.2843F, 0.0F, -31.8701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r15 = painting.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(482, 82).addBox(-39.0F, -64.0F, -1.0F, 103.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(98.5685F, 0.0F, -46.5685F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r16 = painting.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(482, 0).addBox(-61.0F, -64.0F, -1.0F, 103.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-96.5682F, 0.0F, -48.5678F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r17 = painting.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(644, 602).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-68.6985F, 0.0F, -6.2843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r18 = painting.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(644, 537).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(69.2843F, 0.0F, -4.8701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r19 = painting.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(161, 682).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-68.6985F, 0.0F, 8.7157F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r20 = painting.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 681).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(69.2843F, 0.0F, 10.1299F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r21 = painting.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(689, 0).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(69.2843F, 0.0F, 22.1299F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r22 = painting.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(483, 682).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-68.6985F, 0.0F, 20.7157F, 0.0F, -0.7854F, 0.0F));

        PartDefinition clipframe = painting.addOrReplaceChild("clipframe", CubeListBuilder.create().texOffs(644, 732).addBox(-24.0F, -48.0F, -111.0F, 48.0F, 32.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 1024, 1024);
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        painting.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return painting;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}