package dev.amble.ait.client.models.doors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
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

public class CapsuleDoorModel extends DoorModel {
    private final ModelPart body;

    public CapsuleDoorModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.body = root.getChild("body");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 3.0F, -15.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition top = body.addOrReplaceChild("top",
                CubeListBuilder.create().texOffs(87, 15)
                        .addBox(-12.0F, -36.1F, -12.0F, 24.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(15, 40)
                        .addBox(-12.0F, -33.89F, -12.0F, 24.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(61, 114)
                        .addBox(-4.9706F, -36.0F, -12.0F, 9.9411F, 2.0F, 8.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.0F, 21.0F, 0.0F));

        top.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(125, 35).addBox(-4.9706F, -2.0F, -12.0F, 3.0F, 2.0F,
                24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -34.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        top.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(20, 101).addBox(-4.9706F, -2.0F, -12.0F, 9.9411F, 2.0F,
                4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -34.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        top.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(93, 85).addBox(-4.9706F, -2.0F, -12.0F, 9.9411F, 2.0F,
                4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -34.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition middle = body.addOrReplaceChild("middle", CubeListBuilder.create(),
                PartPose.offset(0.0F, 21.0F, 0.0F));

        middle.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(48, 135).addBox(-2.2365F, -34.0F, 9.5F, 8.0F, 32.0F,
                2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

        middle.addOrReplaceChild("octagon_r5", CubeListBuilder.create().texOffs(142, 128).addBox(-5.7635F, -34.0F, 9.5F, 8.0F, 32.0F,
                2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.7854F, 3.1416F));

        middle.addOrReplaceChild("back", CubeListBuilder.create().texOffs(146, 0).addBox(-12.0F, -34.0F, -4.0F, 24.0F, 32.0F, 2.0F,
                new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bottom = body.addOrReplaceChild("bottom",
                CubeListBuilder.create().texOffs(135, 85)
                        .addBox(-5.0294F, -2.0F, -12.0F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.001F)).texOffs(14, 64)
                        .addBox(-12.0F, 0.01F, -12.0F, 24.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(14, 15)
                        .addBox(-12.0F, -2.1F, -12.0F, 24.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 21.0F, 0.0F));

        bottom.addOrReplaceChild("octagon_r6", CubeListBuilder.create().texOffs(125, 68).addBox(-4.9706F, -2.0F, -12.0F, 3.0F, 2.0F,
                24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        bottom.addOrReplaceChild("octagon_r7", CubeListBuilder.create().texOffs(138, 55).addBox(-4.9706F, -2.0F, -12.0F, 9.9411F,
                2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        bottom.addOrReplaceChild("octagon_r8", CubeListBuilder.create().texOffs(20, 128).addBox(-4.9706F, -2.0F, -12.0F, 9.9411F,
                2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition doors = body.addOrReplaceChild("doors", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -2.0F, -17.0F, 0.0F, 3.1416F, 0.0F));

        doors.addOrReplaceChild("door_right", CubeListBuilder.create().texOffs(161, 95).addBox(0.4706F, -11.0F, -0.5F, 6.0F, 32.0F,
                1.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.5F, 0.0F, -8.5F));

        doors.addOrReplaceChild("door_left", CubeListBuilder.create().texOffs(162, 162).addBox(-6.5294F, -11.0F, -0.5F, 6.0F, 32.0F,
                1.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 0.0F, -8.5F));

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
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        matrices.pushPose();

        matrices.translate(0, -1.5f, 0);
        matrices.mulPose(Axis.YP.rotationDegrees(180f));

        DoorHandler door = tardis.door();

        if (!AITModClient.CONFIG.animateDoors) {
            this.body.getChild("doors").getChild("door_left").yRot = (door.isLeftOpen() || door.isOpen()) ? -5F : 0.0F;
            this.body.getChild("doors").getChild("door_right").yRot = (door.isRightOpen() || door.areBothOpen())
                    ? 5F
                    : 0.0F;
        } else {
            float maxRot = 90f;
            this.body.getChild("doors").getChild("door_left").yRot = (float) Math.toRadians(maxRot*door.getLeftRot());
            this.body.getChild("doors").getChild("door_right").yRot = (float) -Math.toRadians(maxRot*door.getRightRot());
        }

        this.root().getChild("middle").getChild("back").visible = !AITModClient.CONFIG.enableTardisBOTI;

        super.renderWithAnimations(tardis, linkableBlockEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);

        matrices.popPose();
    }
}
