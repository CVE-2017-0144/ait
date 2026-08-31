package dev.amble.ait.client.models.exteriors; // Made with Blockbench 4.10.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.handler.DoorHandler;

public class GeometricExteriorModel extends SimpleExteriorModel {
    private final ModelPart geometric;

    public GeometricExteriorModel(ModelPart root) {
        this.geometric = root.getChild("geometric");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition geometric = modelPartData.addOrReplaceChild("geometric", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = geometric.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-16.0F, -44.0F, -8.0F, 32.0F, 44.0F, 24.0F, new CubeDeformation(0.05F)).texOffs(49, 69)
                        .addBox(8.0F, -40.0F, -8.0F, 0.0F, 40.0F, 24.0F, new CubeDeformation(0.0F)).texOffs(0, 69)
                        .addBox(-8.0F, -40.0F, -8.0F, 0.0F, 40.0F, 24.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition door = geometric.addOrReplaceChild("door",
                CubeListBuilder.create().texOffs(98, 97)
                        .addBox(-8.0F, -40.0F, -8.0F, 16.0F, 40.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(113, 0)
                        .addBox(-8.0F, -38.0F, 15.75F, 16.0F, 36.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(98, 72)
                        .addBox(-8.0F, -2.0F, -6.0F, 16.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)).texOffs(91, 47)
                        .addBox(-8.0F, -40.0F, -6.0F, 16.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        geometric.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return geometric;
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        matrices.pushPose();
        matrices.scale(1F, 1F, 1F);
        matrices.translate(0, -1.5f, 0);

        this.renderDoors(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, false);

        super.renderWithAnimations(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);

        matrices.popPose();
    }

    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices,
                                                           VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.pushPose();
        matrices.scale(1F, 1F, 1F);
        matrices.translate(0, -1.5f, 0);

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        DoorHandler door = tardis.door();

        this.geometric.getChild("door").z = door.isOpen() ? -16f : 0f;

        if (isBOTI) {
            matrices.pushPose();
            matrices.scale(1F, 1F, 1F);
            matrices.translate(0f, 0f, -4f);
            this.geometric.getChild("door").render(matrices, vertices, light, overlay, red, green, blue, pAlpha);
            matrices.popPose();
        }
    }
}
