package dev.amble.ait.client.models.doors;

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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.tardis.handler.DoorHandler;

// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class PresentDoorModel extends DoorModel {
    private final ModelPart present;
    public PresentDoorModel(ModelPart root) {
        this.present = root.getChild("present");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition present = modelPartData.addOrReplaceChild("present", CubeListBuilder.create().texOffs(20, 72).addBox(-10.0F, -38.6F, -8.0F, 20.0F, 6.0F, 0.0F, new CubeDeformation(0.5F))
        .texOffs(53, 22).addBox(10.0F, -39.0F, -8.001F, 1.0F, 39.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(53, 22).addBox(-11.0F, -39.0F, -8.001F, 1.0F, 39.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_door = present.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(64, 78).addBox(-10.0F, -16.0F, 0.0F, 10.0F, 32.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(10.0F, -16.0F, -8.001F));

        PartDefinition left_door = present.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(44, 78).addBox(0.0F, -16.0F, 0.0F, 10.0F, 32.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(-10.0F, -16.0F, -8.001F));

        PartDefinition bow = present.addOrReplaceChild("bow", CubeListBuilder.create().texOffs(0, 78).addBox(-11.0F, -12.0F, 0.0F, 22.0F, 13.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -39.6F, -7.9F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        present.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        DoorHandler door = tardis.door();

        if (!AITModClient.CONFIG.animateDoors) {
            this.present.getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? 8F : 0.0F;
            this.present.getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen())
                    ? -8F
                    : 0.0F;
        } else {
            float maxRot = 90f;
            this.present.getChild("left_door").yRot = (float) Math.toRadians(maxRot*door.getLeftRot());
            this.present.getChild("right_door").yRot = (float) -Math.toRadians(maxRot*door.getRightRot());
        }

        matrices.pushPose();
        matrices.translate(0, -1.5, 0);
        matrices.mulPose(Axis.YN.rotationDegrees(180));

        super.renderWithAnimations(tardis, linkableBlockEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return present;
    }
}