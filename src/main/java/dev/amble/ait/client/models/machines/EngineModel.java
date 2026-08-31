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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.tardis.Tardis;

public class EngineModel extends HierarchicalModel {
    private final ModelPart coral;
    private final ModelPart base;
    private final ModelPart one;
    private final ModelPart two;
    private final ModelPart three;
    private final ModelPart four;
    private final ModelPart five;
    private final ModelPart six;

    public EngineModel(ModelPart root) {
        this.coral = root.getChild("coral");
        this.base = this.coral.getChild("base");
        this.one = this.coral.getChild("one");
        this.two = this.coral.getChild("two");
        this.three = this.coral.getChild("three");
        this.four = this.coral.getChild("four");
        this.five = this.coral.getChild("five");
        this.six = this.coral.getChild("six");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition coral = modelPartData.addOrReplaceChild("coral", CubeListBuilder.create(), PartPose.offset(0.0F, 10.15F, 0.0F));

        PartDefinition base = coral.addOrReplaceChild("base", CubeListBuilder.create().texOffs(341, 460).addBox(-24.0F, 8.0F, -24.0F, 48.0F, 0.0F, 48.0F, new CubeDeformation(0.0F))
                .texOffs(357, 427).addBox(-16.0F, -5.85F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(383, 524).addBox(-3.0F, -3.0F, -24.0F, 6.0F, 6.0F, 48.0F, new CubeDeformation(0.005F)), PartPose.offset(0.0F, 5.85F, 0.0F));

        PartDefinition cube_r1 = base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(383, 524).addBox(-3.0F, -3.0F, -24.0F, 6.0F, 6.0F, 48.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = base.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(373, 508).addBox(-24.0F, -20.7846F, -12.0F, 48.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, -1.5708F, 1.0472F));

        PartDefinition cube_r3 = base.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(373, 508).addBox(-24.0F, -20.7846F, -12.0F, 48.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, -2.0944F, 0.0F, -3.1416F));

        PartDefinition cube_r4 = base.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(373, 508).addBox(-24.0F, -20.7846F, -12.0F, 48.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 1.5708F, -1.0472F));

