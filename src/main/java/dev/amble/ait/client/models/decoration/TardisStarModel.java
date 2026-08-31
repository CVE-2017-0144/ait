package dev.amble.ait.client.models.decoration; // Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

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

public class TardisStarModel extends HierarchicalModel {
    private final ModelPart star;

    public TardisStarModel(ModelPart root) {
        this.star = root.getChild("star");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition star = modelPartData.addOrReplaceChild("star",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-16.0F, -17.0F, -16.0F, 32.0F, 32.0F, 32.0F, new CubeDeformation(0.0F)).texOffs(0, 64)
                        .addBox(-16.0F, -17.0F, -16.0F, 32.0F, 32.0F, 32.0F, new CubeDeformation(0.25F)),
                PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, -0.6384F, -0.187F, -0.5372F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        star.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return star;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
    }
}
