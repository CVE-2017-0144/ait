package dev.amble.ait.client.models.machines; // Made with Blockbench 4.10.1
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

public class FabricatorModel extends HierarchicalModel {
    private final ModelPart bone;

    public FabricatorModel(ModelPart root) {
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild("bone",
                CubeListBuilder.create().texOffs(0, 33)
                        .addBox(-2.0F, -14.0F, 3.95F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.001F)).texOffs(0, 0)
                        .addBox(-8.0F, -2.0F, -8.05F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(0, 19)
                        .addBox(-3.0F, -13.0F, -4.05F, 6.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(25, 19)
                        .addBox(-3.0F, -2.5F, -3.05F, 6.0F, 0.0F, 11.0F, new CubeDeformation(0.005F)).texOffs(32, 31)
                        .addBox(-2.5F, -14.0F, -2.55F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(17, 40)
                        .addBox(-2.5F, -10.9F, -2.55F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.005F)).texOffs(38, 40)
                        .addBox(-10.0F, -2.0F, -2.05F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(8.0F, -2.0F, -2.05F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(49, 0)
                        .addBox(-2.0F, -2.0F, 7.95F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(17, 46)
                        .addBox(-2.0F, -2.0F, -10.05F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.05F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        bone.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return bone;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
    }
}