        PartDefinition cube_r5 = base.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(373, 508).addBox(-24.0F, -20.7846F, -12.0F, 48.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 1.0472F, 0.0F, 0.0F));

        PartDefinition bone2 = base.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition cube_r6 = bone2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(391, 529).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.0416F, 0.0F, -24.0416F, 0.9163F, 0.7854F, 0.0F));

        PartDefinition bone3 = base.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r7 = bone3.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(391, 529).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.0416F, 0.0F, -24.0416F, 0.9163F, 0.7854F, 0.0F));

        PartDefinition bone4 = base.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r8 = bone4.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(391, 529).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.0416F, 0.0F, -24.0416F, 0.9163F, 0.7854F, 0.0F));

        PartDefinition bone5 = base.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r9 = bone5.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(391, 529).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.0416F, 0.0F, -24.0416F, 0.9163F, 0.7854F, 0.0F));

        PartDefinition one = coral.addOrReplaceChild("one", CubeListBuilder.create().texOffs(296, 363).addBox(-16.0F, -0.25F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r10 = one.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(130, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.0F, 2.5F, 0.0F, 0.0F, 1.5708F, 0.3491F));

        PartDefinition cube_r11 = one.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(195, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 2.5F, -2.0F, 2.7925F, 0.0F, -3.1416F));

        PartDefinition cube_r12 = one.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(260, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.0F, 2.5F, 0.0F, 0.0F, -1.5708F, -0.3491F));

        PartDefinition cube_r13 = one.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(325, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 2.5F, 2.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r14 = one.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(245, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r15 = one.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(294, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition two = coral.addOrReplaceChild("two", CubeListBuilder.create().texOffs(296, 331).addBox(-16.0F, -0.25F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r16 = two.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(390, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r17 = two.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(455, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r18 = two.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(520, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r19 = two.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r20 = two.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(147, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r21 = two.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(196, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone42 = two.addOrReplaceChild("bone42", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -32.0F, 0.0F, 0.0F, -1.1781F, 0.0F));

        PartDefinition bone43 = bone42.addOrReplaceChild("bone43", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition bone44 = bone42.addOrReplaceChild("bone44", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition bone45 = bone42.addOrReplaceChild("bone45", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r22 = bone45.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r23 = bone45.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r24 = bone45.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition bone46 = bone42.addOrReplaceChild("bone46", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition bone47 = bone42.addOrReplaceChild("bone47", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r25 = bone47.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r26 = bone47.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r27 = bone47.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition three = coral.addOrReplaceChild("three", CubeListBuilder.create().texOffs(296, 331).addBox(-16.0F, -0.25F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r28 = three.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(390, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r29 = three.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(455, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r30 = three.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(520, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r31 = three.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(0, 290).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r32 = three.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(49, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r33 = three.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(98, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone36 = three.addOrReplaceChild("bone36", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -32.0F, 0.0F, 0.0F, -1.1781F, 0.0F));

        PartDefinition bone37 = bone36.addOrReplaceChild("bone37", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition bone38 = bone36.addOrReplaceChild("bone38", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r34 = bone38.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r35 = bone38.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r36 = bone38.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone39 = bone36.addOrReplaceChild("bone39", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r37 = bone39.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r38 = bone39.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r39 = bone39.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition bone40 = bone36.addOrReplaceChild("bone40", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition bone41 = bone36.addOrReplaceChild("bone41", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r40 = bone41.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r41 = bone41.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r42 = bone41.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition four = coral.addOrReplaceChild("four", CubeListBuilder.create().texOffs(296, 299).addBox(-16.0F, -0.25F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r43 = four.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(455, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(4.0F, 2.5F, 0.0F, 0.0F, 1.5708F, 0.3491F));

        PartDefinition cube_r44 = four.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(520, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 2.5F, -4.0F, 2.7925F, 0.0F, -3.1416F));

        PartDefinition cube_r45 = four.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(0, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.0F, 2.5F, 0.0F, 0.0F, -1.5708F, -0.3491F));

        PartDefinition cube_r46 = four.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(65, 137).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 2.5F, 4.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r47 = four.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(50, 386).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r48 = four.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(75, 386).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0873F));

        PartDefinition cube_r49 = four.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(100, 386).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 3.0543F, 0.0F, -3.1416F));

        PartDefinition cube_r50 = four.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(125, 386).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.0873F));

        PartDefinition cube_r51 = four.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(0, 222).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -19.0F, 2.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r52 = four.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(520, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.0F, -19.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r53 = four.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(455, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.0F, -19.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r54 = four.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(390, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -19.0F, -2.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r55 = four.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(260, 222).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.0F, -8.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r56 = four.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(195, 222).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -8.0F, 2.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r57 = four.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(130, 222).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -8.0F, -2.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r58 = four.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(65, 222).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.0F, -8.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r59 = four.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(130, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r60 = four.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(195, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r61 = four.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(260, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r62 = four.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(325, 273).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r63 = four.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(390, 0).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r64 = four.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(0, 43).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone28 = four.addOrReplaceChild("bone28", CubeListBuilder.create(), PartPose.offset(0.0F, -32.0F, 0.0F));

        PartDefinition bone31 = bone28.addOrReplaceChild("bone31", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r65 = bone31.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -2.5858F, -13.4142F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-7.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r66 = bone31.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -2.5858F, -13.4142F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone32 = bone28.addOrReplaceChild("bone32", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r67 = bone32.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r68 = bone32.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r69 = bone32.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone33 = bone28.addOrReplaceChild("bone33", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r70 = bone33.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r71 = bone33.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r72 = bone33.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition bone34 = bone28.addOrReplaceChild("bone34", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r73 = bone34.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r74 = bone34.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r75 = bone34.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition bone35 = bone28.addOrReplaceChild("bone35", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r76 = bone35.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r77 = bone35.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r78 = bone35.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition five = coral.addOrReplaceChild("five", CubeListBuilder.create().texOffs(232, 299).addBox(-16.0F, -0.25F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r79 = five.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(343, 43).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.0F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition cube_r80 = five.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(0, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, -8.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r81 = five.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(65, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 3.0F, 0.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r82 = five.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(130, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 8.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r83 = five.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(65, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -1.0F, -6.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r84 = five.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(0, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-6.0F, -1.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r85 = five.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(520, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -1.0F, 6.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r86 = five.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(455, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(6.0F, -1.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r87 = five.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(390, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F))
                .texOffs(130, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -26.0F, -2.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r88 = five.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(325, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F))
                .texOffs(325, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.0F, -26.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r89 = five.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(260, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F))
                .texOffs(260, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -26.0F, 2.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r90 = five.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(195, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F))
                .texOffs(195, 205).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.0F, -26.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r91 = five.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(260, 256).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.4363F, -0.7854F, 0.0F));

        PartDefinition cube_r92 = five.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(195, 256).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r93 = five.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(455, 256).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 2.7053F, 0.7854F, 3.1416F));

        PartDefinition cube_r94 = five.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(390, 256).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.4363F));

        PartDefinition cube_r95 = five.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(325, 256).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.4363F, 0.7854F, 0.0F));

        PartDefinition cube_r96 = five.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(520, 256).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 2.7053F, 0.0F, 3.1416F));

        PartDefinition cube_r97 = five.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(0, 273).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 2.7053F, -0.7854F, 3.1416F));

        PartDefinition cube_r98 = five.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(65, 273).addBox(-8.0F, -6.9583F, -7.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.0F, -1.5708F, -0.4363F));

        PartDefinition cube_r99 = five.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(226, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.0873F));

        PartDefinition cube_r100 = five.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(130, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -14.0F, -2.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r101 = five.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(65, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.0F, -14.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r102 = five.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(0, 188).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -14.0F, 2.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r103 = five.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(520, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.0F, -14.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r104 = five.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(251, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0873F));

        PartDefinition cube_r105 = five.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(0, 386).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 3.0543F, 0.0F, -3.1416F));

        PartDefinition cube_r106 = five.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(25, 386).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r107 = five.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(292, 0).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r108 = five.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(341, 0).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone24 = five.addOrReplaceChild("bone24", CubeListBuilder.create(), PartPose.offset(0.0F, -35.0F, 0.0F));

        PartDefinition bone25 = bone24.addOrReplaceChild("bone25", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r109 = bone25.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -2.5858F, -13.4142F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r110 = bone25.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -2.5858F, -13.4142F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r111 = bone25.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -2.5858F, -13.4142F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r112 = bone25.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -2.5858F, -13.4142F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone26 = bone24.addOrReplaceChild("bone26", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r113 = bone26.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(7.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r114 = bone26.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r115 = bone26.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-7.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r116 = bone26.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone27 = bone24.addOrReplaceChild("bone27", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r117 = bone27.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(7.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r118 = bone27.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r119 = bone27.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-7.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r120 = bone27.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone29 = bone24.addOrReplaceChild("bone29", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r121 = bone29.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(7.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r122 = bone29.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r123 = bone29.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-7.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r124 = bone29.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone30 = bone24.addOrReplaceChild("bone30", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r125 = bone30.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(7.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r126 = bone30.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r127 = bone30.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-7.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r128 = bone30.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition six = coral.addOrReplaceChild("six", CubeListBuilder.create().texOffs(232, 299).addBox(-16.0F, -0.25F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r129 = six.addOrReplaceChild("cube_r129", CubeListBuilder.create().texOffs(195, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 11.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r130 = six.addOrReplaceChild("cube_r130", CubeListBuilder.create().texOffs(260, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(11.0F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition cube_r131 = six.addOrReplaceChild("cube_r131", CubeListBuilder.create().texOffs(325, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, -11.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r132 = six.addOrReplaceChild("cube_r132", CubeListBuilder.create().texOffs(390, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-11.0F, 3.0F, 0.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r133 = six.addOrReplaceChild("cube_r133", CubeListBuilder.create().texOffs(455, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -2.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r134 = six.addOrReplaceChild("cube_r134", CubeListBuilder.create().texOffs(390, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -2.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r135 = six.addOrReplaceChild("cube_r135", CubeListBuilder.create().texOffs(325, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -2.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r136 = six.addOrReplaceChild("cube_r136", CubeListBuilder.create().texOffs(260, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -2.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r137 = six.addOrReplaceChild("cube_r137", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -28.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r138 = six.addOrReplaceChild("cube_r138", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -28.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r139 = six.addOrReplaceChild("cube_r139", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -28.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r140 = six.addOrReplaceChild("cube_r140", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -28.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r141 = six.addOrReplaceChild("cube_r141", CubeListBuilder.create().texOffs(0, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -15.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r142 = six.addOrReplaceChild("cube_r142", CubeListBuilder.create().texOffs(65, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -15.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r143 = six.addOrReplaceChild("cube_r143", CubeListBuilder.create().texOffs(130, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -15.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r144 = six.addOrReplaceChild("cube_r144", CubeListBuilder.create().texOffs(195, 171).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -15.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r145 = six.addOrReplaceChild("cube_r145", CubeListBuilder.create().texOffs(390, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -37.0F, 0.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition cube_r146 = six.addOrReplaceChild("cube_r146", CubeListBuilder.create().texOffs(325, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.0F, -9.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r147 = six.addOrReplaceChild("cube_r147", CubeListBuilder.create().texOffs(520, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.0F, 9.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r148 = six.addOrReplaceChild("cube_r148", CubeListBuilder.create().texOffs(455, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -37.0F, 0.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r149 = six.addOrReplaceChild("cube_r149", CubeListBuilder.create().texOffs(325, 222).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, 0.0F, -1.5708F, 2.7053F));

        PartDefinition cube_r150 = six.addOrReplaceChild("cube_r150", CubeListBuilder.create().texOffs(390, 222).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, 2.7053F, -0.7854F, 0.0F));

        PartDefinition cube_r151 = six.addOrReplaceChild("cube_r151", CubeListBuilder.create().texOffs(455, 222).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, 2.7053F, 0.0F, 0.0F));

        PartDefinition cube_r152 = six.addOrReplaceChild("cube_r152", CubeListBuilder.create().texOffs(520, 222).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, -0.4363F, 0.7854F, -3.1416F));

        PartDefinition cube_r153 = six.addOrReplaceChild("cube_r153", CubeListBuilder.create().texOffs(0, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, 0.0F, 1.5708F, -2.7053F));

        PartDefinition cube_r154 = six.addOrReplaceChild("cube_r154", CubeListBuilder.create().texOffs(65, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, 2.7053F, 0.7854F, 0.0F));

        PartDefinition cube_r155 = six.addOrReplaceChild("cube_r155", CubeListBuilder.create().texOffs(130, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, -0.4363F, 0.0F, -3.1416F));

        PartDefinition cube_r156 = six.addOrReplaceChild("cube_r156", CubeListBuilder.create().texOffs(195, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -42.0F, 0.0F, -0.4363F, -0.7854F, -3.1416F));

        PartDefinition cube_r157 = six.addOrReplaceChild("cube_r157", CubeListBuilder.create().texOffs(260, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.4363F, -0.7854F, 0.0F));

        PartDefinition cube_r158 = six.addOrReplaceChild("cube_r158", CubeListBuilder.create().texOffs(325, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r159 = six.addOrReplaceChild("cube_r159", CubeListBuilder.create().texOffs(390, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.7053F, 0.7854F, 3.1416F));

        PartDefinition cube_r160 = six.addOrReplaceChild("cube_r160", CubeListBuilder.create().texOffs(455, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 1.5708F, 0.4363F));

        PartDefinition cube_r161 = six.addOrReplaceChild("cube_r161", CubeListBuilder.create().texOffs(520, 239).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.4363F, 0.7854F, 0.0F));

        PartDefinition cube_r162 = six.addOrReplaceChild("cube_r162", CubeListBuilder.create().texOffs(0, 256).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.7053F, 0.0F, 3.1416F));

        PartDefinition cube_r163 = six.addOrReplaceChild("cube_r163", CubeListBuilder.create().texOffs(65, 256).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 2.7053F, -0.7854F, 3.1416F));

        PartDefinition cube_r164 = six.addOrReplaceChild("cube_r164", CubeListBuilder.create().texOffs(130, 256).addBox(-8.0F, -6.9583F, -5.6559F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -1.5708F, -0.4363F));

        PartDefinition cube_r165 = six.addOrReplaceChild("cube_r165", CubeListBuilder.create().texOffs(126, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.0873F));

        PartDefinition cube_r166 = six.addOrReplaceChild("cube_r166", CubeListBuilder.create().texOffs(151, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0873F));

        PartDefinition cube_r167 = six.addOrReplaceChild("cube_r167", CubeListBuilder.create().texOffs(176, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 3.0543F, 0.0F, -3.1416F));

        PartDefinition cube_r168 = six.addOrReplaceChild("cube_r168", CubeListBuilder.create().texOffs(201, 343).addBox(-6.0F, -36.0F, -6.0F, 12.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r169 = six.addOrReplaceChild("cube_r169", CubeListBuilder.create().texOffs(194, 0).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r170 = six.addOrReplaceChild("cube_r170", CubeListBuilder.create().texOffs(243, 0).addBox(-12.0F, -42.0F, 0.0F, 24.0F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition octagon2 = six.addOrReplaceChild("octagon2", CubeListBuilder.create(), PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition bone14 = six.addOrReplaceChild("bone14", CubeListBuilder.create(), PartPose.offset(0.0F, -35.0F, 0.0F));

        PartDefinition bone6 = bone14.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r171 = bone6.addOrReplaceChild("cube_r171", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r172 = bone6.addOrReplaceChild("cube_r172", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r173 = bone6.addOrReplaceChild("cube_r173", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r174 = bone6.addOrReplaceChild("cube_r174", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone12 = bone14.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r175 = bone12.addOrReplaceChild("cube_r175", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r176 = bone12.addOrReplaceChild("cube_r176", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r177 = bone12.addOrReplaceChild("cube_r177", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r178 = bone12.addOrReplaceChild("cube_r178", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone13 = bone14.addOrReplaceChild("bone13", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r179 = bone13.addOrReplaceChild("cube_r179", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r180 = bone13.addOrReplaceChild("cube_r180", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r181 = bone13.addOrReplaceChild("cube_r181", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r182 = bone13.addOrReplaceChild("cube_r182", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone11 = bone14.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r183 = bone11.addOrReplaceChild("cube_r183", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r184 = bone11.addOrReplaceChild("cube_r184", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r185 = bone11.addOrReplaceChild("cube_r185", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r186 = bone11.addOrReplaceChild("cube_r186", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone7 = bone14.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r187 = bone7.addOrReplaceChild("cube_r187", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r188 = bone7.addOrReplaceChild("cube_r188", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r189 = bone7.addOrReplaceChild("cube_r189", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r190 = bone7.addOrReplaceChild("cube_r190", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone10 = bone14.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r191 = bone10.addOrReplaceChild("cube_r191", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r192 = bone10.addOrReplaceChild("cube_r192", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r193 = bone10.addOrReplaceChild("cube_r193", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r194 = bone10.addOrReplaceChild("cube_r194", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone9 = bone14.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r195 = bone9.addOrReplaceChild("cube_r195", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r196 = bone9.addOrReplaceChild("cube_r196", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r197 = bone9.addOrReplaceChild("cube_r197", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r198 = bone9.addOrReplaceChild("cube_r198", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone8 = bone14.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 1.5708F, 3.1416F));

        PartDefinition cube_r199 = bone8.addOrReplaceChild("cube_r199", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r200 = bone8.addOrReplaceChild("cube_r200", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r201 = bone8.addOrReplaceChild("cube_r201", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r202 = bone8.addOrReplaceChild("cube_r202", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone15 = six.addOrReplaceChild("bone15", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -39.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition bone16 = bone15.addOrReplaceChild("bone16", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r203 = bone16.addOrReplaceChild("cube_r203", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r204 = bone16.addOrReplaceChild("cube_r204", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r205 = bone16.addOrReplaceChild("cube_r205", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r206 = bone16.addOrReplaceChild("cube_r206", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone17 = bone15.addOrReplaceChild("bone17", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r207 = bone17.addOrReplaceChild("cube_r207", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r208 = bone17.addOrReplaceChild("cube_r208", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r209 = bone17.addOrReplaceChild("cube_r209", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r210 = bone17.addOrReplaceChild("cube_r210", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone18 = bone15.addOrReplaceChild("bone18", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r211 = bone18.addOrReplaceChild("cube_r211", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r212 = bone18.addOrReplaceChild("cube_r212", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r213 = bone18.addOrReplaceChild("cube_r213", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r214 = bone18.addOrReplaceChild("cube_r214", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone19 = bone15.addOrReplaceChild("bone19", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r215 = bone19.addOrReplaceChild("cube_r215", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r216 = bone19.addOrReplaceChild("cube_r216", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r217 = bone19.addOrReplaceChild("cube_r217", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r218 = bone19.addOrReplaceChild("cube_r218", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone20 = bone15.addOrReplaceChild("bone20", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r219 = bone20.addOrReplaceChild("cube_r219", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r220 = bone20.addOrReplaceChild("cube_r220", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r221 = bone20.addOrReplaceChild("cube_r221", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r222 = bone20.addOrReplaceChild("cube_r222", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone21 = bone15.addOrReplaceChild("bone21", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r223 = bone21.addOrReplaceChild("cube_r223", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r224 = bone21.addOrReplaceChild("cube_r224", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r225 = bone21.addOrReplaceChild("cube_r225", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r226 = bone21.addOrReplaceChild("cube_r226", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone22 = bone15.addOrReplaceChild("bone22", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r227 = bone22.addOrReplaceChild("cube_r227", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r228 = bone22.addOrReplaceChild("cube_r228", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r229 = bone22.addOrReplaceChild("cube_r229", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r230 = bone22.addOrReplaceChild("cube_r230", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition bone23 = bone15.addOrReplaceChild("bone23", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.0F, 1.5708F, 3.1416F));

        PartDefinition cube_r231 = bone23.addOrReplaceChild("cube_r231", CubeListBuilder.create().texOffs(260, 154).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r232 = bone23.addOrReplaceChild("cube_r232", CubeListBuilder.create().texOffs(195, 154).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r233 = bone23.addOrReplaceChild("cube_r233", CubeListBuilder.create().texOffs(65, 154).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r234 = bone23.addOrReplaceChild("cube_r234", CubeListBuilder.create().texOffs(130, 154).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));
        return LayerDefinition.create(modelData, 1024, 1024);
    }

    @Override
    public ModelPart root() {
        return this.coral;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
    }

    public void render(Tardis tardis, PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
                       float green, float blue, float alpha) {
        ModelPart modelPart = getModelPartBasedOnSubsystems(tardis);
        if (modelPart != base)
            modelPart.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        base.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    private ModelPart getModelPartBasedOnSubsystems(Tardis tardis) {
        int stage = 0;

        if (tardis.subsystems().get(SubSystem.Id.LIFE_SUPPORT).isUsable()) {
            stage++;
        }
        if (tardis.subsystems().get(SubSystem.Id.DEMAT).isUsable()) {
            stage++;
        }
        if (tardis.subsystems().get(SubSystem.Id.STABILISERS).isUsable() && tardis.subsystems().get(SubSystem.Id.GRAVITATIONAL).isUsable()) {
            stage++;
        }
        if (tardis.subsystems().get(SubSystem.Id.SHIELDS).isUsable()) {
            stage++;
        }
        if (tardis.subsystems().get(SubSystem.Id.CHAMELEON).isUsable()) {
            stage++;
        }
        if (tardis.subsystems().get(SubSystem.Id.DESPERATION).isUsable() && tardis.subsystems().get(SubSystem.Id.EMERGENCY_POWER).isUsable()) {
            stage++;
        }

        return switch (stage) {
            case 1 -> this.one;
            case 2 -> this.two;
            case 3 -> this.three;
            case 4 -> this.four;
            case 5 -> this.five;
            case 6 -> this.six;
            default -> this.base;
        };
    }
}
