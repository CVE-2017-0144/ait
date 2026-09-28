package dev.amble.ait.client.models.doors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;

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

public class PlinthDoorModel extends DoorModel {

    private final ModelPart plinth;

    public PlinthDoorModel(ModelPart root) {
        this.plinth = root.getChild("plinth");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition plinth = modelPartData.addOrReplaceChild("plinth", CubeListBuilder.create(),
                PartPose.offset(-20.0F, 27.0F, 0.0F));

        plinth.addOrReplaceChild("door", CubeListBuilder.create().texOffs(72, 61).addBox(-12.0F, -42.0F, 0.0F, 12.0F, 42.0F, 2.0F,
                new CubeDeformation(0.0F)), PartPose.offset(26.0F, -3.0F, -8.0F));

        plinth.addOrReplaceChild("frame",
                CubeListBuilder.create().texOffs(56, 0).addBox(11.0F, -48.0F, -9.0F, 18.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(28, 79).addBox(26.0F, -45.0F, -8.0F, 2.0F, 42.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(36, 79)
                        .addBox(12.0F, -45.0F, -8.0F, 2.0F, 42.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        plinth.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        matrices.pushPose();
        matrices.translate(0, -1.5f, 0);
        matrices.mulPose(Axis.YN.rotationDegrees(180f));

        if (!AITModClient.CONFIG.animateDoors) {
            plinth.getChild("door").yRot = tardis.door().isOpen() ? -1.75f : 0f;
        } else {
            float maxRot = 90f;
            plinth.getChild("door").yRot = -(float) Math.toRadians(maxRot*tardis.door().getLeftRot());
        }
        super.renderWithAnimations(tardis, linkableBlockEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);

        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return plinth;
    }

}
