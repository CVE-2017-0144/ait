package dev.amble.ait.client.models.exteriors;

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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.handler.DoorHandler;

public class CapsuleExteriorModel extends SimpleExteriorModel {
    private final ModelPart body;

    public CapsuleExteriorModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.body = root.getChild("body");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create(),
                PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition top = body.addOrReplaceChild("top",
                CubeListBuilder.create().texOffs(45, 92)
                        .addBox(-4.9706F, -42.0F, -12.0F, 9.9411F, 8.0F, 24.0F, new CubeDeformation(0.001F)).texOffs(73, 1)
                        .addBox(-12.0F, -42.1F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)).texOffs(0, 25)
                        .addBox(-12.0F, -33.89F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition octagon_r1 = top
                .addOrReplaceChild(
                        "octagon_r1", CubeListBuilder.create().texOffs(73, 26).addBox(-4.9706F, -42.0F, -12.0F, 9.9411F,
                                8.0F, 24.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition octagon_r2 = top
                .addOrReplaceChild(
                        "octagon_r2", CubeListBuilder.create().texOffs(73, 59).addBox(-4.9706F, -42.0F, -12.0F, 9.9411F,
                                8.0F, 24.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition octagon_r3 = top
                .addOrReplaceChild(
                        "octagon_r3", CubeListBuilder.create().texOffs(0, 75).addBox(-22.9706F, -42.0F, -12.0F, 9.9411F,
                                8.0F, 24.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(12.7279F, 0.0F, -12.7279F, 0.0F, 0.7854F, 0.0F));

        PartDefinition middle = body.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(26, 135).addBox(-4.7635F, -34.0F,
                9.5F, 9.5269F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition octagon_r4 = middle
                .addOrReplaceChild(
                        "octagon_r4", CubeListBuilder.create().texOffs(120, 128).addBox(-4.7635F, -34.0F, 9.5F, 9.5269F,
                                32.0F, 2.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition octagon_r5 = middle
                .addOrReplaceChild(
                        "octagon_r5", CubeListBuilder.create().texOffs(50, 135).addBox(-2.2365F, -34.0F, 9.5F, 7.0F, 32.0F,
                                2.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

        PartDefinition octagon_r6 = middle
                .addOrReplaceChild(
                        "octagon_r6", CubeListBuilder.create().texOffs(95, 128).addBox(-4.7635F, -34.0F, 9.5F, 9.5269F,
                                32.0F, 2.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition octagon_r7 = middle
                .addOrReplaceChild(
                        "octagon_r7", CubeListBuilder.create().texOffs(70, 128).addBox(-4.7635F, -34.0F, 9.5F, 9.5269F,
                                32.0F, 2.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition octagon_r8 = middle
                .addOrReplaceChild(
                        "octagon_r8", CubeListBuilder.create().texOffs(144, 128).addBox(-4.7635F, -34.0F, 9.5F, 7.0F, 32.0F,
                                2.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.7854F, 3.1416F));

        PartDefinition octagon_r9 = middle
                .addOrReplaceChild(
                        "octagon_r9", CubeListBuilder.create().texOffs(1, 135).addBox(-4.7635F, -34.0F, 9.5F, 9.5269F,
                                32.0F, 2.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bottom = body.addOrReplaceChild("bottom",
                CubeListBuilder.create().texOffs(118, 68)
                        .addBox(-4.9706F, -2.0F, -12.0F, 9.9411F, 2.0F, 24.0F, new CubeDeformation(0.001F)).texOffs(0, 50)
                        .addBox(-12.0F, 0.01F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-12.0F, -2.1F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition octagon_r10 = bottom
                .addOrReplaceChild(
                        "octagon_r10", CubeListBuilder.create().texOffs(90, 101).addBox(-4.9706F, -2.0F, -12.0F, 9.9411F,
                                2.0F, 24.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition octagon_r11 = bottom
                .addOrReplaceChild(
                        "octagon_r11", CubeListBuilder.create().texOffs(118, 35).addBox(-4.9706F, -2.0F, -12.0F, 9.9411F,
                                2.0F, 24.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition octagon_r12 = bottom
                .addOrReplaceChild(
                        "octagon_r12", CubeListBuilder.create().texOffs(0, 108).addBox(-4.9706F, -2.0F, -12.0F, 9.9411F,
                                2.0F, 24.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition doors = body.addOrReplaceChild("doors", CubeListBuilder.create(),
                PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition right_door = doors.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(162, 162).addBox(0.4706F,
                -11.0F, -0.5F, 6.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.5F, 0.0F, -8.5F));

        PartDefinition left_door = doors.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(161, 95).addBox(-6.5294F,
                -11.0F, -0.5F, 6.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 0.0F, -8.5F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        matrices.pushPose();
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
        matrices.translate(0, -1.5f, 0);

        if (!AITModClient.CONFIG.animateDoors) {
            DoorHandler handler = falling.tardis().get().door();

            this.body.getChild("doors").getChild("left_door").yRot = (handler.isLeftOpen() || handler.isOpen()) ? -5F : 0.0F;
            this.body.getChild("doors").getChild("right_door").yRot = (handler.isRightOpen() || handler.areBothOpen())
                    ? 5F
                    : 0.0F;
        } else {
            float maxRot = 90f;
            this.body.getChild("doors").getChild("left_door").yRot = (float) Math.toRadians(maxRot * falling.tardis().get().door().getLeftRot());
            this.body.getChild("doors").getChild("right_door").yRot = -(float) Math.toRadians(maxRot * falling.tardis().get().door().getRightRot());
        }

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        matrices.popPose();
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        if (!AITModClient.CONFIG.animateDoors) {
            DoorHandler handler = tardis.door();

            this.body.getChild("doors").getChild("left_door").yRot = (handler.isLeftOpen() || handler.isOpen()) ? -5F : 0.0F;
            this.body.getChild("doors").getChild("right_door").yRot = (handler.isRightOpen() || handler.areBothOpen())
                    ? 5F
                    : 0.0F;
        } else {
            float maxRot = 90f;
            this.body.getChild("doors").getChild("left_door").yRot = (float) Math.toRadians(maxRot * tardis.door().getLeftRot());
            this.body.getChild("doors").getChild("right_door").yRot = -(float) Math.toRadians(maxRot * tardis.door().getRightRot());
        }

        if (isBOTI) {
            matrices.pushPose();
            matrices.translate(0, -1.32f, 0);
            this.body.getChild("doors").render(matrices, vertices, light, overlay, FastColor.ARGB32.colorFromFloat(pAlpha, red, green, blue));
            matrices.popPose();
        }
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        this.body.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return this.body;
    }
}
