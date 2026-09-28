package dev.amble.ait.client.models.exteriors;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;

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
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;

public class StallionExteriorModel extends SimpleExteriorModel {
    private final ModelPart body;
    public StallionExteriorModel(ModelPart root) {
        this.body = root.getChild("body");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition door = body.addOrReplaceChild("door", CubeListBuilder.create(), PartPose.offset(7.5F, -20.0F, -9.0F));

        PartDefinition cube_r1 = door.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(17, 122).addBox(-9.0F, -38.0F, -8.0F, 0.0F, 37.0F, 8.0F, new CubeDeformation(-0.001F))
        .texOffs(117, 0).addBox(-9.5F, -38.0F, -8.0F, 1.0F, 37.0F, 8.0F, new CubeDeformation(-0.001F))
        .texOffs(14, 26).addBox(-9.5F, -24.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(5, 16).addBox(-10.25F, -23.5F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(0, 16).addBox(-8.75F, -23.5F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, 20.0F, 9.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition door_two = door.addOrReplaceChild("door_two", CubeListBuilder.create(), PartPose.offset(-7.5F, -3.0F, 0.5F));

        PartDefinition cube_r2 = door_two.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 122).addBox(-9.0F, -38.0F, 0.0F, 0.0F, 37.0F, 8.0F, new CubeDeformation(-0.001F))
        .texOffs(109, 92).addBox(-9.5F, -38.0F, 0.0F, 1.0F, 37.0F, 8.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, 23.0F, 8.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone = body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 26).addBox(-10.0F, -1.0F, -10.0F, 20.0F, 1.0F, 20.0F, new CubeDeformation(0.0F))
        .texOffs(0, 0).addBox(-10.0F, -43.0F, -10.0F, 20.0F, 5.0F, 20.0F, new CubeDeformation(0.0F))
        .texOffs(0, 48).addBox(-9.0F, -44.0F, -9.0F, 18.0F, 1.0F, 18.0F, new CubeDeformation(0.0F))
        .texOffs(81, 0).addBox(-9.5F, -38.0F, -8.0F, 1.0F, 37.0F, 16.0F, new CubeDeformation(-0.001F))
        .texOffs(35, 90).addBox(-9.0F, -38.0F, -8.0F, 0.0F, 37.0F, 16.0F, new CubeDeformation(-0.001F))
        .texOffs(136, 0).addBox(8.0F, -38.0F, -10.0F, 2.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(128, 92).addBox(8.0F, -38.0F, -10.0F, 2.0F, 37.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(0, 68).addBox(-9.5F, -38.0F, -8.0F, 1.0F, 37.0F, 16.0F, new CubeDeformation(-0.001F))
        .texOffs(76, 90).addBox(-9.0F, -38.0F, -8.0F, 0.0F, 37.0F, 16.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(128, 132).addBox(8.0F, -38.0F, -10.0F, 2.0F, 37.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(57, 52).addBox(-9.5F, -38.0F, -8.0F, 1.0F, 37.0F, 16.0F, new CubeDeformation(-0.001F))
        .texOffs(100, 38).addBox(-9.0F, -38.0F, -8.0F, 0.0F, 37.0F, 16.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r5 = bone.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(133, 46).addBox(8.0F, -38.0F, -10.0F, 2.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition top = body.addOrReplaceChild("top", CubeListBuilder.create().texOffs(9, 26).addBox(-1.0F, -52.0F, 0.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, -0.5F));

        PartDefinition antenna = top.addOrReplaceChild("antenna", CubeListBuilder.create(), PartPose.offset(-0.5F, -52.0F, 0.5F));

        PartDefinition cube_r6 = antenna.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(93, 92).addBox(-10.4F, -4.3F, 0.5F, 10.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, -0.6545F));

        PartDefinition antenna2 = top.addOrReplaceChild("antenna2", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r7 = antenna2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(35, 80).addBox(-10.4F, -4.3F, 0.5F, 10.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, -0.6545F));

        PartDefinition antenna3 = top.addOrReplaceChild("antenna3", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r8 = antenna3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(76, 60).addBox(-10.4F, -4.3F, 0.5F, 10.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, -0.6545F));

        PartDefinition antenna4 = top.addOrReplaceChild("antenna4", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r9 = antenna4.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(76, 54).addBox(-10.4F, -4.3F, 0.5F, 10.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, -0.6545F));

        PartDefinition antenna5 = top.addOrReplaceChild("antenna5", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r10 = antenna5.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(19, 74).addBox(-10.4F, -4.3F, 0.5F, 10.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, -0.6545F));

        PartDefinition antenna6 = top.addOrReplaceChild("antenna6", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r11 = antenna6.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(19, 68).addBox(-10.4F, -4.3F, 0.5F, 10.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, -0.6545F));

        PartDefinition antenna7 = top.addOrReplaceChild("antenna7", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r12 = antenna7.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(61, 6).addBox(-10.4F, -4.3F, 0.5F, 10.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, -0.6545F));

        PartDefinition antenna8 = top.addOrReplaceChild("antenna8", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r13 = antenna8.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(61, 0).addBox(-10.4F, -4.3F, 0.5F, 10.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, -0.6545F));

        PartDefinition controls = body.addOrReplaceChild("controls", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -30.0F, 7.0F, 7.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(0, 26).addBox(-1.5F, -28.0F, 5.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        body.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return body;
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        if (!AITModClient.CONFIG.animateDoors) {
            body.getChild("door").yRot = tardis.door().isOpen() ? -1.35f : 0f;
            body.getChild("door").getChild("door_two").yRot = tardis.door().isOpen() ? 2.65f : 0f;
        } else {
            body.getChild("door").yRot = -(float) Math.toRadians(87f * tardis.door().getLeftRot());
            body.getChild("door").getChild("door_two").yRot = (float) Math.toRadians(150f * tardis.door().getLeftRot());
        }

        if (isBOTI) {
            matrices.pushPose();
            matrices.scale(0.95f, 0.95f, 0.95f);
            matrices.translate(10, 10f, -1);
            body.getChild("door").render(matrices, vertices, light, overlay, FastColor.ARGB32.colorFromFloat(pAlpha, red, green, blue));
            matrices.popPose();
        }

    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.pushPose();
        matrices.scale(0.95f, 0.95f, 0.95f);
        matrices.translate(0, -1.5f, 0);

        this.renderDoors(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, alpha, false);

        super.renderWithAnimations(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }

    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices,
                                                           VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.pushPose();
        matrices.scale(0.95f, 0.95f, 0.95f);
        matrices.translate(0, -1.5f, 0);

        if (!AITModClient.CONFIG.animateDoors) {
            body.getChild("door").yRot = falling.tardis().get().door().isOpen() ? -1.35f : 0f;
            body.getChild("door").getChild("door_two").yRot = falling.tardis().get().door().isOpen() ? 2.65f : 0f;
        } else {
            float maxLeftRot = 87f;
            float maxRightRot = 150f;
            body.getChild("door").yRot = -(float) Math.toRadians(maxLeftRot * falling.tardis().get().door().getLeftRot());
            body.getChild("door").getChild("door_two").yRot = (float) Math.toRadians(maxRightRot * falling.tardis().get().door().getLeftRot());
        }

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }
}