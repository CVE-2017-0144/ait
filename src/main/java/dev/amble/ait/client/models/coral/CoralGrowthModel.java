package dev.amble.ait.client.models.coral;

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

// not to be confused with the exterior, this is only for the coral while its growing
@SuppressWarnings("rawtypes")
public class CoralGrowthModel extends HierarchicalModel {
    public final ModelPart coral;
    public final ModelPart one;
    public final ModelPart two;
    public final ModelPart three;
    public final ModelPart four;
    public final ModelPart five;
    public final ModelPart six;
    public final ModelPart seven;

    public CoralGrowthModel(ModelPart root) {
        this.coral = root.getChild("coral");
        this.one = this.coral.getChild("one");
        this.two = this.coral.getChild("two");
        this.three = this.coral.getChild("three");
        this.four = this.coral.getChild("four");
        this.five = this.coral.getChild("five");
        this.six = this.coral.getChild("six");
        this.seven = this.coral.getChild("seven");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition coral = modelPartData.addOrReplaceChild("coral", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition one = coral.addOrReplaceChild("one", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = one.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(130, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.0F, 2.5F, 0.0F, 0.0F, 1.5708F, 0.3491F));

        PartDefinition cube_r2 = one.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(195, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 2.5F, -2.0F, 2.7925F, 0.0F, -3.1416F));

        PartDefinition cube_r3 = one.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(260, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.0F, 2.5F, 0.0F, 0.0F, -1.5708F, -0.3491F));

