package dev.amble.ait.client.models.coral;

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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.client.models.exteriors.SimpleExteriorModel;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;

public class CoralGrowthExteriorModel extends SimpleExteriorModel {
    public final ModelPart coral;
    public final ModelPart one;
    public final ModelPart two;
    public final ModelPart three;
    public final ModelPart four;
    public final ModelPart five;
    public final ModelPart six;
    public final ModelPart sixcorallybits;
    public final ModelPart seven;
    public final ModelPart corallybits;
    public final ModelPart cage;
    public final ModelPart perimeter;
    public final ModelPart door;


    public CoralGrowthExteriorModel(ModelPart root) {
        this.coral = root.getChild("coral");
        this.one = this.coral.getChild("one");
        this.two = this.coral.getChild("two");
        this.three = this.coral.getChild("three");
        this.four = this.coral.getChild("four");
        this.five = this.coral.getChild("five");
        this.six = this.coral.getChild("six");
        this.sixcorallybits = this.six.getChild("sixcorallybits");
        this.seven = this.coral.getChild("seven");
        this.corallybits = this.seven.getChild("corallybits");
        this.cage = this.coral.getChild("cage");
        this.perimeter = this.coral.getChild("perimeter");
        this.door = this.perimeter.getChild("door");
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

        PartDefinition sixcorallybits = six.addOrReplaceChild("sixcorallybits", CubeListBuilder.create(), PartPose.offset(-9.0F, -37.0F, 0.0F));

        PartDefinition cube_r71 = sixcorallybits.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(455, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r72 = sixcorallybits.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(520, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 9.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r73 = sixcorallybits.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r74 = sixcorallybits.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(130, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r75 = sixcorallybits.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(390, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.0F, 40.0F, 0.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r76 = sixcorallybits.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(390, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 35.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r77 = sixcorallybits.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(325, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 40.0F, -11.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r78 = sixcorallybits.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(260, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(20.0F, 40.0F, 0.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition cube_r79 = sixcorallybits.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(195, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 40.0F, 11.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r80 = sixcorallybits.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(325, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 35.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r81 = sixcorallybits.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(195, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 22.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r82 = sixcorallybits.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 9.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r83 = sixcorallybits.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(18.0F, 9.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r84 = sixcorallybits.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(0, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(18.0F, 22.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r85 = sixcorallybits.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(260, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(18.0F, 35.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r86 = sixcorallybits.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(455, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 35.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r87 = sixcorallybits.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(65, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 22.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r88 = sixcorallybits.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 9.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r89 = sixcorallybits.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(325, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, -9.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r90 = sixcorallybits.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(390, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(18.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition bone11 = six.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(84, 343).addBox(-4.9706F, 0.0F, -12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(42, 343).addBox(-4.9706F, 0.0F, 12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(191, 290).addBox(12.0F, 0.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(149, 290).addBox(-12.0F, 0.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -42.0F, 0.0F));

        PartDefinition cube_r91 = bone11.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(325, 222).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 2.7053F));

        PartDefinition cube_r92 = bone11.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(390, 222).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.7053F, -0.7854F, 0.0F));

        PartDefinition cube_r93 = bone11.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(455, 222).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.7053F, 0.0F, 0.0F));

        PartDefinition cube_r94 = bone11.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(520, 222).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.7854F, -3.1416F));

        PartDefinition cube_r95 = bone11.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(0, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, -2.7053F));

        PartDefinition cube_r96 = bone11.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(65, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.7053F, 0.7854F, 0.0F));

        PartDefinition cube_r97 = bone11.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(130, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, -3.1416F));

        PartDefinition cube_r98 = bone11.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(195, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, -0.7854F, -3.1416F));

        PartDefinition cube_r99 = bone11.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(260, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 43.0F, 0.0F, -0.4363F, -0.7854F, 0.0F));

        PartDefinition cube_r100 = bone11.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(325, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 43.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r101 = bone11.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(390, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 43.0F, 0.0F, 2.7053F, 0.7854F, 3.1416F));

        PartDefinition cube_r102 = bone11.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(455, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 43.0F, 0.0F, 0.0F, 1.5708F, 0.4363F));

        PartDefinition cube_r103 = bone11.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(520, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 43.0F, 0.0F, -0.4363F, 0.7854F, 0.0F));

        PartDefinition cube_r104 = bone11.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(0, 256).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 43.0F, 0.0F, 2.7053F, 0.0F, 3.1416F));

        PartDefinition cube_r105 = bone11.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(65, 256).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 43.0F, 0.0F, 2.7053F, -0.7854F, 3.1416F));

        PartDefinition cube_r106 = bone11.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(130, 256).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 43.0F, 0.0F, 0.0F, -1.5708F, -0.4363F));

        PartDefinition cube_r107 = bone11.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(126, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.0F, 42.0F, 0.0F, 0.0F, -1.5708F, -0.0873F));

        PartDefinition cube_r108 = bone11.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(151, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.0F, 42.0F, 0.0F, 0.0F, 1.5708F, 0.0873F));

        PartDefinition cube_r109 = bone11.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(176, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 42.0F, 1.0F, 3.0543F, 0.0F, -3.1416F));

