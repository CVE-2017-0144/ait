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

public class TrenzalorePaintingModel extends HierarchicalModel {
    private final ModelPart painting;
    public TrenzalorePaintingModel(ModelPart root) {
        this.painting = root.getChild("painting");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition painting = modelPartData.addOrReplaceChild("painting", CubeListBuilder.create().texOffs(483, 551).addBox(-15.25F, -22.25F, 41.5F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 0).addBox(-95.25F, -22.25F, -38.5F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.001F))
                .texOffs(0, 162).addBox(64.75F, -22.25F, -38.5F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.001F))
                .texOffs(0, 81).addBox(-15.25F, -22.25F, -38.5F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.001F))
                .texOffs(321, 81).addBox(64.75F, -22.25F, -118.5F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.001F))
                .texOffs(321, 0).addBox(-15.25F, -22.25F, -118.5F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.001F))
                .texOffs(0, 243).addBox(-95.25F, -22.25F, -118.5F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.001F))
                .texOffs(321, 405).addBox(-95.25F, 41.75F, -118.5F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.001F))
                .texOffs(0, 405).addBox(-15.25F, 41.75F, -118.5F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.001F))
                .texOffs(321, 324).addBox(64.75F, 41.75F, -118.5F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.001F))
                .texOffs(0, 324).addBox(64.75F, 41.75F, -38.5F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.001F))
                .texOffs(321, 243).addBox(-15.25F, 41.75F, -38.5F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.001F))
                .texOffs(321, 162).addBox(-95.25F, 41.75F, -38.5F, 80.0F, 0.0F, 80.0F, new CubeDeformation(0.001F))
                .texOffs(483, 616).addBox(-15.25F, -22.25F, 29.5F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(642, 130).addBox(-15.25F, -21.05F, 14.5F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(642, 260).addBox(-15.25F, -22.25F, -12.5F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(644, 551).addBox(-43.25F, -22.25F, -13.5F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(161, 681).addBox(-15.25F, -26.25F, -19.5F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(322, 746).addBox(-15.25F, -22.25F, -38.5F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(803, 0).addBox(-15.25F, -22.25F, -56.5F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(644, 681).addBox(-15.25F, -30.25F, -6.5F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(-23.75F, -17.75F, 7.5F));

        PartDefinition cube_r1 = painting.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(483, 681).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(93.0343F, 33.75F, -33.3701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r2 = painting.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(736, 455).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-44.9485F, 33.75F, -34.7843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r3 = painting.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(483, 746).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(93.0343F, 41.75F, -83.3701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r4 = painting.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(644, 746).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-44.9485F, 41.75F, -84.7843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r5 = painting.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 746).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(93.0343F, 41.75F, -65.3701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r6 = painting.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(161, 746).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-44.9485F, 41.75F, -66.7843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r7 = painting.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 681).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(93.0343F, 37.75F, -46.3701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r8 = painting.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(322, 681).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-44.9485F, 37.75F, -47.7843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r9 = painting.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(642, 390).addBox(-39.0F, -64.0F, -16.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(93.0343F, 41.75F, -52.3701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r10 = painting.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(644, 616).addBox(-39.0F, -64.0F, 3.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-44.9485F, 41.75F, -53.7843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r11 = painting.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(803, 130).addBox(-70.0F, -64.0F, -3.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(122.3175F, 41.75F, -108.0685F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r12 = painting.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(642, 195).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(93.0343F, 41.75F, -39.3701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r13 = painting.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(803, 65).addBox(-8.0F, -64.0F, -3.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-72.8185F, 41.75F, -110.0685F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r14 = painting.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(642, 325).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-44.9485F, 41.75F, -40.7843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r15 = painting.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(642, 0).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(93.0343F, 42.95F, -12.3701F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r16 = painting.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(642, 65).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-44.9485F, 42.95F, -13.7843F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r17 = painting.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(161, 616).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(93.0343F, 41.75F, 2.6299F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r18 = painting.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(322, 616).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-44.9485F, 41.75F, 1.2157F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r19 = painting.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 486).addBox(-61.0F, -64.0F, -1.0F, 103.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-72.8183F, 41.75F, -56.0678F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r20 = painting.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(575, 486).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-44.9485F, 41.75F, 13.2157F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r21 = painting.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(207, 486).addBox(-39.0F, -64.0F, -1.0F, 103.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(122.3185F, 41.75F, -54.0685F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r22 = painting.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 616).addBox(-39.0F, -64.0F, -1.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(93.0343F, 41.75F, 14.6299F, 0.0F, 0.7854F, 0.0F));

        PartDefinition tardis = painting.addOrReplaceChild("tardis", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r23 = tardis.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(322, 551).addBox(-45.5F, -32.0F, -4.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(161, 551).addBox(-45.5F, -32.0F, -7.0F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r24 = tardis.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 551).addBox(-40.0F, -32.0F, 1.5F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(414, 486).addBox(-40.0F, -32.0F, -1.5F, 80.0F, 64.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition roof = tardis.addOrReplaceChild("roof", CubeListBuilder.create(), PartPose.offset(-32.25F, 50.75F, 42.5F));

        PartDefinition cube_r25 = roof.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 1010).addBox(-13.0F, -61.75F, -53.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.001F))
                .texOffs(9, 925).addBox(-8.5F, -64.75F, -48.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition plane = painting.addOrReplaceChild("plane", CubeListBuilder.create().texOffs(803, 195).addBox(-24.0F, -48.0F, -111.0F, 48.0F, 32.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(23.75F, 41.75F, -7.5F));
        return LayerDefinition.create(modelData, 1024, 1024);
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        matrices.pushPose();
        matrices.translate(0.5, 1, 5.5);
        painting.getChild("plane").visible = false;
        painting.render(matrices, vertexConsumer, light, overlay, color);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return painting;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}