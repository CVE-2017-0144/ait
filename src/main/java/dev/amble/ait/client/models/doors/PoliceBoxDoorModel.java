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

public class PoliceBoxDoorModel extends DoorModel {

    private final ModelPart TARDIS;

    public PoliceBoxDoorModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.TARDIS = root.getChild("TARDIS");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition TARDIS = modelPartData.addOrReplaceChild("TARDIS", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Posts = TARDIS.addOrReplaceChild("Posts", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, -32.0F));

        Posts.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(68, 227).addBox(-18.0F, -65.0F, -18.0F, 4.0F, 61.0F,
                4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 10.0F, 0.0F, 3.1416F, 0.0F));

        Posts.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(85, 227).addBox(-18.0F, -65.0F, -18.0F, 4.0F, 61.0F,
                4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 10.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition Doors = TARDIS.addOrReplaceChild("Doors", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        Doors.addOrReplaceChild("right_door",
                CubeListBuilder.create().texOffs(181, 177)
                        .addBox(0.5F, -25.5F, -0.5F, 13.0F, 55.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(102, 228)
                        .addBox(1.5F, -10.5F, 0.5F, 10.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 198)
                        .addBox(0.5F, -25.5F, -1.0F, 14.0F, 55.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 10)
                        .addBox(9.5F, -5.5F, -1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-13.5F, -29.5F, -5.5F));

        Doors.addOrReplaceChild("left_door",
                CubeListBuilder.create().texOffs(189, 41)
                        .addBox(-13.5F, -25.5F, -0.5F, 13.0F, 55.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-12.5F, -6.5F, -1.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(13.5F, -29.5F, -5.5F));

        TARDIS.addOrReplaceChild("Walls",
                CubeListBuilder.create().texOffs(63, 227)
                        .addBox(-14.0F, -56.0F, -6.0F, 1.0F, 56.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(116, 170)
                        .addBox(13.0F, -56.0F, -6.0F, 1.0F, 56.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(115, 0)
                        .addBox(-13.0F, -56.0F, -6.0F, 26.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(59, 113)
                        .addBox(13.0F, -56.0F, -6.5F, 1.0F, 56.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(115, 3)
                        .addBox(-13.0F, -56.0F, -6.5F, 26.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(62, 113)
                        .addBox(-14.0F, -56.0F, -6.5F, 1.0F, 56.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition PCB = TARDIS.addOrReplaceChild("PCB", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PCB.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(160, 9).addBox(-17.0F, -61.0F, 13.0F, 34.0F, 5.0F, 6.0F,
                new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.0F, 10.0F, 0.0F, 3.1416F, 0.0F));
        return LayerDefinition.create(modelData, 512, 512);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        TARDIS.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity doorEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        DoorHandler door = tardis.door();

        if (!AITModClient.CONFIG.animateDoors) {
            this.TARDIS.getChild("Doors").getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? -5F : 0.0F;
            this.TARDIS.getChild("Doors").getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen())
                    ? 5F
                    : 0.0F;
        } else {
            float maxRot = 90f;
            this.TARDIS.getChild("Doors").getChild("left_door").yRot = (float) Math.toRadians(maxRot*door.getLeftRot());
            this.TARDIS.getChild("Doors").getChild("right_door").yRot = (float) -Math.toRadians(maxRot*door.getRightRot());
        }

        matrices.pushPose();
        matrices.scale(0.63F, 0.63F, 0.63F);
        matrices.translate(0, -1.5, 0.35);
        matrices.mulPose(Axis.YN.rotationDegrees(180));

        super.renderWithAnimations(tardis, doorEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return TARDIS;
    }
}
