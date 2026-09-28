package dev.amble.ait.client.models.doors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.tardis.handler.DoorHandler;

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
import net.minecraft.client.renderer.RenderType;

public class TardimDoorModel extends DoorModel {

    private final ModelPart tardis;

    public TardimDoorModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.tardis = root.getChild("tardis");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition tardis = modelPartData.addOrReplaceChild("tardis",
                CubeListBuilder.create().texOffs(62, 58)
                        .addBox(-11.0F, -32.0F, -8.0F, 3.0F, 32.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-8.0F, -40.0F, -8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(78, 26)
                        .addBox(-8.0F, -0.02F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(62, 9)
                        .addBox(-8.0F, 0.02F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        tardis.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(39, 25).addBox(-11.0F, -32.0F, -8.0F, 3.0F, 32.0F,
                16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        tardis.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(23, 74).addBox(-6.5F, -32.0F, -1.5F, 8.0F, 32.0F,
                3.0F, new CubeDeformation(0.001F)), PartPose.offset(6.5F, 0.0F, -9.5F));
        tardis.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(0, 74).addBox(-1.5F, -32.0F, -1.5F, 8.0F, 32.0F,
                3.0F, new CubeDeformation(0.001F)), PartPose.offset(-6.5F, 0.0F, -9.5F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        tardis.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return tardis;
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        matrices.pushPose();
        matrices.translate(0, -1.5f, 0);
        matrices.mulPose(Axis.YP.rotationDegrees(180f));

        if (!AITModClient.CONFIG.animateDoors) {
            DoorHandler handler = tardis.door();

            this.tardis.getChild("left_door").yRot = (handler.isLeftOpen() || handler.isOpen()) ? -1.575f : 0.0F;
            this.tardis.getChild("right_door").yRot = (handler.isRightOpen() || handler.areBothOpen()) ? 1.575f : 0.0F;
        } else {
            float maxRot = 90f;

            DoorHandler handler = tardis.door();
            this.tardis.getChild("left_door").yRot = (float) -Math.toRadians(maxRot*handler.getLeftRot());
            this.tardis.getChild("right_door").yRot = (float) Math.toRadians(maxRot*handler.getRightRot());
        }

        super.renderWithAnimations(tardis, linkableBlockEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);
        matrices.popPose();
    }
}
