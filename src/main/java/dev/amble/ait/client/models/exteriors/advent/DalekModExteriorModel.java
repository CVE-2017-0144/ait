package dev.amble.ait.client.models.exteriors.advent;

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
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.models.exteriors.SimpleExteriorModel;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.handler.DoorHandler;

public class DalekModExteriorModel extends SimpleExteriorModel {
    private final ModelPart dalekmod;

    public DalekModExteriorModel(ModelPart root) {
        this.dalekmod = root.getChild("dalekmod");

    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition dalekmod = modelPartData.addOrReplaceChild("dalekmod", CubeListBuilder.create().texOffs(0, 82).addBox(-12.0F, -1.0F, -12.0F, 24.0F, 1.0F, 24.0F, new CubeDeformation(0.0F))
        .texOffs(0, 56).addBox(-12.0F, -2.0F, -12.0F, 24.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Posts = dalekmod.addOrReplaceChild("Posts", CubeListBuilder.create().texOffs(0, 141).addBox(8.0F, -40.0F, -11.0F, 3.0F, 39.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(0, 141).addBox(-11.0F, -40.0F, -11.0F, 3.0F, 39.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(0, 141).addBox(-11.0F, -40.0F, 8.0F, 3.0F, 39.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(0, 141).addBox(8.0F, -40.0F, 8.0F, 3.0F, 39.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(12, 141).addBox(8.0F, -38.0F, -11.0F, 3.0F, 37.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(12, 141).addBox(-11.0F, -38.0F, -11.0F, 3.0F, 37.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(12, 141).addBox(-11.0F, -38.0F, 8.0F, 3.0F, 37.0F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(12, 141).addBox(8.0F, -38.0F, 8.0F, 3.0F, 37.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Doors = dalekmod.addOrReplaceChild("Doors", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_door = Doors.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(48, 141).addBox(0.0F, -17.0F, 0.0F, 8.0F, 32.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(144, 128).addBox(0.0F, -18.0F, 0.0F, 8.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -16.0F, -10.0F));

        PartDefinition left_door = Doors.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(68, 162).addBox(-8.0F, -17.0F, 0.0F, 8.0F, 32.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(88, 162).addBox(-8.0F, -18.0F, 0.0F, 8.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -16.0F, -10.0F));

        PartDefinition Walls = dalekmod.addOrReplaceChild("Walls", CubeListBuilder.create().texOffs(0, 107).addBox(-8.0F, -33.0F, 8.0F, 16.0F, 32.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(36, 107).addBox(-8.0F, -34.0F, 8.0F, 16.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition LeftWall2px_r1 = Walls.addOrReplaceChild("LeftWall2px_r1", CubeListBuilder.create().texOffs(36, 107).addBox(-8.0F, -34.0F, 8.0F, 16.0F, 32.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(0, 107).addBox(-8.0F, -33.0F, 8.0F, 16.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition RightWall2px_r1 = Walls.addOrReplaceChild("RightWall2px_r1", CubeListBuilder.create().texOffs(36, 107).addBox(-8.0F, -34.0F, 8.0F, 16.0F, 32.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(0, 107).addBox(-8.0F, -33.0F, 8.0F, 16.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition PCB = dalekmod.addOrReplaceChild("PCB", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -37.0F, -12.0F, 24.0F, 4.0F, 24.0F, new CubeDeformation(0.0F))
        .texOffs(0, 28).addBox(-12.0F, -38.0F, -12.0F, 24.0F, 4.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Roof = dalekmod.addOrReplaceChild("Roof", CubeListBuilder.create().texOffs(96, 48).addBox(-10.0F, -41.0F, -10.0F, 20.0F, 4.0F, 20.0F, new CubeDeformation(0.0F))
        .texOffs(96, 72).addBox(-10.0F, -39.0F, -10.0F, 20.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
        .texOffs(96, 0).addBox(-11.0F, -41.0F, -11.0F, 22.0F, 3.0F, 22.0F, new CubeDeformation(0.0F))
        .texOffs(96, 25).addBox(-11.0F, -38.0F, -11.0F, 22.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
        .texOffs(72, 107).addBox(-2.0F, -46.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(72, 116).addBox(-2.0F, -44.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
        .texOffs(156, 162).addBox(-2.0F, -43.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.pushPose();
        matrices.scale(1f, 1f, 1f);
        matrices.translate(0, -0.1, 0);
        dalekmod.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        DoorHandler door = tardis.door();

        if (!AITModClient.CONFIG.animateDoors) {
            this.dalekmod.getChild("Doors").getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? -5F : 0.0F;
            this.dalekmod.getChild("Doors").getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen())
                    ? 5F
                    : 0.0F;
        } else {
            this.dalekmod.getChild("Doors").getChild("left_door").yRot = (float) Math.toRadians(80f*door.getLeftRot());
            this.dalekmod.getChild("Doors").getChild("right_door").yRot = (float) -Math.toRadians(80f*door.getRightRot());
        }

        if (isBOTI) {
            matrices.pushPose();
            matrices.scale(0.945F, 0.945F, 0.945F);
            matrices.translate(-0.002, -0.0012f, -0.004);
            this.dalekmod.getChild("Doors").render(matrices, vertices, light, overlay, red, green, blue, pAlpha);
            matrices.popPose();
        }
    }

    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices,
                                                           VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if (falling.tardis().isEmpty())
            return;

        matrices.pushPose();
        matrices.scale(0.945F, 0.945F, 0.945F);
        matrices.translate(1, -1.5f, 0);

        DoorHandler door = falling.tardis().get().door();

        if (!AITModClient.CONFIG.animateDoors) {
            this.dalekmod.getChild("Doors").getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? -5F : 0.0F;
            this.dalekmod.getChild("Doors").getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen())
                    ? 5F
                    : 0.0F;
        } else {
            float maxRot = 80;
            this.dalekmod.getChild("Doors").getChild("left_door").yRot = (float) Math.toRadians(maxRot*door.getLeftRot());
            this.dalekmod.getChild("Doors").getChild("right_door").yRot = (float) -Math.toRadians(maxRot*door.getRightRot());
        }

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return dalekmod;
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        matrices.pushPose();
        matrices.scale(0.945F, 0.945F, 0.945F);
        matrices.translate(0, -1.5f, 0);

        this.renderDoors(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, false);

        super.renderWithAnimations(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        matrices.popPose();
    }
}