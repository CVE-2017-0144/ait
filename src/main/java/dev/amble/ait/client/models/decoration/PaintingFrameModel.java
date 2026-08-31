package dev.amble.ait.client.models.decoration;// Made with Blockbench 4.10.4
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
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class PaintingFrameModel extends HierarchicalModel {
    private final ModelPart frame;
    public PaintingFrameModel(ModelPart root) {
        this.frame = root.getChild("frame");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition frame = modelPartData.addOrReplaceChild("frame", CubeListBuilder.create().texOffs(0, 0).addBox(-24.0F, -32.0F, -9.0F, 48.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition plane = frame.addOrReplaceChild("plane", CubeListBuilder.create().texOffs(0, 33).addBox(-19.0F, -25.0F, -1.0F, 38.0F, 22.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, -6.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        frame.getChild("plane").visible = false;
        frame.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    public void renderWithFbo(PoseStack matrices, MultiBufferSource vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha, ResourceLocation frameTex) {
        frame.getChild("plane").render(matrices, vertexConsumer.getBuffer(RenderType.entityTranslucentCull(frameTex)), light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return frame;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}