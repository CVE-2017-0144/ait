package dev.amble.ait.client.models.coral;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.models.doors.DoorModel;
import dev.amble.ait.client.tardis.ClientTardis;

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

public class CoralGrowthDoorModel extends DoorModel {
    private final ModelPart coral;

    public CoralGrowthDoorModel(ModelPart root) {
        this.coral = root.getChild("coral");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition coral = modelPartData.addOrReplaceChild("coral", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -12.0F));

        PartDefinition seven = coral.addOrReplaceChild("seven", CubeListBuilder.create().texOffs(150, 386).addBox(-5.0F, -35.0F, -6.0F, 10.0F, 34.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(229, 393).addBox(-5.0F, -35.0F, -12.0F, 10.0F, 0.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(229, 386).addBox(-5.0F, -1.0F, -12.0F, 10.0F, 0.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(205, 386).addBox(-5.0F, -35.0F, -12.0F, 0.0F, 34.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(192, 386).addBox(5.0F, -35.0F, -12.0F, 0.0F, 34.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(97, 0).addBox(-12.0F, -39.0F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.001F))
                .texOffs(0, 0).addBox(-12.0F, 0.0F, -12.0F, 24.0F, 0.0F, 24.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r1 = seven.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(455, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -19.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r2 = seven.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(390, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -19.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r3 = seven.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(260, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -7.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r4 = seven.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(195, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -7.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r5 = seven.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(130, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -7.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r6 = seven.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(325, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -19.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r7 = seven.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(520, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -31.0F, 9.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r8 = seven.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -31.0F, 0.0F, 0.0F, -1.5708F, -0.7854F));

        PartDefinition cube_r9 = seven.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(65, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -31.0F, 0.0F, 0.0F, 1.5708F, 0.7854F));

        PartDefinition cube_r10 = seven.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(130, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -31.0F, -9.0F, 2.3562F, 0.0F, -3.1416F));

        PartDefinition cube_r11 = seven.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(65, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, 12.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r12 = seven.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 103).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(12.0F, 3.0F, 0.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition cube_r13 = seven.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(520, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-12.0F, 3.0F, 0.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r14 = seven.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(455, 86).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 3.0F, -12.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r15 = seven.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(195, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.0F, 9.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r16 = seven.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(260, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0F, -37.0F, 0.0F, 0.0F, -1.5708F, -0.2618F));

        PartDefinition cube_r17 = seven.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(325, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.0F, -9.0F, 2.8798F, 0.0F, -3.1416F));

        PartDefinition cube_r18 = seven.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(390, 120).addBox(-8.0F, -4.0F, -12.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.0F, -37.0F, 0.0F, 0.0F, 1.5708F, 0.2618F));

        PartDefinition bone9 = seven.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(65, 290).addBox(27.2752F, -21.0F, -0.8776F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(21, 343).addBox(10.3046F, -21.0F, 16.093F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(107, 290).addBox(3.2752F, -21.0F, -0.8776F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(42, 427).addBox(10.3046F, -21.0F, -7.907F, 9.9411F, 8.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(63, 427).addBox(10.3046F, 20.0F, -7.907F, 9.9411F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(-15.2752F, -21.0F, -4.093F));

        PartDefinition octagon_r1 = bone9.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(171, 386).addBox(-4.9706F, -13.0F, -12.0F, 9.9411F, 33.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.309F, 0.0F));

        PartDefinition octagon_r2 = bone9.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(233, 290).addBox(-4.9706F, -21.0F, -12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(86, 290).addBox(-12.0F, -21.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(128, 290).addBox(12.0F, -21.0F, -4.9706F, 0.0F, 42.0F, 9.9411F, new CubeDeformation(0.001F))
                .texOffs(0, 343).addBox(-4.9706F, -21.0F, 12.0F, 9.9411F, 42.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.2752F, 0.0F, 4.093F, 0.0F, -0.7854F, 0.0F));

        PartDefinition octagon_r3 = bone9.addOrReplaceChild("octagon_r3", CubeListBuilder.create().texOffs(0, 427).addBox(-4.9706F, -21.0F, -12.0F, 9.9411F, 8.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.2752F, 37.6777F, -8.6141F, -0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r4 = bone9.addOrReplaceChild("octagon_r4", CubeListBuilder.create().texOffs(21, 427).addBox(-4.9706F, -21.0F, -12.0F, 9.9411F, 8.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.2752F, -6.636F, 15.4275F, 0.7854F, 0.0F, 0.0F));

        PartDefinition octagon_r5 = bone9.addOrReplaceChild("octagon_r5", CubeListBuilder.create().texOffs(218, 386).addBox(-12.0F, -13.0F, -4.9706F, 0.0F, 33.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(30.5514F, 0.0F, 0.0003F, 0.0F, -0.2618F, 0.0F));
        return LayerDefinition.create(modelData, 1024, 1024);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        matrices.pushPose();
        matrices.mulPose(Axis.YP.rotationDegrees(180f));
        coral.render(matrices, vertexConsumer, light, overlay, color);
        matrices.popPose();
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity door, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        matrices.pushPose();
        matrices.translate(0, -1.5f, 0f);
        matrices.mulPose(Axis.YP.rotationDegrees(180f));

        super.renderWithAnimations(tardis, door, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);

        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return coral;
    }
}
