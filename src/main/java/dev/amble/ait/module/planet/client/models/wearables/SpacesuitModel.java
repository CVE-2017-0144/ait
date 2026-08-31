package dev.amble.ait.module.planet.client.models.wearables;

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
import net.minecraft.world.entity.LivingEntity;

public class SpacesuitModel extends EntityModel<LivingEntity> {
    public final ModelPart bone;
    public final ModelPart RightLeg;
    public final ModelPart right_leg_pant;
    public final ModelPart RightFoot;
    public final ModelPart LeftLeg;
    public final ModelPart left_leg_pant;
    public final ModelPart LeftFoot;
    public final ModelPart RightArm;
    public final ModelPart LeftArm;
    public final ModelPart Body;
    public final ModelPart bodyreal;
    public final ModelPart flagsize2;
    public final ModelPart flagsize1;
    public final ModelPart agencylogo1;
    public final ModelPart wirex;
    public final ModelPart wirex2;
    public final ModelPart wirex3;
    public final ModelPart wirex4;
    public final ModelPart wirex5;
    public final ModelPart wirex6;
    public final ModelPart Head;
    public SpacesuitModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.RightLeg = this.bone.getChild("RightLeg");
        this.right_leg_pant = this.RightLeg.getChild("right_leg_pant");
        this.RightFoot = this.RightLeg.getChild("RightFoot");
        this.LeftLeg = this.bone.getChild("LeftLeg");
        this.left_leg_pant = this.LeftLeg.getChild("left_leg_pant");
        this.LeftFoot = this.LeftLeg.getChild("LeftFoot");
        this.RightArm = this.bone.getChild("RightArm");
        this.LeftArm = this.bone.getChild("LeftArm");
        this.Body = this.bone.getChild("Body");
        this.bodyreal = this.Body.getChild("bodyreal");
        this.flagsize2 = this.bodyreal.getChild("flagsize2");
        this.flagsize1 = this.bodyreal.getChild("flagsize1");
        this.agencylogo1 = this.flagsize1.getChild("agencylogo1");
        this.wirex = this.bodyreal.getChild("wirex");
        this.wirex2 = this.bodyreal.getChild("wirex2");
        this.wirex3 = this.bodyreal.getChild("wirex3");
        this.wirex4 = this.bodyreal.getChild("wirex4");
        this.wirex5 = this.bodyreal.getChild("wirex5");
        this.wirex6 = this.bodyreal.getChild("wirex6");
        this.Head = this.bone.getChild("Head");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition bone = modelPartData.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition RightLeg = bone.addOrReplaceChild("RightLeg", CubeListBuilder.create(), PartPose.offset(-1.9F, -12.0F, 0.0F));

