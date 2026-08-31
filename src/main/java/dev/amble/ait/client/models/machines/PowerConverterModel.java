package dev.amble.ait.client.models.machines;

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

public class PowerConverterModel extends HierarchicalModel {
    private final ModelPart bone;

    private PowerConverterModel(ModelPart root) {
        this.bone = root.getChild("bone");
    }
    public PowerConverterModel() {
        this(getTexturedModelData().bakeRoot());
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition bone = modelPartData.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -18.0F, -6.0F, 10.0F, 18.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(-5.0F, -13.0F, -7.5F, 10.0F, 3.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(55, 50).addBox(2.0F, -16.0F, -7.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-5.0F, -30.0F, -6.0F, 10.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(44, 0).addBox(-5.0F, -24.0F, -4.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(44, 23).addBox(-5.0F, -24.0F, -6.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 31).addBox(4.0F, -24.0F, -6.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition hacth = bone.addOrReplaceChild("hacth", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, -5.6F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public ModelPart root() {
        return bone;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
                          float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
                       float green, float blue, float alpha) {
        bone.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}