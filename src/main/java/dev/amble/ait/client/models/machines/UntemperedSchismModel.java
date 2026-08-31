package dev.amble.ait.client.models.machines;

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

public class UntemperedSchismModel extends HierarchicalModel {
    private final ModelPart Schism;
    private final ModelPart schismRing;
    private final ModelPart ring;
    private final ModelPart OuterRing;
    private final ModelPart Outer_Ring2;
    private final ModelPart InnerRing;
    private final ModelPart RingFrame;
    private final ModelPart SchismBase;
    private final ModelPart bone9;
    private final ModelPart bone10;
    public UntemperedSchismModel(ModelPart root) {
        this.Schism = root.getChild("Schism");
        this.schismRing = this.Schism.getChild("schism Ring");
        this.ring = this.schismRing.getChild("ring");
        this.OuterRing = this.ring.getChild("Outer Ring");
        this.Outer_Ring2 = this.ring.getChild("Outer_Ring2");
        this.InnerRing = this.ring.getChild("Inner Ring");
        this.RingFrame = this.schismRing.getChild("RingFrame");
        this.SchismBase = this.Schism.getChild("Schism Base");
        this.bone9 = this.SchismBase.getChild("bone9");
        this.bone10 = this.SchismBase.getChild("bone10");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition Schism = modelPartData.addOrReplaceChild("Schism", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition schismRing = Schism.addOrReplaceChild("schism Ring", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition ring = schismRing.addOrReplaceChild("ring", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -44.511F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition OuterRing = ring.addOrReplaceChild("Outer Ring", CubeListBuilder.create().texOffs(0, 21).addBox(-18.0F, -43.489F, -8.0F, 36.0F, 8.0F, 16.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = OuterRing.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 21).addBox(-18.0F, -16.0F, -8.0F, 36.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.4142F, -19.4473F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r2 = OuterRing.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 21).addBox(-18.0F, -16.0F, -8.0F, 36.0F, 8.0F, 16.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(27.4558F, -0.0331F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r3 = OuterRing.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 21).addBox(-18.0F, -4.0F, -8.0F, 36.0F, 8.0F, 16.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(27.8995F, 27.9547F, 0.0F, 0.0F, 0.0F, 2.3562F));

        PartDefinition cube_r4 = OuterRing.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 21).addBox(-18.0F, -4.0F, -8.0F, 36.0F, 8.0F, 16.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 39.511F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r5 = OuterRing.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 21).addBox(-18.0F, -4.0F, -8.0F, 36.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-27.8995F, 27.9547F, 0.0F, 0.0F, 0.0F, -2.3562F));

        PartDefinition cube_r6 = OuterRing.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 21).addBox(-18.0F, -16.0F, -8.0F, 36.0F, 8.0F, 16.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-27.4558F, -0.0331F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r7 = OuterRing.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 21).addBox(-18.0F, -16.0F, -8.0F, 36.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.4142F, -19.4473F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition Outer_Ring2 = ring.addOrReplaceChild("Outer_Ring2", CubeListBuilder.create(), PartPose.offset(1.1342F, -0.6264F, 0.0F));

        PartDefinition cube_r8 = Outer_Ring2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(93, 83).addBox(-20.0F, -19.0F, -4.0F, 38.0F, 7.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(19.7312F, -18.3502F, -0.01F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r9 = Outer_Ring2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 45).addBox(-21.0F, -19.0F, -4.0F, 39.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(26.8842F, 1.9527F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r10 = Outer_Ring2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 45).addBox(-21.0F, -7.0F, -4.0F, 39.0F, 7.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(26.0811F, 29.8622F, 0.0F, 0.0F, 0.0F, 2.3562F));

        PartDefinition cube_r11 = Outer_Ring2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(-21.0F, -7.0F, -4.0F, 39.0F, 7.0F, 8.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-2.7071F, 40.544F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r12 = Outer_Ring2.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 45).addBox(-21.0F, -7.0F, -4.0F, 39.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-30.6166F, 27.7551F, -0.01F, 0.0F, 0.0F, -2.3562F));

        PartDefinition cube_r13 = Outer_Ring2.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(92, 83).mirror().addBox(-21.0F, -19.0F, -4.0F, 38.0F, 7.0F, 8.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-29.2843F, -1.0331F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition InnerRing = ring.addOrReplaceChild("Inner Ring", CubeListBuilder.create(), PartPose.offset(25.5069F, 25.562F, -0.9558F));

        PartDefinition cube_r14 = InnerRing.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-18.0F, -5.0F, -5.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 2.3562F));

        PartDefinition cube_r15 = InnerRing.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-18.0F, -5.0F, -4.5F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-25.5069F, 10.1823F, 2.2331F, -2.2689F, 0.0F, 0.0F));

        PartDefinition cube_r16 = InnerRing.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 64).addBox(-18.0F, -5.0F, -5.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-51.0137F, 0.0F, 1.9117F, -2.2689F, 0.0F, 0.7854F));

