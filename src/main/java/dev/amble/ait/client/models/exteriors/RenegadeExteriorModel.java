package dev.amble.ait.client.models.exteriors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;

public class RenegadeExteriorModel extends SimpleExteriorModel {
    private final ModelPart renegade;

    public RenegadeExteriorModel(ModelPart root) {
        this.renegade = root.getChild("renegade");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition renegade = modelPartData.addOrReplaceChild("renegade", CubeListBuilder.create().texOffs(0, 19).addBox(-12.0F,
                -1.7F, -8.5F, 24.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition walls = renegade.addOrReplaceChild("walls",
                CubeListBuilder.create().texOffs(59, 61).addBox(8.0F, -38.0F, -8.0F, 3.0F, 36.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 77).addBox(7.0F, -37.0F, -7.0F, 5.0F, 35.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(98, 98)
                        .addBox(12.25F, -37.0F, -6.0F, 0.0F, 35.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(98, 98)
                        .addBox(-11.25F, -37.0F, -6.0F, 0.0F, 35.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(59, 61)
                        .addBox(-10.0F, -38.0F, -8.0F, 3.0F, 36.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(83, 5)
                        .addBox(-11.0F, -37.0F, -7.0F, 5.0F, 35.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(98, 55)
                        .addBox(-6.0F, -37.0F, 0.0F, 13.0F, 35.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(68, 114)
                        .addBox(-6.0F, -37.0F, 7.25F, 13.0F, 34.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.5F, 0.3F, 0.0F));

        PartDefinition roof = renegade.addOrReplaceChild("roof",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-12.5F, -40.75F, -8.5F, 26.0F, 1.0F, 17.0F, new CubeDeformation(0.2F)).texOffs(0, 39)
                        .addBox(-12.0F, -39.0F, -8.0F, 25.0F, 1.0F, 16.0F, new CubeDeformation(0.2F)).texOffs(0, 57)
                        .addBox(-11.0F, -42.0F, -7.0F, 23.0F, 5.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.5F, 0.3F, 0.0F));

        PartDefinition door = renegade.addOrReplaceChild("door",
                CubeListBuilder.create().texOffs(39, 114)
                        .addBox(-0.5F, -35.0F, -0.75F, 13.0F, 35.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(122, 0)
                        .addBox(0.0F, -34.5F, -1.0F, 12.0F, 34.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(122, 0)
                        .addBox(0.0F, -34.5F, 0.5F, 12.0F, 34.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(123, 98)
                        .addBox(1.0F, -27.5F, -1.05F, 10.0F, 20.0F, 1.0F, new CubeDeformation(-0.0499F)).texOffs(70, 0)
                        .addBox(1.0F, -22.5F, -1.3F, 10.0F, 10.0F, 1.0F, new CubeDeformation(-0.05F)),
                PartPose.offset(-6.0F, -1.7F, -6.0F));

        PartDefinition cube_r1 = door.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(42, 77)
                        .addBox(3.55F, 3.55F, -0.75F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(25, 77)
                        .addBox(0.0F, 0.0F, -1.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(66, 19)
                        .addBox(-3.5F, -3.5F, -1.25F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 19)
                        .addBox(-7.0F, -7.0F, -1.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(0, 0)
                        .addBox(-10.55F, -10.55F, -0.75F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(6.0F, -17.5F, -0.25F, 3.1416F, 0.0F, 2.3562F));

        PartDefinition cube_r2 = door.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(70, 0)
                        .addBox(-5.0F, -5.0F, -1.05F, 10.0F, 10.0F, 1.0F, new CubeDeformation(-0.05F)).texOffs(123, 98)
                        .addBox(-5.0F, -10.0F, -0.8F, 10.0F, 20.0F, 1.0F, new CubeDeformation(-0.0499F)),
                PartPose.offsetAndRotation(6.0F, -17.5F, -0.25F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r3 = door.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(42, 77)
                        .addBox(3.55F, 3.55F, -0.75F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(0, 19)
                        .addBox(-7.0F, -7.0F, -1.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(25, 77)
                        .addBox(0.0F, 0.0F, -1.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(66, 19)
                        .addBox(-3.5F, -3.5F, -1.25F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-10.55F, -10.55F, -0.75F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(6.0F, -17.5F, -0.25F, 0.0F, 0.0F, 0.7854F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        renegade.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        matrices.pushPose();
        matrices.translate(0, -1.5f, 0);

        this.renderDoors(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, false);

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
        return renegade;
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        if (!AITModClient.CONFIG.animateDoors)
            renegade.getChild("door").yRot = tardis.door().isOpen() ? 1.75f : 0f;
        else {
            renegade.getChild("door").yRot = (float) Math.toRadians(90f * tardis.door().getLeftRot());
        }

        if (isBOTI) {
            matrices.pushPose();
            matrices.translate(0, -1.5f, 0);
            renegade.getChild("door").render(matrices, vertices, light, overlay, FastColor.ARGB32.colorFromFloat(pAlpha, red, green, blue));
            matrices.popPose();
        }
    }
}
