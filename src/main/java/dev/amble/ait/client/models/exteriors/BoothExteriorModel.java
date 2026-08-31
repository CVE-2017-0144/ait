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
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;

public class BoothExteriorModel extends SimpleExteriorModel {

    private final ModelPart k2;

    public BoothExteriorModel(ModelPart root) {
        this.k2 = root.getChild("k2");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition k2 = modelPartData.addOrReplaceChild("k2",
                CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -2.0F, -9.5F, 18.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.5F, 24.0F, 0.5F));

        PartDefinition Posts = k2.addOrReplaceChild("Posts", CubeListBuilder.create().texOffs(58, 103).addBox(-9.0F, -36.0F, -9.0F,
                2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = Posts.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(58, 103).addBox(-8.0F, -36.0F,
                -9.0F, 2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = Posts.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(58, 103).addBox(-8.0F, -36.0F,
                -8.0F, 2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r3 = Posts.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(58, 103).addBox(-9.0F, -36.0F,
                -8.0F, 2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition Roof = k2.addOrReplaceChild("Roof",
                CubeListBuilder.create().texOffs(52, 49)
                        .addBox(-9.0F, -37.0F, -9.0F, 17.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)).texOffs(52, 27)
                        .addBox(-9.0F, -37.0F, -9.0F, 17.0F, 2.0F, 17.0F, new CubeDeformation(0.25F)).texOffs(0, 21)
                        .addBox(-9.0F, -45.0F, -9.0F, 17.0F, 5.0F, 17.0F, new CubeDeformation(0.0F)).texOffs(0, 44)
                        .addBox(-9.0F, -45.0F, -9.0F, 17.0F, 4.0F, 17.0F, new CubeDeformation(0.4F)).texOffs(57, 5)
                        .addBox(-8.5F, -40.25F, -8.5F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(0, 66)
                        .addBox(-8.5F, -40.75F, -8.5F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.3F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition Walls = k2.addOrReplaceChild("Walls", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r4 = Walls.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(94, 104)
                        .addBox(-5.5F, -35.5F, -8.75F, 12.0F, 33.0F, 0.0F, new CubeDeformation(0.01F)).texOffs(0, 84)
                        .addBox(-6.0F, -36.0F, -9.25F, 13.0F, 34.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r5 = Walls.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(94, 104)
                        .addBox(-6.5F, -35.5F, -7.75F, 12.0F, 33.0F, 0.0F, new CubeDeformation(0.01F)).texOffs(0, 84)
                        .addBox(-7.0F, -36.0F, -8.25F, 13.0F, 34.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r6 = Walls.addOrReplaceChild("cube_r6",
                CubeListBuilder.create().texOffs(94, 69).addBox(-6.0F, -36.0F, 8.0F, 13.0F, 34.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(29, 84).addBox(-6.0F, -36.0F, -8.25F, 13.0F, 34.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition Door = k2.addOrReplaceChild("Door",
                CubeListBuilder.create().texOffs(65, 69)
                        .addBox(0.0F, -20.0F, -0.25F, 13.0F, 34.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 4)
                        .addBox(11.5F, -5.0F, -0.85F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(11.0F, -5.5F, -0.35F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(94, 104)
                        .addBox(0.5F, -19.5F, 0.25F, 12.0F, 33.0F, 0.0F, new CubeDeformation(0.01F)),
                PartPose.offset(-7.0F, -16.0F, -9.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        k2.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        matrices.pushPose();
        matrices.scale(1f, 1f, 1f);
        matrices.translate(0, -1.5f, 0);
        this.renderDoors(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, false);

        super.renderWithAnimations(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        matrices.popPose();
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        if (!AITModClient.CONFIG.animateDoors)
            this.k2.getChild("Door").yRot = tardis.door().isOpen() ? 1.575F : 0.0F;
        else {
            float maxRot = 90f;
            this.k2.getChild("Door").yRot = (float) Math.toRadians(maxRot * tardis.door().getLeftRot());
        }

        if (isBOTI) {
            matrices.pushPose();
            matrices.scale(1f, 1f, 1f);
            matrices.translate(0, -1.5f, 0);
            this.k2.getChild("Door").render(matrices, vertices, light, overlay, FastColor.ARGB32.colorFromFloat(pAlpha, red, green, blue));
            matrices.popPose();
        }
    }

    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices,
                                                           VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if (falling.tardis().isEmpty())
            return;

        matrices.pushPose();
        if (!AITModClient.CONFIG.animateDoors)
            this.k2.getChild("Door").yRot = falling.tardis().get().door().isOpen() ? 1.575F : 0.0F;
        else {
            float maxRot = 90f;
            this.k2.getChild("Door").yRot = (float) Math.toRadians(maxRot * falling.tardis().get().door().getLeftRot());
        }
        matrices.scale(1f, 1f, 1f);
        matrices.translate(0, -1.5f, 0);

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return k2;
    }
}