        PartDefinition cube_r17 = InnerRing.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 64).addBox(-18.0F, -5.0F, -5.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.9117F, -2.2689F, 0.0F, -0.7854F));

        PartDefinition cube_r18 = InnerRing.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, 64).addBox(-18.0F, -5.0F, -4.5F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-25.5069F, 10.1823F, -0.3214F, 0.8727F, 0.0F, 3.1416F));

        PartDefinition cube_r19 = InnerRing.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-18.0F, -5.0F, -5.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-51.0137F, 0.0F, 0.0F, 0.8727F, 0.0F, -2.3562F));

        PartDefinition cube_r20 = InnerRing.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-18.0F, -5.0F, -5.5F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-25.5069F, -61.2843F, 2.304F, -0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r21 = InnerRing.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 64).addBox(-18.0F, -1.0F, -8.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-25.5069F, -66.5366F, -1.1358F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r22 = InnerRing.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-18.0F, 1.0F, -8.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.3521F, -55.4541F, -2.6679F, 0.8727F, 0.0F, 0.7854F));

        PartDefinition cube_r23 = InnerRing.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 64).addBox(-18.0F, 1.0F, -2.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.3521F, -55.4541F, 4.5796F, -0.8727F, 0.0F, 0.7854F));

        PartDefinition cube_r24 = InnerRing.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 64).addBox(-18.0F, 1.0F, -2.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-55.3658F, -55.4541F, 4.5796F, -0.8727F, 0.0F, -0.7854F));

        PartDefinition cube_r25 = InnerRing.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-18.0F, 1.0F, -8.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-55.3658F, -55.4541F, -2.6679F, 0.8727F, 0.0F, -0.7854F));

        PartDefinition cube_r26 = InnerRing.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-18.0F, 0.0F, -2.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-67.091F, -25.5952F, 3.8135F, -0.8727F, 0.0F, -1.5708F));

        PartDefinition cube_r27 = InnerRing.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(0, 64).addBox(-18.0F, 0.0F, -8.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-67.091F, -25.5952F, -1.9019F, 0.8727F, 0.0F, -1.5708F));

        PartDefinition cube_r28 = InnerRing.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(0, 64).mirror().addBox(-18.0F, 0.0F, -2.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(16.0774F, -25.5952F, 3.8135F, -0.8727F, 0.0F, 1.5708F));

        PartDefinition cube_r29 = InnerRing.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(0, 64).addBox(-18.0F, 0.0F, -8.0F, 36.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0774F, -25.5952F, -1.9019F, 0.8727F, 0.0F, 1.5708F));

        PartDefinition RingFrame = schismRing.addOrReplaceChild("RingFrame", CubeListBuilder.create().texOffs(104, 21).addBox(11.4142F, 57.9584F, -9.0F, 16.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
        .texOffs(104, 21).addBox(11.4142F, -26.0416F, -9.0F, 16.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-19.4142F, -63.3584F, 0.0F));

        PartDefinition cube_r30 = RingFrame.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(104, 21).mirror().addBox(-8.0F, -18.0F, -9.0F, 16.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(38.8284F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r31 = RingFrame.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(104, 21).mirror().addBox(-8.0F, -18.0F, -9.0F, 16.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(46.87F, 19.4142F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r32 = RingFrame.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(104, 21).mirror().addBox(-8.0F, 11.0F, -9.0F, 16.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(38.8284F, 38.9167F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r33 = RingFrame.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(104, 21).addBox(-8.0F, 11.0F, -9.0F, 16.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 38.9167F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r34 = RingFrame.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(104, 21).addBox(-8.0F, -18.0F, -9.0F, 16.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0416F, 19.4142F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r35 = RingFrame.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(104, 21).addBox(-8.0F, -18.0F, -9.0F, 16.0F, 6.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition SchismBase = Schism.addOrReplaceChild("Schism Base", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, 15.0F, 76.0F, 9.0F, 12.0F, new CubeDeformation(0.003F)), PartPose.offset(-34.0F, 3.0F, -13.0F));

        PartDefinition bone9 = SchismBase.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(0, 104).addBox(-4.0F, -9.0F, -5.0F, 12.0F, 9.0F, 20.0F, new CubeDeformation(0.002F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r36 = bone9.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(92, 98).addBox(-7.0F, -4.5F, -12.0F, 13.0F, 9.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.3873F, -4.5F, 8.3445F, 0.0F, 0.5672F, 0.0F));

        PartDefinition bone10 = SchismBase.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(0, 104).mirror().addBox(-8.0F, -9.0F, -5.0F, 12.0F, 9.0F, 20.0F, new CubeDeformation(0.002F)).mirror(false), PartPose.offset(68.0F, 0.0F, 0.0F));

        PartDefinition cube_r37 = bone10.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(92, 98).mirror().addBox(-6.0F, -4.5F, -12.0F, 13.0F, 9.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.3873F, -4.5F, 8.3445F, 0.0F, -0.5672F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }

    @Override
    public ModelPart root() {
        return Schism;
    }
}