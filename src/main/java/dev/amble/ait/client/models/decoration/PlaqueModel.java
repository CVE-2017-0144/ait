package dev.amble.ait.client.models.decoration;

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

public class PlaqueModel extends HierarchicalModel {
    public final ModelPart plaque;

    public PlaqueModel(ModelPart root) {
        this.plaque = root.getChild("plaque");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition plaque = modelPartData.addOrReplaceChild("plaque",
                CubeListBuilder.create().texOffs(0, 14)
                        .addBox(-13.0F, -12.5F, -1.25F, 26.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-12.0F, -12.0F, -1.0F, 24.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 22.0F, 7.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        plaque.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return plaque;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
    }
}
