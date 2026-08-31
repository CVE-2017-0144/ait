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

public class AstralMapModel extends HierarchicalModel {
    private final ModelPart astral_map;
    public final ModelPart void_cube;

    public AstralMapModel(ModelPart root) {
        this.astral_map = root.getChild("astral_map");
        this.void_cube = this.astral_map.getChild("void_cube");

    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition astral_map = modelPartData.addOrReplaceChild("astral_map", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition base = astral_map.addOrReplaceChild("base", CubeListBuilder.create().texOffs(26, 35).addBox(-3.0455F, -16.0F, -3.2895F, 6.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 35).addBox(-3.0455F, -16.0F, 6.1029F, 6.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.5455F, -15.0F, -5.3895F, 16.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-5.5455F, -0.2F, -2.3895F, 11.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(68, 36).addBox(-4.0455F, -13.5F, 7.5918F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(68, 36).addBox(-4.0455F, -13.5F, -5.2646F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 40).addBox(-0.0455F, -16.0F, -8.2895F, 0.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(5, 40).mirror().addBox(0.0F, -8.0F, -2.5F, 0.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.1201F, -8.0F, 8.352F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r2 = base.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(5, 40).addBox(0.0F, -8.0F, -2.5F, 0.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.21F, -8.0F, 8.3537F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r3 = base.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(68, 36).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5215F, -7.5F, -1.5505F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r4 = base.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(68, 36).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.6125F, -7.5F, -1.5505F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r5 = base.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(68, 36).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.6125F, -7.5F, 4.8777F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r6 = base.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(68, 36).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5215F, -7.5F, 4.8777F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r7 = base.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(26, 35).addBox(-3.0F, -16.0F, 0.0F, 6.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5885F, 0.0F, 4.0048F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r8 = base.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(26, 35).addBox(-3.0F, -16.0F, 0.0F, 6.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.6795F, 0.0F, 4.0048F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r9 = base.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(26, 35).addBox(-3.0F, -16.0F, -1.0F, 6.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.6795F, 0.0F, -0.1914F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r10 = base.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(26, 35).addBox(-3.0F, -16.0F, -1.0F, 6.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5885F, 0.0F, -0.1914F, 0.0F, -1.0472F, 0.0F));

        PartDefinition top = astral_map.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 14).addBox(-7.5455F, -27.9F, -4.1895F, 15.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r11 = top.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(40, 78).addBox(-0.8F, -5.5F, -0.5F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.7033F, -22.4397F, 8.0422F, 0.1618F, -1.0418F, -0.1735F));

        PartDefinition cube_r12 = top.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(40, 78).addBox(-1.2F, -5.5F, -0.5F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7033F, -22.4397F, 8.0422F, 0.1618F, 1.0418F, 0.1735F));

        PartDefinition cube_r13 = top.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(40, 78).addBox(-1.1F, -5.5F, -0.5F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7162F, -22.46F, -4.5449F, -0.1618F, -1.0418F, 0.1735F));

        PartDefinition cube_r14 = top.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(40, 78).addBox(-0.9F, -5.5F, -0.5F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.7162F, -22.46F, -4.5449F, -0.1618F, 1.0418F, -0.1735F));

        PartDefinition cube_r15 = top.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(40, 78).addBox(-8.0F, -5.5F, -0.5F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4814F, -21.8444F, 1.8218F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r16 = top.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(40, 78).addBox(-1.0F, -5.5F, -0.5F, 2.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.4547F, -22.4545F, 1.7805F, 0.0F, 0.0F, -0.0873F));

        PartDefinition cube_r17 = top.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(31, 26).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.129F, -5.0647F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r18 = top.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(31, 26).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.946F, -11.129F, -1.6318F, 0.0873F, -1.0472F, 0.0F));

        PartDefinition cube_r19 = top.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(31, 26).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.946F, -11.129F, -1.6318F, 0.0873F, 1.0472F, 0.0F));

        PartDefinition cube_r20 = top.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(18, 69).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.946F, -11.129F, 5.2341F, -0.0873F, -1.0472F, 0.0F));

