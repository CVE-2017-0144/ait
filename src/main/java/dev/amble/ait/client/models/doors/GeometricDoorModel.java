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
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.tardis.handler.DoorHandler;

public class GeometricDoorModel extends DoorModel {

    private final ModelPart geometric;

    public GeometricDoorModel(ModelPart root) {
        this.geometric = root.getChild("geometric");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition geometric = modelPartData.addOrReplaceChild("geometric",
                CubeListBuilder.create().texOffs(23, 23)
                        .addBox(-16.0F, -44.0F, 7.0F, 32.0F, 44.0F, 1.0F, new CubeDeformation(0.05F)).texOffs(48, 24)
                        .addBox(-16.0F, -44.0F, 8.05F, 32.0F, 44.0F, 0.0F, new CubeDeformation(0.005F)).texOffs(113, 0)
                        .addBox(-8.0F, -38.0F, 6.9F, 16.0F, 36.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        geometric.addOrReplaceChild("door", CubeListBuilder.create().texOffs(98, 97).addBox(-8.0F, -40.0F, -7.0F, 16.0F, 40.0F, 2.0F,
                new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 3.1416F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        geometric.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity doorEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        DoorHandler door = tardis.door();

        this.geometric.getChild("door").z = door.isOpen() ? 5.05f : 1f;

        matrices.pushPose();
        matrices.scale(1F, 1F, 1F);
        matrices.translate(0, -1.5, 0);

        super.renderWithAnimations(tardis, doorEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return geometric;
    }

}
