package dev.amble.ait.client.models.exteriors.exclusive;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.models.exteriors.SimpleExteriorModel;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.handler.DoorHandler;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class BlueBoxExteriorModel extends SimpleExteriorModel {
    private final ModelPart box;
    private final ModelPart base;
    private final ModelPart left_door2;
    private final ModelPart pulltoopen;
    private final ModelPart phone;
    private final ModelPart right_door2;

    public BlueBoxExteriorModel(ModelPart root) {
        this.box = root.getChild("box");
        this.base = this.box.getChild("base");
        this.left_door2 = this.box.getChild("left_door2");
        this.pulltoopen = this.left_door2.getChild("pulltoopen");
        this.phone = this.pulltoopen.getChild("phone");
        this.right_door2 = this.box.getChild("right_door2");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition box = modelPartData.addOrReplaceChild("box", CubeListBuilder.create(), PartPose.offset(-6.0F, 24.0F, 0.0F));

        PartDefinition base = box.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 48).addBox(-20.0F, -3.0F, -20.0F, 40.0F, 3.0F, 40.0F, new CubeDeformation(0.0F))
        .texOffs(39, 39).addBox(-18.0F, -71.0F, -21.0F, 36.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(115, 0).addBox(-18.0F, -75.0F, -18.0F, 36.0F, 4.0F, 36.0F, new CubeDeformation(0.0F))
        .texOffs(121, 48).addBox(-16.0F, -76.0F, -16.0F, 32.0F, 1.0F, 32.0F, new CubeDeformation(0.0F))
        .texOffs(62, 193).addBox(-10.0F, -77.0F, -10.0F, 20.0F, 1.0F, 20.0F, new CubeDeformation(0.0F))
        .texOffs(39, 91).addBox(18.0F, -71.0F, -18.0F, 3.0F, 5.0F, 36.0F, new CubeDeformation(0.0F))
        .texOffs(0, 91).addBox(-21.0F, -71.0F, -18.0F, 3.0F, 5.0F, 36.0F, new CubeDeformation(0.0F))
        .texOffs(156, 133).addBox(-14.0F, -66.0F, -18.5F, 28.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(156, 133).addBox(-14.0F, -66.0F, 17.5F, 28.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(36, 134).addBox(17.5F, -66.0F, -14.0F, 1.0F, 1.0F, 28.0F, new CubeDeformation(0.0F))
        .texOffs(0, 134).addBox(-18.5F, -66.0F, -14.0F, 1.0F, 1.0F, 28.0F, new CubeDeformation(0.0F))
        .texOffs(31, 164).addBox(-18.0F, -65.0F, 0.0F, 1.0F, 62.0F, 14.0F, new CubeDeformation(0.0F))
        .texOffs(0, 164).addBox(-18.0F, -65.0F, -14.0F, 1.0F, 62.0F, 14.0F, new CubeDeformation(0.0F))
        .texOffs(169, 213).addBox(-18.5F, -65.0F, -0.5F, 1.0F, 62.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(159, 213).addBox(17.5F, -65.0F, -0.5F, 1.0F, 62.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(193, 136).addBox(14.0F, -73.0F, -19.0F, 5.0F, 70.0F, 5.0F, new CubeDeformation(0.0F))
        .texOffs(0, 12).addBox(-4.0F, -86.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
        .texOffs(25, 22).addBox(-2.0F, -85.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(25, 13).addBox(-3.25F, -85.0F, -3.25F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(25, 12).addBox(3.25F, -85.0F, -3.25F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(25, 12).addBox(-3.25F, -85.0F, -3.25F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(25, 13).addBox(2.25F, -85.0F, -3.25F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(5, 1).addBox(2.25F, -85.0F, 3.25F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(5, 0).addBox(3.25F, -85.0F, 2.25F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(5, 1).addBox(-3.25F, -85.0F, 3.25F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(5, 0).addBox(-3.25F, -85.0F, 2.25F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(0, 0).addBox(-4.5F, -79.0F, -4.5F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
        .texOffs(0, 22).addBox(-3.0F, -86.85F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
        .texOffs(193, 136).mirror().addBox(-19.0F, -73.0F, -19.0F, 5.0F, 70.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
        .texOffs(193, 136).addBox(-19.0F, -73.0F, 14.0F, 5.0F, 70.0F, 5.0F, new CubeDeformation(0.0F))
        .texOffs(193, 136).mirror().addBox(14.0F, -73.0F, 14.0F, 5.0F, 70.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(162, 136).addBox(-0.5F, -31.0F, -7.0F, 1.0F, 62.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.5F, -34.0F, 7.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r2 = base.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(131, 136).addBox(-0.5F, -31.0F, -7.0F, 1.0F, 62.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.5F, -34.0F, -7.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r3 = base.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(215, 123).addBox(-0.5F, -31.0F, -7.0F, 1.0F, 62.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -34.0F, 17.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r4 = base.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(164, 213).addBox(-0.75F, -31.0F, -0.5F, 1.0F, 62.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -34.0F, 17.75F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r5 = base.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(63, 202).addBox(-0.5F, -31.0F, -7.0F, 1.0F, 62.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -34.0F, 17.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r6 = base.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(38, 38).mirror().addBox(-18.0F, -2.5F, -4.0F, 36.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -68.5F, 17.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r7 = base.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(6, 50).addBox(-18.0F, -1.5F, -18.0F, 36.0F, 3.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -67.25F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition left_door2 = box.addOrReplaceChild("left_door2", CubeListBuilder.create().texOffs(93, 215).addBox(0.0F, -33.0F, -1.0F, 14.0F, 62.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(276, 125).addBox(12.8F, -6.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(174, 213).addBox(13.5F, -33.0F, -1.5F, 1.0F, 62.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.0F, -32.0F, -17.0F));

        PartDefinition cube_r8 = left_door2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(276, 200).addBox(-0.1F, -0.1F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.1F, 27.9F, 0.5F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r9 = left_door2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(297, 211).addBox(-33.5F, -3.05F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.55F, -5.0F, 0.25F, 0.0F, 0.0F, -1.5708F));

        PartDefinition pulltoopen = left_door2.addOrReplaceChild("pulltoopen", CubeListBuilder.create().texOffs(0, 12).addBox(7.0083F, -2.3333F, -1.05F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(128, 233).addBox(0.0083F, -4.8333F, -0.05F, 8.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(85, 182).addBox(1.0083F, -4.3333F, -0.15F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.9917F, -10.1667F, -0.95F));

        PartDefinition phone = pulltoopen.addOrReplaceChild("phone", CubeListBuilder.create().texOffs(278, 38).addBox(-3.75F, -40.0F, -13.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F))
        .texOffs(278, 38).addBox(-3.75F, -39.8F, -13.5F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(278, 44).addBox(-3.75F, -37.75F, -13.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F))
        .texOffs(275, 26).addBox(-8.5F, -40.0F, -13.5F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(278, 35).addBox(-7.0F, -39.5F, -11.25F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
        .texOffs(269, 36).addBox(-5.5F, -42.0F, -13.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(269, 36).addBox(-8.5F, -42.0F, -13.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(260, 36).addBox(-5.5F, -42.0F, -13.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
        .texOffs(260, 36).addBox(-8.5F, -42.0F, -13.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
        .texOffs(260, 26).addBox(-9.0F, -41.5F, -14.5F, 6.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5083F, 37.6667F, 14.95F));

        PartDefinition right_door2 = box.addOrReplaceChild("right_door2", CubeListBuilder.create().texOffs(230, 200).addBox(-14.0F, -33.0F, -1.0F, 14.0F, 62.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(276, 200).addBox(-13.4F, -6.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(297, 211).addBox(-13.95F, -5.5F, -0.25F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(0, 0).addBox(-12.75F, -13.0F, -1.75F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(14.0F, -32.0F, -17.0F));

        PartDefinition cube_r10 = right_door2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, -3.0F, -2.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.5F, -10.0F, -1.5F, 0.0F, 3.1416F, 0.0F));
        return LayerDefinition.create(modelData, 512, 512);
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        DoorHandler door = tardis.door();

        if (!AITModClient.CONFIG.animateDoors) {
            this.left_door2.yRot = (door.isLeftOpen() || door.isOpen()) ? -4.75F : 0.0F;
            this.left_door2.yRot = (door.isRightOpen() || door.areBothOpen()) ? 4.75F : 0.0F;
        } else {
            float maxRot = 90f;
            this.right_door2.yRot = -(float) Math.toRadians(maxRot * door.getLeftRot());
            this.right_door2.yRot = (float) Math.toRadians(maxRot * door.getRightRot());
        }
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        box.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        matrices.pushPose();
        matrices.scale(0.63F, 0.63F, 0.63F);
        matrices.translate(0, -1.5f, 0);

        this.renderDoors(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, false);

        super.renderWithAnimations(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        matrices.popPose();
    }

    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices,
                                                           VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if (!falling.isLinked())
            return;

        matrices.pushPose();
        matrices.scale(0.63F, 0.63F, 0.63F);
        matrices.translate(0, -1.5f, 0);

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return box;
    }
}