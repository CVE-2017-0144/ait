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

public class DalekModDoorModel extends DoorModel {
    private final ModelPart dalekmod;
    public DalekModDoorModel(ModelPart root) {
        this.dalekmod = root.getChild("dalekmod");

    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition dalekmod = modelPartData.addOrReplaceChild("dalekmod", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Doors = dalekmod.addOrReplaceChild("Doors", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 2.0F));

        PartDefinition right_door = Doors.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(48, 141).addBox(0.0F, -17.0F, 0.0F, 8.0F, 32.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(144, 128).addBox(0.0F, -18.0F, 0.0F, 8.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -16.0F, -9.0F));

        PartDefinition left_door = Doors.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(68, 162).addBox(-8.0F, -17.0F, 0.0F, 8.0F, 32.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(88, 162).addBox(-8.0F, -18.0F, 0.0F, 8.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -16.0F, -9.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        dalekmod.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity doorEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        if (!AITModClient.CONFIG.animateDoors) {
            DoorHandler door = tardis.door();

            this.dalekmod.getChild("Doors").getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? -5.0f : 0.0F;
            this.dalekmod.getChild("Doors").getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen())
                    ? 5.0f
                    : 0.0F;
        } else {
            float maxRot = 80f;
        this.dalekmod.getChild("Doors").getChild("left_door").yRot = (float) Math.toRadians(maxRot*tardis.door().getLeftRot());
        this.dalekmod.getChild("Doors").getChild("right_door").yRot = (float) -Math.toRadians(maxRot*tardis.door().getRightRot());
        }

        matrices.pushPose();
        matrices.scale(0.955F, 0.955F, 0.955F);
        matrices.translate(0, -1.56, 0);
        matrices.mulPose(Axis.YN.rotationDegrees(180));

        super.renderWithAnimations(tardis, doorEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return dalekmod;
    }
}