        PartDefinition cube_r4 = one.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(325, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 2.5F, 2.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r5 = one.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(245, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r6 = one.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(294, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition two = coral.addOrReplaceChild("two", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r7 = two.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(390, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r8 = two.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(455, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r9 = two.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(520, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r10 = two.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r11 = two.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(147, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r12 = two.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(196, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition three = coral.addOrReplaceChild("three", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r13 = three.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(390, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r14 = three.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(455, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r15 = three.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(520, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r16 = three.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 290).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r17 = three.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(49, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r18 = three.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(98, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition four = coral.addOrReplaceChild("four", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r19 = four.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(455, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(4.0F, 2.5F, 0.0F, 0.0F, 1.5708F, 0.3491F));

        PartDefinition cube_r20 = four.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(520, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 2.5F, -4.0F, 2.7925F, 0.0F, -3.1416F));

        PartDefinition cube_r21 = four.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.0F, 2.5F, 0.0F, 0.0F, -1.5708F, -0.3491F));

        PartDefinition cube_r22 = four.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(65, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 2.5F, 4.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r23 = four.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(50, 386).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r24 = four.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(75, 386).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0873F));

        PartDefinition cube_r25 = four.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(100, 386).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 3.0543F, 0.0F, -3.1416F));

        PartDefinition cube_r26 = four.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(125, 386).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.0873F));

        PartDefinition cube_r27 = four.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(0, 222).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -19.0F, 2.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r28 = four.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(520, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.0F, -19.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r29 = four.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(455, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.0F, -19.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r30 = four.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(390, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -19.0F, -2.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r31 = four.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(260, 222).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.0F, -8.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r32 = four.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(195, 222).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -8.0F, 2.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r33 = four.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(130, 222).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -8.0F, -2.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r34 = four.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(65, 222).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.0F, -8.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r35 = four.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(130, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r36 = four.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(195, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r37 = four.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(260, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r38 = four.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(325, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r39 = four.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(390, 0).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r40 = four.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(0, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition five = coral.addOrReplaceChild("five", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r41 = five.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(343, 43).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.0F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition cube_r42 = five.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(0, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, -8.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r43 = five.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(65, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 3.0F, 0.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r44 = five.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(130, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 8.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r45 = five.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(65, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -1.0F, -6.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r46 = five.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(0, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-6.0F, -1.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r47 = five.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(520, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -1.0F, 6.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r48 = five.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(455, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(6.0F, -1.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r49 = five.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(390, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F))
                .texOffs(130, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -26.0F, -2.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r50 = five.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(325, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F))
                .texOffs(325, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.0F, -26.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r51 = five.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(260, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F))
                .texOffs(260, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -26.0F, 2.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r52 = five.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(195, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F))
                .texOffs(195, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.0F, -26.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r53 = five.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(260, 256).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.4363F, -0.7854F, 0.0F));

        PartDefinition cube_r54 = five.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(195, 256).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r55 = five.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(455, 256).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 2.7053F, 0.7854F, 3.1416F));

        PartDefinition cube_r56 = five.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(390, 256).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.4363F));

        PartDefinition cube_r57 = five.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(325, 256).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.4363F, 0.7854F, 0.0F));

        PartDefinition cube_r58 = five.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(520, 256).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 2.7053F, 0.0F, 3.1416F));

        PartDefinition cube_r59 = five.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(0, 273).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 2.7053F, -0.7854F, 3.1416F));

        PartDefinition cube_r60 = five.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(65, 273).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, -1.5708F, -0.4363F));

        PartDefinition cube_r61 = five.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(226, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.0873F));

        PartDefinition cube_r62 = five.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(130, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -14.0F, -2.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r63 = five.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(65, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.0F, -14.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r64 = five.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(0, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -14.0F, 2.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r65 = five.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(520, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.0F, -14.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r66 = five.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(251, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0873F));

        PartDefinition cube_r67 = five.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(0, 386).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 3.0543F, 0.0F, -3.1416F));

        PartDefinition cube_r68 = five.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(25, 386).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r69 = five.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(292, 0).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r70 = five.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(341, 0).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition six = coral.addOrReplaceChild("six", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r71 = six.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(195, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 11.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r72 = six.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(260, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(11.0F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition cube_r73 = six.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(325, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, -11.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r74 = six.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(390, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-11.0F, 3.0F, 0.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r75 = six.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(455, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -2.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r76 = six.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(390, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -2.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r77 = six.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(325, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -2.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r78 = six.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(260, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -2.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r79 = six.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -28.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r80 = six.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -28.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r81 = six.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -28.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r82 = six.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -28.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r83 = six.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(0, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -15.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r84 = six.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(65, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -15.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r85 = six.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(130, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -15.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r86 = six.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(195, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -15.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r87 = six.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(390, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -37.0F, 0.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition cube_r88 = six.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(325, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.0F, -9.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r89 = six.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(520, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.0F, 9.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r90 = six.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(455, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -37.0F, 0.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r91 = six.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(325, 222).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, 0.0F, -1.5708F, 2.7053F));

        PartDefinition cube_r92 = six.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(390, 222).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, 2.7053F, -0.7854F, 0.0F));

        PartDefinition cube_r93 = six.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(455, 222).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, 2.7053F, 0.0F, 0.0F));

        PartDefinition cube_r94 = six.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(520, 222).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, -0.4363F, 0.7854F, -3.1416F));

        PartDefinition cube_r95 = six.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(0, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, 0.0F, 1.5708F, -2.7053F));

        PartDefinition cube_r96 = six.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(65, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, 2.7053F, 0.7854F, 0.0F));

        PartDefinition cube_r97 = six.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(130, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, -0.4363F, 0.0F, -3.1416F));

        PartDefinition cube_r98 = six.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(195, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, -0.4363F, -0.7854F, -3.1416F));

        PartDefinition cube_r99 = six.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(260, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.4363F, -0.7854F, 0.0F));

        PartDefinition cube_r100 = six.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(325, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r101 = six.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(390, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.7053F, 0.7854F, 3.1416F));

        PartDefinition cube_r102 = six.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(455, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 1.5708F, 0.4363F));

        PartDefinition cube_r103 = six.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(520, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.4363F, 0.7854F, 0.0F));

        PartDefinition cube_r104 = six.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(0, 256).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.7053F, 0.0F, 3.1416F));

        PartDefinition cube_r105 = six.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(65, 256).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.7053F, -0.7854F, 3.1416F));

        PartDefinition cube_r106 = six.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(130, 256).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -1.5708F, -0.4363F));

        PartDefinition cube_r107 = six.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(126, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.0873F));

        PartDefinition cube_r108 = six.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(151, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0873F));

        PartDefinition cube_r109 = six.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(176, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 3.0543F, 0.0F, -3.1416F));

        PartDefinition cube_r110 = six.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(201, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r111 = six.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(194, 0).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r112 = six.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(243, 0).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition octagon2 = six.addOrReplaceChild("octagon2", CubeListBuilder.create().texOffs(84, 343).addBox(-4.9706F, -21.0F, -12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(42, 343).addBox(-4.9706F, -21.0F, 12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(191, 290).addBox(12.0F, -21.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(149, 290).addBox(-12.0F, -21.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition octagon_r1 = octagon2.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(170, 290).addBox(-12.0F, -21.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(212, 290).addBox(12.0F, -21.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(63, 343).addBox(-4.9706F, -21.0F, 12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(105, 343).addBox(-4.9706F, -21.0F, -12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition seven = coral.addOrReplaceChild("seven", CubeListBuilder.create().texOffs(150, 386).addBox(-5.0F, -35.0F, -6.0F, 10.0F, 34.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(229, 393).addBox(-5.0F, -35.0F, -12.0F, 10.0F, 0.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(229, 386).addBox(-5.0F, -1.0F, -12.0F, 10.0F, 0.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(205, 386).addBox(-5.0F, -35.0F, -12.0F, 0.0F, 34.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(192, 386).addBox(5.0F, -35.0F, -12.0F, 0.0F, 34.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(97, 0).addBox(-12.0F, -39.0F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.001F))
                .texOffs(0, 0).addBox(-12.0F, 0.0F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r113 = seven.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(455, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -19.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r114 = seven.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(390, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -19.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r115 = seven.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(260, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -7.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r116 = seven.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(195, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -7.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r117 = seven.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(130, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -7.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r118 = seven.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(325, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -19.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r119 = seven.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(520, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -31.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r120 = seven.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(0, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -31.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r121 = seven.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(65, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -31.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r122 = seven.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(130, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -31.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r123 = seven.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(65, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 12.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r124 = seven.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(0, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(12.0F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition cube_r125 = seven.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(520, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-12.0F, 3.0F, 0.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r126 = seven.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(455, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, -12.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r127 = seven.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(195, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.0F, 9.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r128 = seven.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(260, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -37.0F, 0.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r129 = seven.addOrReplaceChild("cube_r129", CubeListBuilder.create().texOffs(325, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.0F, -9.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r130 = seven.addOrReplaceChild("cube_r130", CubeListBuilder.create().texOffs(390, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -37.0F, 0.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition bone9 = seven.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(65, 290).addBox(27.2752F, -21.0F, -0.8776F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(21, 343).addBox(10.3046F, -21.0F, 16.093F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(107, 290).addBox(3.2752F, -21.0F, -0.8776F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(42, 427).addBox(10.3046F, -21.0F, -7.907F, 9.9411F, 8.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(63, 427).addBox(10.3046F, 20.0F, -7.907F, 9.9411F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(-15.2752F, -21.0F, -4.093F));

        PartDefinition octagon_r2 = bone9.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(171, 386).addBox(-4.9706F, -13.0F, -12.0F, 9.9411F, 33.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.309F, 0.0F));

        PartDefinition octagon_r3 = bone9.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(233, 290).addBox(-4.9706F, -21.0F, -12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(86, 290).addBox(-12.0F, -21.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(128, 290).addBox(12.0F, -21.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(0, 343).addBox(-4.9706F, -21.0F, 12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.2752F, 0.0F, 4.093F, 0.0F, -0.7854F, 0.0F));

        PartDefinition octagon_r4 = bone9.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(0, 427).addBox(-4.9706F, -21.0F, -12.0F, 9.9411F, 8.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.2752F, 37.6777F, -8.6141F, -0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r5 = bone9.addOrReplaceChild("octagon_r5", CubeListBuilder.create().texOffs(21, 427).addBox(-4.9706F, -21.0F, -12.0F, 9.9411F, 8.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.2752F, -6.636F, 15.4275F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r6 = bone9.addOrReplaceChild("octagon_r6", CubeListBuilder.create().texOffs(218, 386).addBox(-12.0F, -13.0F, -4.9706F, 0.0F, 33.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(30.5514F, 0.0F, 0.0003F, 0.0F, -0.2618F, 0.0F));
        return LayerDefinition.create(modelData, 1024, 1024);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
    }

    @Override
    public ModelPart root() {
        return coral;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
    }
}
