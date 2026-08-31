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
import net.minecraft.world.entity.Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.handler.DoorHandler;

public class PresentExteriorModel extends SimpleExteriorModel {
    private final ModelPart present;
    public PresentExteriorModel(ModelPart root) {
        this.present = root.getChild("present");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition present = modelPartData.addOrReplaceChild("present", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -32.0F, -10.0F, 20.0F, 32.0F, 20.0F, new CubeDeformation(0.0F))
        .texOffs(0, 52).addBox(-10.0F, -38.6F, -10.0F, 20.0F, 6.0F, 20.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_door = present.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(64, 78).addBox(-10.0F, -16.0F, 0.0F, 10.0F, 32.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(10.0F, -16.0F, -10.001F));

        PartDefinition left_door = present.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(44, 78).addBox(0.0F, -16.0F, 0.0F, 10.0F, 32.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(-10.0F, -16.0F, -10.001F));

        PartDefinition bow = present.addOrReplaceChild("bow", CubeListBuilder.create().texOffs(0, 78).addBox(-11.0F, -12.0F, 0.0F, 22.0F, 13.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -39.6F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        present.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
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
            DoorHandler door = falling.tardis().get().door();

            this.present.getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? 8F : 0.0F;
            this.present.getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen())
                    ? -8F
                    : 0.0F;
        } else {
            float maxRot = 90f;
            this.present.getChild("left_door").yRot = (float) Math.toRadians(falling.tardis().get().door().getLeftRot() * maxRot);
            this.present.getChild("right_door").yRot = -(float) Math.toRadians(falling.tardis().get().door().getRightRot() * maxRot);
        }

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return present;
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        if (!AITModClient.CONFIG.animateDoors) {
            DoorHandler door = tardis.door();

            this.present.getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? 8F : 0.0F;
            this.present.getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen())
                    ? -8F
                    : 0.0F;
        } else {
            float maxRot = 90f;
            this.present.getChild("left_door").yRot = (float) Math.toRadians(tardis.door().getLeftRot() * maxRot);
            this.present.getChild("right_door").yRot = -(float) Math.toRadians(tardis.door().getRightRot() * maxRot);
        }

        if (isBOTI) {
            matrices.pushPose();
            matrices.translate(0, -1.5f, -3);
            this.present.getChild("left_door").render(matrices, vertices, light, overlay, red, green, blue, pAlpha);
            this.present.getChild("right_door").render(matrices, vertices, light, overlay, red, green, blue, pAlpha);
            matrices.popPose();
        }
    }
}