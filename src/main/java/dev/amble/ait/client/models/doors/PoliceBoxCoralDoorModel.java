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

public class PoliceBoxCoralDoorModel extends DoorModel {

    private final ModelPart TARDIS;

    public PoliceBoxCoralDoorModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.TARDIS = root.getChild("TARDIS");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition TARDIS = modelPartData.addOrReplaceChild("TARDIS", CubeListBuilder.create(),
                PartPose.offset(0.0F, 28.0F, 0.0F));

        PartDefinition Posts = TARDIS.addOrReplaceChild("Posts", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        Posts.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(236, 42).addBox(-18.0F, -60.0F, -17.0F, 4.0F, 56.0F,
                3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -32.0F, 0.0F, 1.5708F, 0.0F));
        Posts.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(236, 102).addBox(-2.0F, -30.0F, 17.0F, 3.0F, 30.0F, 4.0F,
                new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -34.5F, -18.0F, -1.5708F, 1.4835F, -1.5708F));
        Posts.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(236, 127).addBox(-2.0F, 0.0F, 17.0F, 3.0F, 31.0F, 4.0F,
                new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -34.6743F, -18.0F, 1.5708F, 1.4835F, 1.5708F));
        Posts.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(251, 127).addBox(-2.0F, 0.0F, 17.0F, 3.0F, 31.0F, 4.0F,
                new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-38.0F, -34.6743F, -18.0F, 1.5708F, 1.4835F, 1.5708F));
        Posts.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(251, 102).addBox(-2.0F, -30.0F, 17.0F, 3.0F, 30.0F, 4.0F,
                new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-38.0F, -34.5F, -18.0F, -1.5708F, 1.4835F, -1.5708F));
        Posts.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(251, 41).addBox(-17.0F, -60.0F, -18.0F, 3.0F, 56.0F,
                4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -32.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition Doors = TARDIS.addOrReplaceChild("Doors", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_door = Doors.addOrReplaceChild("right_door",
                CubeListBuilder.create().texOffs(181, 177)
                        .addBox(0.5F, -29.5F, -0.5F, 13.0F, 55.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 198)
                        .addBox(0.5F, -29.5F, -1.0F, 14.0F, 55.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 10)
                        .addBox(9.5F, -9.5F, -1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 51)
                        .addBox(2.5F, -9.5F, -1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-13.5F, -29.5F, -15.5F));

        right_door.addOrReplaceChild("phone",
                CubeListBuilder.create().texOffs(268, 37)
                        .addBox(-3.75F, -40.0F, -13.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(268, 37)
                        .addBox(-3.75F, -39.8F, -13.5F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(268, 43)
                        .addBox(-3.75F, -37.75F, -13.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F)).texOffs(265, 25)
                        .addBox(-8.5F, -40.0F, -13.5F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(268, 34)
                        .addBox(-7.0F, -39.5F, -11.25F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(259, 35)
                        .addBox(-5.5F, -42.0F, -13.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(259, 35)
                        .addBox(-8.5F, -42.0F, -13.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(250, 35)
                        .addBox(-5.5F, -42.0F, -13.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).texOffs(250, 35)
                        .addBox(-8.5F, -42.0F, -13.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)).texOffs(250, 25)
                        .addBox(-9.0F, -41.5F, -14.5F, 6.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(12.5F, 28.5F, 14.5F));

        right_door.addOrReplaceChild("phone_t",
                CubeListBuilder.create().texOffs(266, 74)
                        .addBox(-9.25F, -40.0F, -13.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(266, 74)
                        .addBox(-9.25F, -39.8F, -13.5F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(266, 80)
                        .addBox(-9.0F, -37.75F, -13.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F)).texOffs(266, 60)
                        .addBox(-7.5F, -41.0F, -14.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(266, 71)
                        .addBox(-7.0F, -40.0F, -11.25F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(266, 71)
                        .addBox(-7.0F, -37.0F, -11.25F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(13.25F, 28.5F, 14.5F));

        Doors.addOrReplaceChild("left_door",
                CubeListBuilder.create().texOffs(189, 41)
                        .addBox(-13.5F, -29.5F, -0.5F, 13.0F, 55.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-12.5F, -10.5F, -1.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 51)
                        .addBox(-12.5F, -4.5F, -1.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(13.5F, -29.5F, -15.5F));

        TARDIS.addOrReplaceChild("Walls",
                CubeListBuilder.create().texOffs(63, 227)
                        .addBox(-14.0F, -60.0F, -16.0F, 1.0F, 56.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(116, 170)
                        .addBox(13.0F, -60.0F, -16.0F, 1.0F, 56.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(115, 0)
                        .addBox(-13.0F, -60.0F, -16.0F, 26.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(59, 113)
                        .addBox(13.0F, -60.0F, -16.5F, 1.0F, 56.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(115, 3)
                        .addBox(-13.0F, -60.0F, -16.5F, 26.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(62, 113)
                        .addBox(-14.0F, -60.0F, -16.5F, 1.0F, 56.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition PCB = TARDIS.addOrReplaceChild("PCB",
                CubeListBuilder.create().texOffs(241, 2)
                        .addBox(-17.0F, -64.0F, -19.0F, 34.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(243, 3)
                        .addBox(-16.0F, -60.0F, -17.0F, 32.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(241, 14)
                        .addBox(-16.0F, -63.0F, -17.0F, 32.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(241, 18)
                        .addBox(-1.0F, -63.0F, -14.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(243, 5)
                        .addBox(16.0F, -63.0F, -17.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(277, 5)
                        .addBox(-16.0F, -63.0F, -17.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(243, 3)
                        .addBox(-16.0F, -63.0F, -17.0F, 32.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        PCB.addOrReplaceChild("lights",
                CubeListBuilder.create().texOffs(241, 28)
                        .addBox(10.0F, -59.3F, -16.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).texOffs(241, 22)
                        .addBox(10.0F, -60.4F, -16.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(241, 22)
                        .addBox(4.0F, -60.4F, -16.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(241, 28)
                        .addBox(4.0F, -59.3F, -16.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).texOffs(241, 22)
                        .addBox(-6.0F, -60.4F, -16.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(241, 28)
                        .addBox(-6.0F, -59.3F, -16.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).texOffs(241, 22)
                        .addBox(-12.0F, -60.4F, -16.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(241, 28)
                        .addBox(-12.0F, -59.3F, -16.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)),
                PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition TARDIS_t = TARDIS.addOrReplaceChild("TARDIS_t", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition PCB_t = TARDIS_t.addOrReplaceChild("PCB_t",
                CubeListBuilder.create().texOffs(0, 434)
                        .addBox(-17.0F, -64.0F, -19.0F, 34.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(8, 435)
                        .addBox(-11.0F, -60.0F, -17.0F, 22.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 446)
                        .addBox(-11.0F, -63.0F, -17.0F, 22.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 450)
                        .addBox(-1.0F, -63.0F, -14.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 437)
                        .addBox(11.0F, -63.0F, -17.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(38, 437)
                        .addBox(-11.0F, -63.0F, -17.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(21, 435)
                        .addBox(-11.0F, -63.0F, -17.0F, 22.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        PCB_t.addOrReplaceChild("lights2",
                CubeListBuilder.create().texOffs(241, 28)
                        .addBox(10.0F, -59.3F, -16.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).texOffs(241, 22)
                        .addBox(10.0F, -60.4F, -16.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(241, 22)
                        .addBox(4.0F, -60.4F, -16.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(241, 28)
                        .addBox(4.0F, -59.3F, -16.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).texOffs(241, 22)
                        .addBox(-6.0F, -60.4F, -16.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(241, 28)
                        .addBox(-6.0F, -59.3F, -16.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).texOffs(241, 22)
                        .addBox(-12.0F, -60.4F, -16.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(241, 28)
                        .addBox(-12.0F, -59.3F, -16.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)),
                PartPose.offset(0.0F, -3.0F, 0.0F));

        PCB_t.addOrReplaceChild("lights_t2",
                CubeListBuilder.create().texOffs(267, 55)
                        .addBox(7.0F, -59.3F, -16.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).texOffs(267, 49)
                        .addBox(7.0F, -60.4F, -16.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(267, 49)
                        .addBox(1.75F, -60.4F, -16.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(267, 55)
                        .addBox(1.75F, -59.3F, -16.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).texOffs(267, 49)
                        .addBox(-3.75F, -60.4F, -16.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(267, 55)
                        .addBox(-3.75F, -59.3F, -16.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)).texOffs(267, 49)
                        .addBox(-9.0F, -60.4F, -16.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(267, 55)
                        .addBox(-9.0F, -59.3F, -16.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.15F)),
                PartPose.offset(0.0F, -3.0F, 0.0F));

        return LayerDefinition.create(modelData, 512, 512);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        TARDIS.render(matrices, vertexConsumer, light, overlay, color);
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
        matrices.scale(0.631F, 0.631F, 0.631F);
        matrices.translate(0, -1.5f, -0.35);
        matrices.mulPose(Axis.YN.rotationDegrees(180));

        super.renderWithAnimations(tardis, doorEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return TARDIS;
    }
}
