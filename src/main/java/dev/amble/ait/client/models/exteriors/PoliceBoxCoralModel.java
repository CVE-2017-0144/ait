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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;

public class PoliceBoxCoralModel extends SimpleExteriorModel {
    private final ModelPart TARDIS;

    public PoliceBoxCoralModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.TARDIS = root.getChild("TARDIS");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition TARDIS = modelPartData.addOrReplaceChild("TARDIS", CubeListBuilder.create().texOffs(0, 0).addBox(-19.0F, -4.0F,
                -19.0F, 38.0F, 2.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 26.0F, 0.0F));

        PartDefinition Posts = TARDIS.addOrReplaceChild("Posts", CubeListBuilder.create().texOffs(46, 223).addBox(-18.0F, -66.0F,
                -18.0F, 4.0F, 62.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = Posts.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(29, 198).addBox(-18.0F, -66.0F,
                -18.0F, 4.0F, 62.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = Posts.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(210, 177).addBox(-18.0F, -66.0F,
                -18.0F, 4.0F, 62.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r3 = Posts.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(218, 41).addBox(-18.0F, -66.0F,
                -18.0F, 4.0F, 62.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition Doors = TARDIS.addOrReplaceChild("Doors", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_door = Doors.addOrReplaceChild("right_door",
                CubeListBuilder.create().texOffs(181, 177)
                        .addBox(0.5F, -29.5F, -0.5F, 13.0F, 55.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 198)
                        .addBox(0.5F, -29.5F, -1.0F, 14.0F, 55.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 10)
                        .addBox(9.5F, -9.5F, -1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-13.5F, -29.5F, -15.5F));

        PartDefinition left_door = Doors.addOrReplaceChild("left_door",
                CubeListBuilder.create().texOffs(189, 41)
                        .addBox(-13.5F, -29.5F, -0.5F, 13.0F, 55.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-12.5F, -10.5F, -1.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(13.5F, -29.5F, -15.5F));

        PartDefinition Walls = TARDIS.addOrReplaceChild("Walls",
                CubeListBuilder.create().texOffs(129, 15)
                        .addBox(-16.0F, -60.0F, -14.0F, 1.0F, 56.0F, 28.0F, new CubeDeformation(0.0F)).texOffs(59, 142)
                        .addBox(-16.5F, -60.0F, -14.0F, 0.0F, 56.0F, 28.0F, new CubeDeformation(0.0F)).texOffs(63, 227)
                        .addBox(-14.0F, -60.0F, -16.0F, 1.0F, 56.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(116, 170)
                        .addBox(13.0F, -60.0F, -16.0F, 1.0F, 56.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(115, 0)
                        .addBox(-13.0F, -60.0F, -16.0F, 26.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(59, 113)
                        .addBox(13.0F, -60.0F, -16.5F, 1.0F, 56.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(115, 3)
                        .addBox(-13.0F, -60.0F, -16.5F, 26.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(62, 113)
                        .addBox(-14.0F, -60.0F, -16.5F, 1.0F, 56.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Wall_r1 = Walls.addOrReplaceChild("Wall_r1",
                CubeListBuilder.create().texOffs(160, 72)
                        .addBox(-16.5F, -60.0F, -14.0F, 0.0F, 56.0F, 28.0F, new CubeDeformation(0.0F)).texOffs(93, 85)
                        .addBox(-16.0F, -60.0F, -14.0F, 1.0F, 56.0F, 28.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition Wall_r2 = Walls.addOrReplaceChild("Wall_r2",
                CubeListBuilder.create().texOffs(124, 142)
                        .addBox(-16.75F, -60.0F, -14.0F, 0.0F, 56.0F, 28.0F, new CubeDeformation(0.0F)).texOffs(0, 113)
                        .addBox(-16.0F, -60.0F, -14.0F, 1.0F, 56.0F, 28.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition PCB = TARDIS.addOrReplaceChild("PCB", CubeListBuilder.create().texOffs(181, 167).addBox(-17.0F, -64.0F, -19.0F,
                34.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r4 = PCB.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(153, 157).addBox(-17.0F, -61.0F,
                -19.0F, 34.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r5 = PCB.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(160, 21).addBox(-17.0F, -61.0F,
                -19.0F, 34.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r6 = PCB.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(160, 31).addBox(-17.0F, -61.0F,
                -19.0F, 34.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition Roof = TARDIS.addOrReplaceChild("Roof",
                CubeListBuilder.create().texOffs(0, 43)
                        .addBox(-16.0F, -68.0F, -16.0F, 32.0F, 4.0F, 32.0F, new CubeDeformation(0.0F)).texOffs(0, 43)
                        .addBox(-17.0F, -67.5F, -17.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.05F)).texOffs(22, 7)
                        .addBox(-17.0F, -67.5F, 14.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.05F)).texOffs(0, 30)
                        .addBox(14.0F, -67.5F, -17.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.05F)).texOffs(17, 26)
                        .addBox(14.0F, -67.5F, 14.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.05F)).texOffs(0, 80)
                        .addBox(-15.0F, -71.0F, -15.0F, 30.0F, 3.0F, 30.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-3.0F, -73.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-8, 18)
                        .addBox(-4.0F, -77.0F, -4.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(-8, 18)
                        .addBox(-4.0F, -74.0F, -4.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 10)
                        .addBox(-3.0F, -79.5F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r7 = Roof.addOrReplaceChild("cube_r7",
                CubeListBuilder.create().texOffs(14, 35).addBox(0.0F, -72.75F, -3.5F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -6.25F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r8 = Roof.addOrReplaceChild("cube_r8",
                CubeListBuilder.create().texOffs(14, 35).addBox(0.0F, -72.75F, -3.5F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 57).addBox(-2.0F, -74.25F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -6.25F, 0.0F, 0.0F, 0.7854F, 0.0F));
        return LayerDefinition.create(modelData, 512, 512);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        TARDIS.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return TARDIS;
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
        if (falling.tardis().isEmpty())
            return;

        matrices.pushPose();
        matrices.scale(0.63F, 0.63F, 0.63F);
        matrices.translate(0, -1.5f, 0);

        DoorHandler door = falling.tardis().get().door();

        if (!AITModClient.CONFIG.animateDoors) {
            this.TARDIS.getChild("Doors").getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? -5F : 0.0F;
            this.TARDIS.getChild("Doors").getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen())
                    ? 5F
                    : 0.0F;
        } else {
            float maxRot = 90f;
            this.TARDIS.getChild("Doors").getChild("left_door").yRot = (float) Math.toRadians(maxRot*door.getLeftRot());
            this.TARDIS.getChild("Doors").getChild("right_door").yRot = -(float) Math.toRadians(maxRot*door.getRightRot());
        }

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        DoorHandler door = tardis.door();

        if (!AITModClient.CONFIG.animateDoors) {

            this.TARDIS.getChild("Doors").getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? -5F : 0.0F;
            this.TARDIS.getChild("Doors").getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen())
                    ? 5F
                    : 0.0F;
        } else {
            float maxRot = 90f;
            this.TARDIS.getChild("Doors").getChild("left_door").yRot = (float) Math.toRadians(maxRot*door.getLeftRot());
            this.TARDIS.getChild("Doors").getChild("right_door").yRot = -(float) Math.toRadians(maxRot*door.getRightRot());
        }

        if (isBOTI) {
            matrices.pushPose();
            matrices.scale(0.63F, 0.63F, 0.63F);
            matrices.translate(0, 0.125f, -0.01);
            this.TARDIS.getChild("Doors").render(matrices, vertices, light, overlay, FastColor.ARGB32.colorFromFloat(pAlpha, red, green, blue));
            matrices.popPose();
        }
    }
}