        PartDefinition cube_r110 = bone11.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(201, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 42.0F, -1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition octagon_r1 = bone11.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(170, 290).addBox(-12.0F, -21.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(212, 290).addBox(12.0F, -21.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(63, 343).addBox(-4.9706F, -21.0F, 12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(105, 343).addBox(-4.9706F, -21.0F, -12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(194, 0).addBox(-12.0F, -21.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r111 = bone11.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(243, 0).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 42.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition seven = coral.addOrReplaceChild("seven", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition corallybits = seven.addOrReplaceChild("corallybits", CubeListBuilder.create().texOffs(42, 427).addBox(-4.9706F, -5.0F, -3.0F, 9.9411F, 8.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -37.0F, -9.0F));

        PartDefinition cube_r112 = corallybits.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(325, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r113 = corallybits.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(260, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 9.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r114 = corallybits.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(195, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 18.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r115 = corallybits.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(390, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 9.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition cube_r116 = corallybits.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(65, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 40.0F, 21.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r117 = corallybits.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(130, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 30.0F, 18.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r118 = corallybits.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(455, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 18.0F, 18.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r119 = corallybits.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(520, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 6.0F, 18.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r120 = corallybits.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(0, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 6.0F, 9.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r121 = corallybits.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(390, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 18.0F, 9.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r122 = corallybits.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(195, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 30.0F, 9.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r123 = corallybits.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(520, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-12.0F, 40.0F, 9.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r124 = corallybits.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(455, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 40.0F, -3.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r125 = corallybits.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(0, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(12.0F, 40.0F, 9.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition cube_r126 = corallybits.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(260, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 30.0F, 9.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r127 = corallybits.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(325, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 18.0F, 9.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r128 = corallybits.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(65, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 6.0F, 9.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r129 = corallybits.addOrReplaceChild("cube_r129", CubeListBuilder.create().texOffs(130, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone9 = seven.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(65, 290).addBox(27.2752F, -21.0F, -0.8776F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(21, 343).addBox(10.3046F, -21.0F, 16.093F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(107, 290).addBox(3.2752F, -21.0F, -0.8776F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(63, 427).addBox(10.3046F, 20.0F, -7.907F, 9.9411F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(-15.2752F, -21.0F, -4.093F));

        PartDefinition octagon_r2 = bone9.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(171, 386).addBox(-4.9706F, -13.0F, -12.0F, 9.9411F, 33.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.309F, 0.0F));

        PartDefinition octagon_r3 = bone9.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(233, 290).addBox(-4.9706F, -21.0F, -12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(86, 290).addBox(-12.0F, -21.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(128, 290).addBox(12.0F, -21.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(0, 343).addBox(-4.9706F, -21.0F, 12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.2752F, 0.0F, 4.093F, 0.0F, -0.7854F, 0.0F));

        PartDefinition octagon_r4 = bone9.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(0, 427).addBox(-4.9706F, -21.0F, -12.0F, 9.9411F, 8.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.2752F, 37.6777F, -8.6141F, -0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r5 = bone9.addOrReplaceChild("octagon_r5", CubeListBuilder.create().texOffs(21, 427).addBox(-4.9706F, -21.0F, -12.0F, 9.9411F, 8.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.2752F, -6.636F, 15.4275F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r6 = bone9.addOrReplaceChild("octagon_r6", CubeListBuilder.create().texOffs(218, 386).addBox(-12.0F, -13.0F, -4.9706F, 0.0F, 33.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(30.5514F, 0.0F, 0.0003F, 0.0F, -0.2618F, 0.0F));

        PartDefinition bone12 = seven.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(150, 386).addBox(-5.0F, -35.0F, -6.0F, 10.0F, 34.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(229, 393).addBox(-5.0F, -35.0F, -12.0F, 10.0F, 0.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(229, 386).addBox(-5.0F, -1.0F, -12.0F, 10.0F, 0.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(205, 386).addBox(-5.0F, -35.0F, -12.0F, 0.0F, 34.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(192, 386).addBox(5.0F, -35.0F, -12.0F, 0.0F, 34.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(97, 0).addBox(-12.0F, -39.0F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.001F))
                .texOffs(0, 0).addBox(-12.0F, 0.0F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cage = coral.addOrReplaceChild("cage", CubeListBuilder.create().texOffs(919, 147).addBox(-4.9706F, -42.0F, -12.0F, 9.9411F, 8.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition octagon_r7 = cage.addOrReplaceChild("octagon_r7", CubeListBuilder.create().texOffs(874, 130).addBox(-22.9706F, -42.0F, -12.0F, 9.9411F, 8.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.7279F, 0.0F, -12.7279F, 0.0F, 0.7854F, 0.0F));

        PartDefinition octagon_r8 = cage.addOrReplaceChild("octagon_r8", CubeListBuilder.create().texOffs(947, 114).addBox(-4.9706F, -42.0F, -12.0F, 9.9411F, 8.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition octagon_r9 = cage.addOrReplaceChild("octagon_r9", CubeListBuilder.create().texOffs(947, 81).addBox(-4.9706F, -42.0F, -12.0F, 9.9411F, 8.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition octagon = cage.addOrReplaceChild("octagon", CubeListBuilder.create().texOffs(831, 0).addBox(-5.0741F, -21.0F, -12.25F, 10.1482F, 42.0F, 0.0F, new CubeDeformation(0.002F))
                .texOffs(789, 53).addBox(-5.0741F, -21.0F, 12.25F, 10.1482F, 42.0F, 0.0F, new CubeDeformation(0.002F))
                .texOffs(810, 0).addBox(12.25F, -21.0F, -5.0741F, 0.0F, 42.0F, 10.1482F, new CubeDeformation(0.002F))
                .texOffs(768, 0).addBox(-12.25F, -21.0F, -5.0741F, 0.0F, 42.0F, 10.1482F, new CubeDeformation(0.002F)), PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition octagon_r10 = octagon.addOrReplaceChild("octagon_r10", CubeListBuilder.create().texOffs(789, 0).addBox(-12.25F, -21.0F, -5.0741F, 0.0F, 42.0F, 10.1482F, new CubeDeformation(0.002F))
                .texOffs(768, 53).addBox(12.25F, -21.0F, -5.0741F, 0.0F, 42.0F, 10.1482F, new CubeDeformation(0.002F))
                .texOffs(810, 53).addBox(-5.0741F, -21.0F, 12.25F, 10.1482F, 42.0F, 0.0F, new CubeDeformation(0.002F))
                .texOffs(831, 43).addBox(-5.0741F, -21.0F, -12.25F, 10.1482F, 42.0F, 0.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition octagon3 = cage.addOrReplaceChild("octagon3", CubeListBuilder.create().texOffs(799, 96).addBox(3.1777F, -21.0F, -12.5F, 2.0F, 42.0F, 0.0F, new CubeDeformation(0.002F))
                .texOffs(862, 43).addBox(-5.1777F, -21.0F, -12.5F, 2.0F, 42.0F, 0.0F, new CubeDeformation(0.002F))
                .texOffs(789, 96).addBox(-5.1777F, -21.0F, 12.5F, 2.0F, 42.0F, 0.0F, new CubeDeformation(0.002F))
                .texOffs(860, 88).addBox(3.1777F, -21.0F, 12.5F, 2.0F, 42.0F, 0.0F, new CubeDeformation(0.002F))
                .texOffs(855, 88).addBox(12.5F, -21.0F, 3.1777F, 0.0F, 42.0F, 2.0F, new CubeDeformation(0.002F))
                .texOffs(831, 86).addBox(12.5F, -21.0F, -5.1777F, 0.0F, 42.0F, 2.0F, new CubeDeformation(0.002F))
                .texOffs(841, 86).addBox(-12.5F, -21.0F, -5.1777F, 0.0F, 42.0F, 2.0F, new CubeDeformation(0.002F))
                .texOffs(852, 0).addBox(-12.5F, -21.0F, 3.1777F, 0.0F, 42.0F, 2.0F, new CubeDeformation(0.002F)), PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition octagon_r11 = octagon3.addOrReplaceChild("octagon_r11", CubeListBuilder.create().texOffs(836, 86).addBox(-12.5F, -21.0F, 3.1777F, 0.0F, 42.0F, 2.0F, new CubeDeformation(0.002F))
                .texOffs(846, 86).addBox(-12.5F, -21.0F, -5.1777F, 0.0F, 42.0F, 2.0F, new CubeDeformation(0.002F))
                .texOffs(852, 45).addBox(12.5F, -21.0F, -5.1777F, 0.0F, 42.0F, 2.0F, new CubeDeformation(0.002F))
                .texOffs(857, 0).addBox(12.5F, -21.0F, 3.1777F, 0.0F, 42.0F, 2.0F, new CubeDeformation(0.002F))
                .texOffs(857, 45).addBox(3.1777F, -21.0F, 12.5F, 2.0F, 42.0F, 0.0F, new CubeDeformation(0.002F))
                .texOffs(794, 96).addBox(-5.1777F, -21.0F, 12.5F, 2.0F, 42.0F, 0.0F, new CubeDeformation(0.002F))
                .texOffs(862, 0).addBox(-5.1777F, -21.0F, -12.5F, 2.0F, 42.0F, 0.0F, new CubeDeformation(0.002F))
                .texOffs(804, 96).addBox(3.1777F, -21.0F, -12.5F, 2.0F, 42.0F, 0.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone = cage.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, -38.0F, 0.0F));

        PartDefinition octagon_r12 = bone.addOrReplaceChild("octagon_r12", CubeListBuilder.create().texOffs(815, 6).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 6.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r13 = bone.addOrReplaceChild("octagon_r13", CubeListBuilder.create().texOffs(815, 3).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r14 = bone.addOrReplaceChild("octagon_r14", CubeListBuilder.create().texOffs(821, 0).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 40.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r15 = bone.addOrReplaceChild("octagon_r15", CubeListBuilder.create().texOffs(768, 53).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 34.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r16 = bone.addOrReplaceChild("octagon_r16", CubeListBuilder.create().texOffs(821, 3).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r17 = bone.addOrReplaceChild("octagon_r17", CubeListBuilder.create().texOffs(771, 53).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, -5.25F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone2 = cage.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -38.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition octagon_r18 = bone2.addOrReplaceChild("octagon_r18", CubeListBuilder.create().texOffs(809, 3).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 6.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r19 = bone2.addOrReplaceChild("octagon_r19", CubeListBuilder.create().texOffs(809, 6).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r20 = bone2.addOrReplaceChild("octagon_r20", CubeListBuilder.create().texOffs(812, 0).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 40.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r21 = bone2.addOrReplaceChild("octagon_r21", CubeListBuilder.create().texOffs(812, 3).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 34.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r22 = bone2.addOrReplaceChild("octagon_r22", CubeListBuilder.create().texOffs(812, 6).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r23 = bone2.addOrReplaceChild("octagon_r23", CubeListBuilder.create().texOffs(815, 0).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, -5.25F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone3 = cage.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -38.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition octagon_r24 = bone3.addOrReplaceChild("octagon_r24", CubeListBuilder.create().texOffs(803, 3).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 6.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r25 = bone3.addOrReplaceChild("octagon_r25", CubeListBuilder.create().texOffs(803, 6).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r26 = bone3.addOrReplaceChild("octagon_r26", CubeListBuilder.create().texOffs(806, 0).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 40.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r27 = bone3.addOrReplaceChild("octagon_r27", CubeListBuilder.create().texOffs(806, 3).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 34.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r28 = bone3.addOrReplaceChild("octagon_r28", CubeListBuilder.create().texOffs(806, 6).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r29 = bone3.addOrReplaceChild("octagon_r29", CubeListBuilder.create().texOffs(809, 0).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, -5.25F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone4 = cage.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -38.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition octagon_r30 = bone4.addOrReplaceChild("octagon_r30", CubeListBuilder.create().texOffs(794, 3).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 6.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r31 = bone4.addOrReplaceChild("octagon_r31", CubeListBuilder.create().texOffs(794, 6).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r32 = bone4.addOrReplaceChild("octagon_r32", CubeListBuilder.create().texOffs(800, 0).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 40.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r33 = bone4.addOrReplaceChild("octagon_r33", CubeListBuilder.create().texOffs(800, 3).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 34.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r34 = bone4.addOrReplaceChild("octagon_r34", CubeListBuilder.create().texOffs(800, 6).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r35 = bone4.addOrReplaceChild("octagon_r35", CubeListBuilder.create().texOffs(803, 0).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, -5.25F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone5 = cage.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -38.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition octagon_r36 = bone5.addOrReplaceChild("octagon_r36", CubeListBuilder.create().texOffs(788, 3).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 6.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r37 = bone5.addOrReplaceChild("octagon_r37", CubeListBuilder.create().texOffs(788, 6).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r38 = bone5.addOrReplaceChild("octagon_r38", CubeListBuilder.create().texOffs(791, 0).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 40.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r39 = bone5.addOrReplaceChild("octagon_r39", CubeListBuilder.create().texOffs(791, 3).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 34.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r40 = bone5.addOrReplaceChild("octagon_r40", CubeListBuilder.create().texOffs(791, 6).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r41 = bone5.addOrReplaceChild("octagon_r41", CubeListBuilder.create().texOffs(794, 0).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, -5.25F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone6 = cage.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -38.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition octagon_r42 = bone6.addOrReplaceChild("octagon_r42", CubeListBuilder.create().texOffs(782, 3).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 6.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r43 = bone6.addOrReplaceChild("octagon_r43", CubeListBuilder.create().texOffs(782, 6).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r44 = bone6.addOrReplaceChild("octagon_r44", CubeListBuilder.create().texOffs(785, 0).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 40.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r45 = bone6.addOrReplaceChild("octagon_r45", CubeListBuilder.create().texOffs(785, 3).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 34.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r46 = bone6.addOrReplaceChild("octagon_r46", CubeListBuilder.create().texOffs(785, 6).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r47 = bone6.addOrReplaceChild("octagon_r47", CubeListBuilder.create().texOffs(788, 0).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, -5.25F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone7 = cage.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -38.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition octagon_r48 = bone7.addOrReplaceChild("octagon_r48", CubeListBuilder.create().texOffs(774, 2).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 6.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r49 = bone7.addOrReplaceChild("octagon_r49", CubeListBuilder.create().texOffs(774, 6).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r50 = bone7.addOrReplaceChild("octagon_r50", CubeListBuilder.create().texOffs(779, 0).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 40.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r51 = bone7.addOrReplaceChild("octagon_r51", CubeListBuilder.create().texOffs(779, 3).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 34.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r52 = bone7.addOrReplaceChild("octagon_r52", CubeListBuilder.create().texOffs(779, 6).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r53 = bone7.addOrReplaceChild("octagon_r53", CubeListBuilder.create().texOffs(782, 0).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, -5.25F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone8 = cage.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -38.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition octagon_r54 = bone8.addOrReplaceChild("octagon_r54", CubeListBuilder.create().texOffs(768, 0).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 6.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r55 = bone8.addOrReplaceChild("octagon_r55", CubeListBuilder.create().texOffs(770, 2).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r56 = bone8.addOrReplaceChild("octagon_r56", CubeListBuilder.create().texOffs(768, 4).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 40.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r57 = bone8.addOrReplaceChild("octagon_r57", CubeListBuilder.create().texOffs(772, 0).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 34.75F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r58 = bone8.addOrReplaceChild("octagon_r58", CubeListBuilder.create().texOffs(772, 4).addBox(-12.5F, -1.0F, 4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, 0.75F, 1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r59 = bone8.addOrReplaceChild("octagon_r59", CubeListBuilder.create().texOffs(770, 6).addBox(-12.5F, -1.0F, -4.1777F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(-0.25F, -5.25F, -1.25F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon4 = cage.addOrReplaceChild("octagon4", CubeListBuilder.create().texOffs(867, 67).addBox(-5.0741F, -21.0F, -12.25F, 10.1482F, 0.0F, 1.0F, new CubeDeformation(0.002F))
                .texOffs(867, 63).addBox(-5.0741F, -21.0F, 11.25F, 10.1482F, 0.0F, 1.0F, new CubeDeformation(0.002F))
                .texOffs(867, 33).addBox(11.25F, -21.0F, -5.0741F, 1.0F, 0.0F, 10.1482F, new CubeDeformation(0.002F))
                .texOffs(867, 11).addBox(-12.25F, -21.0F, -5.0741F, 1.0F, 0.0F, 10.1482F, new CubeDeformation(0.002F)), PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition octagon_r60 = octagon4.addOrReplaceChild("octagon_r60", CubeListBuilder.create().texOffs(867, 22).addBox(-12.25F, -21.0F, -5.0741F, 1.0F, 0.0F, 10.1482F, new CubeDeformation(0.002F))
                .texOffs(867, 44).addBox(11.25F, -21.0F, -5.0741F, 1.0F, 0.0F, 10.1482F, new CubeDeformation(0.002F))
                .texOffs(867, 65).addBox(-5.0741F, -21.0F, 11.25F, 10.1482F, 0.0F, 1.0F, new CubeDeformation(0.002F))
                .texOffs(867, 69).addBox(-5.0741F, -21.0F, -12.25F, 10.1482F, 0.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition octagon5 = cage.addOrReplaceChild("octagon5", CubeListBuilder.create().texOffs(867, 59).addBox(-5.0741F, 21.0F, -12.25F, 10.1482F, 0.0F, 1.0F, new CubeDeformation(0.002F))
                .texOffs(867, 55).addBox(-5.0741F, 21.0F, 11.25F, 10.1482F, 0.0F, 1.0F, new CubeDeformation(0.002F))
                .texOffs(865, 98).addBox(11.25F, 21.0F, -5.0741F, 1.0F, 0.0F, 10.1482F, new CubeDeformation(0.002F))
                .texOffs(862, 76).addBox(-12.25F, 21.0F, -5.0741F, 1.0F, 0.0F, 10.1482F, new CubeDeformation(0.002F)), PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition octagon_r61 = octagon5.addOrReplaceChild("octagon_r61", CubeListBuilder.create().texOffs(865, 87).addBox(-12.25F, 21.0F, -5.0741F, 1.0F, 0.0F, 10.1482F, new CubeDeformation(0.002F))
                .texOffs(867, 0).addBox(11.25F, 21.0F, -5.0741F, 1.0F, 0.0F, 10.1482F, new CubeDeformation(0.002F))
                .texOffs(867, 57).addBox(-5.0741F, 21.0F, 11.25F, 10.1482F, 0.0F, 1.0F, new CubeDeformation(0.002F))
                .texOffs(867, 61).addBox(-5.0741F, 21.0F, -12.25F, 10.1482F, 0.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone10 = cage.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition octagon_r62 = bone10.addOrReplaceChild("octagon_r62", CubeListBuilder.create().texOffs(867, 71).addBox(-11.9259F, -42.0F, -0.5F, 24.0F, 0.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

        PartDefinition octagon_r63 = bone10.addOrReplaceChild("octagon_r63", CubeListBuilder.create().texOffs(867, 71).addBox(-11.9259F, -42.0F, -0.5F, 24.0F, 0.0F, 1.0F, new CubeDeformation(0.002F))
                .texOffs(867, 71).addBox(-11.9259F, -84.0F, -0.5F, 24.0F, 0.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, 42.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition octagon_r64 = bone10.addOrReplaceChild("octagon_r64", CubeListBuilder.create().texOffs(867, 71).addBox(-11.9259F, -42.0F, -0.5F, 24.0F, 0.0F, 1.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, 42.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

        PartDefinition perimeter = coral.addOrReplaceChild("perimeter", CubeListBuilder.create().texOffs(789, 141).addBox(-3.8223F, -21.0F, -12.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(789, 141).addBox(-3.8223F, 19.0F, -12.5F, 8.0F, 2.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(947, 56).addBox(-12.0F, -21.1F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition octagon_r65 = perimeter.addOrReplaceChild("octagon_r65", CubeListBuilder.create().texOffs(789, 141).addBox(-3.8223F, -21.0F, -12.5F, 8.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition octagon_r66 = perimeter.addOrReplaceChild("octagon_r66", CubeListBuilder.create().texOffs(789, 141).addBox(-3.8223F, -21.0F, -12.5F, 8.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition octagon_r67 = perimeter.addOrReplaceChild("octagon_r67", CubeListBuilder.create().texOffs(789, 141).addBox(-3.8223F, -21.0F, -12.5F, 8.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition octagon_r68 = perimeter.addOrReplaceChild("octagon_r68", CubeListBuilder.create().texOffs(789, 141).addBox(-3.8223F, -21.0F, -12.5F, 8.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -3.098F, 0.0F));

        PartDefinition octagon_r69 = perimeter.addOrReplaceChild("octagon_r69", CubeListBuilder.create().texOffs(789, 141).addBox(-3.8223F, -21.0F, -12.5F, 8.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition octagon_r70 = perimeter.addOrReplaceChild("octagon_r70", CubeListBuilder.create().texOffs(789, 141).addBox(-3.8223F, -21.0F, -12.5F, 8.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition octagon_r71 = perimeter.addOrReplaceChild("octagon_r71", CubeListBuilder.create().texOffs(789, 141).addBox(-3.8223F, -21.0F, -12.5F, 8.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition door = perimeter.addOrReplaceChild("door", CubeListBuilder.create().texOffs(789, 141).addBox(-3.8223F, -13.0F, -12.5F, 8.0F, 32.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 1024, 1024);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        boolean isNotLanded = tardis.interiorChangingHandler().queued().get() || tardis.travel().getState() != TravelHandlerBase.State.LANDED;
        boolean hasCage = tardis.interiorChangingHandler().hasCage();
        seven.getChild("corallybits").visible = !hasCage && tardis.interiorChangingHandler().plasmicMaterialAmount() == 0;
        six.getChild("sixcorallybits").visible = !hasCage;

        door.visible = isNotLanded;

        float alpha = ((float) tardis.interiorChangingHandler().plasmicMaterialAmount() / 8f);

        super.renderWithAnimations(tardis, exterior, isNotLanded ? six : seven, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        if (hasCage) super.renderWithAnimations(tardis, exterior, cage, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        super.renderWithAnimations(tardis, exterior, perimeter, matrices, vertices, light, overlay, red, green, blue,
                alpha >= 1 ? pAlpha : alpha);
    }

    @Override
    public ModelPart root() {
        return coral;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {

    }
}