        PartDefinition right_leg_pant = RightLeg.addOrReplaceChild("right_leg_pant", CubeListBuilder.create().texOffs(50, 17).addBox(-3.9F, -4.75F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(58, 0).addBox(-3.9F, -5.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 4.75F, 0.0F));

        PartDefinition RightFoot = RightLeg.addOrReplaceChild("RightFoot", CubeListBuilder.create().texOffs(75, 0).addBox(-3.9F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(54, 34).addBox(-3.9F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(1.9F, 11.75F, 0.0F));

        PartDefinition LeftLeg = bone.addOrReplaceChild("LeftLeg", CubeListBuilder.create(), PartPose.offset(1.9F, -12.125F, 0.0F));

        PartDefinition left_leg_pant = LeftLeg.addOrReplaceChild("left_leg_pant", CubeListBuilder.create().texOffs(58, 0).mirror().addBox(-0.1F, -12.25F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(50, 17).mirror().addBox(-0.1F, -12.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(-1.9F, 12.125F, 0.0F));

        PartDefinition LeftFoot = LeftLeg.addOrReplaceChild("LeftFoot", CubeListBuilder.create().texOffs(75, 0).mirror().addBox(-0.1F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(54, 34).mirror().addBox(-0.1F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(-1.9F, 11.875F, 0.0F));

        PartDefinition RightArm = bone.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(47, 47).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 47).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(-5.0F, -22.0F, 0.0F));

        PartDefinition LeftArm = bone.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(47, 47).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(30, 47).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(122, 1).addBox(3.3F, -0.65F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(122, 11).addBox(3.3F, -1.15F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(122, 62).addBox(3.1F, -1.15F, -1.53F, 0.0F, 3.0F, 3.0F, new CubeDeformation(-0.3F))
                .texOffs(122, 66).addBox(3.12F, -1.15F, -1.53F, 0.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offset(5.0F, -22.0F, 0.0F));

        PartDefinition Body = bone.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition bodyreal = Body.addOrReplaceChild("bodyreal", CubeListBuilder.create().texOffs(33, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(79, 14).addBox(-4.0F, -3.0F, 2.0F, 8.0F, 15.0F, 4.0F, new CubeDeformation(0.5F))
                .texOffs(79, 35).addBox(-4.0F, -3.0F, 2.0F, 8.0F, 15.0F, 4.0F, new CubeDeformation(0.4F))
                .texOffs(0, 5).mirror().addBox(-3.0F, 8.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false)
                .texOffs(0, 5).addBox(1.0F, 8.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(2, 2).addBox(1.0F, 6.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(2, 2).addBox(1.0F, 4.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(2, 2).mirror().addBox(-3.0F, 4.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false)
                .texOffs(2, 2).mirror().addBox(-3.0F, 6.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false)
                .texOffs(18, 1).addBox(-2.0F, 0.7F, -2.8F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(29, 30).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.275F))
                .texOffs(1, 35).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition BodyLayer_r1 = bodyreal.addOrReplaceChild("BodyLayer_r1", CubeListBuilder.create().texOffs(40, 65).addBox(-6.0F, 0.0F, -2.0F, 7.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.25F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition flagsize2 = bodyreal.addOrReplaceChild("flagsize2", CubeListBuilder.create().texOffs(116, 24).addBox(-3.0F, -27.4F, 6.5F, 6.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition flagsize1 = bodyreal.addOrReplaceChild("flagsize1", CubeListBuilder.create().texOffs(118, 0).addBox(-2.5F, -27.4F, 6.5F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(116, 20).mirror().addBox(-0.7F, -27.8F, 6.12F, 4.0F, 4.0F, 0.0F, new CubeDeformation(-0.4F)).mirror(false)
                .texOffs(116, 24).addBox(-3.3F, -27.8F, 6.12F, 4.0F, 4.0F, 0.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition agencylogo1 = flagsize1.addOrReplaceChild("agencylogo1", CubeListBuilder.create().texOffs(124, 11).mirror().addBox(-3.9F, -22.9F, -2.3F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(124, 37).addBox(-3.9F, -22.4F, -2.31F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(124, 11).addBox(-1.0F, -22.9F, 6.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(124, 37).addBox(-1.0F, -22.4F, 6.51F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Body_r1 = agencylogo1.addOrReplaceChild("Body_r1", CubeListBuilder.create().texOffs(92, 73).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -21.9F, 6.51F, 0.0F, 0.0F, 1.5708F));

        PartDefinition Body_r2 = agencylogo1.addOrReplaceChild("Body_r2", CubeListBuilder.create().texOffs(124, 7).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -21.9F, 6.51F, 0.0F, 0.0F, 0.7854F));

        PartDefinition Body_r3 = agencylogo1.addOrReplaceChild("Body_r3", CubeListBuilder.create().texOffs(124, 7).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -21.9F, -2.31F, 0.0F, 0.0F, 2.3126F));

        PartDefinition wirex = bodyreal.addOrReplaceChild("wirex", CubeListBuilder.create(), PartPose.offset(4.5036F, 7.2208F, 0.6127F));

        PartDefinition Body_r4 = wirex.addOrReplaceChild("Body_r4", CubeListBuilder.create().texOffs(4, 15).addBox(1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -0.9599F, -2.3562F));

        PartDefinition Body_r5 = wirex.addOrReplaceChild("Body_r5", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.2626F, -0.2626F, 0.148F, -3.1416F, -1.2217F, -2.3562F));

        PartDefinition Body_r6 = wirex.addOrReplaceChild("Body_r6", CubeListBuilder.create().texOffs(4, 15).addBox(1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.3272F, -0.3272F, -3.3839F, 0.0F, -1.4399F, 0.7854F));

        PartDefinition Body_r7 = wirex.addOrReplaceChild("Body_r7", CubeListBuilder.create().texOffs(4, 15).addBox(0.0F, -0.7F, -0.3F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.5036F, -2.2208F, -3.1127F, 0.0F, 0.0F, 0.7854F));

        PartDefinition Body_r8 = wirex.addOrReplaceChild("Body_r8", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.634F, -0.634F, -2.4918F, 0.0F, -0.5672F, 0.7854F));

        PartDefinition wirex2 = bodyreal.addOrReplaceChild("wirex2", CubeListBuilder.create(), PartPose.offset(4.5036F, 9.2208F, 0.6127F));

        PartDefinition Body_r9 = wirex2.addOrReplaceChild("Body_r9", CubeListBuilder.create().texOffs(4, 15).addBox(1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -0.9599F, -2.3562F));

        PartDefinition Body_r10 = wirex2.addOrReplaceChild("Body_r10", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.2626F, -0.2626F, 0.148F, -3.1416F, -1.2217F, -2.3562F));

        PartDefinition Body_r11 = wirex2.addOrReplaceChild("Body_r11", CubeListBuilder.create().texOffs(4, 15).addBox(1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.3272F, -0.3272F, -3.3839F, 0.0F, -1.4399F, 0.7854F));

        PartDefinition Body_r12 = wirex2.addOrReplaceChild("Body_r12", CubeListBuilder.create().texOffs(4, 15).addBox(0.0F, -0.7F, -0.3F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.5036F, -2.2208F, -3.1127F, 0.0F, 0.0F, 0.7854F));

        PartDefinition Body_r13 = wirex2.addOrReplaceChild("Body_r13", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.634F, -0.634F, -2.4918F, 0.0F, -0.5672F, 0.7854F));

        PartDefinition wirex3 = bodyreal.addOrReplaceChild("wirex3", CubeListBuilder.create(), PartPose.offset(4.5036F, 11.2208F, 0.6127F));

        PartDefinition Body_r14 = wirex3.addOrReplaceChild("Body_r14", CubeListBuilder.create().texOffs(4, 15).addBox(1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -0.9599F, -2.3562F));

        PartDefinition Body_r15 = wirex3.addOrReplaceChild("Body_r15", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.2626F, -0.2626F, 0.148F, -3.1416F, -1.2217F, -2.3562F));

        PartDefinition Body_r16 = wirex3.addOrReplaceChild("Body_r16", CubeListBuilder.create().texOffs(4, 15).addBox(1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.3272F, -0.3272F, -3.3839F, 0.0F, -1.4399F, 0.7854F));

        PartDefinition Body_r17 = wirex3.addOrReplaceChild("Body_r17", CubeListBuilder.create().texOffs(4, 15).addBox(0.0F, -0.7F, -0.3F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-2.5036F, -2.2208F, -3.1127F, 0.0F, 0.0F, 0.7854F));

        PartDefinition Body_r18 = wirex3.addOrReplaceChild("Body_r18", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.634F, -0.634F, -2.4918F, 0.0F, -0.5672F, 0.7854F));

        PartDefinition wirex4 = bodyreal.addOrReplaceChild("wirex4", CubeListBuilder.create(), PartPose.offset(-4.5036F, 9.2208F, 0.6127F));

        PartDefinition Body_r19 = wirex4.addOrReplaceChild("Body_r19", CubeListBuilder.create().texOffs(4, 15).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.634F, -0.634F, -2.4918F, 0.0F, 0.5672F, -0.7854F));

        PartDefinition Body_r20 = wirex4.addOrReplaceChild("Body_r20", CubeListBuilder.create().texOffs(4, 15).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.2626F, -0.2626F, 0.148F, -3.1416F, 1.2217F, 2.3562F));

        PartDefinition Body_r21 = wirex4.addOrReplaceChild("Body_r21", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-3.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.9599F, 2.3562F));

        PartDefinition Body_r22 = wirex4.addOrReplaceChild("Body_r22", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-3.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.3272F, -0.3272F, -3.3839F, 0.0F, 1.4399F, -0.7854F));

        PartDefinition Body_r23 = wirex4.addOrReplaceChild("Body_r23", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-2.0F, -0.7F, -0.3F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.5036F, -2.2208F, -3.1127F, 0.0F, 0.0F, -0.7854F));

        PartDefinition wirex5 = bodyreal.addOrReplaceChild("wirex5", CubeListBuilder.create(), PartPose.offset(-4.5036F, 7.2208F, 0.6127F));

        PartDefinition Body_r24 = wirex5.addOrReplaceChild("Body_r24", CubeListBuilder.create().texOffs(4, 15).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.634F, -0.634F, -2.4918F, 0.0F, 0.5672F, -0.7854F));

        PartDefinition Body_r25 = wirex5.addOrReplaceChild("Body_r25", CubeListBuilder.create().texOffs(4, 15).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.2626F, -0.2626F, 0.148F, -3.1416F, 1.2217F, 2.3562F));

        PartDefinition Body_r26 = wirex5.addOrReplaceChild("Body_r26", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-3.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.9599F, 2.3562F));

        PartDefinition Body_r27 = wirex5.addOrReplaceChild("Body_r27", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-3.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.3272F, -0.3272F, -3.3839F, 0.0F, 1.4399F, -0.7854F));

        PartDefinition Body_r28 = wirex5.addOrReplaceChild("Body_r28", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-2.0F, -0.7F, -0.3F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.5036F, -2.2208F, -3.1127F, 0.0F, 0.0F, -0.7854F));

        PartDefinition wirex6 = bodyreal.addOrReplaceChild("wirex6", CubeListBuilder.create(), PartPose.offset(-4.5036F, 11.2208F, 0.6127F));

        PartDefinition Body_r29 = wirex6.addOrReplaceChild("Body_r29", CubeListBuilder.create().texOffs(4, 15).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.634F, -0.634F, -2.4918F, 0.0F, 0.5672F, -0.7854F));

        PartDefinition Body_r30 = wirex6.addOrReplaceChild("Body_r30", CubeListBuilder.create().texOffs(4, 15).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.2626F, -0.2626F, 0.148F, -3.1416F, 1.2217F, 2.3562F));

        PartDefinition Body_r31 = wirex6.addOrReplaceChild("Body_r31", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-3.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.9599F, 2.3562F));

        PartDefinition Body_r32 = wirex6.addOrReplaceChild("Body_r32", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-3.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.3272F, -0.3272F, -3.3839F, 0.0F, 1.4399F, -0.7854F));

        PartDefinition Body_r33 = wirex6.addOrReplaceChild("Body_r33", CubeListBuilder.create().texOffs(4, 15).mirror().addBox(-2.0F, -0.7F, -0.3F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(2.5036F, -2.2208F, -3.1127F, 0.0F, 0.0F, -0.7854F));

        PartDefinition Head = bone.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 63).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -24.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }
    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        bone.render(matrices, vertexConsumer, light, overlay, color);
    }
}