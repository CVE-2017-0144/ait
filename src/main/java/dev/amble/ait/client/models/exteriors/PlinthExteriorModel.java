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

public class PlinthExteriorModel extends SimpleExteriorModel {
    private final ModelPart plinth;

    public PlinthExteriorModel(ModelPart root) {
        this.plinth = root.getChild("plinth");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition plinth = modelPartData.addOrReplaceChild("plinth", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition door = plinth.addOrReplaceChild("door", CubeListBuilder.create().texOffs(72, 61).addBox(-12.0F, -42.0F, 0.0F,
                12.0F, 42.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -3.0F, -8.0F));

        PartDefinition body = plinth.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(6.0F, -45.0F, -8.0F, 2.0F, 42.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 58).addBox(-9.0F, -3.0F, -9.0F, 18.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)).texOffs(54, 40)
                        .addBox(-9.0F, -48.0F, -9.0F, 18.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)).texOffs(0, 79)
                        .addBox(-6.0F, -45.0F, 6.0F, 12.0F, 42.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(36, 0)
                        .addBox(-8.0F, -45.0F, -8.0F, 2.0F, 42.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        plinth.render(matrices, vertexConsumer, light, overlay, color);
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

        if (!AITModClient.CONFIG.animateDoors)
            plinth.getChild("door").yRot = falling.tardis().get().door().isOpen() ? -1.75f : 0f;
        else {
            float maxRot = 90f;
            plinth.getChild("door").yRot = (float) Math.toRadians(maxRot*falling.tardis().get().door().getLeftRot());
        }

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        matrices.popPose();
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        if (!AITModClient.CONFIG.animateDoors)
            plinth.getChild("door").yRot = tardis.door().isOpen() ? -1.75f : 0f;
        else {
            float maxRot = 90f;
            plinth.getChild("door").yRot = -(float) Math.toRadians(maxRot*tardis.door().getLeftRot());
        }

        if (isBOTI) {
            matrices.pushPose();
            matrices.translate(0, -1.5f, 0);
            plinth.getChild("door").render(matrices, vertices, light, overlay, FastColor.ARGB32.colorFromFloat(pAlpha, red, green, blue));
            matrices.popPose();
        }
    }

    @Override
    public ModelPart root() {
        return plinth;
    }
}
