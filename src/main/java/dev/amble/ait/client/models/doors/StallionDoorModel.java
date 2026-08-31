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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;

public class StallionDoorModel extends DoorModel {
    private final ModelPart body;
    public StallionDoorModel(ModelPart root) {
        this.body = root.getChild("body");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition door = body.addOrReplaceChild("door", CubeListBuilder.create(), PartPose.offset(7.5F, -20.0F, -7.0F));

        PartDefinition cube_r1 = door.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(17, 122).addBox(-9.0F, -38.0F, -8.0F, 0.0F, 37.0F, 8.0F, new CubeDeformation(-0.001F))
        .texOffs(117, 0).addBox(-9.5F, -38.0F, -8.0F, 1.0F, 37.0F, 8.0F, new CubeDeformation(-0.001F))
        .texOffs(14, 26).addBox(-9.5F, -24.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(5, 16).addBox(-10.25F, -23.5F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
        .texOffs(0, 16).addBox(-8.75F, -23.5F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, 20.0F, 9.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition door_two = door.addOrReplaceChild("door_two", CubeListBuilder.create(), PartPose.offset(-7.5F, -3.0F, 0.5F));

        PartDefinition cube_r2 = door_two.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 122).addBox(-9.0F, -38.0F, 0.0F, 0.0F, 37.0F, 8.0F, new CubeDeformation(-0.001F))
        .texOffs(109, 92).addBox(-9.5F, -38.0F, 0.0F, 1.0F, 37.0F, 8.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, 23.0F, 8.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone = body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(18, 44).addBox(-10.0F, -1.0F, -10.0F, 20.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(83, 164).addBox(-10.0F, -43.0F, -10.0F, 20.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
        .texOffs(136, 0).addBox(8.0F, -38.0F, -10.0F, 2.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(128, 92).addBox(8.0F, -38.0F, -10.0F, 2.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition top = body.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(0.5F, 0.0F, 1.5F));

        PartDefinition antenna = top.addOrReplaceChild("antenna", CubeListBuilder.create(), PartPose.offset(-0.5F, -52.0F, 0.5F));

        PartDefinition antenna2 = top.addOrReplaceChild("antenna2", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition antenna3 = top.addOrReplaceChild("antenna3", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition antenna4 = top.addOrReplaceChild("antenna4", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, -2.3562F, 0.0F));

        PartDefinition antenna5 = top.addOrReplaceChild("antenna5", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, 3.1416F, 0.0F));

        PartDefinition antenna6 = top.addOrReplaceChild("antenna6", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, 2.3562F, 0.0F));

        PartDefinition antenna7 = top.addOrReplaceChild("antenna7", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, 1.5708F, 0.0F));

        PartDefinition antenna8 = top.addOrReplaceChild("antenna8", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -52.0F, 0.5F, 0.0F, 0.7854F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        body.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return body;
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        matrices.pushPose();
        matrices.scale(0.95f, 0.95f, 0.95f);
        matrices.translate(0, -1.5f, 0);
        matrices.mulPose(Axis.YN.rotationDegrees(180f));

        if (!AITModClient.CONFIG.animateDoors) {
            body.getChild("door").yRot = tardis.door().isOpen() ? -1.35f : 0f;
            body.getChild("door").getChild("door_two").yRot = tardis.door().isOpen() ? 2.65f : 0f;
        } else {
            float maxLeftRot = 87f;
            float maxRightRot = 150f;

            body.getChild("door").yRot = -(float) Math.toRadians(maxLeftRot*tardis.door().getLeftRot());
            body.getChild("door").getChild("door_two").yRot = (float) Math.toRadians(maxRightRot*tardis.door().getLeftRot());
        }

        super.renderWithAnimations(tardis, linkableBlockEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);

        matrices.popPose();
    }

}