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
import dev.amble.ait.client.models.exteriors.BoothExteriorModel;
import dev.amble.ait.client.tardis.ClientTardis;

public class BoothDoorModel extends DoorModel {

    private final ModelPart k2;

    public BoothDoorModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.k2 = root.getChild("k2");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition k2 = modelPartData.addOrReplaceChild("k2", CubeListBuilder.create(),
                PartPose.offset(0.5F, 26.0F, 1.0F));

        PartDefinition Posts = k2.addOrReplaceChild("Posts", CubeListBuilder.create().texOffs(58, 103).addBox(-9.0F, -36.0F, -9.0F,
                2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        Posts.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(58, 103).addBox(-9.0F, -36.0F, -8.0F, 2.0F, 34.0F, 2.0F,
                new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        k2.addOrReplaceChild("Roof",
                CubeListBuilder.create().texOffs(106, 8).addBox(-9.0F, -37.0F, -9.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(106, 13).addBox(-9.0F, -37.0F, -9.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)).texOffs(106, 0)
                        .addBox(-9.0F, -45.0F, -9.0F, 17.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(122, 21)
                        .addBox(-9.0F, -45.0F, -9.0F, 17.0F, 4.0F, 2.0F, new CubeDeformation(0.4F)).texOffs(122, 28)
                        .addBox(-8.5F, -40.25F, -8.5F, 16.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(102, 60)
                        .addBox(-8.5F, -40.75F, -8.5F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        k2.addOrReplaceChild("Door",
                CubeListBuilder.create().texOffs(65, 69)
                        .addBox(0.0F, -20.0F, -0.25F, 13.0F, 34.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 4)
                        .addBox(11.5F, -5.0F, -0.85F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(11.0F, -5.5F, -0.35F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(94, 104)
                        .addBox(0.5F, -19.5F, 0.25F, 12.0F, 33.0F, 0.0F, new CubeDeformation(0.01F)),
                PartPose.offset(-7.0F, -16.0F, -9.0F));

        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public ModelPart root() {
        return k2;
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        BoothExteriorModel boothExteriorModel = new BoothExteriorModel(BoothExteriorModel.getTexturedModelData().bakeRoot());
        ModelPart part = boothExteriorModel.root();
        part.getChild("Door").visible = false;
        part.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        matrices.pushPose();
        if (!AITModClient.CONFIG.animateDoors)
            this.k2.getChild("Door").yRot = tardis.door().isOpen() ? 1.575F : 0.0F;
        else {
            float maxRot = 90f;
            this.k2.getChild("Door").yRot = (float) Math.toRadians(maxRot*tardis.door().getLeftRot());
        }

        matrices.scale(1f, 1f, 1f);
        matrices.translate(0, -1.5f, 0);
        matrices.mulPose(Axis.YP.rotationDegrees(180f));

        super.renderWithAnimations(tardis, linkableBlockEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);
        matrices.popPose();
    }
}
