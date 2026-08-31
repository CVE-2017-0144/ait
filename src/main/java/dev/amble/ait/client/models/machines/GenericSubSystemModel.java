package dev.amble.ait.client.models.machines;

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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.AITMod;

public class GenericSubSystemModel extends HierarchicalModel {
    public static final ResourceLocation TEXTURE = AITMod.id("textures/blockentities/machines/generic_subsystem.png");

    private final ModelPart box;

    public GenericSubSystemModel(ModelPart root) {
        this.box = root.getChild("box");
    }
    public GenericSubSystemModel() {
        this(getTexturedModelData().bakeRoot());
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition box = modelPartData.addOrReplaceChild("box", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(-0.001F)), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition wires = box.addOrReplaceChild("wires", CubeListBuilder.create().texOffs(33, 33).addBox(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 33).addBox(0.0F, -16.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 12.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public ModelPart root() {
        return box;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.box.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
