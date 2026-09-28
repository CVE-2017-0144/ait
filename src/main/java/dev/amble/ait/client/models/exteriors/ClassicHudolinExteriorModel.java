package dev.amble.ait.client.models.exteriors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.AITModClient;
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
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;

public class ClassicHudolinExteriorModel extends SimpleExteriorModel {
    private final ModelPart classic;

    public ClassicHudolinExteriorModel(ModelPart root) {
        this.classic = root.getChild("classic");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition classic = modelPartData.addOrReplaceChild("classic", CubeListBuilder.create().texOffs(0, 0).addBox(-18.0F,
                -2.0F, -18.0F, 36.0F, 3.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition Posts_Hud = classic.addOrReplaceChild("Posts_Hud", CubeListBuilder.create().texOffs(29, 189).addBox(-17.0F,
                -62.0F, -17.0F, 4.0F, 60.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = Posts_Hud.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(169, 153).addBox(-18.0F,
                -64.0F, -18.0F, 4.0F, 60.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, 2.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = Posts_Hud.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(177, 21).addBox(-18.0F,
                -64.0F, -18.0F, 4.0F, 60.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 2.0F, -1.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r3 = Posts_Hud.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(186, 153).addBox(-17.0F,
                -64.0F, -18.0F, 4.0F, 60.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, 2.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition Doors = classic.addOrReplaceChild("Doors", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_door = Doors.addOrReplaceChild("right_door",
                CubeListBuilder.create().texOffs(0, 189)
                        .addBox(-0.5F, -25.5F, -0.5F, 13.0F, 53.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(203, 76)
                        .addBox(-0.5F, -25.5F, -1.0F, 13.0F, 53.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-12.5F, -29.5F, -14.5F));

        PartDefinition left_door = Doors.addOrReplaceChild("left_door",
                CubeListBuilder.create().texOffs(194, 21)
                        .addBox(-12.5F, -25.5F, -0.5F, 13.0F, 53.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(30, 17)
                        .addBox(-11.5F, -4.5F, -1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(12.5F, -29.5F, -14.5F));

        PartDefinition Walls = classic.addOrReplaceChild("Walls",
                CubeListBuilder.create().texOffs(120, 13)
                        .addBox(-15.0F, -58.0F, -13.0F, 1.0F, 56.0F, 26.0F, new CubeDeformation(0.0F)).texOffs(55, 138)
                        .addBox(-15.5F, -57.0F, -13.0F, 0.0F, 55.0F, 26.0F, new CubeDeformation(0.0F)).texOffs(29, 106)
                        .addBox(-13.0F, -58.0F, -15.0F, 26.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(109, 1)
                        .addBox(-13.0F, -57.0F, -15.5F, 26.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Wall_r1 = Walls.addOrReplaceChild("Wall_r1",
                CubeListBuilder.create().texOffs(150, 71)
                        .addBox(-15.5F, -57.0F, -13.0F, 0.0F, 55.0F, 26.0F, new CubeDeformation(0.0F)).texOffs(87, 80)
                        .addBox(-15.0F, -58.0F, -13.0F, 1.0F, 56.0F, 26.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition Wall_r2 = Walls.addOrReplaceChild("Wall_r2",
                CubeListBuilder.create().texOffs(116, 138)
                        .addBox(-15.75F, -57.0F, -13.0F, 0.0F, 55.0F, 26.0F, new CubeDeformation(0.0F)).texOffs(0, 106)
                        .addBox(-15.0F, -58.0F, -13.0F, 1.0F, 56.0F, 26.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition PCB_Hud = classic.addOrReplaceChild("PCB_Hud", CubeListBuilder.create().texOffs(237, 200).addBox(-14.0F,
                -61.0F, -19.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r4 = PCB_Hud.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(237, 173).addBox(-14.0F,
                -58.0F, -19.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r5 = PCB_Hud.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(237, 182).addBox(-14.0F,
                -58.0F, -19.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r6 = PCB_Hud.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(237, 191).addBox(-14.0F,
                -58.0F, -19.0F, 28.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition Roof_Hud = classic.addOrReplaceChild("Roof_Hud",
                CubeListBuilder.create().texOffs(0, 39)
                        .addBox(-15.0F, -64.5F, -15.0F, 30.0F, 5.0F, 30.0F, new CubeDeformation(0.0F)).texOffs(230, 18)
                        .addBox(-14.0F, -55.0F, -14.0F, 28.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)).texOffs(17, 25)
                        .addBox(-16.5F, -63.0F, -16.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.05F)).texOffs(17, 19)
                        .addBox(-16.5F, -63.0F, 13.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.05F)).texOffs(19, 11)
                        .addBox(13.5F, -63.0F, -16.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.05F)).texOffs(19, 0)
                        .addBox(13.5F, -63.0F, 13.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.05F)).texOffs(0, 75)
                        .addBox(-14.0F, -66.0F, -14.0F, 28.0F, 2.0F, 28.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-3.0F, -68.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 11)
                        .addBox(-3.0F, -74.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r7 = Roof_Hud.addOrReplaceChild("cube_r7",
                CubeListBuilder.create().texOffs(0, 19).addBox(-2.0F, -72.75F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 45).addBox(-3.5F, -70.75F, 0.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -2.25F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r8 = Roof_Hud.addOrReplaceChild("cube_r8",
                CubeListBuilder.create().texOffs(0, 39).addBox(-3.5F, -71.75F, 0.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -1.25F, 0.0F, 0.0F, -0.7854F, 0.0F));
        return LayerDefinition.create(modelData, 512, 512);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        classic.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return classic;
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        matrices.pushPose();
        matrices.scale(0.64F, 0.64F, 0.64F);
        matrices.translate(0, -1.5f, 0);

        this.renderDoors(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, false);

        super.renderWithAnimations(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        matrices.popPose();
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        if (!AITModClient.CONFIG.animateDoors) {
            DoorHandler door = tardis.door();

            this.classic.getChild("Doors").getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? -5F : 0.0F;
            this.classic.getChild("Doors").getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen())
                    ? 5F
                    : 0.0F;
        } else {
            float maxRot = 90f;

            DoorHandler door = tardis.door();
            this.classic.getChild("Doors").getChild("left_door").yRot = (float) Math.toRadians(maxRot * door.getLeftRot());
            this.classic.getChild("Doors").getChild("right_door").yRot = -(float) Math.toRadians(maxRot * door.getRightRot());
        }

        if (isBOTI) {
            matrices.pushPose();
            matrices.scale(0.64F, 0.64F, 0.64F);
            matrices.translate(0, -0.06f, 0);
            this.classic.getChild("Doors").render(matrices, vertices, light, overlay, FastColor.ARGB32.colorFromFloat(pAlpha, red, green, blue));
            matrices.popPose();
        }
    }

    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices,
                                                           VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.pushPose();
        matrices.scale(0.64F, 0.64F, 0.64F);
        matrices.translate(0, -1.5f, 0);

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        matrices.popPose();
    }
}
