package dev.amble.ait.client.models.doors;

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
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.tardis.handler.DoorHandler;

public class ClassicDoorModel extends DoorModel {
    private final ModelPart classic;

    public ClassicDoorModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.classic = root.getChild("classic");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition classic = modelPartData.addOrReplaceChild("classic", CubeListBuilder.create(),
                PartPose.offset(0.0F, 26.0F, 9.0F));

        PartDefinition Posts = classic.addOrReplaceChild("Posts", CubeListBuilder.create().texOffs(220, 162).addBox(-17.0F, -62.0F,
                -17.0F, 4.0F, 60.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        Posts.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(203, 162).addBox(-17.0F, -64.0F, -18.0F, 4.0F, 60.0F,
                4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition Doors = classic.addOrReplaceChild("Doors", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        Doors.addOrReplaceChild("right_door",
                CubeListBuilder.create().texOffs(0, 189)
                        .addBox(-0.5F, -25.5F, -0.5F, 13.0F, 53.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 244)
                        .addBox(2.5F, -10.5F, 0.5F, 8.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(203, 76)
                        .addBox(-0.5F, -25.5F, -1.0F, 13.0F, 53.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-12.5F, -29.5F, -14.5F));

        Doors.addOrReplaceChild("left_door",
                CubeListBuilder.create().texOffs(194, 21)
                        .addBox(-12.5F, -25.5F, -0.5F, 13.0F, 53.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 29)
                        .addBox(-11.5F, -0.5F, -1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 29)
                        .addBox(-8.0F, -7.0F, -0.51F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(1, 258)
                        .addBox(-10.5F, -10.5F, 0.5F, 8.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(12.5F, -29.5F, -14.5F));

        classic.addOrReplaceChild("Walls",
                CubeListBuilder.create().texOffs(29, 106)
                        .addBox(-13.0F, -58.0F, -15.0F, 26.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(109, 0)
                        .addBox(-13.0F, -58.0F, -15.5F, 26.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        classic.addOrReplaceChild("PCB", CubeListBuilder.create().texOffs(237, 162).addBox(-14.0F, -61.0F, -18.0F, 28.0F, 4.0F, 6.0F,
                new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));
        return LayerDefinition.create(modelData, 512, 512);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        classic.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return classic;
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity doorEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        matrices.pushPose();
        matrices.scale(0.64F, 0.64F, 0.64F);
        matrices.translate(0, -1.5, 0.35);
        matrices.mulPose(Axis.YN.rotationDegrees(180));

        if (!AITModClient.CONFIG.animateDoors) {
            DoorHandler door = tardis.door();

            this.classic.getChild("Doors").getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? -5F : 0.0F;
            this.classic.getChild("Doors").getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen())
                    ? 5F
                    : 0.0F;
        } else {
            float maxRot = 90f;
            this.classic.getChild("Doors").getChild("left_door").yRot = (float) Math.toRadians(maxRot*tardis.door().getLeftRot());
            this.classic.getChild("Doors").getChild("right_door").yRot = (float) -Math.toRadians(maxRot*tardis.door().getRightRot());
        }

        super.renderWithAnimations(tardis, doorEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);
        matrices.popPose();
    }
}