        PartDefinition cube_r21 = top.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(18, 69).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.129F, 8.6671F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r22 = top.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(18, 69).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.946F, -11.129F, 5.2341F, -0.0873F, 1.0472F, 0.0F));

        PartDefinition cube_r23 = top.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0215F, -22.0F, 5.2777F, 0.0873F, -1.0472F, 0.0F));

        PartDefinition cube_r24 = top.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0215F, -22.0F, 5.2777F, 0.0873F, 1.0472F, 0.0F));

        PartDefinition cube_r25 = top.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, 8.7542F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r26 = top.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0215F, -22.0F, -1.6754F, -0.0873F, 1.0472F, 0.0F));

        PartDefinition cube_r27 = top.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0215F, -22.0F, -1.6754F, -0.0873F, -1.0472F, 0.0F));

        PartDefinition cube_r28 = top.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, -5.1519F, -0.0873F, 0.0F, 0.0F));

        PartDefinition second = top.addOrReplaceChild("second", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r29 = second.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -6.0F, 0.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, -5.1519F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r30 = second.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -6.0F, 0.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0215F, -22.0F, -1.6754F, -0.0873F, -1.0472F, 0.0F));

        PartDefinition cube_r31 = second.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -6.0F, -1.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0215F, -22.0F, 5.2777F, 0.0873F, 1.0472F, 0.0F));

        PartDefinition cube_r32 = second.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -6.0F, -1.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, 8.7542F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r33 = second.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -6.0F, -1.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0215F, -22.0F, 5.2777F, 0.0873F, -1.0472F, 0.0F));

        PartDefinition cube_r34 = second.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -6.0F, 0.5F, 8.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0215F, -22.0F, -1.6754F, -0.0873F, 1.0472F, 0.0F));

        PartDefinition map_interface = top.addOrReplaceChild("map_interface", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r35 = map_interface.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, 0.7F, -0.8F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2636F, -16.8117F, -10.5184F, 0.9553F, 0.5236F, 0.6155F));

        PartDefinition cube_r36 = map_interface.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, 0.1F, -0.9F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1929F, -16.4375F, -10.0442F, 0.9553F, 0.5236F, 0.6155F));

        PartDefinition cube_r37 = map_interface.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -5.9F, -3.9F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 0).addBox(-3.0F, -5.9F, -3.9F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 35).addBox(-4.0F, -5.0F, -5.5F, 8.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(18, 77).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.1697F, -3.4024F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r38 = map_interface.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(38, 60).addBox(-4.0F, -6.0F, -0.5F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, -7.1519F, -0.0873F, 0.0F, 0.0F));

        PartDefinition void_cube = astral_map.addOrReplaceChild("void_cube", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r39 = void_cube.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(0, 78).addBox(-3.0F, -6.0F, 1.0F, 6.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, -5.1519F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r40 = void_cube.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(0, 78).addBox(-3.0F, -6.0F, 1.0F, 6.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0215F, -22.0F, -1.6754F, -0.0873F, -1.0472F, 0.0F));

        PartDefinition cube_r41 = void_cube.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(0, 78).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0215F, -22.0F, 5.2777F, 0.0873F, 1.0472F, 0.0F));

        PartDefinition cube_r42 = void_cube.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(0, 78).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, 8.7542F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r43 = void_cube.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(0, 78).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0215F, -22.0F, 5.2777F, 0.0873F, -1.0472F, 0.0F));

        PartDefinition cube_r44 = void_cube.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(0, 78).addBox(-3.0F, -6.0F, 1.0F, 6.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0215F, -22.0F, -1.6754F, -0.0873F, 1.0472F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public ModelPart root() {
        return astral_map;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
                          float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        astral_map.render(matrices, vertexConsumer, light, overlay, color);
    }
}