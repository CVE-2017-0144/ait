package dev.amble.ait.client.models.decoration;// Made with Blockbench 4.11.2
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

public class FlagModel extends HierarchicalModel {
    private final ModelPart flag;
    public FlagModel(ModelPart root) {
        this.flag = root.getChild("flag");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition flag = modelPartData.addOrReplaceChild("flag", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -32.0F, -0.5F, 1.0F, 32.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 15).addBox(-1.0F, -34.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition clamp = flag.addOrReplaceChild("clamp", CubeListBuilder.create().texOffs(22, 15).addBox(0.0F, -7.0F, 0.0F, 1.0F, 14.0F, 0.0F, new CubeDeformation(0.05F)), PartPose.offset(0.5F, -25.0F, 0.0F));

        PartDefinition flag_elements = flag.addOrReplaceChild("flag_elements", CubeListBuilder.create().texOffs(5, 0).addBox(0.0F, -7.0F, 0.5F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.05F)), PartPose.offset(1.5F, -25.0F, -0.5F));

        PartDefinition element2 = flag_elements.addOrReplaceChild("element2", CubeListBuilder.create().texOffs(22, 0).addBox(0.0F, -7.0F, 0.0F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.05F)), PartPose.offset(8.0F, 0.0F, 0.5F));

        PartDefinition element3 = element2.addOrReplaceChild("element3", CubeListBuilder.create().texOffs(5, 15).addBox(0.0F, -7.0F, 0.0F, 8.0F, 14.0F, 0.0F, new CubeDeformation(0.05F)), PartPose.offset(8.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        flag.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return flag;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}