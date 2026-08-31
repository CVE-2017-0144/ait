package dev.amble.ait.client.models.exteriors;

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

public class JakeTheDogExteriorModel  extends SimpleExteriorModel {
    private final ModelPart jake;

    public JakeTheDogExteriorModel(ModelPart root) {
        this.jake = root.getChild("jake");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition jake = modelPartData.addOrReplaceChild("jake", CubeListBuilder.create().texOffs(0, 0).addBox(-19.0F, -38.0F, -19.0F, 38.0F, 38.0F, 38.0F, new CubeDeformation(0.0F))
        .texOffs(63, 124).addBox(14.0F, -2.0F, -47.0F, 2.0F, 2.0F, 28.0F, new CubeDeformation(0.0F))
        .texOffs(94, 76).addBox(-8.0F, -36.0F, -21.0F, 16.0F, 4.0F, 2.0F, new CubeDeformation(0.2F))
        .texOffs(52, 39).addBox(-8.0F, -31.6F, -21.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.2F))
        .texOffs(52, 39).mirror().addBox(5.0F, -31.6F, -21.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
        .texOffs(102, 82).addBox(9.5F, -37.0F, -20.5F, 9.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(102, 82).mirror().addBox(-18.5F, -37.0F, -20.5F, 9.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
        .texOffs(13, 2).addBox(-17.5F, -36.0F, -20.4F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.2F))
        .texOffs(13, 2).mirror().addBox(10.5F, -36.0F, -20.4F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.2F)).mirror(false)
        .texOffs(63, 124).mirror().addBox(-16.0F, -2.0F, -47.0F, 2.0F, 2.0F, 28.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = jake.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(87, 49).mirror().addBox(-19.0F, -2.0F, -9.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(18.0F, -3.0782F, 27.4572F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r2 = jake.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 125).mirror().addBox(-1.0F, -11.0F, -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-20.0F, -20.0F, -10.0F, -0.6471F, 0.1059F, 0.139F));

        PartDefinition cube_r3 = jake.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 125).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0F, -20.0F, -10.0F, -0.6471F, -0.1059F, -0.139F));

        PartDefinition door = jake.addOrReplaceChild("door", CubeListBuilder.create().texOffs(5, 124).addBox(-8.0F, -32.0F, -19.5F, 16.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        jake.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        matrices.pushPose();
        matrices.translate(0, -1.5f, 0);
        jake.getChild("door").z = tardis.door().isOpen() ? 2.0f : 0f;

        super.renderWithAnimations(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        matrices.popPose();
    }

    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices,
                                                           VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.pushPose();

        matrices.translate(0, -1.5f, 0);

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return jake;
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {

    }
}