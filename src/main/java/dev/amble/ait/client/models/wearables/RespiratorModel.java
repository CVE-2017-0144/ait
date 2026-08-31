package dev.amble.ait.client.models.wearables;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class RespiratorModel extends EntityModel {

    public final ModelPart mask;

    public RespiratorModel(ModelPart root) {
        this.mask = root.getChild("mask");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition partData = modelData.getRoot();

        PartDefinition mask = partData.addOrReplaceChild("mask", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = mask.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(23, 22).addBox(5.5F, -1.5F, -1.5F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0, 0.0F, 0.7418F, 0.829F, 0.0F));

        PartDefinition cube_r2 = mask.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(25, 0).addBox(5.5F, -1.5F, -2.5F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0, 0.0F, 2.3998F, 0.829F, 3.1416F));

        PartDefinition cube_r3 = mask.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(14, 17).addBox(4.5F, -2.0F, -2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.02F)).texOffs(0, 17)
                        .addBox(2.5F, -8.5F, -4.5F, 2.0F, 9.0F, 9.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        mask.render(matrices, vertices, light, overlay, color);
    }
}
